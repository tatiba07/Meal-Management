package com.example.dietmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// define the Adapter for the RecyclerView
public class ViewAdapterDatabase extends RecyclerView.Adapter<ViewAdapterDatabase.ViewHolderDatabase> {

    public List<EntityFood> foodList;
    public
    ItemClickListener itemClickListener;
    int selectedPosition = -1;

    public ViewAdapterDatabase(List<EntityFood> list, ItemClickListener itemClickListener)
    {
        this.foodList = list;
        this.itemClickListener = itemClickListener;
    }
    @Override
    public ViewHolderDatabase onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_database, parent, false);
        ViewHolderDatabase vh = new ViewHolderDatabase(inflate);
        return vh;
    }
    // sets the contents when scrolling through the list
    @Override
    public void onBindViewHolder(ViewHolderDatabase holder, int position) {
        // set Text
        holder.txt_food.setText(foodList.get(position).getFoodName());
        holder.txt_per.setText(Integer.toString(foodList.get(position).getPer()));
        holder.txt_calorie.setText(Double.toString(foodList.get(position).getCalorie()));
        holder.txt_carbon.setText(Double.toString(foodList.get(position).getCarbon()));
        holder.txt_protein.setText(Double.toString(foodList.get(position).getProtein()));
        holder.txt_fat.setText(Double.toString(foodList.get(position).getFat()));
        // select single radioButton
        //holder.radioBtn.setText(list.get(position).isRadioBtn());
        holder.radioBtn.setChecked(position == selectedPosition);
        holder.radioBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                // check condition
                if(b && itemClickListener != null)
                {
                    // when checked, update selected position
                    selectedPosition = holder.getAdapterPosition();
                    // call listener
                    itemClickListener.onClick(holder.radioBtn.getText().toString());
                }
            }
        });
    }
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    @Override public int getItemViewType(int position)
    {
        // pass position
        return position;
    }
    @Override
    public int getItemCount() {
        return foodList.size();
    }
    public class ViewHolderDatabase extends RecyclerView.ViewHolder {

        public Double[] localDataSet;
        public TextView txt_food;
        public TextView txt_per;
        public TextView txt_calorie;
        public TextView txt_carbon;
        public TextView txt_protein;
        public TextView txt_fat;
        RadioButton radioBtn;

        public ViewHolderDatabase(@NonNull View itemView) {
            super(itemView);
            txt_food = itemView.findViewById(R.id.txt_dbFood);
            txt_per = itemView.findViewById(R.id.txt_dbPer);
            txt_calorie = itemView.findViewById(R.id.txt_dbCalorie);
            txt_carbon = itemView.findViewById(R.id.txt_dbCarbon);
            txt_protein = itemView.findViewById(R.id.txt_dbProtein);
            txt_fat = itemView.findViewById(R.id.txt_dbFat);
            radioBtn = itemView.findViewById(R.id.radioBtn);
        }
    }
}