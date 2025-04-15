package com.example.accountapp.ui.categories;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.accountapp.R;
import com.example.accountapp.data.entity.Category;
import com.example.accountapp.data.entity.TransactionEntity;
import com.example.accountapp.databinding.DialogCategoryBinding;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class CategoryDialogFragment extends DialogFragment {
    private DialogCategoryBinding binding;
    private CategoriesViewModel viewModel;
    private long categoryId = -1;
    private String selectedIcon;
    private List<Category> parentCategories = new ArrayList<>();
    private IconAdapter iconAdapter;

    public static CategoryDialogFragment newInstance() {
        return new CategoryDialogFragment();
    }

    public static CategoryDialogFragment newInstance(long categoryId) {
        CategoryDialogFragment fragment = new CategoryDialogFragment();
        Bundle args = new Bundle();
        args.putLong("categoryId", categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
        viewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        if (getArguments() != null) {
            categoryId = getArguments().getLong("categoryId", -1);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DialogCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        setupIconRecyclerView();
        observeData();
        if (categoryId != -1) {
            viewModel.getCategory(categoryId);
        }
    }

    private void setupViews() {
        TextInputEditText editName = binding.editTextName;
        AutoCompleteTextView spinnerType = binding.spinnerType;
        AutoCompleteTextView spinnerParentCategory = binding.spinnerParentCategory;

        // Setup type spinner
        ArrayAdapter<TransactionEntity.Type> typeAdapter = new ArrayAdapter<>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            TransactionEntity.Type.values()
        );
        spinnerType.setAdapter(typeAdapter);

        // Setup parent category spinner
        ArrayAdapter<Category> parentCategoryAdapter = new ArrayAdapter<>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            parentCategories
        );
        spinnerParentCategory.setAdapter(parentCategoryAdapter);

        binding.buttonSave.setOnClickListener(v -> saveCategory());
        binding.buttonCancel.setOnClickListener(v -> dismiss());
    }

    private void setupIconRecyclerView() {
        RecyclerView recyclerView = binding.recyclerViewIcons;
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 6));
        iconAdapter = new IconAdapter(icon -> {
            selectedIcon = icon;
            iconAdapter.setSelectedIcon(icon);
        });
        recyclerView.setAdapter(iconAdapter);
    }

    private void observeData() {
        viewModel.getParentCategories().observe(getViewLifecycleOwner(), categories -> {
            parentCategories.clear();
            parentCategories.addAll(categories);
            ((ArrayAdapter<Category>) binding.spinnerParentCategory.getAdapter()).notifyDataSetChanged();
        });

        viewModel.getCategoryById().observe(getViewLifecycleOwner(), category -> {
            if (category != null) {
                populateFields(category);
            }
        });
    }

    private void populateFields(Category category) {
        binding.editTextName.setText(category.getName());
        binding.spinnerType.setText(category.getType().toString(), false);
        if (category.getParentId() != 0) {
            for (Category parent : parentCategories) {
                if (parent.getId() == category.getParentId()) {
                    binding.spinnerParentCategory.setText(parent.getName(), false);
                    break;
                }
            }
        }
        selectedIcon = category.getIcon();
        iconAdapter.setSelectedIcon(selectedIcon);
    }

    private void saveCategory() {
        String name = binding.editTextName.getText().toString().trim();
        TransactionEntity.Type type = TransactionEntity.Type.valueOf(binding.spinnerType.getText().toString());
        String parentCategoryName = binding.spinnerParentCategory.getText().toString().trim();
        long parentId = 0;

        if (!parentCategoryName.isEmpty()) {
            for (Category parent : parentCategories) {
                if (parent.getName().equals(parentCategoryName)) {
                    parentId = parent.getId();
                    break;
                }
            }
        }

        int level = parentId == 0 ? 1 : 2;

        if (name.isEmpty() || selectedIcon == null) {
            return;
        }

        Category category = new Category(name, type, selectedIcon, "#FF0000", parentId, level);
        if (categoryId != -1) {
            category.setId(categoryId);
            viewModel.updateCategory(category);
        } else {
            viewModel.insertCategory(category);
        }
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
} 