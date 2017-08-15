package com.scrollablelayout.simple.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.scrollablelayout.simple.R;
import com.scrollablelayout.simple.bean.RecyclerBean;

import java.util.ArrayList;
import java.util.List;


public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public PhotoAdapter() {
        super();
    }

    private List<RecyclerBean> imgLists = new ArrayList<>();

    public void setPhotos(List<RecyclerBean> photos) {
        imgLists = photos;
        notifyDataSetChanged();
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PhotoViewHolder) {
            ((PhotoViewHolder) holder).image.setTag(R.id.tag_item, position);
            Glide.with(holder.itemView.getContext())
                    .load(imgLists.get(position).getIcon())
                    .centerCrop()
                    .crossFade()
                    .dontAnimate()
                    .into(((PhotoViewHolder) holder).image);
        }
    }

    @Override
    public int getItemCount() {
        return imgLists == null ? 0 : imgLists.size();
    }

    private class PhotoViewHolder extends RecyclerView.ViewHolder {

        final ImageView image;

        PhotoViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.griditem_image, parent, false));
            image = (ImageView) itemView;
        }
    }
}
