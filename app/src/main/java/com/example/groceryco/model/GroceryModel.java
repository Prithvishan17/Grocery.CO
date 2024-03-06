package com.example.groceryco.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class GroceryModel implements Parcelable {

    private String name;
    private String image;
    private List<Menu>menus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menu) {
        this.menus = menu;
    }

    protected GroceryModel(Parcel in) {
        name = in.readString();
        image = in.readString();
        menus = in.createTypedArrayList(Menu.CREATOR);
    }

    public static final Creator<GroceryModel> CREATOR = new Creator<GroceryModel>() {
        @Override
        public GroceryModel createFromParcel(Parcel in) {
            return new GroceryModel(in);
        }

        @Override
        public GroceryModel[] newArray(int size) {
            return new GroceryModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(image);
        parcel.writeTypedList(menus);
    }
}
