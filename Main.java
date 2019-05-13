package game;
public class Main {
    
    public static void main(String[] args) {
    	
        RocketGame game = new RocketGame();
        // wait until the user clicks their mouse
        while (!StdDraw.isMousePressed());
        game.start();
        
    }
    
}