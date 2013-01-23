package cerebral.effect.forms;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import cerebral.effect.agents.DbAgent;
import cerebral.effect.agents.RESTAgent;
import cerebral.effect.dto.AccountDto;
import cerebral.effect.json.JsonHandler;

import com.geirhilmersen.cerebraleffect.R;
import com.google.gson.Gson;

public class CreateAccountFragment extends DialogFragment{
	private final int MINPASSWORDLENGTH = 5;
	private final String NAMETAKEN = " is not available. Please choose another name.";
	
	private EditText edt_password;
	private EditText edt_confirm_password;
	private TextView ttv_confirm_password;
	
	private EditText edt_clanname;
	private AlertDialog.Builder builder;
	private AlertDialog createUserDialog;
	Context context;
	
	//if in login state, this variable is set to true, 
	//if in create user state, this variable is set to false;
	private boolean isLogin = true;
	@Override
	public Dialog onCreateDialog(Bundle inputstate){
		builder = new AlertDialog.Builder(getActivity());
		context = getActivity();
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View parentView = inflater.inflate(R.layout.form_create_account, null);

		edt_clanname = (EditText) parentView.findViewById(R.id.edt_create_clanname);
		edt_password = (EditText) parentView.findViewById(R.id.edt_create_password);
		
		edt_confirm_password = (EditText) parentView.findViewById(R.id.edt_confirm_password);
		ttv_confirm_password = (TextView) parentView.findViewById(R.id.ttv_confirm_password);
		
		//The listener for the clanname field makes sure that a check is made for available names.
		setClannameListener();
		
		builder.setTitle(R.string.form_create_user);
		builder.setCancelable(false);
		
		//Add listeners for the password fields
		addPasswordListener();
		addConfirmPasswordListener();

		builder.setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(isLogin){
					//Validate password
					//if validated, launch new activity
					
				}else{
					String clanname = edt_clanname.getText().toString();
					String password = edt_password.getText().toString();
					
					AccountDto account = new AccountDto(clanname, password);
					
					//The asynctask createUser handles insertion of the user into the server DB and into the sqliteDB.
					new CreateUser().execute(account);
				}
				
			}
		});
		
		builder.setView(parentView);
		createUserDialog = builder.create();
		
		return createUserDialog;
	}
	
	@Override
	public void onStart(){
		super.onStart();

		setLoginState();

	}
	
	
	//--------------------------------------------------------------------------------------------------------------------------------------
	//---------------LISTENERS FOR THE EDITTEXT FIELDS--------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------------------
	
	
	//The listener on this field checks for changes in the text and compares it to the confirm password field.
	//To enable the confirm password field, the password must of length equal to or above MINPASSWORDLENGTH (member variable)
	private void addPasswordListener(){
		edt_password.addTextChangedListener(new TextWatcher(){
			//TODO: add functionality to make sure that enabled is only set when the number of letters passes the stringlenth limit. now it is repeated for each click.
			@Override
			public void afterTextChanged(Editable s) {
				
				//This code snippet checks if the text matches the minimum required length, and that the passwords written are equal.
				if(s.length() >= MINPASSWORDLENGTH &&
						edt_confirm_password.getText().toString().compareTo(s.toString()) == 0){
					
					setPositiveButtonVisible();
				}else{
					setPasswordsUnequal();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}
			
		});
	}
	
	//Adds a listener that checks if the two passwords are equal and adds
	//a positivebutton to the form if they are.
	private void addConfirmPasswordListener(){
			edt_confirm_password.addTextChangedListener(new TextWatcher(){
			String password;
			@Override
			public void afterTextChanged(Editable s) {
				
				if(edt_password.getText().length() == s.length()){
					password = edt_password.getText().toString();
					
					if(password.compareTo(s.toString()) == 0){ //If the two passwords are an exact match
						setPositiveButtonVisible();//Make sure the confirmdialog is visible.
						
					}else{//The two passwords doesnt match. TODO: this is called excessively
						setPasswordsUnequal();
					}
				}else{
					setPasswordsUnequal();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			
		});
	}

	
	private void setClannameListener(){
		edt_clanname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
					String clanname = ((EditText)v).getText().toString().trim();

					if(clanname != null & clanname.length() > 0){
						new RequestUser().execute(new String[]{clanname});
					}
				}else{
					((EditText) v).setTextColor(Color.BLACK);
				}
			}
		});
		
		edt_clanname.addTextChangedListener(new TextWatcher(){
			String textcontent;
			@Override
			public void afterTextChanged(Editable s) {
				if(!s.toString().equals(textcontent)){
					if(!isLogin){
						setLoginState();
					}
				}
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				textcontent = edt_clanname.getText().toString();
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			}
			
		});
		
	}
	
	//Sets the form attributes so that the user may create a new user
	private void setCreateUserState(){	
		isLogin = false;
		setConfirmPasswordVisible();
		createUserDialog.getButton(AlertDialog.BUTTON_POSITIVE).setText(R.string.confirm_create_user);
	}
	
	//Sets the form attributes so that the user may log in on an existing user
	private void setLoginState(){
		isLogin = true;
		setConfirmPasswordInvisible();
		createUserDialog.getButton(AlertDialog.BUTTON_POSITIVE).setText(R.string.login);
	}
	
	//If the password doesnt match the criteria, make sure the confirmbutton is invisible.
	private void setConfirmPasswordInvisible(){
		edt_confirm_password.setVisibility(View.INVISIBLE);
		ttv_confirm_password.setVisibility(View.INVISIBLE);
	}
	
	//If the password matches the set criteria, enable the confirm button.
	private void setConfirmPasswordVisible(){
		edt_confirm_password.setVisibility(View.VISIBLE);
		ttv_confirm_password.setVisibility(View.VISIBLE);
	}
	
	//Set the confirm button to visible.
	private void setPositiveButtonVisible(){
		createUserDialog.getButton(DialogInterface.BUTTON_POSITIVE).setVisibility(View.VISIBLE);

	}
	
	//Set the confirm button to invisible if not in loginstate.
	private void setPasswordsUnequal(){
		if(!isLogin){
			createUserDialog.getButton(DialogInterface.BUTTON_POSITIVE).setVisibility(View.INVISIBLE);
		}
	}
	
	//-------------------------------------------------------------------------------------------------------
	//-------------NETWORK REQUESTS--------------------------------------------------------------------------
	//-------------------------------------------------------------------------------------------------------	
	
	//THis class ends a POST request to the Pure Effect server that creates a user.
	//TODO: Handle server requests where the server is down, or when the hostname for any reason isn't resolved.
	private class CreateUser extends AsyncTask<AccountDto, AccountDto, AccountDto>{
		private final String URL = "http://www.geirhilmersen.com:8080/api/accounts/";
		@Override
		protected void onPreExecute(){
			
		}
		
		@Override
		protected AccountDto doInBackground(AccountDto... accounts) {
			Gson gson = new Gson();
			String json = gson.toJson(accounts[0]);
			
			//TODO: Make sure that lack of response is handled
			HttpResponse response;
			HttpEntity entity;
			try {
				response = RESTAgent.post(URL, json);
				//entity = response.getEntity();
				//String s = response.toString();
				//entity.getContent();
			} catch (ClientProtocolException e) {
				Log.v("Create user doInBackground", e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				Log.v("Create user doInBackground", e.getMessage());
				e.printStackTrace();
			}
			return accounts[0];
		}
		
		@Override
		protected void onPostExecute(AccountDto account){
			DbAgent dbagent = new DbAgent(context);
			if(!dbagent.createAccount(account)){
				//TODO: Handle this!!
			}
			
		}
	}
	
	//Sends a GET-request to the Pure Effect server that retrieves a user
	private class RequestUser extends AsyncTask<String, Void, String>{
		private final String URL = "http://www.geirhilmersen.com:8080/api/accounts/";
		
		@Override
		protected String doInBackground(String... clannames) {
			String clanname = clannames[0];
			
			try {
				HttpResponse response = RESTAgent.get(URL + clanname);
				HttpEntity entity = response.getEntity();
				clanname = JsonHandler.convertInputStream(entity.getContent());
				
			} catch (ClientProtocolException e) {
				Log.v("Request user: ", e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				Log.v("Request user: ", e.getMessage());
				e.printStackTrace();
			}
			return clanname;
		}
		
		@Override
		protected void onPostExecute(String clanname){
			if(!clanname.equals("null")){
				setLoginState();
				edt_clanname.setError(clanname+NAMETAKEN);
				edt_clanname.setTextColor(getResources().getColor(R.color.selected_blue));
				
			}else{
				setCreateUserState();
				edt_clanname.setTextColor(getResources().getColor(R.color.ok_green));
			}
		}
	}
}
