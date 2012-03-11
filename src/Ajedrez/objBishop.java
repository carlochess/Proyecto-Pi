
package Ajedrez;

/*Esta es la pieza del alfil, extiende de objChessPieces para poder usar sus metodos
 por lo general todas las piezas van a extender de esta clase*/
public class objBishop extends objChessPieces {
    
    public void objBishop() {
    }
    //Movimiento legal 
    public boolean legalMove(int startRow, int startColumn, int desRow, int desColumn, int[][] playerMatrix) {
        
        if (startRow == desRow || startColumn == desColumn) {
            
            strErrorMsg = "Solo se mueven en diagonal";
            return false;
            
        }
        
        //se valida si el movimiento fue en forma diagonal
        return axisMove(startRow, startColumn, desRow, desColumn, playerMatrix, false);
        
    }
    
}