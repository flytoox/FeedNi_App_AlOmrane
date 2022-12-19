package com.example.myloginapp.cluster;


import android.content.Context;
import android.graphics.Bitmap;

import androidx.core.content.ContextCompat;

import com.example.myloginapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;


// had l package kaml khass bles clusters
public class CustomClusterRenderer extends DefaultClusterRenderer<MyClusterItem> {

    private final IconGenerator mClusterIconGenerator;
    private final Context mContext;

    public CustomClusterRenderer(Context context, GoogleMap map, ClusterManager<MyClusterItem> clusterManager) {
        super(context, map, clusterManager);

        mContext = context;
        mClusterIconGenerator = new IconGenerator(mContext);
    }
// _____________________ hna fin kan7t l achkaal dyal les markers _____________________//
    @Override
    protected void onBeforeClusterItemRendered(MyClusterItem item, MarkerOptions markerOptions) {
        BitmapDescriptor markerDescriptor;
       switch (item.getType()){
            case "Sinkhole": markerDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.hole);
                break;

            case "Angry Animals": markerDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.animals);
                break;

            case "Theift": markerDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.theif);
                break;

            case "Low Light": markerDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.light);
                break;

            case "Bad Smell": markerDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.smell);
                break;

            case "Other": markerDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.more);
                break;


            default:
                throw new IllegalStateException("Unexpected value: " + item.getType());
        }
            markerOptions.icon(markerDescriptor).snippet(item.getTitle());

    }

    @Override
    protected void onBeforeClusterRendered(Cluster<MyClusterItem> cluster, MarkerOptions markerOptions) {

        if (cluster.getSize() > 6) {
            mClusterIconGenerator.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_cluster_circle));
        } else {
            mClusterIconGenerator.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_cluster_circle2));
        }

        mClusterIconGenerator.setTextAppearance(R.style.AppTheme_WhiteTextAppearance);

        String clusterTitle = String.valueOf(cluster.getSize());
        Bitmap icon = mClusterIconGenerator.makeIcon(clusterTitle);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
    }

}