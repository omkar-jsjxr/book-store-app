package com.mypack.bookapp.Admin;

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

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mypack.bookapp.Model.Adminorders;
import com.mypack.bookapp.R;

public class AdminordersActivity extends AppCompatActivity {
private RecyclerView orderslist;
private DatabaseReference ordersref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminorders);

        ordersref= FirebaseDatabase.getInstance().getReference().child("Orders");
        orderslist=findViewById(R.id.order_list1);
        orderslist.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Adminorders>options=new FirebaseRecyclerOptions.Builder<Adminorders>()
                .setQuery(ordersref,Adminorders.class).build();
        FirebaseRecyclerAdapter<Adminorders,Adminorderviewholder> adapter=
                new FirebaseRecyclerAdapter<Adminorders, Adminorderviewholder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull Adminorderviewholder holder, final int position, @NonNull final Adminorders model) {
                        holder.ousername.setText("Name:- "+model.getName());
                        holder.ophone.setText("Phone:- "+model.getPhone());
                        holder.odatetime.setText("Date & Time:- "+model.getDate()+" "+model.getTime());
                        holder.oaddress.setText("Address:- "+model.getAddress()+" "+model.getCity());
                        holder.ototalprice.setText("Total Amount:- "+model.getTotalAmount());
                        holder.showordersbutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String uId=getRef(position).getKey();
                                Intent intent=new Intent(AdminordersActivity.this, AdminuserbookActivity.class);
                                intent.putExtra("uid",uId);
                                startActivity(intent);
                            }
                        });
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CharSequence options[]=new CharSequence[]{
                                        "Yes",
                                        "No"

                                };
                                AlertDialog.Builder builder=new AlertDialog.Builder(AdminordersActivity.this);
                                builder.setTitle("Have you shipped this order");
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                    if (i==0){
                                        String uId=getRef(position).getKey();
                                        Removeorder(uId);
                                    }

                                    else {}
                                    finish();
                                    }
                                });
                                builder.show();
                            }
                        });
                    }
                    @NonNull
                    @Override
                    public Adminorderviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_layout,parent,false);
                        return new Adminorderviewholder(view);
                    }
                };
        orderslist.setAdapter(adapter);
        adapter.startListening();
    }

    private void Removeorder(String uId) {
    ordersref.child(uId).removeValue();
    }

    public static  class Adminorderviewholder extends RecyclerView.ViewHolder{
        public TextView ousername,ophone,ototalprice,odatetime,oaddress;
        public Button showordersbutton;

        public Adminorderviewholder(@NonNull View itemView) {

            super(itemView);
            ousername=itemView.findViewById(R.id.username_order);
            ophone=itemView.findViewById(R.id.phonenum_order);
            ototalprice=itemView.findViewById(R.id.order_totalprice);
            odatetime=itemView.findViewById(R.id.order_date_time);
            oaddress=itemView.findViewById(R.id.order_city);
            showordersbutton=itemView.findViewById(R.id.shoeorderbtn);
        }
    }
}
