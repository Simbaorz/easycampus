package com.thelionking.campus.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.thelionking.campus.MainApp;
import com.thelionking.campus.R;
import com.thelionking.campus.model.Discuss;
import com.thelionking.campus.model.User;
import com.thelionking.campus.util.Constant;
import com.thelionking.campus.util.Parser;
import com.thelionking.campus.util.Parser.Result;

public class PublishPostActivity extends Activity{
	public static final int POST_SUCCESSED = 0;
	public static final int POST_FAILED = 1;
	
	private int roomId;
	private EditText titleTextView;
	private EditText contentTextView;
	private RadioGroup typeGroup;
	private TextView publish;
	private ImageView returnImageView;
	private MsgHandler handler;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.publish_post_activity);
		roomId = getIntent().getIntExtra("roomId", -1);
		handler = new MsgHandler();
		findViewById();
		bindListener();
	}
	
	private void findViewById(){
		titleTextView = (EditText)findViewById(R.id.title_text_view);
		contentTextView = (EditText)findViewById(R.id.content_text_view);
		typeGroup = (RadioGroup)findViewById(R.id.type_group);
		publish = (TextView)findViewById(R.id.publish);
		returnImageView = (ImageView)findViewById(R.id.return_image_image_view);
		
	}
	
	private void bindListener(){
		returnImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		publish.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(titleTextView.getText().toString().length() <= 5){
					Toast t = Toast.makeText(PublishPostActivity.this, "标题不能少于5个字！", Toast.LENGTH_SHORT);
					t.show();
					return;
				}
				if(contentTextView.getText().toString().length() <= 20){
					Toast t = Toast.makeText(PublishPostActivity.this, "内容不能少于20个字！", Toast.LENGTH_SHORT);
					t.show();
					return;
				}
				int type = -1;
				if(typeGroup.getCheckedRadioButtonId() == -1){
					Toast t = Toast.makeText(PublishPostActivity.this, "未选择分类！", Toast.LENGTH_SHORT);
					t.show();
					return;
				}else{
					if(typeGroup.getCheckedRadioButtonId() == R.id.pa){
						//求学霸
						type = Discuss.DISCUSS_TYPE_PA;
					}else if(typeGroup.getCheckedRadioButtonId() == R.id.hang){
						//闲聊
						type = Discuss.DISCUSS_TYPE_HANG;
					}else{
						//交易帖
						type = Discuss.DISCUSS_DEAL;
					}
				}
				Discuss discuss = new Discuss();
				//由服务端给出
				discuss.setDiscussTime(0);
				//由服务端给出
				discuss.setDiscussUrl("null");
				discuss.setDiscussTitle(titleTextView.getText().toString());
				discuss.setDiscussContent(contentTextView.getText().toString());
				discuss.setDiscussType(type);
				discuss.setDiscussUserId(MainApp.getInstance().getLogManager().getUser().getUserId());
				discuss.setDiscussUsername(MainApp.getInstance().getLogManager().getUser().getUserName());
				discuss.setRoomId(roomId);
				//开启线程传输数据
				new AsyncTask<Discuss, Void, Void>() {
					@Override
					protected Void doInBackground(Discuss... params) {
						Discuss discuss = params[0];
						String urlString = Constant.SERVER_URL + "publishDiscuss?discuss.roomId=" + discuss.getRoomId()
								+ "&discuss.discussType=" + discuss.getDiscussType() + "&discuss.discussTitle=" + discuss.getDiscussType()
								+ "&discuss.discussContent" + discuss.getDiscussContent() + "&discuss.discussUrl" + discuss.getDiscussUrl()
								+ "&discuss.discussUsername=" + discuss.getDiscussUsername() + "&discuss.discussUserId" + discuss.getDiscussUserId()
								+ "&discuss.discussTime=" + discuss.getDiscussTime();
						URL url = null;
						HttpURLConnection conn = null;
						InputStream is = null;
						try {
							url = new URL(urlString);
							conn = (HttpURLConnection) url.openConnection();
							conn.connect();
							conn.setReadTimeout(4000);
							is = conn.getInputStream();
							boolean isSuccessed = Parser.parseResult(is);
							if(isSuccessed){
								handler.sendEmptyMessage(POST_SUCCESSED);
							}else{
								handler.sendEmptyMessage(POST_FAILED);
							}
						} catch (MalformedURLException e) {
							e.printStackTrace();
							return null;
						} catch (IOException e) {
							e.printStackTrace();
							return null;
						}finally{
							try{
								if(is != null){
									is.close();
								}
							}catch(Exception ee){
								
							}
						}
						return null;
					}
					}.execute(discuss);
			}
		
		});
	}
	
	private final class MsgHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case POST_SUCCESSED :{
				Toast t = Toast.makeText(PublishPostActivity.this, "发表成功", Toast.LENGTH_SHORT);
				t.show();
				finish();
				break;
			}
			case POST_FAILED : {
				Toast t = Toast.makeText(PublishPostActivity.this, "发表失败", Toast.LENGTH_SHORT);
				t.show();
				break;
			}
			default : {
				break;
			}
			}
		}
	}
}
