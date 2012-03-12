package Ajedrez;

/**
 * Esta es la clase rey donde se definen sus movimientos legales
 * hereda de objChessPieces.
 * 
*/
public class objKing extends objChessPieces {
    
	/**
	 * Constructor de la clase Rey
	*/
    public void objKing() {
    }
    /**
	 * Verifica si el movimiento realizado es válido
	 * Dada las coordenadas iniciales y finales
	 *
	 * @param  startRow  Fila inicial
	 * @param  startColumn Columna inicial
	 * @param desRow Fila a llegar
	 * @param desColumn Columna a llegar
	 * @param playerMatrix La matriz del jugador 
	 * @return Si es posible o no mover esta ficha
 	*/
    public boolean legalMove(int startRow, int startColumn, int desRow, int desColumn, int[][] playerMatrix) {
        
        finalDesRow = desRow;
        finalDesColumn = desColumn;

        // Se mueve en sentido Horario.
        // Iniciando desde las 6.00, termina en 4.00
        if (desRow == (startRow + 1) && desColumn == startColumn) // S |
                                                                  //   v
        {
            return true;
        } else if (desRow == (startRow + 1) && desColumn == (startColumn - 1)) //SW   /
            //                                                                       x
        {
            return true;
        }

        else if (desRow == startRow && desColumn == startColumn - 1) //W  <---
        {
            
            return true;
        }

        else if (desRow == (startRow - 1) && desColumn == (startColumn - 1)) //NW  x
            //                                                                         \
        {
            return true;
        } else if (desRow == (startRow - 1) && desColumn == startColumn) //N ^
            //                                                               |
        {
            return true;
        } else if (desRow == (startRow - 1) && desColumn == (startColumn + 1)) //NE  x
            //                                                                      /
        {
            return true;
        }
        else if (desRow == startRow && desColumn == (startColumn + 1)) //E  -->
        {
            
            return true;
        }
        else if (desRow == (startRow + 1) && desColumn == (startColumn + 1)) //SE  \
            //                                                                        x
        {
            return true;
        } else {
            
            strErrorMsg = "Rey solo se puede mover un espacio en diferente direcciones";
            return false;
            
        }
        
    }
    
}
