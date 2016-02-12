package mx.selery.selery;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import mx.selery.library.security.UserSessionManager;
import mx.selery.library.utility.StringHelper;

/**
 * Created by htorres on 12/02/2016.
 */
public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>  {

    private JSONArray items;
    private UserSessionManager session;
    Context context;

    public static class ProgramViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView programImage;
        public TextView programName;
        public TextView description;
        public TextView selectedProgram;
        public TextView duration;

        public ProgramViewHolder(View v) {
            super(v);
            programImage = (ImageView) v.findViewById(R.id.image_program);
            programName = (TextView) v.findViewById(R.id.text_program_name);
            description = (TextView) v.findViewById(R.id.text_description);
            selectedProgram = (TextView) v.findViewById(R.id.text_selected_program);
            duration = (TextView) v.findViewById(R.id.text_duration);
        }
    }

    public ProgramAdapter(JSONArray items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.length();
    }

    @Override
    public ProgramViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.context=viewGroup.getContext();
        View v = LayoutInflater.from(this.context)
                .inflate(R.layout.program_card, viewGroup, false);
        return new ProgramViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ProgramViewHolder viewHolder, int i) {
        try
        {
            String unitOfMeasureTex = StringHelper.getValueFromResourceCode(items.getJSONObject(i).getString("UnitOfMeasureCode"), this.context);
            int colorID;
            if (i % 2 == 0)
                colorID= Color.parseColor(StringHelper.getValueFromResourceCode("wkt_program_item_BGColor", this.context));
            else
                colorID= Color.parseColor(StringHelper.getValueFromResourceCode("wkt_program_item_BGAlternateColor", this.context));

            viewHolder.itemView.setBackgroundColor(colorID);
            viewHolder.programImage.setImageResource(R.mipmap.ic_seleryprograma);
            viewHolder.programName.setText(items.getJSONObject(i).getString("Name"));
            viewHolder.description.setText(items.getJSONObject(i).getString("Description"));
            viewHolder.duration.setText(String.format("%1$s %2$s",items.getJSONObject(i).getString("Duration"),unitOfMeasureTex));

            /*if (!session.getUser().isNull("CurrentProgram") &&
                    session.getUser().getJSONObject("CurrentProgram").getString("ProgramID")==items.get(i).getString("ProgramID") &&
                    session.getUser().getJSONObject("CurrentProgram").getString("StartDate")=="MinValue")
            {
                viewHolder.selectedProgram.setVisibility(View.VISIBLE);
            }*/

        }
        catch(Exception e)
        {

        }

    }

}
