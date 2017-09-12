package com.nairdrie.jumpytrump;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Nick on 2017-04-30.
 */
public class BackdropManager {
    private ArrayList<Backdrop> backdrops;
    private long startTime;
    private BitmapFactory bf;
    private Bitmap backdropImg;
    private float speed;
    private boolean firstTap;

    public BackdropManager() {
        startTime = System.currentTimeMillis();
        backdrops = new ArrayList<>();

        backdrops.add(new Backdrop());
        while(backdrops.size() * Constants.BACKGROUND_WIDTH < Constants.SCREEN_WIDTH ){
            backdrops.add(new Backdrop(backdrops.get(backdrops.size() - 1)));
        }

        bf = new BitmapFactory();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inScaled = false;
        backdropImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.backdrop, options);
        backdropImg = Bitmap.createScaledBitmap(backdropImg, Constants.SCREEN_HEIGHT*160/180, Constants.SCREEN_HEIGHT, false);
        speed = Constants.SCREEN_WIDTH / 15000.0f;
        firstTap = true;
    }

    public void update(int state) {
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        if(!firstTap) {
            for (Backdrop backdrop : backdrops) {
                backdrop.move((int) (speed * elapsedTime));
            }
            if (backdrops.get(backdrops.size() - 1).getX() + Constants.BACKGROUND_WIDTH < Constants.SCREEN_WIDTH) {
                backdrops.add(new Backdrop(backdrops.get(backdrops.size() - 1)));
                System.out.println("added backdrop");
            }
            if (backdrops.get(backdrops.size() - 1).getX() + Constants.BACKGROUND_WIDTH < 0) {
                backdrops.remove(backdrops.size() - 1);
            }
        }
        firstTap = false;
    }

    public void draw(Canvas canvas) {
        for(Backdrop backdrop : backdrops) {
            backdrop.draw(canvas, backdropImg);
        }
    }
}
