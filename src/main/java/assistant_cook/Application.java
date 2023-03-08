package assistant_cook;

import assistant_cook.Menu.MainMenu;
import assistant_cook.MenuItems.AddRecipe;
import assistant_cook.MenuItems.DeleteRecipe;
import assistant_cook.MenuItems.FindRecipe;
import assistant_cook.TelegramBots.TelegramBotJavaHW;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws TelegramApiException {
//        System.out.println("worked!");
//
//        try {
//            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//            botsApi.registerBot(new TelegramBotJavaHW());
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }

        MainMenu mainMenu = new MainMenu();
        mainMenu.displayMenu();
        int menuItemID = mainMenu.getID_MenuItem();

        if (menuItemID == 1) {
            System.out.print("\033[H\033[2J");
            FindRecipe findRecipe = new FindRecipe();
            findRecipe.openMenuFindRecipe();
        } else if (menuItemID == 2) {
            System.out.print("\033[H\033[2J");
            FindRecipe findRecipe = new FindRecipe();
            findRecipe.runSearchRecipeByIngridients();
        } else if (menuItemID == 3) {
            System.out.print("\033[H\033[2J");
            AddRecipe addRecipe = new AddRecipe();
            addRecipe.startAddingRecipe();
        } else if (menuItemID == 4) {
            System.out.print("\033[H\033[2J");
            DeleteRecipe deleteRecipe = new DeleteRecipe();
            deleteRecipe.startDeleteRecipe();
        } else if (menuItemID == 0) {
            System.out.print("\033[H\033[2J");
            System.out.println("Завершение...");
        } else {
            System.out.println("Не реализовано");
        }

        //System.out.println(menuItemID);


    }
}
