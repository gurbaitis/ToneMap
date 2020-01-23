package sample;
public class Node
{
  Node left;
  Node topLeft;
  Node topRight;
  Node right;
  Node bottomRight;
  Node bottomLeft;
  double frequency;
  String label;

  public Node(double frequency, String label) {
      this.frequency = frequency;
      this.label = label;
  }

}
