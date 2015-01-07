package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

import com.fredtm.core.model.Atividade;
import com.fredtm.core.model.Coleta;
import com.fredtm.core.model.Operacao;
import com.fredtm.core.model.TipoAtividade;
import com.fredtm.desktop.eventbus.MainEventBus;

public class ProjetosController implements Initializable {


	@FXML
	private ListView<Operacao> listViewProjetos;

	private Operacao operacao;

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		MenuItem menuColeta = new MenuItem("Ver coletas");
		MenuItem menuAtividade = new MenuItem("Ver atividades");
		menuAtividade.setOnAction(ev -> 
			MainEventBus.INSTANCE.eventoAbrirAtividades(operacao.getAtividadesPreDefinidas())
		);
		menuColeta.setOnAction(ev -> 
			MainEventBus.INSTANCE.eventoAbrirColetas(operacao.getColetas())
		);
		
		ContextMenu contextMenu = new ContextMenu(menuColeta, menuAtividade);
		contextMenu.setStyle("-fx-background-color: #fff");
		contextMenu.setAutoFix(true);
		listViewProjetos.setContextMenu(contextMenu);

		listViewProjetos.setOnMouseClicked(event -> {
			operacao = this.listViewProjetos.getSelectionModel()
					.getSelectedItem();

		});
		for (int i = 0; i < 20; i++) {
			Operacao operacao = gerarOperacao(i);
			listViewProjetos.getItems().add(operacao);
		}

	}

	private Operacao gerarOperacao(int i) {
		Operacao operacao = new Operacao("Teste " + i, "Teste empresa " + i,
				"Primeira operação " + i);
		for (int j = 0; j < 20; j++) {
			TipoAtividade t = i % 2 == 0 ? TipoAtividade.PRODUTIVA
					: j % 2 == 0 ? TipoAtividade.IMPRODUTIVA
							: TipoAtividade.PRODUTIVA;
			Atividade atividade = new Atividade("Atv " + j,
					"Essa é a atv " + j, t);
			atividade.setOperacao(operacao);
			operacao.addAtividade(atividade);
		}
		List<Atividade> atividadesPreDefinidas = operacao
				.getAtividadesPreDefinidas();
		for (int g = 0; g < 10; g++) {
			Coleta coleta = new Coleta();
			coleta.setAtividades(atividadesPreDefinidas);
			atividadesPreDefinidas.forEach(a -> {
				for (int z = 0; z < 5; z++) {
					coleta.addNovoTempo(a, new Date().getTime(),
							new Date().getTime() + 1500, (long) z);
				}
			});
		}

		return operacao;

	}

}
