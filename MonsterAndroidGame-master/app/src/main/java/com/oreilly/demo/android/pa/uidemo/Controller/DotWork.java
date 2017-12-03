package com.oreilly.demo.android.pa.uidemo.Controller;


import com.oreilly.demo.android.pa.uidemo.model.Dot;
import com.oreilly.demo.android.pa.uidemo.model.Dots;
import com.oreilly.demo.android.pa.uidemo.view.DotView;

import java.util.Random;
import java.util.concurrent.Semaphore;




public class DotWork {

    private final int MAX;
    private final Semaphore available;

    public DotWork(DotView view) {
        MAX = view.getNumberOfColumns() * view.getNumberOfRows();
        available = new Semaphore(MAX, true);
    }

    public void getLock() throws InterruptedException {
        available.acquire(10);
    }

    public void releaseLock() {
        available.release(10);
    }

    /* Make the Dots on UI
     * @param Dots
     * @param view
     * @throws InterruptedException
     */
    void makeDot(Dots Dots, DotView view) throws InterruptedException {
        Random rand = new Random();
        int cellSize = view.getCellSize();
        int rows = view.getNumberOfRows();
        int cols = view.getNumberOfColumns();
        int initDots;
        if (cols > 0 && rows > 0) {
            Dots.setKills(0);
            /*The level will determine the number of Dots appearing on the screen*/
            initDots = (Dots.getLevel() * 5) % (40);
            for (int i = 0; i < initDots; i++) {
                int x = rand.nextInt(cols);
                int y = rand.nextInt(rows);
                boolean state = rand.nextBoolean();
                try {
                    this.getLock();
                    Dots.addDot(cellSize * x, cellSize * y, cellSize, state);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    this.releaseLock();
                }
            }
        }
    }

    /*
     * Move the Dot to the random neighbor square
     * @param Dots
     * @param view
     * @param index
     */
    public void moveDot(Dots Dots, DotView view, int index) {
        Random rand = new Random();
        Dot Dot = Dots.getDot(index);

        int cellSize = view.getCellSize();
        int width = view.getWidth();
        int height = view.getHeight();

        float newX = Dot.getX() + cellSize * (rand.nextInt(3) - 1);
        float newY = Dot.getY() + cellSize * (rand.nextInt(3) - 1);


        if (newX < 0)
            newX = Dot.getX() + cellSize * (rand.nextInt(2) + 1);
        else if (newX + cellSize >= width)
            newX = Dot.getX() - cellSize * (rand.nextInt(2) + 1);

        if (newY < 0)
            newY = Dot.getY() + cellSize * (rand.nextInt(2) + 1);
        else if (newY + cellSize >= height)
            newY = Dot.getY() - cellSize * (rand.nextInt(2) + 1);
        boolean newState = !Dot.getProtectedInfo();
        try {
            this.getLock();
            Dots.moveDots(Dot, newX, newY, cellSize, newState);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.releaseLock();
        }
    }
}
