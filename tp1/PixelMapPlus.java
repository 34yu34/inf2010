import java.awt.PageAttributes.ColorType;
import java.math.*;
/**
 * Classe PixelMapPlus
 * Image de type noir et blanc, tons de gris ou couleurs
 * Peut lire et ecrire des fichiers PNM
 * Implemente les methodes de ImageOperations
 * @author :
 * @date   :
 */

public class PixelMapPlus extends PixelMap implements ImageOperations
{
  /**
   * Constructeur creant l'image a partir d'un fichier
   * @param fileName : Nom du fichier image
   */
  PixelMapPlus(String fileName)
  {
    super(fileName);
  }

  /**
   * Constructeur copie
   * @param type : type de l'image a creer (BW/Gray/Color)
   * @param image : source
   */
  PixelMapPlus(PixelMap image)
  {
    super(image);
  }

  /**
   * Constructeur copie (sert a changer de format)
   * @param type : type de l'image a creer (BW/Gray/Color)
   * @param image : source
   */
  PixelMapPlus(ImageType type, PixelMap image)
  {
    super(type, image);
  }

  /**
   * Constructeur servant a allouer la memoire de l'image
   * @param type : type d'image (BW/Gray/Color)
   * @param h : hauteur (height) de l'image
   * @param w : largeur (width) de l'image
   */
  PixelMapPlus(ImageType type, int h, int w)
  {
    super(type, h, w);
  }

  /**
   * Genere le negatif d'une image
   */
  public void negate()
  {
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        imageData[row][col] = imageData[row][col].Negative();
      }
    }
  }

  /**
   * Convertit l'image vers une image en noir et blanc
   */
  public void convertToBWImage()
  {
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        imageData[row][col] = imageData[row][col].toBWPixel();
      }
    }
    imageType = ImageType.BW;
  }

  /**
   * Convertit l'image vers un format de tons de gris
   */
  public void convertToGrayImage()
  {
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        imageData[row][col] = imageData[row][col].toGrayPixel();
      }
    }
    imageType = ImageType.Gray;
  }

  /**
   * Convertit l'image vers une image en couleurs
   */
  public void convertToColorImage()
  {
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        imageData[row][col] = imageData[row][col].toColorPixel();
      }
    }
    imageType = ImageType.Color;
  }

  public void convertToTransparentImage()
  {
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        imageData[row][col] = imageData[row][col].toTransparentPixel();
      }
    }
    imageType = ImageType.Transparent;
  }

  /**
   * Fait pivoter l'image de 10 degres autour du pixel (row,col)=(0, 0)
   * dans le sens des aiguilles d'une montre (clockWise == true)
   * ou dans le sens inverse des aiguilles d'une montre (clockWise == false).
   * Les pixels vides sont blancs.
   * @param clockWise : Direction de la rotation
   */
  public void rotate(int x, int y, double angleRadian)
  {
    AbstractPixel[][] dataCpy = imageData;
    boolean[][] modified = new boolean[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        modified[row][col] = false;
      }
    }

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        int newX = (int)(Math.cos(angleRadian) * (double)x -
                         Math.sin(angleRadian) * (double)y -
                         Math.cos(angleRadian) * (double)col +
                         Math.sin(angleRadian) * (double)row + col);

        int newY = (int)(Math.sin(angleRadian) * (double)x +
                         Math.cos(angleRadian) * (double)y -
                         Math.sin(angleRadian) * (double)col -
                         Math.cos(angleRadian) * (double)row + row);

        if (!(newX >= width || newX < 0 || newY < 0 || newY >= height)) {
          imageData[newY][newX] = dataCpy[row][col];
          modified[newY][newX] = true;
        }
      }
    }

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        if (!modified[row][col]) {
          switch (imageType) {
          case BW :
            imageData[row][col] = new BWPixel();
            break;

          case Gray :
            imageData[row][col] = new GrayPixel();
            break;

          case Color :
            imageData[row][col] = new ColorPixel();
            break;

          case Transparent :
            imageData[row][col] = new TransparentPixel();
            break;

          }
        }
      }
    }
  }

  /**
   * Modifie la longueur et la largeur de l'image
   * @param w : nouvelle largeur
   * @param h : nouvelle hauteur
   */
  public void resize(int w, int h) throws IllegalArgumentException
  {
    if (w < 0 || h < 0)
      throw new IllegalArgumentException();
    double factorW = (double)w / (double)width;
    double factorH = (double)h / (double)height;
    System.out.print(factorW);
    AbstractPixel[][] newImage = new AbstractPixel[h][w];
    for (int row = 0; row < h; row++) {
      for (int col = 0; col < w; col++) {
        int ancientX = (int)((double)col / factorW);
        ancientX = ancientX >= width ? width - 1 : ancientX;
        int ancientY = (int)((double)row / factorH);
        ancientY = ancientY >= height ? height - 1 : ancientY;
        newImage[row][col] = imageData[ancientY][ancientX];
      }
    }
    width = w;
    height = h;
    imageData = newImage;
  }

  /**
   * Insert pm dans l'image a la position row0 col0
   */
  public void inset(PixelMap pm, int row0, int col0)
  {
    for (int row = row0; row < height && row - row0 < pm.getHeight(); row++) {
      for (int col = col0; col < width && col - col0 < pm.getWidth(); col++) {
        switch (imageType) {
        case BW :
          imageData[row][col] = pm.getPixel(row - row0, col - col0).toBWPixel();
          break;

        case Gray :
          imageData[row][col] = pm.getPixel(row - row0, col - col0).toGrayPixel();
          break;

        case Color :
          imageData[row][col] = pm.getPixel(row - row0, col - col0).toColorPixel();
          break;

        case Transparent :
          imageData[row][col] = pm.getPixel(row - row0, col - col0).toTransparentPixel();
          break;
        }
      }
    }
  }

  /**
   * Decoupe l'image
   */
  public void crop(int h, int w)
  {

  }

  /**
   * Effectue une translation de l'image
   */
  public void translate(int rowOffset, int colOffset)
  {
    // compl�ter

  }

  /**
   * Effectue un zoom autour du pixel (x,y) d'un facteur zoomFactor
   * @param x : colonne autour de laquelle le zoom sera effectue
   * @param y : rangee autour de laquelle le zoom sera effectue
   * @param zoomFactor : facteur du zoom a effectuer. Doit etre superieur a 1
   */
  public void zoomIn(int x, int y, double zoomFactor) throws IllegalArgumentException
  {
    if (zoomFactor < 1.0)
      throw new IllegalArgumentException();

    int w = (int)((double)width / zoomFactor);
    int h = (int)((double)height / zoomFactor);

		int botY, topY, leftX, rightX;

    if (x + w > width) {
      rightX = width;
      leftX = rightX - w;
    }
    else if (x - w < 0) {
      leftX = 0;
      rightX = leftX + w;
    }
    else {
      rightX = x + (int)((double)w / 2);
      leftX = rightX - w;
    }

    if (y + h > height) {
      topY = height;
      botY = topY - h;
    }
    else if (y - h < 0) {
      botY = 0;
      topY = botY + h;
    }
    else {
      topY = y + (int)((double)h / 2);
      botY = topY - h;
    }

    AbstractPixel[][] dataCpy = new AbstractPixel[h][w];
    for (int row = botY; row < topY; row++) {
      for (int col = leftX; col < rightX; col++) {
        dataCpy[row - botY][col - leftX] = imageData[row][col];
      }
    }
    int tempW = width;
    int tempH = height;
    width = w;
    height = h;
    imageData = dataCpy;
    this.resize(tempW, tempH);

  }
}
