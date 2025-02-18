
package com.tastynearby;

import org.json.JSONException;
import org.json.JSONObject;

import com.tastynearby.library.DatabaseHandler;
import com.tastynearby.library.UserFunctions;
import com.tastynearby.util.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class RegisterActivity extends Activity {
	Button btnRegister;
	Button btnLinkToLogin;
	EditText inputFullName;
	EditText inputEmail;
	EditText inputPassword;
	EditText reinputPassword;
	TextView registerErrorMsg;
	
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";
	Utils util;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		// Importing all assets like buttons, text fields
		inputFullName = (EditText) findViewById(R.id.registerName);
		inputEmail = (EditText) findViewById(R.id.registerEmail);
		inputPassword = (EditText) findViewById(R.id.registerPassword);
		reinputPassword = (EditText) findViewById(R.id.ReregisterPassword);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
		registerErrorMsg = (TextView) findViewById(R.id.register_error);
		
		// Register Button Click event
		btnRegister.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View view) {
				String name = inputFullName.getText().toString();
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();
				String repassword = reinputPassword.getText().toString();
				
				if(!(email.equals("")) && !(password.equals(""))) {
					if(util.isValidEmail(email)){ 
						if(password.equals(repassword)){
							UserFunctions userFunction = new UserFunctions();
							JSONObject json = userFunction.registerUser(name, email, password);
				
							// check for login response
							try {
								if (json.getString(KEY_SUCCESS) != null) {
									registerErrorMsg.setText("");
									String res = json.getString(KEY_SUCCESS); 
									if(Integer.parseInt(res) == 1){
										// user successfully registred
										// Store user details in SQLite Database
										DatabaseHandler db = new DatabaseHandler(getApplicationContext());
										JSONObject json_user = json.getJSONObject("user");
							
										// Clear all previous data in database
										userFunction.logoutUser(getApplicationContext());
										db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));						
										// Launch Dashboard Screen
										Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
										// Close all views before launching Dashboard
										dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										startActivity(dashboard);
										// Close Registration Screen
										finish();
									}else{
										// Error in registration
										registerErrorMsg.setText("Error occured in registration");
									}
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						else{
							registerErrorMsg.setText("Passwords are not matched");
							inputPassword.setText("");
							reinputPassword.setText("");
						}
					}
					else {
						registerErrorMsg.setText("Not a valid Email");
						inputEmail.setText("");
					}
				}
			 	else {
			 		registerErrorMsg.setText("email and password cannot be empty");
				
			 	}
				}
			});

		// Link to Login Screen
		btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(i);
				// Close Registration View
				finish();
			}
		});
	}
}
