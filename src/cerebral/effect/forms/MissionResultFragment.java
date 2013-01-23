package cerebral.effect.forms;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cerebral.effect.dto.RewardDto;
import cerebral.effect.exceptions.NoParentViewException;

import com.geirhilmersen.cerebraleffect.R;

public class MissionResultFragment extends DialogFragment{
	private TextView txtMissionTitle;
	private TextView txtMissionResult;
	private TextView txtRewardText;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState){
		super.onCreateDialog(savedInstanceState);
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View parentView = inflater.inflate(R.layout.poster_mission_result, null);
		dialogBuilder.setView(parentView);
		
		txtMissionTitle = (TextView) parentView.findViewById(R.id.txtMissiontitle);
		txtMissionResult = (TextView) parentView.findViewById(R.id.txtMissionresult);
		txtRewardText = (TextView) parentView.findViewById(R.id.txtRewardtext);

		//new GetRewardTask().execute(txtMissionTitle, txtMissionResult, txtRewardText);
		dialogBuilder.setTitle(R.string.app_name)//SETTITLE
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							
							RewardDto reward = new RewardDto();
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
							}
						});
		return null;
	}
	
	private class GetRewardTask extends AsyncTask<TextView, String, String> {
		
		View parentView;
		/**
		 * The constructor is necessary to instantiate the parent view for the execute
		 * operation. This ensures that the mechanisms revolving the TextViews are 
		 * handled correctly.
		 * @param parentView - the view that owns the TextViews passed ass arguments in
		 * the execute method.
		 * 
		 * @exception - throws NoParentViewException if the parentview member variable is null;
		 */
		public GetRewardTask(View parentView){
			
		}
		@Override
		protected void onPreExecute(){
			
		}
		
		@Override
		protected String doInBackground(TextView... textViewArray)  {
			

			return null;
		}
		
		@Override
		protected void onPostExecute(String string){
			
		}
		
	}
}
