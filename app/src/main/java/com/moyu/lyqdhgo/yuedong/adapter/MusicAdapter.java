package com.moyu.lyqdhgo.yuedong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moyu.lyqdhgo.yuedong.R;
import com.moyu.lyqdhgo.yuedong.bean.Media;

import java.util.List;

/**
 * Created by lyqdhgo on 2016/7/8.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyViewHolder> implements View.OnClickListener {

    private int position;
    private Context context;
    private List<Media> list;
    private OnRecycleViewItemClickListener onItemClickListener = null;

    public MusicAdapter(Context context, List<Media> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Media media = list.get(position);
        holder.tvName.setText(media.getName());
        holder.tvArtist.setText(media.getArtist());
        holder.itemView.setTag(media);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            Media media = (Media) v.getTag();
            onItemClickListener.onItemClick(v, media);
        }
    }

    public void setOnItemClickListener(OnRecycleViewItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvArtist;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.name);
            tvArtist = (TextView) itemView.findViewById(R.id.artist);
        }
    }

    public interface OnRecycleViewItemClickListener {
        void onItemClick(View view, Media media);
    }
    public interface OnItemClickListener {
        public void onItemClick(View view,int postion);
    }
}
