package ch.kerbtier.glt.elements;

import ch.kerbtier.glt.Layer;


public abstract class Bindable {
  private int bindCount = 0;

  
  public void bind(Layer layer) {
    if(bindCount != 0) {
      System.out.println(this + " = " + bindCount);
      throw new RuntimeException("can only be bound once");
    }
    doBind(layer);
    bindCount++;
  }
  
  public void unbind(Layer layer){
    if(bindCount != 1) {
      System.out.println(this + " = " + bindCount);
      throw new RuntimeException("must be bound to unbind");
    }
    doUnbind(layer);
    bindCount--;
  }
  
  public void bind(){
    if(bindCount != 0) {
      System.out.println(this + " = " + bindCount);
      throw new RuntimeException("can only be bound once");
    }
    doBind();
    bindCount++;
  }
  
  public void unbind(){
    if(bindCount != 1) {
      System.out.println(this + " = " + bindCount);
      throw new RuntimeException("must be bound to unbind");
    }
    doUnbind();
    bindCount--;
  }
  
  public void assertBound() {
    if(bindCount == 0) {
      throw new RuntimeException("not bound");
    }
  }
  
  
  public void doBind(){
    throw new UnsupportedOperationException();
  }

  public void doUnbind() {
    throw new UnsupportedOperationException();
  }

  public void doUnbind(Layer layer) {
    throw new UnsupportedOperationException();
  }

  public void doBind(Layer layer) {
    throw new UnsupportedOperationException();
  }
}
