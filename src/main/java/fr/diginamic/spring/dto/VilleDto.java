package fr.diginamic.spring.dto;

public class VilleDto {
    private int id;
    private String nom;
    private int nbHabitants;
    private String codeDepartement;
    private String nomDepartement;

    public int getId() {
        return id;
    }

    /**
     * Setter
     *
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    /**
     * Setter
     *
     * @param nom nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbHabitants() {
        return nbHabitants;
    }

    /**
     * Setter
     *
     * @param nbHabitants nbHabitants
     */
    public void setNbHabitants(int nbHabitants) {
        this.nbHabitants = nbHabitants;
    }

    public String getCodeDepartement() {
        return codeDepartement;
    }

    /**
     * Setter
     *
     * @param codeDepartement codeDepartement
     */
    public void setCodeDepartement(String codeDepartement) {
        this.codeDepartement = codeDepartement;
    }

    public String getNomDepartement() {
        return nomDepartement;
    }

    /**
     * Setter
     *
     * @param nomDepartement nomDepartement
     */
    public void setNomDepartement(String nomDepartement) {
        this.nomDepartement = nomDepartement;
    }
}