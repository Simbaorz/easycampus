package com.thelionking.campus.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.thelionking.campus.R;
import com.thelionking.campus.model.Building;

/**
 * @author the lion king
 */
public class BuildingView extends FrameLayout {
    public static final String TAG = "BuildingView";

    private Building building;
    
    private int position;

    private ViewHolder holder = new ViewHolder();

    public BuildingView(Context context) {
        super(context);
        initView();
    }

    public BuildingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BuildingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void setBuilding(Building building){
        this.building = building;
        updateData();
    }
    
    public void setPosition(int position){
    	this.position = ++position;
    }

    private void initView(){
        View view = View.inflate(getContext(), R.layout.building_list_item, null);
        holder.root = view.findViewById(R.id.root);
        holder.front = view.findViewById(R.id.front);
        holder.back = view.findViewById(R.id.back);
        holder.number = (TextView)view.findViewById(R.id.number);
        holder.buildingItemName = (TextView)view.findViewById(R.id.building_item_name);
        holder.roomNumber = (TextView)view.findViewById(R.id.room_number);
        addView(view);
    }

    private void updateData(){
//        holder.textView.setText(building.getBuildingName());
    	if(this.position % 2 == 1){
//    		holder.root.setBackgroundColor()
    		holder.front.setBackgroundColor(getContext().getResources().getColor(R.color.building_even_background));
    		holder.root.setBackgroundColor(getContext().getResources().getColor(R.color.building_odd_background));
    	}else{
    		holder.front.setBackgroundColor(getContext().getResources().getColor(R.color.building_odd_background));
    		holder.root.setBackgroundColor(getContext().getResources().getColor(R.color.building_even_background));
    	}
    	holder.number.setText(this.position + "");
    	holder.buildingItemName.setText(building.getBuildingName());
    	holder.roomNumber.setText("[" + building.getRooms().size() + "]");
    }

    private static final class ViewHolder{
        View root;
        View front;
        TextView number;
        View back;
        TextView buildingItemName;
        TextView roomNumber;
    }
}
