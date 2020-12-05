package com.mypack.bookapp.Seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mypack.bookapp.R;

public class SellerbookcategoryActivity extends AppCompatActivity {
private Button cat1,cat2,cat3,cat4,cat5,cat6,cat7,cat8,cat9,cat10,cat11,cat12,cat13,cat14,cat15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellerbookcategory);
        cat1=(Button)findViewById(R.id.cat1);
        cat2=(Button)findViewById(R.id.cat2);
        cat3=(Button)findViewById(R.id.cat3);
        cat4=(Button)findViewById(R.id.cat4);
        cat5=(Button)findViewById(R.id.cat5);
        cat6=(Button)findViewById(R.id.cat6);
        cat7=(Button)findViewById(R.id.cat7);
        cat8=(Button)findViewById(R.id.cat8);
        cat9=(Button)findViewById(R.id.cat9);
        cat10=(Button)findViewById(R.id.cat10);
        cat11=(Button)findViewById(R.id.cat11);
        cat12=(Button)findViewById(R.id.cat12);
        cat13=(Button)findViewById(R.id.cat13);
        cat14=(Button)findViewById(R.id.cat14);
        cat15=(Button)findViewById(R.id.cat15);


        cat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SellerbookcategoryActivity.this, SelleraddnewbookActivity.class);
                intent.putExtra("category","action and adventure");
                startActivity(intent);
            }
        });
        cat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SellerbookcategoryActivity.this, SelleraddnewbookActivity.class);
                intent.putExtra("category","art and architecture");
                startActivity(intent);
            }
        });
        cat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SellerbookcategoryActivity.this, SelleraddnewbookActivity.class);
                intent.putExtra("category","biography and autobiography");
                startActivity(intent);
            }
        });
        cat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SellerbookcategoryActivity.this, SelleraddnewbookActivity.class);
                intent.putExtra("category","comic books");
                startActivity(intent);
            }
        });
        cat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SellerbookcategoryActivity.this, SelleraddnewbookActivity.class);
                intent.putExtra("category","cookbook");
                startActivity(intent);
            }
        });
        cat6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SellerbookcategoryActivity.this, SelleraddnewbookActivity.class);
                intent.putExtra("category","detective and mystery");
                startActivity(intent);
            }
        });
        cat7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SellerbookcategoryActivity.this, SelleraddnewbookActivity.class);
                intent.putExtra("category","fairy tales");
                startActivity(intent);
            }
        });
        cat8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SellerbookcategoryActivity.this, SelleraddnewbookActivity.class);
                intent.putExtra("category","history books");
                startActivity(intent);
            }
        });
        cat9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SellerbookcategoryActivity.this, SelleraddnewbookActivity.class);
                intent.putExtra("category","horror books");
                startActivity(intent);
            }
        });
        cat10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SellerbookcategoryActivity.this, SelleraddnewbookActivity.class);
                intent.putExtra("category","maths");
                startActivity(intent);
            }
        });
        cat11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SellerbookcategoryActivity.this, SelleraddnewbookActivity.class);
                intent.putExtra("category","motivational books");
                startActivity(intent);
            }
        });
        cat12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SellerbookcategoryActivity.this, SelleraddnewbookActivity.class);
                intent.putExtra("category","poetry");
                startActivity(intent);
            }
        });
        cat13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SellerbookcategoryActivity.this, SelleraddnewbookActivity.class);
                intent.putExtra("category","suspense and thriller");
                startActivity(intent);
            }
        });
        cat14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SellerbookcategoryActivity.this, SelleraddnewbookActivity.class);
                intent.putExtra("category","story books");
                startActivity(intent);
            }
        });
        cat15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SellerbookcategoryActivity.this, SelleraddnewbookActivity.class);
                intent.putExtra("category","science books");
                startActivity(intent);
            }
        });
    }
}