/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Ajedrez.Interfaz;

import Ajedrez.engine.WindowChessBoard;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author carlochess
 */
public class TableroTest
{
    
    public TableroTest()
    {
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of newGameConsole method, of class Tablero.
     */
    @Test
    public void testNewGameConsole()
    {
        System.out.println("newGameConsole");
        String j = "e2 e4";
        boolean display = false;
        Tablero instance = new Tablero();
        instance.newGameConsole(j, display);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }


}
