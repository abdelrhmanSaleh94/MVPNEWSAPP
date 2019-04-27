package com.example.abdelrahmansaleh.mvpnewsapp.ui.details;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.abdelrahmansaleh.mvpnewsapp.R;
import com.example.abdelrahmansaleh.mvpnewsapp.dailoge.CustomErrorDialog;
import com.example.abdelrahmansaleh.mvpnewsapp.helper.HelperMethods;

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

public class NewsDetailActivity extends AppCompatActivity implements NewsDetailView, AppBarLayout.OnOffsetChangedListener {
    @BindView(R.id.backdrop)
    ImageView Ivbackdrop;
    @BindView(R.id.title_on_appbar)
    TextView titleOnAppbar;
    @BindView(R.id.subtitle_on_appbar)
    TextView subtitleOnAppbar;
    @BindView(R.id.title_appbar)
    LinearLayout titleAppbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.date_behavior)
    FrameLayout dateBehavior;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    private ColorDrawable randomDrawbleColor;
    private String stringSource;
    private String stringAuthour;
    private String stringContent;
    private String stringTittle;
    private String stringDesc;
    private String stringPublishAt;
    private String stringImage;
    private String stringUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_news_detail );
        ButterKnife.bind( this );
        initActionBar();
        iniIntent();

    }

    private void iniIntent() {
        Intent intent = getIntent();
        stringAuthour = intent.getStringExtra( ARTICLE_AUTHOUR );
        stringContent = intent.getStringExtra( ARTICLE_CONTENT );
        stringTittle = intent.getStringExtra( ARTICLE_TITTLE );
        stringDesc = intent.getStringExtra( ARTICLE_DESCRIPTION );
        stringUrl = intent.getStringExtra( ARTICLE_URL );
        stringImage = intent.getStringExtra( ARTICLE_URL_IMAGE );
        stringPublishAt = intent.getStringExtra( ARTICLE_PUBLISHAT );
        stringSource = intent.getStringExtra( ARTICLE_SOURCE );
        intiWebView( stringUrl );
        collapsingToolbar.setTitle( "" );
        appbar.addOnOffsetChangedListener( this );
        setLayout();
    }

    private void initActionBar() {
        setSupportActionBar( toolbar );
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled( true );
            getSupportActionBar().setTitle( "" );
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    private void setLayout() {
        title.setText( stringTittle );
        date.setText( HelperMethods.DateFormat( stringPublishAt ) );
        HelperMethods.useGlide( this, Ivbackdrop, stringImage );
        if (stringAuthour != null && stringAuthour != "") {
            stringAuthour = "\u2022" + stringAuthour + "\u2022";
        } else {
            stringAuthour = "";
        }
        time.setText( stringAuthour + stringSource + "\u2022" + HelperMethods.DateToTimeFormat( stringPublishAt ) );
        subtitleOnAppbar.setText( stringSource );
        titleOnAppbar.setText( stringTittle );
        randomDrawbleColor = HelperMethods.getRandomDrawbleColor();
    }

    private void intiWebView(String url) {
        webView.getSettings().setLoadsImagesAutomatically( true );
        webView.getSettings().setJavaScriptEnabled( true );
        webView.getSettings().setDomStorageEnabled( true );
        webView.getSettings().setSupportZoom( true );
        webView.getSettings().setBuiltInZoomControls( true );
        webView.getSettings().setDisplayZoomControls( false );
        webView.setScrollBarStyle( View.SCROLLBARS_INSIDE_OVERLAY );
        webView.setWebViewClient( new WebViewClient() );
        webView.loadUrl( url );
    }

    @Override
    public void onError(String tittle, String message) {
        new CustomErrorDialog( NewsDetailActivity.this, "News APP MVP ", "No internet" ).show();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs( verticalOffset ) / (float) maxScroll;
        if (percentage < 1f) {
            dateBehavior.setVisibility( View.VISIBLE );
            titleAppbar.setVisibility( View.GONE );
            toolbar.setBackgroundResource( R.drawable.top_shadow );


        } else if (percentage == 1f) {
            dateBehavior.setVisibility( View.GONE );
            titleAppbar.setVisibility( View.VISIBLE );
            toolbar.setBackground( randomDrawbleColor );
        }
    }
}
