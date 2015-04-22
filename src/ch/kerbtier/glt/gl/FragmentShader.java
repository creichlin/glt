package ch.kerbtier.glt.gl;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.lwjgl.opengl.GL20;

public class FragmentShader extends Shader {
  
  protected int getShaderType(){
    return GL20.GL_FRAGMENT_SHADER;
  }
      
  public FragmentShader(File file, Charset charset) {
    super(file, charset);
  }

  public FragmentShader(InputStream stream, Charset charset) {
    super(stream, charset);
  }

  public FragmentShader(String string) {
    super(string);
  }

}
