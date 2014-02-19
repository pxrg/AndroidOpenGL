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
public class Point extends DrawBase {
    
    @Override
    public float[] getCoords() {
        if (coords == null) {
            coords = new float[]{  // in counterclockwise order:
            -0.02f,  0.02f, 0.0f,// top
            0.02f,  0.02f, 0.0f,// top
           -0.02f, -0.02f, 0.0f,// bottom left
            0.02f, -0.02f, 0.0f // bottom right
            };
        }
        return coords;
    }

    @Override
    public float[] getColor() {
        if (color == null) {
//            color = new float[]{171.0f, 212.0f, 240.0f, 0};
            color = new float[]{171.0f, 0.0f, 0.0f, 0};
        }
        return color;
    }

    @Override
    public void draw(GL10 gl) {
        getColor();
        getCoords();
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
                GL10.GL_TRIANGLE_FAN, 0,
                getCoords().length / 3);

        // Disable vertex array drawing to avoid
        // conflicts with shapes that don't use it
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        
        
    }
    
}
