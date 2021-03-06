package com.cryptocurrency.liam.symbilityintersectcodechallenge.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cryptocurrency.liam.symbilityintersectcodechallenge.Model.CryptoCurrency;

import java.util.List;

/**
 * Dao accessing cryptocurrency object table
 */
@Dao
public interface CryptoCurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CryptoCurrency currencies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<CryptoCurrency> currencies);

    @Delete
    void delete(CryptoCurrency currencies);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(List<CryptoCurrency> currencies);

    @Query("SELECT id FROM crypto_currency WHERE is_liked=1")
    List<Long> getLikedCurrencies();

    @Query("SELECT * FROM crypto_currency")
    List<CryptoCurrency> getAllCurrencies();

}
