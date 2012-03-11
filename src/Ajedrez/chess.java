
/*
 * chess.java
 *
 * Created on 22 de diciembre de 2006, 10:40 PM
 *
 */

package Ajedrez;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;



public class chess extends JFrame {
    
    
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        JFrame.setDefaultLookAndFeelDecorated(true);
          try {

                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

            } catch (Exception e) {
            }
        JFrame frame = new JFrame("Juego de ajedrez - http://hwongu.blogspot.com");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        
        chessGUI chessWindow = new chessGUI();
        frame.setContentPane(chessWindow.createGUI(frame));
        frame.addWindowFocusListener(chessWindow);
        
        frame.setSize(550,650);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.pack();
        
    }
    
}