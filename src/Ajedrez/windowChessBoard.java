
package Ajedrez;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.JDialog;
import java.util.*;


public class windowChessBoard extends objChessBoard implements MouseListener, MouseMotionListener {
    
    private final int refreshRate = 5;
    
    private Image[][] imgPlayer = new Image[2][6];
    private String[] strPlayerName = {"Jugador 1", "Jugador 2"};
    private boolean[] hasidomovida = {false, false, false, false};
    private boolean[] reyHaSidoMovido={false, false};
    private String strStatusMsg = "";
    private objCellMatrix cellMatrix = new objCellMatrix();
    private int currentPlayer = 1, startRow = 0, startColumn = 0, pieceBeingDragged = 0;
    private int startingX = 0, startingY = 0, currentX = 0, currentY = 0, refreshCounter = 0;
    private boolean firstTime = true, hasWon = false, isDragging = false;
    ArrayList<String> atacantes = new ArrayList<String>();
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
    //Sirve para cambiar los mensajes que vez en el tablero
    /**
     * Cambia los mensajes que aparecen debajo del Tablero
     * @return Estado Retorna el estado del juego, sea terminado, sin comenzar o el turno Correspondiente
     */
    private String getPlayerMsg() {
        
        if (hasWon) {
            return "Felicitaciones " + strPlayerName[currentPlayer - 1] + ", tu eres el ganadaror!";
        } else if (firstTime) {
            return "" + strPlayerName[0] + " tu juegas con las piezas blancas, " + strPlayerName[1] + " tu juegas con las piezas negras. Presiona nuevo juego para empezar";
        } else {
            return "" + strPlayerName[currentPlayer - 1] + " mueve";
        }
        
    }
    
    //Comiezan el juego de nuevo
    /**
     * Inicia de Nuevo el Juego
     */
    private void resetBoard() 
    {
        
        hasWon = false;
        currentPlayer = 1;
        strStatusMsg = getPlayerMsg();
        cellMatrix.resetMatrix();
        repaint();
        for(int i=0; i<hasidomovida.length ; i++)
        {
            hasidomovida[i]=false;
        }
        
        for(int i=0; i<reyHaSidoMovido.length ; i++)
        {
            reyHaSidoMovido[i]=false;
        }
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
    
    // Función que verifica si las torres ha sido movidas, capturadas o, en su
    // defecto, se encuentran en su posición inicial
    
    /* 
     * Función que verifica si las torres ha sido movidas, capturadas o, en su
     * defecto, se encuentran en su posición inicial
     * A Milton le Gusta Diana!!!! Lero Lero Lero!!!
     */
    private void updateRook()
    {
        // Verifica si la primera torre (Esquina sup. izquierda) esta y es del jugador actual
        if (cellMatrix.getPieceCell(0, 0)!=1&&cellMatrix.getPlayerCell(0, 0)!=currentPlayer)
                hasidomovida[0]= true;
        // Verifica si la segunda torre (Esquina sup. derecha) esta y es del jugador actual
        if (cellMatrix.getPieceCell(0, 7)!=1&&cellMatrix.getPlayerCell(0, 7)!=currentPlayer)
                hasidomovida[1]= true;
        // Verifica si la tercera torre (Esquina inferior izquierda) esta y es del jugador actual
        if (cellMatrix.getPieceCell(7, 0)!=1&&cellMatrix.getPlayerCell(7, 0)!=currentPlayer)
                hasidomovida[2]= true;
        // Verifica si la cuarta torre (Esquina inferior derecha) esta y es del jugador actual
        if (cellMatrix.getPieceCell(7, 7)!=1&&cellMatrix.getPlayerCell(7, 7)!=currentPlayer)
                hasidomovida[3]= true;
    }
    /*
     * Retorna la posición del arreglo de la torre más cercana dadas las coordenadas finales del mov. del rey.
     * return [0,3]
     */
    private int torreMasProxima(int desRow, int desColumn)
    {
        //(desRow == 0 ) ? ((desColumn==0) ? return 0 : return 1) : ((desColumn==0 )? return 2 : return 3);
        if (desRow == 0 )
        { // Dom {0,1}
            if (desColumn<=4)
            {
                return 0;
            }
            return 1;
        }
        else // (destRow == 7 )
        {// Dom {2,3}
            if (desColumn<=4)
            {
                return 2;
            }
            return 3;
        }
        
    }
    
    //Valida los movimientos
    /**
     * Valida el Movimiento de la pieza Seleccionada
     * @param desRow Coordenadas de la Fila
     * @param desColumn Coordenadas de la Columna
     */
        private void checkMove(int desRow, int desColumn) {

        boolean legalMove = false;
        int enroque = 0;
        
        updateRook();
        
        if (cellMatrix.getPlayerCell(desRow,desColumn) == currentPlayer) {
            strStatusMsg = "No puedes mover";
        }
        else
        {

            switch (pieceBeingDragged) 
            {
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
                    // statusTorre = false si es légitimo el movimiento
                    boolean statusTorre = !hasidomovida[torreMasProxima(desRow, desColumn)];
                    // Agregue como parametro statusTorre
                    legalMove = kingObject.legalMove(startRow,
                                                     startColumn,
                                                     desRow,
                                                     desColumn,
                                                     cellMatrix.getPlayerMatrix(),
                                                     currentPlayer,
                                                     statusTorre, reyHaSidoMovido[currentPlayer-1]);
                    
                    enroque = kingObject.getEnroque();                    
                    kingObject.setEnroque(0);
                    break;
            }

        }
        
        // Ha sido una movida adecuada?
        if (legalMove) {

            int newDesRow = 0;
            int newDesColumn = 0;

            // pieceBeingDragged
            switch (pieceBeingDragged) 
            {

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
                    newDesRow = kingObject.getDesRow();
                    newDesColumn = kingObject.getDesColumn();
                    reyHaSidoMovido[currentPlayer-1]=true;
                    if (enroque!=0)
                    {
                        if(enroque==1){
                        cellMatrix.setPieceCell(newDesRow, newDesColumn-1, 1);
                        cellMatrix.setPlayerCell(newDesRow, newDesColumn-1, currentPlayer);
                        cellMatrix.setPieceCell(newDesRow, newDesColumn+1, 6);
                        cellMatrix.setPlayerCell(newDesRow, newDesColumn+1, 0);
                        }
                        else if (enroque==-1){
                        cellMatrix.setPieceCell(newDesRow, newDesColumn+1, 1);
                        cellMatrix.setPlayerCell(newDesRow, newDesColumn+1, currentPlayer);
                        cellMatrix.setPieceCell(newDesRow, newDesColumn-2, 6);
                        cellMatrix.setPlayerCell(newDesRow, newDesColumn-2, 0);
                        }
                    }
                    

                break;

            }

            cellMatrix.setPlayerCell(newDesRow, newDesColumn, currentPlayer);

            //Si el peon llega al final puede cambiar por cualquier pieza es asi por
            //el cual debe de poder eligir q pieza a cambir

            if (pieceBeingDragged == 0 && (newDesRow == 0 || newDesRow == 7))
            {
                promocion ( newDesRow, newDesColumn);
            }
            // 
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
            switch (pieceBeingDragged) 
            {

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
        // Hay Jaque para el otro jugador despues de currentPlayer?
        if (checkCheck(currentPlayer))
        {
            System.out.println("Estas en Jaque");
        }
        // Eliminar en cuanto pueda:
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
    //********************************************    
    /*
    * Esta función
    * @param 
    * @return 
    */
                
	public boolean ismate(int xKing,int yKing, int  currentPlayer)
	{
            // Comentario, Puede_serCapturada es el mismo método que puede_serInterceptada, ambos revisan
            // Si cierta casilla entre el atacante y el atacado puede ser atacada por una tercera, en defensa
            // Del atacado. Reducir de inmediato!.
            return !(puede_escapar(xKing,yKing, currentPlayer)|| puede_serCapturada(currentPlayer) ||puede_serInterceptado(xKing,yKing));
	}
        
        public boolean puede_serInterceptado(int xKing,int yKing)
        {
            int xAttack = (int)(atacantes.get(0)).charAt(0);
            int yAttack = (int)(atacantes.get(0)).charAt(1);
            ArrayList<string> casillas = new ArrayList<string>;
            //*-------*      *
            //  *            |          *
            //      -        |       -
            //          *    |    *  
            //               *
			while( xKing != xAttack || yKing != yAttack)
			{
				// Agregamos las coordenadas
				casillas.add(""+xKing+""+yKing);
			   	if (xKing != xAttack)
			   	{
			   		(xKing < xAttack) ? ++xKing : --xKing;
			   	}
			   	if (y != yAttack)
			   	{
			       (yKing < yAttack) ? ++yKing : --yKing;
			   	}
	    	}
            // Asumiendo que existe un arreglo con todas las posibles casillas para ser interceptadas...
            // Asumiendo que todas las casillas entre el atacante y el rey son vacias
            // Verificado que el atacante no sea {Peon, Rey(?) o caballo}
            // Entonces...
            
            int tipoPieza = cellMatrix.getPieceCell(xAttack,yAttack);
            // Si no es un 
            //0 peon, 1 torre, 2 caballo, 3 alfil, 4 reina, 5 rey, 6 empty
            if (tipoPieza != 0 && tipoPieza != 2 && tipoPieza != 5 && tipoPieza != 6)
            {
                // Similar a "PuedeEscapar?"
                boolean resultadoparcial = false;
                for(int k=0;k<casillas.size();k++)
                {
                    String temp=casillas.get(k);
                    //para cada pieza del enemigo evaluamos si ataca la casilla adyacente al rey
                    for (int i=0; i<8 ; i++)
                    {
                        for (int j=0; j<8 ; j++)
                        {
                            if(cellMatrix.getPieceCell(i,j) != 6 && cellMatrix.getPlayerCell(i,j) != currentPlayer)
                            {   
                                resultadoparcial=resultadoparcial||(checkPieceCheck(i,j,(int)temp.charAt(0), (int)temp.charAt(1), currentPlayer));
                            }
                        }
                    }
                    if(!resultadoparcial)
                       return true;
                    }
            }
                
            return false;
        }
        
        // Verifica si la ficha atacante puede ser capturada.
        public boolean puede_serCapturada(int currentPlayer)
        {
            // Si los atacantes son más de dos, es Imposible capturar a ambos en una sola jugada...
            // Nota: Cualquier pieza, excepto el rey, puede ser potencialmente capturada.
            if(atacantes.size()==1)
            {
                int xAttack = (int)(atacantes.get(0)).charAt(0);
                int yAttack = (int)(atacantes.get(0)).charAt(1);
                for (int i=0; i<8 ; i++)
                {
                    for (int j=0; j<8 ; j++)
                    {
                        // Aqui cambia un poco, Si mis fichas pueden estan en capacidad de atacar a la pieza que amenaza al rey.
                        if(cellMatrix.getPieceCell(i,j) != 6 && cellMatrix.getPlayerCell(i,j) == currentPlayer)
                        {   
                            // i,j =atacante ; 
                            if(checkPieceCheck(i,j,xAttack,yAttack, currentPlayer))
                                return true;
                        }
                    }
                }
                
            }
            return false;
        }
        
        public boolean puede_escapar(int xKing, int yKing, int currentPlayer)
        {
            boolean resultadoparcial=false;
            
            
            ArrayList<String> casillasPotenciales = new ArrayList<String>();
           //Hallamos las casillas Adyacentes
        for(int i=yKing-1; i<=yKing+1; i++)
        {
                if(i >= 0 && i <= 7)
                {
                        for(int y=xKing-1; y<=xKing+1; y++)
                        {
                                if (y >=0 && y <= 7)
                                {
                                       if(cellMatrix.getPlayerCell(i, y)!=currentPlayer)
                                       {
                                           if(i!=yKing&&y!=xKing)
                                           casillasPotenciales.add(""+i+y);
                                       }
                                }
                        }
                }
        } 
        
        //Evaluamos el Jaque en cada casilla adyacente
        for(int k=0;k<casillasPotenciales.size();k++)
       {
        String temp=casillasPotenciales.get(k);
        
        //para cada pieza del enemigo evaluamos si ataca la casilla adyacente al rey
            for (int i=0; i<8 ; i++)
            {
                for (int j=0; j<8 ; j++)
                {
                    if(cellMatrix.getPieceCell(i,j) != 6 && cellMatrix.getPlayerCell(i,j) != currentPlayer)
                    {   
                        resultadoparcial=resultadoparcial||(checkPieceCheck(i,j,(int)temp.charAt(0), (int)temp.charAt(1), currentPlayer));
                            
                    }
                }
            }
            if(!resultadoparcial)
               return true;
            
               
            }
        return false;
        
        }
    
    //********************************************
    /*
    * Esta función recorre todo el tablero de las fichas, seleccionando
    * Aquellas que pertenezcan al jugandor contrario
    * 	@param currentPlayer Jugador actual
    *	@return boolean Si el rey esta en Jaque;
    */
    public boolean checkCheck(int currentPlayer)
    {
        int xKing=0, yKing=0;
        // Primero encontraremos a nuestro Rey
        for (int i=0; i<8; i++)
        {
            for (int j=0; j <8; j++)
            {
            // 5 = Rey; Si el número 5 se encuentra en alguna casilla de la matriz y pertenece al jugador seleccionado
             if(cellMatrix.getPieceCell(i,j) == 5 && cellMatrix.getPlayerCell(i,j) == currentPlayer)
             {
                 xKing=i;
                 yKing=j;
                 break;
             }
            }
        }
        boolean hayJaque=false;
        // Vamos encontrando cada ficha y determinaremos si estan en alguna posición
        // Ataca al rey del currentPlayer
        for (int i=0; i<8 ; i++)
        {
            for (int j=0; j<8 ; j++)
            {
            	// Si la casilla no es vacia y pertenece al jugador al jugador contrario entonces...
                if(cellMatrix.getPieceCell(i,j) != 6 && cellMatrix.getPlayerCell(i,j) != currentPlayer)
                {
                	// Si la ficha antes seleccionada esta en capacidad de amenazar el rey entonces...
                    if (checkPieceCheck(i,j,xKing, yKing, currentPlayer))
                    {
                    	// Almacenaremos las coordenadas en una pila.
                        atacantes.add(i+""+j);
                        hayJaque = true;
                    }
                }
            }
        }
        return hayJaque;
    }
    /*
    * 	@param i coordenada en x de la ficha atacante
    * 	@param j coordenada en y de la ficha atacante
    * 	@param xKing coordenada en x de la ficha posiblemente atacada
    * 	@param yKing coordenada en y de la ficha posiblemente atacada
    * 	@param currentPlayer jugador posiblemente atacado
    *	@return boolean Si la pieza esta siendo atacada o no;
    */
    public boolean checkPieceCheck(int i, int j,int xKing,int yKing,int currentPlayer)
    {
        boolean jaque=false;
        switch (cellMatrix.getPieceCell(i,j))
        {
                case 0: jaque = pawnObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix(), currentPlayer);
                    break;
                case 1: jaque = rockObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix());
                    break;
                case 2: jaque = knightObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix());
                    break;
                case 3: jaque = bishopObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix());
                    break;
                case 4: jaque = queenObject.legalMove(i, j, xKing, yKing, cellMatrix.getPlayerMatrix());
                    break;
                default:
                    break;
        }
       /* if (jaque){
        if(ismate(xKing, yKing, currentPlayer))
                System.out.println("mate");}*/
        return jaque;
    }
}
