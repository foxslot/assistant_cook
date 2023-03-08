package assistant_cook.MenuItems;

import assistant_cook.ConnectToBase.connectToDataBaseRecipes;
import assistant_cook.Menu.ChoiseDishMenu;

import java.util.HashMap;
import java.util.Map;

public class DeleteRecipe {

    public void startDeleteRecipe() {

        HashMap<Integer, String> findDishes = connectToDataBaseRecipes.getAllDishes();

        ChoiseDishMenu choiseDishMenu = new ChoiseDishMenu();

        int count = 1;
        String textMenuItem = "";
        for (Map.Entry<Integer, String> pair : findDishes.entrySet()) {
            textMenuItem = "- " + String.valueOf(count) + ". " + pair.getValue();
            choiseDishMenu.addMenuItems(textMenuItem, String.valueOf(count), pair.getKey());
            count++;
        }
        choiseDishMenu.displayMenu();

        int id_dish = choiseDishMenu.getID_MenuItem();

        connectToDataBaseRecipes.deleteRecipeByID(id_dish);

        System.out.println("_____________________________________________________________________________________________________");
        System.out.println("Рецепт удален");
        System.out.println("_____________________________________________________________________________________________________");

    }

}
