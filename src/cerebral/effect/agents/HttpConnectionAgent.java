package cerebral.effect.agents;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.methods.HttpPost;

public class HttpConnectionAgent {
	private HttpURLConnection connection;
	private URL url;
	
	/**
	 * This class handles general HttpConnections but closing a connection requires that the close()-
	 * method is called
	 */
	public HttpConnectionAgent(String urlString){
		try{
			url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			
		}catch(MalformedURLException mue){
			Log.v("(mue)URL is malformed: " + urlString + ": ", mue.getMessage());
		}catch(IOException ioex){
			Log.v("(ioex)Trying to establish connection to " + urlString + ": ", ioex.getMessage());
		}
	}
	
	/**
	 * @return creates andreturns a bufferedinputstream by calling the 
	 * getInputStream method of this object.
	 */
	public BufferedInputStream getBufferedInputStream(){
		return new BufferedInputStream(getInputStream());
	}
	
	/**
	 * This method returns an inputstream.
	 * @return
	 */
	public InputStream getInputStream(){

		InputStream inputStream = null;

		if(connection != null){
			try{
				inputStream = connection.getInputStream();
			
				
			}catch(IOException ioex){
				Log.v("(ioex)Reading inputStream failed: ", ioex.getMessage());
			}
		}
		
		return inputStream;
	}
	
	/**
	 * This method closes this objects HttpURLConnection. 
	 * 
	 * @returnIf there was no connection, or if connection.close()
	 * was called successfully it returns true, otherwise
	 * it returns false
	 */
	public boolean closeConnection(){
		boolean connectionclosed = false;
		
		if(connection != null){
			connection.disconnect();
			connectionclosed = true;
			
		}else if( connection == null){
			connectionclosed = true;
		}
		return connectionclosed;
	}
}
