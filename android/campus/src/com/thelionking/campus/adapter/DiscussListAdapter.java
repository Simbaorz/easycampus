package com.thelionking.campus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thelionking.campus.R;
import com.thelionking.campus.customview.DiscussView;
import com.thelionking.campus.model.Discuss;


/**
 * @author the lion king
 */
public class DiscussListAdapter extends BaseAdapter{
    private Context context;

    private List<Discuss> discusses;
    public DiscussListAdapter(final Context context) {
        this.context = context;
        ready();
    }

    @Override
    public int getCount() {
        return discusses.size();
    }

    @Override
    public Object getItem(int position) {
        return discusses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return discusses.get(position).getDiscussId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	DiscussView view = null;
        if(convertView == null) {
        	view = new DiscussView(context);
        }else{
        	view = (DiscussView)convertView;
        }
        view.setDiscuss(discusses.get(position));
        return view;
    }
    
    private void ready() {
    	discusses = new ArrayList<Discuss>();
    	Discuss d1= new Discuss();
    	d1.setDiscussTitle("求大神一个微积分问题");
    	d1.setDiscussTime(System.currentTimeMillis());
    	d1.setDiscussUsername("狮子王");
    	d1.setDiscussType(Discuss.DISCUSS_TYPE_PA);
    	d1.setDiscussContent("求大神一个微积分问题");
    	Discuss d2= new Discuss();
    	d2.setDiscussTitle("求大神一个微积分问题");
    	d2.setDiscussTime(System.currentTimeMillis());
    	d2.setDiscussUsername("伤心的小鸟");
    	d2.setDiscussType(Discuss.DISCUSS_TYPE_HANG);
    	d2.setDiscussContent("求大神一个微积分问题");
    	Discuss d3= new Discuss();
    	d3.setDiscussTitle("求大神一个微积分问题");
    	d3.setDiscussTime(System.currentTimeMillis());
    	d3.setDiscussUsername("光棍的人生");
    	d3.setDiscussType(Discuss.DISCUSS_TYPE_PA);
    	d3.setDiscussContent("求大神一个微积分问题");
    	Discuss d4= new Discuss();
    	d4.setDiscussTitle("求大神一个微积分问题");
    	d4.setDiscussTime(System.currentTimeMillis());
    	d4.setDiscussUsername("类好啊");
    	d4.setDiscussType(Discuss.DISCUSS_DEAL);
    	d4.setDiscussContent("求大神一个微积分问题");
    	discusses.add(d1);
    	discusses.add(d2);
    	discusses.add(d3);
    	discusses.add(d4);
    	
    }
}
