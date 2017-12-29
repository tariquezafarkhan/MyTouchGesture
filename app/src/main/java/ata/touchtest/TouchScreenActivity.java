package ata.touchtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class TouchScreenActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(new SingleTouchEventView(this, null));
	  }
	  
	@Override
	  public boolean onCreateOptionsMenu(Menu menu) {
	    getMenuInflater().inflate(R.layout.screentouch, menu);
	    return true;
	  }
}
