package com.smartapp.api;

import ratpack.handling.Handler;
import ratpack.handling.Context;
import ratpack.http.MutableHeaders;


public class CrosEnable implements Handler {

    public void handle(Context context) {
        MutableHeaders headers = context.getResponse().getHeaders();
        headers.set("Access-Control-Allow-Origin", '*');
        headers.set("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept");
        headers.set("Content-Type","application/json");
        context.next();
    }

}
