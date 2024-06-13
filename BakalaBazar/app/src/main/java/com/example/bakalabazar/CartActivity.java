package com.example.bakalabazar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bakalabazar.Models.BakalaModel;
import com.example.bakalabazar.Models.LoginModel;
import com.example.bakalabazar.database.DatabaseHalper;
import com.example.bakalabazar.network.NetworkClient;
import com.example.bakalabazar.network.NetworkService;
import com.example.bakalabazar.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
   RecyclerView rv;
   List<BakalaModel> list;
   TextView total,place;
   DatabaseHalper databaseHalpe;
   LinearLayout noData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        total = findViewById(R.id.total);
        list = new ArrayList<>();
        noData = findViewById(R.id.no_data);
        place = findViewById(R.id.place);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        rv.setHasFixedSize(true);

        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        findViewById(R.id.back1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.place).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    placeorder();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        getData();
    }

    void getData(){

        list.clear();
        databaseHalpe = new DatabaseHalper(CartActivity.this);
        Cursor cursor = databaseHalpe.getData();
        if (cursor.moveToFirst())
        {
            while (cursor.moveToNext())
            {
                list.add(new BakalaModel(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3)));
            }
        }

        total.setText("");
        if (list != null && list.size()>0) {
            rv.setAdapter(new Myadapter());
            getTotal(list);
            rv.setVisibility(View.VISIBLE);
            noData.setVisibility(View.GONE);
            place.setEnabled(true);
            place.setAlpha(1f);

        }else{
            rv.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
            place.setEnabled(false);
            place.setAlpha(.5f);
        }
    }

    class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder>
    {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(CartActivity.this).inflate(R.layout.cart_container,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            holder.textView.setText(list.get(position).getName());
            holder.price.setText(list.get(position).getPrice());
            Glide.with(CartActivity.this).load(list.get(position).getPhoto()).into(holder.imageView);

            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Dialog dialog = new Dialog(CartActivity.this);
                    dialog.setContentView(R.layout.delete_dialog);
                    dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            databaseHalpe.deleteItem(list.get(position).getId());
                            getData();
                            notifyDataSetChanged();
                            getTotal(list);
                            dialog.dismiss();
                        }
                    });

                    dialog.show();

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView textView,price;
            CardView container;
            ImageView imageView,remove;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.name);
                container = itemView.findViewById(R.id.container_main);
                price = itemView.findViewById(R.id.price);
                imageView = itemView.findViewById(R.id.image);
                remove = itemView.findViewById(R.id.remove);
            }
        }
    }

    void getTotal(List<BakalaModel> list)
    {
        long price = 0;
        for (int i=0;i < list.size() ; i++)
        {
           price += Integer.valueOf(list.get(i).getPrice());
        }
        total.setText("₹"+price+"");
    }

    void placeorder(){
        Constants.list = list;
        startActivity(new Intent(CartActivity.this,PlaceOrderActivity.class).putExtra("price",total.getText().toString()));


    }

}