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
import com.thelionking.campus.model.Province;


/**
 * @author the lion king
 */
public class ProvinceListAdapter extends BaseAdapter implements Filterable{
	private List<Province> provinces;
	
	private List<Province> datas;
    private Context context;
    
    private ProvinceFilter filter;

    public ProvinceListAdapter(final Context context, List<Province> provinces) {
        this.context = context;
        this.provinces = provinces;
    }

    @Override
    public int getCount() {
        return provinces.size();
    }

    @Override
    public Object getItem(int position) {
        return provinces.get(position);
    }

    @Override
    public long getItemId(int position) {
        return provinces.get(position).getProvinceId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.province_list_view_item, null);
        TextView textView = (TextView)view.findViewById(R.id.item_name);
        textView.setText(provinces.get(position).getProvinceName());
        return view;
    }
    
	@Override
	public Filter getFilter() {
		if(this.filter == null){
			this.filter = new ProvinceFilter();
		}
		return this.filter;
	}
	
	private class ProvinceFilter extends Filter {  
        
        public ProvinceFilter() {}
        	
        @Override  
        protected FilterResults performFiltering(CharSequence constraint) {  
            FilterResults results = new FilterResults();  
            if (constraint == null || constraint.length() == 0) {  
                results.values = provinces;  
                results.count = provinces.size();  
            } else {  
                List<Province> temp = new ArrayList<Province>();  
                for (Province p: provinces) {  
                    if (p.getProvinceName().trim().toUpperCase().startsWith(constraint.toString().trim().toUpperCase())) {  
                        temp.add(p);  
                    }  
                }  
                results.values = temp;  
                results.count = temp.size();  
            }  
            return results;  
        }  

        @Override  
        protected void publishResults(CharSequence constraint,  FilterResults results) {  
        	datas = (List<Province>)results.values;  
            notifyDataSetChanged();  
        }  
          
    }  
}
