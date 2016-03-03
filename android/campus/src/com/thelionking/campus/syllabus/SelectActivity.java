package com.thelionking.campus.syllabus;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thelionking.campus.R;
import com.thelionking.campus.activity.MainActivity;
import com.thelionking.campus.fragment.SyllabusFragment;
public class SelectActivity extends Activity {
	 private Button backButton; 
	 private Button saveButton;
	 private EditText course;
	 private EditText place;
	 private EditText teacher;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		course=(EditText)findViewById(R.id.addcourseclass);
		place=(EditText)findViewById(R.id.addcourseedit);
		teacher=(EditText)findViewById(R.id.addcourseteacher);
		backButton = (Button) findViewById(R.id.back1);
		saveButton= (Button) findViewById(R.id.saveb);

		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//获取课表位置
				Intent intent=getIntent();  
		        String title=intent.getStringExtra("number");  
		        int day=Integer.parseInt(title.substring(0,1));
		        int number=Integer.parseInt(title.substring(1,2));
				//获得编辑信息
		        String c=course.getText().toString();
		        String p=place.getText().toString();
		        String t=teacher.getText().toString();
		        //=插入数据==================
		        if(c.equals("")&&p.equals("")&&t.equals(""))
		        {
		        	Toast.makeText(getApplicationContext(), "请输入信息！",
							Toast.LENGTH_SHORT).show();
		        }
		        else {
					
					SyllabusFragment.db.update(day, number+1, c, p, t, "", "", "", "", "");
					SyllabusFragment.db.close();
					
					Toast.makeText(getApplicationContext(), "保存成功！",
							Toast.LENGTH_SHORT).show();
					Intent a = new Intent(SelectActivity.this,
							MainActivity.class);
					startActivity(a);
					SelectActivity.this.finish();
		        }
			}
		});
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SyllabusFragment.db.close();
				Intent a = new Intent(SelectActivity.this,
						MainActivity.class);
				startActivity(a);
				SelectActivity.this.finish();
				
			}

		});
	}
}
