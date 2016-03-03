package com.thelionking.campus.message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.util.Log;

/**
 * Created by the lion king on 2014/9/21.
 */
public final class PictureRecommendDataList extends DataList{

    public PictureRecommendDataList(String name, Context context, String tableName){
        super(name, context, tableName);
    }

    @Override
    public List<Joke> getJokeFromServer(final String action) {
        checkAndReset();
        if(topStart == DataList.INVALID_START) {
            return null;
        }
        boolean fromTop = action.equals(CommonUtil.FROM_TOP_ACTION);
        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            URL serverUrl = null;
            if(fromTop) {
                serverUrl = new URL(CommonUtil.spliceUrl(action, topStart, Joke.PICTURE, Joke.RECOMMEND_MODE, 0));
                Log.d(TAG, "get from url : " + CommonUtil.spliceUrl(action, topStart, Joke.PICTURE, Joke.RECOMMEND_MODE, 0));
            }else{
                serverUrl = new URL(CommonUtil.spliceUrl(action, bottomStart, Joke.PICTURE, Joke.RECOMMEND_MODE, 0));
                Log.d(TAG, "get from url : " + CommonUtil.spliceUrl(action, topStart, Joke.PICTURE, Joke.RECOMMEND_MODE, 0));
            }
            conn = (HttpURLConnection) serverUrl.openConnection();
            conn.connect();
            conn.setReadTimeout(4000);
            is = conn.getInputStream();
            JokeParser.JokeData data = new JokeParser().parser(is);
            if (data != null && data.jokes.size() != 0) {
                if(fromTop) {
                    topStart = data.nextStart;
                    saveTopStart();
                    Collections.sort(data.jokes, new CommonUtil.CustomComparator());
                }else{
                    bottomStart = data.nextStart;
                    saveBottomStart();
                }
//                saveJokeToDB(data.jokes);
                //拿到了数据，且是当天第一次刷新数据(此处不需要清除缓存了，因为写入缓存时会覆盖原数据)
                //每天默认第一次从服务器读取数据时（可能发生当天没从服务器读取过数据的情况）把jokes清空
                if(!first){
                    this.getJokes().clear();
                    first = true;
                    saveFrist();
                }
                return data.jokes;
            }
            //没有解析到数据
            else{
                return null;
            }
        } catch (MalformedURLException e) {
            Log.d(TAG, "getJokeFromServer() 出现错误！");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            Log.d(TAG, "getJokeFromServer() 出现IO错误！)");
            e.printStackTrace();
            return null;
        }
    }

}
