package com.thelionking.campus.activity;

import com.thelionking.campus.R;
import com.thelionking.campus.model.Discuss;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DiscussCommentActivity extends Activity{
	private Discuss discuss;
	private ImageView innerReturnImageView;
	private ImageView innerRefreshImageView;
	private TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discuss_comment_activity);
		discuss = (Discuss)getIntent().getSerializableExtra("discuss");
		findViewById();
		bindListener();
	}
	
	private void findViewById(){
		innerRefreshImageView = (ImageView)findViewById(R.id.inner_top_refresh_image_view);
		innerReturnImageView = (ImageView)findViewById(R.id.inner_top_return_image_view);
		innerRefreshImageView.setVisibility(View.GONE);
		title = (TextView)findViewById(R.id.inner_top_title);
		title.setText("问题讨论区");
	}
	
	private void bindListener() {
		innerReturnImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
