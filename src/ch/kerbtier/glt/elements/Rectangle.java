package ch.kerbtier.glt.elements;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import ch.kerbtier.glt.gl.Texture;
import ch.kerbtier.lanthanum.Line2f;

public class Rectangle extends Element {
  private Line2f pos;
  private Texture texture;
  
  public Rectangle(Texture texture, Line2f pos) {
    this.texture = texture;
    this.pos = pos;
  }
  
  @Override
  public void draw(Context context) {
    context.getLayer().getProgram().useTexture("uniform_Texture", texture, 0);

    float[] vertices = {
        pos.sx(), pos.sy(), 0f, 1f, 0, 0,
        pos.ex(), pos.sy(), 0f, 1f, 1, 0,
        pos.ey(), pos.ey(), 0f, 1f, 1, 1, 
        pos.sx(), pos.ey(), 0f, 1f, 0, 1};
    FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
    verticesBuffer.put(vertices);
    verticesBuffer.flip();

    // Load the vertex data
    GL20.glVertexAttribPointer(context.getLayer().getProgram().getIn("in_Position"), 4, false, 6 * 4, verticesBuffer);
    GL20.glEnableVertexAttribArray(0);

    verticesBuffer.position(4);
    GL20.glVertexAttribPointer(context.getLayer().getProgram().getIn("in_Uv"), 2, false, 6 * 4, verticesBuffer);
    GL20.glEnableVertexAttribArray(0);

    GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, 4);

    context.getLayer().getProgram().releaseTexture(texture, 0);
  }
}
