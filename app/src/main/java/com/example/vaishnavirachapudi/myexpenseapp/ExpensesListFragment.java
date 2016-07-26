package com.example.vaishnavirachapudi.myexpenseapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;



public class ExpensesListFragment extends Fragment {

    ListView listView;
    public static final String EXPENSE_KEY="expense";

    private OnFragmentInteractionListener mListener;

    public ExpensesListFragment() {
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
        View view = inflater.inflate(R.layout.fragment_expenses_list, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        Bundle bundle = getArguments();
        final ArrayList list =bundle.getStringArrayList(MainActivity.MY_CUSTOM_FRAGMENT_KEY);
        ExpenseAdapter adapter = new ExpenseAdapter(getContext(), list);
        if(list!=null) {
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                   Expense expense = (Expense)list.get(position);

                    Bundle bundle = new Bundle();
                   bundle.putSerializable(EXPENSE_KEY,expense);
                    ExpenseDetailFragment expenseDetailFragment = new ExpenseDetailFragment();
                    expenseDetailFragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.container, expenseDetailFragment, "expenseDetail")
                            .addToBackStack(null)
                            .commit();

                }
            });


        }


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView)getActivity().findViewById(R.id.listView);

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
