public class Rockett{
    public double a;
    public double x,y,xVel,yVel,xAcc,yAcc;
    public Rockett(){
        a = Math.random()*Math.PI*2;
        x = 100;
        y = 300;
        xVel = 0;
        yVel = 0;
        xAcc = 0;
        yAcc = -1;
    }
}