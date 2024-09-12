package sio.tp2;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sio.tp2.model.RendezVous;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class HelloController implements Initializable {

    @FXML
    private DatePicker dtRdv;
    @FXML
    private Spinner<Integer> spHeureRdv;
    @FXML
    private TextField txtPatient;
    @FXML
    private TreeView<String> tvPlanning;
    @FXML
    private Spinner<Integer> spMinuteRdv;
    @FXML
    private ComboBox<String> cboPathologie;
    @FXML
    private Button btnValider;

    private TreeMap<String, TreeMap<String, RendezVous>> monPlanning;


    TreeItem root;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        monPlanning = new TreeMap<>();
        root = new TreeItem<>("Mon planning");
        tvPlanning.setRoot(root);

        cboPathologie.getItems().addAll("Angine", "Grippe", "Covid", "Gastro");
        cboPathologie.getSelectionModel().selectFirst();

        SpinnerValueFactory spinnerValueFactoryHeure = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 19, 8, 1);
        spHeureRdv.setValueFactory(spinnerValueFactoryHeure);

        SpinnerValueFactory spinnerValueFactoryMinute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 45, 0, 15);
        spMinuteRdv.setValueFactory(spinnerValueFactoryMinute);


    }

    @FXML
    public void btnValiderClicked(Event event) {
        if (txtPatient.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText("");
            alert.setContentText("Veuillez saisir le nom du patient");
            alert.showAndWait();
        } else if (dtRdv.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText("");
            alert.setContentText("Veuillez choisir une date");
            alert.showAndWait();
        } else {

            String heureChoisie = "";
            String minuteChoisie = "";
            heureChoisie = spHeureRdv.getValue().toString();
            minuteChoisie = spMinuteRdv.getValue().toString();

            //pour tjr heure a 2 chiffre donc pour choix soit 8h ou 9h
            if (heureChoisie.length() == 1) {
                heureChoisie = "0" + heureChoisie;
            }
            //utilisateur a choisi 0
            if (minuteChoisie.length() == 1) {
                minuteChoisie = minuteChoisie + "0";
            }
            String heureRdv = heureChoisie + ":" + minuteChoisie;
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateRdv = dateTimeFormatter.format(dtRdv.getValue());

            if (RechercherRdv(dateRdv, heureRdv)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("");
                alert.setContentText("Il existe deja un rdv a cette date et a cette heure");
                alert.showAndWait();
            } else {
                RendezVous monRendezVous = new RendezVous(spHeureRdv.getValue().toString(), txtPatient.getText(), cboPathologie.getSelectionModel().getSelectedItem().toString());


                if (!monPlanning.containsKey(dateRdv)) {
                    TreeMap<String, RendezVous> lesRendezVous = new TreeMap<>();
                    monPlanning.put(dateRdv, lesRendezVous);
                }
                monPlanning.get(dateRdv).put(heureRdv, monRendezVous);

                //Remplir table view

                root.getChildren().clear();

                for (String dtRdv : monPlanning.keySet()) {
                    TreeItem noeudDateRdv = new TreeItem<>(dtRdv);

                    for (String heureRDv : monPlanning.get(dtRdv).keySet()) {

                        TreeItem noeudHeureRdv = new TreeItem<>(heureRDv);
                        TreeItem noeudNomPatient = new TreeItem<>(monPlanning.get(dtRdv).get(heureRDv).getNomPatient());
                        TreeItem noeudNomPathologie = new TreeItem<>(monPlanning.get(dtRdv).get(heureRDv).getNomPathologie());

                        noeudHeureRdv.getChildren().add(noeudNomPatient);
                        noeudHeureRdv.getChildren().add(noeudNomPathologie);
                        noeudDateRdv.getChildren().add(noeudHeureRdv);
                        noeudDateRdv.setExpanded(true);
                        noeudHeureRdv.setExpanded(true);

                    }
                    root.getChildren().add(noeudDateRdv);

                }
                tvPlanning.setRoot(root);
            }
        }
    }

    public boolean RechercherRdv(String dateRdv, String heureRdv){
        boolean trouve = false;

        if (monPlanning.containsKey(dateRdv)){
            if (monPlanning.get(dateRdv).containsKey(heureRdv)){
                trouve = true;
            }
        }
        return trouve;
    }
}




