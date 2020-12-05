package com.mypack.bookapp.Seller;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mypack.bookapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SelleraddnewbookActivity extends AppCompatActivity {
    private String categoryname,description,price,bname,currentdate,currenttime;
    private Button addbookbtn;
    private ImageView selectbookimg;
    private EditText addbookname,addbookdec,addbookprice;
    private static final  int gallerypic=1;
    private Uri imageuri;
    private DatabaseReference bookref,sellerref;
    private  String bookrandomkey,downloadimgurl;
    private ProgressDialog loadingbar1;
    private StorageReference bookimagesref;
        private String sname,saddress,sphone,semail,sid1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selleraddnewbook);
        categoryname=getIntent().getExtras().get("category").toString();
        bookimagesref= FirebaseStorage.getInstance().getReference().child("Book image");
        bookref=FirebaseDatabase.getInstance().getReference().child("Book");
        sellerref=FirebaseDatabase.getInstance().getReference().child("Sellers");

        addbookbtn=(Button)findViewById(R.id.new_bookbtn);
        selectbookimg=(ImageView)findViewById(R.id.select_book);
        addbookname=(EditText)findViewById(R.id.book_name);
        addbookdec=(EditText)findViewById(R.id.book_description);
        loadingbar1=new ProgressDialog(this);
        addbookprice=(EditText)findViewById(R.id.book_price);
        selectbookimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Opengallary();
            }
        });

        addbookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validatebookdata();
            }
        });

            sellerref.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){

                        sname=snapshot.child("name").getValue().toString();
                        saddress=snapshot.child("address").getValue().toString();
                        sphone=snapshot.child("phone").getValue().toString();
                        semail=snapshot.child("email").getValue().toString();
                        sid1=snapshot.child("sid").getValue().toString();


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }

    private void validatebookdata() {
    description=addbookdec.getText().toString();
    price=addbookprice.getText().toString();
    bname=addbookname.getText().toString();
    if (imageuri==null)
    {
        Toast.makeText(this,"Book image needed",Toast.LENGTH_SHORT).show();
    }
    else if (TextUtils.isEmpty(description)){
        Toast.makeText(this,"Book description needed",Toast.LENGTH_SHORT).show();
    }  else if (TextUtils.isEmpty(price)){
        Toast.makeText(this,"Book price needed",Toast.LENGTH_SHORT).show();
    }  else if (TextUtils.isEmpty(bname)) {
        Toast.makeText(this, "Book name needed", Toast.LENGTH_SHORT).show();
    }
    else {
        storebookinfo();
     }
    }

    private void storebookinfo() {
        loadingbar1.setTitle("Add new Book");
        loadingbar1.setMessage("Please wait,we are adding book");
        loadingbar1.setCanceledOnTouchOutside(false);
        loadingbar1.show();

        Calendar calender=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd, yyyy");
        currentdate=currentDate.format(calender.getTime());
        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        currenttime=currentTime.format(calender.getTime());
            bookrandomkey=currentdate+currenttime;
         final StorageReference filepath=bookimagesref.child(imageuri.getLastPathSegment()+bookrandomkey+".jpg");
        final UploadTask uploadTask=filepath.putFile(imageuri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            String message=e.toString();
            Toast.makeText(SelleraddnewbookActivity.this,"Error:"+message,Toast.LENGTH_SHORT).show();
            loadingbar1.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(SelleraddnewbookActivity.this,"Image uploaded successfully",Toast.LENGTH_SHORT).show();
                Task<Uri>uritask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        downloadimgurl=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadimgurl=task.getResult().toString();
                            Toast.makeText(SelleraddnewbookActivity.this,"Got book image url",Toast.LENGTH_SHORT).show();
                        savebookinfo();
                        }
                        }
                });
            }
        });
    }
private void savebookinfo(){

    HashMap<String,Object>bookmap=new HashMap<>();
    bookmap.put("Bid",bookrandomkey);
    bookmap.put("date",currentdate);
    bookmap.put("time",currenttime);
    bookmap.put("description",description);
    bookmap.put("image",downloadimgurl);
    bookmap.put("category",categoryname);
    bookmap.put("price",price);
    bookmap.put("Bookname",bname);

    bookmap.put("sellername",sname);
    bookmap.put("selleraddress",saddress);
    bookmap.put("sellerphone",sphone);
    bookmap.put("selleremail",semail);
    bookmap.put("sid",sid1);

    bookmap.put("bookstate","not approved");

    bookref.child(bookrandomkey).updateChildren(bookmap).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if(task.isSuccessful()){
                Intent intent=new Intent(SelleraddnewbookActivity.this, SellerHomeActivity.class);
                startActivity(intent);
                loadingbar1.dismiss();
                Toast.makeText(SelleraddnewbookActivity.this,"Book added successfully",Toast.LENGTH_SHORT).show();

            }else {
                loadingbar1.dismiss();
                String message=task.getException().toString();
                Toast.makeText(SelleraddnewbookActivity.this,"Error:"+message,Toast.LENGTH_SHORT).show();

            }
        }
    });
}
    private void Opengallary() {
        Intent galleryintent=new Intent();
        galleryintent.setAction(Intent.ACTION_GET_CONTENT);
        galleryintent.setType("image/*");
        startActivityForResult(galleryintent,gallerypic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==gallerypic && resultCode==RESULT_OK && data!=null){
            imageuri=data.getData();
            selectbookimg.setImageURI(imageuri);

        }
    }
}