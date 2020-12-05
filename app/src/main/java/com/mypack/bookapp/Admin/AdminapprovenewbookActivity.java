package com.mypack.bookapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mypack.bookapp.Interface.itemclicklistner;
import com.mypack.bookapp.Model.books1;
import com.mypack.bookapp.R;
import com.mypack.bookapp.viewholder.bookviewholder;
import com.squareup.picasso.Picasso;

public class AdminapprovenewbookActivity extends AppCompatActivity {
private RecyclerView recyclerView;
RecyclerView.LayoutManager layoutManager;
private DatabaseReference unaprrovedref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminapprovenewbook);
        recyclerView=findViewById(R.id.adminapproverecy);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        unaprrovedref= FirebaseDatabase.getInstance().getReference().child("Book");
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<books1>options=new FirebaseRecyclerOptions.Builder<books1>()
                .setQuery(unaprrovedref.orderByChild("bookstate").equalTo("not approved"),books1.class).build();
        FirebaseRecyclerAdapter<books1, bookviewholder>adapter=new FirebaseRecyclerAdapter<books1, bookviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull bookviewholder holder, int position, @NonNull final books1 model) {
                holder.txtbookname.setText(model.getBookname());
                holder.txtbookdesc.setText(model.getDescription());
                holder.txtbookprice.setText("Price = Rs"+model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.imageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String bookid=model.getBid();
                        CharSequence options[]=new CharSequence[]{

                                "yes",
                                "no"

                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(AdminapprovenewbookActivity.this);
                        builder.setTitle("Do you want to approve this book ?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if (i==0){
                                    changebookstate(bookid);

                                }
                                if (i==1){


                                }
                            }
                        });
                        builder.show();
                    }
                });

            }

            @NonNull
            @Override
            public bookviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.book_layout,parent,false);
                    bookviewholder holder=new bookviewholder(view);
                    return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void changebookstate(String bookid) {
        unaprrovedref.child(bookid).child("bookstate").setValue("approved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AdminapprovenewbookActivity.this,"This book is approved to for sell",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}