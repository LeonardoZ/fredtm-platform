package com.fredtm.desktop.controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import com.fredtm.core.model.Collect;
import com.fredtm.core.model.Operation;
import com.fredtm.core.util.FredObjectMapper;
import com.fredtm.core.util.OperationJsonUtils;
import com.fredtm.desktop.controller.chart.TimeByActivityController;
import com.fredtm.desktop.controller.chart.TimeByClassificationController;
import com.fredtm.desktop.controller.chart.TimeBySimplifiedClassificationController;
import com.fredtm.desktop.controller.chart.TimesChartController;
import com.fredtm.desktop.controller.utils.FredCharts;
import com.fredtm.desktop.controller.utils.MainControllerTabCreator;
import com.fredtm.desktop.sync.ClientConnection;
import com.fredtm.desktop.sync.SwingQRCodeGenerator;
import com.fredtm.desktop.sync.SyncServer;
import com.fredtm.resources.OperationResource;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController extends BaseController implements Initializable, ClientConnection {

	@FXML
	private Label btnSync;

	@FXML
	private Label btnImport;

	@FXML
	private Label btnExit;

	@FXML
	private TabPane tabPane;

	@FXML
	private VBox boxNoSync;

	@FXML
	private ImageView imLogo;

	boolean syncSelected = true;

	private MainControllerTabCreator tabCreator;

	private JDialog jDialog;

	private WindowAdapter adapter;

	private ExecutorService service;

	private SyncServer syncServer;

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
		tabCreator = new MainControllerTabCreator(tabPane);
		tabPane.getTabs().clear();
		abrirParaDebug();
	}

	private void abrirParaDebug() {
		File f = new File("C:/Users/Leonardo/Desktop/operations_1440786273270.json");
		OperationJsonUtils oju = new OperationJsonUtils();
		List<OperationResource> operations = oju.jsonToJava(f);
		List<Operation> entities = FredObjectMapper.mapResourcesToEntities(operations);
		createOperationsWindow(entities);
	}

	@FXML
	private void onSincronizarClicked() {
		if (!syncSelected) {
			tabPane.getTabs().clear();
		} else {
			actionSyncActive();
		}

		syncSelected = !syncSelected;
	}

	private void actionSyncActive() {
		configurarAdapter();
		createTransferServer();
		Optional<BufferedImage> gerarQRCode = new SwingQRCodeGenerator().gerarQRCode();
		createJDialog(gerarQRCode.orElseThrow(IllegalStateException::new));
	}

	private void configurarAdapter() {
		adapter = new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				syncServer.stop();
				syncSelected = false;
				jDialog = null;
			}
		};

	}

	private void createTransferServer() {
		service = Executors.newSingleThreadExecutor();
		syncServer = new SyncServer(MainController.this, service);
		service.execute(syncServer::start);
	}

	private void createJDialog(BufferedImage image) {
		JLabel canvasLabel = new JLabel(new ImageIcon(image));
		JLabel labelTopText = new JLabel(
				"Leia esse QRCode com a opção \"Sincronizar com PC\" " + "no aplicativo Fred TM para sincronizar.");
		labelTopText.setBorder(BorderFactory.createEmptyBorder(10, 10, 3, 10));
		if (jDialog == null) {
			jDialog = new JDialog();
			jDialog.setTitle("Sincronizar com aplicativo Fred TM");
			jDialog.setSize(670, 200);
			jDialog.setLayout(new BorderLayout(10, 10));
			jDialog.getContentPane().setBackground(new Color(255, 255, 255));
			jDialog.getContentPane().setForeground(new Color(226, 237, 222));
			jDialog.setLocationRelativeTo(null);
			jDialog.add(canvasLabel, BorderLayout.CENTER);
			jDialog.add(labelTopText, BorderLayout.NORTH);
			jDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			jDialog.addWindowListener(adapter);
			jDialog.setVisible(true);
			jDialog.setAlwaysOnTop(true);
		} else {
			jDialog.toFront();
		}
	}

	@Override
	public void onConnection(String jsonContent) {
		OperationJsonUtils utils = new OperationJsonUtils();
		// List<Operation> operations = jsonContent.stream()
		// .map(utils::jsonElementToJava)
		// .map(FredObjectMapper::mapResourceToEntity)
		// .collect(Collectors.toList());
		List<OperationResource> jsonToJava = utils.jsonToJava(jsonContent);
		List<Operation> operations = FredObjectMapper.mapResourcesToEntities(jsonToJava);
		createOperationsWindow(operations);
		jDialog.setVisible(false);
		jDialog = null;
	}

	private void createOperationsWindow(List<Operation> list) {
		Consumer<ProjectsController> consumer = c -> c.setOperacoes(list);
		createView("/fxml/projects.fxml", "Projetos", consumer);
	}

	@FXML
	void onConfigureClicked(MouseEvent me) {
		createView("/fxml/configure.fxml", "Configurar");
	}

	@FXML
	void onImportarJsonClicked() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Escolha o arquivo \"operations.json\" gerado por seu aplicativo ou exportado por você.");
		fc.setSelectedExtensionFilter(
				new ExtensionFilter("Arquivos .json gerados por esse software ou pelo Fred TM (mobile)", "*.<json>"));
		File selectedItem = fc.showOpenDialog(getWindow());

		if (selectedItem != null) {
			OperationJsonUtils oju = new OperationJsonUtils();
			List<OperationResource> operations = oju.jsonToJava(selectedItem);
			List<Operation> entities = FredObjectMapper.mapResourcesToEntities(operations);
			createOperationsWindow(entities);
		}
		fc = null;
	}

	@FXML
	void onExitClicked() {
		System.exit(0);
	}

	public void openActivities(Operation operation) {
		Consumer<ActivitiesController> controllerAction = c -> c.setOperacao(operation);
		createView("/fxml/activities.fxml", "Atividades - " + operation.getName(), controllerAction);
	}

	public void openCollects(Operation operation) {
		Consumer<CollectsController> consumidor = c -> c.setOperacao(operation);
		createView("/fxml/collects.fxml", "Coletas - " + operation.getName(), consumidor);
	}

	public void openCollectedTimes(Collect collect) {
		Consumer<CollectedTimesController> consumidor = c -> c.setTempos(collect.getTimeInChronologicalOrder());
		createView("/fxml/collected_times.fxml", "Tempos coletados - " + collect.toString(), consumidor);
	}

	public void exportCollects(List<Collect> collects) {
		Consumer<ExportCollectsController> consumidor = c -> c.setcollects(collects);
		createView("/fxml/export_collect.fxml", "Exportar coletas", consumidor);
	}

	public <T extends BaseController> void openGraphicalAnalisys(FredCharts type, List<Collect> collects) {
		switch (type) {

		case TIME_BY_CLASSIFICATION:
			Consumer<TimeByClassificationController> barCicleConsumer = c -> c.setCollects(collects);
			createView("/fxml/chart_line_classification.fxml",
					"Tempo/classificação por coleta (ciclo): " + collects.get(0).getOperation().toString(),
					barCicleConsumer);
			break;
		case TIME_BY_SIMPLE_CLASSIFICATION:
			Consumer<TimeBySimplifiedClassificationController> barSimpleConsumer = c -> c.setCollects(collects);
			createView("/fxml/chart_line_classification_simple.fxml",
					"Tempo/Classificação por coleta (ciclo): " + collects.get(0).getOperation().toString(),
					barSimpleConsumer);
			break;
		default:
			break;
		}
	}

	public void openGraphicalAnalisys(FredCharts type, Collect collect) {
		switch (type) {
		case TIME_ACTIVITY_DISTRIBUTION:
			Consumer<TimeByActivityController> chart = c -> c.setCollect(collect);
			createView("/fxml/chart_time_activity.fxml", "Distribuição tempo/atividade: "+collect.toString(), chart);
			break;

		case TIME_BY_CLASSIFICATION:
			Consumer<TimeByClassificationController> barConsumer = c -> c.setCollect(collect);
			createView("/fxml/chart_line_classification.fxml", "Tempo/classificação: " + collect.toString(),
					barConsumer);
			break;

		case TIME_BY_SIMPLE_CLASSIFICATION:
			Consumer<TimeBySimplifiedClassificationController> barSimpleConsumer = c -> c.setCollect(collect);
			createView("/fxml/chart_line_classification_simple.fxml", "Tempo/classificação simples: " + collect.toString(),
					barSimpleConsumer);
			break;
		case TIME_ANALYSYS:
			Consumer<TimesChartController> timeConsumer = c -> c.setCollect(collect);
			createView("/fxml/chart_times.fxml", "Análises dos tempos: " + collect.toString(),
					timeConsumer);
			break;
			
			
		default:
			break;
		}
	}

	private <T extends BaseController> void createView(String fxml, String title) {
		tabCreator.createViewMechanism(fxml, title, Optional.empty());
	}

	private <T extends BaseController> void createView(String fxml, String title, Consumer<T> consumer) {
		tabCreator.createViewMechanism(fxml, title, Optional.of(consumer));
	}

}
