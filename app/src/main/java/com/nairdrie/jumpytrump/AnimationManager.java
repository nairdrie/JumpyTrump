package com.nairdrie.jumpytrump;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Nick on 2017-04-29.
 */
public class AnimationManager {
    private RotateAnimation[] animations;
    private int animationIndex = 0;
    private boolean isNew;

    public AnimationManager(RotateAnimation[] animations) {
        this.animations = animations;
        isNew = true;
    }

    public void playAnimation(int index) {
        for(int i = 0; i < animations.length;i++) {
            if(i == index) {
                if(!animations[index].isPlaying() || isNew)
                    animations[i].play();
            }
            else {
                animations[i].stop();
            }
        }
        animationIndex = index;
        isNew = false;
    }

    public void draw(Canvas canvas, Rect rect) {
        if(animations[animationIndex].isPlaying()) {
            animations[animationIndex].draw(canvas, rect);
        }
    }

    public void update() {
        if(animations[animationIndex].isPlaying()) {
            animations[animationIndex].update();
        }
    }

    public void makeNew() {
        isNew = true;
    }

}
