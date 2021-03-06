package mx.selery.selery;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

import mx.selery.entity.AvailableProgram;
import mx.selery.entity.Program;
import mx.selery.library.security.UserSessionManager;
import mx.selery.library.ui.ActivityBase;
import mx.selery.library.utility.StringHelper;

/**
 * Created by htorres on 12/02/2016.
 */
public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>  {

    private List<AvailableProgram> items;
    private UserSessionManager session;
    private Context context;

    public static class ProgramViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        // Campos respectivos de un item
        public ImageView programImage;
        public TextView programName;
        public TextView description;
        public TextView selectedProgram;
        public TextView duration;
        public RelativeLayout rlayout;
        public Program program;

        public ProgramViewHolder(View v) {
            super(v);
            v.setClickable(true);
            v.setOnClickListener(this);
            rlayout = (RelativeLayout) v.findViewById(R.id.rlayout);
            programImage = (ImageView) v.findViewById(R.id.image_program);
            programName = (TextView) v.findViewById(R.id.text_program_name);
            description = (TextView) v.findViewById(R.id.text_description);
            selectedProgram = (TextView) v.findViewById(R.id.text_selected_program);
            duration = (TextView) v.findViewById(R.id.text_duration);
        }

        @Override
        public void onClick(View v) {
            try
            {
                Gson gson = new Gson();
                Intent intent = new Intent().setClass(v.getContext(), ProgramSelectActivity.class);
                intent.putExtra("Position", getAdapterPosition());
                intent.putExtra("Program",gson.toJson(this.program));
                v.getContext().startActivity(intent);
            }
            catch(Exception ex)
            {
                ActivityBase.handleException(ex);
            }

        }

    }

    public ProgramAdapter(List<AvailableProgram> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
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
            String unitOfMeasureTex = StringHelper.getValueFromResourceCode(items.get(i).getUnitOfMeasureCode(), this.context);
            if (i % 2 == 0)
                viewHolder.rlayout.setBackgroundColor(ContextCompat.getColor(this.context,R.color.wkt_program_item_BGColor));
            else
                viewHolder.rlayout.setBackgroundColor(ContextCompat.getColor(this.context,R.color.wkt_program_item_BGAlternateColor));

            viewHolder.program=items.get(i);
            viewHolder.programImage.setImageResource(R.mipmap.ic_seleryprograma);
            viewHolder.programName.setText(items.get(i).getName());
            viewHolder.description.setText(items.get(i).getDescription());
            viewHolder.duration.setText(String.format("%1$s %2$s", items.get(i).getDuration(), unitOfMeasureTex));

            if (items.get(i).getIsCurrent())
            {
                viewHolder.selectedProgram.setText(StringHelper.getValueFromResourceCode("wkt_selected_program_text3", this.context));
                viewHolder.selectedProgram.setVisibility(View.VISIBLE);
            }

        }
        catch(Exception ex)
        {
            ActivityBase.handleException(ex);
        }

    }

}
