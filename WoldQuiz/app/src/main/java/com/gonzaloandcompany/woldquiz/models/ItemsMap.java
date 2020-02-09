package com.gonzaloandcompany.woldquiz.models;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;




public class ItemsMap implements ClusterItem {

    private final LatLng mPosition;
    private final String mTitle;
    private final String mSnippet;
    private final Bitmap mbandera;
    private final String mHabitantes;
    private final String mIsoCode;


    public ItemsMap(double lat, double lng, String title, String snippet, Bitmap bandera, String habitantes, String isoCode) {
        mbandera = bandera;
        mHabitantes = habitantes;
        mIsoCode = isoCode;
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }

    public Bitmap getBandera(){return  mbandera;}

    public String geHabitantes(){return  mHabitantes;}

    public String getIsoCode(){return  mIsoCode;}
}