package com.myfit.brownies.myfit;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FoodLog extends DashBoardActivity {

    TextInputEditText textInputEditTextFood;
    TextInputLayout textInputLayoutFood;
    TextView ViewDay;

    Button GTP;
    Button btn_Check;
    Button btn_Scan;
    ImageButton btnSpeak;

    public static CustomAdapter adapter;

    ListView lv;
    String trial;
    Calendar calendar;

    static int capacity = 7;
    public static FoodStructure<Food> FoodArray = new FoodStructure<>(capacity);
    FoodDatabase foodDatabaseHelper = new FoodDatabase(FoodLog.this);

    GetFoodTest TestFood;
    GetFoodUPC FoodUPC;

    public static int getCalories() {
        int sum = 0;
        for (int i = 0; i < FoodArray.size(); i++) {
            sum = sum + (int) Double.parseDouble(FoodArray.get(i).getCalories());
        }
        return sum;
    }

    public static int getProteinTotal() {
        int sum = 0;
        for (int i = 0; i < FoodArray.size(); i++) {
            sum = sum + (int) Double.parseDouble(FoodArray.get(i).getProtein());
        }
        return sum;
    }

    public static int getCarbsTotal() {
        int sum = 0;
        for (int i = 0; i < FoodArray.size(); i++) {
            sum = sum + (int) Double.parseDouble(FoodArray.get(i).getCarbs());
        }
        return sum;
    }

    public static int getFatsTotal() {
        int sum = 0;
        for (int i = 0; i < FoodArray.size(); i++) {
            sum = sum + (int) Double.parseDouble(FoodArray.get(i).getFats());
        }
        return sum;
    }

    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_diary);

        lv = (ListView) findViewById(R.id.list);
        textInputLayoutFood = (TextInputLayout) findViewById(R.id.textInputLayoutFood);
        textInputEditTextFood = (TextInputEditText) findViewById(R.id.textInputEditTextFood);
        ViewDay = (TextView) findViewById(R.id.ViewDay);

        btnSpeak = (ImageButton) findViewById(R.id.Speak);
        btn_Check = (Button) findViewById(R.id.btn_CheckFood);
        btn_Scan = (Button) findViewById(R.id.btn_ScanFood);

        calendar = Calendar.getInstance();

        String Day = getCurrentDay();
        ViewDay.setText(Day);

        GTP = (Button) findViewById(R.id.btn_GTP);

        GTP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodLog.this, Nutrition.class);
                startActivity(intent);
            }
        });

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        btn_Check.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                trial = textInputEditTextFood.getText().toString().trim();
                if (!trial.isEmpty()) {
                    TestFood = new GetFoodTest();
                    TestFood.execute(trial);
                    textInputEditTextFood.setText(null);
                }
            }
        });

        btn_Scan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FoodUPC = new GetFoodUPC();
                String r = QRActivity.getUPC();
                FoodUPC.execute(r);
                goToQR();
            }

        });

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
        adapter = new CustomAdapter(FoodLog.this, FoodArray);

        List<Food> foodList = foodDatabaseHelper.getAllFood(getDay());

        FoodStructure<Food> newList = new FoodStructure<>();
        for (int i = 0; i < foodList.size(); i++){
            newList.add(foodList.get(i), getDay());
        }
        adapter.setListData(newList);
        lv.setAdapter(adapter);

        foodDatabaseHelper.close();
    }

    public void goToQR(){
        Intent intent = new Intent(this, QRActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    textInputEditTextFood.setText(result.get(0));

                }
                break;
            }

        }

    }

    public String getCurrentDay(){

        String daysArray[] = {"Sunday","Monday","Tuesday", "Wednesday","Thursday","Friday", "Saturday"};

        calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        return daysArray[day];

    }

    public int getDay(){
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    public class GetFoodTest extends AsyncTask<String, String, Void> {

        private String TAG = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... string) {
            GetHTTP sh = new GetHTTP();

            String url = "https://api.nutritionix.com/v1_1/search/" + string[0] + "?results=0:1&fields=item_name,brand_name,nf_calories,nf_total_carbohydrate,nf_protein,nf_total_fat&appId=5bdd20e0&appKey=6aa652ff3c10decb5776c6bdb5f40930";

            // Making a request to url and getting response
            String jsonStr = sh.makeCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    Food FoodGroup1 = new Food();

                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray Food = jsonObj.getJSONArray("hits");

                    for (int i = 0; i < Food.length(); i++) {
                        JSONObject all = Food.getJSONObject(i);
                        JSONObject fields = all.getJSONObject("fields");

                        String itemName = fields.getString("item_name");
                        FoodGroup1.setIName(itemName);

                        String BrandName = fields.getString("brand_name");
                        FoodGroup1.setBName(BrandName);

                        String Calories = fields.getString("nf_calories");
                        FoodGroup1.setCalories(Calories);

                        String Protein = fields.getString("nf_protein");
                        FoodGroup1.setProtein(Protein);

                        String Carbs = fields.getString("nf_total_carbohydrate");
                        FoodGroup1.setCarbs(Carbs);

                        String Fats = fields.getString("nf_total_fat");
                        FoodGroup1.setFats(Fats);

                        FoodArray.add(FoodGroup1, getDay());
                        foodDatabaseHelper.addFood(FoodGroup1);
                    }

                } catch (final JSONException e) {
                    Log.v(TAG, "Json parsing error: " + e.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            List<Food> foodList = foodDatabaseHelper.getAllFood(getDay());
            FoodStructure<Food> newList = new FoodStructure<>();
            for (int i = 0; i < foodList.size(); i++){
                newList.add(foodList.get(i), getDay());
            }

            adapter.setListData(newList);
            adapter.notifyDataSetChanged();
        }

    }

    public class GetFoodUPC extends AsyncTask<String, String, Void> {

        private String TAG = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String...string) {
            GetHTTP sh = new GetHTTP();

            String url = "https://api.nutritionix.com/v1_1/item?upc=" + string[0] + "&appId=5bdd20e0&appKey=6aa652ff3c10decb5776c6bdb5f40930";

            // Making a request to url and getting response
            String jsonStr = sh.makeCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    Food FoodGroup2 = new Food();

                    JSONObject jsonObj = new JSONObject(jsonStr);

                    String itemName = jsonObj.getString("item_name");
                    FoodGroup2.setIName(itemName);

                    String BrandName = jsonObj.getString("brand_name");
                    FoodGroup2.setBName(BrandName);

                    String Calories = jsonObj.getString("nf_calories");
                    FoodGroup2.setCalories(Calories);

                    String Protein = jsonObj.getString("nf_protein");
                    FoodGroup2.setProtein(Protein);

                    String Carbs = jsonObj.getString("nf_total_carbohydrate");
                    FoodGroup2.setCarbs(Carbs);

                    String Fats = jsonObj.getString("nf_total_fat");
                    FoodGroup2.setFats(Fats);

                    FoodArray.add(FoodGroup2, getDay());
                    foodDatabaseHelper.addFood(FoodGroup2);

                } catch (final JSONException e) {
                    Log.v(TAG, "Json parsing error: " + e.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            List<Food> foodList = foodDatabaseHelper.getAllFood(getDay());
            FoodStructure<Food> newList = new FoodStructure<>();
            for (int i = 0; i < foodList.size(); i++){
                newList.add(foodList.get(i), getDay());
            }

            adapter.setListData(newList);
            adapter.notifyDataSetChanged();
        }

    }
}