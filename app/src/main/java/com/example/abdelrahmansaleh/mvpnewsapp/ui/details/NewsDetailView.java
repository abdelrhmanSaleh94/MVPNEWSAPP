package com.example.abdelrahmansaleh.mvpnewsapp.ui.details;

public interface NewsDetailView {
    void showLoading();

    void hideLoading();

    void onError(String tittle, String message);
}
