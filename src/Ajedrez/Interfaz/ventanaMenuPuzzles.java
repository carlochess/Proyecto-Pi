package Ajedrez.Interfaz;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.TitledBorder;

public class ventanaMenuPuzzles {
    
    private JFrame ventMenuPuzzles;
    private JTextArea area;
    private JRadioButton radio[];
    private JButton btnGo;
    private final UIManager.LookAndFeelInfo apariencias[];
    private JPanel panel1, panel2, panel3, panel4;
    private TitledBorder titulo1, titulo2;
    private ButtonGroup grupoBotonesOpcion;
    private ventanaPuzzles ventPuzzles;
    private final ImageIcon fondo = new ImageIcon(PanelFondo.class.getResource("/images/wood.jpg"));
    
    ventanaMenuPuzzles(){
        
        
        String s =    "Para los amantes de los problemas\n"
                    + "logicos a traves del juego del\n"
                    + "ajedres, aqui tienen algunos para\n"
                    + "resolver bajo el sistema  Mate en\n"
                    + "Dos  y  Tres en donde el juegador\n"
                    + "debe  provocar  el Jaque Mate con\n"
                + "algunos movimientos.";
        
  
        ventMenuPuzzles = new JFrame ();
        ventMenuPuzzles.setLayout(new BorderLayout());
        ventMenuPuzzles.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventMenuPuzzles.setSize(410,400);
        ventMenuPuzzles.setLocationRelativeTo(null);
        ventMenuPuzzles.setResizable(false);
        
        apariencias = UIManager.getInstalledLookAndFeels();
        cambiarAparienciaVisual(1);
        
        PanelFondo PF = new PanelFondo(ventMenuPuzzles.getHeight(),
                ventMenuPuzzles.getWidth(),fondo);
        
        //----------------------------------------------------------------------
        panel4 = new JPanel(new BorderLayout());
        titulo2 = new TitledBorder("Instrucciones");
        titulo2.setTitleColor(Color.WHITE);
        panel4.setBorder(titulo2);
        panel4.setOpaque(false);
        area = new JTextArea(s);
        area.setWrapStyleWord(true);
        area.setEditable(false);

        panel4.add(area);
        
        
        //----------------------------------------------------------------------
        panel1 = new JPanel(new BorderLayout());
        panel1.setOpaque(false);
        
        //----------------------------------------------------------------------
        
        titulo1 = new TitledBorder("Puzzles");
        titulo1.setTitleColor(Color.WHITE);
        panel2 = new JPanel(new GridLayout(10,1));
        panel2.setOpaque(false);
        panel2.setBorder(titulo1);
        
        //----------------------------------------------------------------------
        panel3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel3.setOpaque(false);
        btnGo = new JButton("Go!");
        btnGo.addActionListener(
                new ActionListener()
                {  // clase interna an�nima

                    // mostrar cuadro de di�logo de mensaje cuando el usuario seleccione Acerca de...
                    public void actionPerformed(ActionEvent evento)
                    {
                        ventMenuPuzzles.dispose();
                        ventPuzzles = new ventanaPuzzles();
                        for(int i = 0; i < 10; i++){
                            if(radio[i].isSelected())
                            {
                                ventPuzzles.getMainChessBoard().newGame();
                                ventPuzzles.getMainChessBoard().setVisible(true);
                                ventPuzzles.getMainChessBoard().getChessBoard().chessPuzzles(i+1);
                            }
                        } 
                    }
                } // fin de la clase interna an�nima
                ); // fin de la llamada a addActionListener
                
        panel3.add(btnGo);
        
        panel1.add(panel2, BorderLayout.CENTER);
        panel1.add(panel3, BorderLayout.SOUTH);
        
        radio = new JRadioButton[10];
        grupoBotonesOpcion = new ButtonGroup();
        
        for(int a = 0; a < 10; a++)
        {
            radio[a] = new JRadioButton("Puzzle "+ Integer.toString(a+1),false);
            radio[a].setForeground(Color.WHITE);
            grupoBotonesOpcion.add(radio[a]);
            panel2.add(radio[a]);
        }
        
        radio[0].setSelected(true);
        
        PF.add(panel4, BorderLayout.CENTER);
        PF.add(panel1, BorderLayout.EAST);
        
        ventMenuPuzzles.add(PF,BorderLayout.CENTER);
        ventMenuPuzzles.setVisible(true);
        
    }
    
    private void cambiarAparienciaVisual(int valor)
    {
        try // cambia la apariencia visual
        {
            // establece la apariencia visual para esta aplicación
            UIManager.setLookAndFeel(apariencias[ valor].getClassName());
            // actualiza los componentes en esta aplicación
            SwingUtilities.updateComponentTreeUI(ventMenuPuzzles);
        } // fin de try
        catch (Exception excepcion)
        {
        } // fin de catch
    } // fin del método cambiarAparienciaVisual

}