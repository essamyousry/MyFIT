package com.myfit.brownies.myfit;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private int weight;
    private int height;
    private int age;
    private String Activity;
    private String sex;
    private String Goal;
    private int BMR;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setActivity(String Activity) {
        this.Activity = Activity;
    }

    public String getActivity() {
        return Activity;
    }

    public void setGoal(String Goal){ this.Goal = Goal; }

    public String getGoal(){ return Goal; }

    public int getBMR(int weight, int height, int age, String sex, String Activity, String Goal) {

        boolean isMale = sex.startsWith("M");
        double rate = 0.0;

        if ((isMale == true) && (Activity.startsWith("L")) && (Goal.equalsIgnoreCase("Lose Weight"))) {
            rate = (((weight * 6.25) + (9.99 * height) - (4.92 * age)) * 1.2) - 500;
        } else if ((isMale == true) && (Activity.startsWith("L")) && (Goal.equalsIgnoreCase("Maintain Weight"))) {
            rate = ((weight * 6.25) + (9.99 * height) - (4.92 * age)) * 1.2;
        } else if ((isMale == true) && (Activity.startsWith("L")) && (Goal.equalsIgnoreCase("Gain Weight"))) {
            rate = (((weight * 6.25) + (9.99 * height) - (4.92 * age)) * 1.2) + 500;
        } else if ((isMale == true) && (Activity.startsWith("M")) && (Goal.equalsIgnoreCase("Lose Weight"))) {
            rate = (((weight * 6.25) + (9.99 * height) - (4.92 * age)) * 1.55) - 500;
        } else if ((isMale == true) && (Activity.startsWith("M")) && (Goal.equalsIgnoreCase("Maintain Weight"))) {
            rate = ((weight * 6.25) + (9.99 * height) - (4.92 * age)) * 1.55;
        } else if ((isMale == true) && (Activity.startsWith("M")) && (Goal.equalsIgnoreCase("Gain Weight"))) {
            rate = (((weight * 6.25) + (9.99 * height) - (4.92 * age)) * 1.55) + 500;
        } else if ((isMale == true) && (Activity.startsWith("I")) && (Goal.equalsIgnoreCase("Lose Weight"))) {
            rate = (((weight * 6.25) + (9.99 * height) - (4.92 * age)) * 1.9) - 500;
        } else if ((isMale == true) && (Activity.startsWith("I")) && (Goal.equalsIgnoreCase("Maintain Weight"))) {
            rate = ((weight * 6.25) + (9.99 * height) - (4.92 * age)) * 1.9;
        } else if ((isMale == true) && (Activity.startsWith("I")) && (Goal.equalsIgnoreCase("Gain Weight"))) {
            rate = (((weight * 6.25) + (9.99 * height) - (4.92 * age)) * 1.9) + 500;
        } else if ((isMale == false) && (Activity.startsWith("L")) && (Goal.equalsIgnoreCase("Lose Weight"))) {
            rate = ((((weight * 6.25) + (9.99 * height) - (4.92 * age)) - 161) * 1.2) - 500;
        } else if ((isMale == false) && (Activity.startsWith("L")) && (Goal.equalsIgnoreCase("Maintain Weight"))) {
            rate = (((weight * 6.25) + (9.99 * height) - (4.92 * age)) - 161) * 1.2;
        } else if ((isMale == false) && (Activity.startsWith("L")) && (Goal.equalsIgnoreCase("Gain Weight"))) {
            rate = ((((weight * 6.25) + (9.99 * height) - (4.92 * age)) - 161) * 1.2) + 500;
        } else if ((isMale == false) && (Activity.startsWith("M")) && (Goal.equalsIgnoreCase("Lose Weight"))) {
            rate = ((((weight * 6.25) + (9.99 * height) - (4.92 * age)) - 161) * 1.55) - 500;
        } else if ((isMale == false) && (Activity.startsWith("M")) && (Goal.equalsIgnoreCase("Maintain Weight"))) {
            rate = (((weight * 6.25) + (9.99 * height) - (4.92 * age)) - 161) * 1.55;
        } else if ((isMale == false) && (Activity.startsWith("M")) && (Goal.equalsIgnoreCase("Gain Weight"))) {
            rate = ((((weight * 6.25) + (9.99 * height) - (4.92 * age)) - 161) * 1.55) + 500;
        } else if ((isMale == false) && (Activity.startsWith("I")) && (Goal.equalsIgnoreCase("Lose Weight"))) {
            rate = ((((weight * 6.25) + (9.99 * height) - (4.92 * age)) - 161) * 1.9) - 500;
        } else if ((isMale == false) && (Activity.startsWith("I")) && (Goal.equalsIgnoreCase("Maintain Weight"))) {
            rate = (((weight * 6.25) + (9.99 * height) - (4.92 * age)) - 161) * 1.9;
        } else if ((isMale == false) && (Activity.startsWith("I")) && (Goal.equalsIgnoreCase("Gain Weight"))) {
            rate = ((((weight * 6.25) + (9.99 * height) - (4.92 * age)) - 161) * 1.9) + 500;
        }

        return (int) rate;

    }

    public void setBMR(int BMR) {this.BMR = BMR;}
}



