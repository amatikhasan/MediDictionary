package com.example.daffodil.medidictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
    public class ListViewAdapter extends BaseAdapter {

        //this class helps to bind data to the list view

        // Declare Variables
        Context mContext;
        LayoutInflater inflater;
        private List<WordData> NamesList = null;
        private ArrayList<WordData> arraylist;

        //constructor for this class
        public ListViewAdapter(Context context, ArrayList<WordData> NamesList) {
            mContext = context;
            this.NamesList = NamesList;
            inflater = LayoutInflater.from(mContext);
            this.arraylist = new ArrayList<WordData>();
            this.arraylist.addAll(NamesList);
        }


        public class ViewHolder {
            TextView name;
        }

        @Override
        public int getCount() {
            return NamesList.size();
        }

        @Override
        public WordData getItem(int position) {
            return NamesList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.list_view_items,null);
                // Locate the TextViews in listview_item.xml
                holder.name = (TextView) view.findViewById(R.id.name);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            // Set the results into TextViews
            holder.name.setText(NamesList.get(position).getWord());
            return view;
        }

        // Filter Class to filter and show the result in list view
        public void filter(String charText) {
            charText = charText.toLowerCase(Locale.getDefault());
            NamesList.clear();
            if (charText.length() == 0) {
                NamesList.addAll(arraylist);
            } else {
                for (WordData wp : arraylist) {
                    if (wp.getWord().toLowerCase(Locale.getDefault()).startsWith(charText)) {
                        NamesList.add(wp);
                    }
                }
            }
            notifyDataSetChanged();
        }

    }

