import java.util.ArrayList;
import java.util.Iterator;

public class Rocket {
    
    Vector pos;
    Vector accl;
    Vector vel;
    
    ArrayList<AppliedForce> forces;
    
    public Rocket() {
        this.pos = new Vector(0,0);
        this.accl = new Vector(0,0);
        this.vel = new Vector(0,0);
        
        this.forces = new ArrayList<AppliedForce>();
    }
    
    public void update() {
        // add gravitational force
        this.accl.add(new Vector(0,RocketGame.GRAVITY));
        
        // add applied forces
        for (int i = forces.size() - 1; i <= 0; i--) {
            AppliedForce f = forces.get(i);
            if (f.isFinished()) forces.remove(i);
            else {
                this.accl.add(f.getVector());
            }
        }
        
        this.vel.add(accl);
        this.pos.add(vel);
        this.accl = new Vector(0,0);
    }
    
    public void applyForce(AppliedForce force) {
        forces.add(force);
    }
}