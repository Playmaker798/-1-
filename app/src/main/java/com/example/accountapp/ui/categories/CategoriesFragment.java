package com.example.accountapp.ui.categories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.accountapp.R;
import com.example.accountapp.data.entity.Category;
import com.example.accountapp.databinding.FragmentCategoriesBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class CategoriesFragment extends Fragment {
    private FragmentCategoriesBinding binding;
    private CategoriesViewModel viewModel;
    private CategoriesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CategoriesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        observeData();
    }

    private void setupViews() {
        binding.recyclerViewCategories.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CategoriesAdapter(new CategoriesAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(Category category) {
                CategoryDialogFragment dialog = CategoryDialogFragment.newInstance(category.getId());
                dialog.show(getChildFragmentManager(), "CategoryDialog");
            }

            @Override
            public void onCategoryLongClick(Category category) {
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle(R.string.delete_category)
                        .setMessage(R.string.delete_category_confirmation)
                        .setPositiveButton(R.string.delete, (dialog, which) -> viewModel.delete(category))
                        .setNegativeButton(R.string.cancel, null)
                        .show();
            }
        });
        binding.recyclerViewCategories.setAdapter(adapter);

        binding.fabAddCategory.setOnClickListener(v -> {
            CategoryDialogFragment dialog = CategoryDialogFragment.newInstance(-1);
            dialog.show(getChildFragmentManager(), "CategoryDialog");
        });
    }

    private void observeData() {
        viewModel.getCategories().observe(getViewLifecycleOwner(), categories -> {
            adapter.submitList(categories);
            binding.textViewNoCategories.setVisibility(categories.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 