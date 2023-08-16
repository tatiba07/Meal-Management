package com.example.dietmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

// Define an activity class for displaying and editing meal data
public class ActivityMeal extends AppCompatActivity{
    TextView txt_mealCnt;
    AutoCompleteTextView edit_foodName1, edit_foodName2,
                        edit_foodName3, edit_foodName4, edit_foodName5;
    EditText edit_per1,edit_per2,edit_per3,edit_per4,edit_per5;
    TextView txt_cal1, txt_car1, txt_pro1, txt_fat1,
             txt_cal2, txt_car2, txt_pro2, txt_fat2,
             txt_cal3, txt_car3, txt_pro3, txt_fat3,
             txt_cal4, txt_car4, txt_pro4, txt_fat4,
             txt_cal5, txt_car5, txt_pro5, txt_fat5;
    ImageButton imgBtn_food1,imgBtn_food2,imgBtn_food3,imgBtn_food4,imgBtn_food5;
    ImageButton imgBtn_foodDB1, imgBtn_foodDB2 ,imgBtn_foodDB3 ,imgBtn_foodDB4, imgBtn_foodDB5;
    Button btn_mealOk,btn_mealCancel;
    int mealCnt;
    private static final String SP_MEAlNUM = "mealCnt";
    private static final String SP_MEALFOODNAME1 = "foodName1";
    private static final String SP_MEALFOODNAME2 = "foodName2";
    private static final String SP_MEALFOODNAME3 = "foodName3";
    private static final String SP_MEALFOODNAME4 = "foodName4";
    private static final String SP_MEALFOODNAME5 = "foodName5";
    private static final String SP_MEALPER1 = "per1";
    private static final String SP_MEALPER2 = "per2";
    private static final String SP_MEALPER3 = "per3";
    private static final String SP_MEALPER4 = "per4";
    private static final String SP_MEALPER5 = "per5";
    private static final String SP_MEALCAL1 = "cal1";
    private static final String SP_MEALCAL2 = "cal2";
    private static final String SP_MEALCAL3 = "cal3";
    private static final String SP_MEALCAL4 = "cal4";
    private static final String SP_MEALCAL5 = "cal5";
    private static final String SP_MEALCAR1 = "car1";
    private static final String SP_MEALCAR2 = "car2";
    private static final String SP_MEALCAR3 = "car3";
    private static final String SP_MEALCAR4 = "car4";
    private static final String SP_MEALCAR5 = "car5";
    private static final String SP_MEALPRO1 = "pro1";
    private static final String SP_MEALPRO2 = "pro2";
    private static final String SP_MEALPRO3 = "pro3";
    private static final String SP_MEALPRO4 = "pro4";
    private static final String SP_MEALPRO5 = "pro5";
    private static final String SP_MEALFAT1 = "fat1";
    private static final String SP_MEALFAT2 = "fat2";
    private static final String SP_MEALFAT3 = "fat3";
    private static final String SP_MEALFAT4 = "fat4";
    private static final String SP_MEALFAT5 = "fat5";
    private static final String SP_MEAL = "Meal";
    AlertDialog.Builder builder;
    private static final String SP_DISPLAY = "display";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        builder = new AlertDialog.Builder(this);
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_meal);
            // light / night mode
            android.content.SharedPreferences spSetting = getSharedPreferences("Setting", MODE_PRIVATE);
            if(spSetting.getInt(SP_DISPLAY, 0) == 0)
            {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else if (spSetting.getInt(SP_DISPLAY, 0) == 1)
            {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            android.content.SharedPreferences spMeal = getSharedPreferences(SP_MEAL, MODE_PRIVATE);
            SharedPreferences.Editor editorMeal = spMeal.edit();
            int mealNum = getIntent().getIntExtra("mealNum",0); // if meal was edited
            String barcodeNum = getIntent().getStringExtra("barcodeToMeal"); // when user scans the barcode
            mealCnt = spMeal.getInt(SP_MEAlNUM,0);
            if (mealNum == 0) // increment mealCnt only when it is not meal Edit mode
            {
                mealCnt = mealCnt + 1;
            }
            txt_mealCnt = (TextView) findViewById(R.id.txt_mealCnt);
            txt_mealCnt.setText("Meal " + mealCnt);
            edit_foodName1 = findViewById(R.id.edit_foodName1);
            edit_foodName2 = findViewById(R.id.edit_foodName2);
            edit_foodName3 = findViewById(R.id.edit_foodName3);
            edit_foodName4 = findViewById(R.id.edit_foodName4);
            edit_foodName5 = findViewById(R.id.edit_foodName5);
            AutoCompleteTextView[] autoCompleteTextViews = new AutoCompleteTextView[]{
                    edit_foodName1,edit_foodName2,edit_foodName3,edit_foodName4,edit_foodName5};
            imgBtn_food1 = findViewById(R.id.imgBtn_food1);
            imgBtn_food2 = findViewById(R.id.imgBtn_food2);
            imgBtn_food3 = findViewById(R.id.imgBtn_food3);
            imgBtn_food4 = findViewById(R.id.imgBtn_food4);
            imgBtn_food5 = findViewById(R.id.imgBtn_food5);
            ImageButton[] imageButtons = new ImageButton[]{
                    imgBtn_food1, imgBtn_food2, imgBtn_food3, imgBtn_food4, imgBtn_food5
            };
            imgBtn_foodDB1 = findViewById(R.id.imgBtn_foodDB1);
            imgBtn_foodDB2 = findViewById(R.id.imgBtn_foodDB2);
            imgBtn_foodDB3 = findViewById(R.id.imgBtn_foodDB3);
            imgBtn_foodDB4 = findViewById(R.id.imgBtn_foodDB4);
            imgBtn_foodDB5 = findViewById(R.id.imgBtn_foodDB5);

            edit_per1 = findViewById(R.id.edit_per1);
            edit_per2 = findViewById(R.id.edit_per2);
            edit_per3 = findViewById(R.id.edit_per3);
            edit_per4 = findViewById(R.id.edit_per4);
            edit_per5 = findViewById(R.id.edit_per5);
            EditText[] editTexts = new EditText[]{
                    edit_per1, edit_per2, edit_per3, edit_per4, edit_per5
            };
            txt_cal1 = findViewById(R.id.txt_calorie1);
            txt_cal2 = findViewById(R.id.txt_calorie2);
            txt_cal3 = findViewById(R.id.txt_calorie3);
            txt_cal4 = findViewById(R.id.txt_calorie4);
            txt_cal5 = findViewById(R.id.txt_calorie5);
            TextView[] txt_cals = new TextView[]{
                    txt_cal1, txt_cal2, txt_cal3, txt_cal4, txt_cal5
            };
            txt_car1 = findViewById(R.id.txt_carbon1);
            txt_car2 = findViewById(R.id.txt_carbon2);
            txt_car3 = findViewById(R.id.txt_carbon3);
            txt_car4 = findViewById(R.id.txt_carbon4);
            txt_car5 = findViewById(R.id.txt_carbon5);
            TextView[] txt_cars = new TextView[]{
                    txt_car1, txt_car2, txt_car3, txt_car4, txt_car5
            };
            txt_pro1 = findViewById(R.id.txt_protein1);
            txt_pro2 = findViewById(R.id.txt_protein2);
            txt_pro3 = findViewById(R.id.txt_protein3);
            txt_pro4 = findViewById(R.id.txt_protein4);
            txt_pro5 = findViewById(R.id.txt_protein5);
            TextView[] txt_pros = new TextView[]{
                    txt_pro1, txt_pro2, txt_pro3, txt_pro4, txt_pro5
            };
            txt_fat1 = findViewById(R.id.txt_fat1);
            txt_fat2 = findViewById(R.id.txt_fat2);
            txt_fat3 = findViewById(R.id.txt_fat3);
            txt_fat4 = findViewById(R.id.txt_fat4);
            txt_fat5 = findViewById(R.id.txt_fat5);
            TextView[] txt_fats = new TextView[]{
                    txt_fat1, txt_fat2, txt_fat3, txt_fat4, txt_fat5
            };
            String[] strFoodNames = new String[]{
                    SP_MEALFOODNAME1,SP_MEALFOODNAME2,SP_MEALFOODNAME3,SP_MEALFOODNAME4,SP_MEALFOODNAME5
            };
            String[] strPers = new String[]{
                    SP_MEALPER1,SP_MEALPER2,SP_MEALPER3,SP_MEALPER4,SP_MEALPER5
            };
            String[] strCars = new String[]{
                    SP_MEALCAR1,SP_MEALCAR2,SP_MEALCAR3,SP_MEALCAR4,SP_MEALCAR5
            };
            String[] strCals = new String[]{
                    SP_MEALCAL1,SP_MEALCAL2,SP_MEALCAL3,SP_MEALCAL4,SP_MEALCAL5
            };
            String[] strPros = new String[]{
                    SP_MEALPRO1,SP_MEALPRO2,SP_MEALPRO3,SP_MEALPRO4,SP_MEALPRO5
            };
            String[] strFats = new String[]{
                    SP_MEALFAT1,SP_MEALFAT2,SP_MEALFAT3,SP_MEALFAT4,SP_MEALFAT5
            };
            btn_mealOk = findViewById(R.id.btn_mealOk);
            btn_mealCancel = findViewById(R.id.btn_mealCancel);
            //btn_mealOk.setOnClickListener((View.OnClickListener) this);
            DBFood db = DBFood.getDatabase(getApplicationContext());
            List<String> allFoods = db.foodDao().getFoodNames(); // get all food name data in the DB
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, allFoods);
            Boolean[] imgBtnPressed = new Boolean[]{false,false,false,false,false};
            // imgBtn_food(1 ~ 5) pressed (function:loading)
            for (int cnt = 0;cnt<autoCompleteTextViews.length;cnt++)
            {
                autoCompleteTextViews[cnt].setAdapter(adapter);
                int finalCnt = cnt;
                imageButtons[cnt].setOnClickListener(view -> {
                    EntityFood entityFood1 = db.foodDao().getByFoodName(String.valueOf(autoCompleteTextViews[finalCnt].getText()));
                    int editPer = 1;
                    if(autoCompleteTextViews[finalCnt].getText().length() != 0 &&
                            editTexts[finalCnt].getText().length() != 0)
                    {
                        editPer = Integer.parseInt(String.valueOf(editTexts[finalCnt].getText()));
                    }
                    else
                    {
                        builder.setTitle("Error");
                        builder.setMessage("Enter the Food name & Per");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                    String[] txtResults = Result(entityFood1, editPer);
                    txt_cals[finalCnt].setText(txtResults[0]);
                    txt_cars[finalCnt].setText(txtResults[1]);
                    txt_pros[finalCnt].setText(txtResults[2]);
                    txt_fats[finalCnt].setText(txtResults[3]);
                    imgBtnPressed[finalCnt] = true;
                });
            }
            // if user scans the barcode
            {
                if(barcodeNum != null)
                {
                    String strMealNum = barcodeNum.substring(0,1);
                    String strFoodName = barcodeNum.substring(1);
                    if(strFoodName != null)
                    {
                        SharedPreferences spMealNum = getSharedPreferences(SP_MEAL + String.valueOf(mealCnt), MODE_PRIVATE);
                        SharedPreferences.Editor editorMealNum = spMealNum.edit();
                        switch (strMealNum)
                        {
                            case "1":
                                edit_foodName1.setText(strFoodName);
                                editorMealNum.putString(strFoodNames[0],String.valueOf(autoCompleteTextViews[0].getText()));
                                editorMealNum.apply();
                                break;
                            case "2":
                                edit_foodName2.setText(strFoodName);
                                editorMealNum.putString(strFoodNames[1],String.valueOf(autoCompleteTextViews[1].getText()));
                                editorMealNum.apply();
                                break;
                            case "3":
                                edit_foodName3.setText(strFoodName);
                                editorMealNum.putString(strFoodNames[2],String.valueOf(autoCompleteTextViews[2].getText()));
                                editorMealNum.apply();
                                break;
                            case "4":
                                edit_foodName4.setText(strFoodName);
                                editorMealNum.putString(strFoodNames[3],String.valueOf(autoCompleteTextViews[3].getText()));
                                editorMealNum.apply();
                                break;
                            case "5":
                                edit_foodName5.setText(strFoodName);
                                editorMealNum.putString(strFoodNames[4],String.valueOf(autoCompleteTextViews[4].getText()));
                                editorMealNum.apply();
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            int finalMealNum = mealNum;
            imgBtn_foodDB1.setOnClickListener(view -> {
                // go back to activity_barcode.xml
                SaveMealData(finalMealNum, imgBtnPressed, strFoodNames, autoCompleteTextViews, strPers,
                        editTexts, strCals, txt_cals, strCars, txt_cars, strPros, txt_pros, strFats,
                        txt_fats, editorMeal);
                Intent intent = new Intent(getApplication(), ActivityBarcode.class);
                intent.putExtra("barcode", "1");
                intent.putExtra("mealNum", mealNum);
                startActivity(intent);
            });
            imgBtn_foodDB2.setOnClickListener(view -> {
                // go back to activity_barcode.xml
                Intent intent = new Intent(getApplication(), ActivityBarcode.class);
                intent.putExtra("barcode", "2");
                intent.putExtra("mealNum", mealNum);
                startActivity(intent);
            });
            imgBtn_foodDB3.setOnClickListener(view -> {
                // go back to activity_barcode.xml
                Intent intent = new Intent(getApplication(), ActivityBarcode.class);
                intent.putExtra("barcode", "3");
                intent.putExtra("mealNum", mealNum);
                startActivity(intent);
            });
            imgBtn_foodDB4.setOnClickListener(view -> {
                // go back to activity_barcode.xml
                Intent intent = new Intent(getApplication(), ActivityBarcode.class);
                intent.putExtra("barcode", "4");
                intent.putExtra("mealNum", mealNum);
                startActivity(intent);
            });
            imgBtn_foodDB5.setOnClickListener(view -> {
                // go back to activity_barcode.xml
                Intent intent = new Intent(getApplication(), ActivityBarcode.class);
                intent.putExtra("barcode", "5");
                intent.putExtra("mealNum", mealNum);
                startActivity(intent);
            });
            // button OK pressed
            btn_mealOk.setOnClickListener(view -> {
                SaveMealData(finalMealNum, imgBtnPressed, strFoodNames, autoCompleteTextViews, strPers,
                        editTexts, strCals, txt_cals, strCars, txt_cars, strPros, txt_pros, strFats,
                        txt_fats, editorMeal);
            });
            // button cancel pressed
            btn_mealCancel.setOnClickListener(view -> {
                // go back to activity_main.xml
                Intent intent = new Intent(getApplication(), ActivityMain.class);
                startActivity(intent);
            });
            // if it is meal edit mode
            if(mealNum != 0)
            {
                SharedPreferences spMealNum =
                        getSharedPreferences(SP_MEAL + String.valueOf(mealNum), MODE_PRIVATE);
                for(int cnt = 0; cnt < strFoodNames.length;cnt++) // set text to adapter
                {
                    if(spMealNum.getString(strFoodNames[cnt], "0").length() > 1)
                    {
                        //mealCnt = mealNum;
                        txt_mealCnt.setText("Meal " + mealNum);
                        autoCompleteTextViews[cnt].setText(spMealNum.getString(strFoodNames[cnt], "0"));
                        editTexts[cnt].setText(spMealNum.getString(strPers[cnt], "0"));
                        txt_cals[cnt].setText(spMealNum.getString(strCals[cnt], "0"));
                        txt_cars[cnt].setText(spMealNum.getString(strCars[cnt], "0"));
                        txt_pros[cnt].setText(spMealNum.getString(strPros[cnt], "0"));
                        txt_fats[cnt].setText(spMealNum.getString(strFats[cnt], "0"));
                    }
                }
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
    // calculate
    private String[] Result(EntityFood entityFood, int editPer)
    {
        String db_foodName = entityFood.getFoodName();
        int db_per = entityFood.getPer();
        double rate = Double.parseDouble(String.valueOf(editPer)) / db_per;
        double txtCalorie = entityFood.getCalorie() * rate;
        double txtCarbon = entityFood.getCarbon() * rate;
        double txtProtein = entityFood.getProtein() * rate;
        double txtFat = entityFood.getFat() * rate;
        String[] txtResults = new String[]{String.valueOf(txtCalorie),String.valueOf(txtCarbon),
                String.valueOf(txtProtein),String.valueOf(txtFat)};
        return txtResults;
    }
    private void SaveMealData(int finalMealNum, Boolean[] imgBtnPressed, String[] strFoodNames,
                              AutoCompleteTextView[] autoCompleteTextViews, String[] strPers,
                              EditText[] editTexts, String[] strCals, TextView[] txt_cals,
                              String[] strCars, TextView[] txt_cars, String[] strPros, TextView[] txt_pros,
                              String[] strFats, TextView[] txt_fats, SharedPreferences.Editor editorMeal)
    {
        try {
            SharedPreferences spMealNum = getSharedPreferences(SP_MEAL + String.valueOf(mealCnt), MODE_PRIVATE);
            if(finalMealNum != 0) // meal edit mode
            {
                spMealNum = getSharedPreferences(SP_MEAL + String.valueOf(finalMealNum), MODE_PRIVATE);
            }
            SharedPreferences.Editor editorMealNum = spMealNum.edit();
            double totalCal = 0;
            for(int cnt = 0; cnt < 5;cnt++)
            {
                if(imgBtnPressed[cnt] == true) // save data only the food that load button pressed
                {
                    editorMealNum.putString(strFoodNames[cnt],String.valueOf(autoCompleteTextViews[cnt].getText()));
                    editorMealNum.apply();
                    editorMealNum.putString(strPers[cnt],String.valueOf(editTexts[cnt].getText()));
                    editorMealNum.apply();
                    editorMealNum.putString(strCals[cnt],String.valueOf(txt_cals[cnt].getText()));
                    editorMealNum.apply();
                    editorMealNum.putString(strCars[cnt],String.valueOf(txt_cars[cnt].getText()));
                    editorMealNum.apply();
                    editorMealNum.putString(strPros[cnt],String.valueOf(txt_pros[cnt].getText()));
                    editorMealNum.apply();
                    editorMealNum.putString(strFats[cnt],String.valueOf(txt_fats[cnt].getText()));
                    editorMealNum.apply();
                }
            }
            // no data registered. No process
            if(imgBtnPressed[0] == false && imgBtnPressed[1] == false && imgBtnPressed[2] == false &&
                    imgBtnPressed[3] == false && imgBtnPressed[4] == false)
            {
                return;
            }
            editorMeal.putInt(SP_MEAlNUM,mealCnt);
            editorMeal.apply();
            // go back to activity_main.xml
            Intent intent = new Intent(getApplication(), ActivityMain.class);
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