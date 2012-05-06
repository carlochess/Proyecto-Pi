
package Ajedrez.engine;

//Esta es la clase para manejar el movimiento de las piezas alrededor del tablero
/**
 * Crea una Abstraccion del Tablero de Ajedrez en dos Matrices, una con las posiciones dominadas por cada jugador y otra con las piezas correspondientes
*/
public class ObjCellMatrix {
    
    private int[][] playerMatrix = new int[8][8];//0 vacio, 1 jugador 1, 2 jugador 2
    private int[][] pieceMatrix = new int[8][8];//0 peon, 1 torre, 2 caballo, 3 alfil, 4 reina, 5 rey, 6 empty
    
    public void objCellMatrix() {
    }
    /**
     * Asigna las posiciones Iniciales al tablero
     */
    public void resetMatrix() {
        
        for (int row = 0; row < 8; row++) {
            
            for (int column = 0; column < 8; column++) {
                
                if (row <= 1) {
                    
                    playerMatrix[row][column] = 2;//Determina las dos filas de arriba como posesion de el jugador 2
                    
                    if (row == 1) {
                        pieceMatrix[row][column] = 0;//Coloca los Peones del jugador 2 en la fila 7
                    }
                    
                } else if (row >= 2 && row <= 5) {
                    
                    playerMatrix[row][column] = 0;//Asigna las casillas sin piezas como sin dominio
                    pieceMatrix[row][column] = 6;//Asigna las casillas sin piezas como vacias
                    
                } else {
                    
                    playerMatrix[row][column] = 1;//Determina las filas restantes como posesion del jugador 1
                    
                    if (row == 6) {
                        pieceMatrix[row][column] = 0;//Coloca los Peones del jugador 1 en la Fila 2
                    }
                    
                }
                
                if (row == 0 || row == 7) {
                    
                    if (column < 5) {
                        pieceMatrix[row][column] = (column + 1);//Coloca las demás Piezas de la columna A hasta E
                    } else {
                        pieceMatrix[row][column] = (8 - column);//Coloca las demaz piezas de F hasta H
                    }
                    
                }
                
            }
            
        }
        
    }
    /**
     * Determina que Jugador tiene una pieza en una casilla
     * @param row  Coordenada  de la Fila
     * @param column Coordenada  de la Columna
     * @return 1 si la casilla contiene una pieza del jugador 1; 2 si la casilla contiene una pieza del jugador 2 y 0 si esta vacia
     */
    public int getPlayerCell(int row, int column) {
        return playerMatrix[row][column];
    }

    /**
     * Determina que pieza esta en una casilla
     * @param row  Coordenada  de la Fila
     * @param column Coordenada  de la Columna
     * @return Entero entre 0 y 6: 0 peon, 1 torre, 2 caballo, 3 alfil, 4 reina, 5 rey, 6 empty
     */
    public int getPieceCell(int row, int column) {
        return pieceMatrix[row][column];
    }

    /**
     * Asigna a una casilla un nuevo jugador
     * @param row  Coordenada  de la Fila
     * @param column Coordenada  de la Columna
     * @param player [0,2] 0 Vacio, 1 Jugador 1, 2 Jugador 2
     */
    public void setPlayerCell(int row, int column, int player) {
        playerMatrix[row][column] = player;
    }

    /**
     * Asigna a una casilla una nueva Pieza
     * @param row  Coordenada  de la Fila
     * @param column Coordenada  de la Columna
     * @param piece Entero entre 0 y 6: 0 peon, 1 torre, 2 caballo, 3 alfil, 4 reina, 5 rey, 6 empty
     */
    public void setPieceCell(int row, int column, int piece) {
        pieceMatrix[row][column] = piece;
    }

    /**
     * Retorna la Referencia del Arreglo con la Matriz Correspondiente  a los Jugadores
     * @return Arreglo[][]
     */
    public int[][] getPlayerMatrix() {
        return playerMatrix;
    }


    /**
     * Retorna la Referencia del Arreglo con la Matriz Correspondiente a las Piezas
     * @return Arreglo[][]
     */
    public int[][] getPieceMatrix() {
        return pieceMatrix;
    }

    /**
     * Determina Jaque Mate o Cambio de Turno
     * @param currentPlayer Jugador Acual
     * @return True si hay Mate, False si no hay Mate
     */
    public boolean checkWinner(int currentPlayer) {// Cambia de Jugador y Checa el JaqueMate!
        
        int checkPlayer = 0;
        
        if (currentPlayer == 1) {
            checkPlayer = 2;
        } else {
            checkPlayer = 1;
        }
        
        for (int row = 0; row < 8; row++) {
            
            for (int column = 0; column < 8; column++) {
                
                if (playerMatrix[row][column] == checkPlayer && pieceMatrix[row][column] == 5) {//Si el rey del enemigo todav�a se queda
                    return false;
                }
                
            }
            
        }
        
        return true;
        
    }
    
}
