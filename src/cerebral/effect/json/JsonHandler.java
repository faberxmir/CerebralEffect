package cerebral.effect.json;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;

import android.util.Log;

public class JsonHandler {
	
	public static String convertInputStream(InputStream inputStream){
		String streamString = null;
		try{
			
			InputStreamReader iStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferReader = new BufferedReader(iStreamReader);
			StringBuilder stringbuilder = new StringBuilder();
			
			while((streamString = bufferReader.readLine()) != null){
				stringbuilder.append(streamString);
			}

			streamString = stringbuilder.toString();
		}catch(IOException ioex){
			Log.v("JsonHandler.convertInputStream", ioex.getMessage());
		}
		return streamString;
	}
	
	public static String convertToJsonString(Object object){
		Gson gson = new Gson();
		String returnString = gson.toJson(object);
		return returnString;
	}
}
