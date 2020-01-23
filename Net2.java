package sample;

public class Net2
{
  int radius;
  public double[][] frequencies;
  public String[][] names;
  private boolean equalTemperament;
  private double etSemitone = 1.059463;
  private double minThird = Math.pow(etSemitone, 3);
  private double majThird = Math.pow(etSemitone, 4);
  private double fifth = Math.pow(etSemitone, 7);
  //Builds a Tonal Network Representation of frequencies in an array
  //based upon a chosen center frequency, a chosen radius for the total
  //network and the chosen tonal system, equal temperament or just intonation
  public Net2(double centerF, String name, int radius, boolean eT)
  {
    this.radius = radius;
    frequencies = new double[radius*2+1][radius*2+1];
    names = new String[radius*2+1][radius*2+1];
    frequencies[radius][radius] = centerF;
    names[radius][radius] = name;
    if(eT){
      initializeET();
    }
    else
    {
      initializeJI();
    }
  }
  //Calculate frequencies by multiplying by the 12th root of 2
  public void initializeET(){
    for (int j = radius -1; j > -1; j--)
    {
      frequencies[radius][j] = frequencies[radius][j+1]/fifth;
      names[radius][j] = getLabel(names[radius][j+1], -7);
    }
    for (int j = radius +1; j < frequencies.length; j++)
    {
      frequencies[radius][j] = frequencies[radius][j-1]*fifth;
      names[radius][j] = getLabel(names[radius][j-1], 7);
    }
    for (int i = radius -1; i > -1; i--)
    {
      for (int j = 0; j < frequencies.length; j++)
      {
        frequencies[i][j] = frequencies[i+1][j]/minThird;
        names[i][j] = getLabel(names[i+1][j], -3);
      }
    }
    for (int i = radius +1; i < frequencies.length; i++)
    {
      for (int j = 0; j < frequencies.length; j++)
      {
        frequencies[i][j] = frequencies[i-1][j]*minThird;
        names[i][j] = getLabel(names[i-1][j], 3);
      }
    }
  }
  //Calculate frequencies based off off ratios of whole numbers off a chosen
  //center frequency (tonic)
  public void initializeJI(){
    for (int j = radius - 1; j > -1; j--)
    {
      frequencies[radius][j] = (frequencies[radius][j+1]*2)/3;
      names[radius][j] = getLabel(names[radius][j+1], -7);
    }
    for (int j = radius +1; j < frequencies.length; j++)
    {
      frequencies[radius][j] = (frequencies[radius][j-1]*3)/2;
      names[radius][j] = getLabel(names[radius][j-1], 7);
    }
    for (int i = radius -1; i > -1; i--)
    {
      for (int j = 0; j < frequencies.length; j++)
      {
        frequencies[i][j] = (frequencies[i+1][j]*5)/6;
        names[i][j] = getLabel(names[i+1][j], -3);
      }
    }
    for (int i = radius +1; i < frequencies.length; i++)
    {
      for (int j = 0; j < frequencies.length; j++)
      {
        frequencies[i][j] = (frequencies[i-1][j]*6)/5;
        names[i][j] = getLabel(names[i-1][j], 3);
      }
    }
  }
  //Assigning note names to each frequency
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


}
