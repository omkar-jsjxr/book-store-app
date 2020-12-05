package com.mypack.bookapp.Seller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mypack.bookapp.Admin.AdminapprovenewbookActivity;
import com.mypack.bookapp.Enduser.MainActivity;
import com.mypack.bookapp.Model.books1;
import com.mypack.bookapp.R;
import com.mypack.bookapp.viewholder.Itemviewholder;
import com.mypack.bookapp.viewholder.bookviewholder;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SellerHomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference unaprrovedref;
  private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          switch ( item.getItemId()) {
              case R.id.navigation_home:
                  Intent intenthome = new Intent(SellerHomeActivity.this, SellerHomeActivity.class);
                  startActivity(intenthome);
                  return true;

              case R.id.navigation_add:
                  Intent intentcat = new Intent(SellerHomeActivity.this, SellerbookcategoryActivity.class);
                  startActivity(intentcat);
                  return true;

              case R.id.navigation_logout:

                  final FirebaseAuth mauth;
                  mauth = FirebaseAuth.getInstance();
                  mauth.signOut();
                  Intent intent = new Intent(SellerHomeActivity.this, MainActivity.class);
                  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                  startActivity(intent);
                  finish();
                  return true;
          }
              return false;
      }
  };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home);
        BottomNavigationView navView=findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        recyclerView=findViewById(R.id.sellerecy);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        unaprrovedref= FirebaseDatabase.getInstance().getReference().child("Book");

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<books1> options=new FirebaseRecyclerOptions.Builder<books1>()
                .setQuery(unaprrovedref.orderByChild("sid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid()),books1.class).build();
        FirebaseRecyclerAdapter<books1, Itemviewholder> adapter=new FirebaseRecyclerAdapter<books1, Itemviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull Itemviewholder holder, int position, @NonNull final books1 model) {
                holder.txtbookname.setText(model.getBookname());
                holder.txtbookdesc.setText(model.getDescription());
                holder.txtbookprice.setText("Price = Rs"+model.getPrice());
                holder.txtbookstate.setText("State = "+model.getBookstate());
                Picasso.get().load(model.getImage()).into(holder.imageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String bookid=model.getBid();
                        CharSequence options[]=new CharSequence[]{

                                "yes",
                                "no"

                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(SellerHomeActivity.this);
                        builder.setTitle("Do you want to delete this book ?");
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if (i==0){
                                    deletebook(bookid);

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
            public Itemviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.selleritem_view,parent,false);
                Itemviewholder holder=new Itemviewholder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void deletebook(String bookid) {
        unaprrovedref.child(bookid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SellerHomeActivity.this,"This book is deleted successfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}