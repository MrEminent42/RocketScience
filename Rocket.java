public class Rocket {
    
    Vector pos;
    Vector accl;
    Vector vel;
    
    public Rocket() {
        this.pos = new Vector(0,0);
        this.accl = new Vector(0,0);
        this.vel = new Vector(0,0);
    }
    
    public void update() {
        this.accl.add(new Vector(0,RocketGame.GRAVITY));
        
        this.vel.add(accl);
        this.pos.add(vel);
        this.accl = new Vector(0,0);
    }
}