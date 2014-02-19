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

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class must
 * override the OpenGL ES drawing lifecycle methods:
 * <ul>
 * <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 * <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 * <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    /**
     * Store the model matrix. This matrix is used to move models from object
     * space (where each model can be thought of being located at the center of
     * the universe) to world space.
     */
    private float[] mModelMatrix = new float[16];
    /**
     * Store the view matrix. This can be thought of as our camera. This matrix
     * transforms world space to eye space; it positions things relative to our
     * eye.
     */
    private float[] mViewMatrix = new float[16];
    /**
     * Store the projection matrix. This is used to project the scene onto a 2D
     * viewport.
     */
    private float[] mProjectionMatrix = new float[16];
    /**
     * Allocate storage for the final combined matrix. This will be passed into
     * the shader program.
     */
    private float[] mMVPMatrix = new float[16];
    /**
     * This will be used to pass in the transformation matrix.
     */
    private int mMVPMatrixHandle;
    private Triangle mTriangle;
    private Square mSquare;
    private DrawBase mLine;
    private DrawBase mPoint;
    private float width;
    private float height;
    private float mAngle;
    private float mScaleX;
    private float mScaleY;
    private float mMoveX;
    private float mMoveY;
    private boolean moving;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mTriangle = new Triangle();
        mSquare = new Square();
        mLine = new Line();
        mPoint = new Point();

        mScaleX = 1;
        mScaleY = 1;
        moving = false;
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        // Draw background color
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // Set GL_MODELVIEW transformation mode
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();   // reset the matrix to its default state

        // When using GL_MODELVIEW, you must set the view point
        GLU.gluLookAt(gl, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);


        // Create a rotation for the triangle

        // Use the following code to generate constant rotation.
        // Leave this code out when using TouchEvents.
        // long time = SystemClock.uptimeMillis() % 4000L;
        // float angle = 0.090f * ((int) time);

//        // Efeito de Girar o elemento
//        gl.glRotatef(mAngle, 0.0f, 0.0f, 1.0f);
//        // Efeito de Zoom
//        gl.glScalef(mScaleX, mScaleY, 0);
//            Efeito de Mover
//        gl.glTranslatef(mMoveX, mMoveY, 0);
//        // Draw square
//        mSquare.draw(gl);
//
//        // Draw triangle
//        mTriangle.draw(gl);

        // Draw Line
        mLine.draw(gl);

        gl.glTranslatef(mMoveX, mMoveY, 0);
        // Draw point
        mPoint.draw(gl);
//        // Ao setar para ser redesenhada continuamente (SurfaceView) 
//        // o codigo abaixo que faz a rotacao continua
//        mAngle += 1.0f;
        mMoveX += mMoveX < -2 ? 4 : -0.02f;
        if (mMoveY != 0.0 && !moving) {
            mMoveY += mMoveY > 0.0 ? -0.05f : 0.05f;
            if (mMoveY < 0.05 && mMoveY > -0.05) {
                mMoveY = 0;
            }
        }
        moving = false;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Adjust the viewport based on geometry changes
        // such as screen rotations
        gl.glViewport(0, 0, width, height);

        // make adjustments for screen ratio
        float ratio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);        // set matrix to projection mode
        gl.glLoadIdentity();                        // reset the matrix to its default state
        gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);  // apply the projection matrix

    }

    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
    public float getAngle() {
        return mAngle;
    }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setAngle(float angle) {
        mAngle = angle;
    }

    public float getScaleX() {
        return mScaleX;
    }

    public float getScaleY() {
        return mScaleY;
    }

    public float getMoveX() {
        return mMoveX;
    }

    public float getMoveY() {
        return mMoveY;
    }

    public void setScale(float mScaleX, float mScaleY) {
        this.mScaleX = mScaleX < 2 ? mScaleX : 2;
        this.mScaleY = mScaleY < 2 ? mScaleY : 2;
    }

    public void setMove(float moveX, float moveY) {
        this.mMoveX = moveX < 1 ? moveX > -1 ? moveX : -1 : 0.2f;
        this.mMoveY = moveY < 1 ? moveY > -1 ? moveY : -1 : 0.9f;
    }

    public void setMoveY(float moveY) {
        moving = true;
        this.mMoveY = moveY < 1 ? moveY > -1 ? moveY : -1 : 0.9f;
    }
}