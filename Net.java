package sample;
//Initial Prototype using a Linked list implementation,
//Not Actually Implemented
public class Net
{
  private boolean equalTemperament;
  private double etSemitone = 1.059463;
  private double minThird = Math.pow(etSemitone, 3);
  private double majThird = Math.pow(etSemitone, 4);
  private double fifth = Math.pow(etSemitone, 7);
  public Node current;

public Net(Node base, int howMany) {
  this.current = base;
//  System.out.println("starting");
//  System.out.println(current.label);
  addLeft(howMany);
//  System.out.println(current.label);
  goRight(howMany);
//  System.out.println(current.label);
 addRight(howMany);
//  System.out.println(current.label);
  goLeft(howMany);
//  System.out.println(current.label);
  addTopLeft(howMany);
//  System.out.println(current.label);
 goBottomRight(howMany);
//  System.out.println(current.label);
addTopRight(howMany);
//  System.out.println("whatup");
//  System.out.println(current.label);
 goBottomLeft(1);
//  System.out.println(current.label);
 addBottomRight(howMany);
//  System.out.println(current.label);
 goTopLeft(howMany);
//  System.out.println(current.label);
 addBottomLeft(howMany);
//  System.out.println(current.label);
  goTopRight(howMany);
//  System.out.println(current.label);
//
//  System.out.println(majThird+"maj"+minThird+"min");
//  System.out.println(current.left.frequency);
//  System.out.println(current.left.label);
//  System.out.println(current.right.frequency);
//  System.out.println(current.right.label);
//  System.out.println(current.topLeft.frequency);
//  System.out.println(current.topLeft.label);
//  System.out.println(current.topRight.frequency);
//  System.out.println(current.topRight.label);
//  System.out.println(current.frequency);
//  System.out.println(current.label);
//  System.out.println(current.bottomLeft.frequency);
//  System.out.println(current.bottomLeft.label);
//  System.out.println(current.bottomRight.frequency);
//  System.out.println(current.bottomRight.label);
}
public String getLabel(String from, int offset){
  String[] labels = new String[12];
  labels[0] = "A";
  labels[1] = "A#";
  labels[2] = "B";
  labels[3] = "C";
  labels[4] = "C#";
  labels[5] = "D";
  labels[6] = "D#";
  labels[7] = "E";
  labels[8] = "F";
  labels[9] = "F#";
  labels[10] = "G";
  labels[11] = "G#";
  int fromNum = -1;
  for (int i = 0; i < 12; i++)
  {
    if (labels[i].equals(from)){
      fromNum = i;
      break;
    }
  }
  int newIndex = offset + fromNum;
  if(newIndex > -1 && newIndex < 12 && fromNum!= -1){
    return labels[newIndex];
  }
  if(newIndex < 0 && newIndex > -13 && fromNum!= -1){
    return labels[(newIndex+12)];
  }
  if(newIndex < 24 && newIndex > 11 && fromNum!= -1){
    return labels[(newIndex-12)];
  }
  else return "Error";
}
public void addLeft(int howMany){
    while(howMany > 0){
      current.left = new Node(current.frequency/fifth, getLabel(current.label, -7));
      Node right = current;
      current = current.left;
      current.right = right;
      //System.out.println(current.label);
      howMany--;
    }
}
  public void addTopLeft(int howMany){
    while(howMany > 0){
      current.topLeft = new Node(current.frequency/minThird, getLabel(current.label, -3));
      Node bottomRight = current;
      current = current.topLeft;
      current.bottomRight = bottomRight;
      //System.out.println(current.label);
      howMany--;
    }
  }
  public void addTopRight(int howMany){
    while(howMany > 0){
      current.topRight = new Node(current.frequency*(majThird), getLabel(current.label, 4));
      Node bottomLeft = current;
      current = current.topRight;
      current.bottomLeft = bottomLeft;
      howMany--;
    }
  }
  public void addRight(int howMany){
    while(howMany > 0){
      current.right = new Node(current.frequency*(fifth), getLabel(current.label, 7));
      Node left = current;
      current = current.right;
      current.left = left;
      //System.out.println(current.label);
      //System.out.println(current.frequency);
      howMany--;
    }
  }
  public void addBottomRight(int howMany){
    while(howMany > 0){
      current.bottomRight = new Node(current.frequency*(minThird) , getLabel(current.label, 3));
      Node topLeft = current;
      current = current.bottomRight;
      current.topLeft = topLeft;
      //System.out.println(current.label);
      howMany--;
    }
  }
  public void addBottomLeft(int howMany){
    while(howMany > 0){
      current.bottomLeft = new Node(current.frequency/majThird, getLabel(current.label, -4));
      Node topRight = current;
      current = current.bottomLeft;
      current.topRight = topRight;
      //System.out.println(current.label);
      //System.out.println(current.frequency);
      howMany--;
    }
  }
  public void goLeft(int howMany){
    while(howMany > 0)
    {
      if (current.left != null)
      {
        current = current.left;
        //System.out.println(current.label);
      }
      howMany--;
    }
  }
  public void goTopLeft(int howMany){
  while(howMany > 0){
      if(current.topLeft != null){
      current = current.topLeft;
        //System.out.println(current.label);
    }
    howMany--;
  }
  }
  public void goBottomLeft(int howMany){
  while(howMany > 0){
    if(current.bottomLeft != null){
      current = current.bottomLeft;

    }
    howMany--;
  }
  }
  public void goBottomRight(int howMany){
  while(howMany > 0){
    if(current.bottomRight != null){
      current = current.bottomRight;
      //System.out.println(current.label);
    }
    howMany--;
  }
  }
  public void goRight(int howMany){
  while(howMany > 0){
    if(current.right != null){
      current = current.right;
      //System.out.println(current.label);
    }
    howMany--;
  }
  }
  public void goTopRight(int howMany){
  while(howMany > 0){
    if(current.topRight != null){
      current = current.topRight;
      //System.out.println(current.label);
    }
    howMany--;
  }
  }
}


//    this.left = frequency /fifth;
//    this.topLeft = frequency /minThird;
//    this.topRight = frequency * (minThird);
//    this.right = frequency * (fifth);
//    this.bottomRight = /*frequency * (majThird)*/frequency /majThird;
//    this.bottomLeft = /*frequency /majThird*/frequency * (majThird);