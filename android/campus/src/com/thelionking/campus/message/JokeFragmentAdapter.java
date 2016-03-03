package com.thelionking.campus.message;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by the lion king on 2014/9/16.
 */
public class JokeFragmentAdapter extends FragmentPagerAdapter{
    private List<JokeFragment> jokeFragment;

    private static final String[] TITLE = new String[] { "最近","历史" };

    public JokeFragmentAdapter(FragmentManager manager, List<JokeFragment> jokeFragment){
        super(manager);
        this.jokeFragment = jokeFragment;
    }
    
    @Override
    public Fragment getItem(int position) {
        return jokeFragment.get(position);
    }

    @Override
    public int getCount() {
        return jokeFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position % TITLE.length];
    }
}
