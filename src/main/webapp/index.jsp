<%@ page import="java.util.List" %>
<%@ page import="com.krinroman.parse.avito.data.AvitoObjectApartment" %>
<%@ page import="com.krinroman.parse.avito.core.DataBaseConnection" %>
<%@ page import="java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Аренда квартир</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script>
        function UpdateDataBase() {
            ajaxGet("/update", document.getElementById("lable"))
        }

        function ajaxGet(url, objectOutput) {
            let request = new XMLHttpRequest();
            request.onreadystatechange = function () {
                switch (request.readyState) {
                    case 0:{
                        objectOutput.textContent = "Обновление началось";
                    }
                    case 1:
                    case 2:
                    case 3:{
                        objectOutput.textContent = "Обновление...";
                    } break;
                    case 4:{
                        if(request.status != 200) objectOutput.textContent = "Ошибка обновления";
                        else{
                            if(request.responseText == "ok"){
                                objectOutput.textContent = "Обновление завершено успешно";
                                setTimeout(function () {
                                    window.location.reload();
                                }, 1000);
                            }
                            else objectOutput.textContent = "Ошибка обновления";
                        }
                    }break;
                }
            }
            request.open("GET", url);
            request.send();
        }

    </script>
</head>
<body>
    <input type="button" onclick="return UpdateDataBase (this);"
           value="Обновление базы данных" />
    <span id="lable"></span></br>
    <%
    List<AvitoObjectApartment> avitoObjectApartmentList = null;
    try {
        avitoObjectApartmentList = DataBaseConnection.getListToTableApartment();
    } catch (SQLException e) {
        e.printStackTrace();
    }%>
    <h1>Объявления аренды квартир в кирове (найдено: <%=avitoObjectApartmentList.size()%>)</h1>
    <%
    for(AvitoObjectApartment avitoObjectApartment: avitoObjectApartmentList){
    %>
    <hr width="100%" size="2" color="Black" />
    <h2><a href="<%=avitoObjectApartment.getUrl()%>"><%=avitoObjectApartment.getTitle()%></a></h2>
    <span>Цена: <%=avitoObjectApartment.getPrice()%></span></br>
    <span>Адрес: <%=avitoObjectApartment.getAddress()%></span></br>
    <span>Этаж: <%=avitoObjectApartment.getFloor()%></span></br>
    <span>Этажей в доме: <%=avitoObjectApartment.getFloorInHome()%></span></br>
    <%if(avitoObjectApartment.getRooms() == 0){%>
        <span>Студия</span></br>
        <%}%>
    <%if(avitoObjectApartment.getRooms() != 0){%>
        <span>Кол-во комнат: <%=avitoObjectApartment.getRooms()%></span></br>
        <%}%>
    <%if(avitoObjectApartment.getDescription() != null){%>
        <span><%=avitoObjectApartment.getDescription()%></span></br>
    <%}%>
    <%
        if(!avitoObjectApartment.getUrlImages().isEmpty()){
            for(String urlImage: avitoObjectApartment.getUrlImages()){
    %>
              <img src="<%=urlImage%>">
            <%}%>
    </br>
        <%}%>
    </br>
<%}%>
</body>
</html>
