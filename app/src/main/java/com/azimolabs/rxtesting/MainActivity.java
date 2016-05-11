package com.azimolabs.rxtesting;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends Activity {

    private RxManager rxManager = new RxManager();

    private TextView valuesFieldOne;
    private TextView valuesFieldTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valuesFieldOne = (TextView) findViewById(R.id.valuesFieldOne);
        valuesFieldTwo = (TextView) findViewById(R.id.valuesFieldTwo);

        fillFieldSimple();
        fillFieldDecorated();
    }

    private void fillFieldSimple() {
        rxManager.getTransformedNumbers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(prepareFieldsFiller(valuesFieldOne));
    }

    private void fillFieldDecorated() {
        rxManager.getTransformedNumberMoreFlatMapped()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(prepareFieldsFiller(valuesFieldTwo));
    }

    private Observer<String> prepareFieldsFiller(final TextView valuesField) {
        return new Observer<String>() {

            @Override
            public void onCompleted() {
                Log.d("RX_TESTS", "Fields filler completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("RX_TESTS", "Error when filling fields: " + e);
            }

            @Override
            public void onNext(String s) {
                StringBuilder sb = new StringBuilder(valuesField.getText());
                sb.append(s);
                sb.append('\n');
                valuesField.setText(sb.toString());
            }
        };
    }
}