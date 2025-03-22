import java.util.List;

public interface ISExpression {

    List<String> getSExpressions();
    Object evaluarExpresion(List<Object> expresion);

    static boolean isAtom(Object valor) {
        //un átomo es cualquier cosa que NO sea una lista
        if (!(valor instanceof List)) {
            return true;
        }
        return false;
    }

    //verifica si un valor es un numero en Lisp
    static boolean isNumber(Object valor) {
        return valor instanceof Number;
    }

    //verifica si un numero es par
    static boolean isEven(Object valor) {
        if (isNumber(valor)) {
            return ((Number) valor).intValue() % 2 == 0;
        }
        return false;
    }

    //verifica si un numero es impar
    static boolean isOdd(Object valor) {
        if (isNumber(valor)) {
            return ((Number) valor).intValue() % 2 != 0;
        }
        return false;
    }

    //devuelve el mayor de dos numeros
    static Object max(Object a, Object b) {
        if (isNumber(a) && isNumber(b)) {
            return Math.max(((Number) a).doubleValue(), ((Number) b).doubleValue());
        }
        throw new IllegalArgumentException("EvaluationError: 'max' expects two numbers but got -> " + a + ", " + b);
    }

    //devuelve el menor de dos numeros
    static Object min(Object a, Object b) {
        if (isNumber(a) && isNumber(b)) {
            return Math.min(((Number) a).doubleValue(), ((Number) b).doubleValue());
        }
        throw new IllegalArgumentException("EvaluationError: 'min' expects two numbers but got -> " + a + ", " + b);
    }

    //verifica si un valor es nulo (NIL)
    static boolean isNil(Object valor) {
        return valor == null || (valor instanceof List && ((List<?>) valor).isEmpty());
    }
    

    //verifica si un valor es nulo (NIL)
    static boolean isT(Object valor) {
        return valor != null || (valor instanceof List && !((List<?>) valor).isEmpty());
    }
    

}