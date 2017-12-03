package com.oreilly.demo.android.pa.uidemo.model;


/*This will represent a single Dot,its co-ordinates,color nad shape.It will also determine if it is protected or vulnerable */
public final class Dot {
    private final float x, y;
    private final int diameter;
    private final boolean isProtected;

    /* @param x horizontal coordinate.
     * @param y vertical coordinate.
     * @param diameter dot diameter.
     */
    public Dot(float x, float y, int diameter, boolean isProtected) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.isProtected = isProtected;
    }


    /*It returns the horizontal coordinate.*/
    public float getX() { return x; }


    /* It returns the vertical coordinate.*/
    public float getY() { return y; }


    /* It returns the dot diameter.*/
    public int getDiameter() { return diameter; }


    /*This returns if the Dot is Protected or not */
    public boolean getProtectedInfo() {
        return isProtected;
    }
}