package fr.diginamic.spring.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "DEPARTEMENT")
public class Departement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "nom")
    private String nom;

    @OneToMany(mappedBy = "departement")//, cascade = CascadeType.ALL
    private Set<Ville> villes;

    {
        villes = new HashSet<Ville>();
    }

    public Departement() {
    }

    public Departement(String nom) {
        this.nom = nom;
    }

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

    public Set<Ville> getVilles() {
        return villes;
    }

    /**
     * Setter
     *
     * @param villes villes
     */
    public void setVilles(Set<Ville> villes) {
        this.villes = villes;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Departement{");
        sb.append("id=").append(id);
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", villes=").append(villes);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departement that = (Departement) o;
        return id == that.id && Objects.equals(nom, that.nom) && Objects.equals(villes, that.villes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, villes);
    }
}
