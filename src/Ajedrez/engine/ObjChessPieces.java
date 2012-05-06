package Ajedrez.engine;
public class ObjChessPieces {

    /**
     * Atributos */

    
    /**
     * Mensaje de error que se mostrara cuando no se pueda ejecutar algo
     */
    protected String strErrorMsg = "";

    /**
     * Constructor de la clase ObjChessPieces
     */
    public ObjChessPieces() {
    }

    public boolean validador(boolean straightAxis, int startColumn, int desColumn, int startRow, int desRow, int[][] playerMatrix) {
        if (straightAxis) {
            //Si se mueve en forma recta (torre , reina)
            if ((startColumn == desColumn) && (startRow != desRow)) {
                if (desRow < startRow) {
                    //Moverse norte
                    for (int newRow = (startRow - 1); newRow > desRow; newRow--) {
                        if (!checkAxisMove(newRow, desColumn, playerMatrix)) {
                            return true;
                        }
                    }
                } else {
                    for (int newRow = (startRow + 1); newRow < desRow; newRow++) {
                        if (!checkAxisMove(newRow, desColumn, playerMatrix)) {
                            return true;
                        }
                    }
                }
            } else if ((startRow == desRow) && (startColumn != desColumn)) {
                if (desColumn < startColumn) {
                    for (int newColumn = (startColumn - 1); newColumn > desColumn; newColumn--) {
                        if (!checkAxisMove(desRow, newColumn, playerMatrix)) {
                            return true;
                        }
                    }
                } else {
                    for (int newColumn = (startColumn + 1); newColumn < desColumn; newColumn++) {
                        if (!checkAxisMove(desRow, newColumn, playerMatrix)) {
                            return true;
                        }
                    }
                }
            } else {
                strErrorMsg = " //Error";
                return true;
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
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else if (desRow < startRow && desColumn > startColumn) {
                if ((desRow - startRow) == (startColumn - desColumn)) {
                    for (int newRow = (startRow - 1); newRow > desRow; newRow--) {
                        newColumn = startColumn + (startRow - newRow);
                        if (!checkAxisMove(newRow, newColumn, playerMatrix)) {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else if (desRow > startRow && desColumn < startColumn) {
                if ((startRow - desRow) == (desColumn - startColumn)) {
                    for (int newRow = (startRow + 1); newRow < desRow; newRow++) {
                        newColumn = startColumn - (newRow - startRow);
                        if (!checkAxisMove(newRow, newColumn, playerMatrix)) {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else if (desRow > startRow && desColumn > startColumn) {
                if ((startRow - desRow) == (startColumn - desColumn)) {
                    for (int newRow = (startRow + 1); newRow < desRow; newRow++) {
                        newColumn = startColumn + (newRow - startRow);
                        if (!checkAxisMove(newRow, newColumn, playerMatrix)) {
                            return true;
                        }
                    }
                } else {
                    return true;
                }
            } else //No se mueve en diagoanl
            {
                strErrorMsg = "Nunca vi este error :S";
                return true;
            }
        }
        return false;
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
            
            strErrorMsg = "La pieeza esta bloqueando el camino";
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
        
        if (validador(straightAxis, startColumn, desColumn, startRow, desRow, playerMatrix)) return false;
        /*
        finalDesRow = desRow;
        finalDesColumn = desColumn;
        */
        return true;
        
    }


   

    /**
     * Getter del Mensaje de error
     * @return strErrorMsg
     */
    public String getErrorMsg() {
        return strErrorMsg;
    }
    
}