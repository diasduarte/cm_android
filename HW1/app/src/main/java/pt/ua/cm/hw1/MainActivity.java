package pt.ua.cm.hw1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the 0,1,2,3,4,5,6,7,8,9,* or # button */
    public void pressButton(View view) {
        CharSequence id = view.getContentDescription();
        TextView textView = findViewById(R.id.phoneNumber);
        textView.append(id);
    }

    /** Called when the user taps the backspace button */
    public void pressButtonBack(View view) {
        TextView textView = findViewById(R.id.phoneNumber);
        CharSequence text = textView.getText();
        textView.setText(text.subSequence(0,text.length()-1));
    }

    /** Called when the user taps the call button */
    public void pressButtonCall(View view) {
        TextView textView = findViewById(R.id.phoneNumber);
        if(textView.getText().equals("")) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+textView.getText()));
        startActivity(intent);
        textView.setText("");
    }
}