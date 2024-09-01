import com.github.AmirrezaZahraei1387.TKGame.Camera.CameraHandler;
import com.github.AmirrezaZahraei1387.TKGame.Camera.StaticCamera;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.GameMap;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.ListenerDraw;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.MapIntData;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.TileGB;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.TileGroup;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;

class DrawSprite implements ListenerDraw, KeyListener {

    BufferedImage im_ = ImageIO.read(new File("data/circle.jpg"));
    TileGroup tiles;


    byte dir = 0;

    public DrawSprite(TileGroup tiles) throws IOException {
        this.tiles = tiles;

        // setting all tiles.
        setAll();

    }

    @Override
    public void drawTile(int i, int tileSize, Graphics2D g2d) {
        g2d.drawImage(im_, 0, 0, tileSize, tileSize, null);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                if(dir == 0){
                    move(0, -1);
                }else if(dir == 1) {
                    move(1, 0);
                }else if(dir == 2){
                    move(0, 1);
                }else if(dir == 3){
                    move(-1, 0);
                }
                break;
            case KeyEvent.VK_S:
                if(dir == 0){
                    move(0, 1);
                }else if(dir == 1) {
                    move(-1, 0);
                }else if(dir == 2){
                    move(0, -1);
                }else if(dir == 3){
                    move(1, 0);
                }
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    void move(int i, int j){
        tiles.reset();
        tiles.repaint();
        tiles.advance(i, j);
        setAll();
        tiles.repaint();
    }

    void setAll(){
        for(int i = 0; i < tiles.getDim().width; ++i)
            for(int j = 0; j < tiles.getDim().height; ++j) {
                tiles.set(i, j, new TileGB((byte) 0, i * tiles.getDim().width + j));
            }
    }
}

class BackGround implements ListenerDraw{

    BufferedImage img = ImageIO.read(new File("data/grass.jpg"));

    TileGroup tiles;

    BackGround(TileGroup t) throws IOException {
        this.tiles = t;

        // setting all tiles.
        for(int i = 0; i < tiles.getDim().width; ++i)
            for(int j = 0; j < tiles.getDim().height; ++j)
                tiles.set(i, j, new TileGB((byte) 1, i * tiles.getDim().width + j));
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
        int BACKGROUND = 1;
        BackGround backGround = new BackGround(
                new TileGroup(new Rectangle(0,0,
                        worldDim.width, worldDim.height),
                        (byte) 0));
        map.submitListener(BACKGROUND, backGround);

        int OBSTACLE = 0;
        DrawSprite obstacle = new DrawSprite(
                new TileGroup(
                new Rectangle(1, 2,
                3, 5),
                (byte) 1));
        map.submitListener(OBSTACLE, obstacle);

        // window
        JFrame frame = new JFrame();
        frame.setPreferredSize(screenSize);
        frame.add(map);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addKeyListener(obstacle);
        frame.setVisible(true);

        map.start();

        new Timer(50, e -> cam.setViewSize(frame.getSize())).start();
    }
}
