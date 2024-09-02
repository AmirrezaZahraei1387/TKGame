package com.github.AmirrezaZahraei1387.TKGame.Character;

public class Velocity {
    public  final double dist;
    public final long duration;

    public Velocity(double dist, long duration){
        this.dist = dist;
        this.duration = duration;
    }

    @Override
    public String toString(){
        return "Velocity(dist=%f, duration=%d)".formatted(dist, duration);
    }
}
