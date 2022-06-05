package com.example.numad_ujjvalsharma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyActivity4RecycleViewAdaptor extends RecyclerView.Adapter<MyActivity4RecycleViewAdaptor.ViewHolder>{


    private List<String> names;
    private List<String> urls;
    private LayoutInflater layoutInflater;
    private NameClickListener nameClickListener;

    MyActivity4RecycleViewAdaptor(Context context, List<String> names,List<String> urls) {
        this.layoutInflater = LayoutInflater.from(context);
        this.names = names;
        this.urls=urls;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.act4_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.textViewName.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewName;
        ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textView_name);
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

