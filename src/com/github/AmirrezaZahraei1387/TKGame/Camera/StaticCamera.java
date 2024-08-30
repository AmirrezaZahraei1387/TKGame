package com.github.AmirrezaZahraei1387.TKGame.Camera;


import java.awt.Point;
import java.awt.geom.AffineTransform;

public class StaticCamera implements Camera{

    private final AffineTransform transform;
    private final Point worldAlignment;

    public StaticCamera(AffineTransform transform, Point worldAlignment) {
        this.transform = transform;
        this.worldAlignment = worldAlignment;
    }



    @Override
    public Point getWorldPos() {
        return worldAlignment;
    }

    public AffineTransform getTransform() {
        return transform;
    }
}
