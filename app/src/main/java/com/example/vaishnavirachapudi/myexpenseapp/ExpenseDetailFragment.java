package com.example.vaishnavirachapudi.myexpenseapp;

import android.content.Context;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ExpenseDetailFragment extends Fragment {



    private OnFragmentInteractionListener mListener;

    TextView name;
    TextView category;
    TextView amount;
    TextView date;

    public ExpenseDetailFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense_detail, container, false);
        Bundle bundle = getArguments();
        Expense expense =(Expense)bundle.getSerializable(ExpensesListFragment.EXPENSE_KEY);

        name =(TextView)view.findViewById(R.id.name_detail);
        category=(TextView)view.findViewById(R.id.cate_detail);
        amount=(TextView)view.findViewById(R.id.amount_detail);
        date =(TextView)view.findViewById(R.id.date_detail);
        name.setText(expense.getName());
        category.setText(expense.getCategory());
        amount.setText(expense.getAmount());
        date.setText(expense.getDate());
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

    }
}
