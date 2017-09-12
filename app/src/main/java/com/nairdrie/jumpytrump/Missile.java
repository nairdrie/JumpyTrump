package com.nairdrie.jumpytrump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Nick on 2017-08-12.
 */
public class Missile implements GameObject{

    private int xPos;
    private int yPos;
    private boolean cashed;
    private boolean alert;
    private boolean alertFlash;
    private int upTime;


    public Missile(Player trump) {
        this.xPos = Constants.MISSILE_START;
        this.yPos = trump.getY();
        this.cashed = false;
        this.alert = true;
        this.alertFlash = false;
        this.upTime = 0;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public void move(float x) {
        xPos-=x;
    }

    public boolean isCashed () {
        return this.cashed;
    }

    public void cash() {
        this.cashed = true;
    }

    public boolean collision(Player player) {
        if(player.getRectangle().intersects(getX(), getY(), getX() + Constants.MISSILE_WIDTH, getY() + Constants.MISSILE_HEIGHT)) {
            player.tap();
            return true;
        }
        return false;
    }
    public void update() {
        if(upTime % 8 == 0) {
            alertFlash = !alertFlash;
        }
        upTime++;

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(new Rect(xPos, yPos, xPos+Constants.MISSILE_WIDTH, yPos+Constants.MISSILE_HEIGHT), new Paint());
    }

    public void draw(Canvas canvas, Bitmap missileImg, Bitmap alertImg) {
        canvas.drawBitmap(missileImg, null, new Rect(xPos, yPos, xPos+Constants.MISSILE_WIDTH, yPos+Constants.MISSILE_HEIGHT), new Paint());
        if(isAlertActive() && alertFlash) {
            canvas.drawBitmap(alertImg, Constants.SCREEN_WIDTH - alertImg.getWidth(), yPos, new Paint());
        }
    }

    @Override
    public void update(int state) {

    }

    public void removeAlert() {
        this.alert = false;
    }

    public boolean isAlertActive() {
        return alert;
    }
}
