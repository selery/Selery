package mx.selery.selery;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import mx.selery.entity.Activity;

/**
 * Created by htorres on 21/03/2016.
 */
public class SpinAdapter extends ArrayAdapter<mx.selery.entity.Activity> {
    private Context context;
    private List<mx.selery.entity.Activity> values;


    public SpinAdapter(Context context, int textViewResourceId, List<mx.selery.entity.Activity> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount() {
        return values.size();
    }

    public mx.selery.entity.Activity getItem(int position) {
        return values.get(position);
    }

    public int GetPositionByItemID(int itemID)
    {
        int position = -1;

        for (int i = 0; i < values.size(); i++)
        {

            if (values.get(i).getActivityID() == itemID)
            {
                position = i;
                break;
            }
        }

        return position;

    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(this.getItem(position).ActivityName);
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setText(this.getItem(position).ActivityName);
        return label;
    }


}
