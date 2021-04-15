package com.example.spinnerinputcontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener, TextWatcher {
    public static final String TAG = "MainActivity";
    public static final int REQUEST_PERMISSION_PHONE_CALL = 1;
    private String spinnerLabel = "";
    EditText editText;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    TextView textViewDiplaySelection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Inside the oncreate");

        textViewDiplaySelection = findViewById(R.id.text_phone_label);
        editText = findViewById(R.id.edit_text_main);
        spinner = findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.phone_labels_array, android.R.layout.simple_spinner_item);
        if (spinner != null) {
            spinner.setOnItemSelectedListener(this);
            spinner.setAdapter(adapter);
        }

        if (editText != null) {
            editText.addTextChangedListener((TextWatcher) this);
        }

        Log.d(TAG, "end of oncreate");
    }

    private void showText() {
        Log.d(TAG, "inside  of showtext mthod");
        if (editText != null) {
            String text = editText.getText().toString();
            if (text.length() > 0) {
                textViewDiplaySelection.setText(spinnerLabel + " : " + text);
            } else {
                textViewDiplaySelection.setText(" No number");
            }
        }

        Log.d(TAG, "end of showtext method");

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Log.d(TAG, "inside of onItemSelected");
        spinnerLabel = adapterView.getItemAtPosition(position).toString();
        showText();
        Log.d(TAG, "end of onItemSelected");


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(TAG, "inside onNothingSelected");

        Log.d(TAG, "end of onNothingSelected");

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        Log.d(TAG, "inside  of beforetext chhange method");

        Log.d(TAG, "end of beforetext chhange method");

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {


    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    public void phoneCall(View view) {
        Log.d(TAG, "inside of  phoneCall method");
        callUsingImpliciteIntent();
        Log.d(TAG, "end of  phoneCall method");

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "inside of  onRequestPermissionsResult method");

        switch (requestCode) {
            case REQUEST_PERMISSION_PHONE_CALL:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, " PermissionsResult granted to place phone call");
                    callUsingImpliciteIntent();

                } else {
                    Log.d(TAG, "Permission denied to place phone call");
                }
                break;
        }
        Log.d(TAG, "end of  onRequestPermissionsResult method");


    }

    private void callUsingImpliciteIntent() {

        Log.d(TAG, "inside of  callUsingImplicateIntent method");
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri uri = Uri.parse("tel:" + editText.getText().toString());
        intent.setData(uri);

        if (intent.resolveActivity(getPackageManager()) != null) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PERMISSION_PHONE_CALL);
            } else {
                //have already a permission so just need to start activity
                startActivity(intent);
            }
        }
        Log.d(TAG, "end of  callUsingImpliciteIntent method");

    }


    public void gotoNextActivity(View view) {
        Log.d(TAG, "inside of  gotoNextActivity method");
        String numAsString = editText.getText().toString();

        if (numAsString.length() > 0) {
            Log.d(TAG, "editText not empty will proceed to next activity");

            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("num", numAsString);
            intent.putExtra("num_type", spinnerLabel);
            startActivity(intent);
        } else {

            Log.d(TAG, "editText for number has NO NUMBER");
            Toast.makeText(this, R.string.text_no_number, Toast.LENGTH_LONG).show();
            editText.setBackgroundColor(getColor(R.color.teal_200));
        }

        Log.d(TAG, "end of gotoNextActivity method");
    }

}