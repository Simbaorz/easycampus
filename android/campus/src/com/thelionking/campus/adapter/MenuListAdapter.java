package com.thelionking.campus.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thelionking.campus.MainApp;
import com.thelionking.campus.R;


/**
 * @author the lion king
 */
public class MenuListAdapter extends BaseAdapter{
	public static final String TAG = "MenuListAdapter";
    private Context context;
    
    private int selectedNumber;

    public MenuListAdapter(final Context context) {
        this.context = context;
        selectedNumber = 0;
    }

    private static final String[] ITEM_NAMES = {
    	"校园资讯",
    	"课程表",
        "自习室",
//        "考试题",
        "美食",
//        "周边",
        "校园服务"
    };
    
    private static final int[] ITEM_IMAGES = {
    	R.drawable.campus_message_flag,
    	R.drawable.self_study_room_flag,
//    	R.drawable.questions_flag,
    	R.drawable.delicacy_flag,
    	R.drawable.syllabus_flag,
//    	R.drawable.surrording,
    	R.drawable.surrording
    };

    @Override
    public int getCount() {
        return ITEM_NAMES.length;
    }

    @Override
    public Object getItem(int position) {
        return ITEM_NAMES[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	View view = null;
//    	if(position == 0) {
//    		//已登录
//    		if(MainApp.getInstance().getLogManager().getLoginState()){
//    			view = View.inflate(context, R.layout.menu_list_view_user_logined_item, null);
//    			TextView button = (TextView)view.findViewById(R.id.item_name);
//                button.setText(ITEM_NAMES[position]);
//    		}
//    		//未登录
//    		else{
//    			view = View.inflate(context, R.layout.menu_list_view_user_unlogined_item, null);
//    			TextView button = (TextView)view.findViewById(R.id.item_name);
//                button.setText(ITEM_NAMES[position]);
//    		}
//    	}else if(position == 7){
//    		view = View.inflate(context, R.layout.menu_list_view_setting_item, null);
//    	}else{
//    		if(position == selectedNumber){
//    			view = View.inflate(context, R.layout.menu_list_view_selected_item, null);
//    			TextView button = (TextView)view.findViewById(R.id.item_name);
//                button.setText(ITEM_NAMES[position]);
//    		}else{
//    			view = View.inflate(context, R.layout.menu_list_view_unselected_item, null);
//    			TextView button = (TextView)view.findViewById(R.id.item_name);
//                button.setText(ITEM_NAMES[position]);
//    		}
//    	}
    	Log.d(TAG, position + " | "  + selectedNumber);
//    	if(position == selectedNumber){
//			view = View.inflate(context, R.layout.menu_list_view_selected_item, null);
//			TextView button = (TextView)view.findViewById(R.id.item_name);
//			ImageView image = (ImageView)view.findViewById(R.id.item_image_view);
//			View divider = view.findViewById(R.id.divider);
//			if(position == 4){
//				divider.setVisibility(View.GONE);
//			}
//			image.setImageResource(ITEM_IMAGES[position]);
//            button.setText(ITEM_NAMES[position]);
//            
//		}else{
			view = View.inflate(context, R.layout.menu_list_view_unselected_item, null);
			TextView button = (TextView)view.findViewById(R.id.item_name);
			ImageView image = (ImageView)view.findViewById(R.id.item_image_view);
			View divider = view.findViewById(R.id.divider_menu);
			if(position == 4){
				divider.setVisibility(View.GONE);
			}
			image.setImageResource(ITEM_IMAGES[position]);
            button.setText(ITEM_NAMES[position]);
//		}
        return view;
    }
    
    public void setSelectedNumber(int selectedNumber) {
    	this.selectedNumber = selectedNumber;
    }
}
