package demolitiondash.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;

public class ImageUtils {

    /**
     * Applies a glow effect to the given image.
     *
     * @param image  The image to apply the glow effect to.
     * @param amount The amount of glow to apply.
     * @return The image with the glow effect applied.
     */
    public static BufferedImage applyGlow(BufferedImage image, double amount) {
        BufferedImage glowingImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = glowingImage.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        float blurAmount = (float) (amount * 5); // Adjust blur amount according to the specified glow amount
        BufferedImage blurredImage = applyGaussianBlur(glowingImage, blurAmount);

        BufferedImage brightenedImage = adjustBrightness(blurredImage, amount);

        return brightenedImage;
    }

    /**
     * Adjusts the brightness of the given image.
     *
     * @param image  The image to adjust the brightness of.
     * @param amount The amount by which to adjust the brightness.
     * @return The adjusted image.
     */
    public static BufferedImage adjustBrightness(BufferedImage image, double amount) {
        RescaleOp rescaleOp = new RescaleOp((float) (1 + amount), 0, null);
        return rescaleOp.filter(image, null);
    }

    /**
     * Applies a Gaussian blur to the given image.
     *
     * @param image  The image to apply the blur to.
     * @param radius The radius of the blur.
     * @return The blurred image.
     */
    public static BufferedImage applyGaussianBlur(BufferedImage image, float radius) {
        int size = (int) (radius * 3) * 2 + 1;
        float[] data = new float[size * size];
        float sigma = radius / 3.0f;
        float twoSigmaSquare = 2.0f * sigma * sigma;
        float sigmaRoot = (float) Math.sqrt(twoSigmaSquare * Math.PI);
        float total = 0.0f;

        for (int y = -size / 2, i = 0; y <= size / 2; y++) {
            for (int x = -size / 2; x <= size / 2; x++, i++) {
                float distance = (x * x + y * y) / twoSigmaSquare;
                data[i] = (float) Math.exp(-distance) / sigmaRoot;
                total += data[i];
            }
        }

        for (int i = 0; i < data.length; i++) {
            data[i] /= total;
        }

        Kernel kernel = new Kernel(size, size, data);
        ConvolveOp convolveOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return convolveOp.filter(image, null);
    }

    /**
     * Applies opacity to the given image.
     *
     * @param image   The image to apply opacity to.
     * @param opacity The opacity value.
     * @return The image with opacity applied.
     */
    public static BufferedImage applyOpacity(BufferedImage image, float opacity) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        Graphics2D g2d = result.createGraphics();

        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        g2d.setComposite(alphaComposite);

        g2d.drawImage(image, 0, 0, null);

        g2d.dispose();

        return result;
    }

    /**
     * Adjusts the hue of the given image.
     *
     * @param image    The image to adjust the hue of.
     * @param hueShift The amount by which to shift the hue.
     * @return The adjusted image.
     */
    public static BufferedImage adjustHue(BufferedImage image, double hueShift) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage adjustedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                if (alpha == 0) {
                    // Preserve transparency for fully transparent pixels
                    adjustedImage.setRGB(x, y, 0);
                } else {
                    float[] hsb = Color.RGBtoHSB((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, null);
                    hsb[0] = (hsb[0] + (float) hueShift) % 1.0f;
                    int adjustedRGB = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
                    adjustedRGB = (adjustedRGB & 0xFFFFFF) | (alpha << 24); // Combine RGB values with alpha channel
                    adjustedImage.setRGB(x, y, adjustedRGB);
                }
            }
        }
        return adjustedImage;
    }

    /**
     * Converts an Image object to a BufferedImage.
     *
     * @param image The Image object to convert.
     * @return The BufferedImage.
     */
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        BufferedImage bufferedImage = new BufferedImage(
                image.getWidth(null),
                image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bufferedImage;
    }
}
