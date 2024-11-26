package fr.diginamic.spring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "VILLE")
public class Ville {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID auto-généré
    private int id;

    @NotNull
    @Size(min = 2, max = 50)
    @Column(length = 50, nullable = false)
    private String nom;

    @Min(1)
    @Column(nullable = false)
    private int nbHabitants;


    @ManyToOne
    @JoinColumn(name = "departement_id")
    private Departement departement;

    public Ville() {
    }

    public Ville(String nom, int nbHabitants) {
        this.nom = nom;
        this.nbHabitants = nbHabitants;
    }

    public Ville(String nom, int nbHabitants, Departement departement) {
        this.nom = nom;
        this.nbHabitants = nbHabitants;
        this.departement = departement;
    }

    /**
     * Getter
     *
     * @return id id
     */
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

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }
}
