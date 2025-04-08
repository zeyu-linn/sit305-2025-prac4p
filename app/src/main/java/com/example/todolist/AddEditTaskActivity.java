package com.example.todolist;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddEditTaskActivity extends AppCompatActivity {
    private static final String TAG = "AddEditTaskActivity";
    public static final String EXTRA_ID = "com.example.todolist.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.todolist.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION = "com.example.todolist.EXTRA_DESCRIPTION";
    public static final String EXTRA_START_DATE = "com.example.todolist.EXTRA_START_DATE";
    public static final String EXTRA_END_DATE = "com.example.todolist.EXTRA_END_DATE";

    private TextInputEditText editTextTitle;
    private TextInputEditText editTextDescription;
    private Button buttonStartDate;
    private Button buttonEndDate;
    private Button buttonSave;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        try {
            // 隐藏默认的ActionBar
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }

            editTextTitle = findViewById(R.id.edit_text_title);
            editTextDescription = findViewById(R.id.edit_text_description);
            buttonStartDate = findViewById(R.id.button_start_date);
            buttonEndDate = findViewById(R.id.button_end_date);
            buttonSave = findViewById(R.id.button_save);
            ImageButton buttonBack = findViewById(R.id.button_back);

            startCalendar = Calendar.getInstance();
            endCalendar = Calendar.getInstance();
            endCalendar.add(Calendar.MONTH, 1); // 默认结束日期为一个月后
            dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());

            buttonBack.setOnClickListener(v -> finish());
            buttonStartDate.setOnClickListener(v -> showDatePickerDialog(true));
            buttonEndDate.setOnClickListener(v -> showDatePickerDialog(false));
            buttonSave.setOnClickListener(v -> saveTask());

            Intent intent = getIntent();
            if (intent.hasExtra(EXTRA_ID)) {
                editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
                editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
                startCalendar.setTimeInMillis(intent.getLongExtra(EXTRA_START_DATE, startCalendar.getTimeInMillis()));
                endCalendar.setTimeInMillis(intent.getLongExtra(EXTRA_END_DATE, endCalendar.getTimeInMillis()));
                buttonSave.setText("Update Project");
            }

            updateDateButtonsText();
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreate: ", e);
            Toast.makeText(this, "初始化失败: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void showDatePickerDialog(boolean isStartDate) {
        try {
            Calendar calendar = isStartDate ? startCalendar : endCalendar;
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                
                // 确保结束日期不早于开始日期
                if (!isStartDate && calendar.before(startCalendar)) {
                    Toast.makeText(this, "结束日期不能早于开始日期", Toast.LENGTH_SHORT).show();
                    calendar.setTime(startCalendar.getTime());
                }
                // 确保开始日期不晚于结束日期
                if (isStartDate && calendar.after(endCalendar)) {
                    Toast.makeText(this, "开始日期不能晚于结束日期", Toast.LENGTH_SHORT).show();
                    calendar.setTime(endCalendar.getTime());
                }
                
                updateDateButtonsText();
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
               calendar.get(Calendar.DAY_OF_MONTH)).show();
        } catch (Exception e) {
            Log.e(TAG, "Error showing date picker: ", e);
            Toast.makeText(this, "无法显示日期选择器", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDateButtonsText() {
        buttonStartDate.setText(dateFormat.format(startCalendar.getTime()));
        buttonEndDate.setText(dateFormat.format(endCalendar.getTime()));
    }

    private void saveTask() {
        try {
            String title = Objects.requireNonNull(editTextTitle.getText()).toString().trim();
            String description = Objects.requireNonNull(editTextDescription.getText()).toString().trim();

            if (title.isEmpty()) {
                editTextTitle.setError("请输入项目名称");
                editTextTitle.requestFocus();
                return;
            }

            Intent data = new Intent();
            data.putExtra(EXTRA_TITLE, title);
            data.putExtra(EXTRA_DESCRIPTION, description);
            data.putExtra(EXTRA_START_DATE, startCalendar.getTimeInMillis());
            data.putExtra(EXTRA_END_DATE, endCalendar.getTimeInMillis());

            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1) {
                data.putExtra(EXTRA_ID, id);
            }

            setResult(RESULT_OK, data);
            finish();
        } catch (Exception e) {
            Log.e(TAG, "Error saving task: ", e);
            Toast.makeText(this, "保存失败: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
} 