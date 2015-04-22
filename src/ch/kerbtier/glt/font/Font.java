package ch.kerbtier.glt.font;

import java.util.HashMap;
import java.util.Map;

import ch.kerbtier.glt.Layer;
import ch.kerbtier.glt.gl.Bindable;
import ch.kerbtier.glt.gl.Texture;
import ch.kerbtier.lanthanum.Vec2f;

public class Font extends Bindable {

  private Map<String, Letter> letters = new HashMap<String, Letter>();

  private Texture font;

  private float factor;

  private Layer boundLayer = null;

  private String textureVariable = "uniform_Texture";
  private String positionVariable = "in_Position";
  private String uvVariable = "in_Uv";

  public Font(Texture font, int width, int height, String chars) {
    this.font = font;
    factor = width / (float) height;
    int texWidth = font.getWidth();
    int px = 0;
    int py = 0;

    for (int sp = 0; sp < chars.length(); sp++) {
      String l = chars.substring(sp, sp + 1);
      Letter letter = new Letter(this, (float) px / texWidth, (float) py
          / texWidth, (float) (px + width) / texWidth, (float) (py + height)
          / texWidth);

      letters.put(l, letter);

      px += width;
      if (px + width > texWidth) {
        px = 0;
        py += height;
      }
    }
  }

  public void draw(String chars, Vec2f pos, float scale) {
    assertBound();
    float prx = pos.x();
    for (int sp = 0; sp < chars.length(); sp++) {
      String l = chars.substring(sp, sp + 1);
      if (!" ".equals(l)) {
        Letter gle = letters.get(l);
        if (gle == null) {
          throw new RuntimeException("Invalid character: " + l);
        }
        gle.draw(prx, pos.y(), prx + scale * factor, pos.y() + scale, boundLayer);
      }
      prx += factor * scale;
    }
  }

  public Texture getTexture() {
    return font;
  }

  @Override
  public void doBind(Layer layer) {
    layer.getProgram().useTexture(textureVariable, getTexture(), 0);
    boundLayer = layer;
  }

  @Override
  public void doUnbind(Layer layer) {
    boundLayer = null;
    layer.getProgram().releaseTexture(getTexture(), 0);
  }

  public void setTextureVariable(String textureVariable) {
    this.textureVariable = textureVariable;
  }

  public void setPositionVariable(String positionVariable) {
    this.positionVariable = positionVariable;
  }

  public void setUvVariable(String uvVariable) {
    this.uvVariable = uvVariable;
  }

  public String getTextureVariable() {
    return textureVariable;
  }

  public String getPositionVariable() {
    return positionVariable;
  }

  public String getUvVariable() {
    return uvVariable;
  }

}
