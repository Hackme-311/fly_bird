import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
    JFrame jFrame = new JFrame("flopy bird");
    //jFrame.setLayout(null);  
    jFrame.setSize(370,620);                           
    jFrame.setResizable(false);
    jFrame.setLocationRelativeTo(null);
    jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //flopy bird class started
    flopybird flopybird = new flopybird();
    jFrame.add(flopybird);
    jFrame.pack();
    flopybird.requestFocus();
    jFrame.setVisible(true);
    }
}
