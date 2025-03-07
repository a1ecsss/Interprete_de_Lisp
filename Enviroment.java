import java.util.*;

class Environment {
    private List<Object> codigo;
    private Map<String, Object> variables;
    private Map<String, Environment> funciones;
    private Ejecutador ejecutador;

    public Environment(List<Object> codigo, String name) {
        this.codigo = codigo;
        this.variables = new HashMap<>();
        this.funciones = new HashMap<>();
        this.ejecutador = new Ejecutador(this);
        this.funciones.put(name, this); // Se referencia a sí misma en funciones
    }

    public void ejecutarCodigo() {
        for (Object expresion : codigo) {
            this.ejecutador.ejecutar(expresion);
        }
    }

    public List<Object> getCodigo() {
        return codigo;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public Map<String, Environment> getFunciones() {
        return funciones;
    }
}

