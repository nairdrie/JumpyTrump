package com.nairdrie.jumpytrump;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Nick on 2017-04-26.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private SceneManager manager;


    public GamePanel(Context context) {
        super(context);
        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT = context;
        thread = new MainThread(getHolder(), this);
        manager = new SceneManager();

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
       thread.pause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(thread.isRunning()) {
            if(event.getX() > Constants.SCREEN_WIDTH*0.9 && event.getY() < Constants.SCREEN_WIDTH*0.9) {
               thread.pause();
            }
            else {
                manager.receiveTouch(event);
            }
        } else{
            thread = new MainThread(getHolder(), this);
            thread.setRunning(true);
            thread.start();
        }

        return true;
    }

    public void update(MainThread thread) {
        manager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        /*backdropManager.draw(canvas);
        if(state > 0) {
            trump.draw(canvas);
        } else {
            trump.drawStatic(canvas);
        }
        wallManager.draw(canvas);
        score.draw(canvas);*/

        manager.draw(canvas);
    }

}
