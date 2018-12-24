package com.myfit.brownies.myfit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {

    private FoodStructure<Food> items;
    private Context context;

    //public constructor
    public CustomAdapter(Context context, FoodStructure<Food> items) {
        this.context = context;
        this.items = items;
    }

    public void setListData(FoodStructure<Food> data){
        items = data;
    }

    @Override
    public int getCount() {
        return items.size(); //returns total of items in the list
    }

    @Override
    public Food getItem(int position) {
        return items.dumpData(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public void remove(int position){
        items.remove(getItem(position));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // inflate the layout for each list row
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.list_item, parent, false);
        }

        // get the TextView for item name and item description
        TextView textViewItemName = (TextView)
                convertView.findViewById(R.id.name);
        TextView textViewItemDescription = (TextView)
                convertView.findViewById(R.id.calories);

        //sets the text for item name and item description from the current item object
        textViewItemName.setText(getItem(position).getIName());
        textViewItemDescription.setText(Integer.toString(((int) Double.parseDouble(getItem(position).getCalories()))));

        Button deleteBtn = (Button) convertView.findViewById(R.id.delete_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                FoodDatabase foodDatabaseHelper = new FoodDatabase(context);
                foodDatabaseHelper.deleteFood(getItem(position).getID());

                Log.d("CUSTOMADAPTER POSITION", Integer.toString(position));
                Log.d("CUSTOMADAPTER DATABASE", Integer.toString(getItem(position).getID()));

                remove(position);
                notifyDataSetChanged();
            }
        });

        // returns the view for the current row
        return convertView;
    }
}
