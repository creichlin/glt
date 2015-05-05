package ch.kerbtier.glt.elements;

import ch.kerbtier.lanthanum.Vec2f;

public class Translate extends Container {
  private Vec2f translation = new Vec2f(0, 0);

  @Override
  void draw(Context context) {
    context.pushTranslation(translation);
    super.draw(context);
    context.pop();
  }

  public Vec2f getTranslation() {
    return translation;
  }

  public void setTranslation(Vec2f translation) {
    this.translation = translation;
  }
}
