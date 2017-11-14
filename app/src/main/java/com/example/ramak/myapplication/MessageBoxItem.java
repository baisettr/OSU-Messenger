package com.example.ramak.myapplication;

/**
 * Created by songjee on 2017. 11. 1..
 */
import android.graphics.drawable.Drawable;


public class MessageBoxItem {
    private Drawable iconDrawable ;
    private String titleStr ;
    private String descStr ;

    public void setIcon(Drawable icon) {
        System.out.printf("setIcon( )");
        iconDrawable = icon ;
    }


    public void setTitle(String title) {
        System.out.printf("setTitle( )");
        titleStr = title ;
    }


    public void setDesc(String desc) {
        System.out.printf("setDesc( )");
        descStr = desc ;
    }

    public Drawable getIcon() {
        System.out.printf("getIcon( )");
        return this.iconDrawable ;
    }


    public String getTitle() {
        System.out.printf("getTitle( )");
        return this.titleStr ;
    }


    public String getDesc() {
        System.out.printf("getDesc( )");
        return this.descStr ;
    }

}

