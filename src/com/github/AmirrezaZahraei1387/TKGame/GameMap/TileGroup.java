package com.github.AmirrezaZahraei1387.TKGame.GameMap;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;


public class TileGroup {
    private final Rectangle bound;
    public final byte layer;

    public static GameMap map;

    private boolean rota = true;

    public TileGroup(Rectangle bound, byte layer){
        this.layer = layer;
        this.bound = bound;
    }

    public static void setGameMap(GameMap map){
        TileGroup.map = map;
    }

    public TileGB indexGroup(int i, int j, int l){
        if(i < 0 || j < 0 || i >= bound.width || j >= bound.height)
            throw new IndexOutOfBoundsException("the specified index is out of the bounds of this TileGroup");

        return map.map.getTile(i + bound.x, j + bound.y, l);
    }

    public TileGB indexMap(int i, int j, int l){
        return map.map.getTile(i, j, l);
    }

    public Dimension getDim(){
        return bound.getSize();
    }

    public void reset(){
        for(int i = bound.x; i < bound.x + bound.width; ++i)
            for(int j = bound.y; j < bound.y + bound.height; ++j){
                map.map.resetTile(i, j, layer);
            }
    }

    /*
    advances this tileGroup i rows and j columns.
     */
    public boolean advance(int i, int j){
        int new_i = bound.x + i;
        int new_j = bound.y + j;

        int w = map.map.getDim().width;
        int h = map.map.getDim().height;

        if(new_i >= 0 && new_j >= 0)
            if(new_i < w && new_j < h)
                if(new_i + bound.width <= w && new_j + bound.height <= h){
                    bound.x = new_i;
                    bound.y = new_j;

                    return true;
                }

        return false;
    }

    /*
    rotates this tileGroup with a multiple of 90 around
    the point p defined in the tileGroup space.
     */

    public boolean rotate(int deg, int x, int y){
        if(deg % 90 != 0)
            return false;
        AffineTransform t = AffineTransform.getRotateInstance(
                Math.toRadians(deg),
                bound.x + x,
                bound.y + y);

        Point p1 = bound.getLocation();
        Point p2 = new Point(p1.x + bound.width, p1.y);
        Point p3 = new Point(p1.x + bound.width, p1.y + bound.height);
        Point p4 = new Point(p1.x, p1.y + bound.height);

        t.transform(p1, p1);
        t.transform(p2, p2);
        t.transform(p3, p3);
        t.transform(p4, p4);

        Point loc;

        if(p1.y < p2.y){
            if(p1.x < p4.x)
                loc = p1;
            else
                loc = p4;
        }else{
            if(p2.x < p3.x)
                loc = p2;
            else
                loc = p3;
        }

        int w = map.map.getDim().width;
        int h = map.map.getDim().height;

        if(loc.getX() >= 0 && loc.getY() >= 0)
            if(loc.getX() < w && loc.getY() < h)
                if(loc.getX() + bound.height <= w && loc.getY() + bound.width <= h){
                    bound.x = (int) loc.getX();
                    bound.y = (int) loc.getY();

                    resize(bound.height, bound.width);

                    rota = !rota;

                    return true;
                }

        return false;
    }

    public boolean resize(int new_w, int new_h){

        int w = map.map.getDim().width;
        int h = map.map.getDim().height;

        if(new_w + bound.x < w && new_h + bound.y < h){
            if(new_w + bound.x >= 0 && new_h + bound.y >=  0){

                bound.width = new_w;
                bound.height = new_h;

                return true;

            }
        }

        return false;
    }

    public void reset(int i, int j){
        map.map.resetTile(i + bound.x, j + bound.y, layer);
    }

    /*
    returns the origin of this tile group in the actual map.
    it returns the i and j of the tile, so you need to translate.
     */
    public Point getOrigin(){
        return bound.getLocation();
    }

    public void set(int i, int j, TileGB t){

        if(i < 0 || j < 0 || i >= bound.width || j >= bound.height)
            throw new IndexOutOfBoundsException("the specified index is out of the bounds of this TileGroup");

        map.map.setTile(i + bound.x, j + bound.y, layer, t);
    }

    public void repaint(int i, int j){
        if(i < 0 || j < 0 || i >= bound.width || j >= bound.height)
            throw new IndexOutOfBoundsException("the specified index is out of the bounds of this TileGroup");
        map.againPaint(i + bound.x, j + bound.y, 1, 1);
    }

    public void repaint(int i, int j, int w, int h){
        if(i < 0 || j < 0 || i >= bound.width || j >= bound.height)
            throw new IndexOutOfBoundsException("the specified index is out of the bounds of this TileGroup");
        map.againPaint(i + bound.x, j + bound.y, w, h);
    }

    public void repaint(){
        map.againPaint(bound.x, bound.y, bound.width, bound.height);
    }

    public void resetPainter(){
        for(int i = 0; i < bound.width; ++i)
            for(int j = 0; j < bound.height; ++j)
                set(i, j, new TileGB(GameMap.RESET_DRAWER, 0));
        repaint();
        reset();
    }

    public void resetPainter(int i, int j, int w, int h){
        for(int _i = i; _i < i + w; ++_i)
            for(int _j = j; _j < j + h; ++_j) {
                set(_i, _j, new TileGB(GameMap.RESET_DRAWER, 0));
            }

        repaint(i, j, w, h);

        for(int _i = i; _i < i + w; ++_i)
            for(int _j = j; _j < j + h; ++_j)
                reset(_i, _j);

        for(int _i = 0; _i < this.getDim().width; ++_i)
            for(int _j = 0; _j < this.getDim().height; ++_j)
                System.out.println(this.indexGroup(_i, _j, this.layer));
    }

    public void resetPainter(int i, int j){
        set(i, j, new TileGB(GameMap.RESET_DRAWER, 0));
        repaint(i, j);
        reset(i, j);
    }
}
