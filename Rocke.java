public class Rocke{
    public double pos,vel,acc;
    public Rocke(double p, double v, double a){
        pos = p;
        vel = v;
        acc = a;
    }
    public void calculate(){
        vel+=acc/1000;
        pos+=vel/1000;
        if(pos >880){
            pos = 880;
        }
    }
    public boolean touchingGround(){
        return pos == 880;
    }
}