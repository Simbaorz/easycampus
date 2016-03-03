package com.thelionking.campus.syllabus;

import com.thelionking.campus.R;
import com.thelionking.campus.activity.MainActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FeedbackActivity extends Activity {
	private Button backButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_feedback);
		backButton = (Button) findViewById(R.id.back2);
		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent a = new Intent(FeedbackActivity.this,
						MainActivity.class);
				startActivity(a);
				FeedbackActivity.this.finish();
			}

		});
	}
}
