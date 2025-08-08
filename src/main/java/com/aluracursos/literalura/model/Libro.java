

package com.aluracursos.literalura.model;

import jakarta.persistence.*;
import lombok.*;
import com.aluracursos.literalura.dto.DatosLibros;


@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name= "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(unique = true)
    private String titulo;

    private String idioma;

    private int descargas;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Libro(DatosLibros datos) {
        this.titulo = datos.titulo();
        this.idioma = datos.idiomas().get(0); // Tomando el primer lenguaje
        this.descargas = datos.numeroDeDescargas().intValue();
        if (!datos.autor().isEmpty()) {
            Autor autor = new Autor();
            autor.setNombre(datos.autor().get(0).nombre());
            autor.setNacimiento(datos.autor().get(0).fechaDeNacimiento());
            autor.setFallecimiento(datos.autor().get(0).fechaDeFallecimiento());
            this.autor = autor;
        }
    }


    @Override
    public String toString() {
        return "\nLibro [" +
                "id=" + id +
                ", Titulo='" + titulo + '\'' +
                ", Idioma='" + idioma + '\'' +
                ", Cant Descargas=" + descargas +
                ", Autor=" + autor  +"]";
    }


}