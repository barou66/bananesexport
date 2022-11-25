package com.aios.bananesexport.service.helper;


import java.time.LocalDate;

public final class CommandeHelper {
    private CommandeHelper(){
    }

    public static LocalDate minimumLivraisonDate(){
       return LocalDate.now()
                .plusDays(7);
    }

}
