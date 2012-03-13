
package Ajedrez;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.JDialog;

public class windowChessBoard extends objChessBoard implements MouseListener, MouseMotionListener {
    
    private final int refreshRate = 5;
    
    private Image[][] imgPlayer = new Image[2][6];
    private String[] strPlayerName = {"Henry Wong", "TU --> MI CONTRICANTE"};
    private String strStatusMsg = "";
    private objCellMatrix cellMatrix = new objCellMatrix();
    private int currentPlayer = 1, startRow = 0, startColumn = 0, pieceBeingDragged = 0;
    private int startingX = 0, startingY = 0, currentX = 0, currentY = 0, refreshCounter = 0;
    private boolean firstTime = true, hasWon = false, isDragging = false;
    
    private objPawn pawnObject = new objPawn();
    private objRock rockObject = new objRock();
    private objKnight knightObject = new objKnight();
    private objBishop bishopObject = new objBishop();
    private objQueen queenObject = new objQueen();
    private objKing kingObject = new objKing();
    /**
     * Constructor Incluye los Listeners al Canvas
     */
    public windowChessBoard() {
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        
    }
<<<<<<< HEAD
    //Siver para cambiar los mensajes que vez en el tablero
    /**
     * Cambia los mensajes que aparecen debajo del Tablero
     * @return Estado Retorna el estado del juego, sea terminado, sin comenzar o el turno Correspondiente
     */
=======
    //Sirve para cambiar los mensajes que vez en el tablero
>>>>>>> PrimeraModificación
    private String getPlayerMsg() {
        
        if (hasWon) {
            return "Felicitaciones " + strPlayerName[currentPlayer - 1] + ", tu eres el ganadaror!";
        } else if (firstTime) {
            return "" + strPlayerName[0] + " tu eres los rojos, " + strPlayerName[1] + " tu eres los blancos. Presiona nuevo juego para empezar";
        } else {
            return "" + strPlayerName[currentPlayer - 1] + " mueve";
        }
        
    }
    
    //Comiezan el juego de nuevo
    /**
     * Inicia de Nuevo el Juego
     */
    private void resetBoard() {
        
        hasWon = false;
        currentPlayer = 1;
        strStatusMsg = getPlayerMsg();
        cellMatrix.resetMatrix();
        repaint();
        
    }

    /**
     * Carga las Imagenes al Arreglo imgPlayer
     * @param imgRed Arreglo de Imagenes con las Imagenes Rojas
     * @param imgBlue Arreglo de Imagenes con las Imagenes Azules
     */
    public void setupImages(Image[] imgRed, Image[] imgBlue) {
        
        imgPlayer[0] = imgRed;
        imgPlayer[1] = imgBlue;
        resetBoard();
        
    }
    
    //Estabke los nombres a los jugadores
    /**
     * Asigna los nombres de los TextBox a Nombre de Jugadores
     * @param strPlayer1Name Cadena con el TextBox Correspondiente a Jugador 1
     * @param strPlayer2Name Cadena con el TextBox Correspondiente a Jugador 2
     */
    public void setNames(String strPlayer1Name, String strPlayer2Name) {
        
        strPlayerName[0] = strPlayer1Name;
        strPlayerName[1] = strPlayer2Name;
        strStatusMsg = getPlayerMsg();
        repaint();
        
    }

    /**
     * Dibuja la Pieza en la Casilla Correspondiente
     * @param g
     */
    protected void drawExtra(Graphics g) {
        
        for (int i = 0; i < vecPaintInstructions.size(); i++) {
            
            currentInstruction = (objPaintInstruction)vecPaintInstructions.elementAt(i);
            int paintStartRow = currentInstruction.getStartRow();
            int paintStartColumn = currentInstruction.getStartColumn();
            int rowCells = currentInstruction.getRowCells();
            int columnCells = currentInstruction.getColumnCells();
            
            for (int row = paintStartRow; row < (paintStartRow + rowCells); row++) {
                
                for (int column = paintStartColumn; column < (paintStartColumn + columnCells); column++) {
                    
                    int playerCell = cellMatrix.getPlayerCell(row, column);
                    int pieceCell = cellMatrix.getPieceCell(row, column);
                    
                    if (playerCell != 0) {
                        
                        try {
                            g.drawImage(imgPlayer[playerCell - 1][pieceCell], ((column + 1) * 50), ((row + 1) * 50), this);
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                        
                    }
                    
                }
                
            }
            
        }
        
        if (isDragging) {
            g.drawImage(imgPlayer[currentPlayer - 1][pieceBeingDragged], (currentX - 25), (currentY - 25), this);
        }
        
        g.setColor(new Color(0,0,0));
        g.drawString(strStatusMsg, 50, 475);
        
        vecPaintInstructions.clear();
    }
    
    
    //Nuevo juego
    /**
     * Crea un Nuevo Juego
     */
    public void newGame() {
        
        firstTime = false;
        resetBoard();
        
    }
    
    //Valida los movimientos
    /**
     * Valida el Movimiento de la pieza Seleccionada
     * @param desRow Coordenadas de la Fila
     * @param desColumn Coordenadas de la Columna
     */
        private void checkMove(int desRow, int desColumn) {

        boolean legalMove = false, enroque = false;

        if (cellMatrix.getPlayerCell(desRow,desColumn) == currentPlayer) {
            strStatusMsg = "No puedes mover";
        }
        else
        {

            switch (pieceBeingDragged) {
                //Esta es la parte mas importante aca es donde se ve si realmente las piezas se mueven en la direccion correcta
                case 0: legalMove = pawnObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix(), currentPlayer);
                break;
                case 1: legalMove = rockObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix());
                break;
                case 2: legalMove = knightObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix());
                break;
                case 3: legalMove = bishopObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix());
                break;
                case 4: legalMove = queenObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix());
                break;
                case 5:
                    legalMove = kingObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix());
                    enroque = false ;
                    break;

            }

        }
        // Ha sido una movida adecuada?
        if (legalMove) {

            int newDesRow = 0;
            int newDesColumn = 0;

            // pieceBeingDragged
            switch (pieceBeingDragged) {

                case 0: newDesRow = pawnObject.getDesRow();
                newDesColumn = pawnObject.getDesColumn();
                break;
                case 1: newDesRow = rockObject.getDesRow();
                newDesColumn = rockObject.getDesColumn();
                break;
                case 2: newDesRow = knightObject.getDesRow();
                newDesColumn = knightObject.getDesColumn();
                break;
                case 3: newDesRow = bishopObject.getDesRow();
                newDesColumn = bishopObject.getDesColumn();
                break;
                case 4: newDesRow = queenObject.getDesRow();
                newDesColumn = queenObject.getDesColumn();
                break;
                case 5:
                    if (enroque)
                    {

                    }
                    newDesRow = kingObject.getDesRow();
                    newDesColumn = kingObject.getDesColumn();

                break;

            }

            cellMatrix.setPlayerCell(newDesRow, newDesColumn, currentPlayer);

            //Si el peon llega al final puede cambiar por cualquier pieza es asi por
            //el cual debe de poder eligir q pieza a cambir

            if (pieceBeingDragged == 0 && (newDesRow == 0 || newDesRow == 7))
            {
                promocion ( newDesRow, newDesColumn);
            }
            else
            {
                cellMatrix.setPieceCell(newDesRow, newDesColumn, pieceBeingDragged);
            }

            if (cellMatrix.checkWinner(currentPlayer)) {
                hasWon = true;
                strStatusMsg = getPlayerMsg();

            }
            else
            {
                // Cabia de jugador si naide ha ganado.
                // 1 para Jugador Humano
                // 2 para Jugador AI
                if (currentPlayer == 1)
                {
                    currentPlayer = 2;
                }
                else
                {
                    currentPlayer = 1;
                }

                strStatusMsg = getPlayerMsg();
            }

        }
        else
        {
           // Si por el conmtrario, la ficha ha sido movida de forma indebida, aparecerá
        // Un mensaje de error especifico para esa ficha
            switch (pieceBeingDragged) {

                case 0: strStatusMsg = pawnObject.getErrorMsg();
                break;
                case 1: strStatusMsg = rockObject.getErrorMsg();
                break;
                case 2: strStatusMsg = knightObject.getErrorMsg();
                break;
                case 3: strStatusMsg = bishopObject.getErrorMsg();
                break;
                case 4: strStatusMsg = queenObject.getErrorMsg();
                break;
                case 5: strStatusMsg = kingObject.getErrorMsg();
                break;

            }

            unsucessfullDrag(desRow, desColumn);

        }

    }

        /**
         * Establece la Promocion
         * @param desRow Coordenadas de la Fila
         * @param desColumn Coordenadas de la Columna
         */
        private void promocion( int newDesRow, int newDesColumn)
    {
        boolean canPass = false;
                int newPiece = 2;
                String strNewPiece = "Torre";
                String[] strPieces = {"Torre","Caballo","Alfil","Reina"};
                JOptionPane digBox = new JOptionPane("Elige la pieza", JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, strPieces, "Torre");
                JDialog dig = digBox.createDialog(null, "Peon al final del camino");

                do
                {

                    dig.setVisible(true);

                    try {

                        strNewPiece = digBox.getValue().toString();

                        for (int i = 0; i < strPieces.length; i++) {

                            if (strNewPiece.equalsIgnoreCase(strPieces[i])) {

                                canPass = true;
                                newPiece = i + 1;

                            }

                        }

                    } catch (NullPointerException e) {
                        canPass = false;
                    }

                }
                while (canPass == false);

                cellMatrix.setPieceCell(newDesRow, newDesColumn, newPiece);

                if (cellMatrix.checkWinner(currentPlayer)) {

                hasWon = true;
                strStatusMsg = getPlayerMsg();

            } else {

                if (currentPlayer == 1) {
                    currentPlayer = 2;
                } else {
                    currentPlayer = 1;
                }

                strStatusMsg = getPlayerMsg();

            }
    }

    /**
     * Reubica una pieza luego de un movimiento Errado
     * @param desRow Coordenadas de la Fila
     * @param desColumn Coordenadas de la Columna
     */
    private void unsucessfullDrag(int desRow, int desColumn) {
        
        cellMatrix.setPieceCell(startRow, startColumn, pieceBeingDragged);
        cellMatrix.setPlayerCell(startRow, startColumn, currentPlayer);
        
    }

    /**
     * PENDIENTE
     * @param desRow Coordenadas de la Fila
     * @param desColumn Coordenadas de la Columna
     */
    private void updatePaintInstructions(int desRow, int desColumn) {
        
        currentInstruction = new objPaintInstruction(startRow, startColumn, 1);
        vecPaintInstructions.addElement(currentInstruction);
        
        currentInstruction = new objPaintInstruction(desRow, desColumn);
        vecPaintInstructions.addElement(currentInstruction);
        
    }
    
    
    /*Sirve para poder controloar los eventos del mouse, esto es lo que se llama el famoso
     drag and drop
     */
    
    public void mouseClicked(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
        mouseReleased(e);
    }
    
    public void mousePressed(MouseEvent e) {
        
        if (!hasWon && !firstTime) {
            
            int x = e.getX();
            int y = e.getY();
            
            if ((x > 60 && x < 430) && (y > 60 && y < 430)) {
                
                startRow = findWhichTileSelected(y);
                startColumn = findWhichTileSelected(x);
                
                if (cellMatrix.getPlayerCell(startRow, startColumn) == currentPlayer) {
                    
                    pieceBeingDragged = cellMatrix.getPieceCell(startRow, startColumn);
                    cellMatrix.setPieceCell(startRow, startColumn, 6);
                    cellMatrix.setPlayerCell(startRow, startColumn, 0);
                    isDragging = true;
                    
                } else {
                    isDragging = false;
                }
                
            }
            
        }
        
    }
    
    public void mouseReleased(MouseEvent e) {
        
        if (isDragging) {
            
            isDragging = false;
            
            int desRow = findWhichTileSelected(currentY);
            int desColumn = findWhichTileSelected(currentX);
            checkMove(desRow, desColumn);
            repaint();
            
        }
        
    }
    
    public void mouseDragged(MouseEvent e) {
        
        if (isDragging) {
            
            int x = e.getX();
            int y = e.getY();
            
            if ((x > 60 && x < 430) && (y > 60 && y < 430)) {
                
                if (refreshCounter >= refreshRate) {
                    
                    currentX = x;
                    currentY = y;
                    refreshCounter = 0;
                    int desRow = findWhichTileSelected(currentY);
                    int desColumn = findWhichTileSelected(currentX);
                    
                    updatePaintInstructions(desRow, desColumn);
                    repaint();
                    
                }

                else {
                    refreshCounter++;
                }
                
            }
            
        }
        
    }
    
    public void mouseMoved(MouseEvent e) {
    }
    
    public void gotFocus() {
        repaint();
    }
    
}