import com.github.AmirrezaZahraei1387.TKGame.Camera.CameraHandler;
import com.github.AmirrezaZahraei1387.TKGame.Camera.StaticCamera;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.GameMap;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.ListenerDraw;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.MapIntData;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.TileGB;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;

class DrawEX implements ListenerDraw{

    private static BufferedImage img;

    static {
        try {
            img = ImageIO.read(new File("data/Explosion_C.png"));
        } catch (IOException _) {
            throw new RuntimeException();
        }
    }

    @Override
    public void drawTile(int i, int tileSize, Graphics2D g2d) {
        g2d.drawImage(img, 0, 0, tileSize, tileSize, null);
    }
}

class DrawGrass implements ListenerDraw{

    private static BufferedImage img;

    static {
        try {
            img = ImageIO.read(new File("data/grass.jpg"));
        } catch (IOException _) {
            throw new RuntimeException();
        }
    }

    @Override
    public void drawTile(int i, int tileSize, Graphics2D g2d) {
        g2d.drawImage(img, 0, 0, tileSize, tileSize, null);
    }

}

public class Main {
    public static void main(String[] args){

        // setting up the dimensions
        Dimension worldDim = new Dimension(20, 20);
        int tileSize = 30;
        Dimension screenSize = new Dimension(400, 400);
        Dimension worldSize = new Dimension(worldDim.width * tileSize, worldDim.height * tileSize);

        // camera
        CameraHandler cam = new CameraHandler(worldSize);
        cam.setViewSize(screenSize);
        cam.setCurrCamera(new StaticCamera(null, new Point(-100, -100)));

        // map
        MapIntData data = new MapIntData(worldDim.width, worldDim.height, 2);

        for(int i = 0; i < worldDim.width; ++i)
            for(int j = 0; j < worldDim.height; ++j) {
                data.setTile(i, j, 0, new TileGB((byte) 0, 0));
                data.setTile(i, j, 1, new TileGB((byte) 1, 0));
            }

        // Map handler
        GameMap map = new GameMap(data, cam, tileSize, 2);
        map.submitListener(0, new DrawGrass());
        map.submitListener(1, new DrawEX());

        JFrame frame = new JFrame();
        frame.setPreferredSize(screenSize);
        frame.add(map);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        new Timer(1000, e -> {
            cam.setViewSize(frame.getSize());
        }).start();

        map.start();
    }
}
