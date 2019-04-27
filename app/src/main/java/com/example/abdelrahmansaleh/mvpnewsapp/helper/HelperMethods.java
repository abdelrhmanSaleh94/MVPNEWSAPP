package com.example.abdelrahmansaleh.mvpnewsapp.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class HelperMethods {
    public static ColorDrawable[] vibrantLightColorList =
            {
                    new ColorDrawable( Color.parseColor( "#ffeead" ) ),
                    new ColorDrawable( Color.parseColor( "#93cfb3" ) ),
                    new ColorDrawable( Color.parseColor( "#fd7a7a" ) ),
                    new ColorDrawable( Color.parseColor( "#faca5f" ) ),
                    new ColorDrawable( Color.parseColor( "#1ba798" ) ),
                    new ColorDrawable( Color.parseColor( "#6aa9ae" ) ),
                    new ColorDrawable( Color.parseColor( "#ffbf27" ) ),
                    new ColorDrawable( Color.parseColor( "#d93947" ) )
            };

    public static ColorDrawable getRandomDrawbleColor() {
        int idx = new Random().nextInt( vibrantLightColorList.length );
        return vibrantLightColorList[idx];
    }

    public static String DateToTimeFormat(String oldstringDate) {
        PrettyTime p = new PrettyTime( new Locale( getCountry() ) );
        String isTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss'Z'",
                    Locale.ENGLISH );
            Date date = sdf.parse( oldstringDate );
            isTime = p.format( date );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isTime;
    }

    public static String DateFormat(String oldstringDate) {
        String newDate;
        SimpleDateFormat dateFormat = new SimpleDateFormat( "E, d MMM yyyy", new Locale( getCountry() ) );
        try {
            Date date = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss'Z'" ).parse( oldstringDate );
            newDate = dateFormat.format( date );
        } catch (ParseException e) {
            e.printStackTrace();
            newDate = oldstringDate;
        }

        return newDate;
    }

    public static String getCountry() {
        Locale locale = Locale.getDefault();
        String country = String.valueOf( locale.getCountry() );
        return country.toLowerCase();
    }

    public static void useGlide(Context context, ImageView imageView, String imageUrl) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error( getRandomDrawbleColor() );
        Glide.with( context ).load( imageUrl ).transition( DrawableTransitionOptions.withCrossFade() )
                .apply( requestOptions )
                .into( imageView )
        ;

    }

    public static String diplayError(Throwable e) {
        if (e instanceof HttpException) {
            ResponseBody responseBody = ((HttpException) e).response().errorBody();
            return responseBody.toString();
        } else if (e instanceof SocketTimeoutException) {
            return "SocketTimeoutException";
        } else if (e instanceof IOException) {
            return "IOException";

        } else {
            return "else";
        }

    }
}
