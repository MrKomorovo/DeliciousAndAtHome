package com.example.deliciousandathome;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class full_info_about_recipe extends AppCompatActivity {
    List<Integer> picID;
    List<String> picName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_info_about_recipe);
        setContentView(R.layout.activity_main_menu);

        ImageView aboba = findViewById(R.id.aboba);
        TextView tv_name = findViewById(R.id.tv_name);

        Bundle bundle = getIntent().getExtras();
        picID = new ArrayList<>();
        picName = new ArrayList<>();
        picID.add(R.drawable.img1);
        picID.add(R.drawable.img2);
        picID.add(R.drawable.img3);
        picID.add(R.drawable.img4);
        picName.add("salmonSteak");
        picName.add("khachapuri");
        picName.add("funchosaWithChicken");
        picName.add("porkStew");

        tv_name.setText(bundle.getString("Category"));

        for(int i = 0; i < picID.size(); i++){
            if(picName.get(i).equals(bundle.getString("Category"))){
                aboba.setImageResource(picID.get(i));
                break;
            }
        }
    }
}