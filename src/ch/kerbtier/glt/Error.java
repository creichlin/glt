package ch.kerbtier.glt;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Error {
  public static void gl(String name){
    int errorCheckValue = GL11.glGetError();
    if (errorCheckValue != GL11.GL_NO_ERROR) {
      System.out.println("GL_ERROR: " + name + ": "
          + GLU.gluErrorString(errorCheckValue));
    }
  }
}
