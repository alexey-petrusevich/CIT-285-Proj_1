/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project1;

/**
 *
 * @author Aliaksei
 */
public class Course {
    private String courseID;
    private String name;
    private String day;
    private String time;
    private String place;
    private Integer ftPrice;
    private Integer ftdPrice;
    private Integer ptPrice;
    private Integer numCredits;
    
    // constructor
    public Course(String courseID, String name, String day,
            String time, String place, int ftPrice, int ftdPrice, int ptPrice,
            int numCredits){
        this.courseID = courseID;
        this.name = name;
        this.day = day;
        this.time = time;
        this.place = place;
        this.ftPrice = ftPrice;
        this.ftdPrice = ftdPrice;
        this.ptPrice = ptPrice;
        this.numCredits = numCredits;
    }
    public String getCourseID(){
        return courseID;
    }
    public String getName(){
        return name;
    }
    public String getDay(){
        return day;
    }
    public String getTime(){
        return time;
    }
    public String getPlace(){
        return place;
    }
    public Integer getFtPrice(){
        return ftPrice;
    }
    public Integer getFtdPrice(){
        return ftdPrice;
    }
    public Integer getPtPrice(){
        return ptPrice;
    }
    public Integer getNumCredits(){
        return numCredits;
    }
    public void setCourseID(String courseID){
        this.courseID = courseID;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDay(String day){
        this.day = day;
    }
    public void setTime(String time){
        this.time = time;
    }
    public void setPlace(String place){
        this.place = place;
    }
    public void setFtPrice(Integer ftPrice){
        this.ftPrice = ftPrice;
    }
    public void setFtdPrice(Integer ftdPrice){
        this.ftdPrice = ftdPrice;
    }
    public void setPtPrice(Integer ptPrice){
        this.ptPrice = ptPrice;
    }
    public void setNumCredits(Integer numCredits){
        this.numCredits = numCredits;
    }
    
    
}
