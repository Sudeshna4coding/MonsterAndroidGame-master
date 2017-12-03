import com.oreilly.demo.android.pa.uidemo.model.Dot;
import com.oreilly.demo.android.pa.uidemo.model.Dots;
import com.oreilly.demo.android.pa.uidemo.Controller.DotWork;
import com.oreilly.demo.android.pa.uidemo.view.DotView;

import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

public class DotTests {

    private Dot dotModel;
    private Dots dotsModel;
    private DotWork dotWork;
    private DotView dotView;

    // A monster that moves autonomously should only move into their adjacent cells at each move.
    public void testMoveDotIntoAdjacentLocations() {
        // Get a random dot, and compare the x- and y-axis before and after the move
        Random rand = new Random();
        int numberOfDots = dotsModel.getDots().size();
        int randomDotIndex = rand.nextInt(numberOfDots);
        Dot randomDot = dotsModel.getDot(randomDotIndex);

        float oldX = randomDot.getX();
        float oldY = randomDot.getY();
        dotWork.moveDot(dotsModel, dotView, randomDotIndex);

        float newX = randomDot.getX();
        float newY = randomDot.getY();
        int cellSize = dotView.getCellSize();

        assertTrue((newX > (oldX - cellSize)) && (newX < (oldX + cellSize)));
        assertTrue((newY > (oldY - cellSize)) && (newY < (oldY + cellSize)));
    }

    // A monster can only be killed when it's in the vulnerable state
    public void testMonsterKillableState() {
        Random rand = new Random();
        int numberOfDots = dotsModel.getDots().size();
        int randomDotIndex = rand.nextInt(numberOfDots);
        Dot safeDot = dotsModel.getDot(randomDotIndex);

        // Find a monster in protected state and try to kill it
        if (safeDot.getProtectedInfo() != true) {
            randomDotIndex = rand.nextInt(numberOfDots);
            safeDot = dotsModel.getDot(randomDotIndex);
        }

        assertTrue(safeDot.getProtectedInfo());
        dotsModel.killDot(safeDot.getX(), safeDot.getY());
        assertNotNull(safeDot);

        // Find a monster in unprotected state and try to kill it
        randomDotIndex = rand.nextInt(numberOfDots);
        Dot vulnerableDot = dotsModel.getDot(randomDotIndex);

        if (safeDot.getProtectedInfo() == true) {
            randomDotIndex = rand.nextInt(numberOfDots);
            vulnerableDot = dotsModel.getDot(randomDotIndex);
        }

        assertFalse(vulnerableDot.getProtectedInfo());
        dotsModel.killDot(safeDot.getX(), safeDot.getY());
        assertNull(vulnerableDot);
    }

}