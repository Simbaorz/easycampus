package com.thelionking.campus.message;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.thelionking.campus.MainApp;
import com.thelionking.campus.model.Building;
import com.thelionking.campus.util.Parser;

/**
 * Created by the lion king on 2014/9/21.
 */
public final class DataManager implements Destroyable{

    //TAG
    public static final String TAG = "DataManager";

    //Context
    private Context context = null;

    //imageManager
    private ImageManager imageManager = null;

    public volatile boolean imgRecycleClosed = false;

    //DataList的name
    //JokeFragment的name
    public static final String TEXT_RECOMMEND = "TEXT_RECOMMEND";
    public static final String TEXT_LASTEST = "TEXT_LASTEST";
    public static final String PICTURE_RECOMMEND = "PICTURE_RECOMMEND";
    public static final String PICTURE_LASTEST = "PICTURE_LASTEST";

    public static volatile DataManager dataManager = null;
    //存储几个List的映射
    private Map<String, DataList> map = null;

    //所有Joke集合,暂时没使用
    private Set<Joke> jokes = null;
    
    private List<Building> buildings;

    //初始化都放到init里
    private DataManager() {

    }

    //DCL Singleton
    public static DataManager getInstance() {
        if (dataManager == null) {
            synchronized (DataManager.class) {
                dataManager = new DataManager();
            }
        }
        return dataManager;
    }

    public void init(Context context, ImageManager imageManager) {
        this.context = context;
		this.buildings = null;
        map = new HashMap<String, DataList>();
        //此处测试
        DataList textRecommend = new PictureRecommendDataList(TEXT_RECOMMEND, context, DBHelper.JOKE_TEXT_TABLE_NAME);
        DataList textLastest = new TextLastestDataList(TEXT_LASTEST, context, DBHelper.JOKE_TEXT_TABLE_NAME);
        DataList pictureRecommend = new PictureRecommendDataList(PICTURE_RECOMMEND, context, DBHelper.JOKE_PICTURE_TABLE_NAME);
        DataList pictureLastest = new PictureLastestDataList(PICTURE_LASTEST, context, DBHelper.JOKE_PICTURE_TABLE_NAME);
        //添加
        map.put(TEXT_RECOMMEND, textRecommend);
        map.put(TEXT_LASTEST, textLastest);
        map.put(PICTURE_LASTEST, pictureLastest);
        map.put(PICTURE_RECOMMEND, pictureRecommend);
//        map.put(RANDOM, random);
        jokes = new HashSet<Joke>();
        imgRecycleClosed = false;
        this.imageManager = imageManager;
        new Thread(new ImageRecycle()).start();
    }

    private class ImageRecycle implements Runnable {


        public ImageRecycle() {

        }

        @Override
        public void run() {
            select();
        }

        /**
         * 销毁过程
         * @param urls 挑选出的url
         */
        private void recycle(Set<String> urls) {
            imageManager.removeAndDestoryPicture(urls);
        }

        /**
         * 挑选过程
         */
        private void select() {
            Set<String> urls = new HashSet<String>();
            while (!imgRecycleClosed) {
                urls.clear();
                try {
                    Thread.sleep(30 * 1000);
//                  时间复杂度O(n^2)
                    for (Map.Entry<String, DataList> entry : map.entrySet()) {
                        if(!entry.getValue().getVisibleRange().state()){
                            continue;
                        }
                        int top = entry.getValue().getVisibleRange().getTop();
                        int bottom = entry.getValue().getVisibleRange().getBottom();
                        for (int i = top; i <= bottom; ++i) {
                            //此处暂时未做同步处理，稍后讨论
                            urls.add(entry.getValue().jokes.get(i).getImgurl());
                        }
                        entry.getValue().getVisibleRange().setLast();

                    }
                    if(urls.size() > 0) {
                        recycle(urls);
                    }
                } catch (InterruptedException e) {
                    Log.d(TAG, "ImageRecycle error!");
                }
            }
        }
    }

    public Map<String, DataList> getMap() {
        return this.map;
    }

    public void ready(MainApp.DataLoadListener context) {
        new AsyncTask<MainApp.DataLoadListener, Void, Void>(){
            @Override
            protected Void doInBackground(MainApp.DataLoadListener... params) {
                final long interval = 1 * 1000;
                long before = System.currentTimeMillis();

                for (Map.Entry<String, DataList> entry : map.entrySet()) {
                    if(!entry.getValue().ready()){
                        Log.d(TAG, "" + entry.getValue().getName() + " : -----------------------------初始化数据失败----------------------------------------");
                    }else{
                        Log.d(TAG, "" + entry.getValue().getName() + " : -----------------------------初始化数据成功----------------------------------------");
                    }
                }
                long elpased = System.currentTimeMillis() - before;
                if( elpased < interval){
                    try {
                        Thread.sleep(interval - elpased);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                params[0].onLoadFinished(0);
                return null;
            }
        }.execute(context);
    }

    @Override
    public void onDestroy() {
        this.imgRecycleClosed = true;
        //把所有笑话数据写入内存
        for (Map.Entry<String, DataList> entry : map.entrySet()) {
            entry.getValue().onDestroy();
        }
    }
    
	public void initData() {
		buildings = parserBuilding();
	}
	
	/**
	 * 解析Building
	 * @return
	 */
	private List<Building> parserBuilding() {
        InputStream in = null;
        try{
            in = context.getAssets().open("buildings.xml");
            buildings = Parser.parseBuilding(in);
            return buildings;
        }catch (Exception e){
        	return null;
        }finally {
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
	}
	
	public List<Building> getBuildings(){
		return buildings;
	}


    
    

}
