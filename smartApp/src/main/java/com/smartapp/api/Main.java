package com.smartapp.api;
import com.fasterxml.jackson.databind.util.JSONPObject;
import ratpack.func.Action;
import ratpack.http.TypedData;
import ratpack.http.client.HttpClient;
import ratpack.server.RatpackServer;

import java.net.URI;

import static ratpack.jackson.Jackson.json;

public class Main {
    public static void main(String... args) throws Exception {
        String geturi = "https://graph.api.smartthings.com/api/smartapps/installations/4867c836-6215-4f8e-b0de-b8d6c0fb2a06/getLevel";
        String posturi = "https://graph.api.smartthings.com/api/smartapps/installations/4867c836-6215-4f8e-b0de-b8d6c0fb2a06/setLevel";
        String authorization = "Bearer ecfb0b5f-68c1-407c-b7ef-b43df9b6cfc2";

        RatpackServer.start(server -> server
                .handlers(chain -> chain.prefix("getLevel" , chain1 -> chain1.get(ctx -> {
                        HttpClient httpClient = ctx.get(HttpClient.class);
                        URI uri = new URI(geturi);
                        httpClient.request(uri,requestSpec -> {
                            requestSpec.method("GET");
                            requestSpec.headers(mutableHeaders -> {
                                mutableHeaders.add("Authorization",authorization);
                            });
                        }).then(receivedResponse -> {
                                    ctx.render(receivedResponse.getBody().getText());
                                }
                        );
                })).prefix("setLevel",chain1 -> chain1.post(ctx -> {

                            HttpClient httpClient = ctx.get(HttpClient.class);
                            URI uriP = new URI(posturi);
                            ctx.getRequest().getBody().then(typedData -> {
                                httpClient.request(uriP,requestSpec -> {
                                    requestSpec.method("POST");
                                    requestSpec.headers(mutableHeaders -> {
                                        mutableHeaders.add("Authorization",authorization);
                                    });
                                    requestSpec.getBody().text(typedData.getText());

                                }).then(receivedResponse -> {
                                    ctx.render(receivedResponse.getBody().getText());
                                });
                       });


                }))
                )
        );
    }
}
