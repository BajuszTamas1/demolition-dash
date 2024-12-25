package demolitiondash.view.components;

import demolitiondash.util.Coordinate;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Drawable {
    BufferedImage getImage();
    Coordinate getLocation();
}
