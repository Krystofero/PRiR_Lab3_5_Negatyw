package Lab3_5_Negatyw;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedImage obraz;
        File inputJPG = new File("zwykłyobraz.jpg");
        obraz = ImageIO.read(inputJPG);
        int width = obraz.getWidth();
        int height = obraz.getHeight();
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        Negatyw n1 = new Negatyw(obraz, 0, 0, halfWidth, halfHeight);
        Negatyw n2 = new Negatyw(obraz, halfWidth, 0, width, halfHeight);
        Negatyw n3 = new Negatyw(obraz, 0, halfHeight, halfWidth, height);
        Negatyw n4 = new Negatyw(obraz, halfWidth, halfHeight, width, height);

        n1.start();
        n2.start();
        n3.start();
        n4.start();

        try {
            n1.join();
            n2.join();
            n3.join();
            n4.join();
        } catch (Exception e) { }

        File ouptutJPG = new File("obrazNegatyw.jpg");
        ImageIO.write(obraz, "jpg", ouptutJPG);
    }

}
