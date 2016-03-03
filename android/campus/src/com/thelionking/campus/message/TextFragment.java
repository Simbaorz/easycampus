package com.thelionking.campus.message;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.thelionking.campus.R;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.UnderlinePageIndicator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by the lion king on 2014/10/23.
 */
public final class TextFragment extends Fragment{

    private ViewPager viewPager;
    private JokeFragmentAdapter jokeFragmentAdapter;
    private TabPageIndicator indicator;
    private UnderlinePageIndicator underlinePageIndicator;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化adapter
        initAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_fragment, container, false);
        viewPager = (ViewPager)root.findViewById(R.id.view_pager);
        viewPager.setAdapter(jokeFragmentAdapter);
        indicator = (TabPageIndicator)root.findViewById(R.id.indicator);
        indicator.setViewPager(viewPager);
        underlinePageIndicator = (UnderlinePageIndicator)root.findViewById(R.id.underline_page_indicator);
        underlinePageIndicator.setViewPager(viewPager);
        underlinePageIndicator.setOnPageChangeListener(indicator);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
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


    /**
     * 初始化JokeFragmentAdapter
     */
    private void initAdapter() {
        //fragments容器
        List<JokeFragment> fragments = new ArrayList<JokeFragment>();
        //推荐的Fragment
        JokeFragment recommend = new JokeFragment();
        //最新的Fragment
        JokeFragment lastest = new JokeFragment();
        //设置Number
        recommend.setNumber(1);
        lastest.setNumber(0);
        //设置Name
        recommend.setName(DataManager.TEXT_RECOMMEND);
        lastest.setName(DataManager.TEXT_LASTEST);
        //添加
        fragments.add(lastest);
        fragments.add(recommend);
        jokeFragmentAdapter = new JokeFragmentAdapter(getChildFragmentManager(), fragments);
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
