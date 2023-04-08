package com.example.deliciousandathome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class full_info_about_recipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_info_about_recipe);

        TextView aboba = findViewById(R.id.aboba);
        aboba.setText(FinishRecipes.name.get(0));
    }
}