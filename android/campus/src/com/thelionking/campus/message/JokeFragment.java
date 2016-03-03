package com.thelionking.campus.message;


import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.thelionking.campus.MainApp;
import com.thelionking.campus.R;

/**
 * Created by the lion king on 2014/9/16.
 * 鬼故事列表Fragment
 */
public class JokeFragment extends Fragment implements ImageManager.ImageDownloadListener, MainApp.DataRefreshListener {
    //TAG
    public static final String TAG = "JokeListFragment";

    //DataList的索引
    private DataList dataList = null;

    //此JokeListFragment的标示，图片下载回调时候使用
    private String name = null;

    //下拉刷新数据下载成功
    public static final int TOP_JOKE_DOWNLOAD_SUCCESSED = 0X0;

    //下拉刷新数据下载失败
    public static final int TOP_JOKE_DOWNLOAD_FAILED = 0X1;

    //上拉刷新数据下载成功
    public static final int BOTTOM_JOKE_DOWNLOAD_SUCCESSED = 0X2;

    //上拉刷新数据下载失败
    public static final int BOTTOM_JOKE_DOWNLOAD_FAILED = 0X3;

    //图片下载成功
    public static final int PICTURE_DOWNLOAD_SUCCESSED = 0X4;

    //图片下载失败
    public static final int PICTURE_DOWNLOAD_FAILED = 0X5;

    //fragment的编号，暂时无用
    private int number = 0;

    //下拉刷新listView
    private PullToRefreshListView refreshView = null;

    private View moreView = null;

    private Button more = null;

    private ProgressBar wait = null;

    //下拉刷新ListView的适配器
    private RefreshAdapter refreshAdapter = null;

    //消息
    private MsgHandler handler = null;

    /**
     * 绑定到activity时会调用
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    /**
     * 创建时调用
     * 存储一些需要临时保存的数据
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApp.getInstance().getImageManager().addToImageDownloadCallback(this.name, this);
        handler = new MsgHandler();
        dataList = MainApp.getInstance().getDataManager().getMap().get(this.name);
        refreshAdapter = new RefreshAdapter(getActivity(), dataList.getJokes());
        Log.d(TAG, "onCreate()");
    }

    /**
     *  第一次绘制该fragment界面时调用，需返回view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joke, container, false);
        refreshView = (PullToRefreshListView)view.findViewById(R.id.refresh_view);
        moreView = inflater.inflate(R.layout.click_to_load_more, null, false);
        ListView lv = refreshView.getRefreshableView();
        lv.addFooterView(moreView);
        updateFooterView();
        more = (Button)moreView.findViewById(R.id.bt_load);
        wait = (ProgressBar)moreView.findViewById(R.id.pg);
        refreshView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        refreshView.setAdapter(refreshAdapter);
        hookListener();
        return view;
    }

    /**
     * 用户将要离开fragment时,系统调用这个方法作为第一个指示(然而它不总是意味着fragment将被销毁.)
     * 在当前用户会话结束之前,通常应当在这里提交任何应该持久化的变化(因为用户有可能不会返回).
     */
    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 绑定的activity执行onCreated时调用
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 销毁时调用
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 销毁视图时调用
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void setNumber(int number){
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    /**
     * 绑定监听器
     */
    private void hookListener() {
        if(this.refreshView !=null){
            refreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                @Override
                public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                    Log.d(TAG, "onRefresh from top");
                    //此处的作用是当刷新时间在每天的0点-0点5分之间不可以刷新（给服务器时间更新数据）
                    long current = System.currentTimeMillis();
                    if(current - CommonUtil.getMillils(0) < 5 * CommonUtil.MINUTE) {
                        return;
                    }
                    dataList.getFromTopAndNotify(JokeFragment.this);
                }
            });

            //每次滑动屏幕的回调接口
            refreshView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    //do nothing
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                    dataList.getVisibleRange().setRange(firstVisibleItem, firstVisibleItem + visibleItemCount - 1);
                }
            });

            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "OnClick-----------------------");
                    //此处的作用是当刷新时间在每天的0点-0点5分之间不可以刷新（给服务器时间更新数据）
                    long current = System.currentTimeMillis();
                    if(current - CommonUtil.getMillils(0) < 5 * CommonUtil.MINUTE) {
                        return;
                    }
                    if(v == more){
                        more.setVisibility(View.GONE);
                        wait.setVisibility(View.VISIBLE);
                        dataList.getFromBottomAndNotify(JokeFragment.this);
                    }
                }
            });
        }
    }

    @Override
    public void onFinished(int code) {
        if(code == MainApp.DataRefreshListener.TOP_FINISHED){
            handler.sendEmptyMessage(TOP_JOKE_DOWNLOAD_SUCCESSED);
        }else{
            handler.sendEmptyMessage(BOTTOM_JOKE_DOWNLOAD_SUCCESSED);
        }
    }

    @Override
    public void onFailed(int code) {
        if(code == MainApp.DataRefreshListener.TOP_FAILED){
            handler.sendEmptyMessage(TOP_JOKE_DOWNLOAD_FAILED);
        }else{
            handler.sendEmptyMessage(BOTTOM_JOKE_DOWNLOAD_FAILED);
        }
    }

    /**
     * 通信
     */
    private class MsgHandler extends android.os.Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case TOP_JOKE_DOWNLOAD_SUCCESSED: {
                    refreshView.onRefreshComplete();
                    refreshAdapter.notifyDataSetChanged();
                    updateFooterView();
                    Log.d(TAG, "refreshCompleted");
                    break;
                }
                case TOP_JOKE_DOWNLOAD_FAILED: {
                    refreshView.onRefreshComplete();
                    updateFooterView();
                    Log.d(TAG, "refreshFailed");
                    break;
                }
                case BOTTOM_JOKE_DOWNLOAD_SUCCESSED: {
                    more.setVisibility(View.VISIBLE);
                    wait.setVisibility(View.GONE);
                    refreshView.onRefreshComplete();
                    refreshAdapter.notifyDataSetChanged();
                    updateFooterView();
                    Log.d(TAG, "refreshCompleted");
                    break;
                }
                case BOTTOM_JOKE_DOWNLOAD_FAILED: {
                    refreshView.onRefreshComplete();
                    updateFooterView();
                    Log.d(TAG, "refreshFailed");
                    break;
                }
                case PICTURE_DOWNLOAD_SUCCESSED : {
                    Log.d(TAG, "Name: " + name +  " ： Picture Download SUCCESSED");
                    refreshAdapter.notifyDataSetChanged();
                    break;
                }
                case PICTURE_DOWNLOAD_FAILED : {Log.d(TAG, "Picture download error");

                    break;
                }
                default:{
                    //do nothing
                }
            }
        }
    }

    /**
     * 此方法要在,Fragment被添加到Activity之前设置
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void onDownloadSuccessed(int code) {
        handler.sendEmptyMessage(PICTURE_DOWNLOAD_SUCCESSED);
    }

    @Override
    public void onDownloadFailed(int code) {
        handler.sendEmptyMessage(PICTURE_DOWNLOAD_FAILED);
    }

    public String getName() {
        return this.name;
    }

    private void updateFooterView() {
        ListView lv = refreshView.getRefreshableView();
        if(dataList.getJokes().size() > 0) {
            moreView.setVisibility(View.VISIBLE);
        }
        else if(dataList.getJokes().size() == 0) {
            moreView.setVisibility(View.GONE);
        }
    }
}
