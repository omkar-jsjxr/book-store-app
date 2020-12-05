package com.mypack.bookapp.Enduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mypack.bookapp.Prevalent.Prevalent;
import com.mypack.bookapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmorderActivity extends AppCompatActivity {
        private EditText nameedit,phoneedit,addressedit,cityedit;
        private Button confirmbtn;
        private String totalamount="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);
        confirmbtn=(Button)findViewById(R.id.confirm_btn);
        nameedit=(EditText)findViewById(R.id.confirm_name);
        phoneedit=(EditText)findViewById(R.id.confirm_phone);
        addressedit=(EditText)findViewById(R.id.confirm_address);
        cityedit=(EditText)findViewById(R.id.confirm_city);
        totalamount=getIntent().getStringExtra("Total Price");
        Toast.makeText(this,"Total amount = Rs."+totalamount,Toast.LENGTH_SHORT).show();
        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check();
            }
        });
    }

    private void Check() {
    if(TextUtils.isEmpty(nameedit.getText().toString())){
        Toast.makeText(this,"name is required",Toast.LENGTH_SHORT).show();
    }
        else if(TextUtils.isEmpty(phoneedit.getText().toString())){
            Toast.makeText(this,"phone is required",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressedit.getText().toString())){
            Toast.makeText(this,"address is required",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(cityedit.getText().toString())){
            Toast.makeText(this,"city name is required",Toast.LENGTH_SHORT).show();
        }
        else {
            confirmborder();
     }
    }

    private void confirmborder() {
        final String saveCurrentdate,savecurrenttime;
        Calendar callfordate=Calendar.getInstance();
        SimpleDateFormat currentdate= new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentdate=currentdate.format(callfordate.getTime());

        SimpleDateFormat currenttime= new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime=currenttime.format(callfordate.getTime());

        final DatabaseReference orderref= FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentuser.getPhone());
        HashMap<String,Object>ordermap=new HashMap<>();
        ordermap.put("TotalAmount",totalamount);
        ordermap.put("Name",nameedit.getText().toString());
        ordermap.put("Phone",phoneedit.getText().toString());
        ordermap.put("address",addressedit.getText().toString());
        ordermap.put("city",cityedit.getText().toString());
        ordermap.put("date",saveCurrentdate);
        ordermap.put("time",savecurrenttime);
        ordermap.put("state","not shipped");
        orderref.updateChildren(ordermap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("Cart List").child("User View").child(Prevalent.currentuser.getPhone())
                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ConfirmorderActivity.this,"Your Final order placed successfully!!",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(ConfirmorderActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                }
            }
        });
    }
}