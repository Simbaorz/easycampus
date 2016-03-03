package com.thelionking.campus.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.thelionking.campus.R;
import com.thelionking.campus.model.School;


/**
 * @author the lion king
 * SchoolListActivity中需保证传入的provinceId都是可以被解析的
 */
public class SchoolListAdapter extends BaseAdapter implements Filterable{
	private List<School> schools;
	
	private List<School> datas;
    private Context context;
    
    private SchoolFilter filter;

    public SchoolListAdapter(final Context context, List<School> schools) {
        this.context = context;
        this.schools = schools;
    }
    
    @Override
    public int getCount() {
        return schools.size();
    }

    @Override
    public Object getItem(int position) {
        return schools.get(position);
    }

    @Override
    public long getItemId(int position) {
        return schools.get(position).getSchoolId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.province_list_view_item, null);
        TextView textView = (TextView)view.findViewById(R.id.item_name);
        textView.setText(schools.get(position).getSchoolName());
        return view;
    }
    
	@Override
	public Filter getFilter() {
		if(this.filter == null){
			this.filter = new SchoolFilter();
		}
		return this.filter;
	}
	
	private class SchoolFilter extends Filter {  
        
        public SchoolFilter() {}
        	
        @Override  
        protected FilterResults performFiltering(CharSequence constraint) {  
            FilterResults results = new FilterResults();  
            if (constraint == null || constraint.length() == 0) {  
                results.values = schools;  
                results.count = schools.size();  
            } else {  
                List<School> temp = new ArrayList<School>();  
                for (School s: schools) {  
                    if (s.getSchoolName().trim().toUpperCase().startsWith(constraint.toString().trim().toUpperCase())) {  
                        temp.add(s);  
                    }  
                }  
                results.values = temp;  
                results.count = temp.size();  
            }  
            return results;  
        }  

        @Override  
        protected void publishResults(CharSequence constraint,  FilterResults results) {  
        	datas = (List<School>)results.values;  
            notifyDataSetChanged();  
        }  
          
    }  
}
