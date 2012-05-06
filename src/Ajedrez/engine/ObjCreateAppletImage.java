package Ajedrez.engine;
import java.awt.*;
import java.io.*;
import javax.swing.ImageIcon;

/**
 * Maneja las imagenes dentro del juego, debido a que cada ficha es una imagen.
 * @author danielflj
 */

public class ObjCreateAppletImage {
    
    private String strErrorMsg = "";
    /**
     * Get de la imagen.
     * @param parentClass
     * @param path
     * @param fileSize
     * @return
     */
    public Image getImage(Object parentClass, String path, int fileSize) {
        
        byte buff[] = createImageArray(parentClass, path, fileSize);
        return Toolkit.getDefaultToolkit().createImage(buff);
        
    }
    /**
     * Asigna a buff un arreglo de imagenes, y retorna un objeto "new" de  una
     * funcion con buff como parametro.
     * @param parentClass
     * @param path
     * @param description
     * @param fileSize
     * @return ImageIcon(Toolkit.getDefaultToolkit().createImage(buff), description)
     */

    public ImageIcon getImageIcon(Object parentClass, String path, String description, int fileSize) {
        
        byte buff[] = createImageArray(parentClass, path, fileSize);
        return new ImageIcon(Toolkit.getDefaultToolkit().createImage(buff), description);
        
    }
    /**
     * Esta vacia.
     */
    public  ObjCreateAppletImage() {
    }
    /**
     * Get del mensaje de error
     * @return strErrorMsg
     */
    public String getErrorMsg() {
        return strErrorMsg;
    }
    /***
     * Crea un arreglo de imagenes, controla alguno errores.
     * @param parentClass
     * @param path
     * @param fileSize
     * @return en su defecto retornara null
     */
    private byte[] createImageArray(Object parentClass, String path, int fileSize) {
        
        int count = 0;
        
        BufferedInputStream imgStream = new BufferedInputStream(this.getClass().getResourceAsStream(path));
        
        if (imgStream != null) {
            
            byte buff[] = new byte[fileSize];
            
            try {
                count = imgStream.read(buff);
            } catch (IOException e) {
                strErrorMsg = "Error de lecutra del archivo: " + path;
                System.out.println(strErrorMsg + " " + e.getMessage());
            }
            
            try {
                imgStream.close();
            } catch (IOException e) {
                strErrorMsg = "Error al cerrar el archivo: " + path;
                System.out.println(strErrorMsg + " " + e.getMessage());
            }
            
            if (count <= 0) {
                
                strErrorMsg = "Error del archivo: " + path;

                return null;
                
            }
            
            return buff;
            
        } else {
            
            strErrorMsg = "No se puede encontrar el archivo: " + path;
            System.out.println(strErrorMsg + " " );
            return null;
            
        }
        
    }
    
}