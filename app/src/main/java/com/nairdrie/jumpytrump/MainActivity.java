package com.nairdrie.jumpytrump;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {

    private int currentApiVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        currentApiVersion = android.os.Build.VERSION.SDK_INT;

        final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        // This work only for android 4.4+
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT)
        {

            getWindow().getDecorView().setSystemUiVisibility(flags);

            // Code below is to handle presses of Volume up or Volume down.
            // Without this, after pressing volume buttons, the navigation bar will
            // show up and won't hide
            final View decorView = getWindow().getDecorView();
            decorView
                    .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                    {

                        @Override
                        public void onSystemUiVisibilityChange(int visibility)
                        {
                            if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                            {
                                decorView.setSystemUiVisibility(flags);
                            }
                        }
                    });
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getRealMetrics(dm);

        switch(getWindowManager().getDefaultDisplay().getRotation()) {
            case Configuration.ORIENTATION_LANDSCAPE:
                Constants.SCREEN_HEIGHT = dm.widthPixels;
                Constants.SCREEN_WIDTH = dm.heightPixels;
                break;
            case Configuration.ORIENTATION_PORTRAIT:
            default:
                Constants.SCREEN_HEIGHT = dm.heightPixels;
                Constants.SCREEN_WIDTH = dm.widthPixels;
                break;
        }
        //Setting constants
        Constants.PLAYER_WIDTH = Constants.PLAYER_HEIGHT = (int)(Constants.SCREEN_WIDTH/4.878);
        Constants.KIM_WIDTH = (int)(Constants.PLAYER_WIDTH * 0.85);
        Constants.KIM_HEIGHT = (int)(Constants.KIM_WIDTH * 1.33);
        Constants.PLAYER_COLLISION_WIDTH = Constants.PLAYER_COLLISION_HEIGHT = (int)(Constants.SCREEN_WIDTH/6);
        Constants.WALL_WIDTH = (int)(Constants.SCREEN_WIDTH/5);
        Constants.MISSILE_HEIGHT = (int)(Constants.SCREEN_WIDTH/10);
        if(Constants.WALL_WIDTH % 20 != 0){
            int lower = (Constants.WALL_WIDTH / 20) * 20;
            int higher = ((Constants.WALL_WIDTH / 20) + 1) * 20;
            if(Constants.WALL_WIDTH-lower > higher - Constants.WALL_WIDTH) {
                Constants.WALL_WIDTH = higher;
            } else {
                Constants.WALL_WIDTH = lower;
            }
        }
        Constants.WALL_HEIGHT = (int)(Constants.WALL_WIDTH * 10);
        if(Constants.MISSILE_HEIGHT % 10 != 0){
            int lower = (Constants.MISSILE_HEIGHT / 10) * 10;
            int higher = ((Constants.MISSILE_HEIGHT / 10) + 1) * 10;
            if(Constants.MISSILE_HEIGHT-lower > higher - Constants.MISSILE_HEIGHT) {
                Constants.MISSILE_HEIGHT = higher;
            } else {
                Constants.MISSILE_HEIGHT = lower;
            }
        }
        Constants.MISSILE_WIDTH = Constants.MISSILE_HEIGHT*3;
        Constants.MISSILE_START = Constants.SCREEN_WIDTH*4;
        Constants.WALL_SPACING = (int)(Constants.PLAYER_HEIGHT * 2.2);
        Constants.DEFAULT_TAP_VELOCITY = -Constants.WALL_SPACING*2.5f/1000.0f;
        Constants.BACKGROUND_WIDTH = (int)(Constants.SCREEN_HEIGHT*160/180);

        setContentView(new GamePanel(this));
    }

    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if(currentApiVersion >= Build.VERSION_CODES.KITKAT && hasFocus)
        {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}

