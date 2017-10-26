package com.example.ramak.myapplication;

import android.os.Bundle;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ramak on 10/19/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest {
    @Mock
    private MainActivity mainAct;

    @Before
    public void setUp() throws Exception {
        mainAct = new MainActivity();

    }
    @Test
    public void ShouldShowError() throws Exception{
        when(mainAct.getUserNmae()).thenReturn("hello");
        mainAct.valUser();
        verify(mainAct).showError("hello");
    }
}