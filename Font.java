package Graphic;

public class Font {
    public static final Font STANDARD = new Font("/JNR/Image/font.png");

    private Image image;
    private int[] offsets;
    private int[] widths;

    public Font(String path) {
        image = new Image(path);
        offsets = new int[59];
        widths = new int[59];

        int unicode = 0;

        for (int i = 0; i < image.getW(); i++) {
            if (image.getP()[i] == 0xff0000ff) {
                offsets[unicode] = i;
            }

            if (image.getP()[i] == 0xffffff00) {
                widths[unicode] = i - offsets[unicode];
                unicode++;
            }
        }
    }

    public Image getImage() {
        return image;
    }

    public int[] getOffsets() {
        return offsets;
    }

    public int[] getWidths() {
        return widths;
    }
}
