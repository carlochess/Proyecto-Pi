package Ajedrez;


import Ajedrez.*;
import java.util.ArrayList;


/**
 *
 * @author carlochess
 */
public class Jaque 
{
    //-----------------
    // Constantes
    //-----------------
    
    //-----------------
    // Atributos
    //-----------------
    
    public objCellMatrix cellMatrix;
    private ArrayList<String> atacantes;
    private objPawn   pawnObject;
    private objRock   rockObject;
    private objKnight knightObject;
    private objBishop bishopObject;
    private objQueen  queenObject;
    private objKing   kingObject;
    private int xKing;
    private int yKing;
    
    private int idiota;
    //-----------------
    // Constructor
    //------------------
    /**
     * 
     * @param cellMatrix 
     */
    public Jaque(objCellMatrix cellMatrix)
    {
        pawnObject   = new objPawn();
        rockObject   = new objRock ();
        knightObject = new objKnight();
        bishopObject = new objBishop();
        queenObject  = new objQueen();
        kingObject  = new objKing();
        this.cellMatrix   = cellMatrix;
        atacantes    = new ArrayList<String>();
        xKing = 0;
        yKing = 0;
    }

    //-----------------
    // Métodos
    //-----------------
    /*
     * Esta función recorre todo el tablero de las fichas, seleccionando
     * Aquellas que pertenezcan al jugandor contrario @param currentPlayer
     * Jugador actual @return boolean Si el rey esta en Jaque;
     */
    /**
     * 
     * @param xKing
     * @param yKing
     * @param currentPlayer
     * @return 
     */
    public boolean checkCheck(int xKing, int yKing, int currentPlayer)
    {
        if (xKing == -1 && yKing == -1)
        {
            // Primero encontraremos a nuestro Rey
            for (int i = 0; i < 8; i++)
            {
                for (int j = 0; j < 8; j++)
                {
                    // 5 = Rey; Si el número 5 se encuentra en alguna casilla de la matriz y pertenece al jugador seleccionado
                    if (cellMatrix.getPieceCell(i, j) == 5 && cellMatrix.getPlayerCell(i, j) == currentPlayer)
                    {
                        xKing = i;
                        yKing = j;
                        this.xKing = i;
                        this.yKing = j;
                        break;
                    }
                }
            }
        }
        boolean hayJaque;
        atacantes.clear();
        hayJaque = canBecapture(currentPlayer, xKing, yKing, true, this.getCellMatrix(), false);

        return hayJaque;
    }
    
    /**
     * Vamos encontrando cada ficha y determinaremos si pueden atacar a las coordenadas xKing yKing,
     * @param currentPlayer
     * @param xKing
     * @param yKing
     * @param RecolectarAtacantes 
     * @param cellMatrix 
     * @return 
     */
    public boolean canBecapture(int currentPlayer, int xKing, int yKing, boolean RecolectarAtacantes, objCellMatrix cellMatrix, boolean Jaque)
    {
        idiota = 0;
        boolean canBecapture = false;
        // Vamos encontrando cada ficha y determinaremos si estan en alguna posición
        // Ataca al rey del currentPlayer
        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                // Si la casilla no es vacia y pertenece al jugador al jugador contrario entonces...
                if (cellMatrix.getPieceCell(i, j) != 6 && cellMatrix.getPlayerCell(i, j) != currentPlayer)
                {
                    // Si la ficha antes seleccionada esta en capacidad de amenazar el rey entonces...
                    if (checkPieceCheck(i, j, xKing, yKing, currentPlayer, cellMatrix.getPieceCell(i, j), Jaque))
                    {
                        // si la ficha no esta en la LN
                        // ......
                        // Almacenaremos las coordenadas en una pila.
                        if (RecolectarAtacantes)
                        {
                            //
                            atacantes.add( i + "" + j);
                        }
                        System.out.println("  Es intersectada por " +i + "" + j);
                        canBecapture = true;
                        ++idiota;
                    }
                }
            }
        }
        return canBecapture;
    }
    
    public int not(int ply)
    {
        return (ply == 1)? 2 : 1;
    }
    
     public int getIdiota()
     {
         return idiota;
     }
    /**
     * Verifica si un ficha en la piscion (i,j) puede atacar a (xKing, yKing)
     * @param i
     * @param j
     * @param xKing
     * @param yKing
     * @param currentPlayer
     * @return 
     */
    public boolean checkPieceCheck(int i, int j, int xKing, int yKing, int currentPlayer)
    {
        boolean jaque = false;
        switch (cellMatrix.getPieceCell(i, j))
        {
            case 0:
                jaque = pawnObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix(), currentPlayer, false, -1);
                break;
            case 1:
                jaque = rockObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix());
                break;
            case 2:
                jaque = knightObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix());
                break;
            case 3:
                jaque = bishopObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix());
                break;
            case 4:
                jaque = queenObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix());
                break;
            default:
                break;
        }
        return jaque;
    }
    
    public boolean checkPieceCheck(int i, int j, int xKing, int yKing, int currentPlayer, int PieceCell, boolean t)
    {
        boolean jaque = false;
        switch (PieceCell)
        {
            case 0:
                jaque = pawnObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix(), not(currentPlayer), false, -1);
                break;
            case 1:
                jaque = rockObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix());
                break;
            case 2:
                jaque = knightObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix());
                break;
            case 3:
                jaque = bishopObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix());
                break;
            case 4:
                jaque = queenObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix());
                break;
                
            case 5:
                // Por el momento true, true; No debe queda en Jaque
                if (t)
                    jaque = kingObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix(),currentPlayer, true, true);
                break;
            default:
                break;
        }
        
        return jaque;
    }
    
       
    //----------------------------
    // Getters
    //----------------------------
    public objCellMatrix getCellMatrix()
    {
        return cellMatrix;
    }
    
    public ArrayList<String> getAtacantes()
    {
        return atacantes;
    }
    
    public int getxKing()
    {
        return xKing;
    }

    public int getyKing()
    {
        return yKing;
    }
    
    //----------------------------
    // Setters
    //----------------------------
    public void setAtacantes(ArrayList<String> atacantes)
    {
        this.atacantes = atacantes;
    }

    public void setCellMatrix(objCellMatrix cellMatrix)
    {
        this.cellMatrix = cellMatrix;
    }

}
