import java.util.List;
import java.util.Objects;

public class Logica implements ISExpression {
    private static final List<String> SExpressions = List.of("and", "or", "not", "eq", "equal");
    private final Ejecutador ejecutador;

    public Logica(Ejecutador ejecutador) {
        this.ejecutador = ejecutador;
    }

    @Override
    public List<String> getSExpressions() {
        return SExpressions;
    }

    @Override
    public Object evaluarExpresion(List<Object> expresion) {
        if (expresion.isEmpty()) {
            throw new IllegalArgumentException("LogicError: Invalid logical expression -> " + expresion);
        }
        
        String operador = (String) expresion.get(0);
        //NOT: Solo debe tener un operando
        if (operador.equals("not")) {
            if (expresion.size() != 2) {
                throw new IllegalArgumentException("LogicError: 'not' expects exactly one argument -> " + expresion);
            }
            Object operando = expresion.get(1);
            operando = ejecutador.ejecutarExpresion(operando);
            return (ISExpression.isNil(operando)) ? "T" : null;
        }
        //eq: Solo debe tener dos operandos
        if (operador.equals("eq")) {
            if (expresion.size() != 3) {
                throw new IllegalArgumentException("LogicError: 'eq' expects exactly two arguments -> " + expresion);
            }
            Object operando1 = ejecutador.ejecutarExpresion(expresion.get(1));
            Object operando2 = ejecutador.ejecutarExpresion(expresion.get(2));
            if (ISExpression.isAtom(operando1) && ISExpression.isAtom(operando2)) {
                return (operando1 == operando2) ? "T" : null;
            }
            return null; // eq solo se aplica a atomos
            
        }

        if (operador.equals("equal")) {
            if (expresion.size() != 3) {
                throw new IllegalArgumentException("LogicError: 'equal' expects exactly two arguments -> " + expresion);
            }
            Object operando1 = ejecutador.ejecutarExpresion(expresion.get(1));
            Object operando2 = ejecutador.ejecutarExpresion(expresion.get(2));
            if (ISExpression.isNil(operando1) && ISExpression.isNil(operando2)) {
                return "T";
            }
            return (operando1 != null && operando1.equals(operando2)) ? "T" : null;
        }
        // Operaciones AND y OR
        if (expresion.size() < 3) {
            throw new IllegalArgumentException("LogicError: Invalid number of arguments for logical operation -> " + expresion);
        }

        boolean resultado = operador.equals("and");
        for (int i = 1; i < expresion.size(); i++) {
            //resultado += expresion.get(i);
            Object operando = expresion.get(i);
            operando = ejecutador.ejecutarExpresion(operando);
            switch (operador) {
                case "and" -> resultado = resultado && !ISExpression.isNil(operando);
                case "or" -> resultado = resultado || !ISExpression.isNil(operando);
            };
            if (operador.equals("and") && !resultado){
                return null;
            }
            if (operador.equals("or") && resultado) {
                return "T";
            }
            
        }
        return resultado ? "T": null;
    }
}

