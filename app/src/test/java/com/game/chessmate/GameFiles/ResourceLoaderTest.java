package com.game.chessmate.GameFiles;

import android.content.Context;
import android.content.res.Resources;

import com.game.chessmate.R;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

public class ResourceLoaderTest {

    private int screenWidth;

    private ResourceLoader loader;

    @Mock
    private static Resources resources;

    @Mock
    private static Context context;


    @Before
    public void init() {
        resources = Mockito.mock(Resources.class);
        context = Mockito.mock(Context.class);
        screenWidth = 1200;
        loader = new ResourceLoader(resources, screenWidth);
    }

    @Test
    public void ifNewResourceLoader_ThenResourceLoaderExists() {
        assertNotNull(loader);
    }

    @Test
    public void ifLoadBitmapNotFound_ThenThrow() {
        assertThrows(RuntimeException.class, () -> loader.loadBitmap(R.drawable.king_player1));
    }
}
