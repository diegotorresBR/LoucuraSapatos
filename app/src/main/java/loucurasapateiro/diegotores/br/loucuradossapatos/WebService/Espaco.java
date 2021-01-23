package loucurasapateiro.diegotores.br.loucuradossapatos.WebService;

import java.io.Serializable;

/**
 * Created by diego on 06/11/15.
 */
public class Espaco implements Serializable {

    private String id, espaco;

    public void setId(String id) {
        this.id = id;
    }

    public void setEspaco(String espaco) {
        this.espaco = espaco;
    }

    public String getId() {
        return id;
    }

    public String getEspaco() {
        return espaco;
    }

}
