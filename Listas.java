import java.util.List;
import java.util.ArrayList;

public class Listas implements ISExpression {
    private static final List<String> SExpressions = List.of("list", "car", "cdr", "cons", "append", "length", "reverse");
    private final Ejecutador ejecutador;

    public Listas(Ejecutador ejecutador) {
        this.ejecutador = ejecutador;
    }

    @Override
    public List<String> getSExpressions() {
        return SExpressions;
    }

    @Override
    public Object evaluarExpresion(List<Object> expresion) {
        if (expresion.isEmpty()) {
            throw new IllegalArgumentException("ListError: Invalid list expression -> " + expresion);
        }
        // Se evalúa cada expresión antes de asignarlas a una lista
        String operador = (String) expresion.get(0);

        /* 
        
        */
        
        List<Object> argumentos = expresion.subList(1, expresion.size());
        Object evalList;
        switch (operador) {
            case "list":
                //System.out.println("expresione n list: "+expresion);
                List<Object> evalargumentos = new ArrayList<>();
                for (int i = 0; i < argumentos.size(); i++) {
                    evalargumentos.add(ejecutador.ejecutarExpresion(argumentos.get(i))); 
                }
                //System.out.println("argumentos evaluados: "+argumentos);
                return new ArrayList<>(evalargumentos);  // Devuelve una lista con los elementos dados.

            case "car":
                if (argumentos.size() != 1) {
                    throw new IllegalArgumentException("ListError: 'car' expects exactly one argument -> " + expresion);
                }
                evalList = ejecutador.ejecutarExpresion(argumentos.get(0));
                if (ISExpression.isAtom(evalList)) {
                    throw new IllegalArgumentException("ListError: The value "+ evalList +" is not of type LIST");
                }
                List<?> carList = (List<?>) evalList;
                if (carList.isEmpty()) {
                    throw new IllegalArgumentException("ListError: Cannot take car of an empty list");
                }                
                //System.out.println("carlist: "+carList);
                return carList.get(0);  // Devuelve el primer elemento.

            case "cdr":
                if (argumentos.size() != 1) {
                    throw new IllegalArgumentException("ListError: 'cdr' expects exactly one argument -> " + expresion);
                }
                evalList = ejecutador.ejecutarExpresion(argumentos.get(0));
                if (ISExpression.isAtom(evalList)) {
                    throw new IllegalArgumentException("ListError: The value "+ evalList +" is not of type LIST");
                }
                List<?> cdrList = (List<?>) evalList;
                if (cdrList.isEmpty()) {
                    throw new IllegalArgumentException("ListError: Cannot take car of an empty list");
                }
                
                return  cdrList.isEmpty() ? List.of() : new ArrayList<>(cdrList.subList(1, cdrList.size())); // Devuelve la cola.

            case "cons":
                if (argumentos.size() != 2) {
                    throw new IllegalArgumentException("ListError: 'cons' expects exactly two arguments -> " + expresion);
                }

                // Convertir ambos elementos en listas
                List<Object> primerElemento;
                List<?> segundoElemento;
                evalList = ejecutador.ejecutarExpresion(argumentos.get(0));
                /*
                if (ISExpression.isAtom(evalList)) {
                    throw new IllegalArgumentException("ListError: The value "+ evalList +" is not of type LIST");
                }*/
                if(!ISExpression.isAtom(evalList)){
                    primerElemento = new ArrayList<>((List<?>) evalList);
                }else{
                    primerElemento = new ArrayList<>(List.of(evalList));
                }

                evalList = ejecutador.ejecutarExpresion(argumentos.get(1));
                /* 
                if (ISExpression.isAtom(evalList)) {
                    throw new IllegalArgumentException("ListError: The value "+ evalList +" is not of type LIST");
                }*/
                if(!ISExpression.isAtom(evalList)){
                    segundoElemento = new ArrayList<>((List<?>) evalList);
                }else{
                    segundoElemento = new ArrayList<>(List.of(evalList));
                }
            
                // Concatenar las listas 
                primerElemento.addAll(segundoElemento);
                return primerElemento;
            
                case "append":
                List<Object> resultAppend = new ArrayList<>();
                
                for (Object arg : argumentos) {
                    evalList = ejecutador.ejecutarExpresion(arg);
                    if (evalList == null) {
                        evalList = List.of();
                    }
                    if (ISExpression.isAtom(evalList)) {
                        throw new IllegalArgumentException("ListError: The value " + evalList + " is not of type LIST");
                    }
                    resultAppend.addAll((List<?>) evalList);
                }
                return resultAppend;
            

            case "length":
                if (argumentos.size() != 1) {
                    throw new IllegalArgumentException("ListError: 'length' expects exactly one argument -> " + expresion);
                }
                evalList = ejecutador.ejecutarExpresion(argumentos.get(0));
                if (ISExpression.isAtom(evalList)) {
                    throw new IllegalArgumentException("ListError: The value "+ evalList +" is not of type LIST");
                }
                List<?> listalen = (List<?>) evalList;
                return listalen.size();  // Devuelve la cantidad de elementos.

            case "reverse":
                if (argumentos.size() != 1) {
                    throw new IllegalArgumentException("ListError: 'reverse' expects exactly one argument -> " + expresion);
                }
                evalList = ejecutador.ejecutarExpresion(argumentos.get(0));
                if (ISExpression.isAtom(evalList)) {
                    throw new IllegalArgumentException("ListError: The value "+ evalList +" is not of type LIST");
                }
                List<Object> reversedList = new ArrayList<>((List<?>) evalList);
                java.util.Collections.reverse(reversedList); // Invierte la lista.
                return reversedList;  

            default:
                throw new RuntimeException("OperatorError: Unknown list operator -> " + operador);
        }
    }
}
