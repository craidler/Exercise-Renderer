package Graphic;

import Interface.Locatable;
import Interface.Renderable;
import JNR.Renderer;

public class Rectangle implements Locatable, Renderable {
    private int x, y, w, h, c1, c2 = 0;

    public Rectangle(int x, int y, int width, int height, int color1, int color2) {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
        this.c1 = color1;
        this.c2 = color2;
    }

    public Rectangle(int x, int y, int width, int height, int color1) {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
        this.c1 = color1;
    }

    public void relocate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void render(Renderer r) {
        r.drawRect(x, y, w, h , c1, c2);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
