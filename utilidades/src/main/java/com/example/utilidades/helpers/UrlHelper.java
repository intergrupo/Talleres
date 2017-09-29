package com.example.utilidades.helpers;

import android.webkit.URLUtil;

/**
 * Created by santiagolopezgarcia on 9/25/17.
 */

public class UrlHelper {

    public boolean validarUrlHttpYHttps(String url){
        return URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url);
    }
}
