package com.github.AmirrezaZahraei1387.TKGame.GameMap;

import java.awt.Dimension;

/*
MapData defines how the Tiles in the game are going to be
represented. It interfaces the storage of a map with actual
map handling.
 */
public interface MapData {

    /*
    returns the number of rows and columns
    of this mapData. It should not return the
    actual worldSize.
     */
    Dimension getDim();

    /*
    retrieves the tile contained in the ith row,
    jth column and lth layer of the map.
    should return null in case the layer does not exist or
    the tile contains no element.
     */
    TileGB getTile(int i, int j, int l);

    /*
    reset the tile in the specified position
    meaning it no longer contain a tile.
     */
    void resetTile(int i, int j, int l);

    /*
    sets the tile in the specified position to
    a new TileGB object t.
     */
    void setTile(int i, int j, int l, TileGB t);

    /*
    returns the number of layers contained in
    the specified position.
     */
    int getLayerCount(int i, int j);
}
