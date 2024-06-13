package com.example.bakalabazar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.bakalabazar.Models.RegisterModel;
import com.example.bakalabazar.network.NetworkClient;
import com.example.bakalabazar.network.NetworkService;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText name,lname,mobile,email,pass,cpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        lname = findViewById(R.id.lname);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        cpass = findViewById(R.id.cpass);

        findViewById(R.id.sin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }
        });

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name == null || name.getText() == null || name.getText().toString() == null)
                {
                    Toast.makeText(RegisterActivity.this, "Enter Your First Name", Toast.LENGTH_SHORT).show();
                }else if (lname == null || lname.getText() == null || lname.getText().toString() == null)
                {
                    Toast.makeText(RegisterActivity.this, "Enter Your Last Name", Toast.LENGTH_SHORT).show();

                }else if (mobile == null || mobile.getText() == null || mobile.getText().toString() == null || mobile.getText().toString().length() != 10)
                {
                    Toast.makeText(RegisterActivity.this, "Enter Your Number Properly", Toast.LENGTH_SHORT).show();

                }else if (email == null || email.getText() == null || email.getText().toString() == null || !verifyEmail(email.getText().toString()))
                {
                    Toast.makeText(RegisterActivity.this, "Enter Your Email Properly", Toast.LENGTH_SHORT).show();

                }else if (pass == null || pass.getText() == null || pass.getText().toString() == null || pass.getText().toString().length() < 8 || pass.getText().toString().length() > 15)
                {
                    Toast.makeText(RegisterActivity.this, "Enter Your Password Properly", Toast.LENGTH_SHORT).show();

                }else if (cpass == null || cpass.getText() == null || cpass.getText().toString() == null || cpass.getText().toString().length() < 8 || cpass.getText().toString().length() > 15 || !cpass.getText().toString().equals(pass.getText().toString()))
                {
                    Toast.makeText(RegisterActivity.this, "Enter Your Confirm Password Properly", Toast.LENGTH_SHORT).show();

                }else {
                    register(name.getText().toString(),lname.getText().toString().toString(),mobile.getText().toString(),email.getText().toString(),pass.getText().toString());

                }
            }
        });
    }

    void register(String fname,String lname,String mobile,String email,String pass){

        ProgressDialog alertDialog = new ProgressDialog(RegisterActivity.this);
        alertDialog.setTitle("Loading..");
        alertDialog.setTitle("Please Wait...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        HashMap<String,String> map = new HashMap<>();
        map.put("fname",fname);
        map.put("lname",lname);
        map.put("mobile",mobile);
        map.put("email",email);
        map.put("pass",pass);

        NetworkService networkService = NetworkClient.getRetrofit().create(NetworkService.class);
        Call<RegisterModel> call = networkService.registerUser(map);
        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                alertDialog.dismiss();
//                Toast.makeText(RegisterActivity.this, "" + response.body().getMassage(), Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

                 if (response != null && response.body() != null && response.body().getSuccess().equals("1")) {
                    Toast.makeText(RegisterActivity.this, "" + response.body().getMassage(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this, "Registeration Failed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                alertDialog.dismiss();
                Toast.makeText(RegisterActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

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