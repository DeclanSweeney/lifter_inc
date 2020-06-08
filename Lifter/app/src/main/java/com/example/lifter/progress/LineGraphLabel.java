package com.example.lifter.progress;

public class LineGraphLabel {
    private float x;
    private float y;
    private String label;

    public LineGraphLabel(float x,float y,String label){
        this.x = x;
        this.y = y;
        this.label = label;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String getLabel() {
        return label;
    }
}
