package com.example.dietmanagement;

import android.os.AsyncTask;

import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NutritionixAPI extends AsyncTask<Void, Void, JSONObject> {
    private static final String APP_ID = "2593181e";
    private static final String APP_KEY = "61f68c8e22802b495de8283db9d278b6";
    private static final String API_URL = "https://api.nutritionix.com/v1_1/search/";

    @Override
    protected JSONObject doInBackground(Void... voids) {
        try {
            String query = "Chicken Breast";
            String fields = "nf_calories,nf_total_carbohydrate,nf_protein,nf_total_fat";
            String servingWeightGrams = "100";

            // Build the request URL
            String requestUrl = API_URL + query + "?results=0:1&fields=" + fields + "&appId=" + APP_ID + "&appKey=" + APP_KEY + "&portion_size=" + servingWeightGrams;

            // Create the HttpURLConnection object and set request properties
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            // Read the response and extract the nutrition data
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            connection.disconnect();

            JSONObject json = new JSONObject(response.toString());
            JSONArray hits = json.getJSONArray("hits");
            JSONObject fieldsObj = hits.getJSONObject(0).getJSONObject("fields");
            double calories = fieldsObj.getDouble("nf_calories");
            double carbs = fieldsObj.getDouble("nf_total_carbohydrate");
            double protein = fieldsObj.getDouble("nf_protein");
            double fat = fieldsObj.getDouble("nf_total_fat");

            // Output the nutrition data
            System.out.println("Calories: " + calories);
            System.out.println("Carbs: " + carbs);
            System.out.println("Protein: " + protein);
            System.out.println("Fat: " + fat);

            return json;
        } catch (Exception e) {
            StackTraceElement[] st = (new Throwable()).getStackTrace();
            String methodName = st[1].getMethodName();
            String className = st[1].getClassName();
            int line = st[1].getLineNumber();
            System.err.println("@@@@Err : where=" + className + "." + methodName + "():" + line + ", mes=" + e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject json) {
        if (json != null) {
            try {
                // Output the nutrition data
                JSONArray hits = json.getJSONArray("hits");
                JSONObject fieldsObj = hits.getJSONObject(0).getJSONObject("fields");
                double calories = fieldsObj.getDouble("nf_calories");
                double carbs = fieldsObj.getDouble("nf_total_carbohydrate");
                double protein = fieldsObj.getDouble("nf_protein");
                double fat = fieldsObj.getDouble("nf_total_fat");

                System.out.println("Calories: " + calories);
                System.out.println("Carbs: " + carbs);
                System.out.println("Protein: " + protein);
                System.out.println("Fat: " + fat);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
