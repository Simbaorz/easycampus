package com.thelionking.campus.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thelionking.campus.R;
import com.thelionking.campus.activity.SchoolMapActivity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

public class SchoolServiceFragment extends Fragment{

	public static final String TAG = "SchoolServiceFragment";
	
	private View topLayout;
    private ImageView topMenuButton;
    private TextView topTitle;
    private ImageView topRefreshButton;
    private View rootLayout;
    private Integer[] mImages = {R.drawable.s01, R.drawable.s02,R.drawable.s03,R.drawable.s04, R.drawable.s05,R.drawable.s06,R.drawable.s07, R.drawable.s08,R.drawable.s09,R.drawable.s10, R.drawable.s11,R.drawable.s12,R.drawable.s13,R.drawable.s14 };  //存放照片资源名字
    private String[] text={"失物招领","校园地图","校园黄页","图书馆","交通助手","二手市场","订外卖","邀约吧","美丽晒","表白墙","约慧帮","周边推荐","邮箱管理","资料区"};
    //private Integer[] mImages = {R.drawable.an01, R.drawable.an02};
    private ImageView schoolservice_image;
    private Button schoolservice_button1;
    private Button schoolservice_button2;
    private TextView schoolservice_title;
    String[] from = { "simageView","name"};
    int[] to = {R.id.schoolservice_item_image,R.id.schoolservice_item_title};
//    SchoolServiceMapFragment map=new SchoolServiceMapFragment();
    
    private GridView serviceDisplay;  //美食显示列表
    
    private ActionContext actionContext;

    public SchoolServiceFragment() {

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
        rootLayout = inflater.inflate(R.layout.schoolservice, null);
        topLayout = rootLayout.findViewById(R.id.top_layout);
        topTitle = (TextView)rootLayout.findViewById(R.id.top_title);
        topTitle.setText("校园服务");
        topMenuButton = (ImageView)rootLayout.findViewById(R.id.top_menu_button);
        topRefreshButton = (ImageView)rootLayout.findViewById(R.id.top_refresh_button);
        topRefreshButton.setVisibility(View.GONE);
        serviceDisplay=(GridView)rootLayout.findViewById(R.id.schoolservice_display); 
        schoolservice_button1=(Button)rootLayout.findViewById(R.id.schoolservice_button1);
        schoolservice_button2=(Button)rootLayout.findViewById(R.id.schoolservice_button2);
        schoolservice_image=(ImageView)rootLayout.findViewById(R.id.schoolservice_item_image);
        schoolservice_title=(TextView)rootLayout.findViewById(R.id.schoolservice_item_title);
        
        List<Map<String, Object>> data1 = new ArrayList<Map<String,Object>>(); 
        for(int i=0;i<mImages.length;i++){ 
        	Map<String,Object> map = new HashMap<String, Object>();
        	map.put("simageView",mImages[i]); 
        	map.put("name", text[i]);
        	data1.add(map);        	
        		
        }
        
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),data1,R.layout.schoolservice_item1,from,to);             
        
        serviceDisplay.setAdapter(simpleAdapter);    //思考题 在Gridview中 SimpleAdpater 图片添加事件 
        
//        simpleAdapter.setViewBinder(new ViewBinder() {
//			@Override
//			public boolean setViewValue(View view, Object data,
//					String textRepresentation) {
//				if (view instanceof ImageView && data instanceof Drawable) {
//					ImageView iv = (ImageView) view;
//					iv.setImageDrawable((Drawable) data);
//					return true;
//				} else
//					return false;
//			}
//		});	
        
        
        	
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
        
        //GridView点击响应操作
        serviceDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            
            @Override 
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            	//可以显示图片大图 
            	if(position==3){ //如果点击图书馆图标
            		
            		Uri uri = Uri.parse("http://lib.imu.edu.cn/");    
            		Intent it = new Intent(Intent.ACTION_VIEW, uri);    
            		startActivity(it);  
            	}
            	if(position==1){
            		//getActivity().addContentView(view, params)
            		Intent map=new Intent(getActivity(),SchoolMapActivity.class);
            		startActivity(map);
            		Toast.makeText(getActivity().getApplicationContext(), "xxxx"+mImages[position], Toast.LENGTH_LONG).show(); 
            	}
            	if(position==6){ //如果点击订外卖图标
            		
            		Uri uri = Uri.parse("http://waimai。meituan.com/");    
            		Intent it = new Intent(Intent.ACTION_VIEW, uri);    
            		startActivity(it);  
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

    
    
}
