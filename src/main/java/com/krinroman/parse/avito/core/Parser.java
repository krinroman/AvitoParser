package com.krinroman.parse.avito.core;

import com.krinroman.parse.avito.data.IndexAvitoObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public List<IndexAvitoObject> ParsingIndex() throws ConnectException {
        System.out.println("Началась загрузка индексов");
        System.out.println("Загрузка...");
        Document doc = MyConnection.getDocument("https://www.avito.ru/kirovskaya_oblast_kirov/kvartiry/sdam/na_dlitelnyy_srok?p=1");
        System.out.println(doc);
        int count = getCountPage(doc);
        List<IndexAvitoObject> indexAvitoObjectList = new ArrayList<>();
        indexAvitoObjectList.addAll(ParsingPage(doc));

        for(int i = 2; i <= count; i++){
            indexAvitoObjectList.addAll(ParsingPage("https://www.avito.ru/kirovskaya_oblast_kirov/kvartiry/sdam/na_dlitelnyy_srok?p=" + i));
        }
        System.out.println("Индексация закончилась, индексов загружено: " + indexAvitoObjectList.size());
        return indexAvitoObjectList;
    }

    private int getCountPage(Document document){
        Elements elementsCountPage = document.select("span.pagination-item-1WyVp");
        int index = 0;
        for(Element element: elementsCountPage){
            if(element.attr("data-marker").equals("page(...)")) break;
            index++;
        }
        return Integer.parseInt(elementsCountPage.get(++index).text());
    }

    private List<IndexAvitoObject> ParsingPage(String urlPage) throws ConnectException {
        return ParsingPage(MyConnection.getDocument(urlPage));
    }

    private List<IndexAvitoObject> ParsingPage(Document document){
        List<IndexAvitoObject> indexAvitoObjects = new ArrayList<>();
        Elements elements = document.select("div.snippet-horizontal.item.item_table.clearfix.js-catalog-item-enum.item-with-contact.js-item-extended");
        for(Element element:elements){
            long id = Long.parseLong(element.attr("data-item-id"));
            String url = "https://www.avito.ru" +
                    element.select( "a.snippet-link").get(0).attr("href");

            indexAvitoObjects.add(new IndexAvitoObject(id, url));

        }
        return  indexAvitoObjects;
    }
}



