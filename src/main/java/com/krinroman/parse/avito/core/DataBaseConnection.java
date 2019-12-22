package com.krinroman.parse.avito.core;

import com.krinroman.parse.avito.data.AvitoObjectApartment;
import com.krinroman.parse.avito.data.IndexAvitoObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataBaseConnection {

    static Connection connection;

    static{
        try {
            String dbUrl = System.getenv("JDBC_DATABASE_URL");
            connection = DriverManager.getConnection(dbUrl);
            CreateIfNotExistTableApartment();
        } catch (SQLException e) {
            System.out.println("Не удалось подключиться к базе данных");
            e.printStackTrace();
        }

    }

    private static Statement getStatement(){
        if(connection == null){
            System.out.println("Не удалось подключиться к базе данных");
            return null;
        }
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            System.out.println("Не удалось создать обработчик запросов");
            e.printStackTrace();
        }
        return null;
    }

    private static PreparedStatement getPreparedStatement(String query){
        if(connection == null){
            System.out.println("Не удалось подключиться к базе данных");
            return null;
        }
        try {
            return connection.prepareStatement(query);
        } catch (SQLException e) {
            System.out.println("Не удалось создать обработчик запросов");
            e.printStackTrace();
        }
        return null;
    }

    public static void CreateIfNotExistTableApartment(){
        String query = "CREATE TABLE IF NOT EXISTS public.apartment\n" +
                "(\n" +
                "    id integer NOT NULL GENERATED ALWAYS AS IDENTITY,\n" +
                "    \"id-avito\" integer NOT NULL,\n" +
                "    url text NOT NULL,\n" +
                "    title text NOT NULL,\n" +
                "    price integer NOT NULL,\n" +
                "    description text,\n" +
                "    address text,\n" +
                "    floor integer,\n" +
                "    \"floor-in-home\" integer,\n" +
                "    rooms integer,\n" +
                "    \"url-images\" text[],\n" +
                "    PRIMARY KEY (id)\n" +
                ");";


        Statement statement = getStatement();
        if(statement == null){
            System.out.println("Не удалось создать обработчик запросов");
            return;
        }

        try {
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("Не удалось отправить запрос");
            e.printStackTrace();
            return;
        }

        System.out.println("Проверка таблицы apartment прошла успешно");
    }

    public static void InsertToTableApartment(AvitoObjectApartment avitoObjectApartment){
        String query = "INSERT INTO public.apartment(\n" +
                "\"id-avito\", url, title, price, description, address, floor, \"floor-in-home\", rooms, \"url-images\")\n" +
                "\tVALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = getPreparedStatement(query);
        if(preparedStatement == null){
            System.out.println("Не удалось создать обработчик запросов");
            return;
        }

        try {
            preparedStatement.setLong(1, avitoObjectApartment.getId());
            preparedStatement.setString(2, avitoObjectApartment.getUrl());
            preparedStatement.setString(3, avitoObjectApartment.getTitle());
            preparedStatement.setInt(4, avitoObjectApartment.getPrice());
            preparedStatement.setString(5, avitoObjectApartment.getDescription());
            preparedStatement.setString(6, avitoObjectApartment.getAddress());
            if(avitoObjectApartment.getFloor() != -1)
                preparedStatement.setInt(7, avitoObjectApartment.getFloor());
            else
                preparedStatement.setNull(7, Types.INTEGER);
            if(avitoObjectApartment.getFloorInHome() != -1)
                preparedStatement.setInt(8, avitoObjectApartment.getFloorInHome());
            else
                preparedStatement.setNull(8, Types.INTEGER);
            if(avitoObjectApartment.getRooms() != -1)
                preparedStatement.setInt(9, avitoObjectApartment.getRooms());
            else
                preparedStatement.setNull(9, Types.INTEGER);
            preparedStatement.setArray(10,  connection.createArrayOf("text", avitoObjectApartment.getUrlImages().toArray()));

        } catch (SQLException e) {
            System.out.println("Не удалось задать параметры");
            e.printStackTrace();
            return;
        }

        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Не удалось отправить запрос");
            e.printStackTrace();
        }
        System.out.println("Запись:\n" + avitoObjectApartment.toString() + "\n" + "Успешно добавлена");
    }

    public static List<AvitoObjectApartment> getListToTableApartment() throws SQLException {

        String query = "SELECT \"id-avito\", url, title, price, description, address, floor, \"floor-in-home\", rooms, \"url-images\"\n" +
                "\tFROM public.apartment\n"+
                "\tORDER BY id DESC;";

        Statement statement = getStatement();
        if(statement == null){
            throw new SQLException("Не удалось создать обработчик запросов");
        }

        List<AvitoObjectApartment> avitoObjectApartments = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next()){
            List<String> stringList = Arrays.asList((String[]) resultSet.getArray("url-images").getArray());
            avitoObjectApartments.add(new AvitoObjectApartment(
                    resultSet.getInt("id-avito"),
                    resultSet.getString("url"),
                    resultSet.getString("title"),
                    resultSet.getInt("price"),
                    resultSet.getString("description"),
                    resultSet.getString("address"),
                    resultSet.getInt("floor"),
                    resultSet.getInt("floor-in-home"),
                    resultSet.getInt("rooms"),
                    stringList));
        }
        return avitoObjectApartments;
    }

    public static List<IndexAvitoObject> getListIndexToTableApartment() throws SQLException {

        String query = "SELECT \"id-avito\", url\n" +
                "\tFROM public.apartment\n";

        Statement statement = getStatement();
        if(statement == null){
            throw new SQLException("Не удалось создать обработчик запросов");
        }

        List<IndexAvitoObject> indexAvitoObjectList = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next()){
            indexAvitoObjectList.add(new IndexAvitoObject(
                    resultSet.getInt("id-avito"),
                    resultSet.getString("url"))
            );
        }
        return indexAvitoObjectList;
    }

    public static boolean DeleteItemToTableApartments(long id) throws SQLException {
        boolean result = false;
        String query = "DELETE FROM public.apartment\n" +
                "\tWHERE \"id-avito\" = ?;";

        PreparedStatement preparedStatement = getPreparedStatement(query);
        if(preparedStatement == null){
            throw new SQLException("Не удалось создать обработчик запросов");
        }
        preparedStatement.setLong(1, id);
        return preparedStatement.execute();
    }

}
