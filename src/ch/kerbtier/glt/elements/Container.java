package ch.kerbtier.glt.elements;

import java.util.ArrayList;
import java.util.List;

public class Container extends Element {

  private List<Element> elements = new ArrayList<>();
  
  public void add(Element element) {
    elements.add(element);
  }
  
  public void remove(Element element) {
    elements.remove(element);
  }

  @Override
  void draw(Context context) {
    for(Element element: elements) {
      element.draw(context);
    }
  }
  
}
