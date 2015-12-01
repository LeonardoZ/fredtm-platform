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
import java.util.Properties;
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
import com.fredtm.desktop.sync.PropertiesFolderUtil;
import com.fredtm.desktop.sync.SwingQRCodeGenerator;
import com.fredtm.desktop.sync.SyncServer;
import com.fredtm.resources.OperationDTO;
import com.fredtm.resources.OperationsDTO;
import com.fredtm.resources.security.LoginDTO;
import com.fredtm.resources.security.LoginResponse;
import com.fredtm.sdk.api.AccountApi;
import com.fredtm.sdk.api.FredTmApi;
import com.fredtm.sdk.api.SyncApi;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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

	private Dialog<Void> waitDialog;

	@Override
	public void initialize(URL url, ResourceBundle bundle) {
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
		tabCreator = new MainControllerTabCreator(tabPane);
		tabPane.getTabs().clear();
	}

	@FXML
	void onExitClicked() {
		System.exit(0);
	}

	@FXML
	void onConfigureClicked() {
		createView("/fxml/configure.fxml", "Configurar");
	}

	@FXML
	void onImportApiClicked() {
		Optional<LoginDTO> loginInfo = getLoginInformations();
		if (!loginInfo.isPresent()) {
			return;
		}
		LoginDTO loginDTO = loginInfo.get();
		Properties apiProperties = PropertiesFolderUtil.getApiProperties();
		String ip = apiProperties.getProperty("s_ip");
		String port = apiProperties.getProperty("s_port");
		String endpoint = "https://" + ip + ":" + port + "/fredapi";
		showWaitDialog();
		AccountApi accountApi = new FredTmApi(endpoint).configAnnonymousAdapter().create(AccountApi.class);
		try {
			accountApi.loginAccount(loginDTO, new retrofit.Callback<LoginResponse>() {

				@Override
				public void success(LoginResponse login, Response response) {
					if (response.getStatus() == 200) {
						doLogin(endpoint, login, loginDTO);
					}
				}

				@Override
				public void failure(RetrofitError error) {
					if (error.getResponse() == null) {
						Platform.runLater(() -> closeWaitDialog());
						showInfrastructureError();
						return;
					}

					if (error.getResponse().getStatus() == 401) {
						showLoginError();
					} else {
						showInfrastructureError();
					}

					Platform.runLater(() -> closeWaitDialog());
				}
			});
		} catch (Exception e) {
			showInfrastructureError();
			closeWaitDialog();
		}

	}

	void doLogin(String endpoint, LoginResponse response, LoginDTO dto) {
		if (response != null) {
			SyncApi syncApi = new FredTmApi(endpoint, dto).configCompleteAdapter().create(SyncApi.class);

			syncApi.receiveSyncs(response.getAccountUuid(), new retrofit.Callback<OperationsDTO>() {

				@Override
				public void success(OperationsDTO operations, Response response) {
					List<Operation> entities = FredObjectMapper
							.mapResourcesToEntities(operations.getEmbedded().getOperationDTOList());
					Platform.runLater(() -> {
						closeWaitDialog();
						createOperationsWindow(entities);
					});
				}

				@Override
				public void failure(RetrofitError error) {
					showTransferError();
					closeWaitDialog();
				}
			});
		}
	}

	void showWaitDialog() {
		Platform.runLater(() -> {
			waitDialog = new Dialog<>();
			waitDialog.setTitle("Aguarde");
			waitDialog.setContentText("Aguarde enquanto o sistema processa os resultados.");
			waitDialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
			waitDialog.show();
		});
	}

	void closeWaitDialog() {
		if (waitDialog != null) {
			waitDialog.close();
			waitDialog = null;
		}
	}

	void showLoginError() {
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Login");
			alert.setHeaderText("Não autorizado.");
			alert.setContentText("Suas credenciais não estão autorizadas.");
			alert.showAndWait();
		});
		return;
	}

	void showInfrastructureError() {
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro de conexão.");
			alert.setContentText("Erro ao se conectar ao servidor.");
			alert.showAndWait();
		});
		return;
	}

	void showTransferError() {
		Platform.runLater(() -> {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Erro ao realizar transferência.");
			alert.setContentText("Houve um erro no processo de transferência de dados do servidor.");
			alert.showAndWait();
		});
	}

	Optional<LoginDTO> getLoginInformations() {
		Dialog<LoginDTO> loginDialog = new Dialog<>();

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		loginDialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		TextField textFieldEmail = new TextField();
		textFieldEmail.setPromptText("E-mail");
		PasswordField textFieldPassword = new PasswordField();
		textFieldPassword.setPromptText("Senha");

		grid.add(new Label("E-mail:"), 0, 0);
		grid.add(textFieldEmail, 1, 0);
		grid.add(new Label("Senha:"), 0, 1);
		grid.add(textFieldPassword, 1, 1);

		// Enable/Disable login button depending on whether a username was
		// entered.
		Node loginButton = loginDialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		textFieldEmail.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		textFieldPassword.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty() || newValue.length() < 6);
		});

		loginDialog.getDialogPane().setContent(grid);
		Platform.runLater(() -> textFieldEmail.requestFocus());

		loginDialog.setTitle("Login");

		loginDialog.setResultConverter(new Callback<ButtonType, LoginDTO>() {

			@Override
			public LoginDTO call(ButtonType param) {
				String email = textFieldEmail.getText();
				String password = textFieldPassword.getText();
				return new LoginDTO(password, email);
			}
		});
		return loginDialog.showAndWait();
	}

	@FXML
	void onImportJsonClicked() {
		FileChooser fc = new FileChooser();
		fc.setTitle("Escolha o arquivo \"operations.json\" gerado por seu aplicativo ou exportado por você.");
		fc.setSelectedExtensionFilter(
				new ExtensionFilter("Arquivos .json gerados por esse software ou pelo Fred TM (mobile)", "*.<json>"));
		File selectedItem = fc.showOpenDialog(getWindow());

		if (selectedItem != null) {
			OperationJsonUtils oju = new OperationJsonUtils();
			List<OperationDTO> operations = oju.jsonToJava(selectedItem);
			List<Operation> entities = FredObjectMapper.mapResourcesToEntities(operations);
			createOperationsWindow(entities);
		}
		fc = null;
	}

	@FXML
	void onSyncClicked() {
		actionSyncActive();
		syncSelected = !syncSelected;
	}

	private void actionSyncActive() {
		configureWindowAdapter();
		createTransferServer();
		Optional<BufferedImage> gerarQRCode = new SwingQRCodeGenerator().gerarQRCode();
		createJDialog(gerarQRCode.orElseThrow(IllegalStateException::new));
	}

	private void configureWindowAdapter() {
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
		List<OperationDTO> jsonToJava = utils.jsonToJava(jsonContent);
		List<Operation> operations = FredObjectMapper.mapResourcesToEntities(jsonToJava);
		createOperationsWindow(operations);
		jDialog.setVisible(false);
		jDialog = null;
	}

	private void createOperationsWindow(List<Operation> list) {
		Consumer<ProjectsController> consumer = c -> c.setOperacoes(list);
		createView("/fxml/projects.fxml", "Projetos", consumer);
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
		Consumer<CollectedTimesController> consumidor = c -> c.setCollect(collect);
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
					"Tempo/Classificação por coleta (ciclo): " + collects.get(0).getOperation().toString(),
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
			createView("/fxml/chart_time_activity.fxml", "Distribuição tempo/atividade: " + collect.toString(), chart);
			break;

		case TIME_BY_CLASSIFICATION:
			Consumer<TimeByClassificationController> barConsumer = c -> c.setCollect(collect);
			createView("/fxml/chart_line_classification.fxml", "Tempo/classificação: " + collect.toString(),
					barConsumer);
			break;

		case TIME_BY_SIMPLE_CLASSIFICATION:
			Consumer<TimeBySimplifiedClassificationController> barSimpleConsumer = c -> c.setCollect(collect);
			createView("/fxml/chart_line_classification_simple.fxml",
					"Tempo/classificação simples: " + collect.toString(), barSimpleConsumer);
			break;
		case TIME_ANALYSYS:
			Consumer<TimesChartController> timeConsumer = c -> c.setCollect(collect);
			createView("/fxml/chart_times.fxml", "Análises dos tempos: " + collect.toString(), timeConsumer);
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
