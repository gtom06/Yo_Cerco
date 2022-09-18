package model.dao;

import model.ConstantsExceptions;
import java.util.List;

public class QueriesHelper {
    private QueriesHelper(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
    public static String buildSqlStringFromArrayOfIntegers(List<Integer> integerArrayList){ // (?,?,?)
        StringBuilder sb= new StringBuilder();
        sb.append("(");
        int lenght = integerArrayList.size();
        for (int i = 0; i < lenght; i++){
            sb.append(integerArrayList.get(i));
            if (i != lenght - 1) {
                sb.append(",");
            }
        }
        return sb.append(")").toString();
    }
}
