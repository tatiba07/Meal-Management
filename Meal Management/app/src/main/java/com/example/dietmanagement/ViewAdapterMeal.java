package com.example.dietmanagement;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// define the Adapter for the RecyclerView
public class ViewAdapterMeal extends RecyclerView.Adapter<ViewAdapterMeal.ViewHolderMeal> {
    private Context context;
    public List<List<String>> foodList;
    public ItemClickListener itemClickListener;
    int selectedPosition = -1;

    public ViewAdapterMeal(Context context, List<List<String>> list, ItemClickListener itemClickListener)
    {
        this.context = context;
        this.foodList = list;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolderMeal onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_meal, parent, false);
        ViewHolderMeal vh = new ViewHolderMeal(inflate);
        return vh;
    }
    // sets the contents when scrolling through the list
    @Override
    public void onBindViewHolder(ViewAdapterMeal.ViewHolderMeal holder, int position) {
        // set Text
        TextView[] txts = new TextView[]{
                holder.txt_mealNum,holder.txt_mealCal,
                holder.txt_mealFoodName1,holder.txt_mealPer1,holder.txt_mealCarbon1,holder.txt_mealProtein1,holder.txt_mealFat1,
                holder.txt_mealFoodName2,holder.txt_mealPer2,holder.txt_mealCarbon2,holder.txt_mealProtein2,holder.txt_mealFat2,
                holder.txt_mealFoodName3,holder.txt_mealPer3,holder.txt_mealCarbon3,holder.txt_mealProtein3,holder.txt_mealFat3,
                holder.txt_mealFoodName4,holder.txt_mealPer4,holder.txt_mealCarbon4,holder.txt_mealProtein4,holder.txt_mealFat4,
                holder.txt_mealFoodName5,holder.txt_mealPer5,holder.txt_mealCarbon5,holder.txt_mealProtein5,holder.txt_mealFat5,
        };
        for(int cnt = 0; cnt < txts.length;cnt++)
        {
            if(cnt == 1)
            {
                txts[cnt].setText(foodList.get(position).get(cnt));
            }
            if(foodList.get(position).get(cnt).length() == 0)
            {
                switch (cnt)
                {
                    case 2:
                        holder.linear_food1.setVisibility(View.GONE);
                        break;
                   case 7:
                        holder.linear_food2.setVisibility(View.GONE);
                        break;
                   case 12:
                        holder.linear_food3.setVisibility(View.GONE);
                        break;
                   case 17:
                        holder.linear_food4.setVisibility(View.GONE);
                        break;
                   case 22:
                        holder.linear_food5.setVisibility(View.GONE);
                        break;
                }
            }
            txts[cnt].setText(foodList.get(position).get(cnt));
        }
        Intent intent = new Intent(context, ActivityMeal.class);
        holder.imgBtn_mealEdit.setOnClickListener(view -> {
            intent.putExtra("mealNum", position + 1);
            view.getContext().startActivity(intent);
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
    public class ViewHolderMeal extends RecyclerView.ViewHolder {
        public ImageButton imgBtn_mealEdit;
        public LinearLayout linear_food1, linear_food2,linear_food3,linear_food4,linear_food5;
        public TextView txt_mealNum;
        public TextView txt_mealCal;
        public TextView txt_mealFoodName1,txt_mealFoodName2,txt_mealFoodName3,
                        txt_mealFoodName4,txt_mealFoodName5;
        public TextView txt_mealPer1,txt_mealPer2,txt_mealPer3,txt_mealPer4,txt_mealPer5;
        public TextView txt_mealCarbon1,txt_mealCarbon2,txt_mealCarbon3,txt_mealCarbon4,txt_mealCarbon5;
        public TextView txt_mealProtein1,txt_mealProtein2,txt_mealProtein3,txt_mealProtein4,txt_mealProtein5;
        public TextView txt_mealFat1,txt_mealFat2,txt_mealFat3,txt_mealFat4,txt_mealFat5;

        public ViewHolderMeal(@NonNull View itemView) {
            super(itemView);
            imgBtn_mealEdit = itemView.findViewById(R.id.imgBtn_mealEdit);
            linear_food1 = itemView.findViewById(R.id.linear_food1);
            linear_food2 = itemView.findViewById(R.id.linear_food2);
            linear_food3 = itemView.findViewById(R.id.linear_food3);
            linear_food4 = itemView.findViewById(R.id.linear_food4);
            linear_food5 = itemView.findViewById(R.id.linear_food5);
            txt_mealNum = itemView.findViewById(R.id.txt_mealNum);
            txt_mealCal= itemView.findViewById(R.id.txt_mealCal);
            txt_mealFoodName1 = itemView.findViewById(R.id.txt_mealFoodName1);
            txt_mealFoodName2 = itemView.findViewById(R.id.txt_mealFoodName2);
            txt_mealFoodName3 = itemView.findViewById(R.id.txt_mealFoodName3);
            txt_mealFoodName4 = itemView.findViewById(R.id.txt_mealFoodName4);
            txt_mealFoodName5 = itemView.findViewById(R.id.txt_mealFoodName5);
            txt_mealPer1 = itemView.findViewById(R.id.txt_mealPer1);
            txt_mealPer2 = itemView.findViewById(R.id.txt_mealPer2);
            txt_mealPer3 = itemView.findViewById(R.id.txt_mealPer3);
            txt_mealPer4 = itemView.findViewById(R.id.txt_mealPer4);
            txt_mealPer5 = itemView.findViewById(R.id.txt_mealPer5);
            txt_mealCarbon1 = itemView.findViewById(R.id.txt_mealCarbon1);
            txt_mealCarbon2 = itemView.findViewById(R.id.txt_mealCarbon2);
            txt_mealCarbon3 = itemView.findViewById(R.id.txt_mealCarbon3);
            txt_mealCarbon4 = itemView.findViewById(R.id.txt_mealCarbon4);
            txt_mealCarbon5 = itemView.findViewById(R.id.txt_mealCarbon5);
            txt_mealProtein1 = itemView.findViewById(R.id.txt_mealProtein1);
            txt_mealProtein2 = itemView.findViewById(R.id.txt_mealProtein2);
            txt_mealProtein3 = itemView.findViewById(R.id.txt_mealProtein3);
            txt_mealProtein4 = itemView.findViewById(R.id.txt_mealProtein4);
            txt_mealProtein5 = itemView.findViewById(R.id.txt_mealProtein5);
            txt_mealFat1 = itemView.findViewById(R.id.txt_mealFat1);
            txt_mealFat2 = itemView.findViewById(R.id.txt_mealFat2);
            txt_mealFat3 = itemView.findViewById(R.id.txt_mealFat3);
            txt_mealFat4 = itemView.findViewById(R.id.txt_mealFat4);
            txt_mealFat5 = itemView.findViewById(R.id.txt_mealFat5);
        }
    }
}