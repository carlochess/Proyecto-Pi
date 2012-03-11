package Ajedrez;
import java.awt.*;
import java.io.*;
import javax.swing.ImageIcon;
//Solo sirve para poder manejar el ingreso de las imagenes dentro del juego
//Dado que cada pieza es una imagen
public class objCreateAppletImage {
    
    private String strErrorMsg = "";
    
    public Image getImage(Object parentClass, String path, int fileSize) {
        
        byte buff[] = createImageArray(parentClass, path, fileSize);
        return Toolkit.getDefaultToolkit().createImage(buff);
        
    }
    
    public ImageIcon getImageIcon(Object parentClass, String path, String description, int fileSize) {
        
        byte buff[] = createImageArray(parentClass, path, fileSize);
        return new ImageIcon(Toolkit.getDefaultToolkit().createImage(buff), description);
        
    }
    
    public void objCreateAppletImage() {
    }
    
    public String getErrorMsg() {
        return strErrorMsg;
    }
    
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