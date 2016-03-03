package com.thelionking.campus.adapter;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.thelionking.campus.MainApp;
import com.thelionking.campus.customview.BuildingView;
import com.thelionking.campus.customview.RoomView;
import com.thelionking.campus.model.Building;

/**
 * @author the lion king
 */
public class BuildingListAdapter extends BaseExpandableListAdapter{
	public static final String TAG = "BuildingListAdapter";
    private Context context;

    private List<Building> buildings;

    public BuildingListAdapter(final Context context) {
        this.context = context;
        //for test
        buildings = MainApp.getInstance().getDataManager().getBuildings();
    }

	@Override
	public int getGroupCount() {
		return buildings.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		
		return buildings.get(groupPosition).getRooms().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return buildings.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return buildings.get(groupPosition).getRooms().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return buildings.get(groupPosition).getBuildingId();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return buildings.get(groupPosition).getRooms().get(childPosition).getRoomId();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		BuildingView view = null;
		if(convertView == null) {
			view = new BuildingView(context);
		}
		else{
			view = (BuildingView)convertView;
		}
		Log.d(TAG, buildings.get(groupPosition).toString());
		view.setPosition(groupPosition);
		view.setBuilding(buildings.get(groupPosition));
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		RoomView view = null;
		if(convertView == null) {
			view = new RoomView(context);
		}
		else{
			view = (RoomView)convertView;
		}
		view.setRoom(buildings.get(groupPosition).getRooms().get(childPosition));
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	@Override
	public boolean hasStableIds() {
		return true;
	}

}
