package com.example.santiagolopezgarcia.talleres.data;

import java.util.List;

/**
 * Created by santiagolopezgarcia on 6/10/15.
 */
public interface AdministradorBaseDatosInterface {

    List<String> GetQuerysCreate();

    List<String> GetQuerysUpgrade();

}