package com.myfit.brownies.myfit;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

abstract class DashBoardActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setHeader(String title, boolean btnHomeVisible, boolean btnFeedbackVisible) {
        ViewStub stub = (ViewStub) findViewById(R.id.vsHeader);
        View inflated = stub.inflate();

        TextView txtTitle = (TextView) inflated.findViewById(R.id.txtHeading);
        txtTitle.setText(title);

    }

}

