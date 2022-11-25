package com.aios.bananesexport.service.helper;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CommandeHelperTest {

    @Test
    void testMinimumLivraisonDate(){
        assertTrue(LocalDate.now()
                .isBefore(CommandeHelper.minimumLivraisonDate()));
        assertTrue(LocalDate.now().plusDays(6)
                .isBefore(CommandeHelper.minimumLivraisonDate()));
    }
}