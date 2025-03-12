import java.util.Arrays;
import java.util.List;

public class ControlFlujo implements ISExpression {
    private static final List<String> SExpressions = Arrays.asList("if", "cond", "case");
    public Ejecutador ejecutador;

    public ControlFlujo(Ejecutador ejecutador) {
        this.ejecutador = ejecutador;
    }

    @Override
    public List<String> getSExpressions() {
        return SExpressions;
    }

    @Override
    public Object evaluarExpresion(List<Object> expresion) {
        // Implementación de flujo de control
        return null; // Placeholder
    }
}

