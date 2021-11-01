Programowanie równolegle i rozproszone - projekt 5 labolatorium 3 Negatyw

Projekt przedstawia tworzenie negatywu obrazu pracując na wielu wątkach, co znacząco skraca czas wykonywania się programu.

Obraz naturalny:
zwykłyobraz.jpg
![image](https://user-images.githubusercontent.com/80296885/139743848-cc7fa827-bef7-4b5e-b8ad-2a6090d3ea80.png)

Negatyw:
obrazNegatyw.jpg
![image](https://user-images.githubusercontent.com/80296885/139743864-487fba24-ccea-4bca-845a-9887dd1f44cc.png)


W main'ie wczytywany jest obraz i pobierane są wymiary pierwotnego obrazu:

        BufferedImage obraz;
        File inputJPG = new File("zwykłyobraz.jpg");
        obraz = ImageIO.read(inputJPG);
        int width = obraz.getWidth();
        int height = obraz.getHeight();
        
Tworzone i uruchamiane są 4 wątki ( każdy pracuje na swojej 1/4 obrazu):        
        
        Negatyw n1 = new Negatyw(obraz, 0, 0, halfWidth, halfHeight);
        Negatyw n2 = new Negatyw(obraz, halfWidth, 0, width, halfHeight);
        Negatyw n3 = new Negatyw(obraz, 0, halfHeight, halfWidth, height);
        Negatyw n4 = new Negatyw(obraz, halfWidth, halfHeight, width, height);

        n1.start();
        n2.start();
        n3.start();
        n4.start();
      
Program czeka aż wszystkie wątki skończą pracę:      
      
        try {
            n1.join();
            n2.join();
            n3.join();
            n4.join();
        } catch (Exception e) { }

I tworzy obraz wynikowy - negatyw:

        File ouptutJPG = new File("obrazNegatyw.jpg");
        ImageIO.write(obraz, "jpg", ouptutJPG);
        
        
Klasa Negatyw rozszerza klasę Thread. Konstruktor przyjmuje parametry początkowego punktu przedziału pracy tego wątku, parametry końcowego punktu:

    public Negatyw(BufferedImage picture, int xStart, int yStart, int xStop, int yStop){
        this.xStart = xStart;
        this.yStart = yStart;
        this.xStop = xStop;
        this.yStop = yStop;
        this.picture = picture;
    }

Piksel po pikselu dla każdego z pikseli w przedziale wątków zamieniane są kolory RGB:

    @Override
    public void run() {
        for(int i = xStart; i < xStop; i++){
            for(int j = yStart; j < yStop; j++) {
                Color c = new Color(picture.getRGB(i, j));
                int r = c.getRed();
                int g= c.getGreen();
                int b = c.getBlue();

                int R, G, B;

                R = 255 - r;
                G = 255 - g;
                B = 255 - b;

                Color newColor = new Color(R, G, B);
                picture.setRGB(i, j, newColor.getRGB());
            }
        }
    }
