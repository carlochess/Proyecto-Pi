package Ajedrez;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.Transparency;
import java.lang.reflect.Method;

public class ventanaInicio
{

    private final ImageIcon fondo = new ImageIcon(PanelFondo.class.getResource("/images/inicioVentana.jpg"));
    private final ImageIcon icon = new ImageIcon(PanelFondo.class.getResource("/images/btnEntrar.png"));
    private JProgressBar barraProgreso;
    private JPanel panel1;
    private JButton btnEntrar;
    private final JFrame ventana;
    private PanelFondo PF;

    ventanaInicio()
    {

        ventana = new JFrame();
        ventana.setLayout(new BorderLayout());
        ventana.setSize(500, 400);
        ventana.setBackground(Color.BLACK);
        ventana.setUndecorated(true);
        ventana.setLocationRelativeTo(null);

        try
        {

            System.setProperty("sun.java2d.noddraw", "true");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e)
        {
        }

        PF = new PanelFondo(ventana.getHeight(), ventana.getWidth(), fondo);
        panel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel1.setOpaque(false);

        btnEntrar = new JButton("");
        btnEntrar.setEnabled(false);
        btnEntrar.setIcon(icon);
        btnEntrar.setOpaque(false);
        btnEntrar.setContentAreaFilled(false);
        btnEntrar.setBorderPainted(false);

        btnEntrar.addActionListener(
                new ActionListener()
                {  // clase interna an�nima

                    // mostrar cuadro de di�logo de mensaje cuando el usuario seleccione Acerca de...
                    public void actionPerformed(ActionEvent evento)
                    {
                        ventana.dispose();
                        VentanaPrincipal aplicacion = new VentanaPrincipal();
                    }
                } // fin de la clase interna an�nima
                ); // fin de la llamada a addActionListener


        PF.add(panel1, BorderLayout.SOUTH);

        barraProgreso = new JProgressBar(0, 500);
        barraProgreso.setBorderPainted(false);
        barraProgreso.setSize(400, 50);
        barraProgreso.setValue(0);
        barraProgreso.setStringPainted(true);
        panel1.add(barraProgreso);
        panel1.add(btnEntrar);

        ventana.add(PF, BorderLayout.CENTER);
        ventana.setVisible(true);

    }

    public void iterate()
    {
        int num = 0;
        while (num <= 500)
        {

            barraProgreso.setValue(num);
            try
            {
                Thread.sleep(250);
            } catch (InterruptedException e)
            {
            }
            num += 100;

            if (num == 500)
            {
                panel1.remove(barraProgreso);
                btnEntrar.setEnabled(true);
                ventana.repaint();
            }
        }
    }

    public static void main(String... args)
    {
        ventanaInicio aplicacion = new ventanaInicio();
        aplicacion.iterate();
    }
}
