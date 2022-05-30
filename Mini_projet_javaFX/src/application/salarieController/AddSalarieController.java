package application.salarieController;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.enteties.employe;
import application.enteties.entreprise;
import application.enteties.salarie;
import application.enteties.vendeur;

import application.srevice.employeService;
import application.srevice.entrepriseService;
import application.srevice.salarieService;
import application.srevice.vendeurService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AddSalarieController implements Initializable {
	@FXML 
	private TextArea tfDetails;
	@FXML
	private ChoiceBox cbEntreprise;
	@FXML
	private RadioButton re;
	@FXML 
	private RadioButton rbLemp;
	@FXML
	private RadioButton rbLvend;
	@FXML
	private RadioButton rv;
	@FXML
	private TextField tfMat;
	@FXML
	private TextField tfNom;
	@FXML
	private TextField tfEmail;
	@FXML
	private TextField tfDateRec;
	
	@FXML
	private TextField tfSalaireMin;
	@FXML
	private TextField tfSalaireMax;
	@FXML
	private TextField tfTHV;
	@FXML
	private TextField tfHVS;
	@FXML
	private Button addBtn;
	@FXML
	private Button updateBtn;
	@FXML
	private Button exportBtn;
	@FXML
	private Button importBtn;
	@FXML
	private Button removeBtn;
	@FXML
	private Button detailsBtn;
	@FXML
	private Button quitBtn;
	@FXML
	private Button catBtn;
	@FXML
	private Button dateBtn;
	@FXML
	private Button maxTauxBtn;
	@FXML
	private Button minSalaireBtn;
	@FXML
	private Button minMaxBtn;
	@FXML
	private TableView<salarie> table;
	@FXML
	private TableColumn<salarie, Integer> matriculeCol;
	@FXML
	private TableColumn<salarie, String> nomCol;
	@FXML
	private TableColumn<salarie, String> emailCol;
	@FXML
	private TableColumn<salarie, String> catCol;
	@FXML
	private TableColumn<salarie, Double> salaireCol;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		matriculeCol.setCellValueFactory(new PropertyValueFactory<>("matricule"));
		nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		catCol.setCellValueFactory(new PropertyValueFactory<>("cat"));
		salaireCol.setCellValueFactory(new PropertyValueFactory<>("salaireFix"));
		employeService es=new employeService();
		vendeurService vs=new vendeurService();
		entrepriseService ents=new entrepriseService();
		
		for(entreprise i:ents.findAll()) {
		cbEntreprise.getItems().add(i.getNomE()+"-"+i.getIdE());
		//cbEntreprise.setValue(i.getIdE());
		}
		table.getItems().addAll(es.findAll());
		table.getItems().addAll(vs.findAll());
		
	}
	
	public void addSalarie() {
		String str=(String) cbEntreprise.getSelectionModel().getSelectedItem();
		if(re.isSelected()) {
			employe  emp = new employe(Integer.parseInt(tfMat.getText()), tfNom.getText(), tfEmail.getText(),"employe",Double.parseDouble(tfDateRec.getText()),Integer.parseInt(str.substring(str.indexOf("-")+1)), 0,Double.parseDouble(tfTHV.getText()), Double.parseDouble(tfHVS.getText()));
			employeService es = new employeService();
			es.createEmploye(emp);
			table.getItems().add(es.findEmployeById(emp.getMatricule()));
		} else if(rv.isSelected()) {
			vendeur v = new vendeur(Integer.parseInt(tfMat.getText()), tfNom.getText(), tfEmail.getText(),"vendeur",Double.parseDouble(tfDateRec.getText()), Integer.parseInt(str.substring(str.indexOf("-")+1)),0,Double.parseDouble(tfTHV.getText()), Double.parseDouble(tfHVS.getText()));
			vendeurService vs = new vendeurService();
			vs.createVendeur(v);
			table.getItems().add(vs.findVendeurById(v.getMatricule()));
		}
	
		
	}
	public void supprimer() {
		if(table.getSelectionModel().getSelectedItem().getCat().equals("employe")) {
			employeService es=new employeService();
			es.deleteEmploye(es.findEmployeById(table.getSelectionModel().getSelectedItem().getMatricule()));
			table.getItems().remove(table.getSelectionModel().getSelectedItem());
		}else
		if(table.getSelectionModel().getSelectedItem().getCat().equals("vendeur")){
			vendeurService vs=new vendeurService();
			vs.deleteVendeur(vs.findVendeurById(table.getSelectionModel().getSelectedItem().getMatricule()));
			table.getItems().remove(table.getSelectionModel().getSelectedItem());
		}
		
	}
	
	public void update() {
		String str=(String) cbEntreprise.getSelectionModel().getSelectedItem();
		if(re.isSelected()) {
			employe  emp = new employe(Integer.parseInt(tfMat.getText()), tfNom.getText(), tfEmail.getText(),"employe",Double.parseDouble(tfDateRec.getText()),Integer.parseInt(str.substring(str.indexOf("-")+1)), 0.1,Double.parseDouble(tfTHV.getText()), Double.parseDouble(tfHVS.getText()));
			employeService es = new employeService();
			es.updateEmploye(emp);
			table.getItems().clear();
			table.getItems().addAll(es.findAll());
		} else if(rv.isSelected()) {
			vendeur v = new vendeur(Integer.parseInt(tfMat.getText()), tfNom.getText(), tfEmail.getText(),"vendeur",Double.parseDouble(tfDateRec.getText()), Integer.parseInt(str.substring(str.indexOf("-")+1)),0.1,Double.parseDouble(tfTHV.getText()), Double.parseDouble(tfHVS.getText()));
			vendeurService vs = new vendeurService();
			vs.updateVendeur(v);
			table.getItems().clear();
			table.getItems().addAll(vs.findAll());
		}
	
		
	}
	
	public void listCat() {
		employeService  es=new employeService();
		vendeurService vs=new vendeurService();
		table.getItems().clear();
		if (rbLemp.isSelected()) {
			table.getItems().addAll(es.findAll());
		}else 
			if(rbLvend.isSelected()) {
			table.getItems().addAll(vs.findAll());
		}
	}
	
	public void details() {
		if(table.getSelectionModel().getSelectedItem().getCat().equals("employe")) {
			employeService es=new employeService();
			employe emp=es.findEmployeById(table.getSelectionModel().getSelectedItem().getMatricule());
			tfDetails.setText(emp.toString());
		}else
		if(table.getSelectionModel().getSelectedItem().getCat().equals("vendeur")){
			vendeurService vs=new vendeurService();
			vendeur vend=vs.findVendeurById(table.getSelectionModel().getSelectedItem().getMatricule());
			tfDetails.setText(vend.toString());
		}
	}
	
	public void listerMinMax() {
		salarieService sv=new salarieService();
		List<salarie> listSalMinMax=sv.betweenSal(Double.parseDouble(tfSalaireMin.getText()),Double.parseDouble(tfSalaireMax.getText()));
		table.getItems().clear();
		table.getItems().addAll(listSalMinMax);
		}
	
	public void listAnciennete() {
		salarieService sv=new salarieService();
		List<salarie> listAnciennete=sv.anciennete();
		table.getItems().clear();
		table.getItems().addAll(listAnciennete);
	}
	
	public void listerMaxTauxVente() {
		vendeurService vs=new vendeurService();
		List<vendeur> Lmtv=vs.maxTauxVente();
		table.getItems().clear();
		table.getItems().addAll(Lmtv);
	}
	
	public void salaireFaible() {
		salarieService sv=new salarieService();
		List<salarie> listFaibleSalaire=sv.salaireFaible();
		table.getItems().clear();
		table.getItems().addAll(listFaibleSalaire);
	}
	
	public void exporter() {
		//System.out.println(cbEntreprise.getSelectionModel().getSelectedItem());
		//data.setExportList(table.getItems());
	}

	public void exit() {
		System.exit(0);
	}


}
