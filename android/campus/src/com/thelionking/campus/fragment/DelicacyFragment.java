package com.thelionking.campus.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import com.thelionking.campus.R;

/**
 * @author the lion king
 * <b>美食Fragment</b>
 */
public class DelicacyFragment extends Fragment{
    public static final String TAG = "DelicacyFragment";

    private View topLayout;
    private ImageView topMenuButton;
    private TextView topTitle;
    private ImageView topRefreshButton;
    private View rootLayout;
    //private ListView listViewLeft;
	//private ListView listViewRight;
    private GridView mDisplay;  //美食显示列表
    //private ContentView contentview;
    private Button schoolfoodButton1;
    private Button schoolfoodButton2;
    private int res[] = new int[] {R.drawable.an06, R.drawable.an07,R.drawable.an08, R.drawable.an04,R.drawable.an05};
    private Integer[] mThumbIds = {
    		R.drawable.an06, R.drawable.an07,R.drawable.an07,R.drawable.an08, R.drawable.an04,R.drawable.an07,R.drawable.an08, R.drawable.an04,R.drawable.an05
    };
    
    private String[] mTextValues = {
                    "甜心葡萄","旋风冰激凌","麦旋风",
                    "水煮鱼","牛肉玉米","Spinner",
                    "","",""
    };
    private String[] mTime = {
            "很不错！","味道超赞哦","建议大家尝一下",
            "很美味，32赞","好好吃哦！","我们都喜欢",
            "","",""
    };

    String[] from = { "imageView", "content","time" };
    int[] to = { R.id.schoolfood_item_img, R.id.schoolfood_item_title,R.id.schoolfood_item_description };

    
    private TextView schoolfood_content;
    private TextView schoolfood_time;
    private ImageView schoolfood_image;
    
    
	
	int[] leftViewsHeights;
	int[] rightViewsHeights;

    private ActionContext actionContext;

    public DelicacyFragment() {

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
    	//rootLayout = inflater.inflate(R.layout.self_study_room_fragment, null);
    	rootLayout = inflater.inflate(R.layout.schoolfood, null);

        //setContentView(R.layout.schoolfood_list_view_item);
        topLayout = rootLayout.findViewById(R.id.top_layout);
        topTitle = (TextView)rootLayout.findViewById(R.id.top_title);
        topTitle.setText("美食");
        topMenuButton = (ImageView)rootLayout.findViewById(R.id.top_menu_button);
        
        topRefreshButton = (ImageView)rootLayout.findViewById(R.id.top_refresh_button);
        topRefreshButton.setVisibility(View.GONE);
        mDisplay=(GridView)rootLayout.findViewById(R.id.food_display);
        schoolfoodButton1=(Button)rootLayout.findViewById(R.id.schoolfood_button1);
        schoolfoodButton2=(Button)rootLayout.findViewById(R.id.schoolfood_button2);
        schoolfood_content=(TextView)rootLayout.findViewById(R.id.schoolfood_item_img);
		schoolfood_time=(TextView)rootLayout.findViewById(R.id.schoolfood_item_title);
		schoolfood_image=(ImageView)rootLayout.findViewById(R.id.schoolfood_item_description);
		
		//List<Map<String, Object>> data = new ArrayList<Map<String,Object>>(); 
//		for(int i=0;i<mThumbIds.length;i++){
//			
//			
//			schoolfood_image.setImageResource(mThumbIds[i]);
//			schoolfood_content.setText(mTextValues[i]);
//			schoolfood_time.setText(mTime[i]);
//			
//		}
		
        
        List<Map<String, Object>> data = new ArrayList<Map<String,Object>>(); 
        //List<Map<String, Object>> content = new ArrayList<Map<String,Object>>(); 
   	    //List<Map<String, Object>> time = new ArrayList<Map<String,Object>>(); 
        for(int i=0;i<res.length;i++){ 
        	Map<String,Object> map = new HashMap<String, Object>();
        	//Map<String,Object> map1 = new HashMap<String, Object>();
        	//Map<String,Object> map2 = new HashMap<String, Object>();
        	map.put("imageView",mThumbIds[i]); 
        	map.put("content", mTextValues[i]);
        	map.put("time",mTime[i]);
        	data.add(map);        	
   	
        }
        
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),data,R.layout.schoolfood_item1,from,to );
        
        
        mDisplay.setAdapter(simpleAdapter);    //思考题 在Gridview中 SimpleAdpater 图片添加事件  
        //mDisplay.setAdapter(new ImageAdapter(getActivity()));
        mDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	
        
        @Override 
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	//可以显示图片大图 
        	
        	Toast.makeText(getActivity().getApplicationContext(), "xxxx"+res[position], Toast.LENGTH_LONG).show();  
        	} 
        });   
        

        
        
		//setListener();
		//init();
        
        //listViewLeft = (ListView) rootLayout.findViewById(R.id.list_view_left);  
		//listViewRight = (ListView) rootLayout.findViewById(R.id.list_view_right);
		
		        
        bindListener();
        return rootLayout;
    }
    

    private void init() {
		// TODO Auto-generated method stub
		
	}

	private void setListener() {
		// TODO Auto-generated method stub
		
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

}

