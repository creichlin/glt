package ch.kerbtier.glt.font;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import ch.kerbtier.glt.Layer;

public class Letter {
  
  private Font font;
  
  private float u1; 
  private float u2; 
  private float v1; 
  private float v2; 
  
  public Letter(Font font, float u1, float v1, float u2, float v2) {
    this.font = font;
    this.u1 = u1;
    this.u2 = u2;
    this.v1 = v1;
    this.v2 = v2;
  }
  
  public void draw(float x1, float y1, float x2, float y2, Layer layer) {
    float[] vertices = {
        x1, y1, 0f, 1f, u1, v1,
        x2, y1, 0f, 1f, u2, v1,
        x2, y2, 0f, 1f, u2, v2, 
        x1, y2, 0f, 1f, u1, v2};
    FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
    verticesBuffer.put(vertices);
    verticesBuffer.flip();

    // Load the vertex data
    GL20.glVertexAttribPointer(layer.getProgram().getIn(font.getPositionVariable()), 4, false, 6 * 4, verticesBuffer);
    GL20.glEnableVertexAttribArray(0);

    verticesBuffer.position(4);
    GL20.glVertexAttribPointer(layer.getProgram().getIn(font.getUvVariable()), 2, false, 6 * 4, verticesBuffer);
    GL20.glEnableVertexAttribArray(0);

    GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, 4);
  }

}
