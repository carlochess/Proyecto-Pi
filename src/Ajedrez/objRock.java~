
package Ajedrez;
public class objRock extends objChessPieces {
    
    public void objRock() {
    }
    
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