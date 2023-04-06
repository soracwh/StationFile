package com.example.stationfile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.stationfile.R;
import com.example.stationfile.entity.Simplified;

import java.util.List;

public class StationAdapter extends BaseAdapter {

    private List<Simplified> data;
    private LayoutInflater layoutInflater;

    public StationAdapter(List<Simplified> data, Context context) {
        this.data = data;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.stationlayout,null,false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.singleName.setText(data.get(i).getName());
        viewHolder.s_id.setText(String.valueOf(data.get(i).getId()));
        return view;
    }

    class ViewHolder{
        TextView singleName;
        TextView s_id;
        public ViewHolder(View v){
            singleName = v.findViewById(R.id.name);
            s_id = v.findViewById(R.id.sid);
        }
    }
}
