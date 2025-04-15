package com.example.accountapp.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.accountapp.data.Converters;
import com.example.accountapp.data.entity.TransactionEntity;

@Entity(tableName = "categories")
@TypeConverters(Converters.class)
public class Category {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private TransactionEntity.Type type;
    private String icon;
    private String color;
    private long parentId; // 父分类ID，0表示一级分类
    private int level; // 分类级别：1表示一级分类，2表示二级分类

    public Category(String name, TransactionEntity.Type type, String icon, String color, long parentId, int level) {
        this.name = name;
        this.type = type;
        this.icon = icon;
        this.color = color;
        this.parentId = parentId;
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TransactionEntity.Type getType() {
        return type;
    }

    public void setType(TransactionEntity.Type type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id == category.id &&
            name.equals(category.name) &&
            type == category.type &&
            icon.equals(category.icon) &&
            color.equals(category.color) &&
            parentId == category.parentId &&
            level == category.level;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id, name, type, icon, color, parentId, level);
    }
} 