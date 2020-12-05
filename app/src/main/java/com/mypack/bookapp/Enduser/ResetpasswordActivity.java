package com.mypack.bookapp.Enduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mypack.bookapp.Prevalent.Prevalent;
import com.mypack.bookapp.R;

import java.util.HashMap;

public class ResetpasswordActivity extends AppCompatActivity {
private String check="";
private TextView pagetitle,qtitle;
private EditText phonesecurity,secQ1,secQ2;
private Button verifybtnsec;


@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
    check=getIntent().getStringExtra("check");
    pagetitle=findViewById(R.id.resetpassword);
    qtitle=findViewById(R.id.titleQ);
    phonesecurity=findViewById(R.id.find_phone);
    secQ1=findViewById(R.id.securityQ1);
    secQ2=findViewById(R.id.securityQ2);
    verifybtnsec=findViewById(R.id.verifybtn);


    }

    @Override
    protected void onStart() {
        super.onStart();

        phonesecurity.setVisibility(View.GONE);
        if (check.equals("setting")){
        pagetitle.setText("Set Questions");
        qtitle.setText("please answer for follwing security Questions");
        verifybtnsec.setText("Set");
        diplayans();

        verifybtnsec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        setanswer();
            }
        });
        }
    else if (check.equals("login")){
            phonesecurity.setVisibility(View.VISIBLE);

            verifybtnsec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifyuser();
                }
            });
      }
    }

    private void verifyuser() {
        final String phone=phonesecurity.getText().toString();
        final String Answer1=secQ1.getText().toString().toLowerCase();
        final String Answer2=secQ2.getText().toString().toLowerCase();

        if (!phone.equals("") && !Answer1.equals("") && !Answer2.equals("")) {
            final DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(phone);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        String mphone=snapshot.child("Phone").getValue().toString();
                        if (snapshot.hasChild("Security Questions")){
                            String ans1=snapshot.child("Security Questions").child("answer1").getValue().toString();
                            String ans2=snapshot.child("Security Questions").child("answer2").getValue().toString();
                            if (!ans1.equals(Answer1)){
                                Toast.makeText(ResetpasswordActivity.this,"your first answer is wrong",Toast.LENGTH_SHORT).show();
                            }else if (!ans2.equals(Answer2)){
                                Toast.makeText(ResetpasswordActivity.this,"your second answer is wrong",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                AlertDialog.Builder builder=new AlertDialog.Builder(ResetpasswordActivity.this);
                                builder.setTitle("New Password");
                                final EditText newpass=new EditText(ResetpasswordActivity.this);
                                newpass.setHint("Write New Password Here");
                                builder.setView(newpass);
                                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        if (!newpass.getText().toString().equals("")){
                                            ref.child("Password").setValue(newpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        Toast.makeText(ResetpasswordActivity.this,"password changed successfully",Toast.LENGTH_SHORT).show();
                                                    Intent intent=new Intent(ResetpasswordActivity.this, LoginActivity.class);
                                                    startActivity(intent);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                    dialog.cancel();
                                    }
                                });
                                builder.show();
                            }
                        }
                        else {
                            Toast.makeText(ResetpasswordActivity.this,"you have not set security questions",Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(ResetpasswordActivity.this,"this phone no. not exists",Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }else {
            Toast.makeText(ResetpasswordActivity.this,"please complete the form",Toast.LENGTH_SHORT).show();
        }
}

    private  void  setanswer(){
        String ans1=secQ1.getText().toString().toLowerCase();
        String ans2=secQ2.getText().toString().toLowerCase();
        if (secQ1.equals("")&&secQ2.equals("")){
            Toast.makeText(ResetpasswordActivity.this, "please answer security questions", Toast.LENGTH_SHORT).show();
        }else{
            DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentuser.getPhone());
            HashMap<String,Object> userdatamap=new HashMap<>();
            userdatamap.put("answer1",ans1);
            userdatamap.put("answer2",ans2);

            ref.child("Security Questions").updateChildren(userdatamap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ResetpasswordActivity.this,"you have answer security questions successfully",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(ResetpasswordActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

    }
    private void diplayans(){
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentuser.getPhone());
        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    String ans1=snapshot.child("answer1").getValue().toString();
                    String ans2=snapshot.child("answer2").getValue().toString();
                    secQ1.setText(ans1);
                    secQ2.setText(ans2);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}