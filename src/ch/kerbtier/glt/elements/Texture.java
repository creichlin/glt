package ch.kerbtier.glt.elements;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.PNGDecoder;

import ch.kerbtier.glt.Error;

public class Texture extends Base {

  private int width = 0;
  private int height = 0;

  /**
   * reads RGBA from buffer and creates texture
   * 
   * @param width
   * @param height
   * @param buf
   */
  private Texture(int width, int height, ByteBuffer buf, boolean alpha) {

    int type = alpha ? GL_RGBA : GL_RGB;
    
    this.width = width;
    this.height = height;

    setID(glGenTextures());
    bind();

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

    glTexImage2D(GL_TEXTURE_2D, 0, type, width, height, 0, type,
        GL_UNSIGNED_BYTE, buf);

    glPixelStorei(GL_UNPACK_ALIGNMENT, 4);
    unbind();
    
    Error.gl("load texture " + width + "/" + height + (alpha ? "alpha" : ""));
  }

  public static Texture load(BufferedImage image) {

    boolean hasAlpha = false;
    if (image.getRaster().getNumBands() == 4) {
      hasAlpha = true;
    }

    int[] pixels = new int[image.getWidth() * image.getHeight()];
    image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0,
        image.getWidth());

    ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth()
        * image.getHeight() * (hasAlpha ? 4 : 3)); // 4 for RGBA, 3 for RGB

    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        int pixel = pixels[y * image.getWidth() + x];
        buffer.put((byte) ((pixel >> 16) & 0xFF)); // Red component
        buffer.put((byte) ((pixel >> 8) & 0xFF)); // Green component
        buffer.put((byte) (pixel & 0xFF)); // Blue component
        if (hasAlpha) {
          buffer.put((byte) ((pixel >> 24) & 0xFF)); // Alpha component. Only
                                                     // for RGBA
        }
      }
    }

    buffer.flip();

    return new Texture(image.getWidth(), image.getHeight(), buffer, hasAlpha);
  }

  public static Texture loadPng(InputStream in) {
    ByteBuffer buf = null;

    try {
      // Link the PNG decoder to this stream
      PNGDecoder decoder = new PNGDecoder(in);

      // Decode the PNG file in a ByteBuffer
      buf = ByteBuffer.allocateDirect(4 * decoder.getWidth()
          * decoder.getHeight());
      decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.RGBA);
      buf.flip();

      Texture tex = new Texture(decoder.getWidth(), decoder.getHeight(), buf, true);

      in.close();

      return tex;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static Texture loadPng(String file) {
    try {
      return loadPng(new FileInputStream(file));
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public void doBind() {
    glBindTexture(GL_TEXTURE_2D, getID());
  }

  public void doUnbind() {
    glBindTexture(GL_TEXTURE_2D, 0);
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
}
