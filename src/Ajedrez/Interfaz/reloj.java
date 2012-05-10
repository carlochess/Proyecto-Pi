package Ajedrez.Interfaz;

import javax.swing.*;
import javax.swing.JButton; 
import javax.swing.Timer; 
import java.awt.event.*; 
import java.awt.*;
import java.io.Serializable; 
import java.awt.Color; 

public class reloj extends JButton implements ActionListener,Serializable { 
Timer timer; 
private int segundos=0; 
private int minutos=0; 
private boolean congelado=false; 
private String min="0",seg = "0"; 
public reloj(){ 
timer=new Timer(1000,this); 
this.setText("00"+" : "+"00"); 
//this.setEnabled(false); 
this.setForeground(Color.BLACK); 
this.setBackground(Color.WHITE); 




} 

public reloj(int minuto,int segundo){ 
timer=new Timer(1000,this); 
this.setText("00"+" : "+"00"); 
estMinuto(minuto); 
estSegundo(segundo); 
this.setOpaque(false);
this.setBorderPainted(false);
} 

public void estMinuto(int min){ 
this.minutos=min; 
} 

public void estSegundo(int seg){ 
this.segundos=seg; 
} 


public void iniciar() { 
if (congelado) { 

} else { 

timer.start(); 
} 
} 
public boolean estaCorriendo(){ 
return timer.isRunning(); 
} 

public void detenerse() { 
//Stop the animating thread. 
timer.stop(); 
} 


public void reiniciar(){ 
timer.stop(); 
segundos=0; 
minutos=0; 
this.setText("00"+" : "+"00"); 

} 
public int obtMinuto(){ 
return this.minutos; 

} 
public int obtSegundo(){ 
return this.segundos; 
} 


public void actionPerformed(ActionEvent e){
    


if(segundos>0 && segundos<60){
    
    segundos--;
    if(segundos >= 0 && segundos < 10){this.setText(min+minutos+" : "+seg+segundos);}
    else{this.setText(min+minutos+" : "+segundos); }
    
    if(minutos == 0 && segundos == 0){timer.stop();}
    
}else{

    
minutos--;
segundos=59;
if (minutos<10){ 
min="0"; 
this.setText(min+minutos+" : "+segundos);
}else{ 
min=""; 
this.setText(min+minutos+" : "+segundos);
}

}

} 

 public static void main(String ... args){
        JFrame aplicacion = new JFrame();
        aplicacion.setSize(200, 100);
        aplicacion.setLayout(new BorderLayout());
        
        reloj apliReloj = new reloj();
       
         apliReloj.estMinuto(0);
         apliReloj.estSegundo(10);
         apliReloj.iniciar();
         
        aplicacion.add(apliReloj, BorderLayout.SOUTH);
        
        aplicacion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        aplicacion.setVisible(true);
    }

} 
