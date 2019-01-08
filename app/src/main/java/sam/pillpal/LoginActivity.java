package sam.pillpal;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sam.pillpal.models.InputValidation;
import sam.pillpal.models.DatabaseHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = LoginActivity.this;

    private RelativeLayout loginLayout;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        initViews();
        initListeners();
        initObjects();
        textInputEditTextEmail.setText("sam@gmail.com");// TODO delete after tested
        textInputEditTextPassword.setText("hi"); // TODO delete after tested
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {

        loginLayout = findViewById(R.id.loginLayout);

        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = findViewById(R.id.textViewLinkRegister);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (inputValidation.isInputEditTextEmpty(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (inputValidation.isInputEditTextEmpty(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))) {
            return;
        }

        Editable emailEditable = textInputEditTextEmail.getText();
        Editable passwordEditable = textInputEditTextPassword.getText();
        if (emailEditable == null || passwordEditable == null)  {
            return;
        }
        if (databaseHelper.checkUser(emailEditable.toString().trim()
                , passwordEditable.toString().trim())) {
           Intent accountsIntent = new Intent(activity, CalendarActivity.class);
           accountsIntent.putExtra("EMAIL", "sam@gmail.com"); //textInputEditTextEmail.getText().toString().trim());
           emptyInputEditText();
           startActivity(accountsIntent);
        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar snackbar =
                    Snackbar.make(loginLayout, getString(R.string.error_valid_email_password),
                            Snackbar.LENGTH_LONG);
            snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
            View snackbarView = snackbar.getView();
            TextView textView =
                    snackbarView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(getResources().getColor(R.color.colorTextHint));
            snackbar.show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}