package com.palprotech.ensyfi.customview;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.palprotech.ensyfi.R;

public class AttachFileTest extends Activity {

    TextView textFile;

    private static final int PICKFILE_RESULT_CODE = 1;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
////        setContentView(R.layout.main);
////
////        Button buttonPick = (Button)findViewById(R.id.buttonpick);
////        textFile = (TextView)findViewById(R.id.textfile);
//
//        buttonPick.setOnClickListener(new Button.OnClickListener(){

//            @Override
//            public void onClick(View arg0) {
//                // TODO Auto-generated method stub
//
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("file/*");
//                startActivityForResult(intent,PICKFILE_RESULT_CODE);
//
//            }});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch(requestCode){
            case PICKFILE_RESULT_CODE:
                if(resultCode==RESULT_OK){
                    String FilePath = data.getData().getPath();
                    textFile.setText(FilePath);
                }
                break;

        }
    }
}