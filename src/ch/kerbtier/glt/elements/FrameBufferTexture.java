package ch.kerbtier.glt.elements;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.*;

import java.nio.ByteBuffer;

public class FrameBufferTexture extends Base {

  public FrameBufferTexture(FrameBuffer frameBuffer, boolean depth, Channels channels) {
    setID(glGenTextures());

    
    bind();

    if(depth) {
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
      glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT24, frameBuffer.getWidth(), frameBuffer.getHeight(),
          0, GL_DEPTH_COMPONENT, GL_FLOAT, (ByteBuffer) null);
    } else {
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
      glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
      // NULL means reserve texture memory, but texels are undefined
      glTexImage2D(GL_TEXTURE_2D, 0, channels.internal, frameBuffer.getWidth(),
          frameBuffer.getHeight(), 0, channels.format, channels.dataType,
          (ByteBuffer) null);
    }
    
    
    unbind();
  }

  public void doBind() {
    glBindTexture(GL_TEXTURE_2D, getID());
  }

  public void doUnbind() {
    glBindTexture(GL_TEXTURE_2D, 0);
  }
}
