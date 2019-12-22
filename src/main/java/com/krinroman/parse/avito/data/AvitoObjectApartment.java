package com.krinroman.parse.avito.data;

import com.krinroman.parse.avito.core.MyConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AvitoObjectApartment {
    private long id;
    private String url;
    private String title;
    private int price;
    private String description;
    private String address;
    private int floor;
    private int floorInHome;
    private int rooms;
    private List<String> urlImages;

    public AvitoObjectApartment(long id, String url) throws IOException {
        this.id = id;
        this.url = url;
        description = null;
        address = null;
        floor = -1;
        floorInHome = -1;
        rooms = -1;
        urlImages = new ArrayList<>();

        Document doc = MyConnection.getDocument(url);

        Elements paramElements;
        title = doc.select("span.title-info-title-text").get(0).text();
        price = Integer.parseInt(doc.select("span.js-item-price").get(0).text().replace(" ", ""));
        paramElements = doc.select("div.item-description-text");
        if(!paramElements.isEmpty()) description = paramElements.get(0).text();
        paramElements = doc.select("span.item-address__string");
        if(!paramElements.isEmpty()) address = paramElements.get(0).text();
        Elements elementsImgUrl = doc.select("div.gallery-img-frame.js-gallery-img-frame");
        for(Element elementImgUrl : elementsImgUrl){
            urlImages.add("https:" + elementImgUrl.attr("data-url"));
        }

        paramElements = doc.select("li.item-params-list-item");
        for(Element elementParam: paramElements){

            switch(elementParam.text().split(":")[0]){
                case("Этаж"):{
                    floor = Integer.parseInt(elementParam.text().split(" ")[1].trim());
                }break;
                case("Этажей в доме"):{
                    String [] args = elementParam.text().split(" ");
                    floorInHome = Integer.parseInt(args[args.length-1].trim());
                }break;
                case("Количество комнат"):{
                    String [] args = elementParam.text().split(" ");
                    switch(args[args.length-1].trim()){
                        case("студии"): rooms = 0; break;
                        case("1-комнатные"): rooms = 1; break;
                        case("2-комнатные"): rooms = 2; break;
                        case("3-комнатные"): rooms = 3; break;
                        case("4-комнатные"): rooms = 4; break;
                        case("5-комнатные"): rooms = 5; break;
                        case("6-комнатные"): rooms = 6; break;
                        case("7-комнатные"): rooms = 7; break;
                    }
                }
            }
        }
    }

    public AvitoObjectApartment(long id, String url, String title, int price, String description, String address, int floor, int floorInHome, int rooms, List<String> urlImages) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.price = price;
        this.description = description;
        this.address = address;
        this.floor = floor;
        this.floorInHome = floorInHome;
        this.rooms = rooms;
        this.urlImages = urlImages;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public int getFloor() {
        return floor;
    }

    public int getFloorInHome() {
        return floorInHome;
    }

    public int getRooms() {
        return rooms;
    }

    public List<String> getUrlImages() {
        return urlImages;
    }

    @Override
    public String toString() {
        return id + ": " + url;
    }
}
