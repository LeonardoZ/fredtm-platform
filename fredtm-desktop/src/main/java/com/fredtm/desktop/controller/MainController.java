package com.fredtm.desktop.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import com.fredtm.core.model.Coleta;
import com.fredtm.core.model.Operacao;
import com.fredtm.desktop.OperacoesJsonUtils;
import com.fredtm.desktop.controller.grafico.DistribuicaoTempoAtividadeController;
import com.fredtm.desktop.controller.grafico.TempoObtidoPorClassificacaoController;
import com.fredtm.desktop.controller.utils.MainControllerTabCreator;
import com.fredtm.desktop.controller.utils.TiposGrafico;
import com.fredtm.desktop.sync.ClientConnected;
import com.fredtm.desktop.sync.SwingQRCodeGenerator;
import com.fredtm.desktop.sync.SyncServer;

public class MainController extends BaseController implements Initializable,
		ClientConnected {

	@FXML
	private ToggleButton btnSincronizar;

	@FXML
	private Button btnProjetos;

	@FXML
	private Button btnInstrucoes;

	@FXML
	private Button btnSair;

	@FXML
	private TabPane tabPane;

	@FXML
	private VBox boxNenhumSync;

	private MainControllerTabCreator tabCreator;

	private Optional<List<Operacao>> operacoes;

	private Thread thread;

	private JDialog jDialog;

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
		tabCreator = new MainControllerTabCreator(tabPane);
		tabPane.getTabs().clear();
	}

	@FXML
	private void onSincronizarClicked(ActionEvent event) {
		if (!btnSincronizar.isSelected()) {
			tabPane.getTabs().clear();
			btnProjetos.setDisable(true);
		} else {
			acaoSincronizarAtivo();
		}
	}

	private void acaoSincronizarAtivo() {
		criarServidorDeTransferencia();
		Optional<BufferedImage> gerarQRCode = new SwingQRCodeGenerator()
				.gerarQRCode();
		criarJDialog(gerarQRCode.orElseThrow(IllegalStateException::new));
	}

	private void criarServidorDeTransferencia() {
		if (thread == null) {
			thread = new Thread(() -> new SyncServer(this));
			thread.start();
		}
	}

	private void criarJDialog(BufferedImage image) {
		JLabel canvasLabel = new JLabel(new ImageIcon(image));
		JLabel textTopLabel = new JLabel(
				"Leia esse QRCode com a opção \"Sincronizar com PC\" "
						+ "no aplicativo Fred TM para sincronizar.");
		textTopLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 3, 10));
		if (jDialog == null) {
			jDialog = new JDialog();
			jDialog.setTitle("Sincronizar com aplicativo Fred TM");
			jDialog.setSize(670, 200);
			jDialog.setLayout(new BorderLayout(10, 10));
			jDialog.getContentPane().setBackground(new Color(226, 237, 222));
			jDialog.getContentPane().setForeground(new Color(226, 237, 222));
			jDialog.add(canvasLabel, BorderLayout.CENTER);
			jDialog.add(textTopLabel, BorderLayout.NORTH);
			jDialog.setVisible(true);
		} else {
			jDialog.toFront();
		}
	}

	@Override
	public void onConnection(String jsonContent) {
		OperacoesJsonUtils utils = new OperacoesJsonUtils();
		operacoes = utils.converterJsonParaJava(jsonContent);
		if (operacoes.isPresent()) {
			Consumer<ProjetosController> consumer = c -> c
					.setOperacoes(operacoes.get());
			criarView("/fxml/projetos.fxml", "Projetos", consumer);
			btnProjetos.setDisable(false);
		}
		jDialog.setVisible(false);
		jDialog = null;
	}

	/**
	 * private void acaoSincronizarAtivo() { tabPane.getTabs().clear(); File
	 * selectedDirectory; DirectoryChooser dc = new DirectoryChooser();
	 * dc.setTitle
	 * ("Escolha o local de seus arquivos no aparelho Android conectado.");
	 * selectedDirectory = dc.showDialog(getWindow()); if (selectedDirectory !=
	 * null && selectedDirectory.isDirectory()) { BuscarDispositivo
	 * buscarDispositivo = new BuscarDispositivo( selectedDirectory); operacoes
	 * = buscarDispositivo.getOperacoes(); if (operacoes.isPresent()) {
	 * Consumer<ProjetosController> consumer = c -> c
	 * .setOperacoes(operacoes.get()); criarView("/fxml/projetos.fxml",
	 * "Projetos", consumer); btnProjetos.setDisable(false); } else {
	 * JOptionPane.showMessageDialog(null, "Falha ao sincronizar."); } } }
	 */

	@FXML
	void onProjetosClicked(ActionEvent event) {
		Consumer<ProjetosController> consumer = c -> c.setOperacoes(operacoes
				.get());
		criarView("/fxml/projetos.fxml", "Projetos", consumer);
	}

	@FXML
	void onConfigurarClicked(ActionEvent event) {
		criarView("/fxml/configurar.fxml", "Configurar");
	}

	@FXML
	void onSairClicked(ActionEvent event) {
		System.exit(0);
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

	public void abrirTiposDeGraficos(Coleta coleta, List<Coleta> coletas) {
		Consumer<TiposGraficosController> consumidor = c -> {
			c.setColeta(coleta);
			c.setColetas(coletas);
		};
		criarView("/fxml/tipos_graficos.fxml",
				"Análises da coleta " + coleta.toString(), consumidor);
	}

	public void abrirAnaliseGrafica(TiposGrafico tipo, Coleta coleta,
			List<Coleta> coletas) {
		switch (tipo) {
		case DISTRIBUICAO_TEMPO_ATIVIDADE_PIZZA:
			Consumer<DistribuicaoTempoAtividadeController> pizzaConsumer = c -> c
					.setColeta(coleta);
			criarView("/fxml/grafico_pizza.fxml",
					"Distribuição tempo/atividade: " + coleta.toString(),
					pizzaConsumer);
			break;

		case CLASSIFICACAO_POR_BARRAS:
			Consumer<TempoObtidoPorClassificacaoController> barConsumer = c -> c
					.setColeta(coleta);
			criarView("/fxml/grafico_linha_classificacao.fxml",
					"Tempo por classificação: " + coleta.toString(),
					barConsumer);
			break;

		case CLASSIFICACAO_CICLOS_POR_BARRAS:
			Consumer<TempoObtidoPorClassificacaoController> barCicloConsumer = c -> c
					.setColetas(coletas);
			criarView("/fxml/grafico_linha_classificacao.fxml",
					"Tempo por classificação/ciclo: " + coleta.toString(),
					barCicloConsumer);
			break;

		default:
			break;
		}
	}

	private <T extends BaseController> void criarView(String fxml, String titulo) {
		tabCreator.criaViewMecanismo(fxml, titulo, Optional.empty());
	}

	private <T extends BaseController> void criarView(String fxml,
			String titulo, Consumer<T> consumidor) {
		tabCreator.criaViewMecanismo(fxml, titulo, Optional.of(consumidor));
	}

}
