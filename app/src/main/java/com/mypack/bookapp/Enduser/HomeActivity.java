package com.mypack.bookapp.Enduser;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mypack.bookapp.Admin.AdmineditbookActivity;
import com.mypack.bookapp.Model.books1;
import com.mypack.bookapp.Prevalent.Prevalent;
import com.mypack.bookapp.R;
import com.mypack.bookapp.viewholder.bookviewholder;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
private DatabaseReference bookref1;
private RecyclerView recyclerView;
private String type="";
RecyclerView.LayoutManager layoutmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null) {
            type = getIntent().getExtras().get("Admin").toString();
        }

        Paper.init(this);
        bookref1= FirebaseDatabase.getInstance().getReference().child("Book");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!type.equals("Admin")){
                    Intent intent=new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);
                }
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(
                this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview=navigationView.getHeaderView(0);
        TextView usernametext=headerview.findViewById(R.id.user_name);
        CircleImageView profileimage=headerview.findViewById(R.id.user_profile_image);
       // if (!type.equals("Admin")) {
            usernametext.setText(Prevalent.currentuser.getName());
            Picasso.get().load(Prevalent.currentuser.getImage()).placeholder(R.drawable.profile).into(profileimage);
       // }
        recyclerView=findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutmanager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutmanager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<books1>options=
                new FirebaseRecyclerOptions.Builder<books1>().setQuery(bookref1.orderByChild("bookstate").equalTo("approved"),books1.class).build();
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
                        if(type.equals("Admin")){
                            Intent intent=new Intent(HomeActivity.this, AdmineditbookActivity.class);
                            intent.putExtra("Bid",model.getBid());
                            startActivity(intent);
                        }
                        else{
                            Intent intent=new Intent(HomeActivity.this, BookdetailActivity.class);
                            intent.putExtra("Bid",model.getBid());
                            startActivity(intent);
                        }
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


    @Override
    public void onBackPressed() {
        DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
        super.onBackPressed();
    }}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        return super.onOptionsItemSelected(item);
    }

@SuppressWarnings("StamentWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.nav_cart) {
            if (!type.equals("Admin")) {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        }
    else if(id==R.id.nav_search) {
            if (!type.equals("Admin")) {
                Intent intent = new Intent(HomeActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        }


    else if(id==R.id.nav_setting){
            if(!type.equals("Admin")) {
                Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        }
    else if(id==R.id.nav_logout){
            if(!type.equals("Admin")) {
                Paper.book().destroy();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }
    DrawerLayout drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}