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
    
     // -1 enroque largo, 0 enroque , 1 enroque corto
    int enroque=0;
 
    
    public void objKing() {
    }
    public void setEnroque(int enroque)
    {
        this.enroque = enroque;
    }
    
    public int getEnroque()
    {
        return this.enroque;
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
    public boolean legalMove(int startRow, int startColumn, int desRow, int desColumn, int[][] playerMatrix, int currentPlayer, boolean statusTorre, boolean hasidomovida) 
    {
        

        boolean movimientoLegal=false;

        // Se mueve en sentido Horario.
        // Iniciando desde las 6.00, termina en 4.00
        if (desRow == (startRow + 1) && desColumn == startColumn) // S |
                                                                  //   v
        {
            
            movimientoLegal= true;
        }
        else if (desRow == (startRow + 1) && desColumn == (startColumn - 1)) //SW   /
            //                                                                       x
        {
            
            movimientoLegal= true;
        }

        else if (desRow == startRow && desColumn == startColumn - 1) //W  <---
        {
            
            movimientoLegal= true;
        }

        else if (desRow == (startRow - 1) && desColumn == (startColumn - 1)) //NW  x
            //                                                                      \
        {
            
            movimientoLegal= true;
        } 
        else if (desRow == (startRow - 1) && desColumn == startColumn) //N ^
            //                                                               |
        {
           
            movimientoLegal= true;
        }
        else if (desRow == (startRow - 1) && desColumn == (startColumn + 1)) //NE  x
            //                                                                      /
        {
            movimientoLegal= true;
        }
        else if (desRow == startRow && desColumn == (startColumn + 1)) //E  -->
        {
            
            movimientoLegal= true;
        }
        
        
        else if (desRow == (startRow + 1) && desColumn == (startColumn + 1)) //SE  \
            //                                                                      x
        {
            
            movimientoLegal= true;
        }
        

       //Movimientos para Enroque
        
        // Se mueve dos casillas a la derecha 
        else if(statusTorre)
        {
        
            if (desRow == startRow && desColumn == (startColumn + 2) )
                {
                    if (this.axisMove(startRow, startColumn, desRow, desColumn, playerMatrix, true)&&!hasidomovida)
                    {
                        enroque = 1;
                        
                        movimientoLegal= true;
                    }
                }
                //Se mueve dos casillas a la izquierda
                else if (desRow == startRow && desColumn == (startColumn - 2))
                {
                    if (this.axisMove(startRow, startColumn, desRow, desColumn, playerMatrix, true)&&!hasidomovida)
                    {
                        enroque = -1;
                        
                        movimientoLegal= true;
                    }
                }
        }
        
 
        
        else
        {
            
            strErrorMsg = "Rey solo se puede mover un espacio en diferente direcciones";
            
            movimientoLegal= false;
        }
        

        
        return movimientoLegal;
        }
}
    
    

