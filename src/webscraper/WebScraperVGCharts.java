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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author lamon
 */
public class WebScraperVGCharts {

    private static void until(WebDriver driver, Function<WebDriver, Boolean> waitCondition, Long timeoutInSeconds) {
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
        String system = "PS4";
        int numPages = 6;
        
        
        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("window-size=1290x1080");
        WebDriver browser = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(browser, 10);

        List<VGData> products = new ArrayList<>();
        
        
        for (int i = 1; i <= numPages; i++) {
            String site = "http://www.vgchartz.com/games/games.php?name=&keyword=&region=All&developer=&publisher=&goty_year=&genre=&boxart=Both&banner=Both&ownership=Both&results=200&order=TotalSales&showtotalsales=0&showtotalsales=1&showpublisher=0&showpublisher=1&showvgchartzscore=0&showvgchartzscore=1&shownasales=0&shownasales=1&showdeveloper=0&showdeveloper=1&showcriticscore=0&showcriticscore=1&showpalsales=0&showreleasedate=0&showreleasedate=1&showuserscore=0&showuserscore=1&showjapansales=0&showlastupdate=0&showlastupdate=1&showothersales=0&page="+i+"&console="+system;
            browser.get(site);
            WebElement table = browser.findElement(By.cssSelector("div#generalBody"));
            List<WebElement> rows = table.findElements(By.cssSelector("tr"));
            
            for(int j = 3;j < rows.size();j++){
                List<WebElement> gameData = rows.get(j).findElements(By.cssSelector("td"));
                int count = 0;
                for(WebElement data: gameData){
                    count++;
                    System.out.print(data.getText().replace("\nRead the review", ""));
                    if(count == gameData.size()){
                        System.out.println("");
                    }else{
                        System.out.print(",");
                    }
                }
            }
        }
        
        browser.close();
        
//        for(int i = 0; i < descriptions.size(); i++){
//            System.out.printf("%s,%s,%s,%s\n",descriptions.get(i), ids.get(i), pieces.get(i), prices.get(i));
//        }
    }
    
}
