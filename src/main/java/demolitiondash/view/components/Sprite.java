package demolitiondash.view.components;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;

public class Sprite {
    private final BufferedImage spriteSheet;
    private int frameCount;
    private int fps;
    private int frameWidth;
    private int frameHeight;
    private Instant startTime;
    private boolean loop;

    public Sprite(BufferedImage spriteSheet, int frameCount, int fps) {
        this.spriteSheet = spriteSheet;
        this.frameCount = frameCount;
        this.fps = fps;
        this.frameWidth = spriteSheet.getWidth() / frameCount;
        this.frameHeight = spriteSheet.getHeight();
        this.loop = true;
    }

    public Sprite(BufferedImage spriteSheet, int frameCount, int fps, boolean loop) {
        this.spriteSheet = spriteSheet;
        this.frameCount = frameCount;
        this.fps = fps;
        this.frameWidth = spriteSheet.getWidth() / frameCount;
        this.frameHeight = spriteSheet.getHeight();
        this.loop = loop;
    }

    private int getCurrentFrameIndex() {
        if(startTime == null) startTime = Instant.now();

        Instant currentTime = Instant.now();
        Duration elapsedTime = Duration.between(startTime, currentTime);
        long totalFrames = elapsedTime.toMillis() * fps / 1000;

        if(!this.loop && totalFrames >= frameCount) return frameCount - 1;

        return (int) (totalFrames % frameCount);
    }

    public BufferedImage getCurrentFrame() {
        int frameIndex = getCurrentFrameIndex();
        int frameX = frameIndex * frameWidth;
        return spriteSheet.getSubimage(frameX, 0, frameWidth, frameHeight);
    }

    public Sprite flip() {
        BufferedImage flippedSpriteSheet = new BufferedImage(
                spriteSheet.getWidth(), spriteSheet.getHeight(), BufferedImage.TYPE_INT_ARGB);

        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-frameWidth, 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        for (int i = 0; i < frameCount; i++) {
            BufferedImage frame = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
            BufferedImage flippedFrame = op.filter(frame, null);
            flippedSpriteSheet.getGraphics().drawImage(flippedFrame, i * frameWidth, 0, null);
        }

        // Return the flipped sprite
        return new Sprite(flippedSpriteSheet, frameCount, fps, loop);
    }
}

