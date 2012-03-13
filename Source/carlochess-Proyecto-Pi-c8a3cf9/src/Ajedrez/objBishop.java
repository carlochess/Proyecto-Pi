
package Ajedrez;

/**
 * Esta es la clase alfil, extiende de objChessPieces para poder usar sus metodos
 * por lo general todas las piezas van a extender de esta clase
 * 
*/

public class objBishop extends objChessPieces {
	/**
	 * Constructo objetos.
	 * <p>
	*/    

    public void objBishop() {
    }

    /**
	 * Retorna un boleano informando si es posible o no ejecutar dicho movimiento
	 * <p>
	 * El metodo verifica el movimiento en diagonal de la ficha.
	 *
	 * @param  startRow  Fila inicial
	 * @param  startColumn Columna inicial
	 * @param desRow Fila a llegar
	 * @param desColumn Columna a llegar
	 * @param playerMatrix La matriz del jugador 
	 * @return Si es posible o no mover esta ficha
	 * @see         Image
 	*/
    public boolean legalMove(int startRow, int startColumn, int desRow, int desColumn, int[][] playerMatrix) {
        
        if (startRow == desRow || startColumn == desColumn) {
            strErrorMsg = "Solo se mueven en diagonal";
            return false;
        }
        
        //se valida si el movimiento fue en forma diagonal
        return axisMove(startRow, startColumn, desRow, desColumn, playerMatrix, false);
        
    }
    
}
