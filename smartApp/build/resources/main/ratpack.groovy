import com.smartapp.api.CrosEnable
import groovy.json.JsonOutput
import ratpack.exec.Promise
import ratpack.func.Action
import ratpack.handling.Chain
import ratpack.handling.Context
import ratpack.handling.Handler
import ratpack.http.client.HttpClient
import ratpack.http.client.ReceivedResponse
import ratpack.http.client.RequestSpec

import static ratpack.groovy.Groovy.ratpack

ratpack {

    handlers {
        String geturi = "https://graph.api.smartthings.com/api/smartapps/installations/7771cbf6-395b-410e-a12b-6fda5036f824/getLevel";
        String posturi = "https://graph.api.smartthings.com/api/smartapps/installations/7771cbf6-395b-410e-a12b-6fda5036f824/setLevel";
        def authorization = 'Bearer ecfb0b5f-68c1-407c-b7ef-b43df9b6cfc2';
        all(new CrosEnable())
        prefix("getLevel",new Action<Chain>() {
            @Override
            void execute(Chain chain) throws Exception {
                chain.get(new Handler() {
                    @Override
                    void handle(Context ctx) throws Exception {
                        HttpClient httpClient = ctx.get(HttpClient.class);
                        Promise<ReceivedResponse> responsePromise = httpClient.request(geturi.toURI()) {
                            it.method('GET')
                                    .headers {
                                it.set('Authorization', authorization)
                            }
                        }
                        responsePromise.then({
                            response -> response.send(ctx.render(response.getBody().text))
                        });
                    }
                })
            }
        })
        prefix("setLevel",new Action<Chain>() {
            @Override
            void execute(Chain chain) throws Exception {
                chain.post(new Handler() {
                    @Override
                    void handle(Context ctx) throws Exception {
                        HttpClient httpClient = ctx.get(HttpClient.class);
                        def body = "";
                        ctx.getRequest().getBody().then({ data ->
                            body = data.getText();
                        })
                        Promise<ReceivedResponse> responsePromise = httpClient.request(posturi.toURI()) {
                            it.method('POST')
                                    .headers {
                                it.set('Authorization',authorization )
                            }.body{
                                it.text(body.toString())
                            }
                        }
                        responsePromise.then({
                            response ->
                                response.send(ctx.render(response.getBody().text))


                        });
                    }
                })
            }
        })

    }
}