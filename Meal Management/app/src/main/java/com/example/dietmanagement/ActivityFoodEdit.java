package com.example.dietmanagement;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

// Define an activity class for editing food data in the database
public class ActivityFoodEdit extends AppCompatActivity implements View.OnClickListener {
    EditText edit_foodName, edit_per, edit_calorie, edit_carbon, edit_protein, edit_fat;
    Button btn_ok, btn_cancel;
    String foodName;
    private int per;
    private double calorie;
    private double carbon;
    private double protein;
    private double fat;
    AlertDialog.Builder builder;
    private static final String SP_DISPLAY = "display";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        builder = new AlertDialog.Builder(this);
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit);
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
            {
                edit_foodName = (EditText) findViewById(R.id.edit_foodName);
                edit_per = (EditText) findViewById(R.id.edit_per);
                edit_calorie = (EditText) findViewById(R.id.edit_calorie);
                edit_carbon = (EditText) findViewById(R.id.edit_carbon);
                edit_protein = (EditText) findViewById(R.id.edit_protein);
                edit_fat = (EditText) findViewById(R.id.edit_fat);
                // retrieve the selected food item from the intent extra
                String[] selectedFood = getIntent().getStringArrayExtra("selectedFood");
                if(selectedFood != null) {
                    // set the text in the EditText fields
                    edit_foodName.setText(selectedFood[0]);
                    edit_per.setText(selectedFood[1]);
                    edit_calorie.setText(selectedFood[2]);
                    edit_carbon.setText(selectedFood[3]);
                    edit_protein.setText(selectedFood[4]);
                    edit_fat.setText(selectedFood[5]);
                    // make edit unable
                    edit_foodName.setEnabled(false);
                }
            }
            btn_ok = (Button)findViewById(R.id.btn_ok);
            btn_ok.setOnClickListener((View.OnClickListener) this);
            btn_cancel = (Button)findViewById(R.id.btn_cancel);
            btn_cancel.setOnClickListener((View.OnClickListener) this);
        }
        catch (Exception e)
        {
            builder.setTitle("Error");
            builder.setMessage(String.valueOf(e));
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    private class InsertFoodTask extends AsyncTask<EntityFood, Void, Void> {

        @Override
        protected Void doInBackground(EntityFood... entityFoods) {
            DBFood db = DBFood.getDatabase(getApplicationContext());
            String btn = getIntent().getStringExtra("button");
            if (btn.equals("Add")) // add button is pressed
            {
                db.foodDao().insert(entityFoods[0]);
            }
            else // edit button is pressed
            {
                db.foodDao().update(foodName,per,calorie,carbon,protein,fat);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(getApplicationContext(), ActivityFoodDB.class);
            startActivity(intent);
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
                case R.id.btn_ok:
                    if (edit_foodName.getText().length() != 0 && edit_per.getText().length() != 0 &&
                            edit_calorie.getText().length()!=0&& edit_carbon.length()!=0&&
                            edit_protein.length()!=0&&edit_fat.length()!=0) // if every EditText was entered
                    {
                        foodName = edit_foodName.getText().toString();
                        per = Integer.parseInt(edit_per.getText().toString());
                        calorie = Double.parseDouble(edit_calorie.getText().toString());
                        carbon = Double.parseDouble(edit_carbon.getText().toString());
                        protein = Double.parseDouble(edit_protein.getText().toString());
                        fat = Double.parseDouble(edit_fat.getText().toString());
                        // Create an instance of the database
                        DBFood db = DBFood.getDatabase(getApplicationContext());
                        // Create a new Food object with the entered data
                        EntityFood entityFood = new EntityFood(foodName, per, calorie, carbon,protein,fat);
                        new InsertFoodTask().execute(entityFood);
                    }
                    else
                    {
                        builder.setTitle("Error");
                        builder.setMessage("Enter the data");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                    break;
                case R.id.btn_cancel:
                    // go back to activity_database.xml
                    NutritionixAPI nutritionixAPI = new NutritionixAPI();
                    //nutritionixAPI.execute();
                    intent = new Intent(getApplication(), ActivityFoodDB.class);
                    startActivity(intent);
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
