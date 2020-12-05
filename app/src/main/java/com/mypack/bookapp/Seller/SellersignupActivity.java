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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mypack.bookapp.Enduser.MainActivity;
import com.mypack.bookapp.R;

import java.util.HashMap;

public class SellersignupActivity extends AppCompatActivity {
private Button selleralredybtn,sellersignupbtn1;
private EditText nameinput,phoneinput,emailinput,passwordinput,addressinput;
private FirebaseAuth mauth;
    private ProgressDialog loadingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellersignup);
        mauth=FirebaseAuth.getInstance();

        selleralredybtn=findViewById(R.id.selleralreadyaccountbtn);
        nameinput=findViewById(R.id.sellername);
        loadingbar=new ProgressDialog(this);
        phoneinput=findViewById(R.id.sellerphone);
        emailinput=findViewById(R.id.selleremail);
        passwordinput=findViewById(R.id.sellerpassword);
        addressinput=findViewById(R.id.selleraddress);
        sellersignupbtn1=findViewById(R.id.sellersignupbtn);
        sellersignupbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupseller();
            }
        });

        selleralredybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SellersignupActivity.this, SellerloginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void signupseller() {
        final String name=nameinput.getText().toString();
        final String phone=phoneinput.getText().toString();
        final String email=emailinput.getText().toString();
        final String password=passwordinput.getText().toString();
        final String address=addressinput.getText().toString();
        if (!name.equals("") && !phone.equals("") && !email.equals("") && !password.equals("") && !address.equals("")){
            loadingbar.setTitle("Creating Seller Account");
            loadingbar.setMessage("Please wait,we are checking credentials");
            loadingbar.setCanceledOnTouchOutside(false);
            loadingbar.show();

            mauth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        final DatabaseReference rootref;
                        rootref= FirebaseDatabase.getInstance().getReference();
                        String Sid=mauth.getCurrentUser().getUid();
                        HashMap<String ,Object>sellermap=new HashMap<>();
                        sellermap.put("sid",Sid);
                        sellermap.put("phone",phone);
                        sellermap.put("name",name);
                        sellermap.put("email",email);
                        sellermap.put("password",password);
                        sellermap.put("address",address);
                        rootref.child("Sellers").child(Sid).updateChildren(sellermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                               loadingbar.dismiss();
                                Toast.makeText(SellersignupActivity.this,"You signup successsfully",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(SellersignupActivity.this, SellerHomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                }
            });

        }else {
            Toast.makeText(this,"Please signup first",Toast.LENGTH_SHORT).show();

        }



    }
}