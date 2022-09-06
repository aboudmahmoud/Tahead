package com.example.taehaed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.taehaed.Pojo.LogIn.LoginRoot;
import com.example.taehaed.Screens.LoginPage;
import com.example.taehaed.Screens.MainPage;
import com.example.taehaed.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

      SetLocalAribc();

        //this make The app take full Screen -> no Status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        Constans.Token = sharedPreferences.getString(Constans.TokenKey, null);

        //Splach View Display logo for 2 second and go to the loginpage
        Thread thread = new Thread() {
            public void run() {

                try {
                    sleep(2000);
                } catch (Exception ex) {

                } finally {
                    if (Constans.Token == null) {

                        Intent intent = new Intent(MainActivity.this, LoginPage.class);

                        startActivity(intent);
                        finish();
                    } else {

                        LoginRoot loginRoot = getLoginRoot(sharedPreferences);
                        if(loginRoot==null)
                        {
                            Log.d("Aboud", "run: "+"null pro");
                        }
                        Intent intent = new Intent(MainActivity.this, MainPage.class);
                        intent.putExtra(Constans.LoginKeyMain, loginRoot);
                        startActivity(intent);
                        finish();
                    }


                }
            }
        };
        thread.start();
    }

    private void SetLocalAribc() {
        Locale locale = new Locale("ar");
        Locale.setDefault(locale);
        Configuration config =
                getBaseContext().getResources().getConfiguration();
        config.setLocale(locale);
        createConfigurationContext(config);
    }

    private LoginRoot getLoginRoot(SharedPreferences sharedPreferences) {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(Constans.ObjectKey, "");
        LoginRoot loginRoot = gson.fromJson(json, LoginRoot.class);
        return loginRoot;
    }


}

