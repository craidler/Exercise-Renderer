package JNR;

import Element.Button;
import Graphic.Image;
import Interface.Renderable;
import Interface.Updatable;

import java.util.ArrayList;

public class Manager extends AbstractGame {
    private Image image;
    private ArrayList<Renderable> renderables = new ArrayList<>();
    private ArrayList<Updatable> updatables = new ArrayList<>();

    public Manager() {
        image = new Image("/JNR/Image/Test.gif");
        Button button0 = Button.create(100, 100, 100, 20, "Christof");
        Button button1 = Button.create(100, 130, 100, 20, "Werner");
        Button button2 = Button.create(100, 160, 100, 20, "Raidler");

        renderables.add(button0);
        renderables.add(button1);
        renderables.add(button2);
        updatables.add(button0);
        updatables.add(button1);
        updatables.add(button2);
    }

    public void update(Container c, float dt) {
        for (Updatable updatable : updatables) {
            updatable.update();
        }
    }

    public void render(Container c, Renderer r) {
        for (Renderable renderable : renderables) {
            renderable.render(r);
        }
    }

    public static void main(String args[]) {
        Container c = new Container(new Manager());
        c.start();
    }
}
