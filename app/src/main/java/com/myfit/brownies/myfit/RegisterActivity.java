package com.myfit.brownies.myfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;
    private TextInputLayout textInputLayoutWeight;
    private TextInputLayout textInputLayoutActivity;
    private TextInputLayout textInputLayoutHeight;
    private TextInputLayout textInputLayoutAge;
    private TextInputLayout textInputLayoutSex;
    private TextInputLayout textInputLayoutGoal;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;
    private TextInputEditText textInputEditTextWeight;
    private TextInputEditText textInputEditTextActivity;
    private TextInputEditText textInputEditTextHeight;
    private TextInputEditText textInputEditTextAge;
    private TextInputEditText textInputEditTextSex;
    private TextInputEditText textInputEditTextGoal;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private UserDatabase userDatabaseHelper;
    private User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
        userDatabaseHelper.close();
    }

    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);
        textInputLayoutWeight = (TextInputLayout) findViewById(R.id.textInputLayoutWeight);
        textInputLayoutActivity = (TextInputLayout) findViewById(R.id.textInputLayoutActivity);
        textInputLayoutHeight = (TextInputLayout) findViewById(R.id.textInputLayoutHeight);
        textInputLayoutAge =  (TextInputLayout) findViewById(R.id.textInputLayoutAge);
        textInputLayoutSex = (TextInputLayout) findViewById(R.id.textInputLayoutSex);
        textInputLayoutGoal = (TextInputLayout) findViewById(R.id.textInputLayoutGoal);

        textInputEditTextName = (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);
        textInputEditTextWeight = (TextInputEditText) findViewById(R.id.textInputEditTextWeight);
        textInputEditTextActivity = (TextInputEditText) findViewById(R.id.textInputEditTextActivity);
        textInputEditTextHeight = (TextInputEditText) findViewById(R.id.textInputEditTextHeight);
        textInputEditTextAge = (TextInputEditText) findViewById(R.id.textInputEditTextAge);
        textInputEditTextSex = (TextInputEditText) findViewById(R.id.textInputEditTextSex);
        textInputEditTextGoal = (TextInputEditText) findViewById(R.id.textInputEditTextGoal);


        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);

    }

    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    private void initObjects() {
        inputValidation = new InputValidation(activity);
        userDatabaseHelper = new UserDatabase(activity);
        user = new User();

    }

    private boolean postDataToSQLite() {

        boolean PostSuccessful = true;

        if (!inputValidation.isInputEditTextFilled(textInputEditTextName, textInputLayoutName, getString(R.string.error_message_name))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return false;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return false;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return false;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(R.string.error_password_match))) {
            return false;
        }

        if (!userDatabaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            user.setName(textInputEditTextName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());
            user.setWeight(Integer.parseInt(textInputEditTextWeight.getText().toString().trim()));
            user.setActivity(textInputEditTextActivity.getText().toString().trim());
            user.setHeight(Integer.parseInt(textInputEditTextHeight.getText().toString().trim()));
            user.setAge(Integer.parseInt(textInputEditTextAge.getText().toString().trim()));
            user.setSex(textInputEditTextSex.getText().toString().trim());
            user.setGoal(textInputEditTextGoal.getText().toString().trim());
            user.setBMR(user.getBMR(Integer.parseInt(textInputEditTextWeight.getText().toString().trim()), Integer.parseInt(textInputEditTextHeight.getText().toString().trim()), Integer.parseInt(textInputEditTextAge.getText().toString().trim()), textInputEditTextSex.getText().toString().trim(), textInputEditTextActivity.getText().toString().trim(), textInputEditTextGoal.getText().toString().trim()));

            userDatabaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();



        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
            return false;
        }
        userDatabaseHelper.close();
        return PostSuccessful;


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonRegister:

                if (postDataToSQLite()) {

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    break;
                }
                else break;

            case R.id.appCompatTextViewLoginLink:

                Intent b = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(b);
                break;
            }
    }

    private void emptyInputEditText() {
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}