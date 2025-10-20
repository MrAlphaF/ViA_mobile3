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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editText)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val goToSecondButton = findViewById<Button>(R.id.goToSecondButton)
        val themeSpinner = findViewById<Spinner>(R.id.themeSpinner)

        // Load saved text and theme from DataStore
        CoroutineScope(Dispatchers.IO).launch {
            val savedText = DataStoreManager.getText(this@MainActivity)?.first()
            val savedTheme = DataStoreManager.getTheme(this@MainActivity).first()
            withContext(Dispatchers.Main) {
                editText.setText(savedText)
                // Apply the saved theme when the activity is created
                applyTheme(savedTheme)
            }
        }

        // Setup theme spinner
        val themes = arrayOf("Default", "Dark theme", "Light theme")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, themes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        themeSpinner.adapter = adapter
        themeSpinner.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                val selectedTheme = themes[position]
                applyTheme(selectedTheme)
                // Save the selected theme
                CoroutineScope(Dispatchers.IO).launch {
                    DataStoreManager.saveTheme(this@MainActivity, selectedTheme)
                }
            } // <- ADDED: Missing closing brace for onItemSelected
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

    private fun applyTheme(theme: String?) { // Allow theme to be nullable
        when (theme) {            "Light theme" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "Dark theme" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) // Handles "Default" and null
        }
    }


}
