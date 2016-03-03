package com.thelionking.campus.adapter;

import com.thelionking.campus.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


/**
 * @author the lion king
 */
public class SelectorAdapter extends BaseAdapter{
	private static final String[] TYPE = {
		"校园新闻",
		"校园活动",
		"校园赛事",
		"校园讲座",
		"校园招聘",
		"全部资讯"
		
	};
	
    private Context context;


    public SelectorAdapter(final Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
    	return TYPE.length;
    }

    @Override
    public Object getItem(int position) {
        return TYPE[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View root = View.inflate(context, R.layout.selector_item, null);
       TextView item = (TextView)root.findViewById(R.id.item);
       item.setText(TYPE[position]);
       View divider = root.findViewById(R.id.selector_divider);
       if(position == TYPE.length - 1){
    	   divider.setVisibility(View.GONE);
       }
       return root;
    }
    
	
	
}
