package com.nairdrie.jumpytrump;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Nick on 2017-04-26.
 */
public class Wall implements GameObject{
    private int xPos;
    private int yPos;
    private boolean cashed;


    public Wall() {
        this.xPos = Constants.SCREEN_WIDTH;
        this.yPos = (int)(Math.random() * (((Constants.SCREEN_HEIGHT*0.96 - Constants.WALL_SPACING-Constants.WALL_HEIGHT) - (0.04*Constants.SCREEN_HEIGHT-Constants.WALL_HEIGHT)) + 1) + (0.04*Constants.SCREEN_HEIGHT-Constants.WALL_HEIGHT));
        this.cashed = false;
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
        if(Math.abs(player.getRectangle().centerX() - getX()) < Constants.PLAYER_WIDTH/2) {

        }


        if(player.getRectangle().intersects(getX(), getY(), getX() + Constants.WALL_WIDTH, getY() + Constants.WALL_HEIGHT)
                || player.getRectangle().intersects(getX(), getY() + Constants.WALL_HEIGHT + Constants.WALL_SPACING, getX() + Constants.WALL_WIDTH, getY() + Constants.WALL_HEIGHT + Constants.WALL_SPACING + Constants.WALL_HEIGHT)) {
            player.tap();
            return true;
        }
        return false;
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(new Rect(xPos, yPos, xPos+Constants.WALL_WIDTH, yPos+Constants.WALL_HEIGHT), new Paint());
        canvas.drawRect(new Rect(xPos, yPos+Constants.WALL_HEIGHT+Constants.WALL_SPACING, xPos+Constants.WALL_WIDTH, yPos+Constants.WALL_HEIGHT+Constants.WALL_SPACING+Constants.WALL_HEIGHT), new Paint());
    }

    public void draw(Canvas canvas, Bitmap img) {
        canvas.drawBitmap(img, null, new Rect(xPos, yPos, xPos+Constants.WALL_WIDTH, yPos+Constants.WALL_HEIGHT), new Paint());
        canvas.drawBitmap(img, null, new Rect(xPos, yPos+Constants.WALL_HEIGHT+Constants.WALL_SPACING, xPos+Constants.WALL_WIDTH, yPos+Constants.WALL_HEIGHT+Constants.WALL_SPACING+Constants.WALL_HEIGHT), new Paint());
    }

    @Override
    public void update(int state) {

    }
}
