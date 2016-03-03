package com.thelionking.campus;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.thelionking.campus.dao.UserDao;
import com.thelionking.campus.model.User;
import com.thelionking.campus.util.Constant;
import com.thelionking.campus.util.Parser;

/**
 * 服务端不放置session，因此注销登录时只是简单的修改对象状态
 * 当尝试登录或注销登录时候锁定本对象
 * @author the lion king
 *
 */
public class LogManager {
	
	public static final String TAG = "LogManager";
	private List<LogListener> listeners;
	private Context context;
	//用户信息
	private User user;
	//是否已经登录
	private boolean isLogined;
	//正在尝试登录
	private boolean trying;
	
	private Bitmap portrait;

	public LogManager() {
		
	}
	
	public void init(final Context context){
		this.context = context;
		this.listeners = new ArrayList<LogListener>();
		reset();
	}
	
	public static LogManager getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	private static class SingletonHelper{
		static LogManager INSTANCE = new LogManager();
	}
	
	/**
	 * @param userName
	 * @param userPassword
	 */
	public void login(String userName, String userPassword){
		if(trying){
			Log.d(TAG, "正在读取服务器数据...");
			return;
		}
		if(isLogined || user != null){
			Log.d(TAG, "已经登录！");
			return;
		}
		if(userName.equals("") || userPassword.equals("") || userName == null || userPassword == null){
			Log.d(TAG, "账号或密码为空！");
			return;
		}
		new AsyncTask<String, Void, Void>(){
			@Override
			protected Void doInBackground(String... params) {
				synchronized (LogManager.this) {
					trying = true;
				}
				URL url = null;
				HttpURLConnection conn = null;
				InputStream is = null;
				try {
					String urlString = Constant.SERVER_URL + "login?user.userName=" + params[0] + "&user.userPassword=" + params[1];
					Log.d(TAG, "url : " + urlString);
					url = new URL(urlString);
					conn = (HttpURLConnection) url.openConnection();
					conn.connect();
					conn.setReadTimeout(4000);
					is = conn.getInputStream();
					user = Parser.parserUser(is);
					if(user != null){
						Log.d(TAG, user.toString());
					}
			        
					//controller可能在多线程下发生读写
					synchronized (LogManager.this) {
						if(user != null){
							isLogined = true;
							InputStream in = null;
							try{
								in = context.getAssets().open("portraits/" + user.getUserImg().trim() + ".png");
								portrait = BitmapFactory.decodeStream(in);
							}catch(Exception ex){
								Log.d(TAG, "ERROR");
								ex.printStackTrace();
							}finally{
								try{
									if(in != null){
										in.close();
									}
								}catch(Exception e){
									
								}
							}
							
//							UserDao ud = new UserDao(context);
//							//本地表中不存在则插入,存在则更新
//							if(!ud.isExist(user.getUserId())){
//								ud.insert(user);
//							}else{
//								ud.update(user);
//							}
							for(LogListener listener : listeners){
								listener.onLoginSuccessed();
							}
						}else{
							isLogined = false;
							user = null;
							if(portrait!=null && !portrait.isRecycled()){
								portrait.recycle();
								portrait = null;
							}
							for(LogListener listener : listeners){
								listener.onLoginFailed();
							}
							listeners.clear();
						}
						trying = false;
					}
					return null;
				} catch (MalformedURLException e) {
					reset();
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					reset();
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
			}
			
		}.execute(userName, userPassword);
	}
	
	public static interface LogListener{
		//登录成功
		void onLoginSuccessed();
		//登录失败
		void onLoginFailed();
		//注销
		void onLogoutFinished();
	}
	
	public synchronized void logout() {	
//		for(LogListener listener : listeners){
//			listener.onLoginFailed();
//		}
		reset();
	}
	
	private final void reset() {
		if(listeners != null) {
			listeners.clear();
		}
		this.user = null;
		this.isLogined = false;
		this.trying = false;
		if(portrait != null && !portrait.isRecycled()){
			portrait.recycle();
			portrait = null;
		}
	}
	
	public boolean getLoginState() {
		return isLogined;
	}
	
	public boolean getTryState() {
		return trying;
	}
	
	public final Bitmap getPortrait() {
		return portrait;
	}
	
	public final User getUser() {
		return user;
	}
	
	public final void addListener(final LogListener listener){
		this.listeners.add(listener);
	}
	
	public synchronized void setUser(User user){
		if(user == null){
			return;
		}
		reset();
		this.user = user;
		this.trying = false;
		this.isLogined = true;
		if(portrait != null){
			if(!this.portrait.isRecycled()){
				portrait.recycle();
				portrait = null;
			}
		}
		InputStream in = null;
		try{
			in = context.getAssets().open("portraits/" + user.getUserImg().trim() + ".png");
			this.portrait = BitmapFactory.decodeStream(in);
		}catch(Exception ex){
			Log.d(TAG, "ERROR");
			ex.printStackTrace();
		}finally{
			try{
				if(in != null){
					in.close();
				}
			}catch(Exception e){
				
			}
		}
	}
}
