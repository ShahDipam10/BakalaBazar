package com.example.bakalabazar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bakalabazar.Models.Categories;
import com.example.bakalabazar.Models.CategoryModel;
import com.example.bakalabazar.network.NetworkClient;
import com.example.bakalabazar.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    List<Categories> list;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        list = new ArrayList<>();

        ProgressDialog alertDialog = new ProgressDialog(CategoryActivity.this);
        alertDialog.setTitle("Loading..");
        alertDialog.setMessage("Please Wait...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        NetworkService networkService = NetworkClient.getRetrofit().create(NetworkService.class);
        Call<CategoryModel> categoryModelCall = networkService.getCategory();
        categoryModelCall.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                alertDialog.dismiss();
                if (response != null && response.body() != null) {
                    list = response.body().getCategories();
                    rv.setAdapter(new Myadapter());
                }
                else {
                    Toast.makeText(CategoryActivity.this, "data failed", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                alertDialog.dismiss();
                Toast.makeText(CategoryActivity.this, "data get", Toast.LENGTH_SHORT).show();

            }
        });

    }
    class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder>
    {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(CategoryActivity.this).inflate(R.layout.category_container,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            holder.textView.setText(list.get(position).getName());

            holder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CategoryActivity.this,BakalaListActivity.class);
                    intent.putExtra("id",list.get(holder.getAdapterPosition()).getId());
                    intent.putExtra("name",list.get(position).getName());
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView textView;
            CardView container;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                textView = itemView.findViewById(R.id.txt);
                container = itemView.findViewById(R.id.container);
            }
        }
    }
}