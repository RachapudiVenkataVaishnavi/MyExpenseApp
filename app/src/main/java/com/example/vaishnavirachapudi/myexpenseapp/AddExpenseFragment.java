package com.example.vaishnavirachapudi.myexpenseapp;

import android.content.Context;

import android.os.Bundle;
import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class AddExpenseFragment extends Fragment {

    Button addExpense;
    EditText name , amount , date;
    String selected = null;
    Spinner spinner;


    private OnFragmentInteractionListener mListener;

    public AddExpenseFragment() {
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
        return inflater.inflate(R.layout.fragment_add_expense, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
       final String user =bundle.getString(MainActivity.USER_KEY);

        spinner = (Spinner)getActivity().findViewById(R.id.spinner);
        name = (EditText) getActivity().findViewById(R.id.editText);
        amount = (EditText) getActivity().findViewById(R.id.editText2);
        date = (EditText) getActivity().findViewById(R.id.editText3);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (getActivity(), R.array.spinner_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        addExpense = (Button) getActivity().findViewById(R.id.button);
        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText  = name.getText().toString();
                String amountText  = amount.getText().toString();
                String dateText  = date.getText().toString();
                if(nameText== null || nameText.equals("") || nameText.isEmpty() ){
                    Toast.makeText(getContext(), "Please Enter the Name!", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(selected== null || selected.equals("Select Category") || selected.isEmpty() ){
                    Toast.makeText(getContext() , "Please Select the category!", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(amountText== null || amountText.equals("") || amountText.isEmpty() ){
                    Toast.makeText(getContext() , "Please Enter the amount!", Toast.LENGTH_LONG).show();
                    return;
                }

               else if(dateText== null || dateText.equals("") || dateText.isEmpty()){
                    Toast.makeText(getContext() , "Please Enter the date!", Toast.LENGTH_LONG).show();
                    return;
                }
                else
                mListener.addExpense(nameText, selected, amountText, dateText,user);
            }
        });

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

        void addExpense(String name , String category , String amount , String date ,String user);

    }
}
