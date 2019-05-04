public class RocketGame {
    
    public static final float GRAVITY = -1;
    
    //RocketScreen screen;
    Rocket rocket;
    
    public RocketGame() {
        this.rocket = new Rocket();
    }
    
    public void update() {
        rocket.update();
    }
    
}