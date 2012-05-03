package Ajedrez;
import java.util.ArrayList;

/**
 *
 * @author carlochess
 */
public class JaqueMate 
{
    //-----------------
    // Constantes
    //-----------------
    
    //-----------------
    // Atributos
    //-----------------
    
    private objCellMatrix cellMatrixCopy;
    private int currentPlayer;
    private ArrayList<String> atacantes;
    private boolean statusTorre;
    private boolean[] reyHaSidoMovido;
    private Jaque jaqueAdmin;
    //-----------------
    // Constructor
    //------------------
    public JaqueMate(objCellMatrix cellMatrix, int currentPlayer )
    {
        // Recibir cellMatriz
        this.cellMatrixCopy = cellMatrix;
        // Recibir currentPlayer
        this.currentPlayer = currentPlayer;
        // Hacer un ataud con esta función checkPieceCheck
        atacantes = new ArrayList<String> ();
        jaqueAdmin = new Jaque(cellMatrix);
        reyHaSidoMovido = new boolean []{false, false};
    }

    //-----------------
    // Métodos
    //-----------------
    /*
     * Esta función verifica si existe un posible Jaque mate
     * @param 
     * @return 
     */

    public boolean ismate (int xKing, int yKing, int currentPlayer)
    {
        // Comentario, Puede_serCapturada es el mismo método que puede_serInterceptada, ambos revisan
        // Si cierta casilla entre el atacante y el atacado puede ser atacada por una tercera, en defensa
        // Del atacado. Reducir de inmediato!.
        boolean ret = true;
        
        cout ("******************\n>>Escapar");
        if (puede_escapar (xKing, yKing, currentPlayer)){
            cout("- Si puede escapar");
            ret = false;
        }
        else
        {
            cout("- No puede escapar");
            
        }
        cout ("----------------");
        cout (">>Intersectar");
        if (puede_serIntercectado (xKing, yKing)){
            cout(" Si puede ser interceptada");
            ret = false;
        }
        else
        {
            cout("- No puede ser interceptada");
        }
        cout ("----------------");
        cout (">>Capturar");
        if (puede_serCapturada (currentPlayer))
        {
            cout(" Si puede ser capturada");
            ret = false;
        }
        else
        {
            cout("- No puede ser capturada");
        }
        cout ("----------------");
        return ret;
    }

    /**
     * Revisa si la ficha atacante puede ser interceptada por alguna ficha del rey atacado
     * @param xKing
     * @param yKing
     * @return 
     */
    public boolean puede_serIntercectado (int xKing, int yKing)
    {
        String strtemp = atacantes.get (0);
        int xAttack = Integer.parseInt (strtemp.substring (0, 1));
        int yAttack = Integer.parseInt (strtemp.substring (1, 2));
        ArrayList<String> casillas = new ArrayList<String> ();
        //*-------*      *
        //  *            |          *
        //      -        |       -
        //          *    |    *  
        //               *
        // Enumera las casillas entre el rey y la ficha atacante
        while (xKing != xAttack || yKing != yAttack)
        {
            // Agregamos las coordenadas
            casillas.add ("" + xKing + "" + yKing);
            if (xKing != xAttack)
            {
                if (xKing < xAttack)
                {
                    ++xKing;
                }
                else
                {
                    --xKing;
                }
            }
            if (yKing != yAttack)
            {
                if (yKing < yAttack)
                {
                    ++yKing;
                }
                else
                {
                    --yKing;
                }
            }
        }
        // remueve la casilla del rey :D
        casillas.remove (0);
        // Asumiendo que existe un arreglo con todas las posibles casillas para ser interceptadas...
        // Asumiendo que todas las casillas entre el atacante y el rey son vacias
        // Verificado que el atacante no sea {Peon, Rey(?) o caballo}
        // Entonces...
        //cout("Ataca: "+ strtemp);
        //for (String g: casillas)
        //    cout (">> Casilla: " + g);
        boolean veredicto = false;
        int tipoPieza = cellMatrixCopy.getPieceCell (xAttack, yAttack);
        // Si no es un {0,2,5} entonces...
        //0 peon, 1 torre, 2 caballo, 3 alfil, 4 reina, 5 rey, 6 empty
        if (tipoPieza != 0 && tipoPieza != 2 && tipoPieza != 5 && tipoPieza != 6)
        {
            System.out.print("Casillas: ");
            for (int k = 0; k < casillas.size (); k++)
            {
                String temp = casillas.get (k);
                int x = Integer.parseInt (temp.substring (0, 1));
                int y = Integer.parseInt (temp.substring (1, 2));
                // Errore conocidos: El rey no puede intersectar, el peon no puede intersectar en diagonal pero lo hacen ....
                if (jaqueAdmin.canBecapture(not(currentPlayer), x, y, false, cellMatrixCopy, false))
                {
                    System.out.print(temp+" ");
                    System.out.print("("+jaqueAdmin.getNumAtacantes()+")");
                    veredicto = true;
                }
            }
        }
        return veredicto;
    }
    
    public int not(int ply)
    {
        return (ply == 1)? 2 : 1;
    }

    // Verifica si la ficha atacante puede ser capturada.
    public boolean puede_serCapturada (int currentPlayer)
    {
        // Si los atacantes son más de dos, es Imposible capturar a ambos en una sola jugada...
        // Nota: Cualquier pieza, excepto el rey, puede ser potencialmente capturada.
        if (atacantes.size () == 1)
        {
            String temp = atacantes.get (0);
            int xAttack = Integer.parseInt (temp.substring (0, 1));
            int yAttack = Integer.parseInt (temp.substring (1, 2));
            if (jaqueAdmin.canBecapture(not(currentPlayer), xAttack, yAttack, false, cellMatrixCopy, false))
            {
                System.out.print("("+jaqueAdmin.getNumAtacantes()+")");
                return true;
            }
        }
        return false;
    }

    public boolean puede_escapar (int xKing, int yKing, int currentPlayer)
    {
        //boolean resultadoparcial = false;
        ArrayList<String> casillasPotenciales = new ArrayList<String> ();
        //Hallamos las casillas Adyacentes vacias (o del enemigo)
        encontrarAdj(xKing, yKing, currentPlayer, casillasPotenciales);
        
        // Elimina el rey en su casilla original
        cellMatrixCopy.setPieceCell(xKing, yKing, 6);
        cellMatrixCopy.setPlayerCell(xKing, yKing, 0);
        //Evaluamos el Jaque en cada casilla adyacente
        for (int k = 0; k < casillasPotenciales.size (); k++)
        {
            String temp = casillasPotenciales.get (k);
            int xAttack = Integer.parseInt (temp.substring (0, 1));
            int yAttack = Integer.parseInt (temp.substring (1, 2));
            // Colocamos el rey en una nueva casilla
            
            if (jaqueAdmin.canBecapture(currentPlayer, xAttack, yAttack, false, cellMatrixCopy, true))
            {
                casillasPotenciales.remove(k);
                --k;
            }
        }
        cellMatrixCopy.setPieceCell(xKing, yKing, 5);
        cellMatrixCopy.setPlayerCell(xKing, yKing, currentPlayer);
        System.out.print("Casillas: ");
        for (String c : casillasPotenciales)
            System.out.print(c+" ");
        System.out.println("");
        return casillasPotenciales.size() > 0;
    }

    private void encontrarAdj(int xKing, int yKing, int currentPlayer, ArrayList<String> casillasPotenciales)
    {
        // Desde la izquierda hacia la derecha del rey...
        for (int i = xKing - 1; i <= xKing + 1; i++)
        {
            // Dentro del intervalo y E [0,7]
            if (i >= 0 && i <= 7)
            {
                // Desde abajo hacia arriba del rey...
                for (int y = yKing - 1; y <= yKing + 1; y++)
                {
                    // Dentro del intervalo y E [0,7]
                    if (y >= 0 && y <= 7)
                    {
                        // Si la casilla no me pertenece (Esta vacia o es del enemigo)
                        if (cellMatrixCopy.getPlayerCell (i, y) != currentPlayer)
                        {
                            casillasPotenciales.add (i + "" + y);
                        }
                    }
                }
            }
        }
    }    
    //------------
    // Getters y Setters
    //-------------
    public void setCellMatrix(objCellMatrix cellMatrix)
    {
        this.cellMatrixCopy = cellMatrix;
    }

    public void setCurrentPlayer(int currentPlayer)
    {
        this.currentPlayer = currentPlayer;
    }

    public void setAtacantes(ArrayList<String> atacantes)
    {
        this.atacantes = atacantes;
    }

    public void setReyHaSidoMovido(boolean[] reyHaSidoMovido)
    {
        this.reyHaSidoMovido = reyHaSidoMovido;
    }

    public void setStatusTorre(boolean statusTorre)
    {
        this.statusTorre = statusTorre;
    }
    
    public void actualizar (objCellMatrix cellMatrix, int currentPlayer, ArrayList<String> atacantes, boolean[] reyHaSidoMovido, boolean statusTorre)
    {           
            setStatusTorre(statusTorre); 
            setReyHaSidoMovido(reyHaSidoMovido);
            setAtacantes(atacantes);
            setCurrentPlayer(currentPlayer);
            setCellMatrix(cellMatrix);
            jaqueAdmin = new Jaque(cellMatrix);
    }
    // -------------------
    // Otras funciones [Borrar]
    // ---------------------
    public void cout (ArrayList j)
    {
        for (int i=0; i< j.size();i++)
        System.out.print(j+" ");
        System.out.println(" ");
    }
    public void cout (String j)
    {
        System.out.println("\n "+j);
    }
    
}
