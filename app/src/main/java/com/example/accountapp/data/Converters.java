package com.example.accountapp.data;

import androidx.room.TypeConverter;
import com.example.accountapp.data.entity.AccountType;
import com.example.accountapp.data.entity.TransactionEntity;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static AccountType fromAccountType(String value) {
        return value == null ? null : AccountType.valueOf(value);
    }

    @TypeConverter
    public static String accountTypeToString(AccountType type) {
        return type == null ? null : type.name();
    }

    @TypeConverter
    public static TransactionEntity.Type fromTransactionType(String value) {
        return value == null ? null : TransactionEntity.Type.valueOf(value);
    }

    @TypeConverter
    public static String transactionTypeToString(TransactionEntity.Type type) {
        return type == null ? null : type.name();
    }

    @TypeConverter
    public static Set<TransactionEntity.Type> fromTransactionTypes(String value) {
        if (value == null || value.isEmpty()) {
            return new HashSet<>();
        }
        return Arrays.stream(value.split(","))
            .map(TransactionEntity.Type::valueOf)
            .collect(Collectors.toSet());
    }

    @TypeConverter
    public static String transactionTypesToString(Set<TransactionEntity.Type> types) {
        if (types == null || types.isEmpty()) {
            return "";
        }
        return types.stream()
            .map(TransactionEntity.Type::name)
            .collect(Collectors.joining(","));
    }
} 