package com.example.deliciousandathome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        View decorView = getWindow().getDecorView(); //скрыть панель навигации
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        LinearLayout temp1 = findViewById(R.id.ll_name1);
        LinearLayout temp2 = findViewById(R.id.ll_name2);
        LinearLayout temp3 = findViewById(R.id.ll_name3);
        LinearLayout temp4 = findViewById(R.id.ll_name4);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Recipes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Recipe recipe = ds.getValue(Recipe.class);
                    FinishRecipes.name.add(recipe.Name);
                    FinishRecipes.ingredients.add(recipe.Ingredients);
                    FinishRecipes.id.add(recipe.id);
                    FinishRecipes.cooking.add(recipe.Cooking);
                    FinishRecipes.category.add(recipe.Category);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        temp1.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenuActivity.this, full_info_about_recipe.class)
                    .putExtra("Category", "salmonSteak");
            View v = findViewById(R.id.cl_anim);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainMenuActivity.this, v, getString(R.string.animIV));
            Bundle bundle = options.toBundle();
            startActivity(intent, bundle);
        });
        temp2.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenuActivity.this, full_info_about_recipe.class)
                    .putExtra("Category", "khachapuri");
            View v = findViewById(R.id.cl_anim1);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainMenuActivity.this, v, getString(R.string.animIV));
            Bundle bundle = options.toBundle();
            startActivity(intent, bundle);
        });
        temp3.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenuActivity.this, full_info_about_recipe.class)
                    .putExtra("Category", "funchosaWithChicken");
            View v = findViewById(R.id.cl_anim2);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainMenuActivity.this, v, getString(R.string.animIV));
            Bundle bundle = options.toBundle();
            startActivity(intent, bundle);
        });
        temp4.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenuActivity.this, full_info_about_recipe.class)
                    .putExtra("Category", "porkStew");
            View v = findViewById(R.id.cl_anim3);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainMenuActivity.this, v, getString(R.string.animIV));
            Bundle bundle = options.toBundle();
            startActivity(intent, bundle);
        });


    }
}



