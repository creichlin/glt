package ch.kerbtier.glt.gl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public enum Channels {
  
  RGBA8(GL_RGBA8, GL_RGBA, GL_UNSIGNED_BYTE),
  R32F(GL_R32F, GL_R, GL_FLOAT),
  RGBA16(GL_RGBA16, GL_RGBA, GL_UNSIGNED_SHORT);

  public int internal;
  public int format;
  public int dataType;
  
  Channels(int internal, int format, int dataType) {
    this.internal = internal;
    this.format = format;
    this.dataType = dataType;
  }
}
