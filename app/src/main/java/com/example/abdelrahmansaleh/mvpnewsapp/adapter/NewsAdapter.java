package com.example.abdelrahmansaleh.mvpnewsapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.abdelrahmansaleh.mvpnewsapp.R;
import com.example.abdelrahmansaleh.mvpnewsapp.data.model.news.Article;
import com.example.abdelrahmansaleh.mvpnewsapp.helper.HelperMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    Context context;
    List<Article> articleList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public NewsAdapter(Context context, List<Article> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( context ).inflate( R.layout.news_item_card, parent, false );
        return new NewsViewHolder( view ,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, int position) {
        Article article = articleList.get( position );
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder( HelperMethods.getRandomDrawbleColor() );
        requestOptions.error( HelperMethods.getRandomDrawbleColor() );
        requestOptions.diskCacheStrategy( DiskCacheStrategy.ALL );
        requestOptions.centerCrop();
        Glide.with( context ).load( article.getUrlToImage() )
                .apply( requestOptions ).listener( new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                holder.cardNewsProgressImgeLoading.setVisibility( View.GONE );
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                holder.cardNewsProgressImgeLoading.setVisibility( View.GONE );
                return false;
            }
        } ).transition( DrawableTransitionOptions.withCrossFade() )
                .into( holder.cardNewsImage );
        holder.cardNewsTvPublishAt.setText( HelperMethods.DateFormat( article.getPublishedAt() ) );
        holder.cardNewsTvAuthor.setText( article.getAuthor() );
        holder.cardNewsTvDec.setText( article.getDescription() );
        holder.cardNewsTvSource.setText( article.getSource().getName() );
        holder.cardNewsTvTitle.setText( article.getTitle() );
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClicked(View view, int position);
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClickListener onItemClickListener;
        @BindView(R.id.cardNewsImage)
        ImageView cardNewsImage;
        @BindView(R.id.cardNewsProgressImgeLoading)
        ProgressBar cardNewsProgressImgeLoading;
        @BindView(R.id.cardNewsTvAuthor)
        TextView cardNewsTvAuthor;
        @BindView(R.id.cardNewsTvPublishAt)
        TextView cardNewsTvPublishAt;
        @BindView(R.id.cardNewsTvTitle)
        TextView cardNewsTvTitle;
        @BindView(R.id.cardNewsTvDec)
        TextView cardNewsTvDec;
        @BindView(R.id.cardNewsTvSource)
        TextView cardNewsTvSource;

        public NewsViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super( itemView );
            ButterKnife.bind( this, itemView );
            itemView.setOnClickListener( this );
            this.onItemClickListener = onItemClickListener ;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClicked( v, getAdapterPosition() );
        }
    }
}
