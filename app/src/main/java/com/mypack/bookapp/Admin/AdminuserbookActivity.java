package com.mypack.bookapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mypack.bookapp.Model.Cart;
import com.mypack.bookapp.R;
import com.mypack.bookapp.viewholder.CartviewHolder;

public class AdminuserbookActivity extends AppCompatActivity {
private RecyclerView booklist;
private String userId="";
private DatabaseReference cartlistref;
RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminuserbook);
        userId=getIntent().getStringExtra("uid");
        booklist=findViewById(R.id.books_list1);
        booklist.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        booklist.setLayoutManager(layoutManager);

        cartlistref= FirebaseDatabase.getInstance().getReference().child("Cart List").child("Admin View").child(userId).child("Book");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Cart>options=new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartlistref,Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartviewHolder>adapter=new FirebaseRecyclerAdapter<Cart, CartviewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartviewHolder holder, int position, @NonNull Cart model) {
                holder.txtbookquantity.setText("Quantity ="+model.getQuantity());
                holder.txtbookname.setText(model.getBookname());
                holder.txtbookprice.setText("Price = Rs."+model.getPrice());
            }

            @NonNull
            @Override
            public CartviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
                CartviewHolder holder=new CartviewHolder(view);
                return holder;
            }
        };
        booklist.setAdapter(adapter);
        adapter.startListening();
    }
}