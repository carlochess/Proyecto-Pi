package Ajedrez;
/**
 * Esta es la clase peon, donde se definen sus movimientos legales
 * hereda de objChessPieces.
*/
public class objPawn extends objChessPieces {
    /**
	 * Constructor de la clase Caballo
	*/
    public void objPawn() {
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
    public boolean legalMove(int startRow, int startColumn, int desRow, int desColumn, int[][] playerMatrix, int currentPlayer) {
        
        boolean legalMove = true;
        int[] playerPawnStart = {6,1};
        
         //Si se mueven en direccion incorrecta= la fila en que se encuentre y las filas anteriores.
        if ((currentPlayer == 1 && desRow >= startRow) ||
           (currentPlayer == 2 && desRow <= startRow))
        {
            
            strErrorMsg = "No se puede mover de esa manera: (Es hacia adelante)";
            legalMove = false;
            
        }
    
        else if (desColumn != startColumn)
        {
            
                // Valida si el avance ha sido de una sola casilla (Sabemos que el avance es hacia la derecha o izquierda)
                if ((desColumn > startColumn && desColumn == (startColumn + 1)) || (desColumn < startColumn && desColumn == (startColumn - 1)))
                {
                     
                // Valida si el avance ha sido de una sola casilla (Sabemos que el avance es hacia abajo o hacia arriba)
                    if ((desRow == (startRow + 1) && currentPlayer == 2) || (desRow == (startRow - 1) && currentPlayer == 1)) {
                        
                    if (playerMatrix[desRow][desColumn] == 0)
                        {
                        
                        strErrorMsg = "Solo se puede mover en diagonal cuando van a cojer una pieza enemiga";
                        legalMove = false;
                        
                        }
                    
                    }
                    else {
                    
                    strErrorMsg = "No puedes mover hacia adelante o hacia arriba";
                    legalMove = false;
                    
                }
                
            }
            
                else {
                
                strErrorMsg =  "No se puede mover de esa manera, (De lado)";
                legalMove = false;
                
            }
            
        }
        else if ((currentPlayer == 1 && desRow < (startRow - 1)) || (currentPlayer == 2 && desRow > (startRow + 1))) //If moved two or more places
        {
            
            if ((currentPlayer == 1 && desRow == (startRow - 2)) || (currentPlayer == 2 && desRow == (startRow + 2))) //If moved two places
            {
                
                if (playerPawnStart[currentPlayer - 1] != startRow) 
                {
                    
                    strErrorMsg =  "No se puede mover de esa manera";
                    legalMove = false;
                    
                }
                
            } else {
                
                strErrorMsg =  "No se puede mover de esa manera";
                legalMove = false;
                
            }
            
        }
        // Dado que se ha movido estrictamente hacia adelante.
            else if ((desRow == (startRow + 1) && currentPlayer == 2) || (desRow == (startRow - 1) && currentPlayer == 1))
            {

                // Si no esta ocupada la casilla entonces...
                if (playerMatrix[desRow][desColumn] != 0)
                {
                    strErrorMsg = "No puedes mover hacia adelante estando bloqueada la casilla";
                    legalMove = false;
                }
            }
        
        if (legalMove) {
            
            finalDesRow = desRow;
            finalDesColumn = desColumn;
            
        }
        
        return legalMove;
        
    }
    
}
