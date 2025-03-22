import java.util.List;

/**
* clase que representa una funcion definida por el usuario en lisp
*/
public class Defun {
    public static int id = 0;
    private Object codigo;
    private List<Object> parameters;

    /**
    * constructor que recibe el cuerpo y los parametros de la funcion
    * @param codigo lista de expresiones que forman el cuerpo
    * @param parameters lista de parametros esperados
    */
    public Defun(Object codigo, List<Object> parameters){
        this.codigo = codigo;
        this.parameters = parameters;
        id ++;
    }

    /**
    * devuelve el id global de la funcion
    * @return entero con el id
    */
    public static int getId() {
        return id;
    }

    /**
    * retorna el cuerpo de la funcion
    * @return objeto con el codigo
    */
    public Object getCodigo(){
        return codigo;
    }

    /**
    * retorna la lista de parametros
    * @return lista de parametros
    */
    public List<Object> getParameters(){
        return parameters;
    }
}
