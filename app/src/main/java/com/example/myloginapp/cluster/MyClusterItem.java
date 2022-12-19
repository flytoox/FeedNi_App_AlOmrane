package com.example.myloginapp.cluster;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyClusterItem implements ClusterItem {

    private String title;
    private String snippet;
    private LatLng latLng;
    private String type;

    public MyClusterItem(String title, String snippet, LatLng latLng, String type) {
        this.title = title;
        this.snippet = snippet;
        this.latLng = latLng;
        this.type = type;
    }

    @Override
    public LatLng getPosition() {
        return latLng;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return snippet;
    }

    public String getType() {
        return type;
    }

}
