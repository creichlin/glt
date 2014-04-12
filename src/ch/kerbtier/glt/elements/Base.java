package ch.kerbtier.glt.elements;

public abstract class Base extends Bindable {
  
  private int ID = -1;
  
  public int getID(){
    return this.ID;
  }
  
  protected void setID(int ID){
    this.ID = ID;
  }
}
