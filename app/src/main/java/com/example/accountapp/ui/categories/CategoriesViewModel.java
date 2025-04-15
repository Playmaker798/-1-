package com.example.accountapp.ui.categories;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.accountapp.data.entity.Category;
import com.example.accountapp.data.entity.TransactionEntity;
import com.example.accountapp.data.repository.CategoryRepository;
import java.util.List;

public class CategoriesViewModel extends AndroidViewModel {
    private final CategoryRepository categoryRepository;
    private final MutableLiveData<Category> categoryById = new MutableLiveData<>();
    private final LiveData<List<Category>> parentCategories;

    public CategoriesViewModel(Application application) {
        super(application);
        categoryRepository = new CategoryRepository(application);
        parentCategories = categoryRepository.getCategoriesByLevel(1);
    }

    public LiveData<List<Category>> getCategoriesByType(TransactionEntity.Type type) {
        return categoryRepository.getCategoriesByType(type);
    }

    public LiveData<List<Category>> getParentCategories() {
        return parentCategories;
    }

    public LiveData<List<Category>> getChildCategories(long parentId) {
        return categoryRepository.getCategoriesByParentId(parentId);
    }

    public void getCategory(long id) {
        new Thread(() -> {
            Category category = categoryRepository.getCategory(id);
            categoryById.postValue(category);
        }).start();
    }

    public LiveData<Category> getCategoryById() {
        return categoryById;
    }

    public void insertCategory(Category category) {
        categoryRepository.insertCategory(category);
    }

    public void updateCategory(Category category) {
        categoryRepository.updateCategory(category);
    }

    public void deleteCategory(Category category) {
        categoryRepository.deleteCategory(category);
    }

    public LiveData<List<Category>> getCategories() {
        return categoryRepository.getCategories();
    }

    public void delete(Category category) {
        categoryRepository.deleteCategory(category);
    }
} 