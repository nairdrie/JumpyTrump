package com.nairdrie.jumpytrump;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Nick on 2017-08-06.
 */
public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void receiveTouch(MotionEvent event);
}
