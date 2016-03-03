package com.thelionking.campus.message;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by the lion king on 2014/9/16.
 */
public class RefreshAdapter extends BaseAdapter{
    private static final String TAG = "RefreshAdapter";
    private List<Joke> jokes;

    private Context context;

    public RefreshAdapter(Context context, List<Joke> jokes) {
        this.context = context;
        this.jokes = jokes;
    }

    public void setJokes(List<Joke> jokes) {
        this.jokes = jokes;
    }

    public List<Joke> getJokes() {
        return this.jokes;
    }

    @Override
    public int getCount() {
        return getJokes().size();
    }

    @Override
    public Object getItem(int position) {
        return getJokes().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView : " + position);
        JokeItemView view = null;
        if (convertView == null) {
            view = new JokeItemView(context);
        } else {
            view = (JokeItemView)convertView;
        }
        view.setJoke(jokes.get(position));
        return view;
    }
}
