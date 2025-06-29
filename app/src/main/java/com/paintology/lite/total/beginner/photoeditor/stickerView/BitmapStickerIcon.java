package com.paintology.lite.total.beginner.photoeditor.stickerView;

import android.graphics.drawable.Drawable;

public class BitmapStickerIcon extends DrawableSticker {
    private float x;
    private float y;

    public BitmapStickerIcon(Drawable drawable) {
        super(drawable);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
