// Generated by view binder compiler. Do not edit!
package com.example.accountapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.accountapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentAccountsBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final FloatingActionButton fabAddAccount;

  @NonNull
  public final RecyclerView recyclerViewAccounts;

  @NonNull
  public final TextView textViewNoAccounts;

  private FragmentAccountsBinding(@NonNull CoordinatorLayout rootView,
      @NonNull FloatingActionButton fabAddAccount, @NonNull RecyclerView recyclerViewAccounts,
      @NonNull TextView textViewNoAccounts) {
    this.rootView = rootView;
    this.fabAddAccount = fabAddAccount;
    this.recyclerViewAccounts = recyclerViewAccounts;
    this.textViewNoAccounts = textViewNoAccounts;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentAccountsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentAccountsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_accounts, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentAccountsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.fabAddAccount;
      FloatingActionButton fabAddAccount = ViewBindings.findChildViewById(rootView, id);
      if (fabAddAccount == null) {
        break missingId;
      }

      id = R.id.recyclerViewAccounts;
      RecyclerView recyclerViewAccounts = ViewBindings.findChildViewById(rootView, id);
      if (recyclerViewAccounts == null) {
        break missingId;
      }

      id = R.id.textViewNoAccounts;
      TextView textViewNoAccounts = ViewBindings.findChildViewById(rootView, id);
      if (textViewNoAccounts == null) {
        break missingId;
      }

      return new FragmentAccountsBinding((CoordinatorLayout) rootView, fabAddAccount,
          recyclerViewAccounts, textViewNoAccounts);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
