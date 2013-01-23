package cerebral.effect.map;

import java.util.List;

import com.geirhilmersen.cerebraleffect.R;

import android.content.Context;
import android.text.Layout;
import android.view.View;
import android.widget.GridView;

public class MapFactory {
	public enum Maptype {
		CITYMAP,
		COUNTYMAP
	}
	
	public final int INITIALMAPSIZE = 9;
	
	public MapFactory(){}
	
	/**
	 * @param view must be a GridView
	 * @param citytype static variables of the MapFactory class. Determines what kind of map is to be made.
	 * @return returns a city map of the desired type.
	 */
	public CerebralMap createMap(View view, Maptype maptype, Context context){
		CerebralMap map = null;
		//TODO: get appropriate data from DB;
		switch(maptype){
			case CITYMAP: 	map = (CityMap) view;
							map.setAdapter(new TileAdapter(context, null));
							map.setColumns(INITIALMAPSIZE);
							map.setRows(INITIALMAPSIZE);
							break;
			case COUNTYMAP:
				//TODO: Implement countymap
				break;
			default:
				break;
		}
		return map;
	}
}
