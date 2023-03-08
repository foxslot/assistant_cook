package assistant_cook.MenuItems;

import assistant_cook.ConnectToBase.connectToDataBaseRecipes;
import assistant_cook.Menu.ChoiseDishMenu;
import assistant_cook.Menu.FindRecepsMenu;
import assistant_cook.Menu.Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FindRecipe {
    public void openMenuFindRecipe() {
        FindRecepsMenu fndRecepsMenu = new FindRecepsMenu();
        fndRecepsMenu.displayMenu();
        int ID_findRecipe = fndRecepsMenu.getID_MenuItem();

        while (true) {
            if (ID_findRecipe == 1) {
                runSearchRecipe();
                break;

            } else if (ID_findRecipe == 2) {
                runSearchRecipeByIngridients();
                break;

            } else if (ID_findRecipe == 0) {
                System.out.println("Завершение...");
                return;
            } else {
                System.out.println("Введена некорректная программа");
            }
        }

    }

    private void runSearchRecipe() {
        System.out.println("Введите название блюда или введите 1 чтобы вывести все названия всех блюд. Для выхода введите 0");

        Scanner in = new Scanner(System.in);

        while (true) {

            String strID = in.nextLine();

            if (strID.equals("1")) {
                showAllDishes();
            } else if (strID.equals("0")) {
                break;
            } else {
                System.out.println("Поиск блюда по наименованию...");
                searchDishByName(strID);
            }
            System.out.println("");
            System.out.println("Введите 0 для выхода");
        }
        return;
    }

    private void showAllDishes() {

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

        ArrayList<String> findedIngridients = connectToDataBaseRecipes.getIngridientsByID(id_dish);

        ArrayList<String> findedRecipeSteps = connectToDataBaseRecipes.getRecipeStepsByID(id_dish);

        String nameDish = findDishes.get(id_dish);

        System.out.println("________________________________________________");
        System.out.println("Рецепт блюда " + nameDish);
        System.out.println("________________________________________________");
        System.out.println("Ингриденты:");

        for (String nameIngridient : findedIngridients) {
            System.out.println(nameIngridient);
        }

        System.out.println("________________________________________________");
        System.out.println("Шаги рецептов:");

        for (String nameRecipeStep : findedRecipeSteps) {
            System.out.println(nameRecipeStep);
        }

    }

    private void searchDishByName(String NameDish) {

        HashMap<Integer, String> findedRecipes = connectToDataBaseRecipes.getRecipesByName(NameDish);

        ChoiseDishMenu choiseDishMenu = new ChoiseDishMenu();

        int count = 1;
        String textMenuItem = "";
        for (Map.Entry<Integer, String> pair : findedRecipes.entrySet()) {
            textMenuItem = "- " + String.valueOf(count) + ". " + pair.getValue();
            choiseDishMenu.addMenuItems(textMenuItem, String.valueOf(count), pair.getKey());
            count++;
        }
        choiseDishMenu.displayMenu();

        int id_dish = choiseDishMenu.getID_MenuItem();

        ArrayList<String> findedIngridients = connectToDataBaseRecipes.getIngridientsByID(id_dish);

        ArrayList<String> findedRecipeSteps = connectToDataBaseRecipes.getRecipeStepsByID(id_dish);

        String nameDish = findedRecipes.get(id_dish);

        System.out.println("________________________________________________");
        System.out.println("Рецепт блюда " + nameDish);
        System.out.println("________________________________________________");
        System.out.println("Ингриденты:");

        for (String nameIngridient : findedIngridients) {
            System.out.println(nameIngridient);
        }

        System.out.println("________________________________________________");
        System.out.println("Шаги рецептов:");

        for (String nameRecipeStep : findedRecipeSteps) {
            System.out.println(nameRecipeStep);
        }

    }

    public void runSearchRecipeByIngridients() {

        System.out.println("Поиск рецепта по ингридиентам...");
        System.out.println("Введите ингредиенты с заглавной буквы через \",\" или нажмите 0 для выхода");

        Scanner in = new Scanner(System.in);

        while (true) {

            String strListOfAvailableIngridients = in.nextLine();

            if (strListOfAvailableIngridients.equals("0")) {
                break;
            }

            String[] listOfAvailableIngridients = strListOfAvailableIngridients.split(",");

            HashMap<Integer, String> listFinedDishes = connectToDataBaseRecipes.getRecipesIdByIngridientsName(listOfAvailableIngridients);

            ChoiseDishMenu menuAvailableDishes = new ChoiseDishMenu();
            int count = 1;
            String textMenuItem = "";

            for (Map.Entry<Integer, String> menuItem : listFinedDishes.entrySet()) {

                HashMap<String, String> ingridients = connectToDataBaseRecipes.getIngridientsNameByID(menuItem.getKey());
                String strNeedToBuy = "";

                for (Map.Entry<String, String> ingridient : ingridients.entrySet()) {
                    boolean needToBuy = true;
                    for (String availableIngridient : listOfAvailableIngridients) {

                        availableIngridient = availableIngridient.trim();

                        if (ingridient.getKey().toLowerCase().contains(availableIngridient.toLowerCase())) {
                            needToBuy = false;
                        }
                    }

                    if (!needToBuy) {
                        continue;
                    }
                    if (strNeedToBuy.equals("")) {
                        strNeedToBuy = ingridient.getValue();
                    } else {
                        strNeedToBuy = strNeedToBuy + ", " + ingridient.getValue();
                    }

                }

                textMenuItem = "- " + String.valueOf(count) + ". " + menuItem.getValue() + " (нужно докупить: " + strNeedToBuy + ")";
                menuAvailableDishes.addMenuItems(textMenuItem, String.valueOf(count), menuItem.getKey());
                count++;
            }
            menuAvailableDishes.displayMenu();

            int id_dish = menuAvailableDishes.getID_MenuItem();
            String nameDish = listFinedDishes.get(id_dish);

            ArrayList<String> findedIngridients = connectToDataBaseRecipes.getIngridientsByID(id_dish);
            ArrayList<String> findedRecipeSteps = connectToDataBaseRecipes.getRecipeStepsByID(id_dish);

            System.out.println("________________________________________________");
            System.out.println("Рецепт блюда " + nameDish);
            System.out.println("________________________________________________");
            System.out.println("Ингриденты:");

            for (String nameIngridient : findedIngridients) {
                System.out.println(nameIngridient);
            }

            System.out.println("________________________________________________");
            System.out.println("Шаги рецептов:");

            for (String nameRecipeStep : findedRecipeSteps) {
                System.out.println(nameRecipeStep);
            }

            System.out.println("");
            System.out.println("________________________________________________");
            System.out.println("Введите 0 для выхода");

        }

    }

}
