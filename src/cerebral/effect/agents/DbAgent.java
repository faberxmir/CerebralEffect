package cerebral.effect.agents;

import java.util.ArrayList;
import java.util.List;

import cerebral.effect.dto.AccountDto;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Geir Hilmersen
 * The account information stored locally is only a shallow copy of the servers' account information.
 * The device itself only contain enough information to log on. */
public class DbAgent {
	
	//Table Name(s)
	private static final String TABLE_PLAYERPROFILE = "playerprofiles";
	
	//Column Names
	private final static String COLUMN_ID_AUTO = "_id";
	private final static String COLUMN_CLANNAME = "clanname";
	private final static String COLUMN_PASSWORD = "password";
	
	private SQLiteDatabase db;
	private DbHelper dbHelper;
	
	//Create String
	private final static String CREATETABLE = "create table " + TABLE_PLAYERPROFILE 
												+ "("+ COLUMN_ID_AUTO + " integer primary key autoincrement, "
												+ COLUMN_CLANNAME + " text not null, "
												+ COLUMN_PASSWORD + " text not null, "
												+ " UNIQUE(" + COLUMN_CLANNAME + ") on conflict fail);";
	
	public DbAgent(Context context){
		if(null == dbHelper){
			dbHelper = new DbHelper(context);
		}
	}
	
	public DbAgent open(boolean readOnly){
		if(null == db || !db.isOpen()){
			if(readOnly){
				db = dbHelper.getReadableDatabase();
				
			}else{
				db = dbHelper.getWritableDatabase();
			}
		}
		return this;
	}
	
	public void close(){
		if(null != db & db.isOpen()){
			db.close();
		}
	}
	
	/**
	 * @return null if no accounts where found, else it 
	 * returns a List<AcountDto> with the accounts found
	 */
	public List<AccountDto> getAccounts(){
		List<AccountDto> returnlist = null;
		AccountDto account = new AccountDto();
		
		open(true);
		Cursor cursor = null;
		
		//Get the content from the columns clanname and password
		cursor = db.query(TABLE_PLAYERPROFILE, new String[]{COLUMN_CLANNAME, COLUMN_PASSWORD}, null, null, null, null, COLUMN_ID_AUTO);
		
		if(cursor.moveToFirst()){
			returnlist = new ArrayList<AccountDto>();
			
			do{
				account.setClan_name(cursor.getString(cursor.getColumnIndex(COLUMN_CLANNAME)));
				account.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
				returnlist.add(account);
				
			}while(cursor.moveToNext());
		}
		close();
		
		return returnlist;
	}
	
	/**
	 * This method uses the arguments given and inserts them into a row in the DB, thus
	 * creating a new user that will be used for autologin on future launches of 
	 * Cerebral Effect.
	 * @param clanname The players selected clan name
	 * @param password The players selected password.
	 * @return true if a user was created. False if this user for some reason could not
	 * be inserted into the DB.
	 */
	public boolean createAccount(String clanname, String password){
		
		open(false);
		
		ContentValues newUser = new ContentValues();
		newUser.put(COLUMN_CLANNAME, clanname);
		newUser.put(COLUMN_PASSWORD, password);
		
		boolean returnValue = db.insert(TABLE_PLAYERPROFILE, null, newUser) > 0;
		
		close();
		
		return returnValue;
	}
	
	/**
	 * This method uses the arguments given and inserts them into a row in the DB, thus
	 * creating a new user that will be used for autologin on future launches of 
	 * Cerebral Effect
	 * @param account an AccountDto object
	 * @return returns true if the a user was created. False if this user fo some reason 
	 * could not be inserted into the DB.
	 */
	public boolean createAccount(AccountDto account){
		return createAccount(account.getClan_name(), account.getPassword());
	}
	
	private static class DbHelper extends SQLiteOpenHelper{
		private static String DATABASE_NAME = "cerebral_effect";
		
		public DbHelper(Context context){
			super(context, DATABASE_NAME, null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATETABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
		}
	}
									
}
