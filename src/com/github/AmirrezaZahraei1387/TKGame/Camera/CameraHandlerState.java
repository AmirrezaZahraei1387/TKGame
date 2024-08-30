package com.github.AmirrezaZahraei1387.TKGame.Camera;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.AffineTransform;

public class CameraHandlerState {
    public final Point currP;
    public final AffineTransform transform;
    public final Dimension dimension;

    public CameraHandlerState(Point currP, AffineTransform transform, Dimension dimension) {
        this.currP = currP;
        this.transform = transform;
        this.dimension = dimension;
    }

    public CameraHandlerState() {
        this.currP = null;
        this.transform = null;
        this.dimension = null;
    }


    @Override
    public boolean equals(Object other) {
        if(other instanceof CameraHandlerState o_s) {
            boolean t1;

            if(currP != null)
                t1 = currP.equals(o_s.currP);
            else
                t1 = (o_s.currP == null);

            boolean t2;

            if(transform != null)
                t2 = transform.equals(o_s.transform);
            else
                t2 = (o_s.transform == null);

            boolean t3;

            if(dimension != null)
                t3 = dimension.equals(o_s.dimension);
            else
                t3 = (o_s.dimension == null);

            return t1 && t2 && t3;
        }

        return false;
    }
}
