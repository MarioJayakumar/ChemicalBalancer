package mariojayakumar.com.chemicalequationbalancer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button) findViewById(R.id.showWork);
        b.setEnabled(false);
    }

    public void calculateEquation(View view)
    {
        File file = new File(this.getFilesDir(),"OutputFile.txt");

        EditText editText = (EditText) findViewById(R.id.enter_equation);
        String input = editText.getText().toString();
        ChemicalBalancer balance = new ChemicalBalancer(input,file);


        TextView textView = (TextView)findViewById(R.id.output_text);
        textView.setTextSize(40);
        textView.setText(balance.getResult());

        Button b = (Button) findViewById(R.id.showWork);
        b.setEnabled(true);

        Toast.makeText(MainActivity.this,"Equation Balanced!",Toast.LENGTH_LONG);
    }

    public void clearText(View view)
    {
        EditText editText = (EditText) findViewById(R.id.enter_equation);
        editText.setText("");
        Button b = (Button) findViewById(R.id.showWork);
        b.setEnabled(false);
    }

    public void showWork(View view)
    {
        Intent intent = new Intent(this,DisplayWorkActivity.class);
        startActivity(intent);

    }
}
