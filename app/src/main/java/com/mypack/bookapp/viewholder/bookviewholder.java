package com.mypack.bookapp.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mypack.bookapp.Interface.itemclicklistner;
import com.mypack.bookapp.R;

public class bookviewholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtbookname,txtbookdesc,txtbookprice;
        public ImageView imageView;
        public itemclicklistner listner;
    public bookviewholder(@NonNull View itemView) {
        super(itemView);
        imageView=(ImageView)itemView.findViewById(R.id.book_image1);
        txtbookprice=(TextView)itemView.findViewById(R.id.book_price1);
        txtbookname=(TextView) itemView.findViewById(R.id.book_name1);
        txtbookdesc=(TextView) itemView.findViewById(R.id.book_desc1);
    }
        public void setitemclicklistner(itemclicklistner listner){

        this.listner=listner;
        }
    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(),false);
    }
}
