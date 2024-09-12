package sio.tp2;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sio.tp2.model.RendezVous;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class HelloController implements Initializable {
    @FXML
    private Spinner minSpinner;
    @FXML
    private DatePicker dateRdv;
    @FXML
    private TextField txtPatient;
    @FXML
    private TreeView tvPlanning;
    @FXML
    private Spinner heureSpinner;
    @FXML
    private ComboBox cboPathologie;
    @FXML
    private Button btnValider;
    private TreeMap<String, TreeMap<String, RendezVous>> monPlanning;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        monPlanning = new TreeMap<>();
        cboPathologie.getItems().addAll("Angine", "Gastro", "Covid", "Grippe");
        cboPathologie.getSelectionModel().selectFirst();

        SpinnerValueFactory spinnerValueFactoryHeure = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 19, 8, 1);
        heureSpinner.setValueFactory(spinnerValueFactoryHeure);
        SpinnerValueFactory spinnerValueFactoryMinute = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 45, 0, 15);
        minSpinner.setValueFactory(spinnerValueFactoryMinute);



    }

    public boolean RechercherRDv(String dateRdv, String heureRdv)
    {
        boolean trouve = false;
        if (monPlanning.containsKey(dateRdv))
        {
            if (monPlanning.get(dateRdv).containsKey(heureRdv)){
                trouve = true;
            }
        }
        return trouve;
    }

    @FXML
    public void btnValiderClicked(Event event) {
        if (txtPatient.getText().isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erreur de saisie");
            alert.setContentText("Veuillez saisir le nom du patient");
            alert.showAndWait();
        }

        TreeItem<String> root = new TreeItem<>("Mon planning");
        String dateDuRdv = dateRdv.getValue().toString();
        String heureChoisie = dateRdv.getValue().toString();
        String minuteChoisie = minSpinner.getValue().toString();
        String time = heureSpinner.getValue() + ":" + minSpinner.getValue() ;
        if (dateDuRdv.length()  == 1) //corrigé l'utilisateur a choisi 8h ou 9h
        {
            dateDuRdv = "0"+dateDuRdv;
        }
        if (minuteChoisie.length() ==1) //l'utilisateur a choisi 0
        {
            minuteChoisie = minuteChoisie+"0";
        }
        String heureRdv = dateDuRdv + ":" + minuteChoisie;
        String dateduRdv = dateRdv.getValue().toString();
        if (RechercherRDv(dateduRdv, heureRdv))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information");
            alert.setContentText("");
            alert.showAndWait();
        }
        else
        {
            RendezVous rendezVous = new RendezVous(time, txtPatient.getText(), cboPathologie.getSelectionModel().getSelectedItem().toString());

            if (!monPlanning.containsKey(dateDuRdv))
            {
                TreeMap<String, RendezVous> treeMapHeureRdv = new TreeMap<>();
                //treeMapHeureRdv.put(rendezVous.getHeureRdv(), rendezVous);
                //monPlanning.put(dateDuRdv,  treeMapHeureRdv); askip ça oblige à mettre un else
            }
            monPlanning.get(dateDuRdv).put(heureRdv, rendezVous);

            for (String date : monPlanning.keySet())
            {
                TreeItem<String> noeudDate = new TreeItem<>(dateDuRdv);


                TreeItem<String> noeudHeure = new TreeItem<>(rendezVous.getHeureRdv());
                TreeItem<String> noeudPatient = new TreeItem<>(rendezVous.getNomPatient());
                TreeItem<String> noeudMaladie = new TreeItem<>(rendezVous.getNomPathologie());

                root.getChildren().add(noeudDate);
                noeudDate.getChildren().add(noeudHeure);
                noeudHeure.getChildren().add(noeudPatient);
                noeudHeure.getChildren().add(noeudMaladie);

                root.setExpanded(true);
                noeudDate.setExpanded(true);
                noeudHeure.setExpanded(true);
//            for ()

            }
            tvPlanning.setRoot(root);
        }







    }
}