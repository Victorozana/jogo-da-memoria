package com.example.jogomemoria;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Window;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class ControleInicio {
    @FXML
    private Button botaoIniciar;
    @FXML
    private Button botaoPack1;
    @FXML
    private Button botaoPack2;
    @FXML
    private Button botaoPack3;
    @FXML
    private TextField nomeJogador1;
    @FXML
    private TextField nomeJogador2;
    private static Button botaoSelecionado = null;

    @FXML
    public void initialize() {
        if (!TratadorDeArquivoJogador.getTodosJogadores().isEmpty()) {
            nomeJogador1.setDisable(true);
            nomeJogador2.setDisable(true);
            nomeJogador1.setText(TratadorDeArquivoJogador.getTodosJogadores().get(0).getNome());
            nomeJogador2.setText(TratadorDeArquivoJogador.getTodosJogadores().get(1).getNome());
        }
    }

    /**
     * Seleciona o pack que o jogador gostaria de escolher
     * @param evento evento que irá ocorrer ao clicar no botão
     */
    @FXML
    public void selecionarPack(ActionEvent evento){
        Button botaoClicado = (Button) evento.getSource();
        if (botaoPack1 != null) botaoPack1.setDisable(true);
        if (botaoPack2 != null) botaoPack2.setDisable(true);
        if (botaoPack3 != null) botaoPack3.setDisable(true);
        botaoSelecionado = botaoClicado;
        botaoIniciar.setDisable(false);
        botaoClicado.getStyleClass().add("botao-selecionado");
    }

    /**
     * Aciona a próxima cena para a aplicação principal do jogo
     * @param evento evento que o botão irá chamar ao ser acionado
     * @throws IOException tratar de erros de entrada e saida
     */
    @FXML
    public void proximaCena(ActionEvent evento) throws IOException {
       //verifica se tem um nome definido para os jogadores
        if (TratadorDeArquivoJogador.getTodosJogadores().isEmpty()) {
            TratadorDeArquivoJogador.adicionarJogador(nomeJogador1.getText());
            TratadorDeArquivoJogador.adicionarJogador(nomeJogador2.getText());
        }
        //se o jogo não tiver uma instância reinicia
        if (Jogo.getInstancia() != null) Jogo.reiniciar();
        //se o pack já foi escolhido libera para iniciar o jogo
        if (botaoSelecionado != null) {
                //puxa a próxima tela (jogo)
                AnchorPane telaPrincipal = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("tela-principal.fxml")));
                Object origem = evento.getSource();
                Node node = (Node) origem;
                Scene cena = node.getScene();
                Window janela = cena.getWindow();
                Stage estado = (Stage) janela;
                estado.setScene(new Scene(telaPrincipal));
                estado.show();
                //se o jogo for fechado, pergunta se quer salvar o nome dos jogadores
                estado.setOnCloseRequest(windowEvent -> {
                    Alert alertaDeSaida = new Alert(Alert.AlertType.CONFIRMATION);
                    alertaDeSaida.setTitle("Saindo do jogo...");
                    alertaDeSaida.setHeaderText("Você está prestes a sair do jogo!");
                    alertaDeSaida.setContentText("Deseja salvar o nome dos Jogadores?");
                    Optional<ButtonType> resultado = alertaDeSaida.showAndWait();
                    //se OK deixa salvo no arquivo JSON
                    if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                        estado.close();
                    //se não ele exclui os jogadores
                    } else {
                        TratadorDeArquivoJogador.deletarJogador(TratadorDeArquivoJogador.getTodosJogadores().get(0).getNome());
                        TratadorDeArquivoJogador.deletarJogador(TratadorDeArquivoJogador.getTodosJogadores().get(0).getNome());
                        estado.close();
                    }
                });
            }
        }

    public static Button getBotaoSelecionado() {
        return botaoSelecionado;
    }
}