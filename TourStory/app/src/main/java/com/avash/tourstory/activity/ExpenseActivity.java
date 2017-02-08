package com.avash.tourstory.activity;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avash.tourstory.R;
import com.avash.tourstory.adapter.EventExpenseAdapter;
import com.avash.tourstory.database.ExpenseManager;
import com.avash.tourstory.model.ExpenseModel;

import java.util.ArrayList;

public class ExpenseActivity extends AppCompatActivity {

    ListView expenseListView;

    EditText titleET,amountET;
    TextView totalTV;
    Button addButton;


    EventExpenseAdapter eventExpenseAdapter;
    ArrayList<ExpenseModel> expenseModels;
    ExpenseManager expenseManager;

    int eid;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        expenseListView = (ListView) findViewById(R.id.expenseAllListView);

        titleET = (EditText) findViewById(R.id.expenseTitleEditText);
        amountET = (EditText) findViewById(R.id.expenseAmountEditText);

        totalTV = (TextView) findViewById(R.id.expenseTotalTextView);

        addButton = (Button) findViewById(R.id.expenseAddButton);

        expenseManager= new ExpenseManager(this);

        eid = getIntent().getIntExtra("eid",0);

        expenseSetAdapter();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleET.getText().toString();
                int amount = Integer.parseInt(amountET.getText().toString());
                long result = expenseManager.addExpense(eid,title,amount);
                if (result>0){
                    Toast.makeText(ExpenseActivity.this, "Expense successfully added.", Toast.LENGTH_SHORT).show();
                    titleET.setText(null);
                    amountET.setText(null);
                    titleET.requestFocus();
                    expenseSetAdapter();
                }
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void expenseSetAdapter() {
        expenseModels = expenseManager.getAllExpense(eid);
        if (expenseModels !=null){
            eventExpenseAdapter = new EventExpenseAdapter(ExpenseActivity.this,expenseModels);
            expenseListView.setAdapter(eventExpenseAdapter);
            expenseListView.setItemsCanFocus(true);

            totalTV.setText(expenseManager.getTotalExpenseAmount(eid)+" TK");
        }


    }
}
