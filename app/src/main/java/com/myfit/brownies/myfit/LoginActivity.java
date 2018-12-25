package com.myfit.brownies.myfit;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;
    private UserDatabase userDatabaseHelper;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String userName = "username";
    public static final String passWord = "password";
    SharedPreferences sharedpreferences;

    static String username;
    static String GetUserName(){
        return username;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
        userDatabaseHelper.close();

    }

    private void initViews() {

        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

    }

    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    private void initObjects() {
        userDatabaseHelper = new UserDatabase(activity);
        inputValidation = new InputValidation(activity);

    }
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.appCompatButtonLogin:
                        if (verifyFromSQLite()) {

                            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

                            String user  = textInputEditTextEmail.getText().toString().trim();
                            String pass  = textInputEditTextPassword.getText().toString().trim();

                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString(userName, user);
                            editor.putString(passWord, pass);
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            break;
                        }
                        else break;

                    case R.id.textViewLinkRegister:
                        // Navigate to RegisterActivity
                        Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                        startActivity(intentRegister);
                        break;
                }
            }

    private boolean verifyFromSQLite() {

        boolean Verify = false;

        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return Verify;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return Verify;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return Verify;
        }

        if (!userDatabaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                ,textInputEditTextPassword.getText().toString().trim())) {

            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();

            System.out.println(textInputEditTextEmail.getText().toString().trim());

           return Verify;

        } if (userDatabaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                ,textInputEditTextPassword.getText().toString().trim())){

            username = textInputEditTextEmail.getText().toString().trim();


        }
        Verify = true;
        return Verify;
    }

    @Override
    public void onBackPressed(){
    }

    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }

}