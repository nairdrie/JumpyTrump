package com.nairdrie.jumpytrump;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Nick on 2017-04-27.
 */
public class WallManager {
    private ArrayList<Wall> walls;
    private long startTime;
    private BitmapFactory bf;
    private Bitmap wallImg;
    private float speed;
    private boolean firstTap;
    private long waveStartTime;

    public WallManager() {
        startTime = System.currentTimeMillis();
        walls = new ArrayList<>();
        walls.add(new Wall());
        bf = new BitmapFactory();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inScaled = false;
        wallImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.wall, options);
        wallImg = Bitmap.createScaledBitmap(wallImg, Constants.WALL_HEIGHT, Constants.WALL_WIDTH, false);
        speed = Constants.SCREEN_WIDTH / 3000.0f;
        firstTap = true;
        waveStartTime = System.currentTimeMillis();
    }

    public int update(int state, Player trump, Score score) {
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        if(!firstTap) {
            for (Wall wall : walls) {
                wall.move(speed * elapsedTime);
                if (wall.getX() < trump.getX() && !wall.isCashed()) {
                    wall.cash();
                    score.wallUpdate();
                    //play sound
                }
                if (wall.collision(trump)) {
                    state = 2;
                    trump.setSprite(2);
                }
            }
            if (getWaveTimeElapsed() < 17000 && walls.get(walls.size() - 1).getX() < Constants.SCREEN_WIDTH * 7 / 24 && walls.size() < 2) {
                walls.add(0, new Wall());
            }

            if (walls.size() > 0 && walls.get(walls.size() - 1).getX() + Constants.WALL_WIDTH < 0) {
                walls.remove(walls.size() - 1);
            }

        }
        firstTap = false;
        return state;
    }

    public void draw(Canvas canvas) {
        for(Wall wall : walls) {
            wall.draw(canvas, wallImg);
        }
    }

    public long getWaveStartTime(){
        return waveStartTime;
    }

    public long getWaveTimeElapsed(){
        return System.currentTimeMillis() - waveStartTime;
    }

}
