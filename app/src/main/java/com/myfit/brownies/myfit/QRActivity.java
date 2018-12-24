package com.myfit.brownies.myfit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;

    static String UPC;
    static String getUPC() {
        return UPC;
    }

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
        getSupportActionBar().hide();
    }


    @Override
    public void onResume() {
        super.onResume();
        UPC = "";
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {

        if (rawResult != null) {
            final String result = rawResult.getText();
            UPC = replaceLeadingZeros(result);
        }

        onBackPressed();

    }

    public String replaceLeadingZeros(String s) {
        s = s.replaceAll("^[0]+", "");
        if (s.equals("")) {
            return "0";
        }

        return s;
    }

}

