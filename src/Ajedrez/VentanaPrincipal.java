/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ajedrez;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

public class VentanaPrincipal implements ActionListener{
    
    private JFrame ventana;
    private final UIManager.LookAndFeelInfo apariencias[];
    
    private Tablero mainChessBoard;
    //private windowChessBoard mainChessBoard;
    private objCreateAppletImage createImage;
    private String[] strRedPieces = {"bluePawn.png","blueRock.png","blueKnight.png","blueBishop.png","blueQueen.png","blueKing.png"};
    private String[] strBluePieces = {"redPawn.png","redRock.png","redKnight.png","redBishop.png","redQueen.png","redKing.png"};
    private MediaTracker mt;
    
    private JPanel panel1, panel2;
    private JTextArea area1, area2, area3;
    private JTextField texto1;
    private JButton btnEnviar;
    private JLabel etiqueta1;
    private JScrollPane barraDesp1,barraDesp2;
    private JMenuItem problema[];
    private String [] Problemas;
    
    
    private final ImageIcon fondo = new ImageIcon(PanelFondo.class.getResource("/images/wood.jpg"));
    
    VentanaPrincipal(){
         
        ventana = new JFrame();
        ventana.setLayout(new BorderLayout());
        ventana.setSize(720,650);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        
        apariencias = UIManager.getInstalledLookAndFeels();
        cambiarAparienciaVisual(1);
        
        administradorJugadas adm = new administradorJugadas();
        
        
        PanelFondo PF = new PanelFondo((ventana.getHeight()),(ventana.getWidth()),fondo);
        
        //panel de abajo
        //----------------------------------------------------------------------
        panel1 = new JPanel(new GridLayout(1,1));
        panel1.setOpaque(false);
        area1 = new JTextArea();
        area1.setEditable(false);
        texto1 = new JTextField("",51);
        btnEnviar = new JButton("Enviar");
        JPanel panelInterno1 = new JPanel();
        panelInterno1.setOpaque(false);
        panelInterno1.setLayout(new GridLayout(2,1));
        TitledBorder bordeChat = new TitledBorder("Chat");
        bordeChat.setTitleColor(Color.white);
        panelInterno1.setBorder(bordeChat);
        panelInterno1.add(area1);
        JPanel panelInterno3 = new JPanel();
        panelInterno3.setOpaque(false);
        panelInterno3.setLayout(new BorderLayout());
        panelInterno3.add(texto1,BorderLayout.CENTER);
        panelInterno3.add(btnEnviar,BorderLayout.EAST);
        panelInterno1.add(panelInterno3);
        panel1.add(panelInterno1);
        
        mainChessBoard = new Tablero();
        //mainChessBoard = new windowChessBoard();
        mainChessBoard.setVisible(false);
        createImage = new objCreateAppletImage();  
        //panel lateral
        //----------------------------------------------------------------------
        panel2 = new JPanel(new GridLayout(1,1));
        panel2.setOpaque(false);
        area2 = administradorJugadas.obtenerTexto1();
        /*area2.setBackground(Color.BLACK);
        area2.setForeground(Color.WHITE);
        area2.setEditable(false);*/
        area3 = administradorJugadas.obtenerTexto2();
        /*area2.setBackground(Color.WHITE);
        area2.setForeground(Color.BLACK);
        area3.setEditable(false);*/
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
        PF.add(panel1, BorderLayout.SOUTH);
        PF.add(panel2, BorderLayout.EAST);
        
        //mainChessBoard = new Tablero();
        
        
       
        
        try {
            
            Image[] imgRed = new Image[6];
            Image[] imgBlue = new Image[6];
            mt = new MediaTracker(ventana);

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
                    texto1.setText("");
            JOptionPane.showMessageDialog(null, "Imposible cargar las imagenes.", "ERROR", JOptionPane.WARNING_MESSAGE);
            e.printStackTrace();
            
        }
        
        //menu
        //----------------------------------------------------------------------
        
        JMenuBar barra = new JMenuBar();
        
        //----------------------------------------------------------------------
        JMenu archivo = new JMenu("Archivo");
        archivo.setMnemonic('A');
        JMenuItem juegoNuevo = new JMenuItem("Nuevo");
        
        juegoNuevo.addActionListener(

         new ActionListener() {  // clase interna an�nima

            // mostrar cuadro de di�logo de mensaje cuando el usuario seleccione Acerca de...
            public void actionPerformed( ActionEvent evento )
            {
               mainChessBoard.newGame();
               mainChessBoard.setVisible(true);
               mainChessBoard.admin.asignarTexto1("");
               mainChessBoard.admin.asignarTexto2("");
            }

         }  // fin de la clase interna an�nima

      ); // fin de la llamada a addActionListener
         
         
        JMenuItem salir = new JMenuItem("Salir");
        archivo.add(juegoNuevo);
        
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
        
        JMenu configuracion = new JMenu("Configuracion");
        configuracion.setMnemonic('C');
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
        ventana.add(PF, BorderLayout.CENTER);
        ventana.setVisible(true);
        leerProblemas();
    }
    
   /* public static void main(String ... args){
        
        VentanaPrincipal aplicacion = new VentanaPrincipal();
        
    }*/
        
    
    
    
    private void cambiarAparienciaVisual(int valor)
    {
        try // cambia la apariencia visual
        {
            // establece la apariencia visual para esta aplicación
            UIManager.setLookAndFeel(apariencias[ valor].getClassName());
            // actualiza los componentes en esta aplicación
            SwingUtilities.updateComponentTreeUI(ventana);
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
    
    private void leerProblemas()
    {
        Scanner Entrada;
        Problemas=new String [10];
        int i=0;
        try {
            Entrada=new Scanner(new File("prob.txt"));
            while(Entrada.hasNext()){
                Problemas[i]=Entrada.next();
                System.out.println(Problemas[i]);
            }
        Entrada.close();}
            catch (FileNotFoundException a){
                System.out.println("error de abierto");
            } catch(NoSuchElementException a){
                System.out.println("nos regamos");
            }
        
			/*//Entrada
			BufferedReader bufferedReader = new BufferedReader(new FileReader("prob.txt"));
			//Buscar si existe una palabra
                        Problemas=new String [10];
			String line = "";
			while((line = bufferedReader.readLine())!=null){
                             System.out.println(line);
			}
		} catch (FileNotFoundException e) {e.printStackTrace();
		} catch (IOException e) {e.printStackTrace();
		}*/
    }
        
    
    
}
