package com.thelionking.campus.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.thelionking.campus.R;

public class IntroductionActivity extends FragmentActivity{
public static final String TAG = "IntroductionActivity";
	
	private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduction_activity);
        viewPager = (ViewPager)findViewById(R.id.viewPager);
//        viewPager.setAdapter(new IntroductionAdapter(this));
    }
    
}
