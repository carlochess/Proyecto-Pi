package Ajedrez.engine;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import java.util.*;

public class WindowChessBoard extends ObjChessBoard //implements MouseListener, MouseMotionListener
{
    //-----------------------------------
    // Atributos
    //-----------------------------------
    
    private Image[][] imgPlayer;
    private int xKing, yKing;
    private boolean statusTorre;
    private boolean[] hasidomovida;
    private boolean[] reyHaSidoMovido;
    private int currentPlayer,startColumn,startRow;
    private ArrayList<String> atacantes;
    private ObjPawn pawnObject;
    private ObjRock rockObject;
    private ObjKnight knightObject;
    private ObjBishop bishopObject;
    private ObjQueen queenObject;
    private ObjKing kingObject;
    private boolean salidapeon,hasWon;
    private int elpeonsalede;
    private AdminJugadas admin;
    private AdminJaque adminJaque;
    private AdminJaqueMate verificarJaquemate;
    public ObjCellMatrix cellMatrix;

    //-----------------------------------
    // Constructor
    //-----------------------------------
    /**
     * Constructor Incluye los Listeners al JPanel
     */
    public WindowChessBoard()
    {
        pawnObject = new ObjPawn();
        rockObject = new ObjRock();
        knightObject = new ObjKnight();
        bishopObject = new ObjBishop();
        queenObject = new ObjQueen();
        kingObject = new ObjKing();
        cellMatrix = new ObjCellMatrix();
        admin = new AdminJugadas();
        verificarJaquemate = new AdminJaqueMate(cellMatrix, currentPlayer);
        adminJaque = new AdminJaque(cellMatrix);
        
        elpeonsalede = -1;
        currentPlayer = 1;
        salidapeon = false;
        hasWon = false;
        startColumn=0;
        startRow=0;
        
        atacantes = new ArrayList<String>();
        
        imgPlayer = new Image[2][6];
        hasidomovida = new boolean[]{false, false, false, false};
        reyHaSidoMovido = new boolean []{false, false};
    }

    //Comiezan el juego de nuevo
    /**
     * Inicia de Nuevo el Juego
     */
    public void resetBoard()
    {

        hasWon = false;
        currentPlayer = 1;
        cellMatrix.resetMatrix();
        repaint();
        for (int i = 0; i < hasidomovida.length; i++)
        {
            hasidomovida[i] = false;
        }

        for (int i = 0; i < reyHaSidoMovido.length; i++)
        {
            reyHaSidoMovido[i] = false;
        }
    }

    /**
     * Carga las Imagenes al Arreglo imgPlayer
     *
     * @param imgRed Arreglo de Imagenes con las Imagenes Rojas
     * @param imgBlue Arreglo de Imagenes con las Imagenes Azules
     */
    public void setupImages(Image[] imgRed, Image[] imgBlue)
    {

        imgPlayer[0] = imgRed;
        imgPlayer[1] = imgBlue;
        resetBoard();

    }

    // Función que verifica si las torres ha sido movidas, capturadas o, en su
    // defecto, se encuentran en su posición inicial
    /*
     * Función que verifica si las torres ha sido movidas, capturadas o, en su
     * defecto, se encuentran en su posición inicial
     */
    private void updateRook()
    {
        // Verifica si la primera torre (Esquina sup. izquierda) esta y es del jugador actual
        if (cellMatrix.getPieceCell(0, 0) != 1 && cellMatrix.getPlayerCell(0, 0) != currentPlayer)
        {
            hasidomovida[0] = true;
        }
        // Verifica si la segunda torre (Esquina sup. derecha) esta y es del jugador actual
        if (cellMatrix.getPieceCell(0, 7) != 1 && cellMatrix.getPlayerCell(0, 7) != currentPlayer)
        {
            hasidomovida[1] = true;
        }
        // Verifica si la tercera torre (Esquina inferior izquierda) esta y es del jugador actual
        if (cellMatrix.getPieceCell(7, 0) != 1 && cellMatrix.getPlayerCell(7, 0) != currentPlayer)
        {
            hasidomovida[2] = true;
        }
        // Verifica si la cuarta torre (Esquina inferior derecha) esta y es del jugador actual
        if (cellMatrix.getPieceCell(7, 7) != 1 && cellMatrix.getPlayerCell(7, 7) != currentPlayer)
        {
            hasidomovida[3] = true;
        }
    }
    /*
     * Retorna la posición del arreglo de la torre más cercana dadas las
     * coordenadas finales del mov. del rey. return [0,3]
     */

    private int torreMasProxima(int desRow, int desColumn)
    {
        //(desRow == 0 ) ? ((desColumn==0) ? return 0 : return 1) : ((desColumn==0 )? return 2 : return 3);
        if (desRow == 0)
        { // Dom {0,1}
            if (desColumn <= 4)
            {
                return 0;
            }
            return 1;
        }
        else // (destRow == 7 )
        {// Dom {2,3}
            if (desColumn <= 4)
            {
                return 2;
            }
            return 3;
        }

    }

    //Valida los movimientos
    /**
     * Valida el Movimiento de la pieza Seleccionada
     *
     * @param desRow Coordenadas de la Fila
     * @param desColumn Coordenadas de la Columna
     */
    public void checkMove(int startRow, int startColumn, int desRow, int desColumn, int pieceBeingDragged)
    {

        boolean legalMove = false;
        int enroque = 0;
        int piezaposeedora = cellMatrix.getPieceCell(desRow, desColumn);
        int manposeedor = cellMatrix.getPlayerCell(desRow, desColumn);
        updateRook();
        if (cellMatrix.getPlayerCell(desRow, desColumn) == currentPlayer)
        {
        }
        else
        {

            switch (pieceBeingDragged)
            {
                case 0:
                    legalMove = pawnObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix(), currentPlayer, salidapeon, elpeonsalede);
                    pawnValidator(legalMove, desColumn, startRow, desRow, startColumn);
                    break;
                case 1:
                    legalMove = rockObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix());

                    break;
                case 2:
                    legalMove = knightObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix());

                    //legalMove = legalMove && !checkCheck(-1,-1, currentPlayer);
                    break;
                case 3:
                    legalMove = bishopObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix());
                    break;
                case 4:
                    legalMove = queenObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix());
                    break;
                case 5:
                    // statusTorre = false si es légitimo el movimiento
                    boolean statusTorre = !hasidomovida[torreMasProxima(desRow, desColumn)];
                    // Agregue como parametro statusTorre
                    legalMove = kingObject.legalMove(startRow,
                            startColumn,
                            desRow,
                            desColumn,
                            cellMatrix.getPlayerMatrix(),
                            currentPlayer,
                            statusTorre, reyHaSidoMovido[currentPlayer - 1]);


                    enroque = kingObject.getEnroque();
                    kingObject.setEnroque(0);
                    break;

            }
            //  legalMove =  !checkCheck(desRow, desColumn, currentPlayer)||!checkCheck(-1, -1, currentPlayer);


        }

        // Ha sido una movida adecuada?
        int flagEnroque = 0;

        if (legalMove)
        {
            if (pieceBeingDragged == 5)
            {


                reyHaSidoMovido[currentPlayer - 1] = true;
                if (enroque != 0)
                {
                    if (enroque == 1)
                    {
                        cellMatrix.setPieceCell(desRow, desColumn - 1, 1);
                        cellMatrix.setPlayerCell(desRow, desColumn - 1, currentPlayer);
                        cellMatrix.setPieceCell(desRow, desColumn + 1, 6);
                        cellMatrix.setPlayerCell(desRow, desColumn + 1, 0);
                        flagEnroque++;
                    }
                    else
                    {
                        if (enroque == -1)
                        {
                            cellMatrix.setPieceCell(desRow, desColumn + 1, 1);
                            cellMatrix.setPlayerCell(desRow, desColumn + 1, currentPlayer);
                            cellMatrix.setPieceCell(desRow, desColumn - 2, 6);
                            cellMatrix.setPlayerCell(desRow, desColumn - 2, 0);
                            flagEnroque--;
                        }
                    }
                }
            }

            admin.almacenarJugada(pieceBeingDragged, startRow, startColumn, desRow, desColumn, flagEnroque);
            admin.imprimirUltimaJugada(cellMatrix.getPlayerMatrix(), currentPlayer);
            cellMatrix.setPlayerCell(desRow, desColumn, currentPlayer);

            //Si el peon llega al final puede cambiar por cualquier pieza es asi por
            //el cual debe de poder eligir q pieza a cambir

            if (pieceBeingDragged == 0 && (desRow == 0 || desRow == 7))
            {
                promocion(desRow, desColumn);
            }
            else
            {

                cellMatrix.setPieceCell(desRow, desColumn, pieceBeingDragged);
            }
            adminJaque.setCellMatrix(cellMatrix);
            if (adminJaque.checkCheck(-1, -1, currentPlayer))
            {
                legalMove = false;
                cellMatrix.setPieceCell(desRow, desColumn, piezaposeedora);
                cellMatrix.setPlayerCell(desRow, desColumn, manposeedor);
                cellMatrix.setPieceCell(startRow, startColumn, pieceBeingDragged);
                cellMatrix.setPlayerCell(startRow, startColumn, currentPlayer);
                JOptionPane.showMessageDialog(this, "La jugada es inválida, porfavor, corrijala");
            }
            if (cellMatrix.checkWinner(currentPlayer))
            {
                hasWon = true;
                // para guardar el último tablero 
                admin.guardarTablero(cellMatrix.getPieceMatrix(), cellMatrix.getPlayerMatrix());
            }
            else if (legalMove)
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

            }

        }
        else
        {
            // Si por el contrario, la ficha ha sido movida de forma indebida, aparecerá
            // Un mensaje de error especifico para esa ficha
            switch (pieceBeingDragged)
            {

                case 0:

                    break;
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;

            }

            unsucessfullDrag(startRow, startColumn, pieceBeingDragged, currentPlayer);

        }
        adminJaque.setCellMatrix(cellMatrix);
        if (adminJaque.checkCheck(-1, -1, currentPlayer))
        {
            JOptionPane.showMessageDialog(null,"Estas en Jaque");
            System.out.println("Jaque");
            recojerDatosJaque();
            verificarJaquemate.actualizar(cellMatrix, currentPlayer, atacantes, reyHaSidoMovido, statusTorre);
            if (verificarJaquemate.ismate(xKing, yKing, currentPlayer))
            {
                JOptionPane.showMessageDialog(this, "JaqueMate");
                System.out.println("Jaque mate");
            }
        }
    }

    private void recojerDatosJaque()
    {
        atacantes = adminJaque.getAtacantes();
//        if (atacantes == null)
//        {
//            //cout("atacantes NIL");
//        }
        xKing = adminJaque.getxKing();
        yKing = adminJaque.getyKing();
//        for (String g : atacantes)
//        {
//            cout(g);
//        }
//        cout("");
    }

//    private void cout(String g)
//    {
//        System.out.println(g);
//    }

    /**
     * Establece la Promocion
     *
     * @param desRow Coordenadas de la Fila
     * @param desColumn Coordenadas de la Columna
     */
    private void promocion(int newDesRow, int newDesColumn)
    {
        boolean canPass = false;
        int newPiece = 2;
        String strNewPiece = "Torre";
        String[] strPieces =
        {
            "Torre", "Caballo", "Alfil", "Reina"
        };
        JOptionPane digBox = new JOptionPane("Elige la pieza", JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, strPieces, "Torre");
        JDialog dig = digBox.createDialog(null, "Peon al final del camino");

        do
        {
            dig.setVisible(true);
            try
            {

                strNewPiece = digBox.getValue().toString();

                for (int i = 0; i < strPieces.length; i++)
                {

                    if (strNewPiece.equalsIgnoreCase(strPieces[i]))
                    {

                        canPass = true;
                        newPiece = i + 1;

                    }

                }

            }
            catch (NullPointerException e)
            {
                canPass = false;
            }

        }
        while (canPass == false);

        cellMatrix.setPieceCell(newDesRow, newDesColumn, newPiece);
    }

    private void pawnValidator(boolean legalMove, int desColumn, int startRow, int desRow, int startColumn)
    {
        if (legalMove && elpeonsalede == desColumn)
        {
            if (startRow + 1 == desRow && desRow == 2)
            {
                if (currentPlayer == 1)
                {
                    cellMatrix.setPieceCell(desRow - 1, desColumn, 6);
                    cellMatrix.setPlayerCell(desRow - 1, desColumn, 0);
                }
                else if (desRow == 5)
                {
                    if (currentPlayer == 2)
                    {
                        cellMatrix.setPieceCell(desRow - 1, desColumn, 6);
                        cellMatrix.setPlayerCell(desRow - 1, desColumn, 0);
                    }
                }
            }
            else
            {
                if (startRow - 1 == desRow && desRow == 2)
                {
                    if (currentPlayer == 1)
                    {
                        cellMatrix.setPieceCell(desRow + 1, desColumn, 6);
                        cellMatrix.setPlayerCell(desRow + 1, desColumn, 0);
                    }
                    else if (desRow == 5)
                    {
                        if (currentPlayer == 2)
                        {
                            cellMatrix.setPieceCell(desRow + 1, desColumn, 6);
                            cellMatrix.setPlayerCell(desRow + 1, desColumn, 0);
                        }
                    }
                }
            }
        }
        if (legalMove && (startRow + 2 == desRow || startRow - 2 == desRow))
        {
            salidapeon = true;
            elpeonsalede = startColumn;
        }
        else
        {
            salidapeon = false;
            elpeonsalede = -1;
        }
    }
    
    /**
     * Reubica una pieza luego de un movimiento Errado
     *
     * @param desRow Coordenadas de la Fila
     * @param desColumn Coordenadas de la Columna
     */
    private void unsucessfullDrag(int startRow, int startColumn, int pieceBeingDragged, int currentPlayer)
    {
        cellMatrix.setPieceCell(startRow, startColumn, pieceBeingDragged);
        cellMatrix.setPlayerCell(startRow, startColumn, currentPlayer);

    }

    public void gotFocus()
    {
        repaint();
    }

    //********************************************
    public void chessPuzzles(int lim)
    {
        String problema = null;
        try 
        {
            BufferedReader in = new BufferedReader(new FileReader("problemas.txt"));
            String str;
            int i = -1;
            while ((str = in.readLine()) != null) 
            {
                if (i == lim) 
                {
                    problema = str;
                    break;
                }
                i++;
            }
            in.close();
        } 
        catch (IOException e) 
        {
            //cout("File");
        }
        
        if (problema != null || !problema.isEmpty()) 
        {
            analizarJugador(problema.substring(0, 3));
            problema = problema.substring(3, problema.length());
            verificarJaquemate = new AdminJaqueMate(cellMatrix, currentPlayer);
            adminJaque = new AdminJaque(cellMatrix);
            admin.configuracionTableroFinal(problema, cellMatrix.getPieceMatrix(), cellMatrix.getPlayerMatrix());
        }
    }

    public void printBoard()
    {
        String fichas = "PTCADR ";
        for (int [] a : cellMatrix.getPieceMatrix() )
        {
            for (int j : a)
            {
                System.out.print(fichas.charAt(j)+" ");
            }
            System.out.println("");
        }
        System.out.println("----------------\n");
    }
    
    void problemBoard(int i)
    {
        chessPuzzles(i);
    }
    
    public int getCurrentPlayer()
    {
        return currentPlayer;
    }

    public void setStartColumn(int startColumn)
    {
        this.startColumn = startColumn;
    }

    public void setStartRow(int startRow)
    {
        this.startRow = startRow;
    }

    private void analizarJugador(String substring) {
        if (substring.equals("1-0"))
        {
            currentPlayer = 1;
        }
        else
        {
            currentPlayer = 2;
        }
    }
    public void setCurrentPlayer(int currentPlayer)
    {
        this.currentPlayer = currentPlayer;
    }
}
