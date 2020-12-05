package com.mypack.bookapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mypack.bookapp.Enduser.HomeActivity;
import com.mypack.bookapp.Enduser.MainActivity;
import com.mypack.bookapp.R;

public class AdminhomeActivity extends AppCompatActivity {
    private Button Alogoutbtn,checkorderbtn,editbookbtn1,approvebookbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);
                editbookbtn1=(Button)findViewById(R.id.editbookbtn);
        Alogoutbtn=(Button)findViewById(R.id.admin_logoutbtn);
        approvebookbtn=(Button) findViewById(R.id.admin_approvebook);
        checkorderbtn=(Button)findViewById(R.id.check_orderbtn);
        Alogoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminhomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        editbookbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(AdminhomeActivity.this, HomeActivity.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);
            }
        });
        approvebookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminhomeActivity.this, AdminapprovenewbookActivity.class);
                startActivity(intent);
            }
        });
        checkorderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminhomeActivity.this, AdminordersActivity.class);
                startActivity(intent);
            }
        });


    }
}