package com.example.dietmanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ActivityCalendar extends AppCompatActivity implements ViewAdapterCalendar.OnItemListener
{
    private TextView txt_monthYear;
    private RecyclerView calendarRecycleView;
    private LocalDate selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        InitWidets();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = LocalDate.now();
            SetMonthView();
        }
    }
    private void InitWidets()
    {
        calendarRecycleView = findViewById(R.id.calendarRecycleView);
        txt_monthYear = findViewById(R.id.txt_monthYear);
    }
    private void SetMonthView()
    {
        txt_monthYear.setText(MonthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = DaysInMonthArray(selectedDate);

        ViewAdapterCalendar viewAdapterCalendar = new ViewAdapterCalendar(getApplicationContext(),
                daysInMonth,txt_monthYear.getText().toString(), this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecycleView.setLayoutManager(layoutManager);
        calendarRecycleView.setAdapter(viewAdapterCalendar);
    }
    private ArrayList<String> DaysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysMonthArray = new ArrayList<>();
        YearMonth yearMonth = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            yearMonth = YearMonth.from(date);
            int daysInMonth = yearMonth.lengthOfMonth();
            LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
            int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
            for(int cnt = 1;cnt <= 42; cnt++)
            {
                if(cnt <= dayOfWeek || cnt > daysInMonth + dayOfWeek)
                    daysMonthArray.add("");
                else
                    daysMonthArray.add(String.valueOf(cnt - dayOfWeek));
            }
        }
        return daysMonthArray;
    }

    private String MonthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
            return date.format(formatter);
        }
        else
        {
            return "";
        }
       // return "Error";
    }
    public void PreviousMonthAction(View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = selectedDate.minusMonths(1);
            SetMonthView();
        }
    }
    public void NextMonthAction(View view)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            selectedDate = selectedDate.plusMonths(1);
            SetMonthView();
        }
    }

    @Override
    public void onItemClick(int position, String dayText)
    {
        //テキスト入力を受け付けるビューを作成。
        //レイアウトの呼び出し
        LayoutInflater factory = LayoutInflater.from(this);
        final View inputView = factory.inflate(R.layout.dialog_weight, null);
        final EditText editText = inputView.findViewById(R.id.dialog_edittext);
        new AlertDialog.Builder(ActivityCalendar.this)
                .setTitle("Input the weight")
                .setMessage(dayText + " " + txt_monthYear.getText())
                //setViewにてビューを設定します。
                .setView(inputView)
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // delete the selected Weight item on a background thread using an AsyncTask
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                String monthAndYear = "";
                                int date = 0;
                                String weight = "";
                                monthAndYear = txt_monthYear.getText().toString();
                                date = Integer.parseInt(dayText);
                                weight = editText.getText().toString();
                                EntityWeight entityWeight = new EntityWeight(monthAndYear, date, weight);
                                DBWeight db = DBWeight.getDatabase(getApplicationContext());
                                db.weightDao().insert(entityWeight);
                                return null;
                            }
                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                SetMonthView();
                            }
                        }.execute();
                    }
                })
                .show();
    }
    @Override
    public void onBackPressed(){
        finish();
    }
}
