/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ajedrez;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

/**
 *
 * @author ma0
 */
public class Tablero extends objChessBoard implements MouseListener, MouseMotionListener 
{
    windowChessBoard chessBoard;

    public windowChessBoard getChessBoard() {
        return chessBoard;
    }
    private final int refreshRate = 5;
    private Image[][] imgPlayer = new Image[2][6];
    private int currentPlayer = 1, startRow = 0, startColumn = 0, pieceBeingDragged = 0;
    private int  currentX = 0, currentY = 0, refreshCounter = 0;
    private boolean firstTime = true, hasWon = false, isDragging = false;
    ArrayList<String> atacantes = new ArrayList<String> ();

    administradorJugadas admin;
    /**
     * Constructor Incluye los Listeners al JPanel
     */
    public Tablero ()
    {

        this.addMouseListener (this);
        this.addMouseMotionListener (this);
        chessBoard = new windowChessBoard();

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

    //Nuevo juego
    /**
     * Crea un Nuevo Juego
     */
    public void newGame ()
    {

        firstTime = false;
        chessBoard.resetBoard ();
       // chessBoard.problemBoard(3);
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
            chessBoard.checkMove (startRow, startColumn,desRow, desColumn, pieceBeingDragged);
            repaint ();
            currentPlayer = chessBoard.getCurrentPlayer();

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



}