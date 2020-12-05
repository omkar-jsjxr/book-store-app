package com.mypack.bookapp.Admin;

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
import com.mypack.bookapp.Enduser.LoginActivity;
import com.mypack.bookapp.Enduser.MainActivity;
import com.mypack.bookapp.R;

import java.util.HashMap;

public class AdminsignupActivity extends AppCompatActivity {
    private Button adminsignupbtn;
    private EditText signupnameadmin,signupphone_noadmin,signuppasswordadmin;
    private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsignup);
        adminsignupbtn=(Button)findViewById(R.id.adminsignup_btn);
        signupnameadmin=(EditText)findViewById(R.id.adminsignup_name);
        signupphone_noadmin=(EditText)findViewById(R.id.adminsignup_phone_no);
        signuppasswordadmin=(EditText)findViewById(R.id.adminsignup_password);
        loadingbar=new ProgressDialog(this);
        adminsignupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAccount();
            }
        });


    }
    private void CreateAccount(){
        String name1=signupnameadmin.getText().toString();
        String phone_no1=signupphone_noadmin.getText().toString();
        String password1=signuppasswordadmin.getText().toString();
        if(TextUtils.isEmpty(name1)) {
            Toast.makeText(this,"Please write your name",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(phone_no1)) {
            Toast.makeText(this,"Please enter your Phone no.",Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(password1)) {
            Toast.makeText(this,"Please enter pasword",Toast.LENGTH_SHORT).show();

        } else{
            loadingbar.setTitle("Create Admin Account");
            loadingbar.setMessage("Please wait admin ,we are checking credentials");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();
            validateemail(name1,phone_no1,password1);
        }
    }


    private void validateemail(final String name1, final String phone_no1, final String password1) {
        final DatabaseReference Rootref;
        Rootref= FirebaseDatabase.getInstance().getReference();
        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("Admin").child(phone_no1).exists())) {
                    HashMap<String,Object> userdatamap=new HashMap<>();
                    userdatamap.put("Phone",phone_no1);
                    userdatamap.put("Password",password1);
                    userdatamap.put("Name",name1);
                    Rootref.child("Admin").child(phone_no1).updateChildren(userdatamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AdminsignupActivity.this, "Your Account Successfully created!!", Toast.LENGTH_SHORT).show();
                                loadingbar.dismiss();
                                Intent intent=new Intent(AdminsignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                loadingbar.dismiss();
                                Toast.makeText(AdminsignupActivity.this, "Sorry something went wrong Try again!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(AdminsignupActivity.this, "This"+phone_no1+"already exists", Toast.LENGTH_SHORT).show();
                    loadingbar.dismiss();
                    Toast.makeText(AdminsignupActivity.this, "Please try again using another phone no.", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(AdminsignupActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

}