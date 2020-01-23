package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
/*
Support
 */

public class ToneMap extends Application implements EventHandler<ActionEvent>
{
    public static final int CHANNELS = 2;
    public static final int SAMPLE_RATE = 48000;
    public static final int NUM_PRODUCERS = 3;
    public static final int BUFFER_SIZE_FRAMES = 4800;
    private static int radius = 10;
    private Button Hexagon;
    private Button startButton;
    private Button endButton;
    private Button leftButton;
    private Button rightButton;
    private Button upButton;
    private Button downButton;
    private Button eTButton;
    private Button freqButton;
    static AudioConsumer consumer;
    int iterator = 0;
    AudioProducer ap1;
    AudioProducer ap2;
    AudioProducer ap3;
    Net2 net;
    Pair sideTop;
    Pair sideBottom;
    Pair left;
    Pair right;
    boolean eT = true;
    boolean up = true;
    boolean text = true;
    Image mapDown = new Image("sample/MapDown.png",
            500, 500, false, false);
    Image mapUp = new Image("sample/MapUp.png",
            500, 500, false, false);
    ImageView imageView = new ImageView(mapUp);
    GridPane gpButtons = new GridPane();
    GridPane gpText = new GridPane();
    Text t1 = new Text("4039");
    Text t2 = new Text("4039");
    Text t3 = new Text("   4039");
    Text t4 = new Text("4039");
    Text t5 = new Text(" 4039");
    Text t6 = new Text("  4039");
    Text t7 = new Text("  4039");
    Text t8 = new Text("4039");
    Text t9 = new Text("   4039");
    Text t10 = new Text("4039");
    Text t11 = new Text(" 4039");
    Text t12 = new Text("  4039");

    //static Net2 Test = new Net2(440, "A", radius, eT);
    StackPane sp = new StackPane();
    @Override
    //The start method implements display of all that is seen on screen
    //Buttons and their associated actions are set and placement
    //of images and GUI components are initialized
    public void start(Stage primaryStage) throws Exception
    {
        net = new Net2(440, "A", radius, eT);
        left = new Pair(1,1);
        sideBottom = new Pair(1,2);
        sideTop = new Pair(2,2);
        right = new Pair(2,2);
        VBox vbox = new VBox(10);
        startButton = new Button("Start Tone");
        startButton.setOnAction(this);
        Hexagon = new Button("Hexagon");
        Hexagon.setOnAction(this);
        endButton = new Button("End Tone  ");
        endButton.setOnAction(this);
        rightButton = new Button("Right");
        rightButton.setOnAction(this);
        leftButton = new Button("Left");
        leftButton.setOnAction(this);
        upButton = new Button("   Up   ");
        upButton.setOnAction(this);
        downButton = new Button(" Down ");
        downButton.setOnAction(this);
        eTButton = new Button("Just Intonation");
        eTButton.setOnAction(this);
        freqButton = new Button("Frequency (Hz)");
        freqButton.setOnAction(this);

        right.a = net.radius;
        right.b = net.radius;

        gpButtons.add(startButton, 0,0);
        gpButtons.add(endButton, 0,1);
        gpButtons.add(Hexagon, 3,0);
        gpButtons.add(rightButton, 3,1);
        gpButtons.add(leftButton, 2,1);
        gpButtons.add(upButton, 2,0);
        gpButtons.add(downButton, 2,2);
        gpButtons.add(eTButton, 1,0);
        gpButtons.add(freqButton, 1,1);


        t1.setFont(Font.font(11));
        t2.setFont(Font.font(11));
        t3.setFont(Font.font(11));
        t4.setFont(Font.font(11));
        t5.setFont(Font.font(11));
        t6.setFont(Font.font(11));
        t7.setFont(Font.font(11));
        t8.setFont(Font.font(11));
        t9.setFont(Font.font(11));
        t10.setFont(Font.font(11));
        t11.setFont(Font.font(11));
        t12.setFont(Font.font(11));



        //drawDown(radius, radius);

        sp.getChildren().addAll(imageView,gpText);

        vbox.getChildren().addAll(sp, gpButtons);
        primaryStage.setResizable(false);
        primaryStage.setTitle("ToneMap");
        primaryStage.setScene(new Scene(vbox, 500, 595));
        primaryStage.show();
    }
    //provides spacing so that note and frequency labels appear in nodes
    public String spacer(double freq){
        Integer frequ = (int)freq;
        String spaced = frequ.toString();
        if(spaced.length() == 1){
            spaced = spaced + "      ";
        }
        if(spaced.length() == 2){
            spaced = spaced + "    ";
        }
        if(spaced.length() == 3){
            spaced = spaced + "  ";
        }
        return spaced;
    }
    public String spacer2(String spaced){
        if(spaced.length() == 1){
            spaced = spaced + "      ";
        }
        if(spaced.length() == 2){
            spaced = spaced + "    ";
        }
        if(spaced.length() == 3){
            spaced = spaced + "  ";
        }
        return spaced;
    }
    //Draws the orientation of the network for a major triad
    public void drawUp(int i, int j){
        imageView.setImage(mapUp);
        gpText.getChildren().removeAll(t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12);
        if(!text)
        {
            t1.setText("    " + spacer(net.frequencies[i - 1][j]));
            t2.setText(" " +spacer(net.frequencies[i - 1][j + 1]));
            t3.setText("    " + spacer(net.frequencies[i][j - 1]));
            t4.setText("   " + spacer(net.frequencies[i][j]));
            t5.setText("   " + spacer(net.frequencies[i][j + 1]));
            t6.setText("        " + spacer(net.frequencies[i + 1][j - 2]));
            t7.setText("   " + spacer(net.frequencies[i + 1][j - 1]));
            t8.setText(spacer(net.frequencies[i + 1][j]));
            t9.setText("   " + spacer(net.frequencies[i + 1][j + 1]));
            t10.setText("    " + spacer(net.frequencies[i + 2][j - 2]));
            t11.setText(" " + spacer(net.frequencies[i + 2][j - 1]));
            t12.setText("    " + spacer(net.frequencies[i + 2][j]));
        }
        else{
            t1.setText("    " + spacer2(net.names[i - 1][j]));
            t2.setText(spacer2(net.names[i - 1][j + 1]));
            t3.setText("    " + spacer2(net.names[i][j - 1]));
            t4.setText("   " + spacer2(net.names[i][j]));
            t5.setText("   " + spacer2(net.names[i][j + 1]));
            t6.setText("        " + spacer2(net.names[i + 1][j - 2]));
            t7.setText("   " + spacer2(net.names[i + 1][j - 1]));
            t8.setText(spacer2(net.names[i + 1][j]));
            t9.setText("   " + spacer2(net.names[i + 1][j + 1]));
            t10.setText("    " + spacer2(net.names[i + 2][j - 2]));
            t11.setText(" " + spacer2(net.names[i + 2][j - 1]));
            t12.setText("    " + spacer2(net.names[i + 2][j]));
        }
//        t1.setText(" 4039");
//        t2.setText("4039");
//        t3.setText(" 4039");
//        t4.setText(" 4039");
//        t5.setText("   4039");
//        t6.setText("     4039");
//        t7.setText(" 4039");
//        t8.setText("4039");
//        t9.setText("   4039");
//        t10.setText(" 4039");
//        t11.setText(" 4039");
//        t12.setText("    4039");
        t1.setFont(Font.font(11));
        t2.setFont(Font.font(11));
        t3.setFont(Font.font(11));
        t4.setFont(Font.font(11));
        t5.setFont(Font.font(11));
        t6.setFont(Font.font(11));
        t7.setFont(Font.font(11));
        t8.setFont(Font.font(11));
        t9.setFont(Font.font(11));
        t10.setFont(Font.font(11));
        t11.setFont(Font.font(11));
        t12.setFont(Font.font(11));
        gpText.setVgap(6);
        gpText.setHgap(8);
        gpText.setPadding(new Insets(30,0,0,0));
        gpText.add(t6, 0 ,40);
        gpText.add(t10, 5 ,60);
        gpText.add(t7, 10 ,40);
        gpText.add(t11, 15 ,60);
        gpText.add(t8, 21 ,40);
        gpText.add(t12, 25 ,60);
        gpText.add(t9, 29 ,40);
        gpText.add(t3, 5 ,20);
        gpText.add(t1, 10 ,0);
        gpText.add(t4, 15 ,20);
        gpText.add(t2, 21 ,0);
        gpText.add(t5, 25 ,20);
    }
    //Draws the orientation of the network for a minor triad
    public void drawDown(int i, int j){
        gpText.getChildren().removeAll(t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12);
        imageView.setImage(mapDown);
        if(!text)
        {
            t1.setText("     "+spacer(net.frequencies[i - 1][j - 1]));//4
            t2.setText(spacer(net.frequencies[i - 2][j]));//1
            t3.setText("     " + spacer(net.frequencies[i - 1][j]));//5
            t4.setText(spacer(net.frequencies[i - 2][j + 1]));//2
            t5.setText(" " + spacer(net.frequencies[i - 1][j + 1]));//6
            t6.setText("    " + spacer(net.frequencies[i - 2][j + 2]));//3
            t7.setText("  " + spacer(net.frequencies[i - 1][j + 2]));//7
            t8.setText(spacer(net.frequencies[i][j - 1]));//8
            t9.setText("   " + spacer(net.frequencies[i + 1][j - 1]));//11
            t10.setText("  " +spacer(net.frequencies[i][j]));//9
            t11.setText(" " + spacer(net.frequencies[i + 1][j]));//12
            t12.setText("  " + spacer(net.frequencies[i][j + 1]));//10
        }
        else{
            t1.setText("     "+spacer2(net.names[i - 1][j - 1]));//4
            t2.setText(spacer2(net.names[i - 2][j]));//1
            t3.setText("     " + spacer2(net.names[i - 1][j]));//5
            t4.setText(spacer2(net.names[i - 2][j + 1]));//2
            t5.setText(" " + spacer2(net.names[i - 1][j + 1]));//6
            t6.setText("    " + spacer2(net.names[i - 2][j + 2]));//3
            t7.setText("  " + spacer2(net.names[i - 1][j + 2]));//7
            t8.setText(spacer2(net.names[i][j - 1]));//8
            t9.setText("   " + spacer2(net.names[i + 1][j - 1]));//11
            t10.setText("  " +spacer2(net.names[i][j]));//9
            t11.setText(" " + spacer2(net.names[i + 1][j]));//12
            t12.setText("  " + spacer2(net.names[i][j + 1]));//10
        }
//         t1.setText("4039");
//         t2.setText("4039");
//         t3.setText("   4039");
//         t4.setText("4039");
//         t5.setText(" 4039");
//         t6.setText("  4039");
//         t7.setText("  4039");
//         t8.setText("4039");
//         t9.setText("   4039");
//         t10.setText("4039");
//         t11.setText(" 4039");
//         t12.setText("  4039");
        t1.setFont(Font.font(11));
        t2.setFont(Font.font(11));
        t3.setFont(Font.font(11));
        t4.setFont(Font.font(11));
        t5.setFont(Font.font(11));
        t6.setFont(Font.font(11));
        t7.setFont(Font.font(11));
        t8.setFont(Font.font(11));
        t9.setFont(Font.font(11));
        t10.setFont(Font.font(11));
        t11.setFont(Font.font(11));
        t12.setFont(Font.font(11));
        gpText.setVgap(6);
        gpText.setHgap(8);
        gpText.setPadding(new Insets(48,0,0,0));
        gpText.add(t1, 4 ,20);
        gpText.add(t2, 9 ,0);
        gpText.add(t3, 13 ,20);
        gpText.add(t4, 18 ,0);
        gpText.add(t5, 23 ,20);
        gpText.add(t6, 27 ,0);
        gpText.add(t7, 31 ,20);
        gpText.add(t8, 9 ,41);
        gpText.add(t9, 13 ,61);
        gpText.add(t10, 18 ,41);
        gpText.add(t11, 23 ,61);
        gpText.add(t12, 27 ,41);
    }
    //Handles actions for starting and ending the tones and for playing the chosen triad
    //or changing the notes for a traversal as well as updating the images and labels for the
    //new orientation
    //Audio Producers are generally stopped and started for a new chord
    @Override
    public void handle(ActionEvent event)
    {
        System.out.println(iterator);
        if(event.getSource() == Hexagon) {
            ActionEvent r1 = event.copyFor(rightButton,rightButton);
            handle(r1);
            ActionEvent u1 = event.copyFor(upButton,upButton);
            handle(u1);
            ActionEvent l1 = event.copyFor(leftButton,leftButton);
            handle(l1);
            handle(l1);
            ActionEvent d1 = event.copyFor(downButton,downButton);
            handle(d1);
            handle(r1);
        }
        if(event.getSource() == startButton)
        {
//            System.out.println(net.current.frequency);
//            net.goLeft(1);
//            System.out.println(net.current.frequency);
//            net.goRight(1);
//            net.goRight(1);
//            System.out.println(net.current.frequency);
//            net.goLeft(1);
//            net.goTopLeft(1);
//            System.out.println(net.current.frequency);
//            net.goBottomRight(1);
//            net.goTopRight(1);
//            System.out.println(net.current.frequency);
//            net.goBottomLeft(1);
//            net.goBottomLeft(1);
//            System.out.println(net.current.frequency);
//            net.goTopRight(1);
//            net.goBottomRight(1);
//            System.out.println(net.current.frequency);
//            net.goTopLeft(1);
//            System.out.println(net.current.frequency);
//            net.goTopLeft(1);
//            System.out.println(net.current.frequency);
            drawUp(radius, radius);
            up = true;
            if (iterator == 0)
            {
                left.a = net.radius+1;
                left.b = net.radius-1;
                sideBottom.a = net.radius+1;
                sideBottom.b = net.radius;
                sideTop.a = net.radius;
                sideTop.b = net.radius;
                System.out.println(net.frequencies[left.getA()][left.getB()]);
                System.out.println(net.frequencies[sideBottom.getA()][sideBottom.getB()]);
                System.out.println(net.frequencies[sideTop.getA()][sideTop.getB()]);
                ap1 = new AudioProducer(consumer,/*net.current.frequency660 422.4 */net.frequencies[left.getA()][left.getB()]);
                ap1.start();
                ap2 = new AudioProducer(consumer,/*net.current.right.frequency 550 */net.frequencies[sideBottom.getA()][sideBottom.getB()]);
                ap2.start();
                ap3 = new AudioProducer(consumer,/*net.current.topRight.frequency 825*/ net.frequencies[sideTop.getA()][sideTop.getB()]);
                ap3.start();
            }
//            if (iterator == 1)
//            {
//                ap1.stop();
////                ap2.stop();
////                ap3.stop();
//                ap1 = new AudioProducer(consumer, /*net.current.frequency*/412.5);
//
//                ap1.start();
////                ap2 = new AudioProducer(consumer, /*net.current.right.frequency*/554.36);
////                ap2.start();
////                ap3 = new AudioProducer(consumer, /*net.current.bottomRight.frequency*/830.6085814151652);
////                ap3.start();
//            }
//            if (iterator == 2)
//            {
//                ap1.stop();
//                ap2.stop();
//                ap3.stop();
//                ap1 = new AudioProducer(consumer, net.current.frequency);
//                ap1.start();
//                ap2 = new AudioProducer(consumer, net.current.bottomLeft.frequency);
//                ap2.start();
//                ap3 = new AudioProducer(consumer, net.current.bottomRight.frequency);
//                ap3.start();
//
//            }
//            if (iterator == 3)
//            {
//                ap1.stop();
//                ap2.stop();
//                ap3.stop();
//                ap1 = new AudioProducer(consumer, net.current.frequency);
//                ap1.start();
//                ap2 = new AudioProducer(consumer, net.current.bottomLeft.frequency);
//                ap2.start();
//                ap3 = new AudioProducer(consumer, net.current.left.frequency);
//                ap3.start();
//
//            }
//            if (iterator == 4)
//            {
//                ap1.stop();
//                ap2.stop();
//                ap3.stop();
//                ap1 = new AudioProducer(consumer, net.current.frequency);
//                ap1.start();
//                ap2 = new AudioProducer(consumer, net.current.topLeft.frequency);
//                ap2.start();
//                ap3 = new AudioProducer(consumer, net.current.left.frequency);
//                ap3.start();
//
//            }
//            if (iterator == 5)
//            {
//                ap1.stop();
//                ap2.stop();
//                ap3.stop();
//                ap1 = new AudioProducer(consumer, net.current.frequency);
//                ap1.start();
//                ap2 = new AudioProducer(consumer, net.current.topLeft.frequency);
//                ap2.start();
//                ap3 = new AudioProducer(consumer, net.current.topRight.frequency);
//                ap3.start();
//
//            }
            iterator++;
        }
        if(event.getSource() == endButton)
        {
            iterator = 0;
            ap1.stop();
            ap2.stop();
            ap3.stop();
        }
        //for (int i = 0; i < NUM_PRODUCERS; i++)


        //System.console().readLine();
        //consumer.stop();
        if(event.getSource() == rightButton)
        {
            ap1.stop();
            ap2.stop();
            ap3.stop();
            if(iterator%2 == 0){

                left.a = sideBottom.getA();
                left.b = sideBottom.getB();
                sideBottom.a = sideTop.getA()+1;
                sideBottom.b = sideTop.getB()+1;
                sideTop.a = right.getA();
                sideTop.b = right.getB();
                right.a = -1000;
                right.b = -1000;
                drawUp(sideTop.getA(),sideTop.getB());
                up = true;
                ap1 = new AudioProducer(consumer, net.frequencies[left.getA()][left.getB()]);
                ap1.start();
                System.out.println(net.frequencies[left.getA()][left.getB()]);
                System.out.println(net.frequencies[sideBottom.getA()][sideBottom.getB()]);
                System.out.println(net.frequencies[sideTop.getA()][sideTop.getB()]);
            }
            else{
                right.a = (left.getA())-1;
                right.b = (left.getB())+2;
                left.a = -1000;
                left.b = -1000;
                drawDown(sideBottom.getA(),sideBottom.getB());
                up = false;
                ap1 = new AudioProducer(consumer, net.frequencies[right.getA()][right.getB()]);
                ap1.start();
                System.out.println(net.frequencies[right.getA()][right.getB()]);
                System.out.println(net.frequencies[sideBottom.getA()][sideBottom.getB()]);
                System.out.println(net.frequencies[sideTop.getA()][sideTop.getB()]);
            }
            ap2 = new AudioProducer(consumer, net.frequencies[sideBottom.getA()][sideBottom.getB()]);
            ap2.start();
            ap3 = new AudioProducer(consumer, net.frequencies[sideTop.getA()][sideTop.getB()]);
            ap3.start();
            iterator++;

        }
        if(event.getSource() == leftButton)
        {
            ap1.stop();
            ap2.stop();
            ap3.stop();
            if(iterator%2 == 0){
                left.a = right.getA()+1;
                left.b = right.getB()-2;
                right.a = -1000;
                right.b = -1000;
                drawUp(sideTop.getA(),sideTop.getB());
                up = true;
                ap1 = new AudioProducer(consumer, net.frequencies[left.getA()][left.getB()]);
                ap1.start();
            }
            else{
                right.a = sideTop.getA();
                right.b = sideTop.getB();
                sideTop.a = sideBottom.getA()-1;
                sideTop.b = sideBottom.getB()-1;
                sideBottom.a = left.getA();
                sideBottom.b = left.getB();
                drawDown(sideBottom.getA(),sideBottom.getB());
                up = false;
                left.a = -1000;
                left.b = -1000;
                ap1 = new AudioProducer(consumer, net.frequencies[right.getA()][right.getB()]);
                ap1.start();
            }
            ap2 = new AudioProducer(consumer, net.frequencies[sideBottom.getA()][sideBottom.getB()]);
            ap2.start();
            ap3 = new AudioProducer(consumer, net.frequencies[sideTop.getA()][sideTop.getB()]);
            ap3.start();
            iterator++;

        }
        if(event.getSource() == downButton)
        {
            if(iterator%2 == 0){

            }
            else{
                ap1.stop();
                ap2.stop();
                ap3.stop();
                right.a = sideBottom.getA();
                right.b = sideBottom.getB();
                sideBottom.a = sideTop.getA()+2;
                sideBottom.b = sideTop.getB()-1;
                sideTop.a = left.getA();
                sideTop.b = left.getB();
                left.a = -1000;
                left.b = -1000;
                System.out.println(net.frequencies[right.getA()][right.getB()]);
                System.out.println(net.frequencies[sideBottom.getA()][sideBottom.getB()]);
                System.out.println(net.frequencies[sideTop.getA()][sideTop.getB()]);
                drawDown(sideBottom.getA(),sideBottom.getB());
                up = false;
                ap1 = new AudioProducer(consumer, net.frequencies[right.getA()][right.getB()]);
                ap1.start();
                ap2 = new AudioProducer(consumer, net.frequencies[sideBottom.getA()][sideBottom.getB()]);
                ap2.start();
                ap3 = new AudioProducer(consumer, net.frequencies[sideTop.getA()][sideTop.getB()]);
                ap3.start();
                iterator++;
            }
        }
        if(event.getSource() == upButton)
        {
            if(iterator%2 == 0){
                ap1.stop();
                ap2.stop();
                ap3.stop();
                left.a = sideTop.getA();
                left.b = sideTop.getB();
                sideTop.a = sideBottom.getA()-2;
                sideTop.b = sideBottom.getB()+1;
                sideBottom.a = right.getA();
                sideBottom.b = right.getB();
                right.a = -1000;
                right.b = -1000;
                drawUp(sideTop.getA(),sideTop.getB());
                up = true;
                ap1 = new AudioProducer(consumer, net.frequencies[left.getA()][left.getB()]);
                ap1.start();
                ap2 = new AudioProducer(consumer, net.frequencies[sideBottom.getA()][sideBottom.getB()]);
                ap2.start();
                ap3 = new AudioProducer(consumer, net.frequencies[sideTop.getA()][sideTop.getB()]);
                ap3.start();
                iterator++;
            }
        }
        if(event.getSource() == eTButton)
        {
            if(eT == false){
                eT = true;
                eTButton.setText("Just Intonation");
            }
            else{
                eT = false;
                eTButton.setText("Equal Temperament");
            }
            //iterator = 0;
            ap1.stop();
            ap2.stop();
            ap3.stop();
            net = new Net2(440, "A", radius, eT);
            if(up){
                drawUp(sideTop.getA(),sideTop.getB());
            }
            else{
                drawDown(sideBottom.getA(),sideBottom.getB());
            }
            ap1 = new AudioProducer(consumer,/*net.current.frequency660 422.4 */net.frequencies[left.getA()][left.getB()]);
            ap1.start();
            ap2 = new AudioProducer(consumer,/*net.current.right.frequency 550 */net.frequencies[sideBottom.getA()][sideBottom.getB()]);
            ap2.start();
            ap3 = new AudioProducer(consumer,/*net.current.topRight.frequency 825*/ net.frequencies[sideTop.getA()][sideTop.getB()]);
            ap3.start();
        }
        if(event.getSource() == freqButton)
        {
            if (text == false)
            {
                text = true;
                freqButton.setText("Frequency (Hz)");

            } else
            {
                text = false;
                freqButton.setText("Note Names");
            }
            if(up){
                drawUp(sideTop.getA(),sideTop.getB());
            }
            else{
                drawDown(sideBottom.getA(),sideBottom.getB());
            }
        }
    }

    // generates some random sine wave
    public static class ToneGenerator {

        private static final double[] NOTES = {261.63, 311.13, 392.00};
        private static final double[] OCTAVES = {/*1.0,*/ 1.0/*, 4.0, 8.0*/};
        private static final double[] LENGTHS = {/*0.05, 0.25,*/ 100.0/*, 2.5, 5.0*/};

        private double phase = 0;
        private int framesProcessed;
        private final double length;
        private final double frequency;

        public ToneGenerator(double note) {
            ThreadLocalRandom rand = ThreadLocalRandom.current();
            length = LENGTHS[rand.nextInt(LENGTHS.length)];
            frequency = /*NOTES[rand.nextInt(NOTES.length)]*/note * OCTAVES[rand.nextInt(OCTAVES.length)];
        }

        // make sound
        public void fill(short[] block) {
            for (int f = 0; f < block.length / CHANNELS; f++) {
                double sample = Math.sin(phase * 2.0 * Math.PI);
                for (int c = 0; c < CHANNELS; c++)
                    block[f * CHANNELS + c] = (short) (sample * Short.MAX_VALUE);
                phase += frequency / SAMPLE_RATE;
            }
            framesProcessed += block.length / CHANNELS;
        }

        // true if length of tone has been generated
        public boolean done() {
            return framesProcessed >= length * SAMPLE_RATE;
        }
    }

    // dummy audio producer, based on sinewave generator
// above but could also be incoming network packets
    public static class AudioProducer {

        final Thread thread;
        final AudioConsumer consumer;
        final short[] buffer = new short[BUFFER_SIZE_FRAMES * CHANNELS];
        double note;
        boolean done = true;

        public AudioProducer(AudioConsumer consumer, double note) {
            System.out.println("Note:"+note);
            this.consumer = consumer;
            this.note = note;
            thread = new Thread(() -> run());
            thread.setDaemon(true);
        }

        public void start() {
            try
            {
                thread.sleep(70);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            thread.start();
        }

        public void stop(){
            thread.interrupt();}

        // repeatedly play random sine and sleep for some time
        void run() {
            try {
                ThreadLocalRandom rand = ThreadLocalRandom.current();
                while (true) {
                    long pos = consumer.position();
                    ToneGenerator g = new ToneGenerator(note);

                    // if we schedule at current buffer position, first part of the tone will be
                    // missed so have tone start somewhere in the middle of the next buffer
                    pos += BUFFER_SIZE_FRAMES + rand.nextInt(BUFFER_SIZE_FRAMES);
                    while (!g.done()&&done) {
                        g.fill(buffer);
                        consumer.mix(pos, buffer);
                        pos += BUFFER_SIZE_FRAMES;

                        // we can generate audio faster than it's played
                        // sleep a while to compensate - this more closely
                        // corresponds to playing audio coming in over the network
                        double bufferLengthMillis = BUFFER_SIZE_FRAMES * 1000.0 / SAMPLE_RATE;
                        Thread.sleep((int) (bufferLengthMillis * 0.9));
                    }
                    // sleep a while in between tones
                    //Thread.sleep(1000 /*+ rand.nextInt(2000)*/);
                }
            } catch (Throwable t) {
                System.out.println(t.getMessage());
                t.printStackTrace();
            }
        }
    }

    // audio consumer - plays continuously on a background
// thread, allows audio to be mixed in from arbitrary threads
    public static class AudioConsumer {

        // audio block with "when to play" tag
        private static class QueuedBlock {

            final long when;
            final short[] data;

            public QueuedBlock(long when, short[] data) {
                this.when = when;
                this.data = data;
            }
        }

        // need not normally be so low but in this example
        // we're mixing down a bunch of full scale sinewaves
        private static final double MIXDOWN_VOLUME = 1.0 / NUM_PRODUCERS;

        private final List<QueuedBlock> finished = new ArrayList<>();
        private final short[] mixBuffer = new short[BUFFER_SIZE_FRAMES * CHANNELS];
        private final byte[] audioBuffer = new byte[BUFFER_SIZE_FRAMES * CHANNELS * 2];

        private final Thread thread;
        private final AtomicLong position = new AtomicLong();
        private final AtomicBoolean running = new AtomicBoolean(true);
        private final ConcurrentLinkedQueue<QueuedBlock> scheduledBlocks = new ConcurrentLinkedQueue<>();


        public AudioConsumer() {
            thread = new Thread(() -> run());
        }

        public void start() {
            thread.start();
        }

        public void stop() {
            running.set(false);
        }

        // gets the play cursor. note - this is not accurate and
        // must only be used to schedule blocks relative to other blocks
        // (e.g., for splitting up continuous sounds into multiple blocks)
        public long position() {
            return position.get();
        }

        // put copy of audio block into queue so we don't
        // have to worry about caller messing with it afterwards
        public void mix(long when, short[] block) {
            scheduledBlocks.add(new QueuedBlock(when, Arrays.copyOf(block, block.length)));
        }

        // better hope mixer 0, line 0 is output
        private void run() {
            Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
            try (Mixer mixer = AudioSystem.getMixer(mixerInfo[0])) {
                Line.Info[] lineInfo = mixer.getSourceLineInfo();
                try (SourceDataLine line = (SourceDataLine) mixer.getLine(lineInfo[0])) {
                    line.open(new AudioFormat(SAMPLE_RATE, 16, CHANNELS, true, false), BUFFER_SIZE_FRAMES);
                    line.start();
                    while (running.get())
                        processSingleBuffer(line);
                    line.stop();
                }
            } catch (Throwable t) {
                System.out.println(t.getMessage());
                t.printStackTrace();
            }
        }

        // mix down single buffer and offer to the audio device
        private void processSingleBuffer(SourceDataLine line) {

            Arrays.fill(mixBuffer, (short) 0);
            long bufferStartAt = position.get();

            // mixdown audio blocks
            for (QueuedBlock block : scheduledBlocks) {

                int blockFrames = block.data.length / CHANNELS;

                // block fully played - mark for deletion
                if (block.when + blockFrames <= bufferStartAt) {
                    finished.add(block);
                    continue;
                }

                // block starts after end of current buffer
                if (bufferStartAt + BUFFER_SIZE_FRAMES <= block.when)
                    continue;

                // mix in part of the block which overlaps current buffer
                // note that block may have already started in the past
                // but extends into the current buffer, or that it starts
                // in the future but before the end of the current buffer
                int blockOffset = Math.max(0, (int) (bufferStartAt - block.when));
                int blockMaxFrames = blockFrames - blockOffset;
                int bufferOffset = Math.max(0, (int) (block.when - bufferStartAt));
                int bufferMaxFrames = BUFFER_SIZE_FRAMES - bufferOffset;
                for (int f = 0; f < blockMaxFrames && f < bufferMaxFrames; f++)
                    for (int c = 0; c < CHANNELS; c++) {
                        int bufferIndex = (bufferOffset + f) * CHANNELS + c;
                        int blockIndex = (blockOffset + f) * CHANNELS + c;
                        mixBuffer[bufferIndex] += (short) (block.data[blockIndex] * MIXDOWN_VOLUME);
                    }
            }

            scheduledBlocks.removeAll(finished);
            finished.clear();
            ByteBuffer.wrap(audioBuffer).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(mixBuffer);
            line.write(audioBuffer, 0, audioBuffer.length);
            position.addAndGet(BUFFER_SIZE_FRAMES);
        }
    }
    //starts a new audio consumer and launches the GUI
    public static void main(String[] args) {
        System.out.print("Press return to exit...");
//        for (int i = 0; i < Test.frequencies.length; i++)
//        {
//            System.out.println("");
//            for (int j = 0; j < Test.frequencies.length; j++)
//            {
//                System.out.print(" "+Test.names[i][j]+" "+(int)Test.frequencies[i][j]);
//            }
//        }
        consumer = new AudioConsumer();
        consumer.start();
        launch(args);
        //System.console().readLine();
        consumer.stop();


    }
}

