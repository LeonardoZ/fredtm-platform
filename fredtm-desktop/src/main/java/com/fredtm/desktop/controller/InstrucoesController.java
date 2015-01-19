package com.fredtm.desktop.controller;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class InstrucoesController extends BaseController implements
		Initializable {
	private final static String CONTENT = 
			"1 - Conecte seu aparelho Android no computador utilizando um cabo USB. Certifique-se de que seu computador consiga acessar os arquivos do aparelho. \n"
			+ "2 - No aplicativo de celular, acesse a opção na tela principal \"Sincronizar com PC\". \n"
			+ "3 - Pressione o botão \"Preparar para sincronizar com Desktop\" e aguarde até que a tela fique verde. \n"
			+ "4 - No seu computador, acesse os arquivos do seu aparelho Android, acesse a pasta \"Documents\" e copie a pasta \"fred_tm\" para o seu computador. \n"
			+" Na aplicação então, clique em \"Sincronizar\" e selecione a sua pasta recém copiada."
			+ "5 - As operações serão listados na aplicação e você poderá verificar as opções disponíveis para esses dados utilizando o botão DIREITO do mouse.\n "
			+ "\n\n"
			+ "ATENÇÃO.: O celular deve estar com o modo de transferência \"MTP\", que geralmente é padrão nas comunicações via USB. \nEm aparelhos Android modernos,"
			+ "o celular deve estar listado no computador com seu modelo e marca, e ao acessar o dispostivo verá a opção \"Armazenamento interno\", \n"
			+ "que é a localização dos arquivos no aparelho. ";
	@FXML
	private TextFlow textFlowInstrucoes;

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		ByteBuffer encoded = java.nio.charset.StandardCharsets.UTF_8.encode(CONTENT);
		Text text;
		try {
			text = new Text(new String(encoded.array(),"UTF-8"));
			textFlowInstrucoes.getChildren().add(text);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
