package com.mypack.bookapp.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mypack.bookapp.R;

public class SellerloginActivity extends AppCompatActivity {
private EditText emaillogin,passwordlogin;
private Button loginsellerbtn1;
private FirebaseAuth mauth;
    private ProgressDialog loadingbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellerlogin);
        emaillogin=findViewById(R.id.selleremaillogin);
        passwordlogin=findViewById(R.id.sellerpasswordlogin);
        loginsellerbtn1=findViewById(R.id.sellerlogin_btn);
        loadingbar=new ProgressDialog(this);
            mauth=FirebaseAuth.getInstance();
      loginsellerbtn1.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
        loginseller();
         }
     });
    }

    private void loginseller() {

        final String email=emaillogin.getText().toString();
        final String password=passwordlogin.getText().toString();
        if (!email.equals("") && !password.equals("") ) {
            loadingbar.setTitle("Seller Account login");
            loadingbar.setMessage("Please wait,we are checking credentials");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

        mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SellerloginActivity.this,"You login successsfully",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SellerloginActivity.this, SellerHomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });

        }else {
            Toast.makeText(this,"Please login first",Toast.LENGTH_SHORT).show();
        }
    }
}