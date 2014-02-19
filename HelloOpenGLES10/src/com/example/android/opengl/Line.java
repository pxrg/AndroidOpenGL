/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.android.opengl;

import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author paulo.gomes
 */
public class Line extends DrawBase {

    @Override
    public void draw(GL10 gl) {
        getColor();

        // Since this shape uses vertex arrays, enable them
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        // draw the shape
        gl.glColor4f( // set color:
                color[0], color[1],
                color[2], color[3]);
        gl.glVertexPointer( // point to vertex data:
                COORDS_PER_VERTEX,
                GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glDrawArrays( // draw shape:
                GL10.GL_LINES, 0,
                getCoords().length / COORDS_PER_VERTEX);

        // Disable vertex array drawing to avoid
        // conflicts with shapes that don't use it
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    @Override
    public float[] getColor() {
        if (color == null) {
            color = new float[]{255.0f, 255.0f, 255.0f, 0.0f};
        }
        return color;
    }

    @Override
    public float[] getCoords() {
        coords = new float[]{
            -2.0f, 0.0f, 0.0f,
            2.0f, 0.0f, 0.0f, //                -2.0f, 0.05f, 0.0f,
        //                2.0f, 0.05f, 0.0f,
        //                
        //                -2.0f, 0.1f, 0.0f,
        //                2.0f, 0.1f, 0.0f,
        };
        return coords;
    }
}
