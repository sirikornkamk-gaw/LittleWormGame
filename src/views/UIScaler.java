package views;

import java.awt.*;
import java.util.IdentityHashMap;
import java.util.Map;
import javax.swing.*;

public class UIScaler {

    private static final int DESIGN_WIDTH = 1920;
    private static final int DESIGN_HEIGHT = 1080;
    private boolean remembered = false;
    private static class Original {
        Rectangle bounds;
        Font font;

        Original(Component c) {
            bounds = c.getBounds();
            font = c.getFont();
        }
    }

    private final Map<Component, Original> originals =
        new IdentityHashMap<>();

    public void scale(JPanel panel, int width, int height) {
        if (width <= 0 || height <= 0) return;  

        double scale = Math.min(
            (double) width / DESIGN_WIDTH,
            (double) height / DESIGN_HEIGHT
        );

        int scaledWidth  = (int) Math.round(DESIGN_WIDTH * scale);
        int scaledHeight = (int) Math.round(DESIGN_HEIGHT * scale);
        int offsetX = (width - scaledWidth) / 2;
        int offsetY = (height - scaledHeight) / 2;

        if (!remembered) {
            remember(panel);
            remembered = true;
        }
        panel.setBounds(offsetX, offsetY, scaledWidth, scaledHeight);
        apply(panel, scale);
        panel.revalidate();
        panel.repaint();
    }

    private void remember(Container parent) {
        for (Component c : parent.getComponents()) {
            originals.put(c, new Original(c));

            if (c instanceof Container) {
                remember((Container) c);
            }
        }
    }

    private void apply(Container parent, double scale) {
        for (Component c : parent.getComponents()) {
            Original o = originals.get(c);
            if (o == null) continue;

            Rectangle b = o.bounds;
            c.setBounds(
                (int) Math.round(b.x * scale),
                (int) Math.round(b.y * scale),
                (int) Math.round(b.width * scale),
                (int) Math.round(b.height * scale)
            );

            if (o.font != null) {
                c.setFont(o.font.deriveFont((float) (o.font.getSize2D() * scale)));
            }

            if (c instanceof Container) {     
                apply((Container) c, scale);
            }
        }
    }
}