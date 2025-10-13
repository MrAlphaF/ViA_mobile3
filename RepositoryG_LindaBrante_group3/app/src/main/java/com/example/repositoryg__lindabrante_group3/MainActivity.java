package com.example.repositoryg__lindabrante_group3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView show_text;
    private EditText enter_text;
    private String text;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button save = findViewById(R.id.saveText);
        show_text = findViewById(R.id.show_text);
        enter_text = findViewById(R.id.text_enter);
        Spinner theme_select = findViewById(R.id.select_theme);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_text.setText(enter_text.getText().toString());
                saveData();
            }
        });

        theme_select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                if(item.toString().equals("Dark theme")){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        loadData();
        updateViews();
    }

    public void goTo2nd(View v){
        Intent goTo2ndIntent = new Intent(this, MainActivity2.class);
        startActivity(goTo2ndIntent);
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(TEXT, show_text.getText().toString());
        editor.apply();

        Toast.makeText(this, "Text saved in data store", Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(TEXT, null);
    }

    public void updateViews(){
        show_text.setText(text);
    }
}
