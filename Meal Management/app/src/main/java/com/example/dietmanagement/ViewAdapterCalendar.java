package com.example.dietmanagement;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// define the Adapter for the RecyclerView
public class ViewAdapterCalendar extends RecyclerView.Adapter<ViewAdapterCalendar.ViewHolderCalendar> {

    private Context context;
    public final ArrayList<String> arryDayOfMonth;
    private String monthYear;
    public final OnItemListener onItemListener;


    public ViewAdapterCalendar(Context context,ArrayList<String> arryDayOfMonth,
                               String monthYear, OnItemListener onItemListener)
    {
        this.context = context;
        this.monthYear = monthYear;
        this.arryDayOfMonth = arryDayOfMonth;
        this.onItemListener = onItemListener;
    }
    @Override
    public ViewHolderCalendar onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycle_item_calendar,parent,false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int)(parent.getHeight() * 0.14);
        return new ViewHolderCalendar(view, onItemListener);
    }
    // sets the contents when scrolling through the list
    @Override
    public void onBindViewHolder(ViewHolderCalendar holder, int position) {
        DBWeight db = DBWeight.getDatabase(context);
        List<EntityWeight> entityWeight = db.weightDao().getByWeight(monthYear);
        // set Text
        holder.txt_dayOfMonth.setText(arryDayOfMonth.get(position));
        if(holder.txt_dayOfMonth.getText().length() > 0)
        {
            // display the weight
            for(int cnt = 0;cnt < entityWeight.size(); cnt++)
            {
                if (holder.txt_dayOfMonth.getText().toString().equals(Integer.toString(entityWeight.get(cnt).getDate())))
                {
                    holder.txt_dayData.setText((entityWeight.get(cnt).getWeight()) + " kg");
                }
            }
            // highlight the textview of today
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM YYYY");
            String formattedMonth = dateFormat.format(calendar.getTime());
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if(formattedMonth.equals(monthYear) &&
                    String.valueOf(dayOfMonth).equals(holder.txt_dayOfMonth.getText().toString())
                )
                {
                    holder.linear_calender.setBackgroundResource(R.drawable.borderline_all);
                }
            }
        }
        else
        {
        }
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
        return arryDayOfMonth.size();
    }
    public class ViewHolderCalendar extends RecyclerView.ViewHolder implements View.OnClickListener{

        public Double[] localDataSet;
        public LinearLayout linear_calender;
        public TextView txt_monthYear;
        public TextView txt_dayOfMonth;
        public TextView txt_dayData;
        private final ViewAdapterCalendar.OnItemListener onItemListener;


        public ViewHolderCalendar(@NonNull View itemView, ViewAdapterCalendar.OnItemListener onItemListener) {
            super(itemView);
            linear_calender = itemView.findViewById(R.id.linear_calender);
            txt_dayOfMonth = itemView.findViewById(R.id.txt_dayOfMonth);
            txt_dayData = itemView.findViewById(R.id.txt_dayData);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view)
        {
            onItemListener.onItemClick(getAdapterPosition(), (String)txt_dayOfMonth.getText());
        }
    }
    public interface OnItemListener
    {
        void onItemClick(int position, String dayText);
    }

}