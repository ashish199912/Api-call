package com.example.apilistdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class AdapterItems extends RecyclerView.Adapter<AdapterItems.ViewHolder> {

    private POJOItems[] listdata;
    Context mContext;
    MainActivity mThis;

    // RecyclerView recyclerView;
    public AdapterItems(POJOItems[] listdata, Context context, MainActivity mThis) {
        this.listdata = listdata;
        mContext=context;
        this.mThis = mThis;
    }

    @Override
    public AdapterItems.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.adapter_potrait, parent, false);

        AdapterItems.ViewHolder viewHolder = new AdapterItems.ViewHolder(listItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final POJOItems myListData = listdata[position];
        holder.name.setText(listdata[position].getName());
        holder.slug.setText(listdata[position].getSlug());
        holder.year.setText(listdata[position].getYear());
        holder.count.setText(listdata[position].getCount());
//        holder.img.setText(listdata[position].getName());
        String imageUrl = listdata[position].getImg();

        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .placeholder(mContext.getDrawable(R.drawable.ic_baseline_image_not_supported_24))
                .error(mContext.getDrawable(R.drawable.ic_baseline_image_not_supported_24))
                .dontAnimate()
                .dontTransform();

        Glide.with(mContext)
                .asBitmap()
                .load(imageUrl)
                .apply(requestOptions)
                .into(holder.img);

    }


    @Override
    public int getItemCount() {
        return listdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, slug, year, count;
        public ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);
            this.slug = (TextView) itemView.findViewById(R.id.slug);
            this.year = (TextView) itemView.findViewById(R.id.year);
            this.count = (TextView) itemView.findViewById(R.id.count);
            this.img = (ImageView) itemView.findViewById(R.id.img);

        }
    }

}
