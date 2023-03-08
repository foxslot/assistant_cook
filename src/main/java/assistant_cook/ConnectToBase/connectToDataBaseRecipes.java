package assistant_cook.ConnectToBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class connectToDataBaseRecipes {

    static final String URL = "jdbc:postgresql://localhost:5432/postgres";

    static final String USER = "postgres";

    static final String PASSWORD_DB = "postgres";

    public static HashMap<Integer, String> getAllDishes() {

        HashMap<Integer, String> findDishes = new HashMap<>();

        String query = "SELECT * FROM public.recipes WHERE archive = false";

        try (
                Connection connection
                        = DriverManager.getConnection(URL, USER, PASSWORD_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                findDishes.put(resultSet.getInt("ID_Dish"), resultSet.getString("NameDish"));
            }

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        return findDishes;
    }

    public static ArrayList<String> getIngridientsByID(int ID){

        ArrayList<String> findedIngridients = new ArrayList<>();

        String query = "SELECT * FROM public.ingridients WHERE recipe_id = ?\n" +
                "ORDER BY index_number ASC ";

        try (
                Connection connection
                        = DriverManager.getConnection(URL, USER, PASSWORD_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1,ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                findedIngridients.add(resultSet.getString("index_number") + ". " +
                        resultSet.getString("name_ingridient") + "   "  + resultSet.getString("amount") +
                        resultSet.getString("unit_type") + ".");

            }
            return findedIngridients;
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<String, String> getIngridientsNameByID(int ID){

        HashMap<String, String> findedIngridients = new HashMap<>();

        String query = "SELECT * FROM public.ingridients WHERE recipe_id = ?\n" +
                "ORDER BY index_number ASC ";

        try (
                Connection connection
                        = DriverManager.getConnection(URL, USER, PASSWORD_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1,ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                findedIngridients.put(resultSet.getString("name_ingridient"), resultSet.getString("name_ingridient")
                        + "   "  + resultSet.getString("amount") + resultSet.getString("unit_type") + ".");
            }
            return findedIngridients;
        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static ArrayList<String> getRecipeStepsByID(int ID){

        ArrayList<String> findedRecipeSteps = new ArrayList<>();

        String query = "SELECT * FROM public.recipe_steps WHERE recipe_id = ?\n" +
                "ORDER BY step_number ASC";

        try (
                Connection connection
                        = DriverManager.getConnection(URL, USER, PASSWORD_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1,ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                findedRecipeSteps.add(resultSet.getString("step_number") + ". " +
                        resultSet.getString("instruction"));

            }

            return findedRecipeSteps;

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<Integer,String> getRecipesByName(String NameDish){

        HashMap<Integer,String> findedRecipes = new HashMap<>();

        String query = "SELECT * FROM public.recipes WHERE archive = false AND \"NameDish\" LIKE '%" + NameDish + "%'";

        try (
                Connection connection
                        = DriverManager.getConnection(URL, USER, PASSWORD_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                findedRecipes.put(resultSet.getInt("ID_Dish"), resultSet.getString("NameDish"));
            }

            return findedRecipes;

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap<Integer, String> getRecipesIdByIngridientsName(String[] listOfAvailableIngridients){

        HashMap<Integer, String> listRecipeID = new HashMap<>();

        String condition = "";

        for (String availableIngridient : listOfAvailableIngridients) {

            availableIngridient = availableIngridient.trim();
            availableIngridient = availableIngridient.substring(0, 1).toUpperCase() + availableIngridient.substring(1);

            if (condition.equals("")) {
                condition = condition + "(public.ingridients.name_ingridient LIKE '%" + availableIngridient + "%')";
            } else {
                condition = condition + " OR (public.ingridients.name_ingridient LIKE '%" + availableIngridient + "%')";
            }
        }

        String query = "SELECT public.ingridients.recipe_id, \"NameDish\"\n" +
                "FROM public.ingridients LEFT JOIN public.recipes\n" +
                "                                    ON public.ingridients.recipe_id = \"ID_Dish\"\n" +
                "WHERE public.recipes.archive = false AND " + condition +
                " GROUP BY public.ingridients.recipe_id, \"NameDish\"\n" +
                " ORDER BY public.ingridients.recipe_id ASC";

        try (
                Connection connection
                        = DriverManager.getConnection(URL, USER, PASSWORD_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listRecipeID.put(resultSet.getInt("recipe_id"), resultSet.getString("NameDish"));
            }

            return listRecipeID;

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addRecipeToTheDB(HashMap<String,String> recipeKey, ArrayList<HashMap<String, String>> stringsIngridients,
                                        ArrayList<HashMap<String, String>> stringsStepsRecipe){

        String query = "INSERT INTO public.recipes (\n" +
                "\"NameDish\", \"TypeDish\", \"ForNumbersOfPerson\", \"ApproximateCookingTime\") VALUES (\n" +
                "'" + recipeKey.get("name_recipe") + "'::character varying, '" + recipeKey.get("type_recipe") + "'::character varying, '" +
                recipeKey.get("numbers_of_person") + "'::character varying, '" +recipeKey.get("approximate_cooking_time")+  "'::integer)\n" +
                " returning \"ID_Dish\";";

        int ID_Dish = -1;

        try (
                Connection connection
                        = DriverManager.getConnection(URL, USER, PASSWORD_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ID_Dish = resultSet.getInt("ID_Dish");
            }

            if (ID_Dish == -1){
                System.out.println("Не удалось добавить рецепт");
                return;
            }

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }

        for (HashMap<String, String> hmIngridient:stringsIngridients) {

            String querySetIngridient = "INSERT INTO public.ingridients (\n" +
                    "recipe_id, name_ingridient, unit_type, amount, index_number) VALUES (\n" +
                    "'" + ID_Dish + "'::integer, '" + hmIngridient.get("name_ingridient") + "'::character varying, '" + hmIngridient.get("unit_type") +
                    "'::character varying, '" + hmIngridient.get("amount") + "'::integer, '" + hmIngridient.get("index_number") + "'::integer)\n" +
                    " returning ingridient_id;";

            try (
                    Connection connectionAddIngridient
                            = DriverManager.getConnection(URL, USER, PASSWORD_DB);
                    PreparedStatement preparedStatementAddIngridient = connectionAddIngridient.prepareStatement(querySetIngridient)) {

                ResultSet resultSet = preparedStatementAddIngridient.executeQuery();

                int ID_Ingridient;

                while (resultSet.next()) {
                    ID_Ingridient = resultSet.getInt("ingridient_id");
                }

            } catch (
                    SQLException e) {
                throw new RuntimeException(e);
            }

        }

        for (HashMap<String, String> hmStepRecipe:stringsStepsRecipe) {

            String queryStepRecipe = "INSERT INTO public.recipe_steps (\n" +
                    "recipe_id, step_number, instruction) VALUES (\n" +
                    "'" + ID_Dish + "'::integer, '" + hmStepRecipe.get("step_number") +
                    "'::integer, '" + hmStepRecipe.get("instruction") + "'::character varying)\n" +
                    " returning \"ID_recipe_steps\";";

            try (
                    Connection connectionAddStepRecipe
                            = DriverManager.getConnection(URL, USER, PASSWORD_DB);
                    PreparedStatement preparedStatementAddStepRecipe = connectionAddStepRecipe.prepareStatement(queryStepRecipe)) {

                ResultSet resultSet = preparedStatementAddStepRecipe.executeQuery();

                int ID_RecipeStep;

                while (resultSet.next()) {
                    ID_RecipeStep = resultSet.getInt("ID_recipe_steps");
                }

            } catch (
                    SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }

    public static void deleteRecipeByID(int RecipeID) {

        String query = "UPDATE public.recipes SET\n" +
                "archive = true::boolean WHERE\n" +
                "\"ID_Dish\" = " + RecipeID + " " +
                "returning true;";

        try (
                Connection connection
                        = DriverManager.getConnection(URL, USER, PASSWORD_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.executeQuery();

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
