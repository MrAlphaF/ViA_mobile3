package com.example.repositoryg_group3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull // Import firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textView = findViewById<TextView>(R.id.textView)
        val readButton = findViewById<Button>(R.id.readButton)
        val backButton = findViewById<Button>(R.id.backButton)

        // Read button functionality
        readButton.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch { // Best to launch from the Main dispatcher
                // Switch to the IO dispatcher to read from DataStore
                val savedText = withContext(Dispatchers.IO) {
                    DataStoreManager.getText(this@SecondActivity).firstOrNull() // Collect the first value from the Flow
                }

                if (savedText != null) {
                    textView.text = savedText
                } else {
                    Toast.makeText(this@SecondActivity, "Nothing found", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Back button functionality
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
