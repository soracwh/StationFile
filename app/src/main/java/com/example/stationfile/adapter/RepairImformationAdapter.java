package com.example.stationfile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.stationfile.R;
import com.example.stationfile.entity.RepairRecord;

import java.util.List;

public class RepairImformationAdapter extends BaseAdapter {

    private List<RepairRecord> data;
    private LayoutInflater layoutInflater;

    public RepairImformationAdapter(List<RepairRecord> data, Context context) {
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        RepairImformationAdapter.ViewHolder viewHolder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.imformationlayout,null,false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.person.setText(data.get(i).getPerson());
        viewHolder.date.setText(data.get(i).getTime());
        viewHolder.content.setText(data.get(i).getContent());
        return view;
    }

    static class ViewHolder{
        TextView person;
        TextView date;
        TextView content;
        public ViewHolder(View v){
            person = v.findViewById(R.id.person);
            date = v.findViewById(R.id.date);
            content = v.findViewById(R.id.content);
        }
    }
}
