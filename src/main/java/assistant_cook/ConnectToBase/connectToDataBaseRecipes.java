package assistant_cook.ConnectToBase;

import java.sql.*;
import java.util.ArrayList;
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
}
