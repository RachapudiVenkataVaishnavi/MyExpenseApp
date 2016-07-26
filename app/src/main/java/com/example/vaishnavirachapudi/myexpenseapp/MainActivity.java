package com.example.vaishnavirachapudi.myexpenseapp;

import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity  implements LoginFragment.OnFragmentInteractionListener,SignUpFragment.OnFragmentInteractionListener,ExpensesListFragment.OnFragmentInteractionListener,
        AddExpenseFragment.OnFragmentInteractionListener,ExpenseDetailFragment.OnFragmentInteractionListener{
    Firebase myFirebaseRef;
    public static final String MY_CUSTOM_FRAGMENT_KEY = "CUSTOM_TEXT";
    public static final String USER_KEY="user";
    String user=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase("https://androidhomework09.firebaseio.com/");

        getFragmentManager().beginTransaction()
                .add(R.id.container, new LoginFragment(), "login")
                .commit();

    }



    @Override
    public void signUp() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new SignUpFragment(), "signUp")
                .commit();
    }

    @Override
    public void loginUser(String email, String password) {

        if(email == null || ("").equals(email)){
            Toast.makeText(MainActivity.this, "Please enter username", Toast.LENGTH_SHORT).show();
        }else if(password == null || ("").equals(password)){
            Toast.makeText(MainActivity.this,"Please enter password",Toast.LENGTH_SHORT).show();
        }else{
            user=email;
            Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    Toast.makeText(MainActivity.this, "Login in successfully!", Toast.LENGTH_LONG).show();




                    myFirebaseRef.child("Expenses").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            HashMap<String,Object> expenses = (HashMap<String,Object>) snapshot.getValue();
                            final ArrayList<Expense> list = new ArrayList<Expense>();
                            int i = 1;
                            for (String key : expenses.keySet()) {
                                Object value = expenses.get(key);



                                if (value instanceof Object) {
                                    HashMap<String, String> map = (HashMap<String, String>) value;
                                    System.out.println("Class: " + map.get("user"));
                                    Expense expense = new Expense(map.get("category"), map.get("date"), map.get("name"),
                                            map.get("user"),map.get("amount")   );

                                    System.out.println("user: " + expense.toString());
                                    if(map.get("user").equals(user))
                                    list.add(expense);


                                }
                                i++;
                            }

                            getFragmentManager().beginTransaction()
                                   .replace(R.id.container, createCustomFragment(list)).commit();
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            String message = "Server error. Refresh page";
                            // Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    });





                }
                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    switch (firebaseError.getCode()) {
                        case FirebaseError.INVALID_EMAIL:
                            Toast.makeText(MainActivity.this,"Please enter a valid email address",Toast.LENGTH_SHORT).show();

                            break;
                        case FirebaseError.USER_DOES_NOT_EXIST:
                            Toast.makeText(MainActivity.this,"User doesn't exist",Toast.LENGTH_SHORT).show();

                            break;
                        case FirebaseError.INVALID_PASSWORD:
                            Toast.makeText(MainActivity.this,"Wrong Password",Toast.LENGTH_SHORT).show();

                            break;
                    }
                }
            };
            myFirebaseRef.authWithPassword(email , password , authResultHandler);
        }


    }


    @Override
    public void createUserAccount(String name, String email, String password) {
        User user = new User(name,email,password);
        createUser(user);
    }

    @Override
    public void goToLogin() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new LoginFragment(), "login")
                .commit();
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_expense) {

            if(user!=null) {
                Bundle bundle = new Bundle();
                bundle.putString(USER_KEY, user);
                AddExpenseFragment addExpenseFragment = new AddExpenseFragment();
                addExpenseFragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, addExpenseFragment, "addExpense")
                        .addToBackStack(null)
                        .commit();
            }else
            {
                Toast.makeText(MainActivity.this,"Please login first",Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (item.getItemId() == R.id.log_out) {
            myFirebaseRef.unauth();

             user=null;
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new LoginFragment(), "login")
                    .addToBackStack(null)
                    .commit();
            return true;
        }
        return true;
    }



    private Fragment createCustomFragment(ArrayList list) {

        Bundle bundle = new Bundle();
        bundle.putStringArrayList(MY_CUSTOM_FRAGMENT_KEY, list);
        ExpensesListFragment placeholderFragment = new ExpensesListFragment();
        placeholderFragment.setArguments(bundle);
        return placeholderFragment;
    }

    private void createUser(final User user) {

        myFirebaseRef.createUser(user.getEmail(), user.getPassword(), new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                Firebase expenseRef = myFirebaseRef.child("users").child(user.getUserName());
                expenseRef.setValue(user);
                Toast.makeText(MainActivity.this, "Sign up successfully!", Toast.LENGTH_LONG).show();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, new LoginFragment(), "login")
                        .commit();
                if (getFragmentManager().getBackStackEntryCount() > 0) {

                    getFragmentManager().popBackStack();
                }
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                switch (firebaseError.getCode()) {
                    case FirebaseError.EMAIL_TAKEN:
                        Toast.makeText(MainActivity.this, "Email Already Exist!", Toast.LENGTH_LONG).show();
                        break;
                    case FirebaseError.INVALID_EMAIL:
                        Toast.makeText(MainActivity.this, "Invalid email address!", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getFragmentManager().popBackStack();
        }
    }
    @Override
    public void addExpense(String name, String category, String amount, String date ,String user) {
        if(name== null || name.equals("") || name.isEmpty() ){
            Toast.makeText(MainActivity.this , "Please Enter the Name!", Toast.LENGTH_LONG).show();
        }
        if(category== null || category.equals("") || category.isEmpty() ){
            Toast.makeText(MainActivity.this , "Please Enter the category!", Toast.LENGTH_LONG).show();
        }
        if(amount== null || amount.equals("") || amount.isEmpty() ){
            Toast.makeText(MainActivity.this , "Please Enter the category!", Toast.LENGTH_LONG).show();
        }

        if(date== null || date.equals("") || date.isEmpty()){
            Toast.makeText(MainActivity.this , "Please Enter the date!", Toast.LENGTH_LONG).show();
        }
         Expense expense = new Expense(category , date , name , user , amount);
          Firebase expenseRef = myFirebaseRef.child("Expenses");
          expenseRef.push().setValue(expense);
        Toast.makeText(MainActivity.this , "success!", Toast.LENGTH_LONG).show();
    }


}
