package com.webcheckers.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("UI-tier")
class AIHandlerTest {

    private AIHandler CuT;

    @BeforeEach
    public void testSetup(){
    }

    @Test
    public void ctor () {
        assertNotNull(CuT);
    }
}