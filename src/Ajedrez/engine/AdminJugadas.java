/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ajedrez.engine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JTextArea;

/**
 *
 * @author mandrescc
 */
public class AdminJugadas
{
    //-----------------------------
    // Constantes
    //-----------------------------

    private final String fichas = " TCADR";
    //-----------------------------
    // Variables
    //-----------------------------
    ArrayList<String> historialJugadas;
    public static JTextArea texto1, texto2;
    // Temporal
    public String[][] matrizConversion =
    {
        {
            "1 8", "2 8", "3 8", "4 8", "5 8", "6 8", "7 8", "8 8"
        },
        {
            "1 7", "2 7", "3 7", "4 7", "5 7", "6 7", "7 7", "8 7"
        },
        {
            "1 6", "2 6", "3 6", "4 6", "5 6", "6 6", "7 6", "8 6"
        },
        {
            "1 5", "2 5", "3 5", "4 5", "5 5", "6 5", "7 5", "8 5"
        },
        {
            "1 4", "2 4", "3 4", "4 4", "5 4", "6 4", "7 4", "8 4"
        },
        {
            "1 3", "2 3", "3 3", "4 3", "5 3", "6 3", "7 3", "8 3"
        },
        {
            "1 2", "2 2", "3 2", "4 2", "5 2", "6 2", "7 2", "8 2"
        },
        {
            "1 1", "2 1", "3 1", "4 1", "5 1", "6 1", "7 1", "8 1"
        }
    };
    //-----------------------------
    // Constructor
    //-----------------------------

    public AdminJugadas()
    {
        historialJugadas = new ArrayList();

        texto1 = new JTextArea("", 20, 5);
        texto1.setBackground(Color.WHITE);
        texto1.setForeground(Color.BLACK);
        texto1.setEditable(false);

        texto2 = new JTextArea("", 20, 5);
        texto2.setBackground(Color.BLACK);
        texto2.setForeground(Color.WHITE);
        texto2.setEditable(false);
    }

    public AdminJugadas(ArrayList<String> historialJugadas)
    {
        this.historialJugadas = historialJugadas;
    }

    public void almacenarJugada(int pieceBeingDragged, int startRow, int startColumn, int newDesRow, int newDesColumn, int flagEnroque)
    {
        String jugada;
        if (flagEnroque == 1)
        {
            jugada = "0-0";
        } else if (flagEnroque == -1)
        {
            jugada = "0-0-0";
        } else
        {
            jugada = fichas.charAt(pieceBeingDragged) + "" + (char) (startColumn + 97) + (8 - startRow) + "" + " " + fichas.charAt(pieceBeingDragged) + (char) (newDesColumn + 97) + (8 - newDesRow);
        }
        historialJugadas.add(jugada);
    }

    public void imprimirUltimaJugada(int[][] tablero, int jugadorActual)
    {
        if (jugadorActual == 1)
        {

            texto1.setText(texto1.getText() + simplificador(historialJugadas.get(historialJugadas.size() - 1), tablero) + "\n");

        } else
        {
            texto2.setText(texto2.getText() + simplificador(historialJugadas.get(historialJugadas.size() - 1), tablero) + "\n");
        }

    }

    public static JTextArea obtenerTexto1()
    {

        return texto1;
    }

    public static JTextArea obtenerTexto2()
    {

        return texto2;
    }

    public static void asignarTexto1(String s)
    {
        texto1.setText(s);
    }

    public static void asignarTexto2(String s)
    {
        texto2.setText(s);
    }

    private String simplificador(String jugada, int[][] tablero)
    {
        String decodificador = "";
        if (jugada.equals("0-0-0") || jugada.equals("0-0"))
        {
            return jugada;
        }
        String decodificacion = decodificadorPanel(jugada).substring(decodificadorPanel(jugada).length() - 3);

        int y = (int) decodificacion.charAt(0) - 49;
        int x = Math.abs(8 - ((int) decodificacion.charAt(1) - 48));

        // JOptionPane.showMessageDialog(texto1, x+" "+y);
        if (tablero[x][y] == 0)
        {
            decodificador = jugada.substring(jugada.length() - 3, jugada.length());
        } else
        {
            decodificador = jugada.substring(jugada.length() - 3, jugada.length() - 2) + "x" + jugada.substring(jugada.length() - 2, jugada.length());
        }
        /*
         * for (int i=0; i<tablero.length ; i++) { for (int j=0;
         * j<tablero.length ; j++) { System.out.print(tablero[i][j]); }
         * System.out.println(); }
         */


        return decodificador;
    }

    public String decodificadorPanel(String jugada)
    {
        if (jugada.equals("0-0-0") || jugada.equals("0-0"))
        {
            return jugada;
        }
        String decodificador = "";
        StringTokenizer token = new StringTokenizer(jugada);
        for (int i = 0; token.hasMoreElements(); i++)
        {
            String temp = token.nextToken();
            if (temp.length() == 2)
            {
                temp = " " + temp;
            }
            for (int j = 0; j < fichas.length(); j++)
            {
                if (temp.charAt(0) == fichas.charAt(j))
                {
                    decodificador += j;
                    break;
                }
            }
            //System.out.println("Prueba H "+(Character.valueOf('h')-96));
            decodificador = decodificador + "" + (Character.valueOf(temp.charAt(1)) - 96) + "" + temp.charAt(2) + " ";

        }
        return decodificador;
    }

    /**
     * Convierte las columnas de notación algebraica a números Ejemplos: 'A' ->
     * 0, 'B'-> 1, 'C'->2 , 'H'-> 7 resulmen: [A,H] -> [0,7]
     *
     * @param parse Caracter a ser convertido
     * @return coordenadaNumerica
     */
    public static int chessColumnToInt(char parse)
    {
        return (charToint(parse)) - 97;
    }

    /**
     * Dada una serie de jugadas en notación algebraica, retorna su equivalente
     * en notación matricial Ejemplos: "e2" -> "06 4" ,
     *
     * @param jugada
     * @return
     */
    public String decodificador(String jugada)
    {
        String decodificador = "";   // inicializamos el buffer de salida         
        StringTokenizer token = new StringTokenizer(jugada); // parimos la cadena entre espacios
        for (int i = 0; token.hasMoreElements(); i++) // Mientras el token tenga elementos...
        {
            String temp = token.nextToken(); // Saque el primer elemento de la lista
            if (temp.length() == 2) // Si el elemento en cuestion tiene longitud menor a 2, 
            {
                temp = " " + temp;
            }
            assert temp.length() > 2;// Afirmamos que la cadena es mayor que dos
            assert temp.length() == 3;// Afirmamos que la cadena es igual a tres
            for (int j = 0; j < fichas.length(); j++) // Iteramos el arreglo de las InicialesFichas para saber que número le corresponde
            {
                if (temp.charAt(0) == fichas.charAt(j))
                {
                    decodificador += j; // Si lo encuentra, entonces agregue en el buffer la posición en el arreglo
                    break;
                }
            }
            // Los dos siguientes caracteres a analizar son la columna y la fila (ambas en notación matricial)
            decodificador = decodificador
                    + tableroToMatriz(((chessColumnToInt(temp.charAt(1))) + 1) + " " + temp.charAt(2)) + "\n";
        }
        return decodificador;
    }

    /**
     * Convierte las coordenadas de un tablero a coordenadas de una matriz
     * (numericas)
     *
     * @param coordenadas Coordenas numericas "8 1"->"7 7"
     * @return
     */
    public String tableroToMatriz(String coordenadas)
    {
        // {(x y) | x,y e [1,8]} -> {(x y) | x,y e [1,8]}
        assert coordenadas.length() == 3;
        for (int i = 0; i < matrizConversion.length; i++)
        {
            for (int j = 0; j < matrizConversion[i].length; j++)
            {
                if (coordenadas.equals(matrizConversion[i][j]))
                {
                    return (i + " " + j);
                }
            }
        }
        return "";
    }

    private void limpiarMatriz(int[][] matriz, int criterioVaciado)
    {
        for (int i = 0; i < matriz.length; i++)
        {
            for (int j = 0; j < matriz[i].length; j++)
            {
                matriz[i][j] = criterioVaciado;
            }
        }
    }

    /**
     * La invocación se hace de este modo: La primera letra del nombre de la
     * ficha en mayusculas, seguido de las coordenadas en notación algebraica,
     * las grupos de fichas de los jugadores estan separados por un salto de
     * línea admin.configuracionTableroFinal("Rh8 Th1\nRa1 e4 Ta8",
     * cellMatrix.getPieceMatrix(), cellMatrix.getPlayerMatrix());
     *
     * @param posiciones
     * @param cellpieceMatrix
     * @param cellplayerMatrix
     */
    public void configuracionTableroFinal(String posiciones, int[][] cellpieceMatrix, int[][] cellplayerMatrix)
    {
        limpiarMatriz(cellpieceMatrix, 6); // Limpia la matriz de fichas
        limpiarMatriz(cellplayerMatrix, 0); // Limpia la matriz de jugadores

        StringTokenizer token = new StringTokenizer(posiciones, "-"); // Tokeniza la cadena de posiciones, primero para blancas y despues negras
        for (int i = 1; token.hasMoreElements(); i++)// Para cada jugador, empezando desde las blancas
        {
            String temp = decodificador((String) token.nextElement()); // Decodifique cada posicion del jugador i
            StringTokenizer token2 = new StringTokenizer(temp, "\n"); // Estando cada una separada por un salto de línea, tokenicelas

            while (token2.hasMoreElements())
            {
                String Ficha = token2.nextToken(); // Almacene la n-sima posicion
                int t = charToint(Ficha.charAt(0)); // Almacene el tipo de ficha
                cellplayerMatrix[charToint(Ficha.charAt(1))][charToint(Ficha.charAt(3))] = i; // Asignele el jugador (Blanco o negro)
                cellpieceMatrix[charToint(Ficha.charAt(1))][charToint(Ficha.charAt(3))] = t; // Asignele la ficha
            }
        }
    }

    /**
     * Convierte literalmente un caracter a un entero<br> ejemplos: '1' -> 1
     * <br> '8' -> 8 <br> De caracter a entero, toda letra es minuscula
     *
     * @param pars
     * @return
     */
    public static int charToint(char pars)
    {
        pars = Character.toLowerCase(pars);
        if (Character.isDigit(pars))
        {
            return Character.valueOf(pars) - 48;
        } else
        {
            return Character.valueOf(pars);
        }
    }

    /**
     * Guarda el tablero en dos arreglos, el primero para las blancas, el
     * segundo para las negras
     *
     * @param cellpieceMatrix
     * @param cellplayerMatrix
     */
    public void guardarTablero(int[][] cellpieceMatrix, int[][] cellplayerMatrix)
    {
        ArrayList<String> set1 = new ArrayList<String>();
        ArrayList<String> set2 = new ArrayList<String>();
        for (int i = 0; i < cellpieceMatrix.length; i++)
        {
            for (int j = 0; j < cellpieceMatrix.length; j++)
            {
                if (cellpieceMatrix[i][j] != 6)
                {
                    // blancas
                    if (cellplayerMatrix[i][j] == 1)
                    {
                        set1.add(fichas.charAt(cellpieceMatrix[i][j]) + "" + reverse(MatrizTotablero(i + " " + j)));
                    } else
                    {
                        set2.add(fichas.charAt(cellpieceMatrix[i][j]) + "" + reverse(MatrizTotablero(i + " " + j)));
                    }
                }
            }
        }
        /*
         * System.out.println("[Saving]"); for (String h : set1)
         * System.out.print(h+" "); System.out.println(" "); for (String h :
         * set2) System.out.print(h+" "); System.out.println("[Saved]");
         *
         */
    }

    /**
     * Convierte las coordenadas de una matriz a coordenadas de tablero
     * (numericas)
     *
     * @param coordenadasMatriz
     * @return coordenadasTablero
     */
    public String MatrizTotablero(String coordenadas)
    {
        // {(x y) | x,y e [1,8]} -> {(x y) | x,y e [1,8]}
        assert coordenadas.length() == 3;
        return matrizConversion[charToint(coordenadas.charAt(0))][charToint(coordenadas.charAt(2))];
    }

    private String reverse(String MatrizTotablero)
    {
        return (intTochessColumn(charToint(MatrizTotablero.charAt(0)) - 1)) + "" + MatrizTotablero.charAt(2);
    }

    /**
     * Convierte los números en notación matricial [0,7] a caracteres [A,H]
     *
     * @param parse
     * @return
     */
    public static char intTochessColumn(int parse)
    {
        return (char) (parse + 97);
    }

    /*
     * {Th1 Tg1}
     * Ta8 Te8
     * Tg1 Tg8
     * e4  e3
     * Tg8 Te8
     * e3  e2
     * Te8 Te2
     * ----------
     * "e2  e4"
       "e7  e5"
       "Cg1 Cf3"
       "Cb8 Cc6"
     * @param ArrayList<String>-historialJugadas
     * 
     * Dado un arreglo de cadenas, las cuales contienen la posición inicial
     * y la posición final de una ficha durante un turno, imprimir el tablero.
     * mousePressed = Función que recoje y almacena initRow y initColumn
     * mouseReleased = Función que ordena aplicar la jugada. Entrega desRow y desColumn ()
     * ** Lo mejor es obtener un objet WindowChessBoard y midificarlo a consideración.
     * Desde WindowChessBoard
     * ArrayList jugadas = new ArrayList<String>();
        jugadas.add(  "e2 e4" );
        jugadas.add( "e7  e5"  );
        jugadas.add( "Cg1 Cf3"  );
        jugadas.add(  "Cb8 Cc6" );
        jugadas.add(  "Af1 Ac4" );
        admin.jugadasDisplay( jugadas, this);
     */
    public void jugadasDisplay(ArrayList<String> historialJugadas, WindowChessBoard tablero, boolean display)
    {
        for (int i=0; i < historialJugadas.size() ; i++)
        {
            int initRow, initColumn, desRow, desColumn;
            StringTokenizer token = new StringTokenizer(historialJugadas.get(i));
            // Para la inicial
            String init = token.nextToken();
            init = decodificador(init);
            assert init.length() >= 3;
            initColumn = charToint(init.charAt(1));
            initRow = charToint(init.charAt(3));
            // Para la final
            String fin = token.nextToken();
            fin = (fin.length()!=3)? decodificador(" "+ fin ): decodificador(fin);
            assert fin.length() >= 3;
            
            desColumn = charToint(fin.charAt(1));
            desRow = charToint(fin.charAt(3));
            // Ya teniendo ambas variables.
            
            tablero.setStartRow(initColumn);
            tablero.setStartColumn(initRow);
            tablero.setCurrentPlayer ((i % 2 == 0)? 1 : 2);
            int pieceBeingDragged = tablero.cellMatrix.getPieceCell (initColumn, initRow);
            
            // Borra la casilla... 
            tablero.cellMatrix.setPieceCell (initColumn,initRow, 6);
            tablero.cellMatrix.setPlayerCell (initColumn,initRow, 0);
            tablero.checkMove (initColumn,initRow,desColumn,desRow,pieceBeingDragged);
            if (display)
            {
                tablero.printBoard();
            }
        }
    }
    
//    private void cout(String g)
//    {
//        System.out.println(g);
//    }
}