package com.thelionking.campus.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thelionking.campus.LogManager.LogListener;
import com.thelionking.campus.LogManager;
import com.thelionking.campus.MainApp;
import com.thelionking.campus.R;
import com.thelionking.campus.model.User;
import com.thelionking.campus.util.Constant;
import com.thelionking.campus.util.Parser;
import com.thelionking.campus.util.Parser.Result;

public class RegisterActivity extends Activity{
	public static final String TAG = "RegisterActivity";
	
	public static final int ON_REGISTER_SUCCESSED = 0x000;
	public static final int ON_REGISTER_FAILED = 0x001;
	public static final int ON_REGISTER_USERNAME_REPEAT = 0x004;
	
	public static final int ON_LOGIN_SUCCESSED = 0x002;
	public static final int ON_LOGIN_FAILED = 0x003;

	public MsgHandler handler;
	private EditText username;
	private EditText password;
	private EditText repeatPassword;
	private Button registerButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		findViewById();
		bindListener();
	}
	
	private final void findViewById() {
		handler = new MsgHandler();
		username = (EditText)findViewById(R.id.username_edit_text);
		password = (EditText)findViewById(R.id.password_edit_text);
		repeatPassword = (EditText)findViewById(R.id.repeat_password_edit_text);
		registerButton = (Button)findViewById(R.id.register_button);
	}
	
	private final void bindListener() {
		registerButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(username.getText().toString().length() < 7 || username.getText().toString().length() > 20){
					Toast.makeText(RegisterActivity.this, "用户名长度必须在7个字符到20个字符之间", Toast.LENGTH_SHORT).show();
					return;
				}
				if(password.getText().toString().length() < 7 || password.getText().toString().length() > 20){
					Toast.makeText(RegisterActivity.this, "密码长度必须在7个字符到20个字符之间", Toast.LENGTH_SHORT).show();
					return;
				}
				if(!password.getText().toString().equals(repeatPassword.getText().toString())){
					Toast.makeText(RegisterActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
					return;
				}
				User user = new User();
				user.setUserName(username.getText().toString().trim());
				user.setUserPassword(password.getText().toString().trim());
				new AsyncTask<User, Void, Void>() {
					@Override
					protected Void doInBackground(User... params) {
						User user = params[0];
						String urlString = Constant.SERVER_URL + "register?user.userName=" + user.getUserName()
								+ "&user.userPassword=" + user.getUserPassword();
						URL url = null;
						HttpURLConnection conn = null;
						InputStream is = null;
						try {
							url = new URL(urlString);
							conn = (HttpURLConnection) url.openConnection();
							conn.connect();
							conn.setReadTimeout(4000);
							is = conn.getInputStream();
							Result<User> result = Parser.parseRegister(is);
							if(result.status == Result.ERROR_CODE_REPEAT_USERNAME){
								handler.sendEmptyMessage(ON_REGISTER_USERNAME_REPEAT);
							}else if(result.status == Result.ERROR_CODE){
								handler.sendEmptyMessage(ON_REGISTER_FAILED);
							}else{
								MainApp.getInstance().getLogManager().setUser(result.content.get(0));
								handler.sendEmptyMessage(ON_REGISTER_SUCCESSED);
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
				}.execute(user);
			}
		});
	}

	private final class MsgHandler extends Handler{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case ON_REGISTER_SUCCESSED : {
				Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
				finish();
				break;
			}
			case ON_REGISTER_FAILED : {
				Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
				break;
			}
			case ON_REGISTER_USERNAME_REPEAT : {
				Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_LONG).show();
				break;
			}
			default : {
				
			}
			}
		}
	}

}
