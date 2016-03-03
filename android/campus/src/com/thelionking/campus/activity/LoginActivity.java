package com.thelionking.campus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thelionking.campus.LogManager.LogListener;
import com.thelionking.campus.MainApp;
import com.thelionking.campus.R;

public class LoginActivity extends Activity implements LogListener{
	public static final String TAG = "LoginActivity";
	
	public static final int ON_LOGIN_SUCCESSED = 0x000;
	public static final int ON_LOGIN_FAILED = 0x001;
	public static final int ON_LOGOUT_FINISHED = 0x002;
	public MsgHandler handler;
	private EditText username;
	private EditText password;
	private Button loginButton;
	private View registerLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MainApp.getInstance().getLogManager().addListener(this);
		setContentView(R.layout.login_activity);
		findViewById();
		bindListener();
	}
	
	private final void findViewById() {
		handler = new MsgHandler();
		username = (EditText)findViewById(R.id.username_edit_text);
		password = (EditText)findViewById(R.id.password_edit_text);
		loginButton = (Button)findViewById(R.id.login_button);
		registerLayout = findViewById(R.id.register_layout);
	}
	
	private final void bindListener() {
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(MainApp.getInstance().getLogManager().getTryState()){
					Toast.makeText(LoginActivity.this, "正在登录", Toast.LENGTH_LONG).show();
					return;
				}
				if(username.getText().toString().equals("") || password.getText().toString().equals("")){
					Toast.makeText(LoginActivity.this, "用户名或密码为空！", Toast.LENGTH_LONG).show();
					return;
				}
				MainApp.getInstance().getLogManager().login(username.getText().toString(), password.getText().toString());
			}
		});
		registerLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(TAG, "register");
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public void onLoginSuccessed() {
		handler.sendEmptyMessage(ON_LOGIN_SUCCESSED);
	}

	@Override
	public void onLoginFailed() {
		handler.sendEmptyMessage(ON_LOGIN_FAILED);
	}

	@Override
	public void onLogoutFinished() {
		handler.sendEmptyMessage(ON_LOGOUT_FINISHED);
	}
	
	private final class MsgHandler extends Handler{
		
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case ON_LOGIN_SUCCESSED : {
				Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
				finish();
				break;
			}
			case ON_LOGIN_FAILED : {
				Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
				break;
			}
			case ON_LOGOUT_FINISHED : {
				break;
			}
			default : {
				
			}
			}
		}
	}
}
