package com.mypack.bookapp.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mypack.bookapp.Interface.itemclicklistner;
import com.mypack.bookapp.R;

public class Itemviewholder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    public TextView txtbookname,txtbookdesc,txtbookprice,txtbookstate;
    public ImageView imageView;
    public itemclicklistner listner;
    public Itemviewholder(@NonNull View itemView) {
        super(itemView);
        imageView=(ImageView)itemView.findViewById(R.id.selbook_image1);
        txtbookprice=(TextView)itemView.findViewById(R.id.selbook_price1);
        txtbookname=(TextView) itemView.findViewById(R.id.selbook_name1);
        txtbookdesc=(TextView) itemView.findViewById(R.id.selbook_desc1);
        txtbookstate=(TextView)itemView.findViewById(R.id.selbook_state);
    }
    public void setitemclicklistner(itemclicklistner listner){

        this.listner=listner;
    }
    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(),false);
    }
}
