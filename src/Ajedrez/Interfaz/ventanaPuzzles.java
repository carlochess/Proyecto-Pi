package Ajedrez.Interfaz;

import Ajedrez.engine.AdminJugadas;
import Ajedrez.engine.ObjCreateAppletImage;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class ventanaPuzzles implements ActionListener{
    
    private JFrame ventPuzzles;
    private final UIManager.LookAndFeelInfo apariencias[];
    
    private Tablero mainChessBoard;
    private ObjCreateAppletImage createImage;
    private String[] strRedPieces = {"bluePawn.png","blueRock.png","blueKnight.png","blueBishop.png","blueQueen.png","blueKing.png"};
    private String[] strBluePieces = {"redPawn.png","redRock.png","redKnight.png","redBishop.png","redQueen.png","redKing.png"};
    private MediaTracker mt;
    
    private JPanel panel2;
    private JTextArea area2, area3;
    private JLabel etiqueta1;
    private JScrollPane barraDesp1,barraDesp2;
    private JMenuItem problema[];
    private String [] Problemas;
    
    
    private final ImageIcon fondo = new ImageIcon(PanelFondo.class.getResource("/images/wood.jpg"));
    
    ventanaPuzzles(){
         
        ventPuzzles = new JFrame();
        ventPuzzles.setLayout(new BorderLayout());
        ventPuzzles.setSize(720,580);
        ventPuzzles.setResizable(false);
        ventPuzzles.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventPuzzles.setLocationRelativeTo(null);
        
        apariencias = UIManager.getInstalledLookAndFeels();
        cambiarAparienciaVisual(1);
        
        AdminJugadas adm = new AdminJugadas();
        
        
        PanelFondo PF = new PanelFondo((ventPuzzles.getHeight()),(ventPuzzles.getWidth()),fondo);

        
        mainChessBoard = new Tablero();
        mainChessBoard.setVisible(false);
        createImage = new ObjCreateAppletImage(); 
        
        //panel lateral
        //----------------------------------------------------------------------
        panel2 = new JPanel(new GridLayout(1,1));
        panel2.setOpaque(false);
        
        area2 = AdminJugadas.obtenerTexto1();
        area3 = AdminJugadas.obtenerTexto2();
        
        etiqueta1 = new JLabel("|  Jugador1  |  Jugador2  |");
        etiqueta1.setForeground(Color.white);
        JPanel panelInterno2 = new JPanel();
        panelInterno2.setOpaque(false);
        panelInterno2.setLayout(new BorderLayout());
        TitledBorder bordeMovimientos = new TitledBorder("Movimientos");
        bordeMovimientos.setTitleColor(Color.white);
        panelInterno2.setBorder(bordeMovimientos);
        JPanel panelInterno4 = new JPanel(new GridLayout(1,2));
        panelInterno4.setOpaque(false);
        barraDesp1 = new JScrollPane(mainChessBoard.admin.obtenerTexto1());
        barraDesp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelInterno4.add(barraDesp1);
        barraDesp2 = new JScrollPane(mainChessBoard.admin.obtenerTexto2());
        barraDesp2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelInterno4.add(barraDesp2);
        panelInterno2.add(panelInterno4);
        panelInterno2.add(etiqueta1, BorderLayout.NORTH);
        panel2.add(panelInterno2);
        
        TitledBorder bordeAjedrez = new TitledBorder("Ajedrez");
        bordeAjedrez.setTitleColor(Color.WHITE);
        PF.setBorder(bordeAjedrez);
        PF.add(panel2, BorderLayout.EAST);
       
        
        try {
            
            Image[] imgRed = new Image[6];
            Image[] imgBlue = new Image[6];
            mt = new MediaTracker(ventPuzzles);

            // Añade ficha por ficha en el arreglo de Fichas-Jugadores
            for (int i = 0; i < 6; i++) {
                imgRed[i] = createImage.getImage(this, "/images/" + strRedPieces[i], 5000);
                imgBlue[i] = createImage.getImage(this,"/images/" + strBluePieces[i], 5000);
                mt.addImage(imgRed[i], 0);
                mt.addImage(imgBlue[i], 0);
            }
            
            try {
                mt.waitForID(0);
            } 
            catch (InterruptedException e) {
            }
            
            mainChessBoard.setupImages(imgRed, imgBlue);
            } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Imposible cargar las imagenes.", "ERROR", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
            
        }
        
        //menu
        //----------------------------------------------------------------------
        
        JMenuBar barra = new JMenuBar();
        
        //----------------------------------------------------------------------
        JMenu archivo = new JMenu("Archivo");
        archivo.setMnemonic('A');
        
        JMenuItem atras = new JMenuItem("Atras");
        archivo.add(atras);
        
        atras.addActionListener(
                new ActionListener()
                {

                    @Override
                    public void actionPerformed(ActionEvent evento)
                    {
                        ventPuzzles.dispose();
                        ventanaMenu aplicacionMenu = new ventanaMenu();
                    }
                });
        
        JMenuItem salir = new JMenuItem("Salir");
        
        salir.addActionListener(
                new ActionListener()
                {

                    @Override
                    public void actionPerformed(ActionEvent evento)
                    {
                        System.exit(0);
                    }
                });
        archivo.add(salir);
        
        //----------------------------------------------------------------------
        
        JMenu configuracion = new JMenu("Puzzles");
        configuracion.setMnemonic('P');
        problema = new JMenuItem[10];
        for(int a=0;a<10;a++)
        {
        problema[a]=new JMenuItem("Problema "+(a+1));
        problema[a].addActionListener(this);
        configuracion.add(problema[a]);
        }        
        //----------------------------------------------------------------------
        
        JMenu ayuda = new JMenu("Ayuda");
        ayuda.setMnemonic('y');
        JMenuItem acercaDe = new JMenuItem("Acerca de");
        ayuda.add(acercaDe);
        
        //----------------------------------------------------------------------
        
        barra.add(archivo);
        barra.add(configuracion);
        barra.add(ayuda);
        
        
        PF.add(mainChessBoard, BorderLayout.CENTER);
        PF.add(barra, BorderLayout.NORTH);
        ventPuzzles.add(PF, BorderLayout.CENTER);
        ventPuzzles.setVisible(true);
    }

    
    
    private void cambiarAparienciaVisual(int valor)
    {
        try // cambia la apariencia visual
        {
            // establece la apariencia visual para esta aplicación
            UIManager.setLookAndFeel(apariencias[ valor].getClassName());
            // actualiza los componentes en esta aplicación
            SwingUtilities.updateComponentTreeUI(ventPuzzles);
        } // fin de try
        catch (Exception excepcion)
        {
        } // fin de catch
    } // fin del método cambiarAparienciaVisual

    public void actionPerformed(ActionEvent ae) {
        for(int i = 0; i < 10; i++){
           if(ae.getSource()== problema[i]) {
                JOptionPane.showMessageDialog(null, "Problema " +(i+1));
                mainChessBoard.newGame();
                mainChessBoard.setVisible(true);
                mainChessBoard.getChessBoard().chessPuzzles(i+1);
           }
        }
    }
  
}