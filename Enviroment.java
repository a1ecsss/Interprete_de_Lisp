import java.util.*;

class Environment {
    public List<Object> codigo;
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
        if (!this.codigo.isEmpty()) {
            for (Object object : this.codigo) {
                if (object instanceof List) {
                    // Si es una lista, hacer un casting a List<Object>
                    try{
                        this.ejecutador.ejecutarExpresion((List<Object>) object);
                    }catch (RuntimeException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    
                }
            }
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

