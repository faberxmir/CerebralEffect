package cerebral.effect.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import android.os.AsyncTask;

public class Sockettest {
	
	public void sockettester(){

		new SocketTask().execute("test");
		
	}
	
	
	public class SocketTask extends AsyncTask <String, String, Void>{

		@Override
		protected Void doInBackground(String... params) {
			try {
				Socket suckit = new Socket(); //"Geir Hilmersen", 8000
				SocketAddress adress = new InetSocketAddress("Geir Hilmersen", 8000);
				suckit.connect(adress);
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}
}
