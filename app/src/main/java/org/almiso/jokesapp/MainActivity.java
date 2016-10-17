package org.almiso.jokesapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.almiso.jokesapp.model.FewJokesResponse;
import org.almiso.jokesapp.model.Joke;
import org.almiso.jokesapp.model.RandomJokeResponse;
import org.almiso.jokesapp.model.base.JokeError;
import org.almiso.jokesapp.model.base.JokeObject;
import org.almiso.jokesapp.network.request.BaseRequest;
import org.almiso.jokesapp.network.JokesApi;
import org.almiso.jokesapp.network.calback.JokeRequestListener;
import org.almiso.jokesapp.network.calback.ProgressInterface;
import org.almiso.jokesapp.util.Logger;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /* Constants */

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String lineSeparator = System.getProperty("line.separator");

    /* Views */

    private TextView tvRandomJoke;
    private TextView tvJokeByName;
    private TextView tvFewJokes;
    private EditText editFirstName;
    private EditText editLastName;

    /* Controls */

    private ProgressDialog progressDialog;

    /* Common methods */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initViews();
        initData();
    }

    /* Initialisation methods */

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initViews() {
        tvRandomJoke = (TextView) findViewById(R.id.tv_random_joke);
        tvJokeByName = (TextView) findViewById(R.id.tv_joke_by_name);
        tvFewJokes = (TextView) findViewById(R.id.tv_few_jokes);

        editFirstName = (EditText) findViewById(R.id.edit_first_name);
        editLastName = (EditText) findViewById(R.id.edit_last_name);

        findViewById(R.id.button_random).setOnClickListener(v -> {
            getRandomJoke();
        });

        findViewById(R.id.button_joke_by_name).setOnClickListener(v -> {
            getJokeByName();
        });

        findViewById(R.id.button_few_jokes).setOnClickListener(v -> {
            getFewJokes();
        });
    }

    private void initData() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.app_name);
        progressDialog.setMessage(getString(R.string.app_name));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
    }

    /* private methods */

    private void getRandomJoke() {
        BaseRequest request = JokesApi.common().getRandomJoke();
        request.setProgressInterface(getProgressInterface());
        request.setRequestListener(new JokeRequestListener() {
            @Override
            public void onComplete(JokeObject response) {
                RandomJokeResponse jokeResponse = (RandomJokeResponse) response;
                tvRandomJoke.setText(jokeResponse.getJoke().getValue());
            }

            @Override
            public void onError(JokeError error) {
                Logger.d(TAG, "onError. error: " + error);
            }
        });
        request.execute();
    }

    private void getJokeByName() {
        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();

        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, "First ot last name are empty", Toast.LENGTH_SHORT).show();
            return;
        }

        BaseRequest request = JokesApi.common().getJokeForName(firstName, lastName);
        request.setProgressInterface(getProgressInterface());
        request.setRequestListener(new JokeRequestListener() {
            @Override
            public void onComplete(JokeObject response) {
                RandomJokeResponse jokeResponse = (RandomJokeResponse) response;
                tvJokeByName.setText(jokeResponse.getJoke().getValue());
            }

            @Override
            public void onError(JokeError error) {
                Logger.d(TAG, "onError. error: " + error);
            }
        });
        request.execute();
    }

    private void getFewJokes() {
        BaseRequest request = JokesApi.common().getFewJokes(new Random().nextInt(20));
        request.setProgressInterface(getProgressInterface());
        request.setRequestListener(new JokeRequestListener() {
            @Override
            public void onComplete(JokeObject response) {
                FewJokesResponse jokeResponse = (FewJokesResponse) response;
                StringBuilder builder = new StringBuilder();

                for (Joke joke : jokeResponse.getJokes()) {
                    builder.append(joke.getValue());
                    builder.append(lineSeparator);
                    builder.append(lineSeparator);
                }

                String jokes = builder.toString();
                tvFewJokes.setText(jokes);
            }

            @Override
            public void onError(JokeError error) {
                Logger.d(TAG, "onError. error: " + error);
            }
        });
        request.execute();
    }

    private ProgressInterface getProgressInterface() {
        return new ProgressInterface() {
            @Override
            public void showProgress() {
                if (progressDialog != null && !progressDialog.isShowing()) {
                    progressDialog.show();
                }
            }

            @Override
            public void hideProgress() {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        };
    }
}