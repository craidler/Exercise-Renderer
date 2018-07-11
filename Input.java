package JNR;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    public final static Input instance = new Input();

    public final static int NUM_KEYS = 256;
    public final static int NUM_BUTTONS = 5;

    private boolean[] keysCurrent = new boolean[NUM_KEYS];
    private boolean[] keysLast = new boolean[NUM_KEYS];
    private boolean[] buttonsCurrent = new boolean[NUM_BUTTONS];
    private boolean[] buttonsLast = new boolean[NUM_BUTTONS];

    private int mouseX, mouseY, scroll;
    private float scale;

    public void initialize(Container c) {
        mouseX = 0;
        mouseY = 0;
        scroll = 0;
        scale = c.getScale();

        c.getWindow().getCanvas().addKeyListener(this);
        c.getWindow().getCanvas().addMouseListener(this);
        c.getWindow().getCanvas().addMouseMotionListener(this);
        c.getWindow().getCanvas().addMouseWheelListener(this);

        System.out.println("initialize input");
    }

    public void update() {
        scroll = 0;

        for (int i = 0; i < NUM_KEYS; i++) {
            keysLast[i] = keysCurrent[i];
        }

        for (int i = 0; i < NUM_BUTTONS; i++) {
            buttonsLast[i] = buttonsCurrent[i];
        }
    }

    public boolean isButton(int code) {
        return buttonsCurrent[code];
    }

    public boolean isButtonUp(int code) {
        return !buttonsCurrent[code] && buttonsLast[code];
    }

    public boolean isButtonDown(int code) {
        return buttonsCurrent[code] && !buttonsLast[code];
    }

    public boolean isKey(int code) {
        return keysCurrent[code];
    }

    public boolean isKeyUp(int code) {
        return !keysCurrent[code] && keysLast[code];
    }

    public boolean isKeyDown(int code) {
        return keysCurrent[code] && !keysLast[code];
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        keysCurrent[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keysCurrent[e.getKeyCode()] = false;
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        buttonsCurrent[e.getButton()] = true;
    }

    public void mouseReleased(MouseEvent e) {
        buttonsCurrent[e.getButton()] = false;
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        mouseX = (int) (e.getX() / scale);
        mouseY = (int) (e.getY() / scale);
    }

    public void mouseMoved(MouseEvent e) {
        mouseX = (int) (e.getX() / scale);
        mouseY = (int) (e.getY() / scale);
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }
}
