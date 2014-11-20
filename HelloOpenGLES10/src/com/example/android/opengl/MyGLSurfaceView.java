/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * A view container where OpenGL ES graphics can be drawn on screen. This view
 * can also be used to capture touch events, such as a user interacting with
 * drawn objects.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer();
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
//        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        // Sempre atualizando a tela chamando o onDrawFrame do Renderer
//        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
//        move(e);
//        rotate(e);
//        zoom(e);
//        movePoint(e);
        nextPoint(e);
        return true;
    }

    private void nextPoint(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mRenderer.next();
                requestRender();
        }
    }
    
    private void movePoint(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float y = e.getY();
                float dy = mRenderer.getMoveY();

                if (e.getX() > 600) {
                    dy += 0.02f;
                } else {
                    dy -= 0.02f;
                }
                mRenderer.setMoveY(dy);
                requestRender();
        }
    }

    private void zoom(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float x = e.getX();
                float y = e.getY();
                float dx = mRenderer.getScaleX();
                float dy = mRenderer.getScaleY();

                if (x > 600) {
                    dx += 0.1f;
                    dy += 0.1f;
                } else {
                    dx -= 0.1f;
                    dy -= 0.1f;
                }
                mRenderer.setScale(dx, dy);
                requestRender();
        }
    }

    private void move(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float x = e.getX();
                float y = e.getY();
                float dx = 0;
                float dy = mRenderer.getMoveY();

                if (x > 600) {
                    dy += 0.05f;
                } else {
                    dy -= 0.05f;
                }
                mRenderer.setMove(dx, dy);
//                requestRender();
        }
    }

    private void rotate(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, we are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1;
                }

                mRenderer.setAngle(
                        mRenderer.getAngle()
                        + ((dx + dy) * TOUCH_SCALE_FACTOR));  // = 180.0f / 320
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
    }
}
