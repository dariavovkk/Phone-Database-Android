package com.jilquero.project2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class InsertActivity extends AppCompatActivity {
    Button button_web;
    Button button_cancel;
    Button button_save;
    EditText ET_manufacturer;
    EditText ET_model;
    EditText ET_version;
    EditText ET_web;
    private boolean is_manufacturer = false;
    private boolean is_model = false;
    private boolean is_version = false;
    private boolean is_web = false;
    public static final String PHONE_RESULT = "phone_result";
    public static final int SAVE = 1;
    public static final int UPDATE = 2;
    Phone item = null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        button_web = findViewById(R.id.btn_Website);
        button_cancel = findViewById(R.id.btn_Cancel);
        button_save = findViewById(R.id.btn_Save);
        ET_manufacturer = findViewById(R.id.ET_Manufacturer);
        ET_model = findViewById(R.id.ET_Model);
        ET_version = findViewById(R.id.ET_AndroidVersion);
        ET_web = findViewById(R.id.ETWebSite);
        item = (Phone) getIntent().getSerializableExtra(MainActivity.PHONEUPDATE_RESULT);
        isValid();
        System.out.println(item);
        button_cancel.setOnClickListener(view -> {
            finish();
        });
        button_web.setOnClickListener(view -> {
            openWebpage();
        });
        ET_manufacturer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ET_manufacturer.getText().toString().isEmpty()){
                    is_manufacturer = false;
                    return;
                }
                is_manufacturer = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                isValid();
            }
        });
        ET_manufacturer.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if(!is_manufacturer && !hasFocus) {
                            ET_manufacturer.setError(getString(R.string.error_manufacturer));
                        }
                    }
                }
        );
        ET_web.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ET_web.getText().toString().isEmpty()) {
                    is_web = false;
                    return;
                }
                is_web = true;
            }
            @Override
            public void afterTextChanged(Editable s) {
                isValid();
            }
        });
        ET_web.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if(!is_web && !hasFocus){
                            ET_web.setError(getString(R.string.error_website));
                        }
                    }
                }
        );
        ET_version.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ET_version.getText().toString().isEmpty()) {
                    is_version = false;
                    return;
                }
                is_version = true;
            }
            @Override
            public void afterTextChanged(Editable s) {
                isValid();
            }
        });
        ET_version.setOnFocusChangeListener(
                new View.OnFocusChangeListener(){
                    @Override
                    public void onFocusChange(View view, boolean hasFocus){
                        if(!is_version && !hasFocus) {
                            ET_version.setError(getString(R.string.error_version));
                        };
                    }
                }
        );
        ET_model.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ET_model.getText().toString().isEmpty()) {
                    is_model = false;
                    return;
                }
                is_model = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                isValid();
            }
        });
        ET_model.setOnFocusChangeListener(
                new View.OnFocusChangeListener(){
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if(!is_model && !hasFocus){
                            ET_model.setError(getString(R.string.error_model));
                        }
                    }
                }
        );
        if(item == null) {
            button_save.setOnClickListener(view -> {
                backToMainSave();
            });
            return;
        }
        ET_manufacturer.setText(item.getManufacturer());
        ET_model.setText(item.getModel());
        ET_version.setText(String.valueOf(item.getAndroidVersion()));
        ET_web.setText(item.getWebsite());
        button_save.setOnClickListener(view -> {
            backToMainUpdate();
        });
    }
    private void backToMainSave(){
        Intent intent = new Intent();
        String manufacturer = ET_manufacturer.getText().toString();
        String model = ET_model.getText().toString();
        int androidVersion = Integer.parseInt(ET_version.getText().toString());
        String link = ET_web.getText().toString();
        Phone item = new Phone(manufacturer, model, androidVersion, link);
        intent.putExtra(PHONE_RESULT, item);
        setResult(SAVE, intent);
        finish();
    }
    private void backToMainUpdate(){
        Intent intent = new Intent();
        item.setManufacturer(ET_manufacturer.getText().toString());
        item.setModel(ET_model.getText().toString());
        item.setAndroidVersion(Integer.parseInt(ET_version.getText().toString()));
        item.setWebsite(ET_web.getText().toString());
        intent.putExtra(PHONE_RESULT, item);
        setResult(UPDATE, intent);
        finish();
    }
    private void openWebpage(){
        Intent Browser = new Intent("android.intent.action.VIEW", Uri.parse(ET_web.getText().toString()));
        startActivity(Browser);
    }
    private void isValid(){
        if(is_manufacturer && is_model && is_version && is_web){
            button_save.setEnabled(true);
            return;
        }
        button_save.setEnabled(false);
    }
}
