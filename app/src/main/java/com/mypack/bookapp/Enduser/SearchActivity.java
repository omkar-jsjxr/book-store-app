package com.mypack.bookapp.Enduser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mypack.bookapp.Model.books1;
import com.mypack.bookapp.R;
import com.mypack.bookapp.viewholder.bookviewholder;
import com.squareup.picasso.Picasso;

public class SearchActivity extends AppCompatActivity {

    private Button searchbtn;
    private EditText inputbname;
    private RecyclerView serchlist;
    private String searchinput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        inputbname=findViewById(R.id.search_book_name);
        searchbtn=findViewById(R.id.search_btn);
        serchlist=findViewById(R.id.search_list);
        serchlist.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
         searchbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        searchinput=inputbname.getText().toString();
    onStart();
    }
});
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference refrence= FirebaseDatabase.getInstance().getReference().child("Book");
        FirebaseRecyclerOptions<books1>options=new FirebaseRecyclerOptions.Builder<books1>()
                .setQuery(refrence.orderByChild("Bookname").startAt(searchinput),books1.class).build();
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
                        Intent intent=new Intent(SearchActivity.this, BookdetailActivity.class);
                        intent.putExtra("Bid",model.getBid());
                        startActivity(intent);
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
        serchlist.setAdapter(adapter);
        adapter.startListening();
    }
}