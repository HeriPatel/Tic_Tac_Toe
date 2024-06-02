package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class first extends AppCompatActivity {

    EditText ed1,ed2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_first);

        ed1 = findViewById(R.id.p1);
        ed2 = findViewById(R.id.p2);
    }

    public void go(View view) {
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        String v1 = ed1.getText().toString();
        String v2 = ed2.getText().toString();
        intent.putExtra("arg1",v1);
        intent.putExtra("arg2",v2);
        startActivity(intent);
    }
}