package com.thelionking.campus.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.thelionking.campus.R;
import com.thelionking.campus.adapter.ProvinceListAdapter;
import com.thelionking.campus.adapter.SchoolListAdapter;
import com.thelionking.campus.model.Province;
import com.thelionking.campus.model.School;
import com.thelionking.campus.util.Parser;


public class SchoolSelectorActivity extends Activity {
    public static final String TAG = "SchoolSelectorActivity";
    
    private AutoCompleteTextView autoProvinceSelector;
    private AutoCompleteTextView autoSchoolSelector;
    
    private Button submit;
    
    private ProvinceListAdapter adapter;
    
    private Map<String, List<School>> schoolsMap;
    
    private List<Province> provinces;
    
    private SchoolListAdapter schoolAdapter;
    
    private static final String[] PROVINCES_NAMES = {
    	"北京市",
    	"内蒙古自治区",
    	"上海市",
    	"重庆市",
    	"山东省",
    	"黑龙江省"
    };
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.school_selector_activity);
        this.getProvinces();
        this.schoolsMap = new HashMap<String, List<School>>();
        for(int i=0; i<PROVINCES_NAMES.length; ++i){
        	getSchools(PROVINCES_NAMES[i]);
        }
        Log.d(TAG, schoolsMap.toString());
        this.findViewById();
        this.bindListener();  
    }
    
    private final void  findViewById() {
    	submit = (Button)findViewById(R.id.submit);
    	autoProvinceSelector = (AutoCompleteTextView)findViewById(R.id.auto_province_selector);
    	autoProvinceSelector.setThreshold(0);
    	autoSchoolSelector = (AutoCompleteTextView)findViewById(R.id.auto_school_selector);
    	autoSchoolSelector.setThreshold(0);
    	adapter = new ProvinceListAdapter(this, provinces);
    	autoProvinceSelector.setAdapter(adapter);
    	schoolAdapter = new SchoolListAdapter(this, schoolsMap.get(PROVINCES_NAMES[0]));
    }

    private final void bindListener() {
    	
    	submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SchoolSelectorActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
    	
    	autoSchoolSelector.setOnFocusChangeListener(new View.OnFocusChangeListener() {
    		
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				Log.d(TAG, "onFocusChange");
				if(hasFocus){
					Log.d(TAG, "onFocusChange + hasFocus");
					String text = autoProvinceSelector.getText().toString().trim();
					if(isLeagl(text)){
						Toast t = Toast.makeText(SchoolSelectorActivity.this, "请选择一个省份", Toast.LENGTH_SHORT);
						t.show();
						return;
					}
					if(!schoolsMap.containsKey(text)){
						getSchools(text);
					}
					SchoolListAdapter schoolAdapter = new SchoolListAdapter(SchoolSelectorActivity.this, schoolsMap.get(text));
					autoSchoolSelector.setAdapter(schoolAdapter);
				}
			}
		});
    	
    }
    
    /**
	 * 获取Provinces
	 * @return
	 */
	private void getProvinces() {
        InputStream in = null;
        try{
            in = this.getAssets().open("provinces.xml");
            provinces = Parser.parseProvince(in);
        }catch (Exception e){

        }finally {
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
	}
	
    /**
	 * 获取school
	 * @return
	 */
	private void getSchools(String provinceName) {
		List<School> schools = null;
        InputStream in = null;
        try{
        	int temp = convert(provinceName);
            in = this.getAssets().open("schools" + temp + ".xml");
            schools = Parser.parseSchools(in);
            Log.d(TAG, schools.toString());
            this.schoolsMap.put(provinceName, schools);
        }catch (Exception e){
        	return;
        }finally {
            try {
                if(in != null){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } 
	}
	
	/**
	 * 省份名是否合法
	 * @param provinceName
	 * @return
	 */
	private boolean isLeagl(String provinceName) {
		for(Province p : provinces) {
			if(p.equals(provinceName)){
				return true;
			}
		}
		return false;
	}
	
	private int convert(String provinceName){
		for(int i=0; i<PROVINCES_NAMES.length; ++i){
			if(provinceName.equals(PROVINCES_NAMES[i])){
				return i + 1;
			}
		}
		return -1;
	}
	
}
