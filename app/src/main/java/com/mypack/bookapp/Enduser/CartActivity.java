package com.mypack.bookapp.Enduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mypack.bookapp.Model.Cart;
import com.mypack.bookapp.Prevalent.Prevalent;
import com.mypack.bookapp.R;
import com.mypack.bookapp.viewholder.CartviewHolder;

public class CartActivity extends AppCompatActivity {
private RecyclerView recyclerView;
private RecyclerView.LayoutManager layoutManager;
private Button nextbutton;
private TextView totalamount_txt,txtmsg1;
private int overalltotalprice=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        recyclerView=findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        nextbutton=(Button)findViewById(R.id.next_btn);
        totalamount_txt=(TextView)findViewById(R.id.total_price);
        txtmsg1=(TextView)findViewById(R.id.msg1);
           nextbutton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            totalamount_txt.setText("Total Price =Rs." + String.valueOf(overalltotalprice));
            Intent intent=new Intent(CartActivity.this, ConfirmorderActivity.class);
            intent.putExtra("Total Price",String.valueOf(overalltotalprice));
            startActivity(intent);
            finish();
        }
    });
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkorderstate();
        final DatabaseReference cartlistref= FirebaseDatabase.getInstance().getReference().child("Cart List");
        FirebaseRecyclerOptions<Cart>options=new FirebaseRecyclerOptions.Builder<Cart>().setQuery(cartlistref.child("User View").child(Prevalent.currentuser.getPhone())
        .child("Book"),Cart.class).build();
        FirebaseRecyclerAdapter<Cart, CartviewHolder>adapter=new FirebaseRecyclerAdapter<Cart, CartviewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartviewHolder holder, int position, @NonNull final Cart model) {
                holder.txtbookquantity.setText("Quantity ="+model.getQuantity());
                holder.txtbookname.setText(model.getBookname());
                holder.txtbookprice.setText("Price = Rs."+model.getPrice());
                int onebookpriceT=((Integer.valueOf(model.getPrice())))*Integer.valueOf(model.getQuantity());
                overalltotalprice=overalltotalprice+onebookpriceT;
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[]=new CharSequence[]{
                                "Edit",
                                "Remove"
                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                                 if(i==0){
                                     Intent intent=new Intent(CartActivity.this, BookdetailActivity.class);
                                     intent.putExtra("Bid",model.getBid());
                                     startActivity(intent);
                                 }
                                 if(i==1){
                                     cartlistref.child("User View").child(Prevalent.currentuser.getPhone())
                                             .child("Book").child(model.getBid()).removeValue()
                                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                 @Override
                                                 public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(CartActivity.this,"Item is removed",Toast.LENGTH_SHORT).show();
                                                    Intent intent=new Intent(CartActivity.this, HomeActivity.class);
                                                    startActivity(intent);
                                                  }
                                                 }
                                             });
                                 }
                        }
                    });
                    builder.show();
                    }
                });
            }
            @NonNull
            @Override
            public CartviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
                CartviewHolder holder=new CartviewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    private void checkorderstate(){
        DatabaseReference orderref;
        orderref=FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentuser.getPhone());
        orderref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
           if (snapshot.exists()){
               String shippingstate=snapshot.child("state").getValue().toString();
               String username=snapshot.child("Name").getValue().toString();
               if (shippingstate.equals("shipped")){
                   totalamount_txt.setText("Dear "+username+" your order of Rs."+overalltotalprice+"is shipped!!");
                   recyclerView.setVisibility(View.GONE);
                   txtmsg1.setVisibility(View.VISIBLE);
                   txtmsg1.setText("your order shipped successfully");
                   nextbutton.setVisibility(View.GONE);
               }
               else if (shippingstate.equals("not shipped")){
                   totalamount_txt.setText("Shipping state=not shipped");
                   recyclerView.setVisibility(View.GONE);
                   txtmsg1.setVisibility(View.VISIBLE);
                   nextbutton.setVisibility(View.GONE);
               }
              }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}