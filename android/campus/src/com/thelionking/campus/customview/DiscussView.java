package com.thelionking.campus.customview;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.thelionking.campus.R;
import com.thelionking.campus.activity.DiscussCommentActivity;
import com.thelionking.campus.model.Discuss;
import com.thelionking.campus.util.Utility;

/**
 * @author the lion king
 */
public class DiscussView extends FrameLayout {
    public static final String TAG = "DiscussView";
    private Discuss discuss;

    private ViewHolder holder = new ViewHolder();

    public DiscussView(Context context) {
        super(context);
        initView();
        setListener();
    }

    public DiscussView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        setListener();
    }

    public DiscussView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setListener();
    }

    public void setDiscuss(Discuss discuss){
        this.discuss = discuss;
        updateData();
    }

    private void initView(){
        View root = View.inflate(getContext(), R.layout.discuss_list_item, null);
        holder.discussType = (TextView)root.findViewById(R.id.discuss_type);
        holder.discussContent = (TextView)root.findViewById(R.id.discuss_content);
        holder.discussTitle = (TextView)root.findViewById(R.id.discuss_title);
        holder.discussUsername = (TextView)root.findViewById(R.id.discuss_user_name);
        holder.discussTime = (TextView)root.findViewById(R.id.discuss_time);
        addView(root);
    }

    private void updateData(){
    	if(discuss.getDiscussType() == Discuss.DISCUSS_TYPE_PA){
    		holder.discussType.setText("求");
    		holder.discussType.setBackgroundResource(R.drawable.qiu_background);
    	}else if(discuss.getDiscussType() == Discuss.DISCUSS_TYPE_HANG){
    		holder.discussType.setText("闲");
    		holder.discussType.setBackgroundResource(R.drawable.xian_background);
    	}else if(discuss.getDiscussType() == Discuss.DISCUSS_DEAL){
    		holder.discussType.setBackgroundResource(R.drawable.jiao_background);
    		holder.discussType.setText("交");
    	}
    	holder.discussContent.setText(discuss.getDiscussContent());
    	holder.discussTime.setText(Utility.getDate(discuss.getDiscussTime()));
    	holder.discussTitle.setText(discuss.getDiscussTitle());
    	holder.discussUsername.setText(discuss.getDiscussUsername());
    }

    private static final class ViewHolder{
        TextView discussType;
        TextView discussUsername;
        TextView discussTime;
        TextView discussTitle;
        TextView discussContent;
    }
    
    private void setListener() {
    	//holder.textView.setOnClickListener();
    	this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), DiscussCommentActivity.class);
				intent.putExtra("discuss", discuss);
				getContext().startActivity(intent);
			}
		});
    }
    

}
