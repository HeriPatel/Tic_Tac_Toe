package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class first_pg extends AppCompatActivity {

    EditText ed1,ed2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_pg);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_first_pg);

        ed1 = findViewById(R.id.p1);
        ed2 = findViewById(R.id.p2);
    }

    public void go(View view) {
        Log.d("HMP",ed1.getText().toString());
        Toast.makeText(this,"X for ".concat(ed1.getText().toString().concat(" and O for ").concat(ed2.getText().toString())),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}