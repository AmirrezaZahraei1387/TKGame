package com.github.AmirrezaZahraei1387.TKGame.GameMap;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class TileGroup {
    private final Rectangle bound;
    private final byte layer;

    private static GameMap map;

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

    public Point getOrigin(){
        return bound.getLocation();
    }

    public void set(int i, int j, TileGB t){
        if(i < 0 || j < 0 || i >= bound.width || j >= bound.height)
            throw new IndexOutOfBoundsException("the specified index is out of the bounds of this TileGroup");

        map.map.setTile(i + bound.x, j + bound.y, layer, t);
    }

    public void repaint(int tm, int i, int j){
        if(i < 0 || j < 0 || i >= bound.width || j >= bound.height)
            throw new IndexOutOfBoundsException("the specified index is out of the bounds of this TileGroup");
        map.againPaint(tm,i + bound.x, j + bound.y, 1, 1);
    }

    public void repaint(int tm){
        map.againPaint(tm, bound.x, bound.y, bound.width, bound.height);
    }
}
