package com.nairdrie.jumpytrump;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Nick on 2017-08-06.
 */
public class GameplayScene implements Scene {
    private Player trump;
    private WallManager wallManager;
    private MissileManager missileManager;
    private BackdropManager backdropManager;
    private int state = 0; //0:pre game 1:in game 2:dead
    private Score score;
    private int waveType;

    private BitmapFactory bf;
    private Bitmap pauseImg;

    public GameplayScene(){
        trump = new Player();
        score = new Score();
        wallManager = new WallManager();
        backdropManager = new BackdropManager();
        waveType = 0;

        bf = new BitmapFactory();
        pauseImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.pause);
    }

    @Override
    public void update(){
        if(state == 1) backdropManager.update(state);
        if(state > 0) trump.update(state);
        if(state == 1) {
            if(waveType == 0) {
                state = wallManager.update(state, trump, score);
                if (wallManager.getWaveTimeElapsed() > 20000) {
                    changeWaveType();
                    wallManager = null;
                    missileManager = new MissileManager(trump);
                }
            } else {
                state = missileManager.update(state, trump, score);
                if(missileManager.getWaveTimeElapsed() > 20000) {
                    changeWaveType();
                    missileManager = null;
                    wallManager = new WallManager();
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas){
        backdropManager.draw(canvas);
        if(state > 0) {
            trump.draw(canvas);
        } else {
            trump.drawStatic(canvas);
        }

        if(waveType == 0) {
            wallManager.draw(canvas);
        } else {
            missileManager.draw(canvas);
        }

        score.draw(canvas);
        canvas.drawBitmap(pauseImg, null, new Rect(Constants.SCREEN_WIDTH - 40 - pauseImg.getWidth(), 40, Constants.SCREEN_WIDTH - 40, 40 + pauseImg.getHeight()), new Paint());
    }

    @Override
    public void receiveTouch(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(state != 2) {
                trump.tap();
                state = 1;
            }
        }
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    public void changeWaveType() {
        if(waveType == 0) {
            waveType = 1;
        } else {
            waveType = 0;
        }
    }
}
