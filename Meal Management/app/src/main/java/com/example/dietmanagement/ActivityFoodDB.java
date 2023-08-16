package com.example.dietmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

// Define an activity class for displaying and editing food data
public class ActivityFoodDB extends AppCompatActivity implements View.OnClickListener{
    RecyclerView recyclerView;
    Button btn_foodAdd;
    Button btn_foodEdit;
    Button btn_foodDelete;
    ViewAdapterDatabase viewAdapterDatabase;
    ItemClickListener itemClickListener;
    AlertDialog.Builder builder;
    private static final String SP_DISPLAY = "display";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // dialog builder for showing errors
        builder = new AlertDialog.Builder(this);
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_database);
            // light / night mode
            android.content.SharedPreferences spSetting = getSharedPreferences("Setting", MODE_PRIVATE);
            if(spSetting.getInt(SP_DISPLAY, 0) == 0)
            {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else if (spSetting.getInt(SP_DISPLAY, 0) == 1)
            {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }

            // For displaying the food list
            recyclerView = (RecyclerView) findViewById(R.id.recycle_database);
            // For all the foods in the database
            LiveData<List<EntityFood>> allFoodsLiveData = DBFood.getDatabase(this).foodDao().getAllFoods();
            // Checking the LiveData and update the UI when the food data changes
            allFoodsLiveData.observe(this, new Observer<List<EntityFood>>() {
                @Override
                public void onChanged(List<EntityFood> allFoods) {
                    // itemClickListener for the RecyclerView
                    itemClickListener = new ItemClickListener() {
                        @Override
                        public void onClick(String s) {
                            // Notify the data has changed
                            recyclerView.post(new Runnable() {
                                @SuppressLint("NotifyDataSetChanged")
                                @Override
                                public void run() {
                                    viewAdapterDatabase.notifyDataSetChanged();
                                }
                            });
                        }
                    };
                    // viewAdapterDatabase for the food list
                    viewAdapterDatabase = new ViewAdapterDatabase(allFoods, itemClickListener);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ActivityFoodDB.this));
                    recyclerView.setAdapter(viewAdapterDatabase);
                }
            });
            // Move to Setting page
            btn_foodAdd = (Button)findViewById(R.id.btn_foodAdd);
            btn_foodEdit = (Button)findViewById(R.id.btn_foodEdit);
            btn_foodDelete = (Button)findViewById(R.id.btn_foodDelete);
            btn_foodAdd.setOnClickListener((View.OnClickListener) this);
            btn_foodEdit.setOnClickListener((View.OnClickListener) this);
            btn_foodDelete.setOnClickListener((View.OnClickListener) this);
        }
        catch (Exception e)
        {
            builder.setTitle("Error");
            builder.setMessage(String.valueOf(e));
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    // button clicked method
    @Override
    public void onClick(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        try {
            Intent intent;
            EntityFood selectedFood;
            switch (v.getId())
            {
                case R.id.btn_foodAdd:
                    intent = new Intent(getApplication(), ActivityFoodEdit.class);
                    intent.putExtra("button", "Add");
                    startActivity(intent);
                    break;
                case R.id.btn_foodEdit:
                    selectedFood= viewAdapterDatabase.foodList.get(viewAdapterDatabase.selectedPosition);
                    // add the selected food item as an extra to the intent
                    String[] strSelected = new String[]{selectedFood.getFoodName(), String.valueOf(selectedFood.getPer()),
                            String.valueOf(selectedFood.getCalorie()), String.valueOf(selectedFood.getCarbon()),
                            String.valueOf(selectedFood.getProtein()), String.valueOf(selectedFood.getFat())};
                    intent = new Intent(getApplication(), ActivityFoodEdit.class);
                    intent.putExtra("selectedFood", strSelected);
                    intent.putExtra("button", "Edit");
                    startActivity(intent);
                    break;
                case R.id.btn_foodDelete:
                    selectedFood= viewAdapterDatabase.foodList.get(viewAdapterDatabase.selectedPosition);
                    builder.setTitle("Warning");
                    builder.setMessage("Are you sure you want to delete this data?");
                    builder.setNegativeButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // delete the selected food item on a background thread using an AsyncTask
                                    new AsyncTask<Void, Void, Void>() {
                                        @Override
                                        protected Void doInBackground(Void... voids) {
                                            DBFood.getDatabase(getApplicationContext()).foodDao().deleteFoodByName(selectedFood.getFoodName());
                                            return null;
                                        }
                                        @Override
                                        protected void onPostExecute(Void aVoid) {
                                            super.onPostExecute(aVoid);
                                            // refresh the list after deleting the food item
                                            viewAdapterDatabase.notifyDataSetChanged();
                                        }
                                    }.execute();
                                }
                            });
                    builder.setPositiveButton("Cancel", null).show();
                    break;
                default:
                    break;
            }
        }
        catch (Exception e)
        {
            builder.setTitle("Error");
            if(v.getId() == R.id.btn_foodEdit || v.getId() == R.id.btn_foodDelete)
            {
                builder.setMessage("Select the data");
            }
            else
            {
                builder.setMessage(String.valueOf(e));
            }
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    @Override
    public void onBackPressed(){
        finish();
    }
}