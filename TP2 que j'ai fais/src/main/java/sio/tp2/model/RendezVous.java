package sio.tp2.model;

public class RendezVous {
    private String heureRdv;
    private String nomPatient;
    private String nomPathologie;

    public RendezVous(String heureRdv, String nomPatient, String nomPathologie) {
        this.heureRdv = heureRdv;
        this.nomPatient = nomPatient;
        this.nomPathologie = nomPathologie;
    }

    public String getHeureRdv() {
        return heureRdv;
    }

    public void setHeureRdv(String heureRdv) {
        this.heureRdv = heureRdv;
    }

    public String getNomPatient() {
        return nomPatient;
    }

    public void setNomPatient(String nomPatient) {
        this.nomPatient = nomPatient;
    }

    public String getNomPathologie() {
        return nomPathologie;
    }

    public void setNomPathologie(String nomPathologie) {
        this.nomPathologie = nomPathologie;
    }
}
