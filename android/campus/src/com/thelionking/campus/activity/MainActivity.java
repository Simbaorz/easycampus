package com.thelionking.campus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thelionking.campus.MainApp;
import com.thelionking.campus.R;
import com.thelionking.campus.Type;
import com.thelionking.campus.adapter.MenuListAdapter;
import com.thelionking.campus.fragment.ActionContext;
import com.thelionking.campus.fragment.CampusMessageFragment;
import com.thelionking.campus.fragment.DelicacyFragment;
import com.thelionking.campus.fragment.SchoolServiceFragment;
import com.thelionking.campus.fragment.SelfStudyRoomFragment;
import com.thelionking.campus.fragment.SettingFragment;
import com.thelionking.campus.fragment.SyllabusFragment;
import com.thelionking.campus.fragment.UserFragment;
import com.thelionking.campus.util.Utility;

/**
 * @author the lion king
 * 主界面
 */
public class MainActivity extends FragmentActivity implements ActionContext{
    public static final String TAG = "MainActivity";
//  public static final int ON_LOGIN_SUCCESSED = 0x000;
//	public static final int ON_LOGIN_FAILED = 0x001;
//	public static final int ON_LOGOUT_FINISHED = 0x002;
	
//  private MsgHandler handler;
    //view
    private DrawerLayout drawerLayout;
    private FrameLayout contentLayout;
    private RelativeLayout menuLayout;
    private View userLayout;
    private ImageView settingImageView;
    private ListView menuListView;
    
    //登录相关view
    private View loginedLayout;
    private View unloginedLayout;
    private ImageView portrait;
    private TextView username;
//    private TextView userGrade;
    private Button toLoginButton;
    private ImageView unloginedText;
//    private Button logout;
    
    private MenuListAdapter menuListAdapter;
    //fragment
    private CampusMessageFragment campusMessageFragment;
    private SelfStudyRoomFragment selfStudyRoomFragment;
    private DelicacyFragment delicacyFragment;
//    private QuestionShareFragment questionShareFragment;
    private SyllabusFragment syllabusFragment;
//    private SurrordingFragment surrordingFragment;
    private UserFragment userFragment;
    private SettingFragment settingFragment;
    private SchoolServiceFragment schoolserviceFragment;
    //当前的Fragment
    private Type current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initFragment();
        bindListener();
        setAdapter();
//        handler = new MsgHandler();
    }

    private final void findViewById() {
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        contentLayout = (FrameLayout)findViewById(R.id.content_layout);
        menuLayout = (RelativeLayout)findViewById(R.id.menu_layout);
        menuListView = (ListView)findViewById(R.id.menu_listView);
        userLayout = findViewById(R.id.user_layout);
        settingImageView = (ImageView)findViewById(R.id.setting_image_view);
        loginedLayout = findViewById(R.id.logined_layout);
        unloginedLayout = findViewById(R.id.unlogined_layout);
        portrait = (ImageView)findViewById(R.id.portrait);
        username = (TextView)findViewById(R.id.username);
//        userGrade = (TextView)findViewById(R.id.user_grade);
//        userGrade.setVisibility(View.GONE);
        toLoginButton = (Button)findViewById(R.id.to_login_button);
        unloginedText = (ImageView)findViewById(R.id.unlogined_imageView);
//        logout = (Button)findViewById(R.id.logout);
        setLoginState();
    }

    private final void bindListener() {
        //列表选择时的监听器
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ++position;
                Log.d(TAG, "position : " + position);
                if(Type.values()[position] == current){
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    return;
                }
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                if(menuListView == parent){
                	Log.d(TAG, Type.values()[position] + "");
                    switch (Type.values()[position]){
	                    case  SELF_STUDY_ROOM:{
	                    	current = Type.SELF_STUDY_ROOM;
	                    	menuListAdapter.setSelectedNumber(1);
	                    	menuListAdapter.notifyDataSetChanged();
                            ft.replace(R.id.content_layout, selfStudyRoomFragment);
                            break;
	                    }
                        case  DELICACY:{
                            current = Type.DELICACY;
                            menuListAdapter.setSelectedNumber(3);
	                    	menuListAdapter.notifyDataSetChanged();
                            ft.replace(R.id.content_layout, delicacyFragment);
                            break;
                        }
//                        case QUESTION_SHARE:{
//                            current = Type.QUESTION_SHARE;
//                            menuListAdapter.setSelectedNumber(2);
//	                    	menuListAdapter.notifyDataSetChanged();
//                            ft.replace(R.id.content_layout, questionShareFragment);
//                            break;
//                        }
                        case SYLLABUS:{
                            current = Type.SYLLABUS;
                            menuListAdapter.setSelectedNumber(4);
	                    	menuListAdapter.notifyDataSetChanged();
                            ft.replace(R.id.content_layout, syllabusFragment);
                            break;
                        }
//                        case SURRORDING:{
//                            current = Type.SURRORDING;
//                            menuListAdapter.setSelectedNumber(5);
//	                    	menuListAdapter.notifyDataSetChanged();
//                            ft.replace(R.id.content_layout, surrordingFragment);
//                            break;
//                        }
                        case SERVICE:{
                        	current = Type.SERVICE;   //暂时先把周边替换为校园服务
                            //ft.replace(R.id.content_layout, surrordingFragment);
                            ft.replace(R.id.content_layout, schoolserviceFragment);
                            break;
                        }
                        case CAMPUS_MESSAGE:
                        default:{//默认是校园资讯
                        	current = Type.CAMPUS_MESSAGE;
                        	menuListAdapter.setSelectedNumber(0);
	                    	menuListAdapter.notifyDataSetChanged();
	                        ft.replace(R.id.content_layout, campusMessageFragment);
	                        break;
                        }
                    }
                    menuListAdapter.notifyDataSetChanged();
                    ft.commit();
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
            }
        });
        userLayout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(current == Type.USER){
					drawerLayout.closeDrawer(Gravity.LEFT);
					return;
				}
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				current = Type.USER;
                ft.replace(R.id.content_layout, userFragment);
                ft.commit();
                drawerLayout.closeDrawer(Gravity.LEFT);
			}
		});
        
        settingImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(current == Type.SETTING){
					drawerLayout.closeDrawer(Gravity.LEFT);
					return;
				}
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				current = Type.SETTING;
                ft.replace(R.id.content_layout, settingFragment);
                ft.commit();
                drawerLayout.closeDrawer(Gravity.LEFT);
			}
		});
        
        toLoginButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				MainApp.getInstance().getLogManager().addListener(MainActivity.this);
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		});
        
//        logout.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				MainApp.getInstance().getLogManager().logout();
//				setLoginState();
//			}
//		});
    }

    private final void setAdapter() {
    	menuListAdapter = new MenuListAdapter(this);
    	menuListAdapter.setSelectedNumber(Type.CAMPUS_MESSAGE.getNumber());
        menuListView.setAdapter(menuListAdapter);
    }

    private final void initFragment() {
    	campusMessageFragment = new CampusMessageFragment();
    	campusMessageFragment.setActionContext(this);
        selfStudyRoomFragment = new SelfStudyRoomFragment();
        selfStudyRoomFragment.setActionContext(this);
        delicacyFragment = new DelicacyFragment();
        delicacyFragment.setActionContext(this);
//        questionShareFragment = new QuestionShareFragment();
//        questionShareFragment.setActionContext(this);
        syllabusFragment = new SyllabusFragment();
        syllabusFragment.setActionContext(this);
//        surrordingFragment = new SurrordingFragment();
//        surrordingFragment.setActionContext(this);
        userFragment = new UserFragment();
        userFragment.setActionContext(this);
        settingFragment = new SettingFragment();
        settingFragment.setActionContext(this);
        schoolserviceFragment = new SchoolServiceFragment();
        schoolserviceFragment.setActionContext(this);
        //默认是自习室
        current = Type.CAMPUS_MESSAGE;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.content_layout, campusMessageFragment);
        ft.commit();
    }

    @Override
    public void openMenu(int id) {
        if(!drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }

//  @Override
//	public void onLoginSuccessed() {
//		handler.sendEmptyMessage(ON_LOGIN_SUCCESSED);
//	}
//
//	@Override
//	public void onLoginFailed() {
//		handler.sendEmptyMessage(ON_LOGIN_FAILED);
//	}
//
//	@Override
//	public void onLogoutFinished() {
//		handler.sendEmptyMessage(ON_LOGOUT_FINISHED);
//	}
	
//	private final class MsgHandler extends Handler{
//		
//		@Override
//		public void handleMessage(Message msg) {
//			switch(msg.what){
//			case ON_LOGIN_SUCCESSED : {
//				setLoginState();
//				break;
//			}
//			case ON_LOGIN_FAILED : {
//				break;
//			}
//			case ON_LOGOUT_FINISHED : {
//				setLoginState();
//				break;
//			}
//			default : {
//				
//			}
//			}
//		}
//	}
	
	private final void setLoginState(){
		if(!MainApp.getInstance().getLogManager().getLoginState()){
        	loginedLayout.setVisibility(View.GONE);
        	unloginedLayout.setVisibility(View.VISIBLE);
        }else{
        	loginedLayout.setVisibility(View.VISIBLE);
        	unloginedLayout.setVisibility(View.GONE);
        	portrait.setImageBitmap(MainApp.getInstance().getLogManager().getPortrait());
        	username.setText(Utility.cutString(MainApp.getInstance().getLogManager().getUser().getUserNickname(), 5));
        	
//        	userGrade.setText("[" + MainApp.getInstance().getLogManager().getUser().getUserGrade() + "级]");
        }
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setLoginState();
	}
}
