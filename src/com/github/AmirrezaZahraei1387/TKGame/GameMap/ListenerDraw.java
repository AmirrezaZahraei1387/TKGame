package com.github.AmirrezaZahraei1387.TKGame.GameMap;

import java.awt.Graphics2D;

/*
all the objects that handle tiles
must implement the following interface and
provide it to the GameMap.
 */
public interface ListenerDraw {

    /*
    draw the tile contained in ith location
    using the provided graphics2D.

    Note: if you change one of graphics contexts
    make sure to reset it back to it previous value.
    otherwise the rendering of other components will not
    be as desired.
     */
    void drawTile(int i, int tileSize, Graphics2D g2d);

}
