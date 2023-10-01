package com.thebytguru.utils;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ResourceLoader {
    public static final String PATH = "res/";

    public static BufferedImage loadImage(String fileName) {
        BufferedImage image = null;

        try{
            image = ImageIO.read(new File(PATH + fileName));
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }
}
