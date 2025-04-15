package com.example.accountapp.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.accountapp.data.AppDatabase;
import com.example.accountapp.data.dao.CategoryDao;
import com.example.accountapp.data.entity.Category;
import com.example.accountapp.data.entity.TransactionEntity;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoryRepository {
    private final CategoryDao categoryDao;
    private final ExecutorService executorService;

    public CategoryRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        categoryDao = database.categoryDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Category>> getCategoriesByType(TransactionEntity.Type type) {
        return categoryDao.getCategoriesByType(type);
    }

    public LiveData<List<Category>> getCategoriesByLevel(int level) {
        return categoryDao.getCategoriesByLevel(level);
    }

    public LiveData<List<Category>> getCategoriesByParentId(long parentId) {
        return categoryDao.getCategoriesByParentId(parentId);
    }

    public LiveData<List<Category>> getCategories() {
        return categoryDao.getAllCategories();
    }

    public Category getCategory(long id) {
        return categoryDao.getCategory(id);
    }

    public void insertCategory(Category category) {
        executorService.execute(() -> categoryDao.insert(category));
    }

    public void updateCategory(Category category) {
        executorService.execute(() -> categoryDao.update(category));
    }

    public void deleteCategory(Category category) {
        executorService.execute(() -> categoryDao.delete(category));
    }
} 