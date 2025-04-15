package com.example.accountapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.accountapp.data.entity.Category;
import com.example.accountapp.data.entity.TransactionEntity;
import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories WHERE type = :type ORDER BY name")
    LiveData<List<Category>> getCategoriesByType(TransactionEntity.Type type);

    @Query("SELECT * FROM categories WHERE level = :level ORDER BY name")
    LiveData<List<Category>> getCategoriesByLevel(int level);

    @Query("SELECT * FROM categories WHERE parentId = :parentId ORDER BY name")
    LiveData<List<Category>> getCategoriesByParentId(long parentId);

    @Query("SELECT * FROM categories ORDER BY name")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM categories WHERE id = :id")
    Category getCategory(long id);

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);
} 