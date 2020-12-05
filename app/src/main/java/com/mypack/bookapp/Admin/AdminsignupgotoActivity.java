package com.mypack.bookapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mypack.bookapp.R;
import com.mypack.bookapp.Enduser.SignupActivity;

public class AdminsignupgotoActivity extends AppCompatActivity {
    private Button gotoadminsignupbtn;
    private EditText getsignuppassword;
    private String adminpassword="bookadmin123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsignupgoto);
    gotoadminsignupbtn=(Button)findViewById(R.id.gotoadminsignup_btn);
    getsignuppassword=(EditText)findViewById(R.id.gotoadmin_signup_password);
    gotoadminsignupbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkadminpassword();
        }
    });
    }

    private void checkadminpassword() {
        String password1=getsignuppassword.getText().toString();
        if(password1.equals(adminpassword)){
            Intent intent=new Intent(AdminsignupgotoActivity.this, AdminsignupActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(AdminsignupgotoActivity.this, "Please enter correct password to become admin  Try again!!!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(AdminsignupgotoActivity.this, SignupActivity.class);
            startActivity(intent);
        }

    }

}