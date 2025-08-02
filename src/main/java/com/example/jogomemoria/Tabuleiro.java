package com.example.jogomemoria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tabuleiro {
    private final int colunas;
    private final int linhas;
    private final List<Carta> cartas;

    public Tabuleiro(int linhas, int colunas){
        this.linhas = linhas;
        this.colunas = colunas;
        this.cartas = new ArrayList<>();
        criarCartas();
        embaralhar();
    }

    //cria as cartas lógicas, montando no tabuleiro
    private void criarCartas() {
        int numeroDePares = (this.linhas * this.colunas) / 2;
        for(int i = 0; i<numeroDePares; i++){
            Carta novaCarta = new Carta(i, ControleInicio.getBotaoSelecionado().getText());
            cartas.add(novaCarta);
            cartas.add(novaCarta);
        }
    }

    public Carta getCarta(int linha, int coluna){
        return this.cartas.get(linha * this.colunas + coluna);
    }

    private void embaralhar() {
        Collections.shuffle(cartas);
    }
}
