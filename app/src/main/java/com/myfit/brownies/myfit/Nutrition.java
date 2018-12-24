package com.myfit.brownies.myfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class Nutrition extends DashBoardActivity {

        Button button;
        TextView Calories;
        ProgressBar CalProg, CarbProgress, ProteinProgress, FatsProgress;
        static String Activity;
        static int Weight;
        Calendar calendar;

        FoodDatabase foodDatabaseHelper = new FoodDatabase(Nutrition.this);

        public int CalorieCounter() {
            return FoodLog.getCalories();
        }

        public int FatCounter(){
            return FoodLog.getFatsTotal();
        }

        public int ProteinCounter(){
            return FoodLog.getProteinTotal();
        }

        public int CarbsCounter(){
            return FoodLog.getCarbsTotal();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.progress_layout);

            button = (Button) findViewById(R.id.AddFood);

            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Nutrition.this, FoodLog.class);
                    startActivity(intent);
                }
            });

        }

        public void onStart() {
            super.onStart();

            calendar = Calendar.getInstance();

            Calories = (TextView) findViewById(R.id.CalNumber);
            TextView Goal = (TextView) findViewById(R.id.GoalText);
            TextView Info = (TextView) findViewById(R.id.UserInfo);
            CalProg = (ProgressBar) findViewById(R.id.CalProgress) ;
            CarbProgress = (ProgressBar) findViewById(R.id.CarbProgress);
            ProteinProgress = (ProgressBar) findViewById(R.id.ProteinProgress);
            FatsProgress = (ProgressBar) findViewById(R.id.FatProgress);

            UserDatabase userDatabase = new UserDatabase(this);
            String username = LoginActivity.GetUserName();
            final int BMR = userDatabase.getBMR(username);
            Activity = userDatabase.getActivity(username);
            Weight = userDatabase.getWeight(username);
            String GoalInfo = userDatabase.getGoal(username);

            userDatabase.close();

            Info.setText(printGoal(BMR, GoalInfo));

            CalProg.setProgress(0);

            CarbProgress.setProgress(0);
            FatsProgress.setProgress(0);
            ProteinProgress.setProgress(0);

            CarbProgress.setMax(CalculateCarbs(BMR));
            ProteinProgress.setMax(CalculateProtein(BMR));
            FatsProgress.setMax(CalculateFat(BMR));

            CalProg.setMax(BMR);
            Goal.setText(Integer.toString(BMR));

        }

        public String printGoal(int bmr, String goal){
            String showGoal = "";
            if (goal.toLowerCase().equals("maintain weight")){
                showGoal = "If you want to " + goal.toLowerCase() + " you must consume " + bmr + " calories daily" + "\n\n"
                        + "Carbs: 55% of daily calorie intake" + "\n"
                        + "Protein is based on weight and activity level" + "\n"
                        + "Fats: 30% of dailiy calorie intake";
            }
            else showGoal = "If you want to " + goal.toLowerCase() + " you must consume " + bmr + " calories daily in order to gain one pound per week" + "\n\n"
                    + "Carbs: 55% of daily calorie intake" + "\n"
                    + "Protein is based on weight and activity level" + "\n"
                    + "Fats: 30% of dailiy calorie intake";
            return showGoal;
        }

        public int getDay(){
            return calendar.get(Calendar.DAY_OF_WEEK) - 1;
        }

        @Override
        public void onResume(){
            super.onResume();

            List<Food> foodList = foodDatabaseHelper.getAllFood(getDay());

            int CurrentProgress1 = 0;
            int CurrentProgress2 = 0;
            int CurrentProgress3 = 0;
            int CurrentProgress4 = 0;

            for (int i = 0; i < foodList.size(); i++){
                CurrentProgress1 += Double.parseDouble(foodList.get(i).getCalories());
                CurrentProgress2 += Double.parseDouble(foodList.get(i).getCarbs());
                CurrentProgress3 += Double.parseDouble(foodList.get(i).getProtein());
                CurrentProgress4 += Double.parseDouble(foodList.get(i).getFats());
            }

            CalProg.setProgress(0);
            CalProg.setProgress(CurrentProgress1);
            Calories.setText(Integer.toString(CurrentProgress1));

            CarbProgress.setProgress(0);
            CarbProgress.setProgress(CurrentProgress2);

            ProteinProgress.setProgress(0);
            ProteinProgress.setProgress(CurrentProgress3);

            FatsProgress.setProgress(0);
            FatsProgress.setProgress(CurrentProgress4);

        }

        @Override
        public void onBackPressed(){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }


        public int CalculateCarbs (int number){
            double carbs = (number * 0.55);
            return (int) carbs / 4;
        }

        public int CalculateProtein (int number) {
            double protein;

            if (Activity.equalsIgnoreCase("Light")) {
                protein = Weight * 2.2 * 0.7;
                return (int) protein;
            }
            if (Activity.equalsIgnoreCase("Moderate")) {
                protein = Weight * 2.2 * 0.9;
                return (int) protein;
            }
            if (Activity.equalsIgnoreCase("Intense")) {
                protein = Weight * 2.2 * 0.9;
                return (int) protein;
            }
            return 0;
        }

        public int CalculateFat (int number){
            double fats = (number * 0.30);
            return (int) fats / 9;
        }


    }



