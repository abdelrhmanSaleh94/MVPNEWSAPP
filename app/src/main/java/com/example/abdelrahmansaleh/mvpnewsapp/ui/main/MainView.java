package com.example.abdelrahmansaleh.mvpnewsapp.ui.main;

import com.example.abdelrahmansaleh.mvpnewsapp.data.model.news.NewsBasic;

public interface MainView {
    void showLoading();

    void hideLoading();

    void getNews(NewsBasic newsBasic);

    void onError(String tittle, String message);
}
