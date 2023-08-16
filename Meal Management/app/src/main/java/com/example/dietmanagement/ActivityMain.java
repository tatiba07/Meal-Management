package com.example.dietmanagement;

import static androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dietmanagement.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

// Define an activity class for displaying main display
public class ActivityMain extends AppCompatActivity implements View.OnClickListener{
    private ActivityMainBinding binding;
    private static final String SP_DATE = "date";
    private static final String SP_MEAL = "Meal";

    private static final String SP_TDEE = "tdee";
    private static final String SP_MEALMODE = "mealmode";
    private static final String SP_CARBON = "carbon";
    private static final String SP_PROTEIN = "protein";
    private static final String SP_FAT = "fat";
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
    private static final String SP_DISPLAY = "display";
    Toolbar toolbar;
    private Button btn_mealAdd,btnProfile,btnDatabase,btn_calendar, btnSetting;
    TextView txt_calLeft, txt_carLeft, txt_proLeft, txt_fatLeft;
    TextView txt_calorieNum;
    TextView txt_carbonNum;
    TextView txt_proteinNum;
    TextView txt_fatNum;
    ProgressBar progress_calorie;
    ProgressBar progress_carbon;
    ProgressBar progress_protein;
    ProgressBar progress_fat;
    RecyclerView recyclerView;
    ItemClickListener itemClickListener;
    ViewAdapterMeal viewAdapterMeal;
    private List<List<String>> allFoods;
    private List<String>  db_allFoods;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        builder = new AlertDialog.Builder(this);
        try
        {
            super.onCreate(savedInstanceState);
            android.content.SharedPreferences sp = getSharedPreferences("DataTable", MODE_PRIVATE);
            MealRenew(sp); // reset data when the date is changed
            DBFood db = DBFood.getDatabase(getApplicationContext());
            // Tool bar setting
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(binding.appBarMain.toolbar);
            DrawerLayout drawer = binding.drawerLayout;
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            // light / night mode
            android.content.SharedPreferences spSetting = getSharedPreferences("Setting", MODE_PRIVATE);
            if(spSetting.getInt(SP_DISPLAY, 0) == 0)
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else if (spSetting.getInt(SP_DISPLAY, 0) == 1)
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            // TextView & progress bar
            {
                txt_calLeft = (TextView)findViewById(R.id.txt_calLeft);
                txt_carLeft = (TextView)findViewById(R.id.txt_carLeft);
                txt_proLeft = (TextView)findViewById(R.id.txt_proLeft);
                txt_fatLeft = (TextView)findViewById(R.id.txt_fatLeft);
                txt_calorieNum = (TextView) findViewById(R.id.txt_calorieNum);
                txt_carbonNum = (TextView) findViewById(R.id.txt_carbonNum);
                txt_proteinNum = (TextView) findViewById(R.id.txt_proteinNum);
                txt_fatNum = (TextView) findViewById(R.id.txt_fatNum);
                progress_calorie = (ProgressBar) findViewById(R.id.progress_calorie);
                progress_carbon = (ProgressBar) findViewById(R.id.progress_carbon);
                progress_protein = (ProgressBar) findViewById(R.id.progress_protein);
                progress_fat = (ProgressBar) findViewById(R.id.progress_fat);
            }
            // button
            {
                // Move to Setting page
                btnProfile = (Button)findViewById(R.id.btn_profile);
                btnProfile.setOnClickListener((View.OnClickListener) this);
                // Move to FoodDB page
                btnDatabase = (Button)findViewById(R.id.btn_foodDB);
                btnDatabase.setOnClickListener((View.OnClickListener) this);
                // Move to Meal page
                btn_mealAdd = (Button)findViewById(R.id.btn_mealAdd);
                btn_mealAdd.setOnClickListener((View.OnClickListener) this);
                // Move to Setting page
                btnSetting = (Button)findViewById(R.id.btn_setting);
                btnSetting.setOnClickListener((View.OnClickListener) this);
                // Move to FoodDB page
                btn_calendar = (Button)findViewById(R.id.btn_calendar);
                btn_calendar.setOnClickListener((View.OnClickListener) this);
            }
            // Meal recycleAdapter
            recyclerView = (RecyclerView) findViewById(R.id.recycle_meal);
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
    protected void onResume() {
        try
        {
            super.onResume();
            double totalCal = 0.0, totalCar = 0.0, totalPro = 0.0, totalFat = 0.0;
            String tmpCal, tmpCar, tmpPro, tmpFat;
            // database calling
            SharedPreferences sp = getSharedPreferences("DataTable", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            SharedPreferences spMeal = getSharedPreferences(SP_MEAL, MODE_PRIVATE);
            SharedPreferences.Editor editorMeal = spMeal.edit();
            DBFood db = DBFood.getDatabase(getApplicationContext());
            db_allFoods = db.foodDao().getFoodNames();
            String.valueOf(sp.getInt(SP_MEALMODE,0));
            // tool bar set title
            toolBarTitle(sp);
            // ViewAdapterMeal
            itemClickListener = new ItemClickListener() {
                @Override
                @NonNull
                public void onClick(String s) {
                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            viewAdapterMeal.notifyDataSetChanged();
                        }
                    });
                }
            };
            int mealCnt = spMeal.getInt(SP_MEAlNUM,0);
            if(mealCnt > 0) // if user has more than one meal, display adapter
            {
                allFoods = new ArrayList<>();
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
                for(int cnt = 1; cnt <= mealCnt;cnt++) // set text to adapter
                {
                    double addingCal = 0.0;
                    SharedPreferences spMealNum =
                            getSharedPreferences(SP_MEAL + String.valueOf(cnt), MODE_PRIVATE);
                    List<String> tmp = new ArrayList<>();
                    // add Meal Number to adapter
                    tmp.add("Meal " + String.valueOf(cnt));
                    // add Calorie to adapter & calculate total Calorie
                    for(int cnt2 = 0;cnt2 < strFoodNames.length;cnt2++)
                    {
                        tmpCal = spMealNum.getString(strCals[cnt2],"0");
                        addingCal = addingCal + Double.parseDouble(tmpCal);
                        totalCal = totalCal + Double.parseDouble(tmpCal);
                    }
                    tmp.add(String.format(Locale.US,"%,.1f",addingCal));
                    // add Food name, Per, Carbon, Fat to adapter
                    for(int cnt2 = 0;cnt2 < strFoodNames.length;cnt2++)
                    {
                        // add Food Name to adapter
                        tmp.add(spMealNum.getString(strFoodNames[cnt2],""));
                        // add Per to adapter
                        tmp.add(spMealNum.getString(strPers[cnt2],""));
                        // add Carbon to adapter & calculate total Carbon
                        tmpCar = spMealNum.getString(strCars[cnt2],"0");
                        totalCar = totalCar + Double.parseDouble(tmpCar); // for progress bar
                        tmp.add(String.format(Locale.US,"%,.1f", Double.parseDouble(tmpCar)));
                        // add Protein to adapter & calculate total Protein
                        tmpPro = spMealNum.getString(strPros[cnt2],"0");
                        totalPro = totalPro + Double.parseDouble(tmpPro); // for progress bar
                        tmp.add(String.format(Locale.US,"%.1f",Double.parseDouble(tmpPro)));
                        // add Fat to adapter & calculate total Fat
                        tmpFat = spMealNum.getString(strFats[cnt2],"0");
                        totalFat = totalFat + Double.parseDouble(tmpFat); // for progress bar
                        tmp.add(String.format(Locale.US,"%.1f",Double.parseDouble(tmpFat)));
                    }
                    allFoods.add(tmp);
                }
                if(allFoods.size() > 0)
                {
                    viewAdapterMeal = new ViewAdapterMeal(this, allFoods, itemClickListener);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ActivityMain.this));
                    recyclerView.setAdapter(viewAdapterMeal);
                }
            }
            // Progress bar & Text
            if (sp.getString(SP_TDEE, "").length() > 0) // null check
            {
                double doublePgCalorie = Double.parseDouble(sp.getString(SP_TDEE, ""));
                int intPgCalorie = (int) Math.round(doublePgCalorie);
                if(doublePgCalorie >= totalCal)
                {
                    txt_calorieNum.setText(String.format(Locale.US,"%.1f",doublePgCalorie - totalCal));
                }
                else
                {
                    txt_calorieNum.setText(String.format(Locale.US,"%.1f",totalCal - doublePgCalorie));
                    txt_calLeft.setText("Calories Over");
                    progress_calorie.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_circle_over));
                }
                progress_calorie.setMax(intPgCalorie);
                progress_calorie.setProgress((int)Math.round(totalCal));
            }
            String[] strSPs = new String[]{SP_CARBON, SP_PROTEIN, SP_FAT};
            double doublrPgCarbon = 0,doublePgProtein = 0,doublePgFat = 0;
            Double[] doubles = new Double[]{doublrPgCarbon,doublePgProtein, doublePgFat};
            Double[] dbTotals = new Double[]{totalCar, totalPro, totalFat};
            TextView[] txtNums = new TextView[]{txt_carbonNum, txt_proteinNum, txt_fatNum};
            TextView[] txtLefts = new TextView[]{txt_carLeft, txt_proLeft, txt_fatLeft};
            ProgressBar[] progressBarss = new ProgressBar[]{progress_carbon, progress_protein, progress_fat};
            for(int cnt = 0;cnt < strSPs.length;cnt++)
            {
                if(sp.getString(strSPs[cnt], "").length() > 0) // null check
                {
                    doubles[cnt] = Double.parseDouble(sp.getString(strSPs[cnt], ""));
                    int roundDoubles = (int)Math.round(doubles[cnt]);
                    if(doubles[cnt] >= dbTotals[cnt]) // carbon left
                    {
                        txtNums[cnt].setText(String.format(Locale.US,"%.1f",doubles[cnt] - dbTotals[cnt]));
                    }
                    else
                    {
                        txtNums[cnt].setText(String.format(Locale.US,"%.1f",dbTotals[cnt] - doubles[cnt]));
                        txtLefts[cnt].setText("g   Over");
                        progressBarss[cnt].setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_horizontal_over));
                    }
                    progressBarss[cnt].setMax(roundDoubles);
                    progressBarss[cnt].setProgress((int)Math.round(dbTotals[cnt]));
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
    // date is changed, reset data
    private void MealRenew(android.content.SharedPreferences sp)
    {
        SharedPreferences.Editor editor = sp.edit();
        Date d = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        int dateBefore = sp.getInt(SP_DATE,0);
        // date is changed, reset data
        if(Integer.parseInt(date.format(d)) != dateBefore && dateBefore != 0)
        {
            SharedPreferences spMeal = getSharedPreferences(SP_MEAL, MODE_PRIVATE);
            SharedPreferences.Editor editorMeal = spMeal.edit();
            int mealCnt = spMeal.getInt(SP_MEAlNUM,0);
            for(int cnt = 1; cnt <= mealCnt;cnt++)
            {
                SharedPreferences spMealNum =
                        getSharedPreferences(SP_MEAL + String.valueOf(cnt), MODE_PRIVATE);
                spMealNum.edit().clear().commit(); // reset data
            }
            editorMeal.clear().commit(); // reset data
        }
        editor.putInt(SP_DATE,Integer.parseInt(date.format(d)));
        editor.apply();
    }
    // set tool bar title
        private void toolBarTitle(android.content.SharedPreferences sp)
        {
            int mealMode = sp.getInt(SP_MEALMODE,4);
            toolbar.setTitleTextColor(getResources().getColor(R.color.normalText));
            switch (mealMode)
            {
                case 0:
                    toolbar.setTitle("Bulk up");
                    break;
                case 1:
                    toolbar.setTitle("Low Carbo Diet");
                    break;
                case 2:
                    toolbar.setTitle("Low Fat Diet");
                    break;
                case 3:
                    toolbar.setTitle("Maintain");
                    break;
                default:
                    toolbar.setTitle("");
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
                case R.id.btn_calendar:
                    intent = new Intent(getApplication(), ActivityCalendar.class);
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
}