package com.thelionking.campus.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.thelionking.campus.R;

/**
 * @author the lion king
 * <b>设置Fragment</b>
 */
public class SettingFragment extends Fragment{
    public static final String TAG = "SettingFragment";

    private View topLayout;
    private ImageView topMenuButton;
    private TextView topTitle;
    private ImageView topRefreshButton;
    private View rootLayout;

    private ActionContext actionContext;

    public SettingFragment() {

    }

    public void setActionContext(final ActionContext actionContext){
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootLayout = inflater.inflate(R.layout.setting_fragment, null);
        topLayout = rootLayout.findViewById(R.id.top_layout);
        topTitle = (TextView)rootLayout.findViewById(R.id.top_title);
        topTitle.setText("设置");
        topMenuButton = (ImageView)rootLayout.findViewById(R.id.top_menu_button);
        topRefreshButton = (ImageView)rootLayout.findViewById(R.id.top_refresh_button);
        topRefreshButton.setVisibility(View.GONE);
        bindListener(); 	
        return rootLayout;
    }

    private final void bindListener(){
        topMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(topMenuButton == v){
                    actionContext.openMenu(0);
                }
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

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
