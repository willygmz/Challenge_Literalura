package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import com.aluracursos.literalura.model.Libro;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT a FROM Libro l JOIN l.autor a WHERE a.nacimiento <= :anio and a.fallecimiento >= :anio ")
    List<Autor> autoresVivosenAnio(Integer anio);

    List<Libro> findByIdioma(String idioma);

    List<Libro> findByTitulo(String titulo);


    }
