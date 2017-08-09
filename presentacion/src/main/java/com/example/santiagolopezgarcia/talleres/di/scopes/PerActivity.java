package com.example.santiagolopezgarcia.talleres.di.scopes;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by santiagolopezgarcia on 8/8/17.
 */

@Scope
@Retention(RUNTIME)
public @interface PerActivity {
}