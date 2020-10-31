package com.mypack.bookapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mypack.bookapp.Model.Users;
import com.mypack.bookapp.Prevalent.Prevalent;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
   private Button loginbtn,signupbtn;
    private ProgressDialog loadingbar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    loginbtn=(Button)findViewById(R.id.main_login_btn);
    signupbtn=(Button)findViewById(R.id.main_signup_btn);
    loadingbar1=new ProgressDialog(this);
        Paper.init(this);

    loginbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    });

    signupbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(MainActivity.this,SignupActivity.class);
            startActivity(intent);
        }
    });
        String userphonekey=Paper.book().read(Prevalent.userphonekey);
        String userpasswordkey=Paper.book().read(Prevalent.userpasswordkey);
        if(userphonekey != "" && userpasswordkey != ""){
            if (!TextUtils.isEmpty(userphonekey) && !TextUtils.isEmpty(userpasswordkey)){
                Allowaccess(userphonekey,userpasswordkey);
                loadingbar1.setTitle("Already Logged In");
                loadingbar1.setMessage("Please wait....");
                loadingbar1.setCanceledOnTouchOutside(false);
                loadingbar1.show();
            }

        }
    }

    private void Allowaccess(final String phone, final String password1) {

        final DatabaseReference Rootref1;
        Rootref1= FirebaseDatabase.getInstance().getReference();
        Rootref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("Users").child(phone).exists()) {
                    Users userdata = snapshot.child("Users").child(phone).getValue(Users.class);
                    if (userdata.getPhone_no().equals(phone)) {
                        if (userdata.getPassword().equals(password1)) {
                            Toast.makeText(MainActivity.this, " Already Logged in!!", Toast.LENGTH_SHORT).show();
                            loadingbar1.dismiss();
                            Intent intent = new Intent(MainActivity.this, HomeActivity2.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(MainActivity.this, "Passwod is Incorrect try again", Toast.LENGTH_SHORT).show();
                            loadingbar1.dismiss();
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Account with this phone no. do not exist", Toast.LENGTH_SHORT).show();
                    loadingbar1.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}