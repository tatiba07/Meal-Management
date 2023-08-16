package com.example.dietmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Locale;
// Define an activity class for  editing user profile
public class ActivityProfile extends AppCompatActivity implements View.OnClickListener {
    private static final String SP_SEX = "sex";
    private static final String SP_AGE = "age";
    private static final String SP_HEIGHT = "height";
    private static final String SP_WEIGHT = "weight";
    private static final String SP_WORKOUTLEVEL = "workoutlevel";
    private static final String SP_MEALMODE = "mealmode";
    private static final String SP_WEIGHTCHANGE = "weightchange";
    private static final String SP_TDEE = "tdee";
    private static final String SP_CARBON = "carbon";
    private static final String SP_PROTEIN = "protein";
    private static final String SP_FAT = "fat";
    private int intAge = 0;
    private float floatHeight = 0;
    private float floatWeight = 0;
    private float floatWeightChange = 0;
    LinearLayout linear_setting;
    EditText edit_age;
    EditText edit_weight;
    EditText edit_height;
    EditText edit_weightChange;
    TextView txt_calorie;
    TextView txt_carbon;
    TextView txt_protein;
    TextView txt_fat;
    AlertDialog.Builder builder;
    private InputMethodManager mInputMethodManager;
    private static final String SP_DISPLAY = "display";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);
            builder = new AlertDialog.Builder(this);
//            // light / night mode
//            android.content.SharedPreferences spSetting = getSharedPreferences("Setting", MODE_PRIVATE);
//            if(spSetting.getInt(SP_DISPLAY, 0) == 0)
//            {
//                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            } else if (spSetting.getInt(SP_DISPLAY, 0) == 1)
//            {
//                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            }
            android.content.SharedPreferences sp = getSharedPreferences("DataTable", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            // Spinner
            {
                Spinner spinnerSex = findViewById(R.id.spinner_sex);
                Spinner spinnerWorkoutLevel = findViewById(R.id.spinner_workoutLevel);
                Spinner spinnerMealMode = findViewById(R.id.spinner_mealMode);
                String male = "Male", female = "Female";
                String lv1 = "Sedentary ",lv2 = "Lightly active",lv3 = "Moderately active",
                        lv4 = "Very active", lv5 = "Super active";
                String bulkUp = "Bulk Up", lowCar = "Low Carbo Diet", LowFat = "Low Fat Diet", maintain = "Maintain";
                String[] listSex = new String[]{male, female};
                String[] listWorkoutLevel = new String[]{lv1, lv2, lv3, lv4, lv5};
                String[] listMealMode = new String[]{bulkUp, lowCar, LowFat, maintain};
                String[] strFinals = new String[]{SP_SEX,SP_WORKOUTLEVEL,SP_MEALMODE};
                Spinner[] spinners = new Spinner[]{spinnerSex, spinnerWorkoutLevel,spinnerMealMode};
                String[][] strings = new String[][]{listSex,listWorkoutLevel,listMealMode};
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
                            editor.putInt(strFinals[finalCnt], idx);
                            editor.apply();
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
            // Edit text
            {
                linear_setting = (LinearLayout)findViewById(R.id.linear_setting);
                mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                edit_age = (EditText) findViewById(R.id.edit_age);
                edit_height = (EditText) findViewById(R.id.edit_height);
                edit_weight = (EditText) findViewById(R.id.edit_weight);
                edit_weightChange = (EditText) findViewById(R.id.edit_weightChange);
                SetEditText(sp);
            }
            // TextView
            {
                txt_calorie = (TextView) findViewById(R.id.txt_resultCalorie);
                txt_carbon = (TextView) findViewById(R.id.txt_resultCarbon);
                txt_protein = (TextView) findViewById(R.id.txt_resultProtein);
                txt_fat = (TextView) findViewById(R.id.txt_resultFat);
                SetTextView(sp);
            }
            // Image button
            ImageButton btnLoad = (ImageButton)findViewById(R.id.imgBtn_result);
            btnLoad.setOnClickListener((View.OnClickListener) this);
        }
        catch (Exception e)
        {
            //builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage(String.valueOf(e));
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }
    }
    // set text to EditText
    private void SetEditText(android.content.SharedPreferences sp)
    {
        if (sp.getInt(SP_AGE, 0) > 0) // if editText is entered
        {
            edit_age.setText(String.valueOf(sp.getInt(SP_AGE, 0)));
        }
        if (sp.getFloat(SP_HEIGHT, 0) > 0) // if editText is entered
        {
            edit_height.setText(String.valueOf(sp.getFloat(SP_HEIGHT, 0)));
        }
        if (sp.getFloat(SP_WEIGHT, 0) > 0) // if editText is entered
        {
            edit_weight.setText(String.valueOf(sp.getFloat(SP_WEIGHT, 0)));
        }
        if (sp.getFloat(SP_WEIGHTCHANGE, 0) > 0) // if editText is entered
        {
            edit_weightChange.setText(String.valueOf(sp.getFloat(SP_WEIGHTCHANGE, 0)));
        }
    }
    // set text to TextView
    private void SetTextView(android.content.SharedPreferences sp)
    {
        TextView[] textViews = new TextView[]{
                txt_calorie,txt_carbon,txt_protein,txt_fat
        };
        String[] strings = new String[]{
                SP_TDEE, SP_CARBON, SP_PROTEIN, SP_FAT
        };
        for(int cnt = 0;cnt < textViews.length;cnt++) // set text to TextView
        {
            if (sp.getString(strings[cnt], "").length() > 0) {
                String str = "";
                if (cnt == 0) {
                    str = " cal";
                } else {
                    str = " g";
                }
                textViews[cnt].setText(sp.getString(strings[cnt], "") + str);
            } else {
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Error");
                builder.setMessage("Calculation Failure");
                AlertDialog dialog = builder.create();
                dialog.show();
                return;
            }
        }
    }
    // calculate the result
    private void Calculate()
    {
        // call database
        android.content.SharedPreferences sp = getSharedPreferences("DataTable", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        // set data from database
        int sex = sp.getInt(SP_SEX,0); // 0:Male, 1:Female
        int age = sp.getInt(SP_AGE,0);
        double height = sp.getFloat(SP_HEIGHT,0);
        double weight = sp.getFloat(SP_WEIGHT,0);
        int workoutLevel = sp.getInt(SP_WORKOUTLEVEL,0);
        int mealMode = sp.getInt(SP_MEALMODE,0);
        double weightChange = sp.getFloat(SP_WEIGHTCHANGE,0);
        double BMR = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // checking purpose & weight change
        if(weight > weightChange && mealMode == 0)
        {
            builder.setTitle("Meal Mode : Bulk up");
            builder.setMessage("Weight goal must be higher than weight.");
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }
        else if(weight < weightChange && (mealMode == 1 || mealMode == 2))
        {
            builder.setTitle("Meal Mode : Diet");
            builder.setMessage("Weight goal must be lower than weight.");
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }
        else if(weight != weightChange && mealMode == 3)
        {
            builder.setTitle("Meal Mode : Maintain");
            builder.setMessage("Goal weight must be same as weight.");
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }
        // calculate BMR
        if(sex == 0) // male
        {
            BMR = 88.362 + (weight * 13.397) + (height * 4.799) - (age * 5.677);
        }
        else if(sex == 1) // female
        {
            BMR = 447.593 + (weight * 9.247) + (height * 3.098) - (age * 4.330);
        }
        // calculate TDEE
        double TDEE = 0;
        double carbon = 0;
        double protein = 0;
        double fat = 0;
        switch (workoutLevel)
        {
            case 0: // Sedentary
                TDEE = BMR * 1.2;
                break;
            case 1: // Lightly active
                TDEE = BMR * 1.375;
                break;
            case 2: // moderately active
                TDEE = BMR * 1.55;
                break;
            case 3: // very active
                TDEE = BMR * 1.725;
                break;
            case 4: // super active
                TDEE = BMR * 1.9;
                break;
            default:
                builder.setTitle("Error");
                builder.setMessage("calculate fail");
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
        }
        if((weight - weightChange) > 0) // loosing weight
        {
            if((weight - weightChange) < 6)
            {
                TDEE = TDEE - 250 * (weight-weightChange);
            }
            else // danger weight change
            {
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Error");
                builder.setMessage("Rapid weight changes are dangerous.");
                AlertDialog dialog = builder.create();
                dialog.show();
                return;
            }
        }
        else // bulk up || maintain
        {
            if((weight - weightChange) < -6) // danger weight change
            {
                builder.setTitle("Error");
                builder.setMessage("Rapid weight changes are dangerous.");
                AlertDialog dialog = builder.create();
                dialog.show();
                return;
            }
            else
            {
                TDEE = TDEE + 250 * (weightChange - weight);
            }
        }
        switch (mealMode)
        {
            case 0: // bulk up
            case 3: // maintain
                protein = (TDEE * 0.3) / 4;
                carbon = (TDEE * 0.5) / 4;
                fat = (TDEE * 0.2) / 9;
                break;
            case 1: // low carbon diet
                protein = (TDEE * 0.3) / 4;
                carbon = (TDEE * 0.1) / 4;
                fat = (TDEE * 0.6) / 9;
                break;
            case 2: // low fat diet
                protein = (TDEE * 0.3) / 4;
                carbon = (TDEE * 0.55) / 4;
                fat = (TDEE * 0.15) / 9;
                break;
            default:
                builder.setTitle("Error");
                builder.setMessage("calculate fail");
                AlertDialog dialog = builder.create();
                dialog.show();
                return;
        }
        // format each number
        String strCal = String.format(Locale.US,"%.1f",TDEE);
        String strCar = String.format(Locale.US,"%.1f",carbon);
        String strPro = String.format(Locale.US,"%.1f",protein);
        String strFat = String.format(Locale.US,"%.1f",fat);
        txt_calorie.setText(strCal + " cal");
        txt_carbon.setText(strCar + " g");
        txt_protein.setText(strPro + " g");
        txt_fat.setText(strFat + " g");
        // save each data
        editor.putString(SP_TDEE, strCal);
        editor.apply();
        editor.putString(SP_CARBON, strCar);
        editor.apply();
        editor.putString(SP_PROTEIN, strPro);
        editor.apply();
        editor.putString(SP_FAT, strFat);
        editor.apply();
    }
    // button clicked method
    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(getApplication(), ActivityProfile.class);
        try
        {
            android.content.SharedPreferences sp = getSharedPreferences("DataTable", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            if(v.getId() == R.id.imgBtn_result)
            {
                if(edit_age.getText().length() != 0) // if editText is entered
                {
                    intAge = Integer.parseInt(edit_age.getText().toString());
                }
                else
                {
                    builder.setTitle("Error");
                    builder.setMessage("Enter Age");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }
                editor.putInt(SP_AGE,intAge);
                editor.apply();

                if(edit_height.getText().length() != 0) // if editText is entered
                {
                    floatHeight = Float.parseFloat(edit_height.getText().toString());
                }
                else
                {
                    builder.setTitle("Error");
                    builder.setMessage("Enter Height");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }
                editor.putFloat(SP_HEIGHT,floatHeight);
                editor.apply();
                if(edit_weight.getText().length() != 0) // if editText is entered
                {
                    floatWeight = Float.parseFloat(edit_weight.getText().toString());
                }
                else
                {
                    builder.setTitle("Error");
                    builder.setMessage("Enter Weight");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }
                editor.putFloat(SP_WEIGHT,floatWeight);
                editor.apply();
                if(edit_weightChange.getText().length() != 0) // if editText is entered
                {
                    floatWeightChange = Float.parseFloat(edit_weightChange.getText().toString());
                }
                else
                {
                    builder.setTitle("Error");
                    builder.setMessage("Enter Goal Weight");
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }
                editor.putFloat(SP_WEIGHTCHANGE,floatWeightChange);
                editor.apply();
                Calculate();
            }
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
    // finish key board
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //hide key board
        mInputMethodManager.hideSoftInputFromWindow(linear_setting.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        // change focus to background
        linear_setting.requestFocus();
        return super.dispatchTouchEvent(ev);
    }
    // default back button pressed
    @Override
    public void onBackPressed(){
        finish();
    }
}