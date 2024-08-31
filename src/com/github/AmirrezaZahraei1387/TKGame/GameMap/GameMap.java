package com.github.AmirrezaZahraei1387.TKGame.GameMap;

import com.github.AmirrezaZahraei1387.TKGame.Camera.CameraHandler;
import com.github.AmirrezaZahraei1387.TKGame.Camera.CameraHandlerState;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javax.swing.JComponent;
import javax.swing.Timer;


public class GameMap extends JComponent {
    private final ListenerDraw[] listeners;

    MapData map;
    private int tileSize;

    private final CameraHandler cam;
    private CameraHandlerState prev_cam;

    private final Timer timer;
    private Rectangle currB;

    public GameMap(MapData map, CameraHandler cam, int tileSize, int count){
        this.map = map;
        this.listeners = new ListenerDraw[count];
        this.cam = cam;
        this.tileSize = tileSize;

        prev_cam = this.cam.getState();
        currB = null;

        timer = new Timer(5, e -> {

            CameraHandlerState curr_state = cam.getState();

            if(!curr_state.equals(prev_cam)){
                prev_cam = curr_state;
                againPaint(1);
            }

        });
    }

    public int getTileSize(){
        return tileSize;
    }

    public CameraHandler getCameraHandler(){
        return cam;
    }

    public Dimension getMapDim(){
        return map.getDim();
    }

    public void submitListener(int id, ListenerDraw d){
        if(listeners[id] != null)
            throw new NullPointerException("the ith listener is not empty.");
        listeners[id] = d;
    }

    public void start(){
        timer.start();
    }

    public void stop(){
        timer.stop();
    }

    @Override
    public void repaint(long tm, int x, int y, int w, int h){
        currB = translateBoundMap(new Rectangle(x, y, w, h));
        super.repaint(tm, x, y, w, h);
    }

    @Override
    public void repaint(long tm){
        currB = null;
        super.repaint(tm);
    }

    @Override
    public void paintComponent(Graphics _g2d){

        Graphics2D g2d = (Graphics2D) (_g2d);
        cam.setGraphics(g2d);

        if(currB == null)
            paintMap(g2d);
        else
            paintMap(g2d, currB);
    }

    /*
    do not directly use the repaint.
    instead use the following defined methods.
     */
    void againPaint(long tm){
        this.repaint(tm);
    }

    void againPaint(long tm, int i, int j, int w, int h){
        Rectangle rect = translateBoundWorld(new Rectangle(i, j, w, h));
        this.repaint(tm, rect.x, rect.y, rect.width, rect.height);
    }

    // translation functions
    // translate different entities from the world coordinates
    // to the map space and vise versa.
    // the returned value's coordinate space is the postFix of
    // each translation function.

    public Point translateMap(Point point){
        Point pos = new Point();

        pos.x = point.x / tileSize;
        pos.y = point.y / tileSize;

        Dimension dim = map.getDim();

        if(pos.x < 0)
            pos.x = 0;
        if(pos.y < 0)
            pos.y = 0;

        if(pos.x >= dim.width)
            pos.x = dim.width - 1;
        if(pos.y >= dim.height)
            pos.y = dim.height - 1;

        return pos;
    }

    public Point translateWorld(Point pos){
        return new Point(pos.x * tileSize, pos.y * tileSize);
    }

    private Rectangle translateBoundMap(Rectangle bound){

        Point o = translateMap(bound.getLocation());

        double _w =  ((double)bound.width / tileSize);
        double _h =  ((double)bound.height / tileSize);

        int w = (int) (_w % 1 == 0? _w : _w + 1);
        int h = (int) (_h % 1 == 0? _h : _h + 1);

        Dimension dim = map.getDim();

        if(o.x + w > dim.width)
            w = dim.width - o.x;
        if(o.y + h > dim.height)
            h = dim.height - o.y;

        return new Rectangle(o.x, o.y, w, h);
    }

    public Rectangle translateBoundWorld(Rectangle worldBound){

        Point o = translateWorld(worldBound.getLocation());

        int w = worldBound.width * tileSize;
        int h = worldBound.height * tileSize;

        Dimension dim = map.getDim();

        if(o.x + w > dim.width)
            w = dim.width - o.x;
        if(o.y + h > dim.height)
            h = dim.height - o.y;

        return new Rectangle(o.x, o.y, w, h);
    }

    /*
    returns the a
     */
    private Rectangle inViewTiles(Rectangle chunk, Rectangle bound){
        // the visible chunk
        Rectangle visChunk = translateBoundMap(bound);

        return visChunk.intersection(chunk);
    }

    /*
    paints the tiles into the visible portion of the
    window.
     */
    private void paintMap(Graphics2D g2d){

        Rectangle mapBound = translateBoundMap(cam.getBounds());

        drawTiles(g2d, mapBound);

    }


    /*
    only draw those tiles that are in the chunk.
     */
    private void paintMap(Graphics2D g2d, Rectangle chunk){

        Rectangle vis = inViewTiles(chunk, cam.getBounds());

        drawTiles(g2d, vis);
    }

    private void drawTiles(Graphics2D g2d, Rectangle chunk){
        for(int i = chunk.x; i < chunk.x + chunk.width; ++i)
            for(int j = chunk.y; j < chunk.y + chunk.height; ++j){

                int l_count = map.getLayerCount(i, j);

                for(int k = 0; k < l_count; ++k){
                    TileGB tile = map.getTile(i, j, k);

                    if(tile != null) {
                        ListenerDraw lis = listeners[tile.id];
                        if (lis != null) {
                            AffineTransform prev = g2d.getTransform();
                            g2d.translate(j * tileSize, i * tileSize);
                            lis.drawTile(tile.index, tileSize, g2d);
                            g2d.setTransform(prev);
                        }
                    }

                }

            }
    }
}
