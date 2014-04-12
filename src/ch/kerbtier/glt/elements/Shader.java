package ch.kerbtier.glt.elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static org.lwjgl.opengl.GL20.*;

import ch.kerbtier.glt.Error;

public abstract class Shader extends Base {
  
  public Shader(String code) {
    read(code);
  }

  public Shader(File file, Charset charset) {
    read(file, charset);
  }

  public Shader(InputStream stream, Charset charset) {
    read(stream, charset);
  }

  protected abstract int getShaderType();

  
  public void reload(InputStream stream, Charset charset){
    
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream,
        charset));

    StringBuffer sb = new StringBuffer();
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    glDeleteShader(getID());
    
    setID(glCreateShader(getShaderType()));
    glShaderSource(getID(), sb.toString());
    glCompileShader(getID());
  }
  
  protected void read(String code) {
    setID(glCreateShader(getShaderType()));
    glShaderSource(getID(), code);
    glCompileShader(getID());
    
    Error.gl("shader");
  }

  private void read(File file, Charset charset) {
    try {
      InputStream is = new FileInputStream(file);
      read(is, charset);
      is.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void read(InputStream stream, Charset charset) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream,
        charset));

    StringBuffer sb = new StringBuffer();
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line + "\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    read(sb.toString());
  }
  
  public void doBind(){
    
  }
  
  public void doUnbind(){
    
  }
}
