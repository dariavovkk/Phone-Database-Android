package com.jilquero.project2;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "phones")
public class Phone implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String manufacturer;

    @NonNull
    private String model;

    @NonNull
    private int androidVersion;

    @NonNull
    private String website;

    public Phone(@NonNull String manufacturer, @NonNull String model, @NonNull int androidVersion, @NonNull String website) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.androidVersion = androidVersion;
        this.website = website;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getManufacturer(){
        return manufacturer;
    }
    public void setManufacturer(@NonNull String manufacturer){
        this.manufacturer = manufacturer;
    }
    public String getModel(){
        return model;
    }
    public void setModel(@NonNull String model){
        this.model = model;
    }
    public int getAndroidVersion(){
        return androidVersion;
    }
    public void setAndroidVersion(@NonNull int androidVersion){
        this.androidVersion = androidVersion;
    }
    public String getWebsite(){
        return website;
    }
    public void setWebsite(@NonNull String website) {
        this.website = website;
    }
    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", producent='" + manufacturer + '\'' +
                ", model='" + model + '\'' +
                ", a_version=" + androidVersion +
                ", url='" + website + '\'' +
                '}';
    }
}
