package Ajedrez;
/**
 * Esta es la clase Dama, donde se definen sus movimientos legales
 * hereda de objChessPieces.
*/
public class objQueen extends objChessPieces {
    /**
	 * Constructor de la clase Caballo
	*/
    public void objQueen() {
    }
     /**
	 * Dada las coordenadas iniciales y finales,
	 * verifica si el movimiento realizado es válido
	 *
	 * @param  startRow  Fila inicial
	 * @param  startColumn Columna inicial
	 * @param desRow Fila a llegar
	 * @param desColumn Columna a llegar
	 * @param playerMatrix La matriz del jugador 
	 * @return Si es posible o no mover esta ficha
 	*/
    public boolean legalMove(int startRow, int startColumn, int desRow, int desColumn, int[][] playerMatrix) {
        
        boolean axis = true;
        
        if (startRow == desRow ^ startColumn == desColumn) {
            axis = true;
        } else if (startRow != desRow && startColumn != desColumn) {
            axis = false; //MOver diagonal
        } else {
            
            strErrorMsg = "Reina se puede mover en cualquier direccion";
            return false;
            
        }
        
        return axisMove(startRow, startColumn, desRow, desColumn, playerMatrix, axis);
        
    }
}
