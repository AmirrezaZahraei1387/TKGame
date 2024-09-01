package com.github.AmirrezaZahraei1387.TKGame.Tools;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/*
this is a very simple program that creates 2D graphical
images by a matrix of integers in order. Each integer in the matrix will
correspond to a pixel which its color and transparency is defined by
the user. We then construct the image and save it with the given name
and format in the specified location.
 */
public class DG {
    public static BufferedImage setUpImg(int[][] m,
                                         Dimension dim,
                                         Color[] colors) {
        BufferedImage img = new BufferedImage(
                dim.width,
                dim.height,
                BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < dim.width; x++) {
            for (int y = 0; y < dim.height; y++) {
                img.setRGB(x, y, colors[m[x][y]].getRGB());
            }
        }

        return img;
    }

    public static String getExtension(File file) {
        String fileName = file.toPath().toString();
        int dotIndex = fileName.lastIndexOf('.');

        // handle cases with no extension or multiple dots
        if (dotIndex == -1 || dotIndex == fileName.length() - 1) {
            return "";          // no extension found
        } else {
            return fileName.substring(dotIndex + 1);
        }
    }

    public static void main(String[] args) throws IOException {
        if(args.length != 2){
            System.out.println("Usage : java DrawGraphic <image-name> <data-file>");
            System.exit(1);
        }

        FileReader fr = null;
        Scanner sc = null;

        try {
            fr = new FileReader(args[1]);
            sc = new Scanner(fr);
        }catch (IOException e){
            e.printStackTrace();
            fr.close();
            sc.close();
            System.exit(1);
        }

        int colorCount = sc.nextInt();
        Color[] colors = new Color[colorCount];

        for(int i = 0; i < colorCount; i++){

            int r = sc.nextInt();
            int g = sc.nextInt();
            int b = sc.nextInt();
            int a = sc.nextInt();

            colors[i] = new Color(r, g, b, a);
        }

        int w = sc.nextInt();
        int h = sc.nextInt();

        int[][] m = new int[w][h];

        for(int x = 0; x < w; x++){
            for(int y = 0; y < h; y++){
                m[x][y] = sc.nextInt();
            }
        }

        File file = new File(args[0]);

        BufferedImage img = setUpImg(m, new Dimension(w, h), colors);

        try {
            ImageIO.write(img, getExtension(file), file);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
