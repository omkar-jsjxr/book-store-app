package com.mypack.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminActivity extends AppCompatActivity {
    private Button tocatgory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toast.makeText(this,"welcome admin",Toast.LENGTH_SHORT).show();

        tocatgory=(Button)findViewById(R.id.tocategory);
        tocatgory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminActivity.this,CategoryActivity.class);
                startActivity(intent);
            }
        });
    }
}