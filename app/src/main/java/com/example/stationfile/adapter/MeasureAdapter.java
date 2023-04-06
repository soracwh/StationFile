package com.example.stationfile.adapter;

import android.content.Context;
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
        viewHolder.content.setText(measures.get(i).getContent());
        return view;
    }

    class ViewHolder{
        TextView content;

        public ViewHolder(View view) {
            this.content = view.findViewById(R.id.content);
        }
    }
}
