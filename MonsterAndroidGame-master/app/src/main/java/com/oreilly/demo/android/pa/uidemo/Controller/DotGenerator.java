package com.oreilly.demo.android.pa.uidemo.Controller;

import android.os.AsyncTask;

import com.oreilly.demo.android.pa.uidemo.model.Dots;
import com.oreilly.demo.android.pa.uidemo.view.DotView;

import java.util.concurrent.Semaphore;


/* This will generate the Dot*/


public final class DotGenerator extends AsyncTask<Void, Void, Void> {
    Dots Dots;
    DotView view;
    Alerter alerter;
    boolean start,reset;
    boolean set = true, levelChanged = true;
    DotWork DWork;
    Semaphore lock;


    public DotGenerator(Dots Dots, DotView view, int level) {
        this.Dots = Dots; /* generate Dots*/
        this.view = view; /* generate view*/
        Dots.setLevel(level); /*generate level*/
        alerter = new Alerter(view, Dots );
        DWork = new DotWork(view);
        Dots.setDelay(3000);
        lock = new Semaphore(1);

    }



    @Override


    protected void onProgressUpdate(final Void... params) {

        if (Dots.getLevel() > 5) {
            alerter.gameOverAlert();
            cancel(true);
        }

        if (Dots.countDots() == 0 || levelChanged) {

            /* Creates newDot in case no Dot is found*/

            try {
                DWork.makeDot(Dots, view);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (Dots.getLevel() > 1 && levelChanged && set) {
                alerter.congratsAlert(Dots.getLevel() - 1);
                set = false;
            }
            levelChanged = false;
        } else {
            /*Move the Dots on UI*/
            for (int i = 0; i < Dots.countDots(); i++)
                DWork.moveDot(Dots, view, i);
        }
    }
    /*runs in background of UI*/
    @Override

    protected Void doInBackground(final Void... params) {
        long time;
        time = System.currentTimeMillis();
        while (!isCancelled()) {
           if (start) {

                publishProgress(null);
                DWork.releaseLock();
            /*The level is changed if vulnerable Dots are killed*/
                   if (Dots.getScore() == 10 * 5  *   (Dots.getLevel()*2-1))  {
                    Dots.setLevel(Dots.getLevel() + 1);
                    Dots.setDelay(3000 / Dots.getLevel());
                    set = true;
                    levelChanged = true;
                    Dots.setKills(0);
                   }


            /*delay between next move*/
                try {
                    Thread.sleep(Dots.getDelay());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        return null;
    }

    @Override

    protected void onCancelled() {
        try {
            lock.acquire(1);
            alerter.gameOverAlert();
            Dots.setLevel(1);
            Dots.setScore(0);
            Dots.setTimeLeft(0);
            Dots.clearDots();
            Dots = null;
            cancel(true);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }


        finally {
            lock.release(1);
        }
    }

    public void setStart(boolean s){
        start = s;

    }

   public void setReset(boolean reset){
        this.reset = reset;

    }

    public void reSet(){
        try {
            lock.acquire(1);
            } catch(InterruptedException e) {
            e.printStackTrace();
        }
        Dots.setLevel(1);
        Dots.setScore(0);
        reset = false;
        start = false;
    }

}


