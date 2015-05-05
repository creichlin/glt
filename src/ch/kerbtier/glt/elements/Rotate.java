package ch.kerbtier.glt.elements;

public class Rotate extends Container {
  private float angle = 0;

  public float getAngle() {
    return angle;
  }

  public void setAngle(float angle) {
    this.angle = angle;
  }

  @Override
  void draw(Context context) {
    context.pushRotation(angle);
    super.draw(context);
    context.pop();
  }
}
