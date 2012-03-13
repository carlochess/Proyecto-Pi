package Ajedrez;
public class objChessPieces {

    /**
     * Atributos */

    protected int finalDesRow = 0;
    protected int finalDesColumn = 0;
    /**
     * Mensaje de error que se mostrara cuando no se pueda ejecutar algo
     */
    protected String strErrorMsg = "";

    /**
     * Constructor de la clase objChessPieces
     */
    public objChessPieces() {
    }
    
    /** 
     * Verifica si la celda esta vacia 
     * 
     * @param newRow Nueva fila a la que se va a desplazar 
     * @param newColumn Nueva Columna a la que se va a desplazar 
     * @param playerMatrix
     * @return false si la celda contiene otra ficha, true si la celda esta vacia.
     */

    private boolean checkAxisMove(int newRow, int newColumn, int[][] playerMatrix) {
        
        if (playerMatrix[newRow][newColumn] != 0) {//Si no esta vacia
            
            strErrorMsg = "La pieza esta bloqueando el camino";
            return false;
            
        }
        
        return true;
        
    }

    /**
     * Metodo que gestiona el movimiento de las fichas dividiendolo segun su
     * tipo de movimiento
     *
     * @param startRow Fila inicial
     * @param startColumn Columna inicial
     * @param desRow Fila "Destino"
     * @param desColumn Columna "Destino"
     * @param playerMatrix
     * @param straightAxis Movimiento en forma recta.
     * @return
     */
    
    protected boolean axisMove(int startRow, int startColumn, int desRow, int desColumn, int[][] playerMatrix, boolean straightAxis) {
        
        if (straightAxis) {//Si se mueve en forma recta (torre , reina)
            
            if ((startColumn == desColumn) && (startRow != desRow)) {
                
                if (desRow < startRow) {//Moverse norte
                    
                    for (int newRow = (startRow - 1); newRow > desRow; newRow--) {
                        
                        if (!checkAxisMove(newRow, desColumn, playerMatrix)) {
                            return false;
                        }
                        
                    }
                    
                } else {
                    
                    for (int newRow = (startRow + 1); newRow < desRow; newRow++) {
                        
                        if (!checkAxisMove(newRow, desColumn, playerMatrix)) {
                            return false;
                        }
                        
                    }
                    
                }
                
            } else if ((startRow == desRow) && (startColumn != desColumn)) {
                
                if (desColumn < startColumn) {
                    
                    for (int newColumn = (startColumn - 1); newColumn > desColumn; newColumn--) {
                        
                        if (!checkAxisMove(desRow, newColumn, playerMatrix)) {
                            return false;
                        }
                        
                    }
                    
                } else {
                    
                    for (int newColumn = (startColumn + 1); newColumn < desColumn; newColumn++) {
                        
                        if (!checkAxisMove(desRow, newColumn, playerMatrix)) {
                            return false;
                        }
                        
                    }
                    
                }
                
            } else {
                
                strErrorMsg = " //Error";
                return false;
                
            }
            
        } else //Moverse en diagonal (alfil, reina)
        {
            
            strErrorMsg = "El destino es esta la misma diagonal";
            int newColumn = 0;
            
            if (desRow < startRow && desColumn < startColumn) {
                
                if ((desRow - startRow) == (desColumn - startColumn)) {
                    
                    for (int newRow = (startRow - 1); newRow > desRow; newRow--) {
                        
                        newColumn = startColumn - (startRow - newRow);
                        
                        if (!checkAxisMove(newRow, newColumn, playerMatrix)) {
                            return false;
                        }
                        
                    }
                    
                } else {
                    return false;
                }
                
            } else if (desRow < startRow && desColumn > startColumn) //si se mueve NE
            {
                
                if ((desRow - startRow) == (startColumn - desColumn)) {
                    
                    for (int newRow = (startRow - 1); newRow > desRow; newRow--) {
                        
                        newColumn = startColumn + (startRow - newRow);
                        
                        if (!checkAxisMove(newRow, newColumn, playerMatrix)) {
                            return false;
                        }
                        
                    }
                    
                } else {
                    return false;
                }
                
            } else if (desRow > startRow && desColumn < startColumn) //si se mueve SW
            {
                
                if ((startRow - desRow) == (desColumn - startColumn)) {
                    
                    for (int newRow = (startRow + 1); newRow < desRow; newRow++) {
                        
                        newColumn = startColumn - (newRow - startRow);
                        
                        if (!checkAxisMove(newRow, newColumn, playerMatrix)) {
                            return false;
                        }
                        
                    }
                    
                } else {
                    return false;
                }
                
            } else if (desRow > startRow && desColumn > startColumn) //si se mueve SE
            {
                
                if ((startRow - desRow) == (startColumn - desColumn)) {
                    
                    for (int newRow = (startRow + 1); newRow < desRow; newRow++) {
                        
                        newColumn = startColumn + (newRow - startRow);
                        
                        if (!checkAxisMove(newRow, newColumn, playerMatrix)) {
                            return false;
                        }
                        
                    }
                    
                } else {
                    return false;
                }
                
            } else //No se mueve en diagoanl
            {
                
                strErrorMsg = "Nunca vi este error :S";
                return false;
                
            }
            
        }
        
        finalDesRow = desRow;
        finalDesColumn = desColumn;
        
        return true;
        
    }


    /**
     * Getter del DesRow
     * @return finalDesRow
     */
    public int getDesRow() {
        return finalDesRow;
    }

    /**
     * Getter del DesColumn
     * @return finalDesColumn
     */
    public int getDesColumn() {
        return finalDesColumn;
    }

    /**
     * Getter del Mensaje de error
     * @return strErrorMsg
     */
    public String getErrorMsg() {
        return strErrorMsg;
    }
    
}