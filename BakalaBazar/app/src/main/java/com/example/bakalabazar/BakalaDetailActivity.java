package com.example.bakalabazar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bakalabazar.Models.BakalaModel;
import com.example.bakalabazar.database.DatabaseHalper;

import java.util.ArrayList;
import java.util.List;

public class BakalaDetailActivity extends AppCompatActivity {
    ImageView imageView;
    TextView name,price,des;
    private Spinner  spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakala_detail);

        addItemsOnSpinner2();
        addListenerOnSpinnerItemSelection();

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHalper databaseHalper = new DatabaseHalper(getApplicationContext());
                BakalaModel bakalaModel = new BakalaModel();
                bakalaModel.setName(getIntent().getStringExtra("id"));
                bakalaModel.setName(getIntent().getStringExtra("name"));
                bakalaModel.setPrice(getIntent().getStringExtra("price"));
                bakalaModel.setPhoto(getIntent().getStringExtra("photo"));
                databaseHalper.insert(bakalaModel);
                Toast.makeText(getApplicationContext(), "Item Added To Cart.", Toast.LENGTH_SHORT).show();
            }
        });

        imageView = findViewById(R.id.image);
        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        des = findViewById(R.id.dec);

        Glide.with(BakalaDetailActivity.this).load(getIntent().getStringExtra("photo")).into(imageView);
        name.setText(getIntent().getStringExtra("name"));
        des.setText(getIntent().getStringExtra("des"));
    }

    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();
        list.add("1 kg : ₹40");
        list.add("500 g : ₹20");
        list.add("250 g  : ₹10");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void addListenerOnButton() {


        spinner2 = (Spinner) findViewById(R.id.spinner2);


        spinner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(BakalaDetailActivity.this,
                        "OnClickListener : " +

                                "\nSpinner 2 : "+ String.valueOf(spinner2.getSelectedItem()),
                        Toast.LENGTH_SHORT).show();
            }

        });
    }

}