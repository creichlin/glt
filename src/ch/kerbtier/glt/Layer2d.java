package ch.kerbtier.glt;

import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

import ch.kerbtier.lanthanum.Mat44f;
import ch.kerbtier.lanthanum.Vec2f;

public class Layer2d extends Layer {
  private Matrix4f projection = new Matrix4f();
  private float factor = 1;

  public Layer2d(GltDisplay display, Class<?> loader, String shader) {
    super(display, loader, shader);
    factor = display.getWidth() / (float) display.getHeight();
    Layer2d.orthographic(-2.5f, 10, -factor, factor, -1f, 1f, projection);
  }

  @Override
  public void enable() {
    super.enable();
    getProgram().setUniform(getUniform("uniform_Transformation"), projection);
    glDisable(GL_DEPTH_TEST);
    glEnable(GL_BLEND);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  }

  public void renderFullscreenUV() {
    float[] vertices = { -factor, -1f, 0f, 1f, 0, 0, factor, -1f, 0f, 1f, 1, 0, factor, 1f, 0f, 1f, 1, 1, -factor, 1,
        0f, 1f, 0, 1 };
    FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
    verticesBuffer.put(vertices);
    verticesBuffer.flip();

    // Load the vertex data
    verticesBuffer.position(0);
    GL20.glEnableVertexAttribArray(getIn("in_Position"));
    GL20.glVertexAttribPointer(getIn("in_Position"), 4, false, 6 * 4, verticesBuffer);

    verticesBuffer.position(4);
    GL20.glEnableVertexAttribArray(getIn("in_Uv"));
    GL20.glVertexAttribPointer(getIn("in_Uv"), 2, false, 6 * 4, verticesBuffer);

    GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, 4);
  }

  public void renderFullscreen() {
    float[] vertices = { -factor, -1f, 0f, 1f, factor, -1f, 0f, 1f, factor, 1f, 0f, 1f, -factor, 1, 0f, 1f };
    FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
    verticesBuffer.put(vertices);
    verticesBuffer.flip();

    // Load the vertex data
    GL20.glVertexAttribPointer(getIn("in_Position"), 4, false, 0, verticesBuffer);
    GL20.glEnableVertexAttribArray(0);
    GL11.glDrawArrays(GL11.GL_TRIANGLE_FAN, 0, 4);
  }

  public Vec2f asGlCoords(int x, int y) {
    return new Vec2f(x * factor * 2 / getWidth() - factor, y * -2.0f / getHeight() + 1);
  }

  public static Mat44f orthographic(float n, float f, float l, float r, float t, float b) {
    Matrix4f m = new Matrix4f();
    orthographic(n, f, l, r, t, b, m);
    return new Mat44f(m.m00, m.m01, m.m02, m.m03,
        m.m10, m.m11, m.m12, m.m13,
        m.m20, m.m21, m.m22, m.m23,
        m.m30, m.m31, m.m32, m.m33);
    
  }
  
  public static void orthographic(float n, float f, float l, float r, float t, float b, Matrix4f m) {

    Matrix4f.setIdentity(m);

    m.m00 = 2 / (r - l);
    m.m11 = 2 / (t - b);
    m.m22 = -2 / (f - n);

    m.m03 = -((r + l) / (r - l));
    m.m13 = -((t + b) / (t - b));
    m.m23 = -((f + n) / (f - n));

    m.transpose();
  }

}
