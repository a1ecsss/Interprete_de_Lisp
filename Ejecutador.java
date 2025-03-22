import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
* clase que se encarga de evaluar y ejecutar expresiones lisp
*/
public class Ejecutador {
    public Environment environment;
    private final List<ISExpression> evaluadores;

    /**
    * constructor que inicializa el ejecutador con su entorno y lista de evaluadores
    * @param environment entorno actual de ejecucion
    */
    public Ejecutador(Environment environment) {
        this.environment = environment;
        this.evaluadores = List.of(
            new Evaluacion(this),
            new Aritmetica(this),
            new ControlFlujo(this),
            new Comparaciones(this),
            new Logica(this),
            new Variables(this),
            new Funciones(this),
            new EntradaSalida(this),
            new Listas(this)
        );
    }

    /**
    * ejecuta una expresion lisp, puede ser un atomo o una lista
    * @param expresion objeto que representa la expresion
    * @return resultado de la evaluacion
    */
    public Object ejecutarExpresion(Object expresion) {
        // Si es una lista se ejecuta como tal
        if (!ISExpression.isAtom(expresion)) {
            return ejecutarExpresionLista((List<Object>) expresion);
        }
        if (expresion instanceof String) {
            String strExp = (String) expresion;
            // Si es un string con comillas al inicio y al final, las elimina y se retorna
            if (strExp.startsWith("\"") && strExp.endsWith("\"") && strExp.length() > 1) {
                return strExp.substring(1, strExp.length() - 1);
            }
            return environment.getVariable((String) expresion);
        }
        // Se retorna la expresion si no cumple con lo anterior
        return expresion;
    }

    /**
    * ejecuta una expresion que es una lista, buscando el evaluador apropiado
    * @param expresion lista con operador y argumentos
    * @return resultado de la evaluacion
    */
    private Object ejecutarExpresionLista(List<Object> expresion) {
        if (expresion.isEmpty()) {
            throw new IllegalArgumentException("SyntaxError: Attempt to call an empty list as a function." );
        }

        Object operador = expresion.get(0);
        // Si no es un string, error de sintaxis
        if (!(operador instanceof String)) {
            throw new IllegalArgumentException("SyntaxError: Invalid operator -> " + operador + " in expression " + expresion);
        }
        //Se autoasigna el evaluador a su clase
        for (ISExpression evaluador : evaluadores) {
            if (evaluador.getSExpressions().contains(operador)) {
                Object resultado = evaluador.evaluarExpresion(expresion);
                return resultado;
            }
        }
        //intenta ejecutarlo como una funcion
        return ejecutarFuncion((String) operador, expresion.subList(1, expresion.size())); 
    }

    /**
    * ejecuta una funcion definida por el usuario
    * @param nombreFuncion nombre de la funcion
    * @param argumentos lista de argumentos
    * @return resultado de ejecutar la funcion
    */
    private Object ejecutarFuncion(String nombreFuncion, List<Object> argumentos) {
        Defun codigoFuncionEntorno = environment.getFuncion(nombreFuncion); //si no existe la funcion tira error   
        Environment parentEnv = this.environment;
        // Si no se encuentra la funcion dentro del environment, se lanza un error
        if (codigoFuncionEntorno == null) {
            throw new RuntimeException("FunctionError: Undefined function -> " + nombreFuncion);
        }
        // Hacemos un cast seguro
        if (!(codigoFuncionEntorno.getCodigo() instanceof List)) {
            throw new RuntimeException("FunctionError: Invalid function structure -> " + nombreFuncion);
        }
        List<Object> body = (List<Object>) codigoFuncionEntorno.getCodigo();
        List<Object> parameters = codigoFuncionEntorno.getParameters();
        // Verificamos que el numero de parametros coincida con el numero de argumentos
        if (parameters.size() != argumentos.size()) {
            throw new RuntimeException("FunctionError: Parameter mismatch in function '" + nombreFuncion + "'. Expected -> "+parameters.size()+ " but got -> "+argumentos.size());
        }
        Environment newFunction = new Environment(body, parameters, parentEnv);
        // Asignamos los valores de los argumentos a los parametros
        for (int i = 0; i < parameters.size(); i++) {
            String variable = (String) parameters.get(i);
            Object valor = argumentos.get(i);
            newFunction.setVariable(variable, this.ejecutarExpresion(valor));
        }
        Defun codigoafter = environment.getFuncion(nombreFuncion); //si no existe la funcion tira error
        Object a = newFunction.ejecutarCodigo();
        
        return a;
    }
}
