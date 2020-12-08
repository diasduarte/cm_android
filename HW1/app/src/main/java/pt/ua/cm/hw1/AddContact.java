package pt.ua.cm.hw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class AddContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.editTextPhone);
        textView.setText(message);
    }

    public void pressSaveButton(View view) {
        TextView phone = findViewById(R.id.editTextPhone);
        TextView name = findViewById(R.id.editTextTextPersonName);
        if(phone.getText().length() != 0 && name.getText().length() != 0) {
            // Creates a new Intent to insert a contact
            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            // Sets the MIME type to match the Contacts Provider
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            intent.putExtra(ContactsContract.Intents.Insert.NAME, name.getText().toString())
                    // Inserts a phone number
                    .putExtra(ContactsContract.Intents.Insert.PHONE, phone.getText())
                    .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
            startActivity(intent);
            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
    }
}