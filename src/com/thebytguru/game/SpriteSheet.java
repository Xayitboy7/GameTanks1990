package com.thebytguru.game;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private BufferedImage sheet;
    private int spriteCount;
    private int scale;
    private int spriteInWidth;

    public SpriteSheet(BufferedImage sheet, int spriteCount, int scale) {
        this.sheet = sheet;
        this.scale = scale;
        this.spriteCount = spriteCount;

        this.spriteInWidth = sheet.getWidth() / scale;
    }

    public BufferedImage getSprite(int index) {
        index = index % spriteCount;
        int x = index % spriteInWidth * scale;
        int y = index / spriteInWidth * scale;

        return sheet.getSubimage(x, y, scale, scale);
    }
}
