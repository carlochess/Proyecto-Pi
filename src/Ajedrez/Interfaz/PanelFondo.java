package Ajedrez.Interfaz;

import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelFondo extends JPanel{
    
    ImageIcon fondo;
    
    PanelFondo(int a, int b ,ImageIcon fondoEx)
    {   
        fondo = fondoEx;
        this.setLayout(new BorderLayout());
        setOpaque(false);
        setSize(a,b);
    }
    
    public void AsignarFondo(ImageIcon fnd)
    {
        fondo=fnd;
    } 
    
    @Override
    public void paintComponent( Graphics g )
    {
      g.drawImage(fondo.getImage(),0,0,this.getSize().width,this.getSize().height,null);
      super.paintComponent(g);
    }
    
    /*public static void main(String ... args){
        
        final ImageIcon fondo = new ImageIcon(PanelFondo.class.getResource("/images/wood.jpg"));
        
        JFrame apl = new JFrame();
        apl.setSize(800,600);
        PanelFondo aplicacion = new PanelFondo(800,600,fondo);
        apl.add(aplicacion);
        apl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        apl.setVisible(true);
    }*/
}
