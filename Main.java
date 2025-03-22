import java.util.*;
//"C:\Program Files\Java\jdk-22\bin\java" Main
/**
* clase principal que inicia la ejecucion del interprete lisp
*/
public class Main {

    /**
    * metodo principal que carga y ejecuta el archivo de codigo lisp
    * @param args argumentos desde consola (no usados)
    */
    public static void main(String[] args) {
        String rutaArchivo = "codigo.lisp";
        List<Object> codigo = null; 
        try {
            codigo = Lector.getCodigo(rutaArchivo);
        } catch (Exception e) {
            System.err.println("FileError: An error occurred while reading the file ->" + e.getMessage());
        }
        
        if (codigo != null && !codigo.isEmpty()) {
            try {
                Environment MainEnviroment = new Environment(codigo, null, null);
                MainEnviroment.ejecutarCodigo();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.err.println("No code on file selected");
        }
    }

}
