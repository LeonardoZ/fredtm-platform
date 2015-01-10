package com.fredtm.desktop.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;

import com.fredtm.core.model.Coleta;
import com.fredtm.core.model.Operacao;
import com.fredtm.desktop.controller.utils.MainControllerTabCreator;

public class MainController extends BaseController implements Initializable {

	@FXML
	private ToggleButton btnSincronizar;

	@FXML
	private Button btnProjetos;

	@FXML
	private Button btnExportar;

	@FXML
	private Button btnSair;

	@FXML
	private TabPane tabPane;

	@FXML
	private VBox boxNenhumSync;

	private MainControllerTabCreator tabCreator;

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
		tabCreator = new MainControllerTabCreator(tabPane);
	}

	@FXML
	private void onSincronizarClicked(ActionEvent event) {
		tabPane.getTabs().clear();
		criarView("/fxml/projetos.fxml", "Projetos");
		btnProjetos.setDisable(false);
	}

	public void abrirAtividades(Operacao operacao) {
		Consumer<AtividadesController> controllerAction = c -> c
				.setOperacao(operacao);
		criarView("/fxml/atividades.fxml",
				"Atividades - " + operacao.getNome(), controllerAction);
		btnProjetos.setDisable(false);
	}

	public void abrirColetas(Operacao operacao) {
		Consumer<ColetasController> consumidor = c -> c.setOperacao(operacao);
		criarView("/fxml/coletas.fxml", "Coletas - " + operacao.getNome(),
				consumidor);
	}

	public void habilitarExportarAtividades(Operacao operacao) {
		Consumer<AtividadesController> consumidor = c -> c
				.setOperacao(operacao);
		criarView("/fxml/exportar_atividades.fxml", "Exportar atividades - "
				+ operacao.getNome(), consumidor);
	}

	public void abrirTemposColetados(Coleta coleta) {
		Consumer<TemposColetadosController> consumidor = c -> c
				.setTempos(coleta.getTemposEmOrdemCronologica());
		criarView("/fxml/tempos_coletados.fxml",
				"Tempos coletados - " + coleta.toString(), consumidor);
	}

	public void exportarColetas(List<Coleta> coletas) {
		Consumer<ExportarColetasController> consumidor = c -> c
				.setColetas(coletas);
		criarView("/fxml/exportar_coleta.fxml", "Exportar coletas", consumidor);
	}

	public void criarGraficoPizza(Coleta coleta) {
		Consumer<GraficoPizzaController> consumidor = c -> c.setColeta(coleta);
		criarView("/fxml/grafico_pizza.fxml", "Gráfico pizza", consumidor);
		
	}

	private <T extends BaseController> void criarView(String fxml, String titulo) {
		tabCreator.criaViewMecanismo(fxml, titulo, Optional.empty());
	}

	private <T extends BaseController> void criarView(String fxml,
			String titulo, Consumer<T> consumidor) {
		tabCreator.criaViewMecanismo(fxml, titulo, Optional.of(consumidor));
	}

}
