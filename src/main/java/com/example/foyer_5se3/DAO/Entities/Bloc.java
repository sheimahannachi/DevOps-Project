package com.example.foyer_5se3.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
        import jakarta.persistence.*;
        import lombok.*;
        import lombok.experimental.FieldDefaults;
        import java.util.ArrayList;
        import java.util.List;

@Entity
@Table(name = "T_BLOC")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bloc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idBloc;
    String nomBloc;
    long capaciteBloc;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "foyer_id", referencedColumnName = "idFoyer")
     Foyer foyer;
    @OneToMany(mappedBy = "bloc", fetch = FetchType.EAGER)
    @JsonIgnore
    List<Chambre> chambres= new ArrayList<>();
}