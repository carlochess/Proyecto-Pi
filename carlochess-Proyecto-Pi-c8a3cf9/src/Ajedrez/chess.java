
/*
 * chess.java
 *
 * Created on 22 de diciembre de 2006, 10:40 PM
 *
 */

// Define el paquete que contiene todas las clases
package Ajedrez;

// Importa las bibliotecas para la parte gráfica
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase principal, crea unicamente el objeto ChessGUI que administra las demas clases
*/
public class chess extends JFrame {
    
 
    /**
	 * Funcion principal, crea unicamente el objeto ChessGUI que administra las demas clases
	 * @param args Aquellos parametros por consola
 	 * @return void
	*/public static void main(String[] args) {
        
        JFrame.setDefaultLookAndFeelDecorated(true);
          try
	{
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
         }
		 catch (Exception e)
	 {
         }
        JFrame frame = new JFrame("Juego de ajedrez - http://hwongu.blogspot.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        // Define y declara un objeto tipo chessGUI. [Ver chessGUI]
        chessGUI chessWindow = new chessGUI();
        frame.setContentPane(chessWindow.createGUI(frame));
        frame.addWindowFocusListener(chessWindow);
        
        frame.setSize(550,650);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
        
    }
    
}
