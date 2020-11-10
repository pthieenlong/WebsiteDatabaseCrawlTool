package JSoupTutorial;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class GetDocumentFromURL {
    public static int counter = 0;
    public static ArrayList<String> linksList = new ArrayList<>();
    public static final String RAM = "https://gearvn.com/collections/ram-pc?page=";
    public static final String MainBoard = "https://gearvn.com/collections/mainboard-bo-mach-chu?page=";
    public static final String CPU = "https://gearvn.com/collections/cpu-bo-vi-xu-ly?page=";
    public static final String VGA = "https://gearvn.com/collections/vga-card-man-hinh?page=";
    public static final String SSD = "https://gearvn.com/collections/ssd-o-cung-the-ran?page=";
    public static final String HDD = "https://gearvn.com/collections/hdd-o-cung-pc?page=";

    public static void main(String[] args) throws IOException {
        double startTime = System.nanoTime();
        System.out.println("Start");
        getAllPages(RAM, 3);
        getAllPages(MainBoard, 7); //limit = 10
        getAllPages(CPU, 5);//limit = 5
        getAllPages(VGA, 4);//limit = 9
        getAllPages(SSD, 4);//limit = 5
        getAllPages(HDD, 2);//limit = 2
        for(int i = 0; i < linksList.size(); i++){
            System.out.println("******************" + linksList.get(i) + "******************");
            getAllItems(linksList.get(i));
        }
        System.out.println("================================******************************************====================================");
        double endTime   = System.nanoTime();
        double totalTime = ((endTime - startTime)/1000000000)/60;
        System.out.println("END");
        System.out.println("total products: " + counter);
        System.out.println("total runtime: " + totalTime);
    }

    public static void getAllPages(String topic, int limit){
        String result = "";
        for(int i = 1; i <= limit; i++){
            result = topic + i;
            linksList.add(result);
        }
    }


    //lay link tung san pham va crawl du lieu
    public static void getAllItems(String url) throws IOException{
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select(".product-row a[href]");
        System.out.println("========================================================================");
        for(Element link : links){
            counter++;
            String itemUrl = "https://gearvn.com" + link.attr("href");
            System.out.println("item link : " + itemUrl);
            getItemName(itemUrl);
            getItemImage(itemUrl);
            getItemPrice(itemUrl);
            getItemInform(itemUrl);
            System.out.println("========================================================================");
        }
    }
    //Get Name
    public static void getItemName(String url) throws  IOException{
        Document doc = Jsoup.connect(url).get();
        Elements names = doc.select("div.col-sm-6.col-xs-12.product_parameters > h1");
        for (Element name: names) {
            System.out.println("\nname : " + name.text());
        }
    }
    //Get Image
    public static void getItemImage(String url) throws IOException{
        Document doc = Jsoup.connect(url).get();
        Elements imgs = doc.select("img[src$=.jpg]");
        for (Element img: imgs) {
            System.out.println("\nimage : " + img.attr("src"));
        }
    }
    //Get Price
    public static void getItemPrice(String url) throws IOException{
        Document doc = Jsoup.connect(url).get();
        Elements prices = doc.select(".product_price > del");
        for(Element price : prices){
            System.out.println("\nprice : " + price.text());
        }
    }
    //Get Information
    public static void getItemInform(String url) throws IOException{
        Document doc = Jsoup.connect(url).get();
        Elements informTopic = doc.select(".specTopic");
        Elements informDetails = doc.select(".specDetail");
        int length = informTopic.size();
        System.out.println("inform: ");
        for(int i = 1; i < length; i++){
            System.out.println(informTopic.get(i).text() + " - " + informDetails.get(i).text());
        }
    }
}
