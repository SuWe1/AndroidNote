package com.ysw.rxjavaretrofit.map;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ysw.R;

import java.util.List;

/**
 * Created by Swy on 2017/3/28.
 */

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Item> list;
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.rxjava_retrofir_map_item,parent,false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        myHolder myHolder= (ItemAdapter.myHolder) holder;
        Item item=list.get(position);
        Glide.with(holder.itemView.getContext()).load(item.imageUrl).into(((myHolder)holder).imageView);
    }

    public void setItems(List<Item> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class myHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public myHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.imageView_map);
        }
    }
}
