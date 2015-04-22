package ch.kerbtier.glt.gl;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.lwjgl.opengl.GL20;

public class VertexShader extends Shader {

  protected int getShaderType(){
    return GL20.GL_VERTEX_SHADER;
  }

  public VertexShader(File file, Charset charset) {
    super(file, charset);
  }

  public VertexShader(InputStream stream, Charset charset) {
    super(stream, charset);
  }

  public VertexShader(String string) {
    super(string);
  }

}
