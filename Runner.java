import javax.swing.*;
public class Runner{
    public static RocketGamee game;
    public static JFrame frame;
    public static void main(String[] args){
        frame = new JFrame("Lunar Lander");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game = new RocketGamee();
        frame.add(game);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }
}