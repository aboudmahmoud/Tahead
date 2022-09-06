
package com.example.taehaed.Screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.taehaed.Constans;
import com.example.taehaed.Model.TaehaedVModel;
import com.example.taehaed.Pojo.UserData;
import com.example.taehaed.R;
import com.example.taehaed.databinding.ActivityLoginPageBinding;
import com.google.gson.Gson;

public class LoginPage extends AppCompatActivity {
    private ActivityLoginPageBinding binding;
    private TaehaedVModel taehaedVModel;

    //This To Store Token
    private SharedPreferences.Editor edit;
    private SharedPreferences sp;

    //this Insated of ProggesBar
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       //this make The app take full Screen -> no Status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sp = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        edit = sp.edit();
        taehaedVModel = new ViewModelProvider(this).get(TaehaedVModel.class);



        SetFieldsGriveAndHints();


        binding.btnLogin.setOnClickListener(view -> {

            if(binding.Email.getText().toString().isEmpty())
            {
               binding.Email.setError(getString(R.string.DontLetEMpaty));
               return;
            }

            if(binding.passworded.getText().toString().isEmpty())
            {
                binding.passworded.setError(getString(R.string.DontLetEMpaty));
                return;
            }

            UserData userData = new UserData(binding.Email.getText().toString(), binding.passworded.getText().toString());
            alertDialog =Constans.setAlertMeaage(getString(R.string.Login),LoginPage.this);

            //Here To make Display touch for User
            alertDialog.show();
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            //Here Is The Procces Of Login
            taehaedVModel.InsertLogin(userData, (status, Token, ErrorMassge) -> {
                if (status) {
                    //Here We Store the Data InSheradPranfce
                    edit.putString(Constans.TokenKey, Token);

                    Gson gson = new Gson();
                    //Convert Logn root  to josn So I can Save it in shared Perefnse
                    String json = gson.toJson(taehaedVModel.UserData.getValue());
                    edit.putString(Constans.ObjectKey, json);
                    edit.commit();
                    edit.apply();
                    alertDialog.dismiss();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Intent intent = new Intent(LoginPage.this, MainPage.class);
                    intent.putExtra(Constans.LoginKeyLoginPage, taehaedVModel.UserData.getValue());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),getString( R.string.error)+" \n"+ ErrorMassge , Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
            });


        });


    }

    private void SetFieldsGriveAndHints() {
        //this code for make hint text disapper  on fouces mode and diplay on non foucse mode
        binding.passworded.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                binding.passworded.setHint("");
            else
                binding.passworded.setHint("كلمة السر");

        });

        //this code for make hint text disapper  on fouces mode and diplay on non foucse mode
        binding.Email.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                binding.Email.setHint("");
            else {
                binding.Email.setHint("البريد الالكتروني");
            }
        });

        /*Problem : Password mode is alsways in start Grvaity
        and the hint is Arabic and Arabis is in the End Grvaity
        So To fix This problem
        in xml edit text is grvit end
        and use this code to check textlister when write arabic , givity end
        when is write en grivity start
        * */
        binding.passworded.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!isProbablyArabic(charSequence + "")) {
                    binding.passworded.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                } else {
                    binding.passworded.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    //this method to chek is String is Arabic or Not
    public static boolean isProbablyArabic(@NonNull String s) {
        for (int i = 0; i < s.length(); ) {
            int c = s.codePointAt(i);
            if (c >= 0x0600 && c <= 0x06E0)
                return true;
            i += Character.charCount(c);
        }
        return false;
    }



}