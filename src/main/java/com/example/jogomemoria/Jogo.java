package com.example.jogomemoria;

public class Jogo {
    private static Jogo instancia;
    private final Tabuleiro tabuleiro;
    private final Jogador jogador1;
    private final Jogador jogador2;
    private Jogador jogadorAtual;
    private boolean primeiraCartaFoiClicada;
    private Carta idPrimeiraCarta;
    private int paresEncontrados;


    private Jogo(){
        this.tabuleiro = new Tabuleiro(3, 4);
        this.primeiraCartaFoiClicada = false;
        jogador1 = new Jogador(TratadorDeArquivoJogador.getTodosJogadores().get(0).getNome());
        jogador2 = new Jogador(TratadorDeArquivoJogador.getTodosJogadores().get(1).getNome());
        jogadorAtual = jogador1;
        paresEncontrados = 0;
    }

    public static Jogo getInstancia(){
        if (instancia == null){
            instancia = new Jogo();
        }
        return instancia;
    }

    //esse método retorna true para carta encontrada, e false para carta não encontrada
    public boolean verificarJogada(int linha, int coluna){
        Carta cartaAtual = tabuleiro.getCarta(linha, coluna);
        if (idPrimeiraCarta.getId() == cartaAtual.getId() && paresEncontrados < 12){
            paresEncontrados++;
            paresEncontrados++;
            jogadorAtual.setPontuacao();
            return true;
        }
        alternarJogador();
        return false;
    }

    //retorna a carta lógica
    public Carta jogada(int linha, int coluna){
        if (!primeiraCartaFoiClicada) {
            idPrimeiraCarta = tabuleiro.getCarta(linha, coluna);
            primeiraCartaFoiClicada = true;
        } else {
            primeiraCartaFoiClicada = false;
        }
        return tabuleiro.getCarta(linha, coluna);
    }

    private void alternarJogador(){
        jogadorAtual = (jogadorAtual == jogador1) ? jogador2 : jogador1;
    }

    public int getPontuacaoJogador1(){
        return jogador1.getPontuacao();
    }

    public int getPontuacaoJogador2(){
        return jogador2.getPontuacao();
    }

    public Carta getIdPrimeiraCarta(){return this.idPrimeiraCarta;}

    public int getParesEncontrados(){return paresEncontrados;}

    //reinicia o jogo pegando uma nova instância do jogo
    public static void reiniciar(){
        instancia = new Jogo();
    }
}
