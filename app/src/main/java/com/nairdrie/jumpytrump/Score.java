package com.nairdrie.jumpytrump;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Nick on 2017-06-18.
 */
public class Score {
    private int value;
    private Paint paint;
    private Typeface pixeled = Typeface.createFromAsset(Constants.CURRENT_CONTEXT.getAssets(), "fonts/AldotheApache.ttf");

    public Score() {
        value = 0;
        paint = new Paint();
        paint.setColor(Color.parseColor("#002868"));
        paint.setTextSize(140);
        paint.setTypeface(pixeled);
    }

    public void wallUpdate() {
        this.value += 10;
    }

    public void coinUpdate() {
        this.value += 50;
    }

    public void missileUpdate() {
        this.value += 20;
    }

    public int getScore() {
        return this.value;
    }

    public void resetScore() {
        this.value = 0;
    }

    public void draw(Canvas canvas) {
        //canvas.drawPaint(paint);
        canvas.drawText(Integer.toString(value), 40, 150, paint);
    }
}
