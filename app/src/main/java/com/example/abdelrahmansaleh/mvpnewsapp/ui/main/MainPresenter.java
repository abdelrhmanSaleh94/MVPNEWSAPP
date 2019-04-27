package com.example.abdelrahmansaleh.mvpnewsapp.ui.main;

import android.util.Log;
import android.widget.Toast;

import com.example.abdelrahmansaleh.mvpnewsapp.data.model.news.NewsBasic;
import com.example.abdelrahmansaleh.mvpnewsapp.data.rest.ApiService;
import com.example.abdelrahmansaleh.mvpnewsapp.data.rest.RetrofitClient;
import com.example.abdelrahmansaleh.mvpnewsapp.helper.HelperMethods;

import java.io.IOException;
import java.net.SocketTimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;

import static android.support.constraint.Constraints.TAG;

public class MainPresenter {
    private MainView mainView;
    ApiService apiService;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
        apiService = RetrofitClient.getClient().create( ApiService.class );
    }

    public void getNewsByKeyword(String keyWord, String apiKey) {
        mainView.showLoading();
        Observable<NewsBasic> basicObservable = apiService.NEWS_SEARCH_CALL( keyWord, "publishAt", apiKey );
        basicObservable.subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe( new Observer<NewsBasic>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsBasic newsBasic) {
                        mainView.getNews( newsBasic );
                        mainView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainView.onError( "App News", HelperMethods.diplayError( e ) );
                    }

                    @Override
                    public void onComplete() {

                    }
                } );
    }

    public void getNewsData(String country, String apiKey) {
        mainView.showLoading();
        Observable<NewsBasic> newsBasicObservable = apiService.NEWS_BASIC_GET_CALL( country, apiKey );
        newsBasicObservable.
                subscribeOn( Schedulers.io() )
                .observeOn( AndroidSchedulers.mainThread() )
                .subscribe( new Observer<NewsBasic>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsBasic newsBasic) {
                        mainView.getNews( newsBasic );
                        mainView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mainView.onError( "App News", HelperMethods.diplayError( e ) );
                    }

                    @Override
                    public void onComplete() {
                        mainView.hideLoading();
                    }
                } );
    }
}
