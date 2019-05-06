import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
public class RocketGamee extends JPanel implements MouseListener, KeyListener{
    public static Rockett rocket;
    private static Clip clip;
    private static AudioInputStream audioInputStream;
    private static Image image;
    private static int score;
    private static Timer timer;
    private static Timer timer2;
    private static int time;
    private static int fuel = 1000;
    private static boolean paused = true;
    private static boolean landed;
    private static boolean crashed;
    private static boolean acc;
    public RocketGamee(){
        setVisible(true);
        setBackground(Color.BLACK);
        setFocusable(true);
        addMouseListener(this);
        addKeyListener(this);
        setPreferredSize(new Dimension(1000,1000));
        rocket = new Rockett();
        try{
            clip = AudioSystem.getClip();
            /*
             * Uncomment when file name inserted
             * image = Toolkit.getDefaultToolkit().getImage(NAME);
             */
        }catch(Exception e){
            System.out.println("ERROR");
        }
        timer = new Timer(1,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                time++;
            }
        });
        timer2 = new Timer(20,new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                calculate();
                repaint();
            }
        });
        timer2.start();
    }
    
    public void calculate(){
        if(acc)
            fuel--;
        rocket.x++;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        if(score>9999){
            g.setFont(new Font("Arial", Font.PLAIN, 50));
            g.drawString("YOU WIN!",400,475);
            return;
        }
        if(fuel <=0){
            g.setFont(new Font("Arial", Font.PLAIN, 50));
            g.drawString("NO MORE FUEL!",325,475);
            g.drawString("GAME OVER",375,525);
            return;
        }
        Graphics2D graphics = (Graphics2D)g;
        graphics.translate(rocket.x,rocket.y);
        graphics.rotate(30);
        if(image == null)//not needed if image is set
            graphics.drawRect(-15,-15,30,30);
        else
            graphics.drawImage(image,-15,-15,30,30,this);
        graphics.rotate(-30);
        graphics.translate(-rocket.x,-rocket.y);
        for(int x = 0; x<1000; x++)
            g.fillOval(x,850-(int)(25*Math.sin((double)x/15)+75*Math.cos((double)x/100)-35*Math.sin((double)x/25)),5,5);
        int number = 0;
        if(score !=0)
            number = 3-(int)Math.log10(score);
        else
            number = 3;
        String s = "SCORE ";
        for(int k = 0; k<number; k++)
            s += "0";
        s+= score;
        g.setFont(new Font("Arial",Font.PLAIN,25));
        g.drawString(s,20,40);
        int minutes = time/24000;
        int seconds = (time%24000)/400;
        if(minutes>59 && seconds>59){
            super.paintComponent(g);
            g.setFont(new Font("Arial", Font.PLAIN, 50));
            g.drawString("TIMES UP!",400,475);
            g.drawString("GAME OVER",375,525);
            return;
        }
        s = "TIME ";
        if(minutes <=9)
            s+="0";
        s+=minutes + ":";
        if(seconds <=9)
            s+="0";
        s+= seconds;
        g.drawString(s,20,70);
        if(fuel !=0)
            number = 3-(int)Math.log10(fuel);
        else
            number = 3;
        s = "FUEL ";
        for(int k = 0; k<number; k++)
            s += "0";
        s+= fuel;
        g.drawString(s,20,100);
        int alt = (int)(850-rocket.y-150-25*Math.sin((double)rocket.x/15)+75*Math.cos((double)rocket.x/100)-35*Math.sin((double)rocket.x/25));
        if(alt ==0)
            number = 3;
        else
            number = 3-(int)Math.log10(alt);
        s = "ALTITUDE ";
        for(int k = 0; k<number; k++)
            s += "0";
        s+= alt;
        g.drawString(s,750,40);
        if(rocket.xVel == 0)
            number = 3;
        else
            number = 3-(int)Math.log10(rocket.xVel);
        s = "X VELOCITY ";
        for(int k = 0; k<number; k++)
            s += "0";
        s+= rocket.xVel;
        g.drawString(s,750,70);
        if(rocket.yVel == 0)
            number = 3;
        else
            number = 3-(int)Math.log10(rocket.yVel);
        s = "Y VELOCITY ";
        for(int k = 0; k<number; k++)
            s += "0";
        s+= rocket.yVel;
        g.drawString(s,750,100);
    }

    public void play(String fileName){
        try{
            audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
            clip.open(audioInputStream);
        }catch(Exception e){
            System.out.println("ERROR");
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }
    
    public void stop(){
        clip.stop();
        clip.close();
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        if(paused){
            paused = false;
            timer.start();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        if(paused)
            return;
        int action = e.getKeyCode();
        if(action == KeyEvent.VK_LEFT)
            rocket.xAcc =-5;
        else if(action == KeyEvent.VK_RIGHT)
            rocket.xAcc = 5;
        else if(action == KeyEvent.VK_UP)
            rocket.yAcc = 4;
        else if(action == KeyEvent.VK_DOWN)
            rocket.yAcc = -6;
    }
    
    @Override
    public void keyReleased(KeyEvent e){
        if(paused)
            return;
        int action = e.getKeyCode();
        if(action == KeyEvent.VK_LEFT)
            rocket.xAcc = 0;
        else if(action == KeyEvent.VK_RIGHT)
            rocket.xAcc = 0;
        else if(action == KeyEvent.VK_UP)
            rocket.yAcc = -1;
        else if(action == KeyEvent.VK_DOWN)
            rocket.yAcc = -1;
        else
            return;
        acc = true;
    }
    
    @Override public void mouseEntered(MouseEvent e){}
    
    @Override public void mouseExited(MouseEvent e){}
    
    @Override public void mousePressed(MouseEvent e){}
    
    @Override public void mouseReleased(MouseEvent e){}
    
    @Override public void keyTyped(KeyEvent e){}
}