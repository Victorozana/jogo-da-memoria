package com.example.jogomemoria;

public class Carta {
    private final int id;
    private String caminhoFrente;

    protected Carta(int id, String packEscolhido){
        this.id = id;
        switch (packEscolhido) {
            case "Pack 1" -> this.caminhoFrente = "/imagens/Cartas/Pack1/carta" + id + ".png";
            case "Pack 2" -> this.caminhoFrente = "/imagens/Cartas/Pack2/carta" + id + ".png";
            case "Pack 3" -> this.caminhoFrente = "/imagens/Cartas/Pack3/carta" + id + ".png";
        }
    }

    protected int getId() {
        return id;
    }

    public String getCaminhoFrente() {
        return caminhoFrente;
    }
}
