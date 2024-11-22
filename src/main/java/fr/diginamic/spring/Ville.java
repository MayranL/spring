package fr.diginamic.spring;

public class Ville {
    private String nom;
    private String nbHabitants;

    public Ville(String nom, String nbHabitants) {
        this.nom = nom;
        this.nbHabitants = nbHabitants;
    }

    /**
     * Getter
     *
     * @return nom nom
     */
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

    /**
     * Getter
     *
     * @return nbHabitants nbHabitants
     */
    public String getNbHabitants() {
        return nbHabitants;
    }

    /**
     * Setter
     *
     * @param nbHabitants nbHabitants
     */
    public void setNbHabitants(String nbHabitants) {
        this.nbHabitants = nbHabitants;
    }
}
