package ch.kerbtier.glt;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class GltDisplay {

  
  private int w = -1;
  private int h = -1;
  private boolean fullscreen;
  
  private List<Layer> layers = new ArrayList<Layer>();
  private boolean initialised = false;

  public GltDisplay(int width, int height, boolean fullscreen) {
    this.w = width;
    this.h = height;
    this.fullscreen = fullscreen;
  }

  public void init() {
    try {
      DisplayMode displayMode = null;
      for(DisplayMode dm: Display.getAvailableDisplayModes()){
        if(dm.getWidth() == w && dm.getHeight() == h){
          displayMode = dm;
          System.out.println("DM: " + dm.getWidth() + "/" + dm.getHeight() + " " + dm.getBitsPerPixel() + " " + dm.isFullscreenCapable() + " *");
        } else {
          System.out.println("DM: " + dm.getWidth() + "/" + dm.getHeight() + " " + dm.getBitsPerPixel() + " " + dm.isFullscreenCapable());
        }
      }
      
      
      Display.setDisplayMode(displayMode);
      Display.setVSyncEnabled(true);
      Display.setFullscreen(fullscreen);
      Display.setTitle("glt");
      Display.create();
      System.out
          .println("OpenGL version: " + glGetString(GL_VERSION));

      initialised = true;
    } catch (Exception e) {
      System.out.println("Error setting up display");
      System.exit(0);
    }

    glEnable(GL_DEPTH_TEST);
    glEnable(GL_TEXTURE_2D);
    glClearColor(0.4f, 0.6f, 0.9f, 0f);
    glViewport(0, 0, w, h);
    glEnable(GL_CULL_FACE);
    glFrontFace(GL_CW);
    
    Error.gl("initialised");
    
    for(Layer layer: layers) {
      layer.init();
    }
    
  }
  
  public void add(Layer layer) {
    if(initialised) {
      throw new RuntimeException("cannot add layer when already initialised");
    }
    layers.add(layer);
  }
  
  public void clear() {
    glClear(GL_DEPTH_BUFFER_BIT);
    Error.gl("clear");
  }
  
  public void end() {
    // Put everything back to default (deselect)
    glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

    glBindVertexArray(0);

    Error.gl("loop");
    Display.sync(30);
    Display.update();
  }
  

  public void destroy() {
    // Disable the VBO index from the VAO attributes list
    glDisableVertexAttribArray(0);

    Display.destroy();
  }


  public int getWidth() {
    return w;
  }

  public int getHeight() {
    return h;
  }

}
