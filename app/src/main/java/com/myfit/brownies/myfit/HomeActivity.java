package com.myfit.brownies.myfit;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class HomeActivity extends DashBoardActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);
        setHeader(getString(R.string.text_title), false, true);
    }

    @Override
    public void onBackPressed(){};

    public void onButtonClicker(View v)
    {
        Intent intent;

        switch (v.getId()) {
            case R.id.btn_progress:
                intent = new Intent(this, Nutrition.class);
                startActivity(intent);
                break;

            case R.id.btn_diary:
                intent = new Intent(this, FoodLog.class);
                startActivity(intent);
                break;

            case R.id.btn_logout:

                SharedPreferences sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();

                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;

            default:
                break;

        }
    }
}