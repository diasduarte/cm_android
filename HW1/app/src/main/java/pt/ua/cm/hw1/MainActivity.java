package pt.ua.cm.hw1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "pt.ua.cm.hw1.MESSAGE";
    public String phoneN = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button view = findViewById(R.id.button_1);
        view.setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                TextView textView = findViewById(R.id.phoneNumber);
                if(textView.getText().length() != 0) {
                    intent.putExtra(EXTRA_MESSAGE, textView.getText().toString());
                    startActivity(intent);
                }
                return true;
            }
        });
        Button view2 = findViewById(R.id.button_2);
        view2.setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TextView textView = findViewById(R.id.phoneNumber);
                if(textView.getText().length() != 0) {
                    phoneN = textView.getText().toString();
                }
                textView.setText("");
                return true;
            }
        });

        Button view3 = findViewById(R.id.button_3);
        view3.setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TextView textView = findViewById(R.id.phoneNumber);
                if(phoneN.equals("") == false) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneN));
                    startActivity(intent);
                    textView.setText("");
                }
                return true;
            }
        });
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
        if (text.length() != 0) {
            textView.setText(text.subSequence(0, text.length() - 1));
        }
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