package com.gaoyuan.televition;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class LineAdapter extends BaseAdapter{

    Context context;
    List<String> list;

    public LineAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view==null){
            view = View.inflate(context,R.layout.item_line,null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.textView.setText("解析线路"+(i+1));

        return view;
    }

    class ViewHolder{
        TextView textView;

        public ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.tv_line);
        }
    }
}
