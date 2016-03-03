package com.thelionking.campus.syllabus;

import java.util.ArrayList;
import java.util.HashMap;

import com.thelionking.campus.R;
import com.thelionking.campus.activity.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class SyllabusSet extends Activity {
	 private ListView list2;
	 private Button back;
	 private ArrayList<HashMap<String, Object>>   listItems;  
	 private SimpleAdapter listItemAdapter; 
	 private String[] contentItems1 = { "蹭课", "分享", "提醒","意见","退出",
	 };
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.set);
		back=(Button)findViewById(R.id.back1);
		list2 = (ListView) findViewById(R.id.list2);
		initListView(); 
		
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent a = new Intent(SyllabusSet.this,
						MainActivity.class);
				startActivity(a);
				SyllabusSet.this.finish();
				
			}

		});
	}
	 private void initListView() {
			// TODO Auto-generated method stub
			 listItems = new ArrayList<HashMap<String, Object>>();
		        for(int i=0;i<5;i++)    {   
		            HashMap<String, Object> map1 = new HashMap<String, Object>();   
		            map1.put("ItemTitle", contentItems1[i]);    //����
		            switch(i)
		            {
		            case 0:
		            	map1.put("ItemImage", R.drawable.mycourse); 
		            	break;
		            case 1:
		            	 map1.put("ItemImage", R.drawable.myreasource);
		            	 break;
		            case 2:
		            	 map1.put("ItemImage", R.drawable.hint);
		            	 break;
		            case 3:
		            	 map1.put("ItemImage", R.drawable.teacher);
		            	 break;
		            case 4:
		            	 map1.put("ItemImage", R.drawable.exit);
		            	 break;
		            }
		            listItems.add(map1);
		            }

		        listItemAdapter = new SimpleAdapter(this,listItems,     
		                R.layout.list_item,  
		                new String[] {"ItemTitle", "ItemImage"},        
		                new int[ ] {R.id.ItemTitle, R.id.ItemImage}     
		        );   
		        
				list2.setAdapter(listItemAdapter);
		list2.setOnItemClickListener(new OnItemClickListener() {  
			  
	         @Override  
	         public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
	                 long arg3) {  
	         
	        	  if (contentItems1[ arg2] == "蹭课"){
	        		  Intent a = new Intent(SyllabusSet.this,
		  						Search_course.class);
		  				startActivity(a);
		  				SyllabusSet.this.finish();
		      		  	
	        	  }
	        	  if (contentItems1[ arg2] == "分享"){
	        		  Intent a = new Intent(SyllabusSet.this,
		  						FeedbackActivity.class);
		  				startActivity(a);
		  				SyllabusSet.this.finish();
		      		  	
	        	  }
	        	  if (contentItems1[ arg2] == "提醒"){
	        		  Intent a = new Intent(SyllabusSet.this,
		  						Alarm.class);
		  				startActivity(a);
		  				SyllabusSet.this.finish();
		      		  	
	        	  }
	        	  if (contentItems1[ arg2] == " "){
	            	  
	        	  }
	        	  if (contentItems1[ arg2] == " "){
	        		
	        		  	
	        	  }
	        	  if (contentItems1[ arg2] == "意见"){
	        		  Intent a = new Intent(SyllabusSet.this,
	  						FeedbackActivity.class);
	  				startActivity(a);
	  				SyllabusSet.this.finish();
	      		  	
	        	  }
	        	  if (contentItems1[ arg2] == "退出"){
	          		
	        		  System.exit(0);	
	        	  }
	         }  
	     });  
		}
}
