package com.mypack.bookapp.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mypack.bookapp.Interface.itemclicklistner;
import com.mypack.bookapp.R;

public class CartviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtbookname,txtbookprice,txtbookquantity;
    private itemclicklistner itemClickListner;
    public CartviewHolder(@NonNull View itemView) {
        super(itemView);
        txtbookname=itemView.findViewById(R.id.cart_bookname);
        txtbookprice=itemView.findViewById(R.id.cart_bookprice);
        txtbookquantity=itemView.findViewById(R.id.cart_bookquant);

    }


    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view,getAdapterPosition(),false);
    }

    public void setItemClickListner(itemclicklistner itemClickListner) {

        this.itemClickListner = itemClickListner;
    }
}
