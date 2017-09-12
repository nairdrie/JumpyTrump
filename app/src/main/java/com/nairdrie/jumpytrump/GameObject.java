package com.nairdrie.jumpytrump;

import android.graphics.Canvas;

/**
 * Created by Nick on 2017-04-26.
 */
public interface GameObject {
    public void draw(Canvas canvas);
    public void update(int state);
}
