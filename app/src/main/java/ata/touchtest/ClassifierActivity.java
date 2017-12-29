package ata.touchtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import ata.libSVM.svm;
import ata.libSVM.svm_model;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class ClassifierActivity extends Activity implements OnClickListener {

	private DatabaseHelper dbhelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classify_data);
		findViewById(R.id.button2).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			// deleteDB();
			break;
		case R.id.button2:
			classifyDB();
			break;
		case R.id.button3:
			// createDB();
			break;
		}
	}

	private void classifyDB() {
		try {
			File sd = Environment.getExternalStorageDirectory();
			String backupDBPath = "/data.arff";
			File backupDB = new File(sd + backupDBPath);
			BufferedReader reader = new BufferedReader(new FileReader(backupDB));
			Instances data = new Instances(reader);
			reader.close();
			// setting class attribute
			data.setClassIndex(data.numAttributes() - 1);
			svm_model svmModel = svm.svm_load_model(backupDBPath);
			svm.svm_get_nr_class(svmModel);
			svm.svm_save_model("Data.arff", svmModel);

			Toast.makeText(this, "has been read", Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String CSV2Arff(String input, String output) throws IOException {
		try {
			// load CSV
			CSVLoader loader = new CSVLoader();
			loader.setSource(new File(input));
			Instances data = loader.getDataSet();

			// save ARFF
			ArffSaver saver = new ArffSaver();
			saver.setInstances(data);
			saver.setFile(new File(output));
			saver.setDestination(new File(output));
			saver.writeBatch();
			return output;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;

	}
}
