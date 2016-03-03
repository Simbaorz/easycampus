package com.thelionking.campus.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.thelionking.campus.MainApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

/**
 * Created by the lion king on 2014/9/18.
 * 数据集合的基类，封装了从服务器或从本地加载数据的方法，派生类可能根据获得数据的方式不同而分类
 * 采取每次从服务器取完数据存到内存，然后把内存中所有数据在cache中更新
 */
public abstract class DataList implements Destroyable{

    public static final String TAG = "DataList";

    public static final String TOP_START_TAG = "TOP_START_TAG";

    public static final String BOTTOM_START_TAG = "BOTTOM_START_TAG";

    public static final String ZERO_TAG = "ZERO_TAG";

    public static final String FIRST_TAG = "FIRST_TAG";

    public static final int INVALID_START = -1;
    //topStart每次向服务器申请数据时候提交给服务器，服务器根据此
    //号码，返回数据，tryNumber小于0时代表无效，0时代表第一次申请数据
    protected int topStart;
    //小于零代表没数据
    //0代表第一次从取数据
    protected int bottomStart;
    //当天的第一次数据是否抓取成功，如果成功，则删掉以前的记录
    //如果不成功则不删除记录
    protected boolean first;
    //需要频繁的操作，所以需要保留context
    protected Context context;
    //数据
    protected List<Joke> jokes;
    //name的作用相当于id,用name来标识一个DataList的对象
    protected String name;
    //可视区域
    protected VisibleRange visibleRange;
    //此对象映射到表的名称
    protected String tableName;

    public DataList(String name, Context context, String tableName) {
        //拿到一个线程相对安全的ArrayList
        //对象5种状态： 1.不变的 2.绝对安全 3.相对安全 4.线程兼容的 5.线程不兼容的
        jokes = Collections.synchronizedList(new ArrayList<Joke>());
        this.name = name;
        visibleRange = new VisibleRange();
        this.context = context;
        this.tableName = tableName;
        checkAndReset();
        getTopStart();
        getBottomStart();
        getFirst();
    }

    /**
     * 从服务器获取数据后通知context刷新数据
     * @param listener
     *  调用该方法的context
     */
    public void getFromTopAndNotify(final MainApp.DataRefreshListener listener){
        new AsyncTask<MainApp.DataRefreshListener, Void, Void>(){
            @Override
            protected Void doInBackground(MainApp.DataRefreshListener... params) {
                List<Joke> gg = getJokeFromServer(CommonUtil.FROM_TOP_ACTION);
                if(gg == null){
                    listener.onFailed(MainApp.DataRefreshListener.TOP_FAILED);
                }else{
                    getJokes().addAll(0, gg);
                    CacheManager.getInstance().writeJokeBuffer(name + "_BUFFER.SER", jokes);
                    listener.onFinished(MainApp.DataRefreshListener.TOP_FINISHED);
                }
                return null;
            }
        }.execute();
    }

    protected List<Joke> getJokeFromCache() {
        return MainApp.getInstance().getCacheManager().readJokeBuffer(this.name + "_BUFFER.SER");
    }

    /**
     * 从数据库拿数据
     * @return
     */
    protected List<Joke> getJokeFromDB() {
        JokeDao gd = new JokeDao(context);
        return gd.getAll(this.tableName);
    }

    /**
     * 从服务器获取数据
     * @return
     */
    protected abstract List<Joke> getJokeFromServer(final String action);

    /**
     * 将数据存储在本地数据库,存之前先按照时间排序
     * @return
     */
    protected boolean saveJokeToDB(List<Joke> jokes) {
        JokeDao gd = new JokeDao(this.context);
        return gd.save(jokes, this.tableName);
    }

    public List<Joke> getJokes() {
        return jokes;
    }

    public String getName() {
        return name;
    }

    public VisibleRange getVisibleRange() {
        return this.visibleRange;
    }

    //可视区域控制
    public class VisibleRange {
        //可见性
        private volatile int  top;
        private volatile int bottom;
        private int lastTop;
        private int lastBottom;

        public VisibleRange() {
            top = -1;
            bottom = -1;
            lastTop = top;
            lastBottom = bottom;
        }

        /**
         * 当前收集区域：
         * 1.当jokes.size < 5时，不收集
         * 2.当jokes.size >=5 时收集除了可见区域以外的所有图片
         *  暂时使用该算法，以后可能修改
         */
        public void setRange(int top, int bottom) {
            //当jokes的size小于5
            if(jokes.size() < 5 ){
                top = 0;
                bottom = jokes.size() - 1;
                this.top = top;
                this.bottom = bottom;
                return;
            }

            //当top为顶部或第一个元素的时候
            if(top <= 1){
                top = 0;
                //当bottom为jokes最后一个元素，底部元素，或者footer时候
                if(bottom >= jokes.size()){
                    bottom = jokes.size() - 1;
                }else{
                    --bottom;
                }
            }else{
                if(bottom >= jokes.size()){
                    --top;
                    bottom = jokes.size() - 1;
                }else{
                    --top;
                    --bottom;
                }
            }
            this.top = top;
            this.bottom = bottom;
        }

        public int getTop() {
            return this.top;
        }

        public int getBottom() {
            return this.bottom;
        }

        public void setLast() {
            lastTop = top;
            lastBottom = bottom;
        }

        public boolean state() {
            if(top <= bottom && top >= 0){
                return true;
            }
            return false;
        }

    }

    /**
     * 打开应用时的数据准备,不成功返回false
     */
    public boolean ready() {
        List<Joke> jokes = getJokeFromCache();
        if(jokes == null || jokes.size() == 0) {
            jokes = getJokeFromServer(CommonUtil.FROM_TOP_ACTION);
        }
        if (jokes == null) {
            return false;
        }else{
            //数据写入
            CacheManager.getInstance().writeJokeBuffer(name + "_BUFFER.SER", jokes);
        }
        this.jokes.addAll(jokes);
        return true;
    }

    /**
     * 将topStart存储到本地的xml中，通过sharedPreference;
     */
    public void saveTopStart() {
        SharedPreferences sp = context.getSharedPreferences(this.name, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(TOP_START_TAG, topStart);
        editor.commit();
    }

    /**
     * 从本地xml获得topStart的值，通过sharedPreference的方式
     * 若不存在默认的返回值是0
     */
    public void getTopStart() {
        SharedPreferences sp = context.getSharedPreferences(this.name, context.MODE_PRIVATE);
        topStart = sp.getInt(TOP_START_TAG, 0);
    }

    /**
     * 将bottomStart存储到本地的xml中，通过sharedPreference;
     */
    public void saveBottomStart() {
        SharedPreferences sp = context.getSharedPreferences(this.name, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(BOTTOM_START_TAG, bottomStart);
        editor.commit();
    }

    /**
     * 从本地xml获得first的值，通过sharedPreference的方式
     * 若不存在默认的返回值是false
     */
    public void getFirst() {
        SharedPreferences sp = context.getSharedPreferences(this.name, context.MODE_PRIVATE);
        first = sp.getBoolean(FIRST_TAG, false);
    }

    /**
     * 将first存储到本地的xml中，通过sharedPreference;
     */
    public void saveFrist() {
        SharedPreferences sp = context.getSharedPreferences(this.name, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(FIRST_TAG, first);
        editor.commit();
    }

    /**
     * 从本地xml获得bottomStart的值，通过sharedPreference的方式
     * 若不存在默认的返回值是0
     */
    public void getBottomStart() {
        SharedPreferences sp = context.getSharedPreferences(this.name, context.MODE_PRIVATE);
        bottomStart = sp.getInt(BOTTOM_START_TAG, 0);
    }

    /**
     * 从本地取数据
     */
    public void getFromBottomAndNotify(final MainApp.DataRefreshListener listener) {
        new AsyncTask<MainApp.DataRefreshListener, Void, Void>(){
            @Override
            protected Void doInBackground(MainApp.DataRefreshListener... params) {
                List<Joke> gg = getJokeFromServer(CommonUtil.FROM_BOTTOM_ACTION);
                if(gg == null){
                    listener.onFailed(MainApp.DataRefreshListener.BOTTOM_FAILED);
                }else{
                    getJokes().addAll(gg);
                    //将数据重新写入
                    CacheManager.getInstance().writeJokeBuffer(name + "_BUFFER.SER", jokes);
                    listener.onFinished(MainApp.DataRefreshListener.BOTTOM_FINFISHED);
                }
                return null;
            }
        }.execute();
    }

    /**
     * 验证topStart, bottomStart, zero, first是否需要重置
     * 如果跨越了一天则需要重置
     * @return
     */
    public void checkAndReset(){
        SharedPreferences sp = context.getSharedPreferences(this.name, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        long zero = sp.getLong(ZERO_TAG, CommonUtil.getMillils(0));
        long current = System.currentTimeMillis();
        if((current - zero) >= CommonUtil.DAY) {
            zero = CommonUtil.getMillils(0);
            topStart = 0;
            bottomStart = 0;
            first = false;
            editor.putLong(ZERO_TAG, zero);
            editor.putInt(TOP_START_TAG, topStart);
            editor.putInt(BOTTOM_START_TAG, bottomStart);
            editor.putBoolean(FIRST_TAG, first);
        }else {
            editor.putLong(ZERO_TAG, zero);
        }
        editor.commit();
    }

    @Override
    public void onDestroy() {

    }
}
