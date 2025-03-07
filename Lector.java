import java.io.*;
import java.util.*;

public class Lector {
    private static List<Object> codigo = new ArrayList<>();

    public static List<Object> getCodigo(String root) throws IOException {
        if (!root.endsWith(".txt") && !root.endsWith(".lisp")) {
            throw new IllegalArgumentException("Error: Solo se permiten archivos .txt o .lisp");
        }
        String contenido = leerArchivo(root);
        codigo = parsearCodigo(contenido);
        return codigo;
    }

    private static String leerArchivo(String root) throws IOException {
        StringBuilder contenido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(root))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contenido.append(linea).append(" ");
            }
        }
        return contenido.toString().trim();
    }

    private static List<Object> parsearCodigo(String codigo) {
        return parsearLista(new StringTokenizer(codigo, " ()", true));
    }

    private static List<Object> parsearLista(StringTokenizer listaAnidadas) {
        List<Object> lista = new ArrayList<>();
        while (listaAnidadas.hasMoreTokens()) {
            String listaAnidada = listaAnidadas.nextToken().trim();
            if (listaAnidada.isEmpty()) continue;
            if (listaAnidada.equals("(")) {
                lista.add(parsearLista(listaAnidadas));
            } else if (listaAnidada.equals(")")) {
                return lista;
            } else {
                lista.add(parsearValor(listaAnidada));
            }
        }
        return lista;
    }

    private static Object parsearValor(String listaAnidada) {
        if (listaAnidada.matches("-?\\d+")) {
            return Integer.parseInt(listaAnidada);
        }
        return listaAnidada;
    }
}
