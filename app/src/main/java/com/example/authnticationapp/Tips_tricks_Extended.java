package com.example.authnticationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Tips_tricks_Extended extends AppCompatActivity {

    ImageView imageviewGrid;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_tricks__extended);
    imageviewGrid = findViewById(R.id.imageviewGrid);
    textView = findViewById(R.id.defense_id);

        Intent intent = getIntent();
        if (intent.getExtras() != null)
        {
            String selectedNames = intent.getStringExtra("names");
            int selectedImages = intent.getIntExtra("images",0);
            textView.setText(selectedNames);
            imageviewGrid.setImageResource(selectedImages);
        }
    }
}
