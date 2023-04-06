package com.example.stationfile.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.stationfile.R;
import com.example.stationfile.entity.Defect;

import java.util.List;

public class DefectAdapter extends BaseAdapter {

    List<Defect> defects;
    private LayoutInflater layoutInflater;

    public DefectAdapter(List<Defect> defects, Context context) {
        this.defects = defects;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return defects.size();
    }

    @Override
    public Object getItem(int position) {
        return defects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        DefectAdapter.ViewHolder viewHolder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.defectlayout,null,false);
            viewHolder = new DefectAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        /*是否消缺*/
        if(defects.get(i).getFlag()==0){
            viewHolder.complete.setVisibility(View.GONE);
            viewHolder.completeLabel.setText("  未完成  ");
            viewHolder.completeLabel.setBackground(view.getResources().getDrawable(R.drawable.complete_text));
            viewHolder.completeLabel.setTextColor(Color.parseColor("#E31809"));
        }else{
            viewHolder.complete.setVisibility(View.VISIBLE);
            viewHolder.completeLabel.setText("  已完成  ");
            viewHolder.completeLabel.setBackground(view.getResources().getDrawable(R.drawable.shape_text));
            viewHolder.completeLabel.setTextColor(Color.parseColor("#03A9F4"));
            viewHolder.person.setText(defects.get(i).getPerson());
            viewHolder.date.setText(defects.get(i).getTime());
        }

        if (defects.get(i).getLevel() == 1) {
            viewHolder.level.setText("  一般缺陷  ");
            viewHolder.level.setTextColor(Color.parseColor("#FFEB3B"));
            viewHolder.level.setBackground(view.getResources().getDrawable(R.drawable.yellow_text));
        } else {
            viewHolder.level.setText("  重要缺陷  ");
            viewHolder.level.setTextColor(Color.parseColor("#E31809"));
            viewHolder.level.setBackground(view.getResources().getDrawable(R.drawable.complete_text));
        }
        viewHolder.content.setText(defects.get(i).getContent());
        return view;
    }

    static class ViewHolder{
        TextView person;
        TextView date;
        TextView content;
        TextView level;
        View complete;
        TextView completeLabel;
        public ViewHolder(View v){
            complete = v.findViewById(R.id.complete1);
            person = v.findViewById(R.id.person);
            date = v.findViewById(R.id.date);
            content = v.findViewById(R.id.content);
            level = v.findViewById(R.id.level);
            completeLabel = v.findViewById(R.id.complete_label);
        }
    }
}
