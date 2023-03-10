package assistant_cook.MenuItems;

import assistant_cook.ConnectToBase.connectToDataBaseRecipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AddRecipe {

    public void startAddingRecipe() {

        System.out.println("_____________________________________________________________________________");
        System.out.println("Введите название рецепта с заглавной буквы или 0 для выхода в главное меню");
        System.out.println("_____________________________________________________________________________");
        Scanner in = new Scanner(System.in);
        String nameRecipe = in.nextLine();

        if (nameRecipe.equals("0")) {
            return;
        }

        System.out.println("_____________________________________________________________________________");
        System.out.println("Введите тип блюда с заглавной буквы или 0 для отмены");
        System.out.println("_____________________________________________________________________________");
        String typeRecipe = in.nextLine();

        if (typeRecipe.equals("0")) {
            System.out.println("_____________________________________________________________________________");
            return;
        }

        System.out.println("_____________________________________________________________________________");
        System.out.println("Введите количество персон или 0 для выхода в главное меню");
        System.out.println("_____________________________________________________________________________");
        String forNumbersOfPerson = in.nextLine();
        if (forNumbersOfPerson.equals("0")) {
            return;
        }

        System.out.println("_____________________________________________________________________________");
        System.out.println("Введите примерное время готовки в минутах или 0 для выхода в главное меню");
        System.out.println("_____________________________________________________________________________");
        int approximateCookingTime = in.nextInt();
        if (approximateCookingTime == 0) {
            return;
        }

        HashMap<String, String> recipeKey = new HashMap<>();
        recipeKey.put("name_recipe", nameRecipe);
        recipeKey.put("type_recipe", typeRecipe);
        recipeKey.put("numbers_of_person", forNumbersOfPerson);
        recipeKey.put("approximate_cooking_time", Integer.toString(approximateCookingTime));

        // ввод ингридиентов
        int count = 1;

        String[] columnNames = {"name_ingridient", "unit_type", "amount", "index_number"};
        tableForRecipes tableOfIngridients = new tableForRecipes(columnNames);
        ArrayList<HashMap<String, String>> stringsIngridients = tableOfIngridients.strings;

        System.out.println("_____________________________________________________________________________________________________");
        System.out.println("ВВОД ИНГРЕДИЕНТОВ");
        System.out.println("_____________________________________________________________________________________________________");

        while (true) {

            System.out.println("_____________________________________________________________________________");
            System.out.println("Введите наименование Ингредиента " + count + " с заглавной буквы. \n" +
                    "Для завершения ввода ингредиентов введите 1, для выхода в главное меню 0");
            System.out.println("_____________________________________________________________________________");

            in = new Scanner(System.in);
            String nameIngridient = in.nextLine();

            if (nameIngridient.equals("0")) {
                return;
            } else if (nameIngridient.equals("1")) {
                break;
            }

            System.out.println("_____________________________________________________________________________");
            System.out.println("Введите единицу измерения Ингредиента" + count);
            System.out.println("_____________________________________________________________________________");
            String unit_type = in.nextLine();

            System.out.println("_____________________________________________________________________________");
            System.out.println("Введите количество Ингредиента " + count + " в " + unit_type);
            System.out.println("_____________________________________________________________________________");
            int amount = 1;
            while (true) {
                try {
                    in = new Scanner(System.in);
                    amount = in.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("_____________________________________________________________________________");
                    System.out.println("Введено некорректное значение");
                    System.out.println("_____________________________________________________________________________");
                }
            }

            HashMap<String, String> stringIngridient = new HashMap<>();
            stringIngridient.put("name_ingridient", nameIngridient);
            stringIngridient.put("unit_type", unit_type);
            stringIngridient.put("amount", Integer.toString(amount));
            stringIngridient.put("index_number", Integer.toString(count));

            stringsIngridients.add(stringIngridient);

            count++;

        }

        //ввод шагов готовки
        int step_number = 1;
        String instruction;

        String[] columnNamesSteps = {"step_number", "instruction"};
        tableForRecipes tableOfStepsRecipe = new tableForRecipes(columnNamesSteps);
        ArrayList<HashMap<String, String>> stringsStepsRecipe = tableOfStepsRecipe.strings;

        System.out.println("_____________________________________________________________________________________________________");
        System.out.println("ВВОД ШАГОВ ИНСТРУКЦИЙ ПО ГОТОВКЕ");
        System.out.println("_____________________________________________________________________________________________________");

        while (true) {
            System.out.println("_____________________________________________________________________________");
            System.out.println("Введите инструкцию шага " + step_number + ". Для завершения ввода шагов с инструкциями введите 1, \n" +
                    "для выхода в главное меню 0");
            System.out.println("_____________________________________________________________________________");

            in = new Scanner(System.in);
            instruction = in.nextLine();

            if (instruction.equals("0")) {
                return;
            } else if (instruction.equals("1")) {
                break;
            }

            HashMap<String, String> stringStepRecipe = new HashMap<>();
            stringStepRecipe.put("step_number", Integer.toString(step_number));
            stringStepRecipe.put("instruction", instruction);

            stringsStepsRecipe.add(stringStepRecipe);

            step_number++;

        }

        connectToDataBaseRecipes.addRecipeToTheDB(recipeKey, stringsIngridients, stringsStepsRecipe);

        System.out.println("_____________________________________________________________________________");
        System.out.println("Рецепт добавлен");
        System.out.println("_____________________________________________________________________________");

    }


}
