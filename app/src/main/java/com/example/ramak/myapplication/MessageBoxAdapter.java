package com.example.ramak.myapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by songjee on 2017. 11. 1..
 */


public class MessageBoxAdapter extends BaseAdapter {

    private ArrayList<MessageBoxItem> MessageBoxItemList = new ArrayList<MessageBoxItem>();


    public MessageBoxAdapter() {
        //System.out.printf("MessageBoxAdapter");

    }

    @Override
    public int getCount() {
        return MessageBoxItemList.size();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // referencing convertView using tab3
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.messageboxitem, parent, false);
        }

        // items to show on each list item
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2) ;


        // Get data from Data Set(MessageBoxItemList) on 'position'
        MessageBoxItem messageItem = MessageBoxItemList.get(position);

        //set data on Widget
        iconImageView.setImageDrawable(messageItem.getIcon());
        titleTextView.setText(messageItem.getTitle());
        descTextView.setText(messageItem.getDesc());

        return convertView;


    }

    @Override
    public long getItemId(int position) {
        return position ;
    }

    // return MessageBoxItem on 'position'
    @Override
    public Object getItem(int position) {
        return MessageBoxItemList.get(position) ;
    }


    public void addItem(Drawable icon, String title, String desc) {
        MessageBoxItem item = new MessageBoxItem();

        item.setIcon(icon);
        item.setTitle(title);
        item.setDesc(desc);

        MessageBoxItemList.add(item);
    }



}
