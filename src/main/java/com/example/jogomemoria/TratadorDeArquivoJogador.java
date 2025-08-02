package com.example.jogomemoria;

import org.json.*;

import java.io.*;
import java.util.Formatter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
public class TratadorDeArquivoJogador {

    private static final String FILE_NAME = "jogador.json";

    // Método para ler o arquivo JSON
    public static JSONArray lerJSON() {
        JSONArray jogos = new JSONArray();
        File f = new File(FILE_NAME);
        if (f.exists()) {
            try (Scanner br = new Scanner(new FileReader(FILE_NAME))) {
                StringBuilder jsonContent = new StringBuilder();
                String linha;
                while (br.hasNext()) {
                    linha = br.nextLine();
                    jsonContent.append(linha);
                }
                jogos = new JSONArray(jsonContent.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return jogos;
    }

    // Método para escrever o JSON no arquivo
    public static void escreverJSON(JSONArray jogos) {
        try (Formatter file = new Formatter(FILE_NAME)) {
            file.format("%s", jogos.toString(4)); // Indenta o JSON com 4 espaços
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para adicionar uma nova pessoa
    public static void adicionarJogador(String nomeJogador) {
        JSONArray jogadores = lerJSON();
        JSONObject novoJogador = new JSONObject();
        novoJogador.put("nomeJogador", nomeJogador);
        novoJogador.put("pontuacao", 0);
        jogadores.put(novoJogador);
        escreverJSON(jogadores);
        System.out.println("Jogador adicionado!");
    }

    // Método para deletar um jogador
    public static void deletarJogador(String nome) {
        JSONArray jogadores = lerJSON();
        for (int i = 0; i < jogadores.length(); i++) {
            JSONObject jogador = jogadores.getJSONObject(i);
            if (jogador.getString("nomeJogador").equals(nome)) {
                jogadores.remove(i);
                escreverJSON(jogadores);
                return;
            }
        }
    }

    public static List<Jogador> getTodosJogadores() {
        JSONArray jogadoresJSON = lerJSON();
        List<Jogador> lista = new ArrayList<>();

        for (int i = 0; i < jogadoresJSON.length(); i++) {
            JSONObject obj = jogadoresJSON.getJSONObject(i);
            String nome = obj.getString("nomeJogador");
            lista.add(new Jogador(nome));
        }
        return lista;
    }
}
