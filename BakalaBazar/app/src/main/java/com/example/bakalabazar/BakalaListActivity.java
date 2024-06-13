package com.example.bakalabazar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bakalabazar.Models.BakalaModel;
import com.example.bakalabazar.Models.BakalaResponseModel;
import com.example.bakalabazar.network.NetworkClient;
import com.example.bakalabazar.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BakalaListActivity extends AppCompatActivity {

    RecyclerView rv;
    List<BakalaModel> list;
    TextView hader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bakala_list);

        hader = findViewById(R.id.hader);

        if (getIntent() != null)
        hader.setText(getIntent().getStringExtra("name"));

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(BakalaListActivity.this));
        rv.setHasFixedSize(true);
        list = new ArrayList<>();

        ProgressDialog alertDialog = new ProgressDialog(BakalaListActivity.this);
        alertDialog.setTitle("Loading..");
        alertDialog.setMessage("Please Wait...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        NetworkService networkService = NetworkClient.getRetrofit().create(NetworkService.class);
        Call<BakalaResponseModel> call = networkService.getBakala(getIntent().getStringExtra("id"));
        call.enqueue(new Callback<BakalaResponseModel>() {
            @Override
            public void onResponse(Call<BakalaResponseModel> call, Response<BakalaResponseModel> response) {
                alertDialog.dismiss();
                if (response != null && response.body() != null)
                {
                    list = response.body().getBakalas();
                    rv.setAdapter(new BakalaAdapter());
                }

            }
            @Override
            public void onFailure(Call<BakalaResponseModel> call, Throwable t) {
                alertDialog.dismiss();
                Toast.makeText(BakalaListActivity.this, "data failed", Toast.LENGTH_SHORT).show();


            }
        });
    }


    class BakalaAdapter extends RecyclerView.Adapter<BakalaAdapter.ViewHolder>{


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(BakalaListActivity.this).inflate(R.layout.bakalacontainer,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            Glide.with(BakalaListActivity.this).load(list.get(position).getPhoto()).into(holder.imageView);
            holder.name.setText(list.get(position).getName());
            holder.price.setText(list.get(position).getPrice());

            holder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BakalaListActivity.this,BakalaDetailActivity.class);
                    intent.putExtra("name",list.get(position).getName());
                    intent.putExtra("price",list.get(position).getPrice());
                    intent.putExtra("des",list.get(position).getDescription());
                    intent.putExtra("photo",list.get(position).getPhoto());
                    startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {

            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            CardView main;
            ImageView imageView;
            TextView  name,price;


            public ViewHolder(@NonNull View view) {
                super(view);

                imageView = view.findViewById(R.id.image);

                main = view.findViewById(R.id.container_main);
                name = view.findViewById(R.id.name);
                price = view.findViewById(R.id.price);



            }
        }
    }
}