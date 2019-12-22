package com.krinroman.parse.avito.servlet;

import com.krinroman.parse.avito.core.DataBaseConnection;
import com.krinroman.parse.avito.core.Parser;
import com.krinroman.parse.avito.data.AvitoObjectApartment;
import com.krinroman.parse.avito.data.IndexAvitoObject;


import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class Update extends javax.servlet.http.HttpServlet {
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter printWriter = response.getWriter();
        int countAdd = 0;
        int countDelete = 0;
        List<IndexAvitoObject> indexAvitoObjectList = new Parser().ParsingIndex();
        List<IndexAvitoObject> indexAvitoObjectListOld = null;
        try {
            indexAvitoObjectListOld = DataBaseConnection.getListIndexToTableApartment();
        } catch (SQLException e) {
            e.printStackTrace();
            printWriter.println("error");
            return;
        }
        System.out.println("Началась проверка и удаление старых объявлений из базы данных\nПроверка...");
        for(IndexAvitoObject indexAvitoObjectOld:indexAvitoObjectListOld){
            boolean exist = false;
            for(IndexAvitoObject indexAvitoObjectNew :indexAvitoObjectList){
                if(indexAvitoObjectOld.equals(indexAvitoObjectNew)){
                    exist = true;
                    break;
                }
            }
            if(!exist){
                try {
                    if(DataBaseConnection.DeleteItemToTableApartments(indexAvitoObjectOld.getId())) {
                        System.out.println("Запись: \n" + indexAvitoObjectOld.getId() + " " + indexAvitoObjectOld.getUrl() + "\nУспешно удалена");
                        countDelete++;
                    }
                } catch (SQLException e) {
                    System.out.println("Запись: \n" + indexAvitoObjectOld.getId() + " " + indexAvitoObjectOld.getUrl() + "\nНе удалось удалить");
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Закончилась проверка и удаление старых объявлений из базы данных");
        System.out.println("Началась проверка и добавление новых записей\nПроверка...");
        for(IndexAvitoObject indexAvitoObject:indexAvitoObjectList){
            boolean exist = false;
            for(IndexAvitoObject indexAvitoObjectOld :indexAvitoObjectListOld){
                if(indexAvitoObject.equals(indexAvitoObjectOld)){
                    exist = true;
                    break;
                }
            }
            if(!exist) {
                DataBaseConnection.InsertToTableApartment(new AvitoObjectApartment(indexAvitoObject.getId(), indexAvitoObject.getUrl()));
                countAdd++;
            }
        }
        System.out.println("Закончилась проверка и добавление новых записей");
        System.out.println("Добавлено записей: " + countAdd);
        System.out.println("Удалено записей: " + countDelete);
        System.out.println("Всего записей: " + indexAvitoObjectList.size());
        printWriter.print("ok");
    }
}
