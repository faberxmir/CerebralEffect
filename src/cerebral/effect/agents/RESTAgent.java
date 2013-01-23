package cerebral.effect.agents;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class RESTAgent {
	private final static String JSONCONTENT = "application/json";
	private final static String CONTENTTYPE = "Content-Type";
	
	//---------------------------------------------------------------------------------------------------
	//----POST-------------------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------------------
	public static HttpResponse post(String URL, String json) throws ClientProtocolException, IOException{
		StringEntity jsonEntity = null;
		
		try {
			jsonEntity = new StringEntity(json);
			jsonEntity.setContentType(JSONCONTENT);
			
		} catch (UnsupportedEncodingException e) {
			Log.v("JsonREST.httpPost", e.getMessage());
			e.printStackTrace();
		}
		HttpPost postmaster = new HttpPost(URL);
		postmaster.setEntity(jsonEntity);
		postmaster.addHeader(CONTENTTYPE, JSONCONTENT);
		
		
		HttpClient client = new DefaultHttpClient();
		return client.execute(postmaster);
	}
	
	//---------------------------------------------------------------------------------------
	//----GET--------------------------------------------------------------------------------
	//---------------------------------------------------------------------------------------
	public static HttpResponse get(String URL) throws ClientProtocolException, IOException{
		HttpGet getmaster = new HttpGet(URL);
		HttpClient client = new DefaultHttpClient();
		
		return client.execute(getmaster);
	}
}