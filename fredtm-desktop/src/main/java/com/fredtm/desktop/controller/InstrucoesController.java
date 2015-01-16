package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class InstrucoesController extends BaseController implements
		Initializable {
	private final static String CONTENT = "1 - Conecte seu aparelho Android no computador utilizando um cabo USB. Certifique-se de que seu computador consiga acessar os arquivos do aparelho. \n"
			+ "2 - No aplicativo de celular, acesse a opção na tela principal \"Sincronizar com PC\". \n"
			+ "3 - Pressione o botão \"Preparar para sincronizar com Desktop\" e aguarde até que a tela fique verde. \n"
			+ "4 - No aplicativo para Desktop, clique na opção \"Sincronizar\" e escolha a pasta onde seu dispositivo mantém os arquivos.  \n"
			+ "5 - As operações serão listados na aplicação e você poderá verificar as opções disponíveis para esses dados utilizando o botão DIREITO do mouse.\n "
			+ "\n\n"
			+ "ATENÇÃO.: O celular deve estar com o modo de transferência \"MTP\", que geralmente é padrão nas comunicações via USB. \nEm aparelhos Android modernos,"
			+ "o celular deve estar listado no computador com seu modelo e marca, e ao acessar o dispostivo verá a opção \"Aramazenamento interno\", \n"
			+ "que é a localização dos arquivos no aparelho. ";
	@FXML
	private TextFlow textFlowInstrucoes;

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		Text text = new Text(CONTENT);
		textFlowInstrucoes.getChildren().add(text);
	}

}
