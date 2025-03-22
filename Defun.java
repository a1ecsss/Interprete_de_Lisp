import java.util.List;

public class Defun {
    public static int id = 0;
    private Object codigo;
    private List<Object> parameters;

    public Defun(Object codigo, List<Object> parameters){
        this.codigo = codigo;
        this.parameters = parameters;
        id ++;
    }

    public static int getId() {
        return id;
    }

    public Object getCodigo(){
        return codigo;
    }

    public List<Object> getParameters(){
        return parameters;
    }
}

