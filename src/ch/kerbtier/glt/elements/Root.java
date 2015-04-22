package ch.kerbtier.glt.elements;

import ch.kerbtier.glt.Layer;

public class Root {
  private Element element;
  
  public void setElement(Element element) {
    this.element = element;
  }
  
  public void draw(Layer layer) {
    Context context = new Context();
    context.setLayer(layer);
    element.draw(context);
  }
}
