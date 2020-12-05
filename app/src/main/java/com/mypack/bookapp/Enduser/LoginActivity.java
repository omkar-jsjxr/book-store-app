package com.mypack.bookapp.Enduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mypack.bookapp.Admin.AdminhomeActivity;
import com.mypack.bookapp.Seller.SellerbookcategoryActivity;
import com.mypack.bookapp.Model.Users;
import com.mypack.bookapp.Prevalent.Prevalent;
import com.mypack.bookapp.R;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    private Button loginbtn;
    private EditText loginphone_no,loginpassword;
    private ProgressDialog loadingbar1;
    private TextView adminlink,forgotpasswordtxt;
    private com.rey.material.widget.CheckBox checkboxrem;
    private TextView notadminlink;
    private String parentdbname="Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginbtn=(Button)findViewById(R.id.login_btn);
        loginphone_no=(EditText)findViewById(R.id.login_phone_no);
        loginpassword=(EditText)findViewById(R.id.login_password);
        adminlink=(TextView)findViewById(R.id.admin_panel);
        forgotpasswordtxt=findViewById(R.id.forgot_password);
        notadminlink=(TextView)findViewById(R.id.notadmin_panel);
        checkboxrem=(CheckBox) findViewById(R.id.rem_me);
        Paper.init(this);

        loadingbar1=new ProgressDialog(this);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loginuser();
            }
        });
   forgotpasswordtxt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(LoginActivity.this, ResetpasswordActivity.class);
        intent.putExtra("check","login");
        startActivity(intent);
    }
});

        adminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginbtn.setText("Login Admin");
                adminlink.setVisibility(View.INVISIBLE);
                notadminlink.setVisibility(View.VISIBLE);
                parentdbname="Admin";
            }
        });
        notadminlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginbtn.setText("Login");
                adminlink.setVisibility(View.VISIBLE);
                notadminlink.setVisibility(View.INVISIBLE);
                parentdbname="Users";

            }
        });



    }

    private void Loginuser() {
        String phone=loginphone_no.getText().toString();
        String password=loginpassword.getText().toString();
        if(TextUtils.isEmpty(phone)) {
            Toast.makeText(this,"Please enter your Phone no.",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingbar1.setTitle("Login Account");
            loadingbar1.setMessage("Please wait,we are checking credentials");
            loadingbar1.setCanceledOnTouchOutside(false);
            loadingbar1.show();
            Allowaccess(phone,password);
        }
    }

    private void Allowaccess(final String phone, final String password) {
            if(checkboxrem.isChecked()){
                Paper.book().write(Prevalent.userphonekey,phone);
                Paper.book().write(Prevalent.userpasswordkey,password);
            }

        final DatabaseReference Rootref1;
        Rootref1= FirebaseDatabase.getInstance().getReference();
        Rootref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentdbname).child(phone).exists()) {
                    Users userdata=snapshot.child(parentdbname).child(phone).getValue(Users.class);
                    if(userdata.getPhone().equals(phone)){
                        if(userdata.getPassword().equals(password)){
                           if (parentdbname.equals("Admin")){
                               Toast.makeText(LoginActivity.this, "Welcome Admin you Logged in Successfully", Toast.LENGTH_SHORT).show();
                               loadingbar1.dismiss();
                               Intent intent=new Intent(LoginActivity.this, AdminhomeActivity.class);
                               startActivity(intent);
                           }
                           else if (parentdbname.equals("Users")){
                               Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                               loadingbar1.dismiss();
                               Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                               Prevalent.currentuser=userdata;
                               startActivity(intent);

                           }
                        } else{
                            Toast.makeText(LoginActivity.this, "Passwod is Incorrect try again", Toast.LENGTH_SHORT).show();
                            loadingbar1.dismiss();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Account with this phone no. do not exist", Toast.LENGTH_SHORT).show();
                    loadingbar1.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}