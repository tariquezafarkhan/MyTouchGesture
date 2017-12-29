package ata.touchtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class ExportDataActivity extends Activity implements OnClickListener {

	private DatabaseHelper dbhelper;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_data);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button1:
			//deleteDB();
			break;
		case R.id.button2:
			exportDB();
			break;
		case R.id.button3:
			//createDB();
			break;
		}
	}
	
	private void exportDB(){
		File sd = Environment.getExternalStorageDirectory();
	      	File data = Environment.getDataDirectory();
	       FileChannel source=null;
	       FileChannel destination=null;
	       String currentDBPath = "/data/"+ "ir.ata.touchtest" +"/databases/"+dbhelper.DATABASE_NAME;
	       String backupDBPath = dbhelper.DATABASE_NAME;
	       File currentDB = new File(data, currentDBPath);
	       File backupDB = new File(sd, backupDBPath);
	       try {
	            source = new FileInputStream(currentDB).getChannel();
	            destination = new FileOutputStream(backupDB).getChannel();
	            destination.transferFrom(source, 0, source.size());
	            source.close();
	            destination.close();
	            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
	        } catch(IOException e) {
	        	e.printStackTrace();
	        }
	}
}
