package com.nairdrie.jumpytrump;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * Created by Nick on 2017-04-26.
 */
public class Player implements GameObject {
    private Rect spriteRectangle;
    private Rect collisionRectangle;
    private long startTime;
    private long tapTime;
    private RotateAnimation flap1;
    private RotateAnimation flap2;
    private RotateAnimation flap3;
    private RotateAnimation flap4;
    private RotateAnimation flap5;
    private AnimationManager animationManager;
    private float tapVelocity;
    private float vy;
    private int sprite;
    private boolean firstTap;
    private boolean firstDeathFrame;
    BitmapFactory bf;
    public Player() {
        this.spriteRectangle = new Rect(Constants.SCREEN_WIDTH/2 - Constants.PLAYER_WIDTH/2,
                Constants.SCREEN_HEIGHT/2 - Constants.PLAYER_HEIGHT/2,
                Constants.SCREEN_WIDTH/2 + Constants.PLAYER_WIDTH/2,
                Constants.SCREEN_HEIGHT/2 + Constants.PLAYER_HEIGHT/2);
        this.collisionRectangle = new Rect(Constants.SCREEN_WIDTH/2 - Constants.PLAYER_COLLISION_WIDTH/2,
                Constants.SCREEN_HEIGHT/2 - Constants.PLAYER_COLLISION_HEIGHT/2,
                Constants.SCREEN_WIDTH/2 + Constants.PLAYER_COLLISION_WIDTH/2,
                Constants.SCREEN_HEIGHT/2 + Constants.PLAYER_COLLISION_HEIGHT/2);
        bf = new BitmapFactory();
        flap1 = new RotateAnimation(bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.trump1), 2, 90);
        flap2 = new RotateAnimation(bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.trump2), 2, 90);
        flap3 = new RotateAnimation(bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.trump3), 2, 90);
        flap4 = new RotateAnimation(bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.trump4), 2, 90);
        flap5 = new RotateAnimation(bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.trump5), 2, 90);
        startTime = System.currentTimeMillis();
        tapTime = System.currentTimeMillis();
        tapVelocity = Constants.DEFAULT_TAP_VELOCITY;
        vy = tapVelocity;
        sprite = 1;
        firstTap = true;
        firstDeathFrame = true;
        animationManager = new AnimationManager(new RotateAnimation[] {flap1, flap2, flap3, flap4, flap5});
        System.out.println("Y pos: "+this.getY()+"\nheight: "+Constants.SCREEN_HEIGHT);
    }
    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas, spriteRectangle);
    }
    public void drawStatic(Canvas canvas) {
        canvas.drawBitmap(bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.trump2), null, spriteRectangle, new Paint());
    }
    @Override
    public void update(int state) {
            int elapsedTime = (int) (System.currentTimeMillis() - startTime);
            startTime = System.currentTimeMillis();
            if(!firstTap) {

                if(getY() + Constants.PLAYER_HEIGHT > -100) {
                    this.move(0, vy * elapsedTime);
                }
                vy += Constants.WALL_SPACING / 3000.0f;
                if (state == 1 || firstDeathFrame) {
                    animationManager.playAnimation(sprite);
                    animationManager.update();
                    if(state == 2) {
                        firstDeathFrame = false;
                    }
                }
            }
        firstTap = false;
    }

    public Rect getRectangle() {
        return this.collisionRectangle;
    }

    public void tap() {
        int elapsedTime = (int) (System.currentTimeMillis() - tapTime);
        tapTime = System.currentTimeMillis();

        if (elapsedTime < 333) {
            tapVelocity -= Constants.WALL_SPACING / 3000.0f;
        } else {
            tapVelocity = Constants.DEFAULT_TAP_VELOCITY;
        }

        this.vy = tapVelocity;
        this.sprite = (int) (Math.random() * 5);
        animationManager.makeNew();
        //play woosh;
    }

    public void move(float relativeX, float relativeY) {
        spriteRectangle.left+=relativeX;
        spriteRectangle.right+=relativeX;
        spriteRectangle.top+=relativeY;
        spriteRectangle.bottom+=relativeY;
        collisionRectangle.left+=relativeX;
        collisionRectangle.right+=relativeX;
        collisionRectangle.top+=relativeY;
        collisionRectangle.bottom+=relativeY;
    }

    public int getY() {
        return this.spriteRectangle.top;
    }

    public int getX() {
        return this.spriteRectangle.left;
    }

    public void setSprite(int sprite){
        if(sprite > 0 && sprite < 6) {
            this.sprite = sprite;
        }
    }
}
