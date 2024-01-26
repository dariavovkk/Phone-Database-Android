package com.jilquero.project2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneModel extends AndroidViewModel {
    private final PhoneData mData;
    private final LiveData<List<Phone>> mItems;

    public PhoneModel(@NonNull Application application) {
        super(application);
        mData = new PhoneData(application);
        mItems = mData.getAllItems();
    }

    LiveData<List<Phone>> getAllPhones(){
        return mItems;
    }
    public void insert(Phone item){
        mData.insert(item);
    }
    public void update(Phone item){
        mData.update(item);
    }
    public void delete(long id) {
        mData.delete(id);
    }
    public void deleteAll(){
        mData.deleteAll();
    }
}
