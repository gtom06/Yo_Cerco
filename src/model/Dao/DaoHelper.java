package model.Dao;

import model.ConstantsExceptions;

import java.util.ArrayList;

public class DaoHelper {
    private DaoHelper(){
        throw new IllegalStateException(ConstantsExceptions.UTILITY_CLASS_INFO);
    }
    public static String buildSqlStringFromArrayOfIntegers(ArrayList<Integer> integerArrayList){ // (?,?,?)
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
