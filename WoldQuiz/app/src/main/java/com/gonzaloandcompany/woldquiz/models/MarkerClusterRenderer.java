package com.gonzaloandcompany.woldquiz.models;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

public class MarkerClusterRenderer extends DefaultClusterRenderer<ItemsMap> {

    private static final int MARKER_DIMENSION = 48;  // 2
    private final IconGenerator iconGenerator;
    private final ImageView markerImageView;

    public MarkerClusterRenderer(Context context, GoogleMap map, ClusterManager<ItemsMap> clusterManager) {
        super(context, map, clusterManager);

        iconGenerator = new IconGenerator(context);  // 3
        markerImageView = new ImageView(context);
        markerImageView.setLayoutParams(new ViewGroup.LayoutParams(MARKER_DIMENSION, MARKER_DIMENSION));
        iconGenerator.setContentView(markerImageView);
    }

    @Override
    protected void onBeforeClusterItemRendered(ItemsMap item, MarkerOptions markerOptions) { // 5

        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(item.getBandera()));  // 8
        markerOptions.title(item.getTitle());
    }
}

