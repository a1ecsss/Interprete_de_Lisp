import java.util.HashMap;
import java.util.List;

public class Ejecutador {
    public Environment environment;
    private final List<ISExpression> evaluadores;

    public Ejecutador(Environment environment) {
        this.environment = environment;
        this.evaluadores = List.of(
            new Aritmetica(this),
            new ControlFlujo(this)
            // Agregar más clases aquí
        );
    }

    public Object ejecutarExpresion(List<Object> expresion) {
        if (expresion == null || expresion.isEmpty()) {
            return null;
        }

        Object operador = expresion.get(0);
        if (!(operador instanceof String)) {
            throw new IllegalArgumentException("SyntaxError: Invalid operator -> " + operador + " in expression " + expresion);
        }

        for (ISExpression evaluador : evaluadores) {
            if (evaluador.getSExpressions().contains(operador)) {
                Object resultado = evaluador.evaluarExpresion(expresion);
                System.out.println(resultado);
                return resultado;
            }
        }

        throw new RuntimeException("SyntaxError: Unrecognized expression -> " + expresion);
    }
}
