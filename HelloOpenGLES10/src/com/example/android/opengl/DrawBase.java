/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

/**
 *
 * @author paulo.gomes
 */
public abstract class DrawBase {

    protected FloatBuffer vertexBuffer;
    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    /**
     * How many bytes per float.
     */
    private final int mBytesPerFloat = 4;
    float coords[];
    protected float color[];

    public DrawBase() {
        makeBufferCoords(getCoords());
    }
    
    public void makeBufferCoords(float[] coord){
         // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                coord.length * mBytesPerFloat);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(coord);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);
    }

    public abstract float[] getCoords();

    public abstract float[] getColor();

    public abstract void draw(GL10 gl);
}
