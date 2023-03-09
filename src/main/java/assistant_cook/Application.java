package assistant_cook;

import assistant_cook.Menu.MainMenu;
import assistant_cook.MenuItems.AddRecipe;
import assistant_cook.MenuItems.DeleteRecipe;
import assistant_cook.MenuItems.FindRecipe;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        MainMenu mainMenu = new MainMenu();
        mainMenu.displayMenu();
        int menuItemID = mainMenu.getID_MenuItem();

        if (menuItemID == 1) {
            FindRecipe findRecipe = new FindRecipe();
            findRecipe.openMenuFindRecipe();
        } else if (menuItemID == 2) {
            FindRecipe findRecipe = new FindRecipe();
            findRecipe.runSearchRecipeByIngridients();
        } else if (menuItemID == 3) {
            AddRecipe addRecipe = new AddRecipe();
            addRecipe.startAddingRecipe();
        } else if (menuItemID == 4) {
            DeleteRecipe deleteRecipe = new DeleteRecipe();
            deleteRecipe.startDeleteRecipe();
        } else if (menuItemID == 0) {
            System.out.println("Завершение...");
        } else {
            System.out.println("Не реализовано");
        }

    }
}
