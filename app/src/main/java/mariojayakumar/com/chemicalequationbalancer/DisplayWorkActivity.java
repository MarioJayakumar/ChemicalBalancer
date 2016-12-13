package mariojayakumar.com.chemicalequationbalancer;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DisplayWorkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_work);

        TextView textView = (TextView) findViewById(R.id.WorkDisplay);
        textView.setMovementMethod(new ScrollingMovementMethod());


        File file = new File(this.getFilesDir(),"OutputFile.txt");
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String read = input.readLine();

            while(read!=null) {

                textView.append(read + "\n");

                read = input.readLine();
            }
        }
        catch(IOException io)
        {
            Toast.makeText(this,"Error Reading Log File", Toast.LENGTH_SHORT);

        }
    }
}
