package com.example.numad_ujjvalsharma;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyActivity7RecycleViewAdaptor extends RecyclerView.Adapter<MyActivity7RecycleViewAdaptor.ViewHolder>{


    private LayoutInflater layoutInflater;
    private NameClickListener nameClickListener;
    List<Product> products;
    public MyActivity7RecycleViewAdaptor(Context context,List<Product> products) {
        this.layoutInflater = LayoutInflater.from(context);
        this.products = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.act7_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        System.out.println(products);
        viewHolder.textViewProductTitle.setText("Name: "+products.get(position).title);
        viewHolder.textViewProductPrice.setText("Price: "+products.get(position).price+"$");

        Runnable loadImg=()->{
            URL url = null;
            try {
                url = new URL(products.get(position).imgUrl);
                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.imageView.setImageBitmap(bmp);
                    }
                });

                System.out.println("not got an exception");
            } catch (Exception e) {
                System.out.println("got an exception");
                e.printStackTrace();
            }
        };
        new Thread(loadImg).start();

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewProductTitle;
        TextView textViewProductPrice;
        ImageView imageView;
        ViewHolder(View itemView) {
            super(itemView);
            textViewProductTitle = itemView.findViewById(R.id.textViewProductTitle);
            textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
            imageView= itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            if (nameClickListener != null){
                nameClickListener.onClickName(view, getAdapterPosition());
            }
        }
    }

    void setClickListener(NameClickListener itemClickListener) {
        this.nameClickListener = itemClickListener;
    }

    public interface NameClickListener {
        void onClickName(View view, int position);
    }
}
