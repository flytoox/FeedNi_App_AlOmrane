package com.example.myloginapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;


import com.szugyi.circlemenu.view.CircleImageView;
import com.szugyi.circlemenu.view.CircleLayout;
import com.szugyi.circlemenu.view.CircleLayout.OnCenterClickListener;
import com.szugyi.circlemenu.view.CircleLayout.OnItemClickListener;
import com.szugyi.circlemenu.view.CircleLayout.OnItemSelectedListener;
import com.szugyi.circlemenu.view.CircleLayout.OnRotationFinishedListener;

// hna a7ssan CircleMenu fl 3alam kifach khdam
public class type extends AppCompatActivity implements OnItemSelectedListener,
        OnItemClickListener, OnRotationFinishedListener, OnCenterClickListener {
    protected CircleLayout circleLayout;
    protected TextView selectedTextView;
    int animationOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);

        // Set content view by passed extra

        setContentView(R.layout.activity_type);

        // Set listeners
        circleLayout = (CircleLayout) findViewById(R.id.circle_layout);
        circleLayout.setOnItemSelectedListener(this);
        circleLayout.setOnItemClickListener(this);
        circleLayout.setOnRotationFinishedListener(this);
        circleLayout.setOnCenterClickListener(this);
        for (animationOrder=0; animationOrder<circleLayout.getChildCount();animationOrder++)
        {
            circleLayout.getChildAt(animationOrder).setVisibility(View.GONE);
        }

        final Handler handler0 = new Handler();
        handler0.postDelayed(new Runnable() {
            @Override
            public void run() {
                circleLayout.getChildAt(0).setVisibility(View.VISIBLE);
                // Do something after 5s = 500ms
                circleLayout.getChildAt(0).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in_zoom_in));
            }
        }, 1);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 500ms
                circleLayout.getChildAt(1).setVisibility(View.VISIBLE);
                circleLayout.getChildAt(1).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in_zoom_in));
            }
        }, 300);

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                circleLayout.getChildAt(2).setVisibility(View.VISIBLE);
                // Do something after 5s = 500ms
                circleLayout.getChildAt(2).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in_zoom_in));
            }
        }, 600);

        final Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                circleLayout.getChildAt(3).setVisibility(View.VISIBLE);
                // Do something after 5s = 500ms
                circleLayout.getChildAt(3).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in_zoom_in));
            }
        }, 900);

        final Handler handler4 = new Handler();
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                circleLayout.getChildAt(4).setVisibility(View.VISIBLE);
                // Do something after 5s = 500ms
                circleLayout.getChildAt(4).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in_zoom_in));
            }
        }, 1200);

        final Handler handler5 = new Handler();
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                circleLayout.getChildAt(5).setVisibility(View.VISIBLE);
                // Do something after 5s = 500ms
                circleLayout.getChildAt(5).startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in_zoom_in));
            }
        }, 1500);

        String name = null;
        View view = circleLayout.getSelectedItem();
        if (view instanceof CircleImageView) {
            // view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemSelected(View view) {
        final String name;
        if (view instanceof CircleImageView) {
            name = ((CircleImageView) view).getName();
        } else {
            name = null;
        }



        switch (view.getId()) {
            case R.id.hole:
                // Handle calendar click
                break;
            case R.id.animals:
                // Handle cloud click
                break;
            case R.id.theift:
                // Handle key click
                break;
            case R.id.low_light:
                // Handle mail click
                break;
            case R.id.bad_smell:
                // Handle profile click
                break;
            case R.id.other:
                // Handle tap click
                break;
        }
    }

    @Override
    public void onItemClick(View view) {
        Intent intent=new Intent(getApplicationContext(),AddPostActivity.class);
        String name = null;
        if (view instanceof CircleImageView) {
            name = ((CircleImageView) view).getName();
        }

     //   String text = getResources().getString(R.string.start_app, name);
     //   Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

        switch (view.getId()) {
            case R.id.hole:
                intent.putExtra("Type","Sinkhole");
                break;

            case R.id.animals:
                intent.putExtra("Type","Angry Animals");
                break;

            case R.id.theift:
                intent.putExtra("Type","Theift");
                break;

            case R.id.low_light:
                intent.putExtra("Type","Low Light");
                break;

            case R.id.bad_smell:
                intent.putExtra("Type","Bad Smell");
                break;

            case R.id.other:
                intent.putExtra("Type","Other");
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onRotationFinished(View view) {
        Animation animation = new RotateAnimation(0, 360, view.getWidth() / 2, view.getHeight() / 2);
        animation.setDuration(250);
        view.startAnimation(animation);
    }

    @Override
    public void onCenterClick() {

        Toast.makeText(getApplicationContext(), R.string.center_click, Toast.LENGTH_SHORT).show();
    }



}
