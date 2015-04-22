package ch.kerbtier.glt.gl;

import java.nio.FloatBuffer;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL13.*;

import org.lwjgl.util.vector.Matrix4f;
import ch.kerbtier.glt.Error;

public class Program extends Base {

  private Set<String> errorsPrinted = new HashSet<String>();

  private VertexShader vs = null;
  private FragmentShader fs = null;

  private String name;

  public Program(String name, VertexShader vs, FragmentShader fs) {
    this.name = name;
    this.vs = vs;
    this.fs = fs;
    create();
  }

  public int getIn(String variable) {
    int u = glGetAttribLocation(getID(), variable);

    if (u == -1 && !errorsPrinted.contains(variable)) {
      System.out.println("WARNING (" + this.name + "): attribute " + variable
          + " does not exist");
      errorsPrinted.add(variable);
    }

    return u;
  }

  public int getUniform(String name) {
    int u = glGetUniformLocation(getID(), name);
    if (u == -1 && !errorsPrinted.contains(name)) {
      System.out.println("WARNING (" + this.name + "): uniform " + name
          + " does not exist");
    }
    errorsPrinted.add(name);
    return u;
  }

  public void doBind() {
    glUseProgram(getID());
  }

  public void doUnbind() {
    glUseProgram(0);
  }

  public void setUniform(int uniform, float v1, float v2, float v3, float v4) {
    assertBound();
    glUniform4f(uniform, v1, v2, v3, v4);
  }

  public void setUniform(int uniform, float v1) {
    assertBound();
    glUniform1f(uniform, v1);
  }

  public void setUniform(int uniform, int i1) {
    assertBound();
    glUniform1i(uniform, i1);
  }

  public void setUniform(String name, float f) {
    setUniform(getUniform(name), f);
  }

  public void setUniform(int uniform, Matrix4f matrix) {
    assertBound();
    FloatBuffer buf = BufferUtils.createFloatBuffer(16);
    matrix.store(buf);
    buf.rewind();
    glUniformMatrix4(uniform, false, buf);
  }

  public void setUniform(String name, float v1, float v2, float v3, int v4) {
    setUniform(getUniform(name), v1, v2, v3, v4);
  }

  public void useTexture(String name, Texture tex, int pos) {
    assertBound();
    int uid = getUniform(name);
    glActiveTexture(GL_TEXTURE0 + pos);
    tex.bind();
    glEnable(GL_TEXTURE_2D);
    glUniform1i(uid, pos);
  }

  public void useTexture(String name, FrameBufferTexture tex, int pos) {
    assertBound();
    int uid = getUniform(name);
    glActiveTexture(GL_TEXTURE0 + pos);
    tex.bind();
    glEnable(GL_TEXTURE_2D);
    glUniform1i(uid, pos);
  }

  public void releaseTexture(FrameBufferTexture tex, int pos) {
    glActiveTexture(GL_TEXTURE0 + pos);
    tex.unbind();
  }

  public void releaseTexture(Texture tex, int pos) {
    glActiveTexture(GL_TEXTURE0 + pos);
    tex.unbind();
  }

  public void destroy() {
    glDetachShader(getID(), vs.getID());
    glDetachShader(getID(), fs.getID());

    glDeleteProgram(getID());
  }

  public void create() {
    setID(glCreateProgram());
    glAttachShader(getID(), vs.getID());
    glAttachShader(getID(), fs.getID());
    glLinkProgram(getID());

    glValidateProgram(getID());
    Error.gl("program");
  }

}
