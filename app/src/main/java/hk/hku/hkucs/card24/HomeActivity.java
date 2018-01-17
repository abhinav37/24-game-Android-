package hk.hku.hkucs.card24;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private static final int RESULT = 1;
    private Button start;
    private EditText number;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context = this;
        start = (Button) findViewById(R.id.start);
        number = (EditText) findViewById(R.id.number);
        number.setText("24");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("number", Integer.parseInt(number.getText().toString()));
                startActivityForResult(intent,RESULT);
            }
        });
    }
}