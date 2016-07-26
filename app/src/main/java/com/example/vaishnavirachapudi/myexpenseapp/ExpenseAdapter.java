package com.example.vaishnavirachapudi.myexpenseapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vaishnavirachapudi on 02/24/16.
 */
public class ExpenseAdapter extends ArrayAdapter<Expense> {

    Context context;
    ArrayList<Expense> expensesList;

    public ExpenseAdapter(Context context, ArrayList<Expense> expensesList) {
        super(context,R.layout.expense_line_layout, expensesList);
        this.context = context;
        this.expensesList = expensesList;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.expense_line_layout, parent, false);
            holder = new ViewHolder();
            holder.expenseName = (TextView)convertView.findViewById(R.id.expenseName);
            holder.expenseAmount = (TextView)convertView.findViewById(R.id.expenseAmount);
            convertView.setTag(holder);
        }
        holder = (ViewHolder)convertView.getTag();

        TextView expenseName = holder.expenseName;
        TextView expenseAmount = holder.expenseAmount;

        expenseName.setText(expensesList.get(position).getName());
        String amount = "$" + expensesList.get(position).getAmount();
        expenseAmount.setText(amount);

        return convertView;
    }

    static class ViewHolder {
        TextView expenseName, expenseAmount;
    }
}
