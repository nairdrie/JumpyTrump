package com.nairdrie.jumpytrump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Nick on 2017-04-29.
 */
public class RotateAnimation {
    private Bitmap img;
    private int frameIndex;
    boolean isPlaying;
    private float degPerFrame;
    private int numFrames;

    public RotateAnimation(Bitmap img, float animationTime, int degrees) {
        this.img = img;
        frameIndex = 0;
        this.degPerFrame = degrees/(30*animationTime);
        numFrames = (int)(animationTime*30);
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void play() {
        isPlaying=true;
        frameIndex = 0;
    }

    public void stop() {
        isPlaying = false;
    }

    public void draw(Canvas canvas, Rect destination) {
        if(!isPlaying) {
            return;
        }
        canvas.rotate(degPerFrame*frameIndex, (destination.left+destination.right)/2, (destination.top+destination.bottom)/2);
        canvas.drawBitmap(img, null, destination, new Paint());
        canvas.rotate(-degPerFrame*frameIndex, (destination.left+destination.right)/2, (destination.top+destination.bottom)/2);
    }
    public void update() {
        if(!isPlaying) {
            return;
        }
        frameIndex++;
        if(frameIndex >= numFrames) {
            isPlaying = false;

        }
    }
}
