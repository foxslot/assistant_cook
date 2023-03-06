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

        String query = "SELECT * FROM public.recipes";

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

                //findDishes.put(resultSet.getInt("ID_Dish"), resultSet.getString("NameDish"));
                //System.out.println(resultSet.getString("NameDish"));
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

        String query = "SELECT * FROM public.recipes WHERE \"NameDish\" LIKE '%" + NameDish + "%'";

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
                "WHERE " + condition +
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

    public static void addRecipeToTheDB(){

        String query = "INSERT INTO public.recipes (\n" +
                "\"NameDish\", \"TypeDish\", \"ForNumbersOfPerson\", \"ApproximateCookingTime\") VALUES (\n" +
                "'Грибной суп'::character varying, 'супы'::character varying, '4'::character varying, '60'::integer)\n" +
                " returning \"ID_Dish\";";

        try (
                Connection connection
                        = DriverManager.getConnection(URL, USER, PASSWORD_DB);
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                //findDishes.put(resultSet.getInt("ID_Dish"), resultSet.getString("NameDish"));
            }

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
