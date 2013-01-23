package cerebral.effect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import cerebral.effect.agents.DbAgent;
import cerebral.effect.agents.RESTAgent;
import cerebral.effect.dto.AccountDto;
import cerebral.effect.forms.CreateAccountFragment;
import cerebral.effect.forms.MissionGenerationFragment;
import cerebral.effect.json.JsonHandler;
import cerebral.effect.map.CityMap;
import cerebral.effect.map.MapFactory;
import cerebral.effect.map.MapFactory.Maptype;
import cerebral.effect.map.TileAdapter;

import com.geirhilmersen.cerebraleffect.R;

public class MainActivity extends FragmentActivity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        List<AccountDto> accountlist = new ArrayList<AccountDto>();
        DbAgent dbAgent = new DbAgent(this);
        accountlist = dbAgent.getAccounts();
        
        setContentView(R.layout.activity_main);
    	
        //Login functionality. Create, select or confirm account.
        if(null == accountlist){
        	CreateAccountFragment createAccount = new CreateAccountFragment();
        	createAccount.show(getSupportFragmentManager(), "test");
        }else if(0 < accountlist.size()){
        	//Login and select user.
        	
        	//TESTCODE:
        	initiateMap();
        }
        
        
        
       
    }
    
    private void initiateMap(){
    	CityMap citymap;
    	
    	setContentView(R.layout.citymap);
    	MapFactory factory = new MapFactory();
    	citymap = (CityMap) factory.createMap(findViewById(R.id.grd_citymap), Maptype.CITYMAP, this);
    	
        List<Integer> tileList = new ArrayList<Integer>();
		for(int i = 0; i < 9*9; ++i){
        	tileList.add(R.drawable.testtile72);
        }
		
		if(citymap != null){
			citymap.setAdapter(new TileAdapter(this, tileList));
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
