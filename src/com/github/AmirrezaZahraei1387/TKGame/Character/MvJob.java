package com.github.AmirrezaZahraei1387.TKGame.Character;

public class MvJob {

    // different types of job
    public static final byte MOVING = 0;
    public static final byte TURNING = 2;

    // different direction of job
    public static final byte FORWARD = 0;
    public static final byte BACKWARD = 2;
    public static final byte LEFT = 4;
    public static final byte RIGHT = 8;

    public final byte id;
    public final byte dir;

    double remaining;
    long prevTime;

    public MvJob(byte id, byte dir, double remaining){
        this.dir = dir;
        this.id = id;
        this.remaining = remaining;

        this.prevTime = 0;
    }

    public boolean isFinished(){
        return remaining == 0;
    }
}
