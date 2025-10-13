package com.example.repositoryg__lindabrante_group3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    private TextView show_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button back = findViewById(R.id.backButton);
        Button read_prefs = findViewById(R.id.checkDataStore);
        show_text = findViewById(R.id.showText);

        back.setOnClickListener(v -> {
            Intent myIntent = new Intent(this, MainActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(myIntent);
        });

        read_prefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_data_store();
            }
        });
    }

    public void check_data_store(){
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.SHARED_PREFS, MODE_PRIVATE);

        String saved_text = sharedPreferences.getString(MainActivity.TEXT, null);

        if(saved_text == null || saved_text.isEmpty()){
            Toast.makeText(this, "Preferences are empty", Toast.LENGTH_SHORT).show();
        }else{
            show_text.setText(saved_text);
        }
    }

}

