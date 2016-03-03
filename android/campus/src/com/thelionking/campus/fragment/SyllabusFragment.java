package com.thelionking.campus.fragment;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thelionking.campus.R;
import com.thelionking.campus.syllabus.DataBase;
import com.thelionking.campus.syllabus.SelectActivity;
import com.thelionking.campus.syllabus.SlidingLayout;
import com.thelionking.campus.syllabus.SyllabusSet;

/**
 * @author the lion king
 * <b>课程表Fragment</b>
 */
public class SyllabusFragment extends Fragment{
    public static final String TAG = "SyllabusFragment";

    private View rootLayout;
    private ImageView topMenuButton;
    private TextView topTitle;
    private ImageView topRefreshButton;
    private View topLayout;

    private ActionContext actionContext;
    
    public static DataBase db;
	// public Cursor cursor;
	 public Cursor[] cursor=new Cursor[7];
	 private SharedPreferences pre;
	 private SlidingLayout slidingLayout;
	 private Button menuButton;
	 private ImageView seletButton;
	 private LinearLayout day[]=new LinearLayout[7];
	
	 private int colors[] = {
				Color.rgb(0xf0, 0x96, 0x09), Color.rgb(0x8c, 0xbf, 0x26),
				Color.rgb(0x00, 0xab, 0xa9), Color.rgb(0x99, 0x6c, 0x33),
				Color.rgb(0x3b, 0x92, 0xbc), Color.rgb(0xd5, 0x4d, 0x34),
				Color.rgb(0xcc, 0xcc, 0xcc), Color.rgb(0xaa, 0xbb, 0xcc), 
				Color.rgb(0x99, 0x66, 0x99), Color.rgb(0xcc, 0x99, 0x33)};

    public SyllabusFragment() {

    }

    public void setActionContext(final ActionContext actionContext){
        this.actionContext = actionContext;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootLayout = inflater.inflate(R.layout.syllabus_fragment, null);
        topLayout = rootLayout.findViewById(R.id.top_layout);
        topTitle = (TextView)rootLayout.findViewById(R.id.top_title);
        topTitle.setText("课程表");
        topMenuButton = (ImageView)rootLayout.findViewById(R.id.top_menu_button);
        topRefreshButton = (ImageView)rootLayout.findViewById(R.id.top_refresh_button);
  
//        menuButton = (Button) rootLayout.findViewById(R.id.);
		seletButton = (ImageView) rootLayout.findViewById(R.id.top_refresh_button);
		Random rand = new Random();
		 //======================
		 db=new DataBase(getActivity());
			pre=getActivity().getSharedPreferences("firstStart",Context.MODE_PRIVATE);
			/*
			 * 判断程序是否第一次运行，如果是创建数据库表
			 */
			if(pre.getBoolean("firstStart", true)){
				SingleInstance.createTable();
				(pre.edit()).putBoolean("firstStart",false).commit();
//				finish();			
			}

		seletButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent a = new Intent(getActivity(),
						SyllabusSet.class);
				startActivity(a);
			}
		});
		day[0] = (LinearLayout) rootLayout.findViewById(R.id.ll1);
		day[1] = (LinearLayout) rootLayout.findViewById(R.id.ll2);
		day[2] = (LinearLayout) rootLayout.findViewById(R.id.ll3);
		day[3] = (LinearLayout) rootLayout.findViewById(R.id.ll4);
		day[4] = (LinearLayout) rootLayout.findViewById(R.id.ll5);
		day[5] = (LinearLayout) rootLayout.findViewById(R.id.ll6);
		day[6] = (LinearLayout) rootLayout.findViewById(R.id.ll7);
		String number;
		Cursor cursor;
		int color=-1,temp;
		for (int i=0;i<7;i++)
			for(int j=0;j<5;j++)
			{
				cursor=db.select(i);
				cursor.moveToPosition(j);
				number=""+i+j;
			  //劲量让颜色不一样
				temp=rand.nextInt(9);
				while(color!=temp)
				{  color=temp;	}
				if(day[i] == null) {
					Log.d(TAG, "LJFAJAJDF");
				}
				setClass(day[i], cursor.getString(1), cursor.getString(2), cursor.getString(3),number,2, color);
				cursor.close();
			}
        
        bindListener(); 	
        return rootLayout;
    }

    private final void bindListener(){
        topMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(topMenuButton == v){
                    actionContext.openMenu(0);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    
    void setClass(LinearLayout ll, String title, String place, String last,String temp,
			int classes, int color) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.item, null); 
		view.setMinimumHeight(dip2px(getActivity(), classes * 60));
		view.setBackgroundColor(colors[color]);
		view.getBackground().setAlpha(150);
		((TextView) view.findViewById(R.id.title)).setText(title);
		((TextView) view.findViewById(R.id.place)).setText(place);
		((TextView) view.findViewById(R.id.last)).setText(last);
		((TextView) view.findViewById(R.id.temp)).setText(temp);
		view.setOnClickListener(new OnClickClassListener());
		view.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				String title ;	
				title = (String) ((TextView) v.findViewById(R.id.temp)).getText();
				Intent a = new Intent(getActivity(),SelectActivity.class);
				Bundle bundle=new Bundle();
				bundle.putString("number", title);
				a.putExtras(bundle);
				startActivity(a);
				return true;
			}
		});
		TextView blank1 = new TextView(getActivity());
		TextView blank2 = new TextView(getActivity());
		blank1.setHeight(dip2px(getActivity(), classes));
		blank2.setHeight(dip2px(getActivity(), classes));
		if(blank1 == null){
			Log.d(TAG, "aaaaaaaaaaaaaaaaa");
		}
		ll.addView(blank1);
		ll.addView(view);
		ll.addView(blank2);
	}

	void setNoClass(LinearLayout ll, int classes, int color) {
		TextView blank = new TextView(getActivity());
		if (color == 0)
			blank.setMinHeight(dip2px(getActivity(), classes * 50));
		blank.setBackgroundColor(colors[color]);
		blank.getBackground().setAlpha(0);
		ll.addView(blank);
	}


	class OnClickClassListener implements OnClickListener {
		public void onClick(View v) {
			
			String cString ,pString,tString;	
			cString = (String) ((TextView) v.findViewById(R.id.title)).getText();
			pString = (String) ((TextView) v.findViewById(R.id.place)).getText();
			tString = (String) ((TextView) v.findViewById(R.id.last)).getText();
			//======================
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View view = inflater.inflate(R.layout.toast, null);
//			ImageView iv = (ImageView) view.findViewById(R.id.tvImageToast);
//			iv.setImageResource(R.drawable.book);
			
			TextView title = (TextView) view.findViewById(R.id.tvTitleToast);
			title.setText("课程信息");
			if(cString.equals("")&&pString.equals("")&&tString.equals("")){
				TextView text1 = (TextView) view.findViewById(R.id.title);
				text1.setText("无课程信息，请编辑！");
				TextView text2 = (TextView) view.findViewById(R.id.place);
				text2.setText("    长按编辑信息！");
				TextView text3 = (TextView) view.findViewById(R.id.last);
				text3.setText("");
			}
			else {
				TextView text1 = (TextView) view.findViewById(R.id.title);
				text1.setText("课程："+cString);
				TextView text2 = (TextView) view.findViewById(R.id.place);
				text2.setText("地点："+pString);
				TextView text3 = (TextView) view.findViewById(R.id.last);
				text3.setText("教师："+tString);
			}
			Toast toast = new Toast(getActivity().getApplicationContext());
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(view);
			toast.show();
			
//			Intent a = new Intent(SyllabusActivity.this,SelectActivity.class);
//			Bundle bundle=new Bundle();
//			bundle.putString("number", title);
//			a.putExtras(bundle);
//			startActivity(a);
		}
	}
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	

	static class SingleInstance{
		static SingleInstance si;
		private SingleInstance(){
			for(int i=0;i<7;i++){
				db.createTable(i);
			}
		}
		static SingleInstance createTable(){
			if(si==null)
				return si=new SingleInstance();
			return null;
		}
	}

}
