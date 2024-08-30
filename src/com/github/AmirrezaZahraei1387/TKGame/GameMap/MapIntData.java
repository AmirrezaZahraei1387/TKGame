package com.github.AmirrezaZahraei1387.TKGame.GameMap;

import java.awt.Dimension;

public class MapIntData implements MapData{

    private Dimension size;
    private TileGB[][][] tiles;

    public MapIntData(int w, int h, int layers){
        tiles = new TileGB[w][h][layers];
        this.size = new Dimension(w, h);
    }

    @Override
    public Dimension getDim() {
        return size;
    }

    @Override
    public TileGB getTile(int i, int j, int l) {
        return tiles[i][j][l];
    }

    @Override
    public void setTile(int i, int j, int l, TileGB t) {
        tiles[i][j][l] = t;
    }

    @Override
    public int getLayerCount(int i, int j) {
        return tiles[i][j].length;
    }
}
