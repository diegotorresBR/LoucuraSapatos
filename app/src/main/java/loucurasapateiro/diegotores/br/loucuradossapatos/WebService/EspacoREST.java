package loucurasapateiro.diegotores.br.loucuradossapatos.WebService;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;

/**
 * Created by diego on 06/11/15.
 */
public class EspacoREST extends AsyncTask<Void, Void, ArrayList<Espaco>> {

    private static final String URL_WS = "http://191.252.56.222:8080/Vendas_WS/espaco/";


    public ArrayList<Espaco> getListaConsultor() throws Exception {

        String[] resposta = new WebServiceClient().getWS(URL_WS + "buscarEspaco2");
//       String[] resposta = new WebServiceCliente().get(URL_WS + "buscarTodos");

        if (resposta[0].equals("200")) {
            Gson gson = new Gson();
            ArrayList<Espaco> listaConsultor = new ArrayList<Espaco>();
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(resposta[1]).getAsJsonArray();

            for (int i = 0; i < array.size(); i++) {
                listaConsultor.add(gson.fromJson(array.get(i), Espaco.class));
                Log.i("rest GSON", array.get(i).toString());
            }
            return listaConsultor;
        } else {
            throw new Exception(resposta[1]);
        }
    }


    @Override
    protected ArrayList<Espaco> doInBackground(Void... params) {
        ArrayList<Espaco> lista = new ArrayList<Espaco>();
        try {

            lista = getListaConsultor();

        } catch (Exception e) {
            Log.e("Erro no AsyncTask", e.getMessage());
        }

        return lista;
    }
}
