package JNR;

abstract public class AbstractGame {
    public abstract void update(Container c, float dt);
    public abstract void render(Container c, Renderer r);
}
