package assistant_cook.Menu;

import java.util.*;

public class Menu implements Menuable{

    protected List<String> menuItems = new ArrayList<>();
    protected Map<String, Integer> commands_id = new HashMap<>();
    public void displayMenu() {
        for (String menuItem : menuItems) {
            System.out.println("");
            System.out.println(menuItem);
        }
    }
    public void addMenuItems(String nameMenuItem, String numberMenuItem, int ID_MenuItem) {
        this.menuItems.add(nameMenuItem);
        this.commands_id.put(numberMenuItem, ID_MenuItem);
    }
    public int getID_MenuItem() {
        int result = 0;
        Scanner in = new Scanner(System.in);

        while (true) {
            String strID = in.nextLine();

            if (commands_id.containsKey(strID)) {
                result = commands_id.get(strID);
                break;
            } else {
                System.out.println("___________________________");
                System.out.println("Введена некорректная команда");
            }
        }
        return result;
    }

}
