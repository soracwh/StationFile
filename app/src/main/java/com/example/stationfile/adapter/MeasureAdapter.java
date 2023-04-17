package com.example.stationfile.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.stationfile.R;
import com.example.stationfile.entity.Measure;

import java.util.List;

public class MeasureAdapter extends BaseAdapter {

    List<Measure> measures;
    private LayoutInflater layoutInflater;

    public MeasureAdapter(List<Measure> measures, Context context) {
        this.measures = measures;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return measures.size();
    }

    @Override
    public Object getItem(int position) {
        return measures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        MeasureAdapter.ViewHolder viewHolder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.measurelayout,null,false);
            viewHolder = new MeasureAdapter.ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        /*是否消缺*/
        if(measures.get(i).getFlag()==0){
            viewHolder.completeLabel.setText("  未完成  ");
            viewHolder.completeLabel.setBackground(view.getResources().getDrawable(R.drawable.complete_text));
            viewHolder.completeLabel.setTextColor(Color.parseColor("#E31809"));
            viewHolder.iv.setVisibility(View.GONE);
        }else{
            viewHolder.completeLabel.setText("  已完成  ");
            viewHolder.completeLabel.setBackground(view.getResources().getDrawable(R.drawable.shape_text));
            viewHolder.completeLabel.setTextColor(Color.parseColor("#03A9F4"));
            viewHolder.iv.setVisibility(View.VISIBLE);
            viewHolder.person.setText(measures.get(i).getPerson());
            viewHolder.date.setText(measures.get(i).getDate());
            viewHolder.target.setText(measures.get(i).getTarget());
        }
        viewHolder.content.setText(measures.get(i).getContent());
        return view;
    }

    class ViewHolder{
        TextView content;
        TextView completeLabel;
        TextView target;
        TextView date;
        TextView person;
        View iv;
        public ViewHolder(View view) {
            this.content = view.findViewById(R.id.content);
            this.completeLabel = view.findViewById(R.id.complete_label);
            this.target = view.findViewById(R.id.target);
            this.person = view.findViewById(R.id.person);
            this.date = view.findViewById(R.id.date);
            this.iv = view.findViewById(R.id.iv);
        }
    }
}
