package ch.kerbtier.glt.elements;

import java.util.Stack;

import ch.kerbtier.glt.Layer;
import ch.kerbtier.lanthanum.Mat44f;
import ch.kerbtier.lanthanum.Vec2f;

public class Context {
  private Layer layer;
  private Stack<Mat44f> transformations = new Stack<>();
  
  public Context(Layer layer) {
    this.layer = layer;
    transformations.push(new Mat44f());
    updateMatrix();
  }

  public Layer getLayer() {
    return layer;
  }
  
  public void pushRotation(float angle) {
    Mat44f rot = Mat44f.rotationZ(angle);
    transformations.push(transformations.peek().mul(rot));
    updateMatrix();
  }
  
  private void updateMatrix() {
  }

  public void pushTranslation(Vec2f translation) {
    Mat44f trans = Mat44f.translation(translation.x(), translation.y(), 0);
    transformations.push(transformations.peek().mul(trans));
    updateMatrix();
  }
  
  public Mat44f getTransformation() {
    return transformations.peek();
  }
  
  public void pop() {
    transformations.pop();
    updateMatrix();
  }
}
