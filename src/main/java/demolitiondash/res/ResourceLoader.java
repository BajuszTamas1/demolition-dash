package demolitiondash.res;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;

public class ResourceLoader {
    public static InputStream loadResource(String resName){
        return ResourceLoader.class.getClassLoader().getResourceAsStream(resName);
    }
    
    public static Image loadImage(String resName){
        Image res = null;
        try{
            URL url = ResourceLoader.class.getClassLoader().getResource(resName);
            res = ImageIO.read(url);
        } catch (Exception e){
            System.out.println("Error while loading image " + e.fillInStackTrace());
        }
        return res;
    }
}
