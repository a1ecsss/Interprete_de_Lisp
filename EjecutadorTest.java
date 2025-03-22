import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class EjecutadorTest {

    private Ejecutador ejecutador;

    @Before
    public void setUp() {
        // creamos un entorno vacio para pruebas
        Environment env = new Environment(List.of(), null, null);
        this.ejecutador = new Ejecutador(env);
    }

    @Test
    public void testSuma() {
        List<Object> expresion = List.of("+", 3, 6); // (+ 3 6)
        Object resultado = ejecutador.ejecutarExpresion(expresion);
        assertEquals(9.0, resultado);
    }

    @Test
    public void testResta() {
        List<Object> expresion = List.of("-", 10, 4); // (- 10 4)
        Object resultado = ejecutador.ejecutarExpresion(expresion);
        assertEquals(6.0, resultado);
    }

    @Test
    public void testMultiplicacion() {
        List<Object> expresion = List.of("*", 2, 5); // (* 2 5)
        Object resultado = ejecutador.ejecutarExpresion(expresion);
        assertEquals(10.0, resultado);
    }
}
