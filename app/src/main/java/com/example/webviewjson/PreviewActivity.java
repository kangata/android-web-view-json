package com.example.webviewjson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class PreviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        TextView textName = findViewById(R.id.text_name);
        TextView textClass = findViewById(R.id.text_class);

        textName.setText(getIntent().getStringExtra("name"));
        textClass.setText(getIntent().getStringExtra("class"));
    }
}
