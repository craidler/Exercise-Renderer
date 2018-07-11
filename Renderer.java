package JNR;

import Graphic.Font;
import Graphic.Image;

import java.awt.image.DataBufferInt;

public class Renderer {
    private Font font = Font.STANDARD;
    private int pW, pH;
    private int[] p;

    public Renderer(Container c) {
        pW = c.getWidth();
        pH = c.getHeight();
        p = ((DataBufferInt) c.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    public void clear() {
        for (int i = 0; i < p.length; i++) {
            p[i] = 0;
        }
    }

    public void setPixel(int x, int y, int color) {
        int alpha = (color >> 24) & 0xff;

        // Offscreen
        if ((x < 0 || x >= pW || y < 0 || y >= pH) || alpha == 0) {
            return;
        }

        if (255 == alpha) {
            p[x + y * pW] = color;
            return;
        }

        int pixelcolor = p[x + y * pW];
        int nr = ((pixelcolor >> 16) & 0xff) - (int) ((((pixelcolor >> 16) & 0xff) - ((color >> 16) & 0xff)) * (alpha / 255f));
        int ng = ((pixelcolor >> 8) & 0xff) - (int) ((((pixelcolor >> 8) & 0xff) - ((color >> 8) & 0xff)) * (alpha / 255f));
        int nb = (pixelcolor & 0xff) - (int) (((pixelcolor & 0xff) - (color & 0xff)) * (alpha / 255f));
        p[x + y * pW] = (255 << 24 | nr << 16 | ng << 8 | nb);
    }

    public void drawImage(Image image, int xo, int yo) {
        int xn = 0, yn = 0;
        int wn = image.getW(), hn = image.getH();

        // Offscreen
        if (xo < -wn || yo < -hn) return;
        if (xo >= pW || yo >= pH) return;

        // Clipping
        if (xn + xo < 0) xn -= xo;
        if (yn + yo < 0) yn -= yo;
        if (wn + xo > pW) wn -= wn + xo - pW;
        if (hn + yo > pH) hn -= hn + yo - pH;

        for (int y = yn; y < hn; y++) {
            for (int x = xn; x < wn; x++) {
                setPixel(x + xo, y + yo, image.getP()[x + y * image.getW()]);
            }
        }
    }

    public void drawRect(int xo, int yo, int width, int height, int color1, int color2) {
        int xn = 0, yn = 0;
        int wn = width, hn = height;

        // Offscreen
        if (xo < -width || yo < -height) return;
        if (xo >= pW || yo >= pH) return;

        // Clipping
        if (xn + xo < 0) xn -= xo;
        if (yn + yo < 0) yn -= yo;
        if (wn + xo > pW) wn -= wn + xo - pW;
        if (hn + yo > pH) hn -= hn + yo - pH;

        for (int y = yn; y <= hn; y++) {
            for (int x = xn; x <= wn; x++) {
                setPixel(x + xo, y + yo, (x == xn || x == wn || y == yn || y == hn) ? color1 : color2);
            }
        }
    }

    public void drawRect(int xo, int yo, int width, int height, int color) {
        int xn = 0, yn = 0;

        // Offscreen
        if (xo < -width || yo < -height) return;
        if (xo >= pW || yo >= pH) return;

        for (int y = 0; y <= width; y++) {
            setPixel(xo, y + yo, color);
            setPixel(xo + width, y + yo, color);
        }

        for (int x = 0; x <= height; x++) {
            setPixel(xo + x, yo, color);
            setPixel(xo + x, yo + height, color);
        }
    }

    public void drawText(String text, int xo, int yo, int color) {
        int offset = 0;

        text = text.toUpperCase();

        for (int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i) - 32;

            for (int y = 0; y < font.getImage().getH(); y++) {
                for (int x = 0; x < font.getWidths()[unicode]; x++) {
                    if (font.getImage().getP()[(x + font.getOffsets()[unicode]) + y * font.getImage().getW()] == 0xffffffff) {
                        setPixel(x + xo + offset, y + yo, color);
                    }
                }
            }

            offset += font.getWidths()[unicode];
        }
    }
}
