package com.github.AmirrezaZahraei1387.TKGame.GameMap;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

public class TileGroup {
    private final Rectangle bound;
    private final byte layer;

    public static GameMap map;

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
                if(new_i + bound.width < w && new_j + bound.height < h){
                    bound.x = new_i;
                    bound.y = new_j;

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

    public void repaint(int tm, int i, int j){
        if(i < 0 || j < 0 || i >= bound.width || j >= bound.height)
            throw new IndexOutOfBoundsException("the specified index is out of the bounds of this TileGroup");
        map.againPaint(tm,i + bound.x, j + bound.y, 1, 1);
    }

    public void repaint(int tm, int i, int j, int w, int h){
        if(i < 0 || j < 0 || i >= bound.width || j >= bound.height)
            throw new IndexOutOfBoundsException("the specified index is out of the bounds of this TileGroup");
        map.againPaint(tm,i + bound.x, j + bound.y, w, h);
    }

    public void repaint(int tm){
        map.againPaint(tm, bound.x, bound.y, bound.width, bound.height);
    }
}
