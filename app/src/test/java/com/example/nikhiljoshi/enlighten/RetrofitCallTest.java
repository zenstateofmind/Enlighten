package com.example.nikhiljoshi.enlighten;

import com.example.nikhiljoshi.enlighten.Network.GSON.BookList;
import com.example.nikhiljoshi.enlighten.Network.NytApi;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.util.Date;

import retrofit2.Callback;

/**
 * Created by nikhiljoshi on 4/26/16.
 */

@Config(constants = BuildConfig.class, sdk = 21,
        manifest = "app/src/main/AndroidManifest.xml")
@RunWith(RobolectricGradleTestRunner.class)
public class RetrofitCallTest {

    @Mock
    private NytApi nytApi;

    @Captor
    private ArgumentCaptor<Callback<BookList>> callbackArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    public void testPositiveCase() throws IOException {



    }

}
