package fr.diginamic.spring.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "DEPARTEMENT")
public class Departement {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "departement")//, cascade = CascadeType.ALL
    private Set<Ville> villes;

    {
        villes = new HashSet<Ville>();
    }

    public Departement() {
    }

    public Departement(String name) {
        this.name = name;
    }

    public Departement(String code, String name) {
        this.code = code;
        this.name = name;
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

    public String getCode() {
        return code;
    }

    /**
     * Setter
     *
     * @param code code
     */
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    /**
     * Setter
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
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
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departement that = (Departement) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(villes, that.villes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, villes);
    }
}
