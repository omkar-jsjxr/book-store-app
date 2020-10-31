package com.mypack.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity {
private Button cat1,cat2,cat3,cat4,cat5,cat6,cat7,cat8,cat9,cat10,cat11,cat12,cat13,cat14,cat15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CategoryActivity.this,CategoryActivity.class);
                intent.putExtra("category","cat1");
                startActivity(intent);
            }
        });
        cat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CategoryActivity.this,CategoryActivity.class);
                intent.putExtra("category","cat1");
                startActivity(intent);
            }
        });
        cat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CategoryActivity.this,CategoryActivity.class);
                intent.putExtra("category","cat1");
                startActivity(intent);
            }
        });
        cat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CategoryActivity.this,CategoryActivity.class);
                intent.putExtra("category","cat1");
                startActivity(intent);
            }
        });
        cat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CategoryActivity.this,CategoryActivity.class);
                intent.putExtra("category","cat1");
                startActivity(intent);
            }
        });
        cat6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CategoryActivity.this,CategoryActivity.class);
                intent.putExtra("category","cat1");
                startActivity(intent);
            }
        });
        cat7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CategoryActivity.this,CategoryActivity.class);
                intent.putExtra("category","cat1");
                startActivity(intent);
            }
        });
        cat8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CategoryActivity.this,CategoryActivity.class);
                intent.putExtra("category","cat1");
                startActivity(intent);
            }
        });
        cat9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CategoryActivity.this,CategoryActivity.class);
                intent.putExtra("category","cat1");
                startActivity(intent);
            }
        });
        cat10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CategoryActivity.this,CategoryActivity.class);
                intent.putExtra("category","cat1");
                startActivity(intent);
            }
        });
        cat11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CategoryActivity.this,CategoryActivity.class);
                intent.putExtra("category","cat1");
                startActivity(intent);
            }
        });
        cat12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CategoryActivity.this,CategoryActivity.class);
                intent.putExtra("category","cat1");
                startActivity(intent);
            }
        });
        cat13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CategoryActivity.this,CategoryActivity.class);
                intent.putExtra("category","cat1");
                startActivity(intent);
            }
        });
        cat15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CategoryActivity.this,CategoryActivity.class);
                intent.putExtra("category","cat1");
                startActivity(intent);
            }
        });
        cat14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CategoryActivity.this,CategoryActivity.class);
                intent.putExtra("category","cat1");
                startActivity(intent);
            }
        });


    }
}