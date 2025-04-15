package com.example.accountapp.ui.transactions;

import com.example.accountapp.data.entity.TransactionEntity;

/**
 * Interface for handling transaction click events
 */
public interface OnTransactionClickListener {
    /**
     * Called when a transaction is clicked
     * @param transaction The transaction that was clicked
     */
    void onTransactionClick(TransactionEntity transaction);
    
    /**
     * Called when a transaction's edit button is clicked
     * @param transaction The transaction to edit
     */
    void onTransactionEditClick(TransactionEntity transaction);
    
    /**
     * Called when a transaction's delete button is clicked
     * @param transaction The transaction to delete
     */
    void onTransactionDeleteClick(TransactionEntity transaction);
} 