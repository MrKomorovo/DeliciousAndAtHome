package com.example.deliciousandathome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    boolean isAuthorization;
    boolean spIsAuthorization;
    private DatabaseReference mDatabase;
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View decorView = getWindow().getDecorView(); //скрыть панель навигации
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        LinearLayout ll_logo = findViewById(R.id.ll_logo);
        LinearLayout ll_change_act = findViewById(R.id.ll_change_enter);
        LinearLayout ll_logo_authorization = findViewById(R.id.ll_logo_authorization);
        LinearLayout ll_authorization = findViewById(R.id.ll_authorization);
        LinearLayout bt_authorization = findViewById(R.id.ll_bt_authorization);
        LinearLayout ll_logo_reg = findViewById(R.id.ll_logo_reg);
        LinearLayout ll_registration = findViewById(R.id.ll_registration);
        LinearLayout bt_reg = findViewById(R.id.ll_bt_reg);
        ImageView bt_back = findViewById(R.id.iv_bt_back);
        EditText et_authorization_login = findViewById(R.id.et_authorization_login);
        EditText et_authorization_password = findViewById(R.id.et_authorization_password);
        EditText et_reg_name = findViewById(R.id.et_reg_name);
        EditText et_reg_login = findViewById(R.id.et_reg_login);
        EditText et_reg_password = findViewById(R.id.et_reg_password);
        EditText et_reg_repeat_password = findViewById(R.id.et_reg_repeat_password);
        TextView bt_authorization_enter = findViewById(R.id.bt_authorization_enter);
        TextView bt_reg_enter = findViewById(R.id.bt_reg_enter);
        TextView tv_autho_invalid_text = findViewById(R.id.tv_autho_invalid_text);
        TextView tv_reg_invalid_text = findViewById(R.id.tv_reg_invalid_text);
        RadioButton rb_rememberMe = findViewById(R.id.rb_remember_me);

        settings = getSharedPreferences("APP_PREFERENCES", MODE_PRIVATE);

        //Проверка авторизирован ли пользователь
        if(settings.getBoolean("isAuth", false)){
            Intent intent = new Intent(MainActivity.this, MainMenuActivity.class)
                    .putExtra("Name", settings.getString("Name", null));
            startActivity(intent);
            finish();
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        //Переход на другое Activity после авторизации
        bt_authorization_enter.setOnClickListener(view -> {
            if(et_authorization_login.getText().length() > 0 && et_authorization_password.getText().length() > 0) {
                mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User userForShare = new User();
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            User user = ds.getValue(User.class);
                            if (et_authorization_login.getText().toString().equals(user.Login) &&
                                    et_authorization_password.getText().toString().equals(user.Password)) {
                                isAuthorization = true;

                                userForShare = user;
                                break;
                            } else
                                isAuthorization = false;
                        }
                        if (isAuthorization) {
                            if(rb_rememberMe.isActivated()) {
                                editor = settings.edit();
                                editor.putBoolean("isAuth", true);
                                editor.putString("Name", userForShare.Nick);
                                editor.commit();
                            }
                            Intent intent = new Intent(MainActivity.this, MainMenuActivity.class).
                                    putExtra("userName", userForShare.Nick);
                            startActivity(intent);
                            finish();
                        } else {
                            tv_autho_invalid_text.setText("Неправильный логин или пароль");
                            et_authorization_login.clearFocus();
                            et_authorization_password.clearFocus();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
        });

        //Кнопка регистрации на регистрации
        bt_reg_enter.setOnClickListener(view -> {
            if(et_reg_login.getText().length() >= 4 && et_reg_password.getText().length() >= 4 &&
                    et_reg_repeat_password.getText().length() >= 4 && et_reg_name.getText().length() >= 4){
                if(et_reg_password.getText().toString().equals(et_reg_repeat_password.getText().toString())){
                    Toast.makeText(MainActivity.this, "Регистрация успешна\nТеперь войдите в свой аккаунт", Toast.LENGTH_LONG).show();
                    User user = new User(et_reg_name.getText().toString(), et_reg_login.getText().toString(), et_reg_password.getText().toString());
                    mDatabase.child("Users").push().setValue(user);
                    ll_logo.setVisibility(View.GONE);
                    ll_change_act.setVisibility(View.GONE);
                    ll_logo_authorization.setVisibility(View.VISIBLE);
                    ll_authorization.setVisibility(View.VISIBLE);
                    bt_back.setVisibility(View.VISIBLE);
                    ll_registration.setVisibility(View.GONE);
                    ll_logo_reg.setVisibility(View.GONE);
                }
                else{
                    //пароли не совпадают
                    tv_reg_invalid_text.setText("Пароли не совпадают");
                    et_reg_login.clearFocus();
                    et_reg_password.clearFocus();
                    et_reg_repeat_password.clearFocus();
                    et_reg_name.clearFocus();
                }
            }
            else{
                //Надписи про не все данные при регистрации
                if(et_reg_login.getText().length() == 0 || et_reg_password.getText().length() == 0 || et_reg_repeat_password.getText().length() == 0 ||
                        et_reg_name.getText().length() == 0) {
                    tv_reg_invalid_text.setText("Вы ввели не все даныне");
                    et_reg_login.clearFocus();
                    et_reg_password.clearFocus();
                    et_reg_repeat_password.clearFocus();
                    et_reg_name.clearFocus();
                }
                else {
                    //Данные меньше 4 символов
                    if (et_reg_login.getText().length() < 4 || et_reg_password.getText().length() < 4 || et_reg_repeat_password.getText().length() < 4 ||
                            et_reg_name.getText().length() < 4) {
                        tv_reg_invalid_text.setText("Данные должны быть больше 4 символов");
                    }
                }
            }
        });

        //Переход на авторизацию
        bt_authorization.setOnClickListener(view -> {
            ll_logo.setVisibility(View.GONE);
            ll_change_act.setVisibility(View.GONE);
            ll_logo_authorization.setVisibility(View.VISIBLE);
            ll_authorization.setVisibility(View.VISIBLE);
            bt_back.setVisibility(View.VISIBLE);

        });

        //Переход на регистрацию
        bt_reg.setOnClickListener(view -> {
            ll_logo.setVisibility(View.GONE);
            ll_change_act.setVisibility(View.GONE);
            ll_logo_reg.setVisibility(View.VISIBLE);
            ll_registration.setVisibility(View.VISIBLE);
            bt_back.setVisibility(View.VISIBLE);
        });

        //Кнопка назад
        bt_back.setOnClickListener(view -> {
            if(ll_authorization.getVisibility() == View.VISIBLE){
                ll_authorization.setVisibility(View.GONE);
                ll_logo_authorization.setVisibility(View.GONE);
            }
            else{
                ll_logo_reg.setVisibility(View.GONE);
                ll_registration.setVisibility(View.GONE);
            }
            ll_logo.setVisibility(View.VISIBLE);
            ll_change_act.setVisibility(View.VISIBLE);
            bt_back.setVisibility(View.GONE);
            et_authorization_login.setText("");
            et_authorization_password.setText("");
            et_reg_login.setText("");
            et_reg_name.setText("");
            et_reg_password.setText("");
            et_reg_repeat_password.setText("");
        });
    }
}