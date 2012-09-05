package dcu.case3.dual_craft;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    //STORE CONTEXT
    private LayoutInflater inflater;
    //STORE RESOURCE
    private int resource;
    //REF TO DATA
    private ArrayList<Streamer> data;
    
    /**
     * Default constructor. Creates the new Adaptor object to
     * provide a ListView with data.
     * @param context
     * @param resource
     * @param data
     */
    public CustomAdapter(Context context, 
    		int resource, ArrayList<Streamer> data) {
            this.inflater = (LayoutInflater) 
            		context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.resource = resource;
            this.data = data;
    }
    
    /**
     * Return the size of the data set.
     */
    public int getCount() {
            return this.data.size();
    }
    
    /**
     * Return an object in the data set.
     */
    public Object getItem(int position) {
            return this.data.get(position);
    }
    
    /**
     * Return the position provided.
     */
    public long getItemId(int position) {
            return position;
    }

    /**
     * Return a generated view for a position.
     */
    public View getView(int position, View convertView, ViewGroup parent) {
            View view;
             
            if (convertView == null) {
                    view = this.inflater.inflate(resource, parent, false);
            } else {
                    view = convertView;
            }
            
            //BIND TO VIEW OBJECT
            return this.bindData(view, position);
    }
    
    /**
     * Bind the provided data to the view.
     * This is the only method not required by base adapter.
     */
    public View bindData(View view, int position) {
            if (this.data.get(position) == null) {
                    return view;
            }
            
            //GET OBJECT
            Streamer item = this.data.get(position);
            
            //GET VIEW OBJECT
            View viewElement = view.findViewById(R.id.name);
            //CAST TO CORRECT TYPE
            TextView tv = (TextView)viewElement;
            //SET VALUE
            tv.setText(item.loginName);
            
            return view;
    }
}

