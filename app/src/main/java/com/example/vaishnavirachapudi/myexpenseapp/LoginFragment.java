package com.example.vaishnavirachapudi.myexpenseapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class LoginFragment extends Fragment {



    private OnFragmentInteractionListener mListener;
    EditText email_login;
    EditText password_login;

    public LoginFragment() {
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
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().findViewById(R.id.login_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.signUp();
            }
        });
        getActivity().findViewById(R.id.login_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_login = (EditText)getActivity().findViewById(R.id.email_login);
                password_login=(EditText)getActivity().findViewById(R.id.password_login);
                String email = email_login.getText().toString();
                String password = password_login.getText().toString();
                mListener.loginUser(email, password);
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
       void signUp();
        void loginUser( String email , String password);
    }
}
