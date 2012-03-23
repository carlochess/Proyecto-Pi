
package Ajedrez;
/**
 * Esta es la clase Torre, donde se definen sus movimientos legales
 * hereda de objChessPieces.
*/
public class objRock extends objChessPieces {
    /**
	 * Constructor de la clase Caballo
	*/
    public void objRock() {
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
        
        if (startRow != desRow && startColumn != desColumn) //Si se mueve en diagonal
        {
            
            strErrorMsg = "Torre solo se puede mover en horinzotal o vertical";
            return false;
            
        }
       //La reina comparte el mismo movimiento como  un alfil o una torre, para eso se valida el movimiento como 
        //si fuera de los tres
        //Al final el resultado es si la pieza se mueve en forma diagonal o en forma recta
        return axisMove(startRow, startColumn, desRow, desColumn, playerMatrix, true);
        
    }
    
}
