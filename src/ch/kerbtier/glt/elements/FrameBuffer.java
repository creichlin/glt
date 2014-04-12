package ch.kerbtier.glt.elements;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

public class FrameBuffer extends Base {

  private static int[] COLOR_ATTACHMENTS = new int[] { GL_COLOR_ATTACHMENT0,
      GL_COLOR_ATTACHMENT1, GL_COLOR_ATTACHMENT2, GL_COLOR_ATTACHMENT3 };

  private FrameBufferTexture[] textures = new FrameBufferTexture[4];

  private int width = 1024;
  private int height = 1024;

  private int depthID = -1;

  private boolean useDepthTexture = false;
  
  private FrameBufferTexture depthTexture = null;

  public FrameBufferTexture getDepthTexture() {
    return depthTexture;
  }

  public FrameBuffer(int width, int height, boolean useDepthTexture) {
    this.width = width;
    this.height = height;
    this.useDepthTexture = useDepthTexture;

    setID(glGenFramebuffers());

    bind();

    // set up depthbuffer
    if (this.useDepthTexture) {
      System.out.println("USING DEPTH TEXTURE");
      depthTexture = new FrameBufferTexture(this, true, null);

      glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT,
          GL_TEXTURE_2D, depthTexture.getID(), 0);
    } else {  
      depthID = glGenRenderbuffers();
      glBindRenderbuffer(GL_RENDERBUFFER, depthID);
      glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH_COMPONENT, width, height);
      glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT,
          GL_RENDERBUFFER, depthID);
    }

    if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
      throw new RuntimeException("INVALID FRAMEBUFFER");
    }
    glClearColor(0, 0, 0, 0);
    glEnable(GL_TEXTURE_2D);
    unbind();
  }

  public FrameBufferTexture create(int pos) {
    return create(pos, Channels.RGBA8);
  }
  
  public FrameBufferTexture create(int pos, Channels channels) {
    FrameBufferTexture fbt = new FrameBufferTexture(this, false, channels);

    bind();

    glFramebufferTexture2D(GL_FRAMEBUFFER, COLOR_ATTACHMENTS[pos],
        GL_TEXTURE_2D, fbt.getID(), 0);

    if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
      throw new RuntimeException("INVALID FRAMEBUFFER");
    }

    unbind();
    textures[pos] = fbt;
    return fbt;
  }

  public void doBind() {
    glBindFramebuffer(GL_FRAMEBUFFER, getID());

    List<Integer> cal = new ArrayList<Integer>();

    for (int cnt = 0; cnt < textures.length; cnt++) {
      if (textures[cnt] != null) {
        cal.add(COLOR_ATTACHMENTS[cnt]);
      }
    }

    IntBuffer ib = BufferUtils.createIntBuffer(cal.size());
    for (int i : cal) {
      ib.put(i);
    }
    ib.position(0);

    glDrawBuffers(ib);
    glViewport(0, 0, width, height);
  }

  public void clearDepth() {
    assertBound();
    glClear(GL_DEPTH_BUFFER_BIT);
  }

  public void doUnbind() {
    glBindFramebuffer(GL_FRAMEBUFFER, 0);
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }
}
