package com.thelionking.campus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.thelionking.campus.MainApp;
import com.thelionking.campus.R;
import com.thelionking.campus.adapter.DiscussListAdapter;
import com.thelionking.campus.model.Room;
import com.thelionking.campus.util.Constant;

public class SelfStudyRoomActivity extends Activity{
	public static final String TAG = "SelfStudyRoomActivity";
	
	private TextView innerTopTitle;
	private ImageView innerReturnImageView;
	private PullToRefreshListView pullToRefreshView;
	private ListView actualListView;
	private TextView occupancyRateTextView;
	private TextView roomCapacityTextView;
	private Button publishPostButton;
	private ProgressBar progressBar;
	
	private MsgHandler handler;
	private Room room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_study_room_activity);
        room = (Room)getIntent().getSerializableExtra("room");
        findViewById();
        bindListener();
        handler = new MsgHandler();
    }
    
    private void findViewById(){
    	innerTopTitle = (TextView)findViewById(R.id.inner_top_title);
    	innerReturnImageView = (ImageView)findViewById(R.id.inner_top_return_image_view);
    	pullToRefreshView = (PullToRefreshListView)findViewById(R.id.pull_to_list_view);
    	occupancyRateTextView = (TextView)findViewById(R.id.occupancy_rate_text);
    	roomCapacityTextView = (TextView)findViewById(R.id.room_capacity);
    	publishPostButton = (Button)findViewById(R.id.publish_post);
    	progressBar = (ProgressBar)findViewById(R.id.progressBar1);
    	progressBar.setProgress(room.getRoomUseRate());
    	actualListView = pullToRefreshView.getRefreshableView();
    	actualListView.setAdapter(new DiscussListAdapter(this));
    	innerTopTitle.setText(room.getRoomName());
    	occupancyRateTextView.setText("当前占用率 ： " + room.getRoomUseRate() + "%");
    	if(room.getRoomCapacity() == room.CAPACITY_0_TO_PERENT_50){
    		roomCapacityTextView.setText("0-50人" );
    	}else if(room.getRoomCapacity() == room.CAPACITY_100_TO_PERENT_150){
    		roomCapacityTextView.setText("100-150人" );
    	}else if(room.getRoomCapacity() == room.CAPACITY_150_TO_PERENT_250){
    		roomCapacityTextView.setText("150-250人" );
    	}else if(room.getRoomCapacity() == room.CAPACITY_50_TO_PERENT_100){
    		roomCapacityTextView.setText("50-100人" );
    	}else if(room.getRoomCapacity() == room.MORE_THAN_250){
    		roomCapacityTextView.setText("容纳：250以上" );
    	}
    	
    }
    
    private void bindListener() {
    	innerReturnImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

    	
    	pullToRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				new Thread(new Runnable(){

					@Override
					public void run() {
						try {
							Thread.sleep(2 * Constant.SECOND);
							handler.sendEmptyMessage(0);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
					
				}).start();
			}
    		
		});
 	
    	publishPostButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(MainApp.getInstance().getLogManager().getLoginState()){
					Intent intent = new Intent(SelfStudyRoomActivity.this, PublishPostActivity.class);
					intent.putExtra("roomId", room.getRoomId());
					startActivity(intent);
				}else{
					Intent intent = new Intent(SelfStudyRoomActivity.this, LoginActivity.class);
					startActivity(intent);
				}
				
			}
		});
    }
    
    private final class MsgHandler extends Handler{
    	@Override
    	public void handleMessage(Message msg) {
    		switch(msg.what){
    		case 0: {
    			pullToRefreshView.onRefreshComplete();
    			break;
    		}
    		}
    	}
    }
    
}
