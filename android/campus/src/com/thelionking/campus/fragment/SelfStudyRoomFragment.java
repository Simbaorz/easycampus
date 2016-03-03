package com.thelionking.campus.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.thelionking.campus.R;
import com.thelionking.campus.adapter.BuildingListAdapter;

/**
 * @author the lion king
 * <b>自习室Fragment</b>
 */
public class SelfStudyRoomFragment extends Fragment{
    public static final String TAG = "SelfStudyRoomFragment";

    private View topLayout;
    private ImageView topMenuButton;
    private TextView topTitle;
    private ImageView topRefreshButton;
    private View rootLayout;

    private ExpandableListView buildingListView;

    private ActionContext actionContext;

    public SelfStudyRoomFragment() {

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
        rootLayout = inflater.inflate(R.layout.self_study_room_fragment, null);
        topLayout = rootLayout.findViewById(R.id.top_layout);
        topTitle = (TextView)rootLayout.findViewById(R.id.top_title);
        topTitle.setText("自习室");
        topMenuButton = (ImageView)rootLayout.findViewById(R.id.top_menu_button);
        topRefreshButton = (ImageView)rootLayout.findViewById(R.id.top_refresh_button);
        buildingListView = (ExpandableListView)rootLayout.findViewById(R.id.building_list_view);
        buildingListView.setAdapter(new BuildingListAdapter(getActivity()));
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
