package assistant_cook.MenuItems;

import java.util.ArrayList;
import java.util.HashMap;

public class tableForRecipes {

    public HashMap<Integer, String> columns = new HashMap<>();

    public ArrayList<HashMap<String, String>> strings = new ArrayList<>();

    tableForRecipes(String[] namesColumns){
        int count = 0;
        for (String columnName: namesColumns) {
            columns.put(count, columnName);
            count++;
        }
    }


}
