/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webscraper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author lamon
 */
public class WebScraperLego {

    private static void until(WebDriver driver, Function<WebDriver, Boolean> waitCondition, Long timeoutInSeconds) {
        // blah blah blah
        WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSeconds);
        webDriverWait.withTimeout(timeoutInSeconds, TimeUnit.SECONDS);
        try {
            webDriverWait.until(waitCondition);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void untilPageLoadComplete(WebDriver driver, Long timeoutInSeconds) {
        until(driver, (d)
                -> {
            Boolean isPageLoaded = (Boolean) ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            if (!isPageLoaded) {
                System.out.println("Document is loading");
            }
            return isPageLoaded;
        }, timeoutInSeconds);
    }

    public static void untilJqueryIsDone(WebDriver driver, Long timeoutInSeconds) {
        until(driver, (d)
                -> {
            Boolean isJqueryCallDone = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active==0");
            if (!isJqueryCallDone) {
                System.out.println("JQuery call is in Progress");
            }
            return isJqueryCallDone;
        }, timeoutInSeconds);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String[] categories = {"https://shop.lego.com/en-CA/category/animals", "https://shop.lego.com/en-CA/category/buildings", "https://shop.lego.com/en-CA/category/cars", "https://shop.lego.com/en-CA/category/fantasy", "https://shop.lego.com/en-CA/category/ninjas", "https://shop.lego.com/en-CA/category/preschool", "https://shop.lego.com/en-CA/category/robotics", "https://shop.lego.com/en-CA/category/seasonal", "https://shop.lego.com/en-CA/category/space", "https://shop.lego.com/en-CA/category/trains", "https://shop.lego.com/en-CA/category/vehicles"};
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("headless");
        options.addArguments("window-size=1290x1080");
        WebDriver browser = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(browser, 10);

        List<String> productLinks = new ArrayList<>();
        
        List<String> prices = new ArrayList<>();
        List<String> descriptions = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        List<String> pieces = new ArrayList<>();
        
        for (String url : categories) {
            int page = 1;
            browser.get(url);
            WebElement text = browser.findElement(By.xpath("//*[@id=\"main-content\"]/div[1]/div[4]/span[1]"));
            String[] numberRaw = text.getText().split(" ");
            int numPages = Integer.parseInt(numberRaw[2]);
            // go through each page on in the category
            for (int i = 1; i <= numPages; i++) {
                browser.get(url + "?page=" + i);
                System.out.println("Page " + i);

                List<WebElement> items = browser.findElements(By.className("kEHKRE"));
                // sale: fRjLmi
                // normal: eptZBP
                for (WebElement item : items) {
                    String link = item.findElement(By.className("jEGNop")).getAttribute("href");
                    productLinks.add(link);
                    WebElement priceElement = null;
                    try {
                        priceElement = item.findElement(By.className("eptZBP"));
                    } catch (Exception e) {
                        priceElement = item.findElement(By.className("fRjLmi"));
                    }
                    String price = priceElement.getText();
                    prices.add(price);
                }
            }
        }

        for (String product : productLinks) {
            browser.get(product);
            WebElement desc = browser.findElement(By.className("JCTXT"));
            List<WebElement> productInfo = browser.findElements(By.className("dEezgi"));
            
            descriptions.add(desc.getText());
            ids.add(productInfo.get(0).getText());
            if(productInfo.size() > 3){
                pieces.add(productInfo.get(3).getText());
            }else{
                pieces.add("0");
            }
            
            //System.out.println(desc.getText() + " - " + productInfo.get(0).getText() + "\n" + productInfo.get(3).getText() + "\n" + price);
            //System.out.println("");
        }
        
        for(int i = 0; i < descriptions.size(); i++){
            System.out.printf("%s,%s,%s,%s\n",descriptions.get(i), ids.get(i), pieces.get(i), prices.get(i));
        }
        
    }
}
