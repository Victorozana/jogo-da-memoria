package com.example.jogomemoria;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import java.io.IOException;
import java.util.Objects;

public class Controle {
    @FXML
    private Label pontuacaoJogador1, pontuacaoJogador2;
    @FXML
    //nome dos jogadores
    private Text jogador1, jogador2;
    @FXML
    private Text exibirVencedor;
    private Jogo jogo;
    private ImageView portaRetratoPrimeiraCarta;
    private boolean bloqueioJogada, primeiraCartaFoiSelecionada;
    private int posicao1, posicao2;

    @FXML
    public void initialize() {
        //posições genericas que nunca serão acessadas para iniciar o jogo
        posicao2 = -1;
        posicao1 = -1;
        //edita o Text (nome dos jogadores)
        jogador1.setText(TratadorDeArquivoJogador.getTodosJogadores().get(0).getNome());
        jogador2.setText(TratadorDeArquivoJogador.getTodosJogadores().get(1).getNome());

        //inicia a pontuacao dos jogadores
        pontuacaoJogador2.setText("  Pontuacao: 0");
        pontuacaoJogador1.setText("  Pontuacao: 0");

        //desbloqueia a jogada
        bloqueioJogada = false;

        //false para primeira carta selecionada
        primeiraCartaFoiSelecionada = false;

        //instância a Classe jogo
        jogo = Jogo.getInstancia();
    }

    //uma foi carta clicada
    @FXML
    protected void mouseClicado(MouseEvent evento) {
        //verifica se a primeira carta foi selecionada e o jogo está bloqueado, ou se os pares foi encontrado, bloqueando a jogada
        if (primeiraCartaFoiSelecionada && bloqueioJogada || jogo.getParesEncontrados() == 12){
            return;
        }
        //pega a posição clicada
        ImageView imageViewCarta = (ImageView) evento.getSource();
        String id = imageViewCarta.getId();
        //retorna a carta que está no tabuleiro
        int linha = Integer.parseInt(id.substring(5, 6));
        int coluna = Integer.parseInt(id.substring(7, 8));
        //verifica se o usuário não clicou na mesma posição
        if(isMesmaPosicaoClicada(linha, coluna)) {
            return;
        }
        Carta cartaAtual = jogo.jogada(linha, coluna);
        //pega a imagem da carta atual
        imageViewCarta.setImage(virarCarta(cartaAtual, true));
        //verifica se a jogada está liberada e a primeira carta não foi selecionada
        if (!bloqueioJogada && !primeiraCartaFoiSelecionada) {
            //muda o boolean da primeira carta clicada para true, e armazena a imagem da primeira carta
            primeiraCartaFoiSelecionada = true;
            portaRetratoPrimeiraCarta = imageViewCarta;
        } else if(!bloqueioJogada){
            bloqueioJogada = true;
            //compara as duas cartas, se true as cartas foram encontradas
            if (jogo.verificarJogada(linha, coluna)){
                //pontua na cena
                pontuacaoJogador1.setText("  Pontuacao: "+jogo.getPontuacaoJogador1());
                pontuacaoJogador2.setText("  Pontuacao: "+jogo.getPontuacaoJogador2());
                reiniciarJogada();
                //se todos os pares forem encontrados, chama o método jogo acabou
                if (jogo.getParesEncontrados() == 12) jogoAcabou();
            //se a carta não for encontrada puxa o verso
            } else {
                animacaoVirarCarta(portaRetratoPrimeiraCarta, imageViewCarta, cartaAtual);
            }
        }
    }

    //desbloqueia a jogada e libera a primeira carta selecionada
    private void reiniciarJogada() {
        bloqueioJogada = false;
        primeiraCartaFoiSelecionada = false;
        posicao2 = -1;
        posicao1 = -1;
    }

    /**
     * Cria a imagem puxando o ‘id’ da carta selecionada
     * @param carta carta lógica a ser puxada
     * @param paraFrente true (para frente) false (para trás)
     * @return a imagem da carta lógica
     */
    private Image virarCarta(Carta carta, boolean paraFrente) {
        if (paraFrente) {
            return new Image((Objects.requireNonNull(getClass().getResourceAsStream(carta.getCaminhoFrente()))));
        }
        return new Image((Objects.requireNonNull(getClass().getResourceAsStream("/imagens/verso.png"))));
    }

    /**
     * Transição de puxar os versos das cartas selecionada
     * @param portaRetratoPrimeiraCarta imagem da primeira carta
     * @param portaRetratoSegundaCarta imagem da segunda carta
     * @param cartaAtual carta atual selecionada
     */
    private void animacaoVirarCarta(ImageView portaRetratoPrimeiraCarta, ImageView portaRetratoSegundaCarta, Carta cartaAtual){
        PauseTransition pausa = new PauseTransition(Duration.seconds(0.5));
        pausa.setOnFinished(e ->{
                    portaRetratoPrimeiraCarta.setImage(virarCarta(jogo.getIdPrimeiraCarta(), false));
                    portaRetratoSegundaCarta.setImage(virarCarta(cartaAtual, false));
                    reiniciarJogada();
                }
        );
        pausa.play();
    }

    //se o jogo acabar, mostra o vencedor
    private void jogoAcabou(){
        if (jogo.getPontuacaoJogador1() > jogo.getPontuacaoJogador2()){
            exibirVencedor.setText(TratadorDeArquivoJogador.getTodosJogadores().get(0).getNome()+" Venceu!");
        } else if (jogo.getPontuacaoJogador1() < jogo.getPontuacaoJogador2()){
            exibirVencedor.setText(TratadorDeArquivoJogador.getTodosJogadores().get(1).getNome()+" Venceu!");
        } else {
            exibirVencedor.setText("Jogo empatado!");
        }
    }

    //se o usuario clicar na mesma posição, ele retorna true
    private boolean isMesmaPosicaoClicada(int linha, int coluna){
        if (primeiraCartaFoiSelecionada) {posicao2 = linha * 4 + coluna;
        } else {
            posicao1 = linha * 4 + coluna;
        }
        return posicao2 == posicao1;
    }

    /**
     * chama a tela inicio para selecionar um novo pack de cartas, e mudar o nome do jogador
     * @param evento recebe o evento (clicada do usuário no botão iniciar novo jogo)
     * @throws IOException trata problemas de entrada e saida
     */
    @FXML
    protected void chamarTelaInicio(ActionEvent evento) throws IOException {
            AnchorPane telaInicio = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("tela-inicio.fxml")));
            Object origem = evento.getSource();
            Node node = (Node) origem;
            Scene cena = node.getScene();
            Window janela = cena.getWindow();
            Stage estado = (Stage) janela;
            estado.setScene(new Scene(telaInicio));
            estado.show();
    }
}
