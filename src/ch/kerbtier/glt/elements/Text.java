package ch.kerbtier.glt.elements;

import ch.kerbtier.glt.font.Font;
import ch.kerbtier.lanthanum.Vec2f;

public class Text extends Element {
  private Font font;
  private String text;
  private float size;
  
  public Text(Font font, String text, float size) {
    this.font = font;
    this.text = text;
    this.size = size;
  }
  
  @Override
  public void draw(Context context) {
    context.getLayer().getProgram().setUniform("uniform_ElementTransformation", context.getTransformation());

    font.bind(context.getLayer());
    font.draw(text, new Vec2f(0, 0), size);
    font.unbind(context.getLayer());
  }

  public void setText(String text) {
    this.text = text;
  }
}
