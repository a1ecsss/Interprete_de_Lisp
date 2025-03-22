import java.util.List;

public interface ISExpression {

    /**
    * devuelve los simbolos que la clase puede evaluar
    * @return lista de strings
    */
    List<String> getSExpressions();

    /**
    * evalua una expresion lisp
    * @param expresion lista con la expresion a evaluar
    * @return resultado de la evaluacion
    */
    Object evaluarExpresion(List<Object> expresion);

    /**
    * verifica si un valor es un atomo (no lista)
    * @param valor objeto a verificar
    * @return true si no es lista
    */
    static boolean isAtom(Object valor) {
        //un atomo es cualquier cosa que NO sea una lista
        if (!(valor instanceof List)) {
            return true;
        }
        return false;
    }

    /**
    * verifica si un valor es un numero
    * @param valor objeto a verificar
    * @return true si es instancia de Number
    */
    static boolean isNumber(Object valor) {
        return valor instanceof Number;
    }

    /**
    * verifica si un numero es par
    * @param valor numero a verificar
    * @return true si es par
    */
    static boolean isEven(Object valor) {
        if (isNumber(valor)) {
            return ((Number) valor).intValue() % 2 == 0;
        }
        return false;
    }

    /**
    * verifica si un numero es impar
    * @param valor numero a verificar
    * @return true si es impar
    */
    static boolean isOdd(Object valor) {
        if (isNumber(valor)) {
            return ((Number) valor).intValue() % 2 != 0;
        }
        return false;
    }

    /**
    * devuelve el mayor de dos numeros
    * @param a primer numero
    * @param b segundo numero
    * @return el mayor valor
    */
    static Object max(Object a, Object b) {
        if (isNumber(a) && isNumber(b)) {
            return Math.max(((Number) a).doubleValue(), ((Number) b).doubleValue());
        }
        throw new IllegalArgumentException("EvaluationError: 'max' expects two numbers but got -> " + a + ", " + b);
    }

    /**
    * devuelve el menor de dos numeros
    * @param a primer numero
    * @param b segundo numero
    * @return el menor valor
    */
    static Object min(Object a, Object b) {
        if (isNumber(a) && isNumber(b)) {
            return Math.min(((Number) a).doubleValue(), ((Number) b).doubleValue());
        }
        throw new IllegalArgumentException("EvaluationError: 'min' expects two numbers but got -> " + a + ", " + b);
    }

    /**
    * verifica si un valor es nil
    * @param valor objeto a verificar
    * @return true si es null o lista vacia
    */
    static boolean isNil(Object valor) {
        return valor == null || (valor instanceof List && ((List<?>) valor).isEmpty());
    }

    /**
    * verifica si un valor es verdadero (no nil)
    * @param valor objeto a verificar
    * @return true si no es nil
    */
    static boolean isT(Object valor) {
        return valor != null || (valor instanceof List && !((List<?>) valor).isEmpty());
    }

}
