package br.com.livraria.dao;

import br.com.livraria.model.Endereco;
import com.google.gson.Gson;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ViaCepService {

    public Endereco buscarEnderecoPorCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(url);
            CloseableHttpResponse response = client.execute(request);
            try {
                String json = EntityUtils.toString(response.getEntity());

                Gson gson = new Gson();
                return gson.fromJson(json, Endereco.class);
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

