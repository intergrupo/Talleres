package com.example.santiagolopezgarcia.talleres.data;

import android.content.ContentValues;

/**
 * Created by juanfelipegomezvelez on 10/06/16.
 */
public class UpdateContentValues {

    private ContentValues contentValues;

    private String clause;

    private String[] where;

    public UpdateContentValues(ContentValues contentValues, String clause, String[] where) {
        this.contentValues = contentValues;
        this.clause = clause;
        this.where = where;
    }

    public UpdateContentValues() {
        this.clause = "";
        this.where = new String[0];
        this.contentValues = new ContentValues();
    }

    public ContentValues getContentValues() {
        return contentValues;
    }

    public void setContentValues(ContentValues contentValues) {
        this.contentValues = contentValues;
    }

    public String getClause() {
        return clause;
    }

    public void setClause(String clause) {
        this.clause = clause;
    }

    public String[] getWhere() {
        return where;
    }

    public void setWhere(String[] where) {
        this.where = where;
    }
}
