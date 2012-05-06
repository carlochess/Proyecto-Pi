/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ajedrez.Interfaz;

import Ajedrez.engine.AdminJugadas;
import Ajedrez.engine.ObjChessBoard;
import Ajedrez.engine.WindowChessBoard;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author ma0
 */
public class Tablero extends ObjChessBoard implements MouseListener, MouseMotionListener 
{
    // ----------------
    // Constantes
    private final int refreshRate = 5;
    // ----------------
    // Atributos
    // ----------------
    WindowChessBoard chessBoard;
    private int currentPlayer, startRow, startColumn, pieceBeingDragged;
    private int  currentX, currentY, refreshCounter;
    private boolean firstTime, hasWon, isDragging;
    private Image[][] imgPlayer;
    ArrayList<String> atacantes;
    AdminJugadas admin;
    // ----------------
    // Constructor
    // ----------------
    
    /**
     * Constructor Incluye los Listeners al JPanel
     */
    public Tablero ()
    {
        imgPlayer = new Image[2][6];
        this.addMouseListener (this);
        this.addMouseMotionListener (this);
        chessBoard = new WindowChessBoard();
        admin = new AdminJugadas();
        atacantes = new ArrayList<String> ();
        currentPlayer = 1; startRow = 0; startColumn = 0; pieceBeingDragged = 0;
        currentX = 0; currentY = 0; refreshCounter = 0;
        firstTime = true; hasWon = false; isDragging = false;
    }

   

    //Comiezan el juego de nuevo
    /**
     * Inicia de Nuevo el Juego
     */
    private void resetBoard ()
    {
        currentPlayer = 1;
        chessBoard.resetBoard();
        chessBoard.cellMatrix.resetMatrix();
        repaint ();
    }

    /**
     * Carga las Imagenes al Arreglo imgPlayer
     * @param imgRed Arreglo de Imagenes con las Imagenes Rojas
     * @param imgBlue Arreglo de Imagenes con las Imagenes Azules
     */
    public void setupImages (Image[] imgRed, Image[] imgBlue)
    {

        imgPlayer[0] = imgRed;
        imgPlayer[1] = imgBlue;
        resetBoard ();

    }

    /**
     * Dibuja la Pieza en la Casilla Correspondiente
     * @param g
     */
    protected void drawExtra (Graphics g)
    {

        for (int i = 0; i < vecPaintInstructions.size (); i++)
        {

            currentInstruction = (objPaintInstruction) vecPaintInstructions.elementAt (i);
            int paintStartRow = currentInstruction.getStartRow ();
            int paintStartColumn = currentInstruction.getStartColumn ();
            int rowCells = currentInstruction.getRowCells ();
            int columnCells = currentInstruction.getColumnCells ();

            for (int row = paintStartRow; row < (paintStartRow + rowCells); row++)
            {

                for (int column = paintStartColumn; column < (paintStartColumn + columnCells); column++)
                {

                    int playerCell = chessBoard.cellMatrix.getPlayerCell (row, column);
                    int pieceCell = chessBoard.cellMatrix.getPieceCell (row, column);

                    if (playerCell != 0)
                    {

                        try
                        {
                            g.drawImage (imgPlayer[playerCell - 1][pieceCell], ((column + 1) * 50), ((row + 1) * 50), this);
                        }
                        catch (ArrayIndexOutOfBoundsException e)
                        {
                        }
                    }
                }
            }
        }

        if (isDragging)
        {
            g.drawImage (imgPlayer[currentPlayer - 1][pieceBeingDragged], (currentX - 25), (currentY - 25), this);
        }

        vecPaintInstructions.clear ();
    }

    public void newGameConsole(String j, boolean display)
    {
        firstTime = false;
        chessBoard.resetBoard ();
        repaint();
        ArrayList jugadas = new ArrayList<String>();
        StringTokenizer token = new StringTokenizer(j,"-");
        while(token.hasMoreTokens())
        {
            jugadas.add(token.nextToken());
        }
        admin.jugadasDisplay( jugadas, chessBoard, display);
    }
    
    //Nuevo juego
    /**
     * Crea un Nuevo Juego
     */
    public void newGame ()
    {

        firstTime = false;
        chessBoard.resetBoard ();
        repaint();
    }

    


    /**
     * PENDIENTE
     * @param desRow Coordenadas de la Fila
     * @param desColumn Coordenadas de la Columna
     */
    private void updatePaintInstructions (int desRow, int desColumn)
    {

        currentInstruction = new objPaintInstruction (startRow, startColumn, 1);
        vecPaintInstructions.addElement (currentInstruction);

        currentInstruction = new objPaintInstruction (desRow, desColumn);
        vecPaintInstructions.addElement (currentInstruction);

    }

    /*Sirve para poder controloar los eventos del mouse, esto es lo que se llama el famoso
    drag and drop
     */
    public void mouseClicked (MouseEvent e)
    {
        //System.out.println("");
    }

    public void mouseEntered (MouseEvent e)
    {
    }

    public void mouseExited (MouseEvent e)
    {
        mouseReleased (e);
    }

    public void mousePressed (MouseEvent e)
    {

        if (!hasWon && !firstTime)
        {

            int x = e.getX ();
            int y = e.getY ();

            if ((x > 60 && x < 430) && (y > 60 && y < 430))
            {

                 startRow = findWhichTileSelected (y);
                 startColumn = findWhichTileSelected (x);
                if (chessBoard.cellMatrix.getPlayerCell (startRow, startColumn) == chessBoard.getCurrentPlayer())
                {
                    chessBoard.setStartRow(startRow);
                    chessBoard.setStartColumn(startColumn);
                    pieceBeingDragged = chessBoard.cellMatrix.getPieceCell (startRow, startColumn);
                    chessBoard.cellMatrix.setPieceCell (startRow, startColumn, 6);
                    chessBoard.cellMatrix.setPlayerCell (startRow, startColumn, 0);
                    isDragging = true;

                }
                else
                {
                    isDragging = false;
                }

            }

        }

    }

    public void mouseReleased (MouseEvent e)
    {

        if (isDragging)
        {

            isDragging = false;

            int desRow = findWhichTileSelected (currentY);
            int desColumn = findWhichTileSelected (currentX);
            if ((desRow < 8 && desRow> -1) && (desColumn < 8 && desColumn>-1))
            {
                chessBoard.checkMove (startRow, startColumn,desRow, desColumn, pieceBeingDragged);
                repaint ();
                currentPlayer = chessBoard.getCurrentPlayer();
            }
        }

    }

    public void mouseDragged (MouseEvent e)
    {

        if (isDragging)
        {

            int x = e.getX ();
            int y = e.getY ();

            if ((x > 60 && x < 430) && (y > 60 && y < 430))
            {

                if (refreshCounter >= refreshRate)
                {

                    currentX = x;
                    currentY = y;
                    refreshCounter = 0;
                    int desRow = findWhichTileSelected (currentY);
                    int desColumn = findWhichTileSelected (currentX);

                    updatePaintInstructions(desRow, desColumn);
                    repaint ();

                }
                else
                {
                    refreshCounter++;
                }

            }

        }

    }

    public void mouseMoved (MouseEvent e)
    {
    }

    public void gotFocus ()
    {
        repaint ();
    }

    public WindowChessBoard getChessBoard() {
        return chessBoard;
    }

}