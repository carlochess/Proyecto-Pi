
package Ajedrez;

import java.awt.*;
import java.util.Vector;
import javax.swing.JPanel;
/**
 * Dibuja y Pinta el Tablero de Ajedrez
*/
public class objChessBoard extends JPanel {
    
    protected objPaintInstruction currentInstruction = null;
    protected Vector vecPaintInstructions = new Vector();
    /**
	 * Constructor de la clase
	 * <p>
	 */
    public void chessBoard() {
    }
    /**
	 * Actualiza el tablero segun g
	 * @param g Objeto Graphics
	 * <p>
	 */    
    public void update(Graphics g) {
        paint(g);
    }
    
	/**
	 * Pinta el tablero
	 * @param g Objeto Graphics
	*/
    public void paint(Graphics g) {
        
        if (vecPaintInstructions.size() == 0) {
            
            g.setColor(new Color(30,10,0)); //Establece los colores al tablero
            g.fillRect(50,20,400,30);//Borde norte
            g.fillRect(20,20,30,460);//Oeste
            g.fillRect(50,450,400,30);//Sur
            g.fillRect(450,20,30,460);//este
            
            currentInstruction = new objPaintInstruction(0,0,8);
            vecPaintInstructions.addElement(currentInstruction);
            
        }
        
        g.setColor(new Color(40,40,40));//75,141,221 ( El azulito inicial)
        // Este rectangulo se usa para que los strings no queden uno encima del otro!!!
        //g.fillRect(40,510,800,50); 
        
        for (int i = 0; i < vecPaintInstructions.size(); i++) {
            
            currentInstruction = (objPaintInstruction)vecPaintInstructions.elementAt(i);
            int startRow = currentInstruction.getStartRow();
            int startColumn = currentInstruction.getStartColumn();
            int rowCells = currentInstruction.getRowCells();
            int columnCells = currentInstruction.getColumnCells();
            
            for (int row = startRow; row < (startRow + rowCells); row++) {
                
                for (int column = startColumn; column < (startColumn + columnCells); column++) {
                    drawTile(row, column, g);
                }
                
            }
            
        }
        
        drawExtra(g);
        
    }
    /**
	 * Pinta los colores de los cuadros del tablero
	 *
	*/
    private void drawTile(int row, int column, Graphics g) {
        
        if ((row + 1) % 2 == 0) {
            
            if ((column + 1) % 2 == 0) {
                g.setColor(new Color(139,69,19)); // Era negro 
            } else {
                g.setColor(new Color(222,184,135)); // Era blanco 
            }
            
        } else {
            
            if ((column + 1) % 2 == 0) {
                g.setColor(new Color(222,184,135)); // era blanco
            } else {
                g.setColor(new Color(139,69,19)); // era negro
            }
            
        }
        
        g.fillRect((50 + (column * 50)), (50 + (row * 50)), 50, 50);
        
    }
    
	/**
	 * No se que hace
	 *
	*/
    protected void drawExtra(Graphics g) {
    }
    
	/**
	 * No se que hace
	 *
	*/
    protected int findWhichTileSelected(int coor) {
        
        for (int i = 0; i < 8; i++) {
            
            if ((coor >= (50 + (i * 50))) && (coor <= (100 + (i * 50)))) {
                return i;
            }
            
        }
        
        return -1;
        
    }
    
}
