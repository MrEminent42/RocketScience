import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
public class Game extends JPanel implements MouseListener, KeyListener{
    public static Rocke rocket;
    private static Timer timer;
    private static int score;
    private static int time;
    private static double miliSec;
    private static double fuel = 1000;
    public static boolean paused = true;
    private static int other;
    private static AudioInputStream audioInputStream;
    private static Clip clip;
    public Game(){
        rocket = new Rocke(200,0,10);
        setVisible(true);
        setBackground(Color.BLACK);
        setFocusable(true);
        addMouseListener(this);
        addKeyListener(this);
        setPreferredSize(new Dimension(200,1000));
        try{
            clip = AudioSystem.getClip();
        }catch(Exception e){}
        timer = new Timer(1, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    if(!paused){
                        miliSec++;
                        time = (int)(miliSec/1000);
                        rocket.calculate();
                        check();
                        repaint();
                    }
                }
            });
        timer.start();
    }

    public void check(){
        if(rocket.touchingGround() && !paused){
            paused = true;
            if(rocket.vel < 10){
                stop();
                play("./sound/rocket_landing.wav",false);
                other = 5;
            }else{
                stop();
                play("./sound/rocket_crashing.wav",false);
                other = 6;
            }
            rocket = new Rocke(200,0,10);
        }
        if(score>9999)
            other = 1;
        if(fuel<=0)
            other = 2;
        else if (fuel<=300)
            other = 4;
        if(time>3599)
            other = 3;
        fuel -= (10-rocket.acc)/1000;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        if(other == 1){
            other = 0;
            g.drawString("YOU WIN",50,475);
            stop();
            return;
        }
        if(other == 2){
            g.drawString("NO MORE FUEL",50,475);
            g.drawString("GAME OVER",50,525);
            stop();
            return;
        }
        if(other == 3){
            g.drawString("TIMES UP!",400,475);
            g.drawString("GAME OVER",375,525);
            stop();
            return;
        }
        if(other == 5){
            g.drawString("YOU LANDED",50,450);
            g.drawString("THE ROCKET",50,500);
            g.drawString("CLICK TO START",25,550);
        }else if(other == 6){
            g.drawString("YOU DESTROYED",50,450);
            g.drawString("THE ROCKET",50,500);
            g.drawString("CLICK TO START",25,550);
        }else if(paused)
            g.drawString("CLICK TO START",25,475);
        else if(other == 4)
            g.drawString("LOW ON FUEL",50,475);
        drawRocket(g);
        drawGround(g);
        drawText(g);
    }

    public void drawRocket(Graphics g){
        g.drawRect(90,(int)rocket.pos-20,20,40);
    }

    public void drawGround(Graphics g){
        g.drawLine(0,900,200,900);
    }

    public void drawText(Graphics g){
        int number = 0;
        if(score !=0)
            number = 3-(int)Math.log10(score);
        else
            number = 3;
        String s = "SCORE ";
        for(int k = 0; k<number; k++)
            s += "0";
        s+= score;
        g.setFont(new Font("Arial",Font.PLAIN,10));
        g.drawString(s,20,40);
        int minutes = time/60;
        int seconds = time%60;
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
        s+= (int)fuel;
        g.drawString(s,20,100);
        int alt = (int)(880-rocket.pos);
        if(alt ==0)
            number = 3;
        else
            number = 3-(int)Math.log10(alt);
        s = "ALTITUDE ";
        for(int k = 0; k<number; k++)
            s += "0";
        s+= alt;
        g.drawString(s,20,130);
        s = "VELOCITY ";
        s+= (int)(rocket.vel);
        g.drawString(s,20,160);
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Lunar Lander");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Game game = new Game();
        frame.add(game);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    public void play(String fileName, boolean xd){
        try{
            File file = new File(fileName);
            audioInputStream = AudioSystem.getAudioInputStream(new File(fileName).getAbsoluteFile());
            clip.open(audioInputStream);
        }catch(IOException e){
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }
        if(xd)
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    public void stop(){
        clip.stop();
        clip.close();
    }
    
    @Override
    public void mouseClicked(MouseEvent e){
        if(!paused)
            return;
        paused = false;
        if(other == 5 || other == 6)
            other = 0;
    }

    @Override
    public void keyPressed(KeyEvent e){
        if(paused)
            return;
        int action = e.getKeyCode();
        if(action == 40 && rocket.acc<10)
            rocket.acc+= 2.5;
        else if(action == 40 && rocket.acc == 7.5)
            rocket.acc = 10;
        else if(action == 38 && rocket.acc >-10)
            rocket.acc-= 2.5;
        stop();
        if(rocket.acc <= 9)
            play("./sound/CLevel 1.wav",true);
        else if(rocket.acc <= 5)
            play("./sound/CLevel 2.wav",true);
        else if(rocket.acc <=1)
            play("./sound/CLevel 3.wav",true);
        else if(rocket.acc <= -3)
            play("./sound/CLevel 4.wav",true);
        else if(rocket.acc <= -7)
            play("./sound/CLevel 5.wav",true);
    }

    @Override public void mouseEntered(MouseEvent e){}

    @Override public void mouseExited(MouseEvent e){}

    @Override public void mousePressed(MouseEvent e){}

    @Override public void mouseReleased(MouseEvent e){}

    @Override public void keyReleased(KeyEvent e){}

    @Override public void keyTyped(KeyEvent e){}
}
