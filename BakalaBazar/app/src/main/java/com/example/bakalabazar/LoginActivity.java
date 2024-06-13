package com.example.bakalabazar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.bakalabazar.Models.LoginModel;
import com.example.bakalabazar.Models.RegisterModel;
import com.example.bakalabazar.network.NetworkClient;
import com.example.bakalabazar.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText email,pass;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences("User",MODE_PRIVATE);
        editor = preferences.edit();

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);


        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email == null || email.getText() == null || email.getText().toString() == null || !verifyEmail(email.getText().toString()))
                {
                    Toast.makeText(LoginActivity.this, "Enter Your Email Properly", Toast.LENGTH_SHORT).show();

                }else if (pass == null || pass.getText() == null || pass.getText().toString() == null || pass.getText().toString().length() < 8 || pass.getText().toString().length() > 15)
                {
                    Toast.makeText(LoginActivity.this, "Enter Your Password Properly", Toast.LENGTH_SHORT).show();

                }else {
                    login(email.getText().toString(),pass.getText().toString());
                }
            }
        });

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    void login(String email,String pass){

        ProgressDialog alertDialog = new ProgressDialog(LoginActivity.this);
        alertDialog.setTitle("Loading..");
        alertDialog.setMessage("Please Wait...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        NetworkService networkService = NetworkClient.getRetrofit().create(NetworkService.class);
        Call<LoginModel> call = networkService.loginUser(email,pass);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                alertDialog.dismiss();
                Toast.makeText(LoginActivity.this, "" + response.body().getMassage(), Toast.LENGTH_SHORT).show();
                editor.putString("first",response.body().getData().getFirst());
                editor.putString("last",response.body().getData().getLast());
                editor.putString("mobile",response.body().getData().getMobile());
                editor.putString("email",response.body().getData().getEmail());
                editor.apply();
                startActivity(new Intent(LoginActivity.this,MainActivity.class));

               /*    if (response != null && response.body() != null && response.body().getSuccess().equals("1")) {
                    Toast.makeText(LoginActivity.this, "" + response.body().getMassage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                }
*/            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                alertDialog.dismiss();
                Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    boolean verifyEmail(String email){

        String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(emailpattern) && email.length() > 0){
            return true;
        }

        return false;
    }

}