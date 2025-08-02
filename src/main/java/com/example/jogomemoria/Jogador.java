package com.example.jogomemoria;

public class Jogador {
    private final String nome;
    private int pontuacao;

    public Jogador(String nome){
        this.nome = nome;
        this.pontuacao = 0;
    }

    public void setPontuacao() {
        this.pontuacao += 1;
    }

    public int getPontuacao(){
        return pontuacao;
    }

    public String getNome() {
        return nome;
    }
}
