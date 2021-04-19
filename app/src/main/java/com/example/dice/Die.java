package com.example.dice;

public class Die {
    private final String diceType;
    private final int numSides;
    private int sideUp;

    public Die() {
        // Using default value of 6 for field "numSides"
        this(6);
    }

    public Die(int numSides) {
        this(numSides, "d"+numSides);
    }

    public Die(String diceType) {
        this( Integer.parseInt(diceType.substring(1)), diceType);
    }

    private Die(int numSides, String diceType) {
        this.diceType = diceType;
        this.numSides = numSides;
        if (numSides == 10) {
            this.sideUp = (int) Math.floor(Math.random()*numSides) * 10;
        } else {
            this.sideUp = (int) Math.ceil(Math.random()*numSides);
        }
    }

    public String getDiceType() {
        return diceType;
    }

    public int getNumSides() {
        return numSides;
    }


    public int getSideUp() {
        return sideUp;
    }

    public void setSideUp(int sideUp) {
            this.sideUp = sideUp;
    }

    public void roll() {
        if (this.getNumSides() == 10) {
            this.sideUp = (int) Math.floor(Math.random() * this.numSides) * 10;
        } else {
            this.sideUp = (int) Math.ceil(Math.random() * this.numSides);
        }

    }


}
