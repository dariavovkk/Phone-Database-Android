package com.jilquero.project2;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PhoneData {
    private PhoneDao mPhoneDao;
    private LiveData<List<Phone>> mPhones;

    PhoneData(Application application) {
        PhoneDatabase phoneDatabase = PhoneDatabase.getDatabase(application);
        mPhoneDao = phoneDatabase.PhoneDao();
        mPhones = mPhoneDao.getAllPhones();
    }
    LiveData<List<Phone>> getAllItems() {
        return mPhones;
    }
    void deleteAll(){
        PhoneDatabase.dbExecutor.execute(() -> {
            mPhoneDao.deleteAll();
        });
    }
    void delete(long id) {
        PhoneDatabase.dbExecutor.execute(() -> {
            mPhoneDao.deleteById(id);
        });
    }
    void insert(Phone item){
        PhoneDatabase.dbExecutor.execute(() -> {
            mPhoneDao.insert(item);
        });
    }
    void update(Phone item) {
        PhoneDatabase.dbExecutor.execute(() ->{
            mPhoneDao.update(item);
        });
    }
}
