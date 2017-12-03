package com.oreilly.demo.android.pa.uidemo;

import android.test.ActivityInstrumentationTestCase2;

import static org.junit.Assert.assertNotNull;

import android.content.pm.ActivityInfo;
import android.widget.TextView;


/**
 * Created by asmina_akram on 5/2/16.
 */

public class TouchMeTests extends ActivityInstrumentationTestCase2<TouchMe> {
    public TouchMeTests() {
        super(TouchMe.class);
    }

    public void testLaunchSuccessful() {
        TouchMe activity = getActivity();
        assertNotNull(activity);
    }

    // The state of the app is preserved when the device is rotated.
    // Among various variables on the screen, the game level was chosen to be compared before and after rotation,
    // as most other variables (number of monsters, score, and remaining time are susceptible to change on a second-to-second basis)
    public void testDisplayAtDifferentOrientation( ) {
        TouchMe activity = getActivity();
        final TextView levelBeforeRotation = (TextView) activity.findViewById(R.id.text1);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        final TextView levelAfterRotation = (TextView) activity.findViewById(R.id.text1);
        assertEquals(levelBeforeRotation, levelAfterRotation);
    }

}
