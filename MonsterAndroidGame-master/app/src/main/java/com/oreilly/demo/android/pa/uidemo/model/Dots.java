package com.oreilly.demo.android.pa.uidemo.model;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/*This is going to represent a list of Dots.*/

public class Dots {



    public interface DotsChangeListener {
        void onDotsChange(Dots Dots);
    }

    private int score = 0;
    private int level = 1;
    private int killedCount = 0;
    private boolean gameOver = false;

/*Returns the time left to play the game*/

    public long getTimeLeft() {
        return timeLeft;
    }


    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }

    private long timeLeft = 60;

    private final LinkedList<Dot> Dots = new LinkedList<Dot>();
    private final List<Dot> DotList = Collections.unmodifiableList(Dots);


    private DotsChangeListener DotsChangeListener;


    /*The parameter l set the change listener.*/

    public void setDotsChangeListener(DotsChangeListener l) {

        DotsChangeListener = l;
    }

    /* This returns the last added Dot*/
    public Dot getLastDot() {
        return (Dots.size() <= 0) ? null : Dots.getLast();
    }

    /* This returns the list of Dots.*/

    public List<Dot> getDots() {

        return DotList;
    }

    /*Here x=horizontal coordinate, y=vertical coordinate, diameter, size of Dot*/


    public void addDot(float x, float y, int diameter, boolean state) {
        Dots.add(new Dot(x, y, diameter, state));
        notifyListener();
    }

    /*To remove all Dots.*/


    public void clearDots() {
        Dots.clear();
        score = 0;
        notifyListener();
    }


    /*This returns the size of Dots*/

    public int countDots() {
        return Dots.size();
    }


    /*@Moves the Dots*/
    public void moveDots(Dot Dot, float newX, float newY, int diameter, boolean state) {
        if (null == getDot(newX, newY)) {
            Dots.remove(Dot);
            addDot(newX, newY, diameter, state);
            notifyListener();
        }

    }

    /*This returns the index of the Dot*/
    public Dot getDot(int index) {
        return Dots.get(index);
    }

    public Dot getDot(float x, float y) {
        for (Dot Dot : Dots) {
            if (x >= Dot.getX() && y >= Dot.getY()
                    && x <= Dot.getX() + Dot.getDiameter() && y <= Dot.getY() + Dot.getDiameter())
                return Dot;
        }
        return null;
    }



    /* This returns the score */
    public int getScore() {
        return score;
    }



    /*This returns the level*/
    public int getLevel() {
        return level;
    }


    /*@sets the score.*/
    public void setScore(int score) {
        this.score = score;
    }



    /*@ sets the level*/
    public void setLevel(int level) {
        this.level = level;
    }

    /*This returns the KilledCount*/
    public int getKilledCount() {
        return killedCount;
    }

   /*Determines when the game is over*/
    public void setGameOver() {
        gameOver = true;
    }

    /*This returns game over*/
    public boolean getGameOver() {
        return gameOver;
    }

    /* x=horizontal value of Dot, y=vertical value of Dot*/


    public void killDot(float x, float y) {
        Dot Dot = getDot(x, y);
        if (Dot != null && !Dot.getProtectedInfo()) {
            Dots.remove(Dot);
            setScore(getScore()+10);
            killedCount += 1;
        }
        notifyListener();
    }

    /*kills the Dots*/

    public void setKills(int kills) {
        this.killedCount = kills;
    }

    /*notifies the Listener*/
    private void notifyListener() {
        if (null != DotsChangeListener) {
            DotsChangeListener.onDotsChange(this);
        }
    }



    /*Returns the delay*/
    public long getDelay() {
        return delay;
    }


    public void setDelay(long delay) {
        this.delay = delay;
    }

    long delay;
}
