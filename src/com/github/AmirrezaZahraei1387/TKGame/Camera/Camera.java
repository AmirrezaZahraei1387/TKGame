package com.github.AmirrezaZahraei1387.TKGame.Camera;

import java.awt.Point;
import java.awt.geom.AffineTransform;

public interface Camera {

    // returns a position in the world that must be
    // aligned by the origin of the window.
    Point getWorldPos();

    // returns an optional transformation to be applied to the
    // points.
    // can be useful to implement zooming and other effects.
    default AffineTransform getTransform(){return null;}
}
