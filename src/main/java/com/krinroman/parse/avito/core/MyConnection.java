package com.krinroman.parse.avito.core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.ConnectException;
import java.util.Random;

public class MyConnection {
    public static Document getDocument(String url) throws ConnectException {
        try {
            Thread.sleep(new Random().nextInt(701) + 300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            org.jsoup.Connection connection = Jsoup.connect(url);
            connection.ignoreHttpErrors(true);
            connection.maxBodySize(Integer.MAX_VALUE);
            connection.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36 OPR/65.0.3467.78");
            connection.header("cookie", "u=2fpjx8zi.d1bznb.g7c67hfght; _ym_uid=15694245931022491262; _ym_d=1569424593; _ga=GA1.2.1855629684.1569424593; _fbp=fb.1.1569424593110.1097427427; buyer_selected_search_radius4=0_general; __utmc=99926606; __utmz=99926606.1569953618.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __cfduid=d8ad5e49dc8064cbb64402b99d9c0fed91571827167; buyer_selected_search_radius0=200; buyer_location_id=631870; buyer_tooltip_location=0; af=2fpjx8zi.d1bznb.g7c67hfght; anid=removed; __utma=99926606.1855629684.1569424593.1575235985.1575244831.7; no-ssr=1; _gid=GA1.2.1045980502.1576771148; _ym_isad=2; v=1576905514; sessid=b5027f862873d61cf63033edf3724938.1576905514; luri=kirovskaya_oblast_kirov; dfp_group=31; f=5.0c4f4b6d233fb90636b4dd61b04726f147e1eada7172e06c47e1eada7172e06c47e1eada7172e06c47e1eada7172e06cb59320d6eb6303c1b59320d6eb6303c1b59320d6eb6303c147e1eada7172e06c8a38e2c5b3e08b898a38e2c5b3e08b890df103df0c26013a0df103df0c26013a2ebf3cb6fd35a0ac0df103df0c26013a8b1472fe2f9ba6b984dcacfe8ebe897bfa4d7ea84258c63d59c9621b2c0fa58f897baa7410138ead3de19da9ed218fe23de19da9ed218fe23de19da9ed218fe23de19da9ed218fe23de19da9ed218fe23de19da9ed218fe23de19da9ed218fe23de19da9ed218fe23de19da9ed218fe23de19da9ed218fe23de19da9ed218fe23de19da9ed218fe2cd39050aceac4b9090778cad0fedbce74a3a63f4331d362fd52690372c0ea63a420d66e83a34e2963e4de658313c91b290a042de6ed0741d230c40ac510863bfa95fd3ce761a54f0c0cd781c71c25a178732de926882853a9d9b2ff8011cc827c4d07ec9665f0b70915ac1de0d0341122fa6c71281fbd41cd953c4055e5646022da10fb74cac1eab2da10fb74cac1eabc35109ce697b01fc816f72e0138cdbd8b04063604e2ea601; _ym_visorc_34241905=b; _nfh=6ba5854aa9f567be1fa333b82405daa3; sx=H4sIAAAAAAACA53QMY7CQAwF0LtMTeEEJ%2FFwmzAQA4Y1wSGOhLg7Rloktl25c%2FH0%2F3%2Bk5qfrx6E5jEKOakUZzaQwpM0jzWmTqvqG83mRKgsroouDMWBhViWktEr7tKmars0Zoc7PVaomnMeLXPthKUyCLI4Ahvohx%2BPJ2izzYW8IZIxMqA5q5lb8m1xDC02Q66Xq%2B%2B2dt1OBov5O6uBq%2FCG7m56ubV01d463ATgIBWasRoTfKdcZKMgy9OfW23q3xWjCEhe9OBr9kjjocTqPl2uQRsJsEAERQWMDKn9IoHfxnU2HXd3fl05EPVCnWMoK%2F4vM%2Bfl8AcajCeOgAQAA; so=1576907629; abp=0; _dc_gtm_UA-2546784-1=1; buyer_from_page=catalog");
            connection.header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
            connection.header("accept-encoding", "gzip, deflate, br");
            connection.header("accept-language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7");
            connection.header("cache-control", "max-age=0");
            connection.header("sec-fetch-mode", "navigate");
            connection.header("sec-fetch-site", "same-origin");
            return connection.get();
        } catch (IOException e) {
            throw new ConnectException("Не удалось получить данные от сервера");
        }
    }
}
