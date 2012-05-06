package Ajedrez.Interfaz;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class ventanaMenu {
    
    private final ImageIcon fondoMenu = new ImageIcon(PanelFondo.class.getResource("/images/wood.jpg"));
    private final ImageIcon imagen1 = new ImageIcon(PanelFondo.class.getResource("/images/king.jpg"));
    
    private JFrame ventMenu;
    private ButtonGroup grupoBotonesOpcion;
    private JRadioButton opcion1, opcion2 ,opcion3, opcion4;
    private JPanel panel1, panel2, panel3;
    private TitledBorder borde1, borde2; 
    private JButton btnJugar;
    private final UIManager.LookAndFeelInfo apariencias[];

            
    ventanaMenu(){
        
        ventMenu = new JFrame();    
        ventMenu.setLayout(new BorderLayout());
        ventMenu.setSize(400, 400);
        
        apariencias = UIManager.getInstalledLookAndFeels();
        cambiarAparienciaVisual(1);
        
        PanelFondo FM = new PanelFondo(ventMenu.getHeight(),ventMenu.getWidth(),fondoMenu);
        PanelFondo imagen = new PanelFondo(200,400,imagen1);
        
        grupoBotonesOpcion = new ButtonGroup();
        opcion1 = new JRadioButton("Ps Vs Pc",false);
        opcion1.setForeground(Color.WHITE);
        opcion2 = new JRadioButton("Partida online",false);
        opcion2.setForeground(Color.WHITE);
        opcion3 = new JRadioButton("Puzzles",false);
        opcion3.setForeground(Color.WHITE);
        opcion4 = new JRadioButton("Prueba",true);
        opcion4.setForeground(Color.WHITE);
        
        grupoBotonesOpcion.add(opcion1);
        grupoBotonesOpcion.add(opcion2);
        grupoBotonesOpcion.add(opcion3);
        grupoBotonesOpcion.add(opcion4);
        
        borde1 = new TitledBorder("Menu");
        borde1.setTitleColor(Color.WHITE);
        borde2 = new TitledBorder("Opciones");
        borde2.setTitleColor(Color.WHITE);        
       
        
        //----------------------------------------------------------------------
        panel2 = new JPanel(new GridLayout(4,1));
        panel2.setSize(300, 400);
        panel2.setOpaque(false);
        panel2.setBorder(borde2);
        panel2.add(opcion1);
        panel2.add(opcion2);
        panel2.add(opcion3);
        panel2.add(opcion4);
        
        //----------------------------------------------------------------------
        panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel3.setOpaque(false);
        btnJugar = new JButton("Jugar");
        btnJugar.addActionListener(
                new ActionListener()
                {  // clase interna an�nima

                    // mostrar cuadro de di�logo de mensaje cuando el usuario seleccione Acerca de...
                    public void actionPerformed(ActionEvent evento)
                    {
                        ventMenu.dispose();
                        verificarSeleccion();
                    }
                } // fin de la clase interna an�nima
                ); // fin de la llamada a addActionListener
        
        
        
        panel3.add(btnJugar); 
        
        //----------------------------------------------------------------------
        panel1 = new JPanel(new BorderLayout());
        panel1.setOpaque(false);
        panel1.add(panel2,BorderLayout.CENTER);
        panel1.add(panel3, BorderLayout.SOUTH);
        
        //----------------------------------------------------------------------
        FM.setBorder(borde1);
        FM.add(imagen, BorderLayout.CENTER);
        FM.add(panel1, BorderLayout.EAST);
        
        ventMenu.add(FM,BorderLayout.CENTER);
        
        ventMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventMenu.setLocationRelativeTo(null);
        ventMenu.setResizable(false);
        ventMenu.setVisible(true);
    }  
    
    private void cambiarAparienciaVisual(int valor)
    {
        try // cambia la apariencia visual
        {
            // establece la apariencia visual para esta aplicación
            UIManager.setLookAndFeel(apariencias[ valor].getClassName());
            // actualiza los componentes en esta aplicación
            SwingUtilities.updateComponentTreeUI(ventMenu);
        } // fin de try
        catch (Exception excepcion)
        {
        } // fin de catch
    } // fin del método cambiarAparienciaVisual
    
    public void verificarSeleccion(){
    
        if(opcion1.isSelected()){
            
        }else if(opcion2.isSelected()){
            
        }else if(opcion3.isSelected()){
            //ventanaPuzzles aplicacion2 = new ventanaPuzzles();
            ventanaMenuPuzzles apliacion2 = new ventanaMenuPuzzles();
        }else{
            ventanaPrueba aplicacion3 = new ventanaPrueba();
        }
    }
}