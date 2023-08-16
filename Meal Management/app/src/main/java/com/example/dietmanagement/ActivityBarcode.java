package com.example.dietmanagement;

//package com.samplegame.barcode_sample;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.journeyapps.barcodescanner.camera.CameraSettings;
import java.util.List;
import java.util.Locale;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
public class ActivityBarcode extends AppCompatActivity implements View.OnClickListener
{
    TextView txt_barcodeFoodName;
    TextView txt_perCal100;
    TextView txt_perCalPot;
    TextView txt_perCar100;
    TextView txt_perCarPot;
    TextView txt_perPro100;
    TextView txt_perProPot;
    TextView txt_perFat100;
    TextView txt_perFatPot;
    RadioGroup radioGroup;
    RadioButton radioBtn_100, radioBtn_pcs;
    Button btn_barcodeOk;
    Button btn_barcodeCancel;
    final String strCal100 = "energy-kcal_100g";
    final String strCalPcs = "energy-kcal_serving";
    final String strCar100 = "carbohydrates_100g";
    final String strCarPcs = "carbohydrates_serving";
    final String strPro100 = "proteins_100g";
    final String strProPcs = "proteins_serving";
    final String strFat100 = "fat_100g";
    final String strFatPcs = "fat_serving";
    Double dbCal100 = null;
    Double dbCalPcs = null;
    Double dbCar100 = null;
    Double dbCarPcs = null;
    Double dbPro100 = null;
    Double dbProPcs = null;
    Double dbFat100 = null;
    Double dbFatPcs = null;
    int flg_radio = 0;

    String foodName;
    private int per;
    private double calorie;
    private double carbon;
    private double protein;
    private double fat;
    CompoundBarcodeView barcodeView;
    private String lastResult;
    // Define the base URL for the Open Food Facts API
    private String BASE_URL = " ";
    // Define the full URL by appending the EAN code to the base URL
    private String FULL_URL = " ";
    // Define the RequestQueue to handle the HTTP requests
    private RequestQueue requestQueue;
    AlertDialog.Builder builder;
    private static final String SP_DISPLAY = "display";
    private int mealNum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_barcode);
            // receive the data ex."Meal1" from ActivityMeal
            mealNum = getIntent().getIntExtra("mealNum",0); // if meal was edited
            // light / night mode
            android.content.SharedPreferences spSetting = getSharedPreferences("Setting", MODE_PRIVATE);
            if(spSetting.getInt(SP_DISPLAY, 0) == 0)
            {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else if (spSetting.getInt(SP_DISPLAY, 0) == 1)
            {
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            txt_barcodeFoodName = findViewById(R.id.txt_barcodeFoodName);
            txt_perCal100 = findViewById(R.id.txt_perCal100);
            txt_perCalPot = findViewById(R.id.txt_perCalPot);
            txt_perCar100 = findViewById(R.id.txt_perCar100);
            txt_perCarPot = findViewById(R.id.txt_perCarPot);
            txt_perPro100 = findViewById(R.id.txt_perPro100);
            txt_perProPot = findViewById(R.id.txt_perProPot);
            txt_perFat100 = findViewById(R.id.txt_perFat100);
            txt_perFatPot = findViewById(R.id.txt_perFatPot);
            radioGroup = findViewById(R.id.radioGroup);
            radioBtn_100 = findViewById(R.id.radioBtn_100);
            radioBtn_pcs = findViewById(R.id.radioBtn_pcs);
            btn_barcodeOk = findViewById(R.id.btn_barcodeOk);
            btn_barcodeCancel = findViewById(R.id.btn_barcodeCancel);
            btn_barcodeOk.setOnClickListener((View.OnClickListener) this);
            btn_barcodeCancel.setOnClickListener((View.OnClickListener) this);

            if(ActivityCompat.checkSelfPermission(ActivityBarcode.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                String[] permissions = {Manifest.permission.CAMERA};
                ActivityCompat.requestPermissions(ActivityBarcode.this, permissions, 100);
                return;
            }
            CameraSetting();
            readBarcode();

            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                // チェック状態変更時に呼び出されるメソッド
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.radioBtn_100)
                    {
                        flg_radio = 1;
                    }
                    if (checkedId == R.id.radioBtn_pcs)
                    {
                        flg_radio = 2;
                    }
                }
            });
        }
        catch (Exception e)
        {
            builder.setTitle("Error");
            builder.setMessage(e.toString());
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    private void CameraSetting(){
        barcodeView = findViewById(R.id.barcodeView);
        CameraSettings settings = barcodeView.getBarcodeView().getCameraSettings();
        barcodeView.getBarcodeView().setCameraSettings(settings);
        barcodeView.setStatusText("Scan the barcode");
        barcodeView.resume();
        readBarcode();
    }
    private void readBarcode(){
        builder = new AlertDialog.Builder(this);
        barcodeView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                //このif文で、不必要な連続読みを防ぐ
                if (result.getText() == null || result.getText().equals(lastResult)){
                    return;
                }
                //このif文で、読み取られたバーコードがJANコードかどうか判定する
                if (result.getBarcodeFormat() != BarcodeFormat.EAN_13){
                    return;
                }
                lastResult = result.getText();
                Toast.makeText(ActivityBarcode.this, "Scanned", Toast.LENGTH_LONG).show();
                //getNumber.setText(result.getText());
                // Initialize the RequestQueue
                requestQueue = Volley.newRequestQueue(ActivityBarcode.this);
                // Make a GET request to the API and handle the response
                BASE_URL = "https://world.openfoodfacts.org/api/v2/product/";
                FULL_URL = BASE_URL +result.getText() + ".json";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FULL_URL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String strnutritionData_list[] = new String[]{strCal100, strCalPcs, strCar100, strCarPcs,
                                            strPro100, strProPcs, strFat100, strFatPcs};
                                    // data in the Json file
                                    Double dbnutritionData_list[] = new Double[]{dbCal100, dbCalPcs, dbCar100, dbCarPcs, dbPro100,
                                            dbCarPcs, dbPro100, dbProPcs, dbFat100, dbFatPcs};
                                    // TextView
                                    TextView txtnutritionData_list[] = new TextView[]{txt_perCal100, txt_perCalPot, txt_perCar100, txt_perCarPot,
                                            txt_perPro100, txt_perProPot, txt_perFat100, txt_perFatPot};
                                    // Check if the product was found (status 1)
                                    if (response.getInt("status") == 1)
                                    {
                                        // Get the product name from the response
                                        String productName = response.getJSONObject("product").getString("product_name");
                                        txt_barcodeFoodName.setText(productName);
                                        // Get the nutrition data from the response
                                        JSONObject nutritionData = response.getJSONObject("product").getJSONObject("nutriments");
                                        // name of data in the Json file

                                        for(int cnt = 0; cnt < strnutritionData_list.length; cnt++)
                                        {
                                            // check that Json file involve each name of data
                                            if (nutritionData.has(strnutritionData_list[cnt]))
                                            {
                                                String strTmp = strnutritionData_list[cnt];
                                                dbnutritionData_list[cnt] = nutritionData.getDouble((strTmp));
                                                Double dbTmp = dbnutritionData_list[cnt];
                                                // Display the product name and nutrition data on the TextViews
                                                if(cnt == 0 || cnt == 1)
                                                {
                                                    txtnutritionData_list[cnt].setText(String.format(Locale.US,"%.2f", dbTmp) + " cal");
                                                }
                                                else
                                                {
                                                    txtnutritionData_list[cnt].setText(String.format(Locale.US,"%.2f", dbTmp) + " g");
                                                }

                                            }
                                            else
                                            {
                                                txtnutritionData_list[cnt].setText("No Data");
                                            }
                                        }
                                    }
                                    else
                                    {
                                        // Display an error message if the product was not found
                                        txt_barcodeFoodName.setText("Product not found");
                                        for(int cnt = 0; cnt < strnutritionData_list.length; cnt++)
                                        {
                                            txtnutritionData_list[cnt].setText("No Data");
                                        }
                                    }
                                }
                                catch (JSONException e) {
                                    builder.setTitle("Error");
                                    builder.setMessage(e.toString());
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Display an error message if the request was not successful
                        txt_barcodeFoodName.setText("Request failed");
                        // TextView
                        TextView txtnutritionData_list[] = new TextView[]{txt_perCal100, txt_perCalPot, txt_perCar100, txt_perCarPot,
                                txt_perPro100, txt_perProPot, txt_perFat100, txt_perFatPot};
                        for(int cnt = 0; cnt < txtnutritionData_list.length; cnt++)
                        {
                            txtnutritionData_list[cnt].setText("No Data");
                        }
                    }
                });
                // Add the request to the RequestQueue
                requestQueue.add(jsonObjectRequest);
            }
            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.checkSelfPermission(ActivityBarcode.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                return;
            }
        }
        CameraSetting();
    }
    private class InsertFoodTask extends AsyncTask<EntityFood, Void, Void> {

        @Override
        protected Void doInBackground(EntityFood... entityFoods) {
            DBFood db = DBFood.getDatabase(getApplicationContext());
            EntityFood db_allFoods = db.foodDao().getByFoodName(foodName);
            // if the food is already registered
            if(db_allFoods == null)
            {
                db.foodDao().insert(entityFoods[0]);
            }
            return null;
        }
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            Intent intent = new Intent(getApplicationContext(), ActivityFoodDB.class);
//            startActivity(intent);
//        }
    }
    // button clicked method
    @Override
    public void onClick(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        try {
            foodName = txt_barcodeFoodName.getText().toString();
            Intent intent = new Intent(getApplication(), ActivityMeal.class);
            String barcodeNum = getIntent().getStringExtra("barcode");
            switch (v.getId())
            {
                case R.id.btn_barcodeOk:
                    if(flg_radio == 0)
                    {
                        builder.setTitle("Error");
                        builder.setMessage("Select the Per");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                    // per 100
                    else if (flg_radio == 1 && txt_perCal100.getText().toString() != "No Data" && txt_perCal100.getText().length() > 0)
                    {
                        per = 100;
                        calorie = Double.parseDouble(txt_perCal100.getText().toString().substring(0,txt_perCal100.getText().length() - " cal".length()));
                        carbon = Double.parseDouble(txt_perCar100.getText().toString().substring(0,txt_perCar100.getText().length() - " g".length()));
                        protein = Double.parseDouble(txt_perPro100.getText().toString().substring(0,txt_perPro100.getText().length() - " g".length()));
                        fat = Double.parseDouble(txt_perFat100.getText().toString().substring(0,txt_perFat100.getText().length() - " g".length()));
                        // Create a new Food object with the entered data
                        EntityFood entityFood = new EntityFood(foodName, per, calorie, carbon,protein,fat);
                        new InsertFoodTask().execute(entityFood);
                    }
                    // per PCS
                    else if (flg_radio == 2 && txt_perCalPot.getText().toString() != "No Data" && txt_perCalPot.getText().length() > 0)
                    {
                        per = 1;
                        calorie = Double.parseDouble(txt_perCalPot.getText().toString().substring(0,txt_perCalPot.getText().length() - " cal".length()));
                        carbon = Double.parseDouble(txt_perCarPot.getText().toString().substring(0,txt_perCarPot.getText().length() - " g".length()));
                        protein = Double.parseDouble(txt_perProPot.getText().toString().substring(0,txt_perProPot.getText().length() - " g".length()));
                        fat = Double.parseDouble(txt_perFatPot.getText().toString().substring(0,txt_perFatPot.getText().length() - " g".length()));
                        // Create a new Food object with the entered data
                        EntityFood entityFood = new EntityFood(foodName, per, calorie, carbon,protein,fat);
                        new InsertFoodTask().execute(entityFood);
                    }
                    else
                    {
                        builder.setTitle("Error");
                        builder.setMessage("Food data is inaccurate");
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                    }
                    break;
                case R.id.btn_barcodeCancel:
                    intent = new Intent(getApplication(), ActivityMeal.class);
                    startActivity(intent);
                    break;

            }
            intent.putExtra("barcodeToMeal", barcodeNum + foodName);
            intent.putExtra("mealNum", mealNum);
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
