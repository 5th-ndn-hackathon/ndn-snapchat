package com.snapchat.fan.snapchat.Chat;

import android.os.Bundle;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import net.named_data.jndn.Face;
import net.named_data.jndn.encoding.EncodingException;
import net.named_data.jndn.security.KeyChain;
import net.named_data.jndn.security.SecurityException;
import net.named_data.jndn.security.identity.IdentityManager;
import net.named_data.jndn.Name;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.snapchat.fan.snapchat.Chat";

    Face face;
    IdentityManager identifymanager;
    public void setup_security() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                KeyChain keyChain = new net.named_data.jndn.security.KeyChain(identifymanager);
                Name certificateName;
                Name testIdName = new Name("/test/chat");
                try {
                    certificateName = keyChain.createIdentityAndCertificate(testIdName);
                    keyChain.getIdentityManager().setDefaultIdentity(testIdName);

                } catch (SecurityException e) {
                    certificateName = new Name("error");
                }
                face.setCommandSigningInfo(keyChain, certificateName);
                try {
                    face.processEvents();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (EncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.run();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void switchActivity(View view) {
        Intent intent = new Intent(this, FileSelectActivity.class);
        startActivity(intent);

    }
}
