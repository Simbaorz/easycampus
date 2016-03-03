package com.thelionking.campus.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.thelionking.campus.R;
import com.thelionking.campus.adapter.ProvinceListAdapter;
import com.thelionking.campus.fragment.ActionContext;

public class SchoolMapActivity extends Activity{
	
	public static final String TAG = "MapFragment";

    
    private View rootLayout;
    private ImageView imagemap;

    private ActionContext actionContext;

    public SchoolMapActivity() {

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schoolservice_map);
        
    }
}
