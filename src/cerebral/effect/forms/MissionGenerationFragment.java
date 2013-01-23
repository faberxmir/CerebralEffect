package cerebral.effect.forms;


import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import cerebral.effect.agents.RESTAgent;
import cerebral.effect.dto.MissionDto;
import cerebral.effect.json.JsonHandler;

import com.geirhilmersen.cerebraleffect.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class MissionGenerationFragment extends DialogFragment{
	private EditText edtCityName;
	private EditText edtMissionTitle;
	private EditText edtMissionDescription;
	private EditText edtCategory;
	
	public MissionGenerationFragment(){}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		super.onCreateDialog(savedInstanceState);
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View parentView = inflater.inflate(R.layout.form_create_mission, null);
		dialogBuilder.setView(parentView);
		

		edtCityName = (EditText) parentView.findViewById(R.id.edt_cityname);
		edtMissionTitle = (EditText) parentView.findViewById(R.id.edt_missiontitle);
		edtCategory = (EditText) parentView.findViewById(R.id.edt_category);
		edtMissionDescription = (EditText) parentView.findViewById(R.id.edt_missiondescription);
		
		dialogBuilder.setTitle(R.string.eventtitle)
								.setPositiveButton("testYES", new DialogInterface.OnClickListener(){

									@Override
									public void onClick(DialogInterface dialog, int which) {
										
										MissionDto mission = new MissionDto();
										mission.setCity_name(edtCityName.getText().toString());
										mission.setCategory(edtCategory.getText().toString());
										mission.setDescription(edtMissionDescription.getText().toString());
										mission.setTitle(edtMissionTitle.getText().toString());
										
										//create a jsonstring from the object
										String jsonString = JsonHandler.convertToJsonString(mission);
										new JsonPost().execute(jsonString);
									}
								});
		
		return dialogBuilder.create();
	}
	public void generateMissionCreation(){
		
	}
	
	private class JsonPost extends AsyncTask<String, String, String>{

		@Override
		protected void onPreExecute(){
			
		}
		
		@Override
		protected String doInBackground(String... strings) {
			String s = strings[0];
			try {
				HttpResponse response = RESTAgent.post("http://www.geirhilmersen.com:8080/api/missions.json", strings[0]);
			} catch (ClientProtocolException e) {
				Log.v("JsonPost.clientprotocolexception", e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				Log.v("JsonPost.IOException", e.getMessage());
				e.printStackTrace();
			}
			return strings[0];
		}
		
		@Override
		protected void onPostExecute(String string){
		}
	}
}
