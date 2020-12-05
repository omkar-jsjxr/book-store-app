package com.mypack.bookapp.Enduser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.mypack.bookapp.Prevalent.Prevalent;
import com.mypack.bookapp.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {
        private CircleImageView profileimageview;
        private EditText fullnametext,userphonetext,addresstext;
        private Uri imageuri;
        private String myurl="";
        private Button setsecurityQ;
        private StorageReference storageprofileRef;
        private String checker="";
        private StorageTask uploadtask1;
        private TextView profilechangebtn,closebtn,savebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        storageprofileRef= FirebaseStorage.getInstance().getReference().child("Profile picture");

        profileimageview=(CircleImageView)findViewById(R.id.setting_profileimage);
        fullnametext=(EditText)findViewById(R.id.setting_name);
        userphonetext=(EditText)findViewById(R.id.setting_phone);
        setsecurityQ=findViewById(R.id.security_Q);
        addresstext=(EditText)findViewById(R.id.setting_address);

        profilechangebtn=(TextView)findViewById(R.id.profile_image_change);

        closebtn=(TextView)findViewById(R.id.close_setting);
        savebtn=(TextView)findViewById(R.id.update_setting);

        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checker.equals("clicked")) {
                    userinfosaved();

                } else {
                    updateonlyinfo();
                }
            }
        });

        setsecurityQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this, ResetpasswordActivity.class);
                intent.putExtra("check","setting");
                startActivity(intent);
            }
        });

        profilechangebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker="clicked";
                CropImage.activity(imageuri)
                        .setAspectRatio(1,1)
                        .start(SettingActivity.this);
            }
        });

userinfodisp(profileimageview,fullnametext,userphonetext,addresstext);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK && data!=null){
                CropImage.ActivityResult result=CropImage.getActivityResult(data);
                imageuri=result.getUri();
                profileimageview.setImageURI(imageuri);

            }
            else{
                Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(SettingActivity.this, SettingActivity.class));
                finish();
            }
    }

    private void updateonlyinfo() {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users");
        HashMap<String ,Object>usermap=new HashMap<>();
        usermap.put("Name",fullnametext.getText().toString());
        usermap.put("Phone",userphonetext.getText().toString());
        usermap.put("address",addresstext.getText().toString());
        ref.child(Prevalent.currentuser.getPhone()).updateChildren(usermap);
        startActivity(new Intent(SettingActivity.this,HomeActivity.class));
        Toast.makeText(SettingActivity.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
        finish();



    }

    private void userinfosaved() {
        if (TextUtils.isEmpty(fullnametext.getText().toString())) {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(addresstext.getText().toString())) {
            Toast.makeText(this, "Name is address.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(userphonetext.getText().toString())) {
            Toast.makeText(this, "Name is mandatory.", Toast.LENGTH_SHORT).show();
        }
        else if(checker.equals("clicked")) {
            uploadimage();
        }
    }

    private void uploadimage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (imageuri!=null){
            final  StorageReference fileref=storageprofileRef.child(Prevalent.currentuser.getPhone()+"jpg");
            uploadtask1=fileref.putFile(imageuri);
            uploadtask1.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
               if (!task.isSuccessful()){
                   throw task.getException();

               }
                    return fileref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()){
                        Uri downloadurl=task.getResult();
                        myurl=downloadurl.toString();
                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users");
                        HashMap<String ,Object>usermap=new HashMap<>();
                        usermap.put("Name",fullnametext.getText().toString());
                        usermap.put("Phone",userphonetext.getText().toString());
                        usermap.put("address",addresstext.getText().toString());
                        usermap.put("image",myurl);
                        ref.child(Prevalent.currentuser.getPhone()).updateChildren(usermap);
                        progressDialog.dismiss();
                        startActivity(new Intent(SettingActivity.this,HomeActivity.class));
                        Toast.makeText(SettingActivity.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(SettingActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }
    }

    private void userinfodisp(final CircleImageView profileimageview, final EditText fullnametext, final EditText userphonetext, final EditText addresstext) {
    DatabaseReference userref=FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentuser.getPhone());
    userref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
        if (snapshot.exists()){
            if (snapshot.child("image").exists()){
                String image=snapshot.child("image").getValue().toString();
                String name=snapshot.child("Name").getValue().toString();
                String phone=snapshot.child("Phone").getValue().toString();
                String address=snapshot.child("address").getValue().toString();
                Picasso.get().load(image).into(profileimageview);
                fullnametext.setText(name);
                userphonetext.setText(phone);
                addresstext.setText(address);

            }

        }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

    }
}