package com.github.AmirrezaZahraei1387.TKGame.Character;

import static com.github.AmirrezaZahraei1387.TKGame.Character.MvJob.*;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.ListenerDraw;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.TileGB;
import com.github.AmirrezaZahraei1387.TKGame.GameMap.TileGroup;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;
import javax.imageio.ImageIO;

public class TankBase implements ListenerDraw, KeyListener {
    protected TileGroup tiles;
    private Velocity velocity;

    private boolean isResetting;
    private byte curr_dir;

    private MvJob[] jobs;

    double p;

    private final BufferedImage img = ImageIO.read(new File("data/Explosion_C.png"));

    public TankBase(TileGroup tiles, Velocity v) throws IOException {
        this.tiles = tiles;
        this.velocity = v;

        isResetting = false;

        jobs = new MvJob[2];

        setAll();

        new Timer(4, e -> {
            if(jobs[0] != null){
                long currTime = System.currentTimeMillis();

                if(currTime - jobs[0].prevTime >= velocity.duration){
                    jobs[0].prevTime = currTime;

                    MvJob job = jobs[0];


                    if(job.id == MOVING){
                        p = velocity.dist;

                        switch (job.dir){
                            case FORWARD:
                                job.remaining -= p;
                                if(job.remaining < 0){
                                    p = - job.remaining;
                                    job.remaining = 0;
                                }
                                p = -p;
                        }
                    }


                    tiles.repaint(0, 0, 4, 6);
                    if(jobs[0].isFinished())
                        jobs[0] = null;

                }
            }
        }).start();
    }

    public boolean submitMove(int t){
        if(jobs[0] != null)
            return false;

        boolean result = false;

        reset();

        switch (curr_dir){
            case FORWARD:
                result = tiles.advance(0, -t);
                break;
        }

        if(result){
            jobs[0] = new MvJob(MOVING, curr_dir, t * TileGroup.map.getTileSize());

        }

        setAll();

        return result;
    }

    @Override
    public void drawTile(int i, int tileSize, Graphics2D g2d) {

        if(isResetting){
            return;
        }

        else if(jobs[0] != null){
            MvJob job = jobs[0];

            if(job.id == MOVING){

                g2d.setClip(null);
                g2d.drawImage((Image) img, 0, (int) job.remaining, tileSize, tileSize, null);

            }else if(job.id == TURNING){

            }
        }
    }


    private void reset(){
        isResetting = true;
        tiles.reset();
        tiles.repaint();
        isResetting = false;
        setAll();
    }


    private void setAll(){
        for(int i = 0; i < tiles.getDim().width; ++i)
            for(int j = 0; j < tiles.getDim().height; ++j)
                tiles.set(i, j, new TileGB((byte) 1, i * tiles.getDim().width + j));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_W:
                submitMove(1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
