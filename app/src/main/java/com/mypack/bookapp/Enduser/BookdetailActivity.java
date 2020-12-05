package com.mypack.bookapp.Enduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mypack.bookapp.Model.books1;
import com.mypack.bookapp.Prevalent.Prevalent;
import com.mypack.bookapp.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class BookdetailActivity extends AppCompatActivity {
        private Button addtocartBtn;
        private ImageView bookimage;
        private  ElegantNumberButton numberbtn;
        private TextView bookprice,bookname,bookdesc;
        private String bookId="",state="normal";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetail);
        bookId=getIntent().getStringExtra("Bid");


        addtocartBtn=(Button) findViewById(R.id.btn_cart);
        bookimage=(ImageView)findViewById(R.id.bookimage_detail);
        numberbtn=(ElegantNumberButton)findViewById(R.id.number_btn);
        bookprice=(TextView)findViewById(R.id.bookprice_detail);
        bookname=(TextView)findViewById(R.id.bookname_detail);
        bookdesc=(TextView)findViewById(R.id.bookdesc_details);
        getbookdetail(bookId);
        addtocartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state.equals("order placed") || state.equals("order shipped")){

                    Toast.makeText(BookdetailActivity.this,"you can add more books once your order is shipped or confirmed",Toast.LENGTH_LONG).show();
                }
                else {

                    addingtocat();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkorderstate();
    }

    private void addingtocat() {
        String saveCurrentdate,savecurrenttime;
        Calendar callfordate=Calendar.getInstance();
        SimpleDateFormat currentdate= new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentdate=currentdate.format(callfordate.getTime());

        SimpleDateFormat currenttime= new SimpleDateFormat("HH:mm:ss a");
        savecurrenttime=currenttime.format(callfordate.getTime());
    final DatabaseReference cartlistner=FirebaseDatabase.getInstance().getReference().child("Cart List");
    final HashMap<String ,Object>cartmap=new HashMap<>();
    cartmap.put("Bid",bookId);
        cartmap.put("Bookname",bookname.getText().toString());
        cartmap.put("price",bookprice.getText().toString());
        cartmap.put("date",saveCurrentdate);
        cartmap.put("time",savecurrenttime);
        cartmap.put("quantity",numberbtn.getNumber());
        cartmap.put("disscount","");
        cartlistner.child("User View").child(Prevalent.currentuser.getPhone()).child("Book").child(bookId)
        .updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    cartlistner.child("Admin View").child(Prevalent.currentuser.getPhone()).child("Book").child(bookId)
                            .updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                       if (task.isSuccessful()){
                           Toast.makeText(BookdetailActivity.this,"Added to Cart list",Toast.LENGTH_SHORT).show();
                           Intent intent=new Intent(BookdetailActivity.this, HomeActivity.class);
                           startActivity(intent);
                       }
                        }
                    });
                }
            }
        });
    }

    private void getbookdetail(String bookId) {
        DatabaseReference bookref= FirebaseDatabase.getInstance().getReference().child("Book");
        bookref.child(bookId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    books1 books=snapshot.getValue(books1.class);
                    bookname.setText(books.getBookname());
                    bookprice.setText(books.getPrice());
                    bookdesc.setText(books.getDescription());
                    Picasso.get().load(books.getImage()).into(bookimage);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void checkorderstate(){
        DatabaseReference orderref;
        orderref=FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentuser.getPhone());
        orderref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String shippingstate=snapshot.child("state").getValue().toString();
                    if (shippingstate.equals("shipped")){
                        state="order shipped";
                    }
                    else if (shippingstate.equals("not shipped")){
                        state="order placed";
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}