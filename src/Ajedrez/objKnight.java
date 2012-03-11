package Ajedrez;
public class objKnight extends objChessPieces {
    
    public void objKnight() {
    }
    
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