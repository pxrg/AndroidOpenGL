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
public class Circle extends DrawBase {

    int sides;
    float radius;

    public Circle() {
        super();
    }

    @Override
    public float[] getCoords() {
        sides = 40;
        radius = 0.05f;
        coords = DrawCoords(0, 0, sides, radius);
//        coords = DrawLoop(0, 0, sides, 0.5f, 0.5f);
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
//        gl.glPushMatrix();
//        gl.glTranslatef(0, 0, 0);
//  gl.glScalef(size, size, 1.0f);
        // draw the shape
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glColor4f( // set color:
                color[0], color[1],
                color[2], color[3]);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, coords.length / 3);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
//        gl.glPopMatrix();
    }

    public float[] DrawLoop(float centerX, float centerY, int sides, float innerRadius, float outerRadius) {
        float[] vertices = new float[(sides + 1) * 3];
        for (int i = 1; i <= sides; i += 4) {
            vertices[i + 0] = (float) (centerX + (Math.sin(Math.toRadians(360f * (i / sides))) * innerRadius));
            vertices[i + 1] = (float) (centerY - (Math.cos(Math.toRadians(360f * (i / sides))) * innerRadius));
            vertices[i + 2] = (float) (centerX + (Math.sin(Math.toRadians(360f * (i / sides))) * outerRadius));
//            vertices[i + 3] = (float) (centerY - (Math.cos(Math.toRadians(360f * (i / sides))) * outerRadius));
        }
        return vertices;
    }

    public float[] DrawCoords(float centerX, float centerY, int sides, float radius) {
        float[] vertices = new float[(sides + 1) * 3];
        vertices[0] = (float) (centerX + (Math.cos((1 * 360 / (sides * 3)) * (3.14 / 180)) * radius));
        for (int i = 3; i < (sides + 1) * 3; i += 3) {
            double rad = (i * 360 / (sides * 3)) * (3.14 / 180);
            vertices[i] = (float) (centerX + (Math.cos(rad) * radius));
            vertices[i + 1] = (float) (centerY + Math.sin(rad) * radius);
            vertices[i + 2] = 0;
        }
        return vertices;
    }
}
