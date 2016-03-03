package com.thelionking.campus.syllabus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.thelionking.campus.R;
import com.thelionking.campus.activity.MainActivity;
import com.thelionking.campus.syllabus.CustomerDateDialog.DateDialogListener;

public class Alarm extends Activity {
	private ToggleButton tButton1;
	private ToggleButton tButton2;
	private Button back;
	private int h, m;  
    private CustomerDateDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.alarm);
		tButton1 = (ToggleButton) findViewById(R.id.toggleButton1);
		tButton2 = (ToggleButton) findViewById(R.id.toggleButton2);
		back= (Button) findViewById(R.id.back1);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent a = new Intent(Alarm.this,
						MainActivity.class);
				startActivity(a);
				Alarm.this.finish();
				
			}

		});
		tButton1.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					Toast.makeText(getApplicationContext(), "开启上课静音模式",
							Toast.LENGTH_SHORT).show();
				}else{
					//关闭
					Toast.makeText(getApplicationContext(), "关闭上课静音模式",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		tButton2.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					//开启
					String datetime = DateFormat.format("kk:mm",  
	                        System.currentTimeMillis()).toString();  
	                String[] strs = datetime.split(":");  
	                h = Integer.parseInt(strs[0]);  
	                m = Integer.parseInt(strs[1]);  
	                dialog = new CustomerDateDialog(Alarm.this, h, m);  
	                dialog.show();  
	                dialog.setOnDateDialogListener(new DateDialogListener() {  
	                    public void getDate() {  
	                        Toast.makeText(  
	                                Alarm.this,  
	                                "时间是:" + dialog.getSettingHour() + "点"  
	                                        + dialog.getSettingMinute() + "分",  
	                                Toast.LENGTH_LONG).show();  
	                    }  
	                }); 
				}else{
					//关闭
					
				}
			}
		});
	}
}
