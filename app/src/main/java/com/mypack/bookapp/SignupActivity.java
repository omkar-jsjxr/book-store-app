package com.mypack.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private Button signupbtn;
    private EditText signupname,signupphone_no,signuppassword;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupbtn=(Button)findViewById(R.id.signup_btn);
        signupname=(EditText)findViewById(R.id.signup_name);
        signupphone_no=(EditText)findViewById(R.id.signup_phone_no);
        signuppassword=(EditText)findViewById(R.id.signup_password);
        loadingbar=new ProgressDialog(this);


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });
    }
    private void CreateAccount(){
        String name=signupname.getText().toString();
        String phone_no=signupphone_no.getText().toString();
        String password=signuppassword.getText().toString();
        if(TextUtils.isEmpty(name)) {
            Toast.makeText(this,"Please write your name",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(phone_no)) {
            Toast.makeText(this,"Please enter your Phone no.",Toast.LENGTH_SHORT).show();
        }

       else if(TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Please enter pasword",Toast.LENGTH_SHORT).show();

       } else{
           loadingbar.setTitle("Create Account");
           loadingbar.setMessage("Please wait,we are checking credentials");
           loadingbar.setCanceledOnTouchOutside(false);
           loadingbar.show();
           validateemail(name,phone_no,password);
        }
    }


    private void validateemail(final String name, final String phone_no, final String password) {
    final DatabaseReference Rootref;
    Rootref= FirebaseDatabase.getInstance().getReference();
    Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (!(snapshot.child("Users").child(phone_no).exists())) {
                HashMap<String,Object>userdatamap=new HashMap<>();
                userdatamap.put("Phone_no",phone_no);
                userdatamap.put("Password",password);
                userdatamap.put("Full_Name",name);
                Rootref.child("Users").child(phone_no).updateChildren(userdatamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignupActivity.this, "Your Account Successfully created!!", Toast.LENGTH_SHORT).show();
                        loadingbar.dismiss();
                        Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }else {
                        loadingbar.dismiss();
                        Toast.makeText(SignupActivity.this, "Sorry something went wrong Try again!", Toast.LENGTH_SHORT).show();
                     }
                    }
                });
            }
            else {
                Toast.makeText(SignupActivity.this, "This"+phone_no+"already exists", Toast.LENGTH_SHORT).show();
                loadingbar.dismiss();
                Toast.makeText(SignupActivity.this, "Please try again using another phone no.", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SignupActivity.this,MainActivity.class);
                startActivity(intent);
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError error) {
        }
    });

    }
}