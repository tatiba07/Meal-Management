package com.example.dietmanagement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.graphics.Color;
import android.widget.TextView;
public class ActivitySetting extends AppCompatActivity  implements View.OnClickListener{

    AlertDialog.Builder builder;
    Button btn_back;
    Spinner spinner_display;
    private static final String SP_DISPLAY = "display";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_setting);
            builder = new AlertDialog.Builder(this);
            android.content.SharedPreferences sp = getSharedPreferences("Setting", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            // light / night mode
            android.content.SharedPreferences spSetting = getSharedPreferences("Setting", MODE_PRIVATE);
            if(spSetting.getInt(SP_DISPLAY, 0) == 0)
            {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else if (spSetting.getInt(SP_DISPLAY, 0) == 1)
            {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            btn_back = findViewById(R.id.btn_back);
            btn_back.setOnClickListener((View.OnClickListener) this);
            spinner_display = findViewById(R.id.spinner_display);
            String light = "Light Mode", night = "Night Mode";
            String[] listDisplay = new String[]{light, night};
            Spinner[] spinners = new Spinner[]{spinner_display};
            String[] strFinals = new String[]{SP_DISPLAY};
            String[][] strings = new String[][]{listDisplay};
            for(int cnt = 0;cnt < strings.length; cnt++)
            {
                ArrayAdapter<String> arry = new ArrayAdapter<>(
                        this, android.R.layout.simple_spinner_item, strings[cnt]);
                arry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinners[cnt].setAdapter(arry);
                if(!(sp.getInt(strFinals[cnt],0) > 0))
                {
                    editor.putInt(strFinals[cnt],0);
                    editor.apply();
                }
                spinners[cnt].setSelection(sp.getInt(strFinals[cnt],0),true);
                int finalCnt = cnt;
                spinners[cnt].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        int idx = spinners[finalCnt].getSelectedItemPosition();
                        if (idx == 0)
                        {
                            getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        }
                        else if (idx == 1)
                        {
                            getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        }
                        editor.putInt(strFinals[finalCnt], idx);
                        editor.apply();
                        recreate();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                // Set text color to android:textColor="@color/normalText"
                TextView spinnerTextView = spinners[cnt].findViewById(android.R.id.text1);
                spinnerTextView.setTextColor(getResources().getColor(R.color.normalText));

                // Set background color to android:background="@color/background"
                spinners[cnt].setPopupBackgroundResource(R.color.background2);
            }
        }
        catch (Exception e)
        {
            builder.setTitle("Error");
            builder.setMessage(String.valueOf(e));
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }
    }
    // button clicked method
    @Override
    public void onClick(View v)
    {
        try
        {
            Intent intent = new Intent(getApplication(), ActivityMain.class);
            switch (v.getId())
            {
                case R.id.btn_mealAdd:
                    intent = new Intent(getApplication(), ActivityMeal.class);
                    break;
                case R.id.btn_profile:
                    intent = new Intent(getApplication(), ActivityProfile.class);
                    break;
                case R.id.btn_foodDB:
                    intent = new Intent(getApplication(), ActivityFoodDB.class);
                    break;
                case R.id.btn_setting:
                    intent = new Intent(getApplication(), ActivitySetting.class);
                    break;
            }
            startActivity(intent);
        }
        catch (Exception e)
        {
            builder.setTitle("Error");
            builder.setMessage(String.valueOf(e));
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    @Override
    public void onBackPressed(){
        finish();
    }
}
