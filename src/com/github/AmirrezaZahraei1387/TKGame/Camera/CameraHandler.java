package com.github.AmirrezaZahraei1387.TKGame.Camera;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

public class CameraHandler {
    private static final Camera DEFAULT_CAMERA = new StaticCamera(null, new Point(0, 0));
    private static final Dimension DEFAULT_VIEW = new Dimension(100, 100);

    private Camera currCamera;

    private Dimension viewSize;
    private final Dimension worldSize;

    public CameraHandler(Dimension worldSize){
        this.currCamera = DEFAULT_CAMERA;
        this.viewSize = DEFAULT_VIEW;

        this.worldSize = worldSize;
    }

    public void setCurrCamera(Camera currCamera) {
        if(currCamera == null)
            throw new NullPointerException("camera can not be null.");
        this.currCamera = currCamera;
    }

    public Dimension getViewSize() {
        return viewSize;
    }

    public void setViewSize(Dimension viewSize) {
        if(viewSize == null)
            throw new NullPointerException("viewSize can not be null.");
        this.viewSize = viewSize;
    }

    public CameraHandlerState getState(){
        return new CameraHandlerState(currCamera.getWorldPos(), currCamera.getTransform(), viewSize);
    }

    public Dimension getWorldSize(){
        return worldSize;
    }

    public Rectangle getBounds(){
        Point p = currCamera.getWorldPos();
        return new Rectangle(p.x, p.y, viewSize.width + p.x, viewSize.height + p.y);
    }

    /*
    sets the given graphics to draw into the camera view.
     */
    public void setGraphics(Graphics2D g2d) {
        Point p = currCamera.getWorldPos();
        g2d.translate(-p.x, -p.y);

        AffineTransform t = currCamera.getTransform();

        if(t != null)
            g2d.setTransform(t);
    }

}
