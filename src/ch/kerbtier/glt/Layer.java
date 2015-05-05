package ch.kerbtier.glt;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.lwjgl.util.vector.Matrix4f;

import ch.kerbtier.glt.gl.FragmentShader;
import ch.kerbtier.glt.gl.FrameBufferTexture;
import ch.kerbtier.glt.gl.Program;
import ch.kerbtier.glt.gl.Texture;
import ch.kerbtier.glt.gl.VertexShader;

public class Layer {

  private GltDisplay display = null;
  private Program program = null;
  private String shader;
  private Class<?> loader;

  private VertexShader vs;
  private FragmentShader fs;

  public Layer(GltDisplay display, Class<?> loader, String shader) {
    this.display = display;
    this.shader = shader;
    this.loader = loader;
  }

  public void init() {
    InputStream vss = loader.getResourceAsStream(shader + "-vertex.glsl");
    InputStream fss = loader.getResourceAsStream(shader + "-fragment.glsl");
    
    if(vss == null || fss == null) {
      throw new RuntimeException("could not find shader files " + loader + " " + shader + "-vertex.glsl/" + shader + "-fragment.glsl");
    }
    
    vs = new VertexShader(vss, Charset.forName("utf-8"));
    fs = new FragmentShader(fss, Charset.forName("utf-8"));

    program = new Program(shader, vs, fs);

    Error.gl("layer initialised " + shader);
  }

  public void reload() {
    program.destroy();

    vs.reload(GltDisplay.class.getResourceAsStream("vertex-" + shader + ".glsl"), Charset.forName("utf-8"));

    fs.reload(GltDisplay.class.getResourceAsStream("fragment-" + shader + ".glsl"), Charset.forName("utf-8"));

    program.create();
  }

  public Program getProgram() {
    return program;
  }

  public int getWidth() {
    return display.getWidth();
  }

  public int getHeight() {
    return display.getHeight();
  }

  public void enable() {
    program.bind();
  }

  public void disable() {
    program.unbind();
  }

  public int getUniform(String name) {
    return getProgram().getUniform(name);
  }

  public int getIn(String name) {
    return getProgram().getIn(name);
  }

  public void setUniform(int uniform, float v1, float v2, float v3, float v4) {
    program.setUniform(uniform, v1, v2, v3, v4);
  }

  public void setUniform(int uniform, float v1) {
    program.setUniform(uniform, v1);
  }

  public void setUniform(int uniform, int i1) {
    program.setUniform(uniform, i1);
  }

  public void setUniform(String name, float f) {
    program.setUniform(name, f);
  }

  public void setUniform(int uniform, Matrix4f matrix) {
    program.setUniform(uniform, matrix);
  }

  public void setUniform(String name, float v1, float v2, float v3, int v4) {
    program.setUniform(name, v1, v2, v3, v4);
  }

  public void useTexture(String name, Texture tex, int pos) {
    program.useTexture(name, tex, pos);
  }

  public void useTexture(String name, FrameBufferTexture tex, int pos) {
    program.useTexture(name, tex, pos);
  }

  public void releaseTexture(FrameBufferTexture tex, int pos) {
    program.releaseTexture(tex, pos);
  }

  public void releaseTexture(Texture tex, int pos) {
    program.releaseTexture(tex, pos);
  }
  
  
}
