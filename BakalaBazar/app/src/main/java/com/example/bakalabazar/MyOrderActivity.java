package com.example.bakalabazar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bakalabazar.Models.OrderModel;
import com.example.bakalabazar.network.NetworkClient;
import com.example.bakalabazar.network.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderActivity extends AppCompatActivity {
    List<OrderModel.DataModel.DataM> list;

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        list = new ArrayList<>();
        rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);




        getData(getSharedPreferences("User",MODE_PRIVATE).getString("email","N/A"));

    }


    void getData(String email) {

        ProgressDialog alertDialog = new ProgressDialog(MyOrderActivity.this);
        alertDialog.setTitle("Loading..");
        alertDialog.setMessage("Please Wait...");
        alertDialog.setCancelable(false);
        alertDialog.show();

        findViewById(R.id.back2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        NetworkService networkService = NetworkClient.getRetrofit().create(NetworkService.class);
        Call<OrderModel> categoryModelCall = networkService.getOrders(email);
        categoryModelCall.enqueue(new Callback<OrderModel>() {
            @Override
            public void onResponse(Call<OrderModel> call, Response<OrderModel> response) {
                alertDialog.dismiss();
                if (response != null && response.body() != null) {
                    list = (List<OrderModel.DataModel.DataM>) response.body().getDataModel().getData();
                    rv.setAdapter(new Myadapter());
                    Toast.makeText(getApplicationContext(), "data success" + list.size(), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "data failed", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<OrderModel> call, Throwable t) {
                alertDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }



    class Myadapter extends RecyclerView.Adapter<Myadapter.ViewHolder>
    {

        @NonNull
        @Override
        public Myadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new Myadapter.ViewHolder(LayoutInflater.from(MyOrderActivity.this).inflate(R.layout.myorder_container,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull Myadapter.ViewHolder holder, int position) {

            holder.id.setText("Order Id :" + list.get(position).getId());
            holder.date.setText("Order Date & Time :" + list.get(position).getDate());
            holder.price.setText("Order Amount :" + list.get(position).getPrice());



        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView id,date,price;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                id = itemView.findViewById(R.id.id);
                date = itemView.findViewById(R.id.datetime);
                price = itemView.findViewById(R.id.price);

            }
        }
    }

}