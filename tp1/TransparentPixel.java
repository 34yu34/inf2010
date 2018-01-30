/**
 * Classe de pixel transparent
 * @author :
 * @date : 
 */

public class TransparentPixel extends AbstractPixel
{
	public int[] rgba; // donnees de l'image
	
	/**
	 * Constructeur par defaut (pixel blanc)
	 */
	TransparentPixel()
	{
		rgba = new int[4];
		rgba[0] = 255;
		rgba[1] = 255;
		rgba[2] = 255;
		rgba[3] = 255;
	}
	
	/**
	 * Assigne une valeur au pixel
	 * @param rgb: valeurs a assigner 
	 */
	TransparentPixel(int[] rgba)
	{
		this.rgba = new int[4];
		this.rgba[0]=rgba[0];
		this.rgba[1]=rgba[1];
		this.rgba[2]=rgba[2];
		this.rgba[3]=rgba[3];	
	}
	
	/**
	 * Renvoie un pixel copie de type noir et blanc
	 */
	public BWPixel toBWPixel()
	{
		int moyenne = (rgba[0]+rgba[1]+rgba[2])/3; 
		boolean pixel = moyenne >= 127;
		return new BWPixel(pixel); 
	}
	
	/**
	 * Renvoie un pixel copie de type tons de gris
	 */
	public GrayPixel toGrayPixel()
	{
		int moyenne = (rgba[0]+rgba[1]+rgba[2])/3; 
		return new GrayPixel(moyenne); 
	}
	
	/**
	 * Renvoie un pixel copie de type couleurs
	 */
	public ColorPixel toColorPixel()
	{
	int[] tableau = new int[3]; 
		tableau[0]=rgba[0]; 
		tableau[1]=rgba[1]; 
		tableau[2]=rgba[2]; 
		return new ColorPixel(tableau); 
		
	}
	
	/**
	 * Renvoie le negatif du pixel (255-pixel)
	 */
	public TransparentPixel Negative()
	{
		int[] tableau = new int[4]; 
		tableau[0]=255-rgba[0]; 
		tableau[1]=255-rgba[1]; 
		tableau[2]=255-rgba[2]; 
		tableau[3]=rgba[3]; 
		return new TransparentPixel(tableau); 
	
	}
	
	public TransparentPixel toTransparentPixel()
	{
	
		int[] tableau = new int[4]; 
		tableau[0]=rgba[0]; 
		tableau[1]=rgba[1]; 
		tableau[2]=rgba[2]; 
		tableau[3]=rgba[3]; 
		return new TransparentPixel(tableau); 
	}
	
	public void setAlpha(int alpha)
	{
		rgba[3] = alpha;
	}
	
	/**
	 * Convertit le pixel en String (sert a ecrire dans un fichier 
	 * (avec un espace supplémentaire en fin)s
	 */
	public String toString()
	{
		return  ((Integer)rgba[0]).toString() + " " + 
				((Integer)rgba[1]).toString() + " " +
				((Integer)rgba[2]).toString() + " " +
				((Integer)rgba[3]).toString() + " ";
	}
}
