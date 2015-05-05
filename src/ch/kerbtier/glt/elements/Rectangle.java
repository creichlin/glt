package ch.kerbtier.glt.elements;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import ch.kerbtier.glt.gl.Texture;
import ch.kerbtier.lanthanum.Vec2f;

public class Rectangle extends Element {
  private Vec2f size;
  private Vec2f sh;
  private Texture texture;
  
  public Rectangle(Texture texture, Vec2f size) {
    this.texture = texture;
    this.size = size;
    this.sh = size.mul(0.5f);
  }
  
  @Override
  public void draw(Context context) {
    context.getLayer().getProgram().useTexture("uniform_Texture", texture, 0);
    context.getLayer().getProgram().setUniform("uniform_ElementTransformation", context.getTransformation());
    float[] vertices = {
        -sh.x(), -sh.y(), 0, 1, 0, 0,
        sh.x(), -sh.y(), 0, 1, 1, 0,
        sh.x(), sh.y(), 0, 1, 1, 1, 
        -sh.x(), sh.y(), 0, 1, 0, 1};
    
    FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
    verticesBuffer.put(vertices);
    verticesBuffer.flip();

    // Load the vertex data
    verticesBuffer.position(0);
    GL20.glEnableVertexAttribArray(context.getLayer().getProgram().getIn("in_Position"));
    GL20.glVertexAttribPointer(context.getLayer().getProgram().getIn("in_Position"), 4, false, 6 * 4, verticesBuffer);
    GL20.glEnableVertexAttribArray(0);

    verticesBuffer.position(4);
    GL20.glEnableVertexAttribArray(context.getLayer().getProgram().getIn("in_Uv"));
    GL20.glVertexAttribPointer(context.getLayer().getProgram().getIn("in_Uv"), 2, false, 6 * 4, verticesBuffer);
    GL20.glEnableVertexAttribArray(0);

    GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, 4);

    context.getLayer().getProgram().releaseTexture(texture, 0);
  }
}
