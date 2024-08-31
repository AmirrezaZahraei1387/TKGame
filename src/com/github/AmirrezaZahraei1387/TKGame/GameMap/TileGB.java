package com.github.AmirrezaZahraei1387.TKGame.GameMap;


/*
represents a single tile of the game.
this is not the supposed way of storing tiles.
The MapData must be only able to return one this.
*/
public class TileGB {
    public byte id; // the type of the tile. It represents what kind of
    // listener is managing this tile.
    public int index; // an integer used to identify this tile in its listener

    public TileGB(byte id, int index){
        this.id = id;
        this.index = index;
    }

    @Override
    public String toString() {
        return "TileGB(id=%d, index=%d)".formatted(id, index);
    }
}
