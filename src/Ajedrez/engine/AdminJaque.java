package Ajedrez.engine;


import java.util.ArrayList;


/**
 *
 * @author carlochess
 */
public class AdminJaque 
{
    //-----------------
    // Constantes
    //-----------------
    
    //-----------------
    // Atributos
    //-----------------
    
    public ObjCellMatrix cellMatrix;
    private ArrayList<String> atacantes;
    private ObjPawn   pawnObject;
    private ObjRock   rockObject;
    private ObjKnight knightObject;
    private ObjBishop bishopObject;
    private ObjQueen  queenObject;
    private ObjKing   kingObject;
    private int xKing;
    private int yKing;
    
    private int numAtacantes;
    //-----------------
    // Constructor
    //------------------
    /**
     * 
     * @param cellMatrix 
     */
    public AdminJaque(ObjCellMatrix cellMatrix)
    {
        pawnObject   = new ObjPawn();
        rockObject   = new ObjRock ();
        knightObject = new ObjKnight();
        bishopObject = new ObjBishop();
        queenObject  = new ObjQueen();
        kingObject  = new ObjKing();
        this.cellMatrix   = cellMatrix;
        atacantes    = new ArrayList<String>();
        numAtacantes = 0;
        xKing = 0;
        yKing = 0;
    }

    //-----------------
    // Métodos
    //-----------------
    /*
     * Esta función recorre todo el tablero de las fichas, seleccionando
     * Aquellas que pertenezcan al jugandor contrario @param currentPlayer
     * Jugador actual @return boolean Si el rey esta en AdminJaque;
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
        //System.out.println((currentPlayer==1)? "AdminJaque verificado para Blancas": "AdminJaque verificado para Negras");
        boolean hayJaque;
        atacantes.clear();
        hayJaque = canBecapture(currentPlayer, xKing, yKing, true, this.getCellMatrix(), true);

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
    public boolean canBecapture(int currentPlayer, int xKing, int yKing, boolean RecolectarAtacantes, ObjCellMatrix cellMatrix, boolean Jaque)
    {
        numAtacantes = 0;
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
                        //System.out.println("  Es intersectada por " +i + "" + j);
                        canBecapture = true;
                        ++numAtacantes;
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
    
     public int getNumAtacantes()
     {
         return numAtacantes;
     }
        
    public boolean checkPieceCheck(int i, int j, int xKing, int yKing, int currentPlayer, int PieceCell, boolean t)
    {
        boolean jaque = false;
        switch (PieceCell)
        {
            case 0:
                jaque = pawnObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix(), not(currentPlayer), false, -1);// not(currentPlayer), false, -1);
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
                // Por el momento true, true; No debe queda en AdminJaque
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
    public ObjCellMatrix getCellMatrix()
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

    public void setCellMatrix(ObjCellMatrix cellMatrix)
    {
        this.cellMatrix = cellMatrix;
    }
    
}
