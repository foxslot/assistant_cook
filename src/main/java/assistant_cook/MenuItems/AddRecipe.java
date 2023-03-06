package assistant_cook.MenuItems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AddRecipe {

    public void startAddingRecipe() {

        System.out.println("Введите название рецепта или 0 для отмены");
        Scanner in = new Scanner(System.in);
        String nameRecipe = in.nextLine();

        if (nameRecipe.equals("0")) {
            System.out.println("Завершение...");
            return;
        }

        System.out.println("Введите тип блюда или 0 для отмены");
        String typeRecipe = in.nextLine();

        if (typeRecipe.equals("0")) {
            System.out.println("Завершение...");
            return;
        }

        System.out.println("Введите количество персон или 0 для отмены");
        String forNumbersOfPerson = in.nextLine();
        if (forNumbersOfPerson.equals("0")) {
            System.out.println("Завершение...");
            return;
        }

        System.out.println("Введите примерное время готовки в минутах или 0 для отмены");
        int approximateCookingTime = in.nextInt();
        if (approximateCookingTime == 0) {
            System.out.println("Завершение...");
            return;
        }

        int count = 1;

        String[] columnNames = {"name_ingridient", "unit_type", "amount", "index_number"};
        tableForRecipes tableOfIngridients = new tableForRecipes(columnNames);
        ArrayList<HashMap<String, String>> stringsIngridients = tableOfIngridients.strings;

        while (true) {

            System.out.println("Введите наименование Ингридиента " + count + ". Для завершения ввода ингридиентов введите 1, для отмены 0");

            in = new Scanner(System.in);
            String nameIngridient = in.nextLine();

            if (nameIngridient.equals("0")) {
                return;
            } else if (nameIngridient.equals("1")) {
                break;
            }

            System.out.println("Введите единицу измерения Ингридиента" + count);
            String unit_type = in.nextLine();

            System.out.println("Введите количество Ингридиента " + count + " в " + unit_type);
            int amount = 1;
            while (true){
                try {
                    in = new Scanner(System.in);
                    amount = in.nextInt();
                    break;
                }
               catch (Exception e){
                    System.out.println("Введено некорректное значение");
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

        while (true){

            System.out.println("Введите инструкцию шага " + step_number + ". Для завершения ввода шагов с инструкциями введите 1, для отмены 0");

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




    }


}
