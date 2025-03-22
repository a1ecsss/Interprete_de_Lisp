import java.util.*;

/**
* representa un entorno de ejecucion para el interprete lisp
*/
class Environment {

    /** referencia al entorno padre, puede ser null */
    public Environment parentEnviroment;

    /** lista de codigo a ejecutar */
    private List<Object> codigo;

    /** mapa de variables locales */
    private Map<String, Object> variables;

    /** mapa de funciones definidas */
    private Map<String, Defun> funciones;

    /** ejecutador para evaluar expresiones */
    private Ejecutador ejecutador;

    /**
    * constructor que inicializa el entorno con codigo, parametros y entorno padre
    * @param codigo lista de expresiones lisp
    * @param parameters parametros (no usado directamente)
    * @param parentEnviroment entorno padre
    */
    public Environment(List<Object> codigo, Object parameters, Environment parentEnviroment) {
        this.codigo = codigo;
        this.variables = new HashMap<>();
        this.funciones = new HashMap<>();
        this.ejecutador = new Ejecutador(this);
        this.variables.put("nil", null);
        this.variables.put("t", "T");
        this.parentEnviroment = parentEnviroment;
    }

    /**
    * ejecuta todas las expresiones del codigo y retorna el ultimo resultado
    * @return resultado final de la ejecucion
    */
    public Object ejecutarCodigo() {
        Object resultado = null;
        for (int i = 0; i < codigo.size(); i++) {
            resultado = this.ejecutador.ejecutarExpresion(codigo.get(i));
        }
        return resultado;
    }

    /**
    * devuelve el codigo del entorno
    * @return lista de expresiones
    */
    public Object getCodigo() {
        return codigo;
    }

    /**
    * retorna el mapa de variables
    * @return variables actuales
    */
    public Map<String, Object> getVariables() {
        return this.variables;  
    }

    /**
    * retorna el mapa de funciones
    * @return funciones actuales
    */
    public Map<String, Defun> getFunciones() {
        return this.funciones;  
    }

    /**
    * agrega una nueva funcion al entorno
    * @param nombre nombre de la funcion
    * @param defun cuerpo de la funcion
    */
    public void setFuncion(String nombre, Defun defun){
        funciones.put(nombre.toLowerCase(), defun);
    }

    /**
    * asigna valor a una variable
    * @param nombre identificador
    * @param valor valor a guardar
    */
    public void setVariable(String nombre, Object valor) {
        if("nil".equalsIgnoreCase(nombre) || "t".equalsIgnoreCase(nombre)){
            throw new RuntimeException("Error: Attempt to set constant symbol -> "+nombre);
        }
        variables.put(nombre.toLowerCase(), valor);
    }

    /**
    * busca una variable en el entorno y sus padres
    * @param nombre nombre de la variable
    * @return valor asociado
    */
    public Object getVariable(String nombre) {
        Environment env = this;
        while (env != null) {
            Map<String, Object> vars = env.getVariables();
            if (vars.containsKey(nombre.toLowerCase())) {
                return vars.get(nombre.toLowerCase());
            }
            env = env.parentEnviroment;
        }
        throw new RuntimeException("VariableError: Undefined variable -> " + nombre);
    }

    /**
    * busca una funcion en el entorno y sus padres
    * @param nombre nombre de la funcion
    * @return funcion encontrada
    */
    public Defun getFuncion(String nombre) {
        Environment env = this;
        while (env != null) {
            Map<String, Defun> funs = env.getFunciones();
            if (funs.containsKey(nombre.toLowerCase())) {
                return funs.get(nombre.toLowerCase());
            }
            env = env.parentEnviroment;
        }
        throw new RuntimeException("DefunError: Undefined defun -> " + nombre);
    }

    /**
    * modifica una lista en memoria usando car o cdr
    * @param lista lista objetivo
    * @param operador puede ser "car" o "cdr"
    * @param nuevoValor valor que se asignara
    */
    public void modificarEstructura(List<Object> lista, Object operador, Object nuevoValor) {
        if (operador.equals("car")) {
            if (lista.isEmpty()) {
                throw new IllegalArgumentException("SetfError: Cannot modify `car` of an empty list.");
            }
            lista.set(0, nuevoValor);  //modifica el primer elemento
        } else if (operador.equals("cdr")) {
            if (lista.isEmpty()) {
                throw new IllegalArgumentException("SetfError: Cannot modify `cdr` of an empty list.");
            }
            lista.clear();
            lista.addAll((List<?>) nuevoValor);  //eeemplaza el resto de la lista
        } else {
            throw new IllegalArgumentException("SetfError: Unsupported operation -> " + operador);
        }
    }
}
