package com.thelionking.campus.message;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

/**
 * 解析xml的类
 * Created by the lion king on 2014/9/20.
 */
public final class JokeParser {
    public static final String TAG = "JokeParser";

    public JokeParser() {}

    public JokeData parser(InputStream in) {
        JokeData jokeData = null;
        List<Joke> jokes = null;
        Joke joke = null;
        XmlPullParser parser = Xml.newPullParser();
        try {
            parser.setInput(in, "utf-8");
            int eventType = parser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT : {
                        jokeData = new JokeData();
                        jokes = new ArrayList<Joke>();
                        break;
                    }
                    case XmlPullParser.START_TAG : {
                        if(parser.getName().equals("nextStart")){
                            jokeData.nextStart = Integer.parseInt(parser.nextText().trim());
                        }
                        else if(parser.getName().equals("joke")){
                            joke = new Joke();
                        }else if(parser.getName().equals("id")){
                            joke.setId(Integer.parseInt(parser.nextText().trim()));
                        }else if(parser.getName().equals("imgurl")){
                            joke.setImgurl(parser.nextText().trim());
                        }else if(parser.getName().equals("content")){
                            joke.setContent(parser.nextText());
                        }else if(parser.getName().equals("readingCount")){
                            joke.setReadingCount(Integer.parseInt(parser.nextText().trim()));
                        }else if(parser.getName().equals("source")){
                            joke.setSource(parser.nextText().trim());
                        }else if(parser.getName().equals("date")){
                            joke.setDate(Long.parseLong(parser.nextText()));
                        }else if(parser.getName().equals("author")){
                            joke.setAuthor(CommonUtil.cutAuthorName(parser.nextText().trim()));
                        }else if(parser.getName().equals("type")){
                            joke.setType(Integer.parseInt(parser.nextText()));
                        }else{
                            Log.d(TAG, "start tag error : " + parser.getName());
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG : {
                        if(parser.getName().equals("joke")){
                            jokes.add(joke);
                        }
                        break;
                    }

                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            Log.d(TAG, "XmlPullParser error");
        } catch (IOException e) {
            Log.d(TAG, "parser next error");
        }finally {
            if(in != null) {
                try{
                    in.close();
                } catch (IOException e) {
                    Log.d(TAG, "closed input stream error");
                }
            }
        }
        jokeData.jokes = jokes;
        return jokeData;
    }

    public static class JokeData{
        public List<Joke> jokes;

        public int nextStart;

        public JokeData() {

        }
    }
}
