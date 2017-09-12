package com.nairdrie.jumpytrump;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by Nick on 2017-08-12.
 */
public class MissileManager {

    private ArrayList<Missile> missiles;
    private long startTime;
    private BitmapFactory bf;
    private Bitmap missileImg;
    private Bitmap alertImg;
    private Bitmap kimage;
    private float speed;
    private boolean firstTap;
    private long waveStartTime;
    private int kimY;

    public MissileManager(Player trump) {
        startTime = System.currentTimeMillis();
        missiles = new ArrayList<>();
        missiles.add(new Missile(trump));
        bf = new BitmapFactory();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inScaled = false;
        missileImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.missile, options);
        missileImg = Bitmap.createScaledBitmap(missileImg, Constants.MISSILE_HEIGHT, Constants.MISSILE_WIDTH, false);
        kimage = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.kim);
        alertImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alert);
        speed = Constants.SCREEN_WIDTH / 500.0f;
        firstTap = true;
        waveStartTime = System.currentTimeMillis();
        kimY = Constants.SCREEN_HEIGHT;
    }

    public int update(int state, Player trump, Score score) {
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        if(!firstTap) {
            for (Missile missile : missiles) {
                missile.update();
                missile.move(speed * elapsedTime);
                if (missile.getX() < trump.getX() && !missile.isCashed()) {
                    missile.cash();
                    score.missileUpdate();
                    //play sound
                }
                if (missile.collision(trump)) {
                    state = 2;
                    trump.setSprite(2);
                }
                if(missile.getX() < Constants.SCREEN_WIDTH && missile.isAlertActive()) {
                    missile.removeAlert();
                }
            }
            if (getWaveTimeElapsed() < 17000 && missiles.get(missiles.size() - 1).getX() < Constants.SCREEN_WIDTH * 2 && missiles.size() < 2) {
                missiles.add(0, new Missile(trump));
            }

            if (missiles.size() > 0 && missiles.get(missiles.size() - 1).getX() + Constants.MISSILE_WIDTH < 0) {
                missiles.remove(missiles.size() - 1);
            }
            System.out.println(Constants.KIM_HEIGHT);
            if(getWaveTimeElapsed() <= 15000 && kimY > Constants.SCREEN_HEIGHT - Constants.KIM_HEIGHT*0.7) {
                kimY-=(speed*elapsedTime/6);
            }
            if(getWaveTimeElapsed() > 15000 && kimY < Constants.SCREEN_HEIGHT) {
                kimY+=(speed*elapsedTime/6);
            }
        }

        firstTap = false;
        return state;
    }

    public void draw(Canvas canvas) {
        for(Missile missile : missiles) {
            missile.draw(canvas, missileImg, alertImg);
        }
        canvas.drawBitmap(kimage, null, new Rect(Constants.SCREEN_WIDTH - (int)(Constants.KIM_WIDTH*1.5), kimY, Constants.SCREEN_WIDTH - (int)(Constants.KIM_WIDTH*1.5) + Constants.KIM_WIDTH, kimY+Constants.KIM_HEIGHT), new Paint());
    }

    public long getWaveStartTime(){
        return waveStartTime;
    }

    public long getWaveTimeElapsed(){
        return System.currentTimeMillis() - waveStartTime;
    }
}
