package com.thelionking.campus.message;

import android.content.Context;
import android.util.Log;

import com.thelionking.campus.message.Joke;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.List;

/**
 * Created by the lion king on 2014/10/22.
 * 单例，负责缓存的管理（图片缓存，笑话缓存）
 */
public final class CacheManager implements Destroyable{
    public static final String TAG = "CacheManager";
    //执行环境
    private Context context;

    private CacheManager(){}

    public void init(Context context){
        this.context = context;
    }

    private static class SingletonHelper{
        static CacheManager INSTANCE = new CacheManager();
    }

    public static CacheManager getInstance() {
        return SingletonHelper.INSTANCE;
    }

    public List<Joke> readJokeBuffer(String bufferName) {
//        Log.d(TAG, context.getFileStreamPath(bufferName).toString());
        Log.d(TAG, context.toString());
        File f = new File(context.getFilesDir(), bufferName);
        if(!f.exists()){
            return null;
        }
        List<Joke> jokes = null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = context.openFileInput(bufferName);
            ois = new ObjectInputStream(fis);
            jokes = (List<Joke>) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jokes;
    }

    public void writeJokeBuffer(String bufferName, List<Joke> jokes){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(bufferName, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(jokes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }finally {
            try {
                if(oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearJokeBuffer(String bufferName) {
        File f = new File(context.getFilesDir(), bufferName);
        if(!f.exists()){
            context.deleteFile(bufferName);
        }
    }

    public long getImageCacheSize() {
        return 0;
    }

    public void deleteImageCacheBuffer() {

    }

    @Override
    public void onDestroy() {

    }
}
