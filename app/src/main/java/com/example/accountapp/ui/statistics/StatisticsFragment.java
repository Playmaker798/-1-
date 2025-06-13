package com.example.accountapp.ui.statistics;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import android.widget.ArrayAdapter;
import com.example.accountapp.R;
import com.example.accountapp.databinding.FragmentStatisticsBinding;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
import com.example.accountapp.data.entity.Account;
import com.example.accountapp.data.entity.Category;
import com.example.accountapp.data.repository.AccountRepository;
import com.example.accountapp.data.repository.CategoryRepository;
import com.example.accountapp.data.entity.TransactionEntity;
import android.widget.TextView;
import android.content.Context;

public class StatisticsFragment extends Fragment {
    private FragmentStatisticsBinding binding;
    private StatisticsViewModel viewModel;
    private final DecimalFormat currencyFormat;
    private Calendar startDate;
    private Calendar endDate;
    private AccountRepository accountRepository;
    private CategoryRepository categoryRepository;

    public StatisticsFragment() {
        // 自定义货币格式
        currencyFormat = new DecimalFormat("¥#0.00");
        currencyFormat.setNegativePrefix("¥-"); // 设置负数前缀为 "¥-"
        currencyFormat.setPositivePrefix("¥"); // 设置正数前缀为 "¥"
        currencyFormat.setGroupingUsed(false); // 禁用千位分隔符
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(StatisticsViewModel.class);
        accountRepository = new AccountRepository(requireActivity().getApplication());
        categoryRepository = new CategoryRepository(requireActivity().getApplication());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        setupSpinners();
        viewModel.getTransactions().observe(getViewLifecycleOwner(), transactions -> updateStatistics());
    }

    private void setupViews() {
        binding.buttonPreviousMonth.setOnClickListener(v -> {
            Calendar cal = viewModel.getSelectedDate();
            cal.add(Calendar.MONTH, -1);
            viewModel.setSelectedDate(cal);
            updateDateDisplay();
        });

        binding.buttonNextMonth.setOnClickListener(v -> {
            Calendar cal = viewModel.getSelectedDate();
            cal.add(Calendar.MONTH, 1);
            viewModel.setSelectedDate(cal);
            updateDateDisplay();
        });

        binding.buttonDateRange.setOnClickListener(v -> showDateRangePicker());
    }

    private void setupSpinners() {
        // 账户Spinner
        accountRepository.getAllAccounts().observe(getViewLifecycleOwner(), new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                ArrayAdapter<Account> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, accounts);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.spinnerAccount.setAdapter(adapter);
                binding.spinnerAccount.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                        Account selected = (Account) parent.getItemAtPosition(position);
                        android.util.Log.d("StatisticsFragment", "Selected account: " + selected.getName() + " (ID: " + selected.getId() + ")");
                        viewModel.setAccountId(selected.getId());
                        updateStatistics();
                    }
                    @Override
                    public void onNothingSelected(android.widget.AdapterView<?> parent) {
                        android.util.Log.d("StatisticsFragment", "No account selected");
                        viewModel.setAccountId(null);
                        updateStatistics();
                    }
                });
            }
        });
        // 分类Spinner
        categoryRepository.getCategories().observe(getViewLifecycleOwner(), new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                List<Category> categoryList = new ArrayList<>();
                categoryList.add(null);  // Add null for "All" option
                if (categories != null) {
                    categoryList.addAll(categories);
                }
                
                CategoryAdapter adapter = new CategoryAdapter(requireContext(), categoryList);
                binding.spinnerCategory.setAdapter(adapter);
                binding.spinnerCategory.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                        Category selected = (Category) parent.getItemAtPosition(position);
                        android.util.Log.d("StatisticsFragment", "Selected category: " + (selected == null ? "All" : selected.getName()));
                        viewModel.setCategory(selected);
                        updateStatistics();
                    }
                    @Override
                    public void onNothingSelected(android.widget.AdapterView<?> parent) {
                        android.util.Log.d("StatisticsFragment", "No category selected");
                        viewModel.setCategory(null);
                        updateStatistics();
                    }
                });
            }
        });
        // 类型Spinner
        List<String> types = new ArrayList<>();
        types.add("全部");
        types.add("收入");
        types.add("支出");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerType.setAdapter(typeAdapter);
        binding.spinnerType.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selected = (String) parent.getItemAtPosition(position);
                android.util.Log.d("StatisticsFragment", "Selected type: " + selected);
                viewModel.setType(selected);
                updateStatistics();
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
                android.util.Log.d("StatisticsFragment", "No type selected");
                viewModel.setType(null);
                updateStatistics();
            }
        });
    }

    private void showDateRangePicker() {
        final Calendar now = Calendar.getInstance();
        DatePickerDialog startDialog = new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            startDate = Calendar.getInstance();
            startDate.set(year, month, dayOfMonth);
            DatePickerDialog endDialog = new DatePickerDialog(requireContext(), (view2, year2, month2, dayOfMonth2) -> {
                endDate = Calendar.getInstance();
                endDate.set(year2, month2, dayOfMonth2);
                String text = String.format(Locale.getDefault(), "%tF ~ %tF", startDate, endDate);
                binding.buttonDateRange.setText(text);
                if (viewModel instanceof StatisticsViewModel) {
                    ((StatisticsViewModel) viewModel).setDateRange(startDate, endDate);
                }
            }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
            endDialog.setTitle("选择结束日期");
            endDialog.show();
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        startDialog.setTitle("选择开始日期");
        startDialog.show();
    }

    private void updateDateDisplay() {
        Calendar cal = viewModel.getSelectedDate();
        String monthYear = String.format(Locale.getDefault(), "%tB %tY", cal, cal);
        binding.textViewMonthYear.setText(monthYear);
        updateStatistics();
    }

    private void updateStatistics() {
        android.util.Log.d("StatisticsFragment", "Updating statistics...");
        double income = viewModel.getTotalIncome();
        double expense = viewModel.getTotalExpense();
        double balance = income - expense;

        android.util.Log.d("StatisticsFragment", String.format("Statistics - Income: %.2f, Expense: %.2f, Balance: %.2f", income, expense, balance));

        binding.textViewIncome.setText(currencyFormat.format(income));
        binding.textViewExpense.setText(currencyFormat.format(expense));
        binding.textViewBalance.setText(currencyFormat.format(balance));

        // Update progress bars
        double total = income + expense;
        if (total > 0) {
            int incomePercentage = (int) ((income / total) * 100);
            int expensePercentage = (int) ((expense / total) * 100);
            binding.progressBarIncome.setProgress(incomePercentage);
            binding.progressBarExpense.setProgress(expensePercentage);
            android.util.Log.d("StatisticsFragment", String.format("Progress bars - Income: %d%%, Expense: %d%%", incomePercentage, expensePercentage));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private static class CategoryAdapter extends ArrayAdapter<Category> {
        private final Context context;
        private final List<Category> categories;

        public CategoryAdapter(Context context, List<Category> categories) {
            super(context, android.R.layout.simple_spinner_item);
            this.context = context;
            this.categories = categories;
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            
            // Add all items including null
            addAll(categories);
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @Override
        public Category getItem(int position) {
            return categories.get(position);
        }

        @Override
        public long getItemId(int position) {
            Category item = getItem(position);
            return item == null ? 0 : item.getId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_item, parent, false);
            }
            
            TextView textView = (TextView) convertView;
            Category category = getItem(position);
            textView.setText(category == null ? "全部" : category.getName());
            
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
            }
            
            TextView textView = (TextView) convertView;
            Category category = getItem(position);
            textView.setText(category == null ? "全部" : category.getName());
            
            return convertView;
        }
    }
} 