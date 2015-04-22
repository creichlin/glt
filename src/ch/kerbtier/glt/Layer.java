package ch.kerbtier.glt;

import java.nio.charset.Charset;

import ch.kerbtier.glt.gl.FragmentShader;
import ch.kerbtier.glt.gl.Program;
import ch.kerbtier.glt.gl.VertexShader;

public class Layer {
  
  private GltDisplay display = null;
  private Program program = null;
  private String shader;
  
  private VertexShader vs;
  private FragmentShader fs;
  
  public Layer(GltDisplay display, String shader) {
    this.display = display;
    this.shader = shader;
  }
  
  
  public void init(){
    vs = new VertexShader(
        this.getClass().getResourceAsStream(shader + "-vertex.glsl"),
        Charset.forName("utf-8"));
    fs = new FragmentShader(
        this.getClass().getResourceAsStream(shader + "-fragment.glsl"),
        Charset.forName("utf-8"));

    program = new Program(shader, vs, fs);
    Error.gl("layer initialised " + shader);
  }
  
  public void reload(){
    program.destroy();
    
    vs.reload(GltDisplay.class.getResourceAsStream("vertex-" + shader + ".glsl"),
        Charset.forName("utf-8"));
    
    fs.reload(GltDisplay.class.getResourceAsStream("fragment-" + shader + ".glsl"),
        Charset.forName("utf-8"));
    
    program.create();
  }
  
  public Program getProgram() {
    return program;
  }

  public int getWidth(){
    return display.getWidth();
  }

  public int getHeight(){
    return display.getHeight();
  }
  
  public void enable() {
    program.bind();
  }
  
  public void disable(){
    program.unbind();
  }

  public int getUniform(String name) {
    return getProgram().getUniform(name);
  }
  
  public int getIn(String name) {
    return getProgram().getIn(name);
  }
}
