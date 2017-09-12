package com.nairdrie.jumpytrump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Nick on 2017-04-30.
 */
public class Backdrop implements GameObject{
    private int xPos;


    public Backdrop(Backdrop otherBackdrop) {
        this.xPos = otherBackdrop.getX() + Constants.BACKGROUND_WIDTH;
    }

    public Backdrop() {
        this.xPos = 0;
    }

    public int getX() {
        return xPos;
    }

    public void move(float x) {
        xPos-=x;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(new Rect(xPos, 0, xPos+Constants.BACKGROUND_WIDTH, Constants.SCREEN_HEIGHT), new Paint());
    }

    public void draw(Canvas canvas, Bitmap img) {
        canvas.drawBitmap(img, null, new Rect(xPos, 0, xPos+Constants.BACKGROUND_WIDTH, Constants.SCREEN_HEIGHT), new Paint());
    }

    @Override
    public void update(int state) {
    }

}
