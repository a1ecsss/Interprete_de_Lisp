import java.util.List;

public interface ISExpression {

    List<String> getSExpressions();
    Object evaluarExpresion(List<Object> expresion);

}