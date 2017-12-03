package com.oreilly.demo.android.pa.uidemo;

import com.oreilly.demo.android.pa.uidemo.model.Dots;
import com.oreilly.demo.android.pa.uidemo.model.Dot;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class DotsTests {

    private Dots model;

    /**
     * Setter for dependency injection. Usually invoked by concrete testcase
     * subclass.
     *
     * @param model
     */
    protected void setModel(final Dots model) {
        this.model = model;
    }

    protected Dots getModel() {
        return model;
    }

    // There should be a predetermined number of monsters in the initial state
    // (with test below setting the initial state at level 1 and level 5 respectively).
    public void testInitialNumberOfDots() {
        model.setLevel(1);
        assertEquals(model.getDots().size(), 5);
        model.setLevel(5);
        assertEquals(model.getDots().size(), 25);
    }

}