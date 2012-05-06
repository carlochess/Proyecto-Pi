
package Ajedrez.Interfaz;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class ventanaMenuPuzzles {
    
    private JFrame ventMenuPuzzles;
    private JTextArea Area;
    private JRadioButton radio[];
    
    private final ImageIcon fondo = new ImageIcon(PanelFondo.class.getResource("/images/wood.jpg"));
    
    ventanaMenuPuzzles(){
        
        ventMenuPuzzles = new JFrame ();
        ventMenuPuzzles.setLayout(new BorderLayout());
        ventMenuPuzzles.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventMenuPuzzles.setSize(500,600);
        
        
        ventMenuPuzzles.setVisible(true);
        
    }
}
