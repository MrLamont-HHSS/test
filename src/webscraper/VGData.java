/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webscraper;

/**
 *
 * @author lamon
 */
public class VGData {
    String name;
    String console;
    String publisher;
    String dev;
    String critScore;
    String userScore;
    String sales;
    String release;
    
    @Override
    public String toString(){
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s",name,console,publisher,dev,critScore,userScore,sales,release);
    }
}
