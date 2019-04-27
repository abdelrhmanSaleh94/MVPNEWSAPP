package com.example.abdelrahmansaleh.mvpnewsapp.ui.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abdelrahmansaleh.mvpnewsapp.R;
import com.example.abdelrahmansaleh.mvpnewsapp.adapter.NewsAdapter;
import com.example.abdelrahmansaleh.mvpnewsapp.dailoge.CustomErrorDialog;
import com.example.abdelrahmansaleh.mvpnewsapp.data.model.news.Article;
import com.example.abdelrahmansaleh.mvpnewsapp.data.model.news.NewsBasic;
import com.example.abdelrahmansaleh.mvpnewsapp.ui.details.NewsDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.abdelrahmansaleh.mvpnewsapp.helper.Constant.ARTICLE_AUTHOUR;
import static com.example.abdelrahmansaleh.mvpnewsapp.helper.Constant.ARTICLE_CONTENT;
import static com.example.abdelrahmansaleh.mvpnewsapp.helper.Constant.ARTICLE_DESCRIPTION;
import static com.example.abdelrahmansaleh.mvpnewsapp.helper.Constant.ARTICLE_PUBLISHAT;
import static com.example.abdelrahmansaleh.mvpnewsapp.helper.Constant.ARTICLE_SOURCE;
import static com.example.abdelrahmansaleh.mvpnewsapp.helper.Constant.ARTICLE_TITTLE;
import static com.example.abdelrahmansaleh.mvpnewsapp.helper.Constant.ARTICLE_URL;
import static com.example.abdelrahmansaleh.mvpnewsapp.helper.Constant.ARTICLE_URL_IMAGE;

public class MainActivity extends AppCompatActivity implements MainView, SwipeRefreshLayout.OnRefreshListener {
    MainPresenter mainPresenter;
    @BindView(R.id.mainActivityTvHead)
    TextView mainActivityTvHead;
    @BindView(R.id.mainActivityRvNews)
    RecyclerView mainActivityRvNews;
    @BindView(R.id.mainActivityTvScroll)
    NestedScrollView mainActivityTvScroll;
    @BindView(R.id.mainActivitySwipeRefreshLayout)
    SwipeRefreshLayout mainActivitySwipeRefreshLayout;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        ButterKnife.bind( this );
        mainPresenter = new MainPresenter( this );
        mainActivitySwipeRefreshLayout.setOnRefreshListener( this );
        mainActivitySwipeRefreshLayout.setColorSchemeResources( R.color.colorAccent );
        onLoading( "" );
    }

    @Override
    public void showLoading() {
        mainActivitySwipeRefreshLayout.setRefreshing( true );
        findViewById( R.id.loadingView ).setVisibility( View.VISIBLE );

    }

    @Override
    public void hideLoading() {
        mainActivitySwipeRefreshLayout.setRefreshing( false );
        findViewById( R.id.loadingView ).setVisibility( View.GONE );
    }

    @Override
    public void getNews(final NewsBasic newsBasic) {
        List<Article> articles = newsBasic.getArticles();
        mainActivityRvNews.setLayoutManager( new LinearLayoutManager( this ) );
        NewsAdapter adapter = new NewsAdapter( getApplicationContext(), articles );
        mainActivityRvNews.setAdapter( adapter );
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener( new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                ImageView imageView = view.findViewById( R.id.cardNewsImage );
                Intent intent = new Intent( MainActivity.this, NewsDetailActivity.class );
                Article article = newsBasic.getArticles().get( position );
                intent.putExtra( ARTICLE_AUTHOUR, article.getAuthor() );
                intent.putExtra( ARTICLE_CONTENT, article.getContent() );
                intent.putExtra( ARTICLE_TITTLE, article.getTitle() );
                intent.putExtra( ARTICLE_DESCRIPTION, article.getDescription() );
                intent.putExtra( ARTICLE_URL, article.getUrl() );
                intent.putExtra( ARTICLE_URL_IMAGE, article.getUrlToImage() );
                intent.putExtra( ARTICLE_PUBLISHAT, article.getPublishedAt() );
                intent.putExtra( ARTICLE_SOURCE, article.getSource().getName() );
                Pair<View, String> pair = Pair.create( (View) imageView, ViewCompat.getTransitionName( imageView ) );
                ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation( MainActivity.this, pair );
                startActivity( intent, compat.toBundle() );
            }
        } );
    }


    @Override
    public void onError(String tittle, String message) {
        CustomErrorDialog customErrorDialog = new CustomErrorDialog( MainActivity.this, tittle, message );
        customErrorDialog.show();
    }

    private void onLoading(final String keyWord) {
        if (!keyWord.isEmpty()) {
            mainActivitySwipeRefreshLayout.post( new Runnable() {
                @Override
                public void run() {
                    mainPresenter.getNewsByKeyword( keyWord, "cc8e4be714664a31ae09984c83f34459" );
                }
            } );
        } else {
            mainActivitySwipeRefreshLayout.post( new Runnable() {
                @Override
                public void run() {
                    mainPresenter.getNewsData( "us", "cc8e4be714664a31ae09984c83f34459" );
                }
            } );
        }
    }

    @Override
    public void onRefresh() {
        onLoading( "" );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate( R.menu.menu, menu );
        SearchManager searchManager = (SearchManager) getSystemService( Context.SEARCH_SERVICE );
        searchView = (SearchView) menu.findItem( R.id.menuSearch ).getActionView();
        MenuItem menuItem = menu.findItem( R.id.menuSearch );
        searchView.setSearchableInfo( searchManager.getSearchableInfo( getComponentName() ) );
        searchView.setQueryHint( "Search News......." );
        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                onLoading( s );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        } );
        menuItem.getIcon().setVisible( false, false );
        return true;
    }
}
