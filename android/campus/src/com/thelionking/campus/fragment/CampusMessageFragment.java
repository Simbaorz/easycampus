package com.thelionking.campus.fragment;

import java.lang.reflect.Field;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.thelionking.campus.R;
import com.thelionking.campus.adapter.SelectorAdapter;
import com.thelionking.campus.message.DataManager;
import com.thelionking.campus.message.JokeFragment;

/**
 * @author the lion king <b>校园资讯Fragment</b>
 */
public class CampusMessageFragment extends Fragment {
	public static final String TAG = "CampusMessageFragment";

	private View topLayout;
	private ImageView topMenuButton;
	private TextView topTitle;
	private ImageView topRefreshButton;
	private View rootLayout;
	private View content;
	private FragmentTabHost tabHost;
	private JokeFragment bulletinFragment;
	private JokeFragment newsFragment;
	private JokeFragment funFragment;
	private ListView selector;
	private ActionContext actionContext;
	
	private int currentFragment = 0;

	public CampusMessageFragment() {

	}

	public void setActionContext(final ActionContext actionContext) {
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootLayout = inflater.inflate(R.layout.campus_message, null);
		topLayout = rootLayout.findViewById(R.id.top_layout);
		topTitle = (TextView) rootLayout.findViewById(R.id.top_title);
		topTitle.setText("校园公告");
		topMenuButton = (ImageView) rootLayout
				.findViewById(R.id.top_menu_button);
		topRefreshButton = (ImageView) rootLayout
				.findViewById(R.id.top_refresh_button);
		topRefreshButton.setVisibility(View.GONE);
		selector = (ListView)rootLayout.findViewById(R.id.selector);
		selector.setAdapter(new SelectorAdapter(getActivity()));
//		View indicator = null;
//		tabHost = (FragmentTabHost) rootLayout.findViewById(R.id.tabhost);
//		tabHost.setup(getActivity(), getChildFragmentManager(),
//				R.id.realtabcontent);
//		indicator = getIndicatorView("校内公告");
//		tabHost.addTab(tabHost.newTabSpec("bulletin").setIndicator(indicator),
//				TextFragment.class, null);
//		indicator = getIndicatorView("校园趣事");
//		tabHost.addTab(tabHost.newTabSpec("fun").setIndicator(indicator),
//				PictureFragment.class, null);
		
		initFragment();
		bindListener();
		return rootLayout;
	}

	private void initFragment() {
		bulletinFragment = new JokeFragment();
		bulletinFragment.setNumber(0);
		bulletinFragment.setName(DataManager.TEXT_LASTEST);
		newsFragment = new JokeFragment();
		newsFragment.setNumber(1);
		newsFragment.setName(DataManager.PICTURE_LASTEST);
		funFragment = new JokeFragment();
		funFragment.setNumber(2);
		funFragment.setName(DataManager.PICTURE_LASTEST);
		FragmentManager fm = getChildFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.campus_message_content_layout, bulletinFragment, bulletinFragment.getName() );
		ft.commit();
	}

	private final void bindListener() {
		topMenuButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (topMenuButton == v) {
					actionContext.openMenu(0);
				}
			}
		});
		
		topTitle.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(selector.getVisibility() == View.GONE){
					Log.d(TAG, "VISIBLE");
					selector.setVisibility(View.VISIBLE);
				}else{
					selector.setVisibility(View.GONE);
					Log.d(TAG, "GONE");
				}
			}
		});
		
		selector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(currentFragment == position){
					selector.setVisibility(View.GONE);
					return;
				}
				currentFragment = position;
				
				FragmentTransaction ft = getChildFragmentManager().beginTransaction();
				switch(position){
				case 0: {
					topTitle.setText("校园新闻");
					ft.replace(R.id.campus_message_content_layout, bulletinFragment);
					ft.commit();
					break;
				}
				case 1: {
					topTitle.setText("校园活动");
					ft.replace(R.id.campus_message_content_layout, newsFragment);
					ft.commit();
					break;
				}
				case 2: {
					topTitle.setText("校园赛事");
					ft.replace(R.id.campus_message_content_layout, funFragment);
					ft.commit();
					break;
				}
				case 3: {
					topTitle.setText("校园讲座");
					ft.replace(R.id.campus_message_content_layout, funFragment);
					ft.commit();
					break;
				}
				case 4: {
					topTitle.setText("校园招聘");
					ft.replace(R.id.campus_message_content_layout, funFragment);
					ft.commit();
					break;
				}
				case 5: {
					topTitle.setText("全部资讯");
					ft.replace(R.id.campus_message_content_layout, funFragment);
					ft.commit();
					break;
				}
				default : {
					break;
				}
				
				}
				selector.setVisibility(View.GONE);
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

	private View getIndicatorView(String type) {
		View root = getActivity().getLayoutInflater().inflate(
				R.layout.tab_item_view, null, false);
		TextView typeTextView = (TextView) root
				.findViewById(R.id.tab_item_type);
		typeTextView.setText(type);
		return root;
	}

	@Override
	public void onDetach() {

		super.onDetach();

		try {

			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");

			childFragmentManager.setAccessible(true);

			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {

			throw new RuntimeException(e);

		} catch (IllegalAccessException e) {

			throw new RuntimeException(e);

		}

	}
	
	
}
