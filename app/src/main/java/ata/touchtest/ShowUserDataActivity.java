package ata.touchtest;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ata.touchtest.UserDataGathering.UserData;
import ata.touchtest.UserDataGathering.UserDataSource;

public class ShowUserDataActivity extends ListActivity{
	private UserDataSource datasource;
	private String selectedRecord="0";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_data);
		
		datasource = new UserDataSource(this);
	    datasource.open();

	    ListView listView1 = (ListView) findViewById(android.R.layout.simple_list_item_1);
	    
	    List<UserData> values = datasource.getAllUserData();

	    // Use the SimpleCursorAdapter to show the
	    // elements in a ListView
	    ArrayAdapter<UserData> adapter = new ArrayAdapter<UserData>(this,
	        android.R.layout.simple_list_item_1, values);
	    setListAdapter(adapter);
	    	 	    
	   /* listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

	        @Override
	        public void onItemClick(AdapterView<?> parent, final View view,
	            int position, long id) {
	        	String item = ((TextView)view).getText().toString();
	               
                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
	        }

	      });*/	    
	}
	
	@Override
	  public void onListItemClick(ListView l, View v, int position, long id) {
	    // Do something with the data
		String item = ((TextView)v).getText().toString();
		String[] parts = item.split("-");
        Toast.makeText(getBaseContext(), parts[0], Toast.LENGTH_SHORT).show();
        selectedRecord=parts[0].trim();
	  }
	
	@Override
	  protected void onResume() {
	    datasource.open();
	    super.onResume();
	  }

	  @Override
	  protected void onPause() {
	    datasource.close();
	    super.onPause();
	  }
	  
	  public boolean onCreateOptionsMenu(Menu menu) {     
		     super.onCreateOptionsMenu(menu);   
		        MenuInflater inflater=getMenuInflater();
		        inflater.inflate(R.menu.showmenu, menu);
		        return true;
       }
	  
	  @Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	      // Handle item selection
	      switch (item.getItemId()) {
	          case R.id.delete:
	              datasource.deleteAllUserdata();
	              List<UserData> values = datasource.getAllUserData();
	      	      ArrayAdapter<UserData> adapter = new ArrayAdapter<UserData>(this,
	      	        android.R.layout.simple_list_item_1, values);
	      	      setListAdapter(adapter);
	      	      
	              return true;
	          case R.id.deleteItem:
	        	  	 datasource.deleteUser(selectedRecord);
	        	  	List<UserData> val = datasource.getAllUserData();
		      	      ArrayAdapter<UserData> adapters = new ArrayAdapter<UserData>(this,
		      	        android.R.layout.simple_list_item_1, val);
		      	      setListAdapter(adapters);
	        	  return true;
	          default:
	              return super.onOptionsItemSelected(item);
	      }
	  }
	  
}
