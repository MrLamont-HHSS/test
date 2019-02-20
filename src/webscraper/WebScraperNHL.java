/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webscraper;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author lamon
 */
public class WebScraperNHL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("headless");
        options.addArguments("window-size=1290x1080");
        WebDriver browser = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(browser, 10);

        List<VGData> products = new ArrayList<>();

        String site = "http://www.nhl.com/stats/team?reportType=season&seasonFrom=20172018&seasonTo=20172018&gameType=2&filter=gamesPlayed,gte,1&sort=points,wins";
        browser.get(site);
        WebElement table = browser.findElement(By.cssSelector("div.rt-tbody"));
        
        List<WebElement> rows = table.findElements(By.cssSelector("div.rt-tr-group"));
        for(WebElement row: rows){
            List<WebElement> gameData = row.findElements(By.cssSelector("div.rt-td"));
                int count = 0;
                for(WebElement data: gameData){
                    count++;
                    System.out.print(data.getText());
                    if(count == gameData.size()){
                        System.out.println("");
                    }else{
                        System.out.print(",");
                    }
                }
        }
        System.out.println(table.getText());
        browser.close();
    }

}
