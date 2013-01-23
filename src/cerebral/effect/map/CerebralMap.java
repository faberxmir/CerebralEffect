package cerebral.effect.map;

import android.content.Context;
import android.widget.GridView;

public class CerebralMap extends GridView{

	public CerebralMap(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private int intColumns;
	private int intRows;
	
	public void setColumns(int columns){
		intColumns = columns;
	}

	public void setRows(int rows){
		intRows = rows;
	}

	public int getWidthCount(){
		return intColumns;
	}

	public int getHeightCount(){
		return intRows;
	}

	public int getTileCount(){
		return intColumns*intRows;
	}
}