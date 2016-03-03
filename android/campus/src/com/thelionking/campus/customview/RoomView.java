package com.thelionking.campus.customview;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thelionking.campus.R;
import com.thelionking.campus.activity.SelfStudyRoomActivity;
import com.thelionking.campus.model.Room;

/**
 * @author the lion king
 */
public class RoomView extends FrameLayout {
    public static final String TAG = "RoomView";
    private Room room;

    private ViewHolder holder = new ViewHolder();

    public RoomView(Context context) {
        super(context);
        initView();
        setListener();
    }

    public RoomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        setListener();
    }

    public RoomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setListener();
    }

    public void setRoom(Room room){
        this.room = room;
        updateData();
    }

    private void initView(){
        View root = View.inflate(getContext(), R.layout.room_list_item, null);
        holder.occupancyRateText = (TextView)root.findViewById(R.id.occupancy_rate_text);
        holder.progressBar = (ProgressBar)root.findViewById(R.id.progressBar1);
        addView(root);
    }

    private void updateData(){
        holder.occupancyRateText.setText(room.getRoomName() + " [" + room.getRoomUseRate() + "%]");
        holder.progressBar.setProgress(room.getRoomUseRate());
    }

    private static final class ViewHolder{
        TextView occupancyRateText;
        ProgressBar progressBar;
    }
    
    private void setListener() {
    	holder.occupancyRateText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), SelfStudyRoomActivity.class);
				intent.putExtra("room", room);
				getContext().startActivity(intent);
			}
		});
    }
    
}
