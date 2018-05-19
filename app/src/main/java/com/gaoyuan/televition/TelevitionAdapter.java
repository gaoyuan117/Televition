package com.gaoyuan.televition;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gaoyuan on 2018/5/14.
 */

public class TelevitionAdapter extends BaseAdapter {

    private Context context;
    private List<TelevitionBean> list;

    public TelevitionAdapter(Context context, List<TelevitionBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_televiton, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageView.setImageResource(list.get(position).img);
        holder.textView.setText(list.get(position).title);
        return convertView;
    }

    class ViewHolder {
        CircleImageView imageView;
        TextView textView;

        public ViewHolder(View view) {
            imageView = (CircleImageView) view.findViewById(R.id.img_logo);
            textView = (TextView) view.findViewById(R.id.tv_title);
        }
    }
}
