package com.thelionking.campus;

import android.app.Application;

import com.thelionking.campus.message.CacheManager;
import com.thelionking.campus.message.DataManager;
import com.thelionking.campus.message.ImageManager;
import com.thelionking.campus.util.Constant;

/**
 * @author the lion king
 * <h>MainApp继承了Application,运行期间的数据都放在此处</h>
 */
public class MainApp extends Application{
	public static final String TAG = "MainApp";
    //Application的句柄，方便存取数据
    private static MainApp app;
    
    private DataManager dataManager;
    
    private LogManager logManager;
   
    private ImageManager imageManager = null;

    private CacheManager cacheManager = null;

    public static MainApp getInstance() {
        if(app != null) {
           return app;
        }
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        imageManager = ImageManager.getInstance();
        imageManager.init(this);
        dataManager = DataManager.getInstance();
        dataManager.init(this, imageManager);
        logManager = LogManager.getInstance();
        logManager.init(this);
        cacheManager = CacheManager.getInstance();
        cacheManager.init(this);
    }

    /**
     * 数据准备监听
     */
    public static interface ReadyListener{
        void onReadySuccessed(int code);
        void onReadyFailed(int code);
    }

    /**
     * 数据准备
     * @param listener 回调的监听器
     */
    public void initData(final ReadyListener listener) {
        if(listener != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                    	long start = System.currentTimeMillis();
                    	//初始化数据
                    	dataManager.initData();
                    	long interval = System.currentTimeMillis() - start;
                    	if(interval < Constant.SECOND  * 2){
                    		Thread.sleep(Constant.SECOND  * 2 - interval);
                    	}
                        listener.onReadySuccessed(0);
                    } catch (InterruptedException e) {
                        listener.onReadyFailed(0);
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    
    public DataManager getDataManager() {
    	return dataManager;
    }
    
    public LogManager getLogManager() {
    	return logManager;
    }
    
    /**
     * 不要依赖此回调方法，清除缓存，此回调方法可能不被调用
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public ImageManager getImageManager() {
        return this.imageManager;
    }

    public CacheManager getCacheManager() {
        return this.cacheManager;
    }

    public static interface DataRefreshListener {
        public static final int TOP_FINISHED = 0;
        public static final int BOTTOM_FINFISHED =1;
        public static final int TOP_FAILED = 2;
        public static final int BOTTOM_FAILED =3;

        public void onFinished(int code);

        public void onFailed(int code);

    }
    
    /**
     * WelcomeActivity实现该接口，当数据准备完毕后回调
     */
    public static interface DataLoadListener {

        public void onLoadFinished(int code);

        public void onLoadFailed(int code);
    }
    
    //每次打开程序时准备数据
    public void ready(DataLoadListener context) {
        dataManager.ready(context);
    }
}
