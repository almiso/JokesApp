package org.almiso.jokesapp.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import org.almiso.jokesapp.R;
import org.almiso.jokesapp.model.FewJokesResponse;
import org.almiso.jokesapp.model.Joke;
import org.almiso.jokesapp.model.RandomJokeResponse;
import org.almiso.jokesapp.model.User;
import org.almiso.jokesapp.model.base.JokeError;
import org.almiso.jokesapp.model.base.JokeResponse;
import org.almiso.jokesapp.network.JokesApi;
import org.almiso.jokesapp.network.calback.JokeRequestListener;
import org.almiso.jokesapp.network.request.BaseRequest;
import org.almiso.jokesapp.util.Constants;
import org.almiso.jokesapp.util.Logger;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity {

    /* Views */

    private TextView tvRandomJoke;
    private TextView tvJokeByName;
    private TextView tvFewJokes;

    /* Controls */

    private Subscription usersSubscription;

    /* Common methods */

    @Override
    public void onDestroy() {
        super.onDestroy();
        usersSubscription.unsubscribe();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initViews();
    }

    /* Initialisation methods */

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initViews() {
        tvRandomJoke = (TextView) findViewById(R.id.tv_random_joke);
        updateViewVisibility(tvRandomJoke, false);
        tvJokeByName = (TextView) findViewById(R.id.tv_joke_by_name);
        updateViewVisibility(tvJokeByName, false);
        tvFewJokes = (TextView) findViewById(R.id.tv_few_jokes);
        updateViewVisibility(tvFewJokes, false);

        findViewById(R.id.button_random).setOnClickListener(v -> {
            getRandomJoke();
        });

        findViewById(R.id.button_few_jokes).setOnClickListener(v -> {
            getFewJokes();
        });

        createSearchByUser();
    }

    /* Network methods */

    private void getRandomJoke() {
        BaseRequest request = JokesApi.common().getRandomJoke();
        request.setProgressInterface(getProgressInterface());
        request.setRequestListener(new JokeRequestListener() {
            @Override
            public void onComplete(JokeResponse response) {
                RandomJokeResponse jokeResponse = (RandomJokeResponse) response;

                updateViewVisibility(tvRandomJoke, true);
                tvRandomJoke.setText(jokeResponse.getJoke().getValue());
            }

            @Override
            public void onError(JokeError error) {
                Logger.d(TAG, "onError. error: " + error);
                Toast.makeText(MainActivity.this, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.execute();
    }

    private void getJokeByName(User user) {
        String firstName = user.getFirstName().toString();
        String lastName = user.getLastName().toString();
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, "First ot last name are empty", Toast.LENGTH_SHORT).show();
            return;
        }

        BaseRequest request = JokesApi.common().getJokeForName(firstName, lastName);
        request.setRequestListener(new JokeRequestListener() {
            @Override
            public void onComplete(JokeResponse response) {
                RandomJokeResponse jokeResponse = (RandomJokeResponse) response;

                updateViewVisibility(tvJokeByName, true);
                tvJokeByName.setText(jokeResponse.getJoke().getValue());
            }

            @Override
            public void onError(JokeError error) {
                Logger.d(TAG, "onError. error: " + error);
                Toast.makeText(MainActivity.this, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.execute();
    }

    private void getFewJokes() {
        BaseRequest request = JokesApi.common().getFewJokes(new Random().nextInt(20));
        request.setProgressInterface(getProgressInterface());
        request.setRequestListener(new JokeRequestListener() {
            @Override
            public void onComplete(JokeResponse response) {
                FewJokesResponse jokeResponse = (FewJokesResponse) response;
                StringBuilder builder = new StringBuilder();

                for (Joke joke : jokeResponse.getJokes()) {
                    builder.append(joke.getValue());
                    builder.append(Constants.LINE_SEPARATOR);
                    builder.append(Constants.LINE_SEPARATOR);
                }

                String jokes = builder.toString();

                updateViewVisibility(tvFewJokes, true);
                tvFewJokes.setText(jokes);
            }

            @Override
            public void onError(JokeError error) {
                Logger.d(TAG, "onError. error: " + error);
                Toast.makeText(MainActivity.this, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.execute();
    }

    private void createSearchByUser() {

        EditText editFirstName = (EditText) findViewById(R.id.edit_first_name);
        EditText editLastName = (EditText) findViewById(R.id.edit_last_name);

        Observable<CharSequence> firstNameObservable = RxTextView.textChanges(editFirstName);
        Observable<CharSequence> lastNameObservable = RxTextView.textChanges(editLastName);

        usersSubscription = Observable.combineLatest(firstNameObservable, lastNameObservable,
                User::new)
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(user -> !TextUtils.isEmpty(user.getFirstName()) && !TextUtils.isEmpty(user.getLastName()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getJokeByName, throwable -> {
                    Logger.e(TAG, throwable);
                });
    }

    /* Util methods */

    private void updateViewVisibility(View view, boolean isVisible) {
        if (view == null) {
            return;
        }
        view.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }
}