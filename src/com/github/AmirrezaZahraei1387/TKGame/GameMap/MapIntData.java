package com.github.AmirrezaZahraei1387.TKGame.GameMap;

import java.awt.Dimension;

/*
the id and index of each tile is packed in an
integer. an extra flag is kept to indicate if this
tile is currently valid or not.
 */
public class MapIntData implements MapData{

    private final Dimension size;
    private final int[][][] tiles;

    private static final int SET;
    private static final int CLEAR;
    private static final int INDEX;

    static {
        SET = 0b10000000000000000000000000000000;
        CLEAR = 0b01111111111111111111111111111111;
        INDEX = 0b00000000011111111111111111111111;
    }

    public MapIntData(int w, int h){
        this.size  = new Dimension(w,h);
        tiles = new int[w][h][];
    }


    @Override
    public Dimension getDim() {
        return size;
    }

    @Override
    public TileGB getTile(int i, int j, int l) {
        int[] stack = tiles[i][j];

        if(stack == null){
            return null;

        }else if(stack.length <= l){
            return null;

        }else if((stack[l] & SET) == 0){
            return null;

        }else{
            int k = stack[l];
            k &= CLEAR; // clear the set flag

            TileGB t = new TileGB((byte) 0, 0);
            t.id = (byte) (k >>> 23);
            t.index = k & INDEX;

            return t;
        }
    }

    @Override
    public void resetTile(int i, int j,  int l) {
        int[] stack = tiles[i][j];

        if(stack != null){
            if(stack.length > l)
                stack[l] &= CLEAR;
        }
    }

    @Override
    public void setTile(int i, int j, int l, TileGB t) {
        int[] stack = tiles[i][j];

        if(stack == null){
            tiles[i][j] = new int[l + 1];

            tiles[i][j][l] = 0b0;

            tiles[i][j][l] |= SET;
            tiles[i][j][l] |= t.id <<23;
            tiles[i][j][l] |= t.index;

        }else{
            int[] temp = new int[l + 1];
            System.arraycopy(stack, 0, temp, 0, stack.length);
            temp[l] = 0b0;

            temp[l] |= SET;
            temp[l] |= t.id <<23;
            temp[l] |= t.index;

            tiles[i][j] = temp;
        }
    }

    @Override
    public int getLayerCount(int i, int j) {
        int[] stack = tiles[i][j];

        if(stack == null){
            return 0;
        }else{
            return stack.length;
        }
    }

}
