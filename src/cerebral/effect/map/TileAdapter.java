package cerebral.effect.map;

import java.util.List;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class TileAdapter extends BaseAdapter{
	private Context ctxContext;
	private List<Integer> lstTileList;
	
	public TileAdapter(Context context, List<Integer> list){
		ctxContext = context;
		lstTileList = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lstTileList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup parent) {
		ImageView imageview;
		
		
		 
		if(null == convertview){
			imageview = new ImageView(ctxContext);
			imageview.setLayoutParams(new GridView.LayoutParams(85,85));
			imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
			imageview.setPadding(0, 0, 0, 0);
			
		}else{
			imageview = (ImageView) convertview;
			imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
		}
		if(!lstTileList.isEmpty()){
			imageview.setImageResource(lstTileList.get(position));
		}
		return imageview;
	}

	public View setViewSize(){
		WindowManager manager = (WindowManager) ctxContext.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(metrics);
		
		int height = metrics.heightPixels;
		
		return null;
	}
}
