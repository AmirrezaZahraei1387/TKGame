import com.github.AmirrezaZahraei1387.TKGame.Camera.CameraHandler;
import com.github.AmirrezaZahraei1387.TKGame.Camera.StaticCamera;
import com.github.AmirrezaZahraei1387.TKGame.Character.TankBase;
import com.github.AmirrezaZahraei1387.TKGame.Character.Velocity;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.GameMap;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.ListenerDraw;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.MapIntData;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.TileGB;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.TileGroup;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;


class BackGround implements ListenerDraw{

    BufferedImage img = ImageIO.read(new File("data/grass.jpg"));

    TileGroup tiles;

    BackGround(TileGroup t) throws IOException {
        this.tiles = t;

        // setting all tiles.
        for(int i = 0; i < tiles.getDim().width; ++i)
            for(int j = 0; j < tiles.getDim().height; ++j)
                tiles.set(i, j, new TileGB((byte) 0, i * tiles.getDim().width + j));
    }

    @Override
    public void drawTile(int i, int tileSize, Graphics2D g2d) {
        g2d.drawImage(img,0, 0, tileSize, tileSize, null);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        // setting up the dimensions
        Dimension worldDim = new Dimension(90, 90);
        int tileSize = 30;
        Dimension worldSize = new Dimension(worldDim.width * tileSize, worldDim.height * tileSize);
        Dimension screenSize = new Dimension(400, 400);
        int LISTENER_COUNT = 2;

        // setting up camera handler
        CameraHandler cam = new CameraHandler(worldSize);
        cam.setViewSize(screenSize);
        cam.setCurrCamera(new StaticCamera(null, new Point(0, 0)));


        // setting up the gameMap
        GameMap map = new GameMap(
                new MapIntData(worldDim.width, worldDim.height),
                cam,
                tileSize,
                LISTENER_COUNT);
        TileGroup.setGameMap(map);

        // setting Draw Listeners
        int BACKGROUND = 0;
        BackGround backGround = new BackGround(
                new TileGroup(new Rectangle(0,0,
                        worldDim.width, worldDim.height),
                        (byte) 0));
        map.submitListener(BACKGROUND, backGround);
        int SPRITE = 1;
        TankBase tank = new TankBase(
                new TileGroup(new Rectangle(4, 20, 4, 1), (byte) 1),
                new Velocity(5, 100)
        );
        map.submitListener(SPRITE, tank);

        // window
        JFrame frame = new JFrame();
        frame.setPreferredSize(screenSize);
        frame.add(map);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addKeyListener(tank);
        frame.setVisible(true);

        map.start();

        new Timer(50, e -> cam.setViewSize(frame.getSize())).start();
    }
}
