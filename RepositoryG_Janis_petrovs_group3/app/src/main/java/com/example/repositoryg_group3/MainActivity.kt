package com.example.repositoryg_group3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.repositoryg_group3.DataStoreManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editText)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val goToSecondButton = findViewById<Button>(R.id.goToSecondButton)
        val themeSpinner = findViewById<Spinner>(R.id.themeSpinner)

        // Load saved text from DataStore
        CoroutineScope(Dispatchers.IO).launch {
            val savedText = DataStoreManager.getText(this@MainActivity)
            if (savedText != null) {
                editText.setText(savedText)
            }
        }

        // Setup theme spinner
        val themes = arrayOf("Default", "Dark theme", "Light theme")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, themes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        themeSpinner.adapter = adapter
        themeSpinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                when (themes[position]) {
                    "Dark theme" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    "Light theme" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
                delegate.applyDayNight()
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>?) {}
        }

        // Save button functionality
        saveButton.setOnClickListener {
            val text = editText.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                DataStoreManager.saveText(this@MainActivity, text)
            }
        }

        // Go to second activity
        goToSecondButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }
    }
}