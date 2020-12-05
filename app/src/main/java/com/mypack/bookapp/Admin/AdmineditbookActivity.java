package com.mypack.bookapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mypack.bookapp.R;
import com.mypack.bookapp.Seller.SellerbookcategoryActivity;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdmineditbookActivity extends AppCompatActivity {
        private Button bookedit_btn,deletebook;
        private EditText nameedit,priceedit,descedit;
        private ImageView edit_image;
        private String bookId="";
        private DatabaseReference bookref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admineditbook);
        bookId=getIntent().getStringExtra("Bid");
        bookref= FirebaseDatabase.getInstance().getReference().child("Book").child(bookId);

        deletebook=findViewById(R.id.delete_book_btn);
        bookedit_btn=findViewById(R.id.book_editbtn);
        nameedit=findViewById(R.id.book_name_edit);
        descedit=findViewById(R.id.book_desc_edit);
        priceedit=findViewById(R.id.book_price_edit);
        edit_image=findViewById(R.id.book_image_edit);
        displaybookinfo();
            bookedit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    applychanges();
                }
            });

    deletebook.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            deletethisbook();
        }
    });
    }

    private void deletethisbook() {
    bookref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            Intent intent=new Intent(AdmineditbookActivity.this, SellerbookcategoryActivity.class);
            startActivity(intent);
            finish();
            Toast.makeText(AdmineditbookActivity.this, "This book is deleted successfully", Toast.LENGTH_SHORT).show();
        }
    });
    }

    private void applychanges() {
        String bname1=nameedit.getText().toString();
        String bprice1=priceedit.getText().toString();
        String bdesc1=descedit.getText().toString();
        if(bname1.equals("")){
            Toast.makeText(this,"Write Book Name",Toast.LENGTH_SHORT).show();

        }
        else if(bprice1.equals("")) {
            Toast.makeText(this, "Write Book Price", Toast.LENGTH_SHORT).show();
        }
        else if(bdesc1.equals("")) {
            Toast.makeText(this, "Write Book Description", Toast.LENGTH_SHORT).show();
         }
        else {
            HashMap<String,Object> bookmap=new HashMap<>();
            bookmap.put("Bid",bookId);
            bookmap.put("description",bdesc1);
            bookmap.put("price",bprice1);
            bookmap.put("Bookname",bname1);
          bookref.updateChildren(bookmap).addOnCompleteListener(new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
               if (task.isSuccessful()){
                   Toast.makeText(AdmineditbookActivity.this, "Changes appiled successfully book info. is saved", Toast.LENGTH_SHORT).show();
                   Intent intent=new Intent(AdmineditbookActivity.this, SellerbookcategoryActivity.class);
                   startActivity(intent);
                   finish();
               }
              }
          });
          }
        }

    private void displaybookinfo() {
    bookref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {

        if (snapshot.exists()){
            String bname=snapshot.child("Bookname").getValue().toString();
            String bprice=snapshot.child("price").getValue().toString();
            String bdesc=snapshot.child("description").getValue().toString();
            String bimage=snapshot.child("image").getValue().toString();
            nameedit.setText(bname);
            priceedit.setText(bprice);
            descedit.setText(bdesc);
            Picasso.get().load(bimage).into(edit_image);

         }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });

    }
}