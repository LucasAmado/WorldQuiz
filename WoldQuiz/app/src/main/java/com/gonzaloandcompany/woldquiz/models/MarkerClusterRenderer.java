package com.gonzaloandcompany.woldquiz.models;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.gonzaloandcompany.woldquiz.MainActivity;
import com.gonzaloandcompany.woldquiz.R;
import com.gonzaloandcompany.woldquiz.models.unsplash.Image;
import com.gonzaloandcompany.woldquiz.service.PaisService;
import com.gonzaloandcompany.woldquiz.service.ServiceGeneratorPais;
import com.gonzaloandcompany.woldquiz.service.unsplash.ServiceGeneratorUnsplash;
import com.gonzaloandcompany.woldquiz.service.unsplash.UnsplashService;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.text.DecimalFormat;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarkerClusterRenderer extends DefaultClusterRenderer<ItemsMap> implements ClusterManager.OnClusterClickListener<ItemsMap>, GoogleMap.OnInfoWindowClickListener {

    private static final int MARKER_DIMENSION = 48;
    private final IconGenerator iconGenerator;
    private final ImageView markerImageView;
    private LayoutInflater layoutInflater;
    private GoogleMap googleMap;
    private Context context;
    private String url;
    private Image im;
    UnsplashService unsplashService;


    public MarkerClusterRenderer(Context context, GoogleMap map, ClusterManager<ItemsMap> clusterManager) {
        super(context, map, clusterManager);
        this.context = context;
        this.googleMap = map;
        layoutInflater = LayoutInflater.from(context);

        iconGenerator = new IconGenerator(context);
        markerImageView = new ImageView(context);
        markerImageView.setLayoutParams(new ViewGroup.LayoutParams(MARKER_DIMENSION, MARKER_DIMENSION));
        iconGenerator.setContentView(markerImageView);

        clusterManager.setOnClusterClickListener(this);

        googleMap.setInfoWindowAdapter(clusterManager.getMarkerManager());

        googleMap.setOnInfoWindowClickListener(this);

        clusterManager.getMarkerCollection().setOnInfoWindowAdapter(new MyCustomClusterItemInfoView());

        googleMap.setOnCameraIdleListener(clusterManager);

        googleMap.setOnMarkerClickListener(clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(ItemsMap item, MarkerOptions markerOptions) {

        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(item.getBandera()));
    }


    public void onInfoWindowClick(Marker marker) {
        ItemsMap item = (ItemsMap) marker.getTag();
        Toast.makeText(context, item.getIsoCode(), Toast.LENGTH_SHORT).show();

        //Intent intent = new Intent(context, MainActivity.class);
        //intent.putExtra("IsoCode",item.getIsoCode());
        //context.startActivity(intent);
    }

    @Override
    public boolean onClusterClick(Cluster<ItemsMap> cluster) {
        return false;
    }

    @Override
    protected void onClusterItemRendered(ItemsMap clusterItem, Marker marker) {

        marker.setTag(clusterItem);
    }



   private class MyCustomClusterItemInfoView implements GoogleMap.InfoWindowAdapter {

        private final View clusterItemView;


        MyCustomClusterItemInfoView() {
            clusterItemView = layoutInflater.inflate(R.layout.marker_info_window, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {


            ItemsMap item = (ItemsMap) marker.getTag();
            TextView itemName = clusterItemView.findViewById(R.id.textViewNombre);
            TextView itemCapital = clusterItemView.findViewById(R.id.textViewCapital);
            TextView itemHabitantes = clusterItemView.findViewById(R.id.textViewHabitantes);
            itemName.setText(item.getTitle());
            itemCapital.setText(item.getSnippet());
            itemHabitantes.setText(String.format("%,d",Integer.parseInt(item.geHabitantes())));
            return clusterItemView;

        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }
}

