import java.util.ArrayList;

public class Main {
    
    public static void main(String[] args) {
    	
        RocketGame game = new RocketGame();
        while (!StdDraw.isMousePressed());
        game.start();
        
    }
    
}