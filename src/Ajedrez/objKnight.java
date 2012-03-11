package Ajedrez;
public class objKnight extends objChessPieces {
    
    public void objKnight() {
    }
    
    /**
    * Verifica si el caballo se puede mover hasta cierta psoción
 * 2N, 1E : Dos casillas norte, una al este.
 *
 * @param  Fila_Inicial Fial en la que inicia el caballo.
 * @param  name the location of the image, relative to the url argument
 * @return      Si es posible o no el movimiento
 * @see         Image
 */

    public boolean legalMove(int startRow, int startColumn, int desRow, int desColumn, int[][] playerMatrix) {
        
        finalDesRow = desRow;
        finalDesColumn = desColumn;
        strErrorMsg = "Caballo sole se puede mover en L";
        
        if (desRow == (startRow - 2) && desColumn == (startColumn - 1)) //2N, 1E
        {
            return true;
        } else if (desRow == (startRow - 2) && desColumn == (startColumn + 1)) //2N, 1W
        {
            return true;
        } else if (desRow == (startRow + 2) && desColumn == (startColumn - 1)) //2S, 1E
        {
            return true;
        } else if (desRow == (startRow + 2) && desColumn == (startColumn + 1)) //2S, 1W
        {
            return true;
        } else if (desRow == (startRow - 1) && desColumn == (startColumn - 2)) //1N, 2E
        {
            return true;
        } else if (desRow == (startRow - 1) && desColumn == (startColumn + 2)) //1N, 2W
        {
            return true;
        } else if (desRow == (startRow + 1) && desColumn == (startColumn - 2)) //1S, 2E
        {
            return true;
        } else if (desRow == (startRow + 1) && desColumn == (startColumn + 2)) //1S, 2W
        {
            return true;
        }
        
        return false;
        
    }
    
}
