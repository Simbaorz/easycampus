package com.thelionking.campus.message;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Created by the lion king on 2014/9/21.
 */
public final class ImageManager {
    //TAG
    public static final String TAG = "ImageManager";
    //ImageManager需要Context进行下载图片的保存等工作
    private Context context = null;

    //image下载等待队列
    private Queue<String> waitQueue = null;

    //image下载中的队列
    private Set<String> downloadingSet = null;

    //当前位于的Fragment名称，下载完成事件发生时可能需要回调方法（暂时没使用）
    private String currentFragmentName = null;

    //线程关闭按钮
    private volatile boolean imageDownLoadClosed = false;

    //单例
    public static volatile ImageManager imageManager = null;

    //图片下载好后的回调列表
    private Map<String, ImageDownloadListener> iImageDownloadedMap = null;

    //以Picture的Url作为key, 缓存时使用MD5算法作为索引
    public Map<String, Picture> pictureMap = null;

    private ImageManager() {
    }

    //初始化
    public void init(Context context) {
        this.context = context;
        pictureMap = new HashMap<String, Picture>();
        waitQueue = new LinkedList<String>();
        //初始容量为3
        iImageDownloadedMap = new HashMap<String, ImageDownloadListener>(3);
        //初始化当前fragment为recommend
        //正在下载的集合，处在正在下载或者下载后备队列的数据不重新放在waitQueue;
        downloadingSet = new HashSet<String>(2);
        //暂时没使用
        currentFragmentName = DataManager.PICTURE_LASTEST;
        new Thread(new ImageDownLoadThread()).start();
        new Thread(new ImageDownLoadThread()).start();
    }

    //DCL Singleton
    public static ImageManager getInstance() {
        if (imageManager == null) {
            synchronized (ImageManager.class) {
                imageManager = new ImageManager();
            }
        }
        return imageManager;
    }

    //图片对象
    public static class Picture {
        private Bitmap bitmap = null;
        //url就是key值，暂时无用处
        private String url = null;

        public Picture(Bitmap bitmap, String url) {
            this.bitmap = bitmap;
            this.url = url;
        }

        //释放自己
        public void destroy() {
            if (this.bitmap == null || !this.bitmap.isRecycled()) {
                this.bitmap.recycle();
            }
            this.bitmap = null;
        }
    }

    //可能造成线程同步问题，需要获得锁
    public void addPicture(String url, Picture picture) {
        synchronized (pictureMap) {
            if (pictureMap.containsKey(url)) {
                picture.destroy();
                picture = null;
                return;
            }
            pictureMap.put(url, picture);
        }
    }

    public void removeAndDestoryPicture(Set<String> urls) {
        int deleteCount = 0;
        List<String> toDelete = new ArrayList<String>();
        synchronized (pictureMap) {
            Log.d(TAG, "Before delete picture map size is " + pictureMap.size());
            for(String key : pictureMap.keySet()){
                if (!urls.contains(key)) {
                    toDelete.add(key);
                }
            }
            Log.d(TAG, "After selection, to delete list size is " + toDelete.size());
            for(String key : toDelete){
                Picture picture = pictureMap.remove(key);
                picture.destroy();
                ++deleteCount;
            }
            Log.d(TAG, "After selection, delete count is " + deleteCount);
            Log.d(TAG, "After selection, pictureMap count is " + pictureMap.size());
        }
    }

    public boolean containPicture(String url) {
        synchronized (pictureMap) {
            return pictureMap.containsKey(url);
        }
    }

    //机制类似于生产者消费者，此处可能也存在并发问题，需要考虑
    private class ImageDownLoadThread implements Runnable {

        public ImageDownLoadThread() {

        }

        @Override
        public void run() {
            download();
        }

        public void download() {
            String url = null;
            while (!imageDownLoadClosed) {
                synchronized (waitQueue) {
                    //当下载队列为空时，wait(),在接受notity方法时，可能会别其他线程获得锁，因此需要循环判断
                    while (waitQueue.size() == 0) {
                        try {
                            waitQueue.wait();
                            continue;
                        } catch (InterruptedException e) {
                            Log.d(TAG, "Url of downloading queue retrieve error");
                        }
                    }
                    Log.d(TAG, "COUNT " + waitQueue.size());
                    url = waitQueue.remove();
                    Log.d(TAG, "EXE");
                    synchronized (downloadingSet){
                        Log.d(TAG, "downloadingSet is added element :" + url);
                        downloadingSet.add(url);
                    }
                }

                //以下是下载,下载完的数据放入pictureMap中
                //若已存在则返回循环，以下两个条件可不加
                if (containPicture(url)) {
                    Log.d(TAG, "containKey");
                    continue;
                }

                if (getPictureFromCache(url) != null) {
                    Log.d(TAG, "containKey in cache");
                    continue;
                }
//              不存在则下载
                InputStream is = null;
                OutputStream os = null;
                HttpURLConnection conn = null;
                Bitmap bitmap = null;
                try {
                    URL target = new URL(url);
                    conn = (HttpURLConnection) target.openConnection();
                    conn.setReadTimeout(5000);
                    conn.connect();
                    is = new BufferedInputStream(conn.getInputStream());
                    os = new BufferedOutputStream(context.openFileOutput(CommonUtil.md5sum(url.getBytes()), Context.MODE_PRIVATE));
                    //从网络读取数据
                    int length = -1;
                    byte[] buffer = new byte[1024];
                    while ((length = is.read(buffer)) != -1) {
                        os.write(buffer, 0 , length);
                    }
                    os.flush();
//                    iImageDownloadedMap.get(DataManager.PICTURE_RECOMMEND).onDownloadSuccessed(0);
                    iImageDownloadedMap.get(DataManager.PICTURE_LASTEST).onDownloadSuccessed(0);
                    Log.d(TAG, "successed download");
                } catch (MalformedURLException e) {

                    Log.d(TAG, "Image Download Error");
                } catch (IOException e) {

                    Log.d(TAG, "image download open Connection error");
                } finally {
                    synchronized (downloadingSet){
                        downloadingSet.remove(url);
                    }
                    if (os != null) {
                        try {
                            os.close();
                        } catch (IOException e) {
                            Log.d(TAG, "is closed error");
                        }
                    }

                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            Log.d(TAG, "os closed error");
                        }
                    }
                    if(conn != null) {
                        conn.disconnect();
                    }
                }

            }

        }
    }

    //图片下载回调接口
    public static interface ImageDownloadListener {
        public void onDownloadSuccessed(int code);

        public void onDownloadFailed(int code);

    }

    public void addToImageDownloadCallback(String key, ImageDownloadListener imageDownloadListener) {
        this.iImageDownloadedMap.put(key, imageDownloadListener);
    }

    public void setCurrentFragmentName(String currentFragmentName) {
        this.currentFragmentName = currentFragmentName;
    }

    /**
     * 会首先判断是否在下载队列里，然后判断是否在后备队列里，如果都不在，则加入
     * @param url
     */
    public void addToWaitQueue(String url) {
        synchronized (downloadingSet){
            synchronized (waitQueue){
                if(downloadingSet.contains(url) || waitQueue.contains(url)){
                    Log.d(TAG, "addToWaitQueue method do nothing : url is " + url);
                    return;
                }
                Log.d(TAG, "addToWaitQueue method : url is " + url);
                waitQueue.add(url);
                waitQueue.notifyAll();
            }
        }
    }

    //此处可能会有并发错误
    public Bitmap getPicture(String url) {
        synchronized (pictureMap) {
            if (!pictureMap.containsKey(url)) {
                Log.d(TAG, "缓存中没有此图片");
                return null;
            } else {
                return pictureMap.get(url).bitmap;
            }
        }
    }

    public Bitmap getPictureFromCache(String url) {
        synchronized (pictureMap) {
            if (pictureMap.containsKey(url)) {
                return pictureMap.get(url).bitmap;
            }
        }
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = context.openFileInput(CommonUtil.md5sum(url.getBytes()));
            bitmap = BitmapFactory.decodeStream(is);
            if (bitmap == null) {
                return null;
            }
            Picture p = new Picture(bitmap, url);
            synchronized (pictureMap) {
                addPicture(url, p);
            }
            //此处线程同步问题讨论
            Log.d(TAG, "从本地缓存加载图片成功！");
            return bitmap;
        } catch (FileNotFoundException e) {
            Log.d(TAG, "open file input error");
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.d(TAG, "InputStream closed error");
                }
            }
        }
    }

}
