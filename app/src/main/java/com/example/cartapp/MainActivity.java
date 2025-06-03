package com.example.cartapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProductAdapter adapter;
    List<Product> productList;
    Button viewCartBtn;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchButton = findViewById(R.id.searchButton);
        viewCartBtn = findViewById(R.id.viewCartBtn);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList = new ArrayList<>();
        productList.add(new Product(R.drawable.prod1, "Canon PowerShot SX740", "$ 119"));
        productList.add(new Product(R.drawable.prod2, "Nikon Coolpix B500", "$ 149"));
        productList.add(new Product(R.drawable.prod3, "Sony Cyber-shot DSC-H300", "$ 95"));
        productList.add(new Product(R.drawable.prod4, "Fujifilm Instax Mini 11", "$ 69"));
        productList.add(new Product(R.drawable.prod5, "Panasonic Lumix ZS70", "$ 179"));
        productList.add(new Product(R.drawable.prod6, "Polaroid Originals OneStep+", "$ 129"));
        productList.add(new Product(R.drawable.prod7, "Olympus Tough TG-6", "$ 249"));
        productList.add(new Product(R.drawable.prod8, "GoPro HERO9 Black", "$ 199"));
        productList.add(new Product(R.drawable.prod9, "Leica Sofort Instant Camera", "$ 229"));


        adapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Searching...", Toast.LENGTH_SHORT).show();
            }
        });

        viewCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "View your Cart", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, Cart.class);

                startActivity(intent);
            }
        });

    }
}