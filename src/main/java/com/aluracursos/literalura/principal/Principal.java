package com.aluracursos.literalura.principal;
import com.aluracursos.literalura.dto.Datos;
import com.aluracursos.literalura.dto.DatosLibros;
import com.aluracursos.literalura.model.Autor;
import com.aluracursos.literalura.model.Libro;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.*;

import static java.util.Arrays.stream;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repositorio;
    private List<Libro> libros;
    private List<Autor>autores;

    public Principal(LibroRepository repository) {
        this.repositorio = repository;
    }

    int opcion = -1;

    public void muestraElMenu() {
        do {
            System.out.println("\n📚 MENÚ PRINCIPAL 📚");
            System.out.println("Elija la opción a través de su número:\n");
            System.out.println("🔍 1 - Buscar libro por título");
            System.out.println("📖 2 - Listar libros registrados");
            System.out.println("🖋️ 3 - Listar autores registrados");
            System.out.println("🎂 4 - Listar autores vivos en un determinado año");
            System.out.println("🌐 5 - Listar libros por idioma(es/en)");
            System.out.println("📊 6 - Listar 10 libros más descargados");
            System.out.println("👤 7 - Buscar autor por nombre");
            System.out.println("🚪 0 - Salir");
            System.out.print("\nOpción: ");

            try {
                opcion = teclado.nextInt();
                teclado.nextLine(); // Limpiar el buffer
            } catch (InputMismatchException e) {
                System.out.println("\n⚠️ Entrada inválida. Usa solo números para las opciones.\n");
                teclado.nextLine(); //reiniciar
                continue; // Volver al inicio
            }
            switch (opcion) {
                case 1:
                    buscarTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosenXAnio();
                    break;
                case 5:
                    buscarLibroPorIdioma();
                    break;
                case 6:
                    listar10LibrosMasDescargados();
                    break;
                case 7:
                    buscarAutorPorNombre();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }

            System.out.println();

        } while (opcion != 0);
    }

    private void buscarAutorPorNombre() {
        System.out.println("Ingrese el nombre del autor que desea buscar");
        var nombreAutor = teclado.nextLine();
        var autoresEncontrados = repositorio.autorPorNombre(nombreAutor.toLowerCase());
        
        if (autoresEncontrados.isEmpty()) {
            System.out.println("Autor no encontrado");
        } else {
            System.out.println("Autores encontrados:");
            for (Autor autor : autoresEncontrados) {
                System.out.println(autor);
            }
        }
    }


    private void listar10LibrosMasDescargados() {
        System.out.println("Top 10 libros más descargados");
        var json = consumoApi.obtenerDatos(URL_BASE);
        var datos = conversor.obtenerDatos(json,Datos.class);
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);
    }

    private void buscarLibroPorIdioma() {
        System.out.println("e generará una estadisca basada en las descargas de libros en el idioma seleccionado. " +
                " Escribe el idioma en el que deseas buscar libros(es/en)  : ");
        var idioma = teclado.nextLine();

        libros = repositorio.findByIdioma(idioma);
        System.out.println("\n*** Libros encontrados en idioma " + idioma + " ***");
        libros.forEach(e-> System.out.println(e.toString()));

        DoubleSummaryStatistics est = libros.stream()
                .filter(d -> d.getDescargas() > 0)
                .collect(Collectors.summarizingDouble(Libro::getDescargas));
        
        System.out.println("\nEstadísticas de descargas:");
        System.out.println("Cantidad media de descargas: " + est.getAverage());
        System.out.println("Cantidad máxima de descargas: " + est.getMax());
        System.out.println("Cantidad mínima de descargas: " + est.getMin());
        System.out.println("Cantidad de registros evaluados para calcular las estadísticas: " + est.getCount());
    }
    

    private void listarAutoresVivosenXAnio() {
        System.out.println("Escribe el año que deseas buscar si un autor estaba vivo: ");
        try {
            int anioBuscado = Integer.parseInt(teclado.nextLine());
            autores = repositorio.autoresVivosenAnio(anioBuscado);

            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos en ese año.");
                return;
            }

            autores.forEach(autor -> {
                System.out.println("-".repeat(60));
                System.out.printf("Nombre: %s\n", autor.getNombre());
                System.out.printf("Año de nacimiento: %d\n", autor.getNacimiento());
                System.out.printf("Año de fallecimiento: %d\n", autor.getFallecimiento());
                System.out.println("-".repeat(60) + "\n");
            });
        } catch (NumberFormatException e) {
            System.out.println("Error: Por favor ingrese un año válido (número).");
        }
    }

    private void listarAutoresRegistrados() {
         autores = repositorio.findAll()
            .stream()
            .map(Libro::getAutor)
            .distinct()
            .collect(Collectors.toList());

        System.out.println("\n=== Listado de Autores Registrados===\n");
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos.");
            return;
        }

        autores.forEach(autor -> {
            System.out.println("-".repeat(60));
            System.out.printf("Nombre: %s\n", autor.getNombre());
            System.out.printf("Año de nacimiento: %d\n", autor.getNacimiento());
            System.out.printf("Año de fallecimiento: %d\n", autor.getFallecimiento());
            System.out.printf("Número de libros: %d\n", autor.getLibros().size());
            System.out.println("-".repeat(60) + "\n");
        });
    }

    private void listarLibrosRegistrados() {
        libros = repositorio.findAll();
        System.out.println("\n=== Listado de Libros Registrados===\n");
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
            return;
        }
        
        libros.stream()
            .forEach(libro -> {
                System.out.println("-".repeat(60));
                System.out.printf("ID: %d\n", libro.getId());
                System.out.printf("Título: %s\n", libro.getTitulo());
                System.out.printf("Autor: %s\n", libro.getAutor().getNombre());
                System.out.printf("Año de nacimiento: %d\n", libro.getAutor().getNacimiento());
                System.out.printf("Año de fallecimiento: %d\n", libro.getAutor().getFallecimiento());
                System.out.printf("Idioma: %s\n", libro.getIdioma());
                System.out.printf("Descargas: %d\n", libro.getDescargas());
                System.out.println("-".repeat(60) + "\n");
            });
    }


    public void buscarTitulo(){
        DatosLibros datos = getTitulo();
        if (datos != null) {
            List<Libro> librosExistentes = repositorio.findByTitulo(datos.titulo());
            if (!librosExistentes.isEmpty()) {
                System.out.println("El libro ya existe en la base de datos.");
                return;
            }
            Libro libro = new Libro(datos);
            repositorio.save(libro);
        }
    }

    private DatosLibros getTitulo() {
        //Busqueda de libros por titulo
        System.out.println("Ingrese el nombre del libro que desea buscar : ");
        var tituloLibro = teclado.nextLine();

        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("\n***Libro Encontrado*** 📚\n");

            DatosLibros libro = libroBuscado.get();
            System.out.println("Título: " + libro.titulo());
            System.out.println(" Autores:");
            libro.autor().forEach(a -> System.out.println("  • " + a.nombre()));
            System.out.println(" Idiomas:");
            libro.idiomas().forEach(idioma -> System.out.println("  • " + idioma));
            System.out.println("Número de descargas: " + libro.numeroDeDescargas()+"\n");
            return libro;
        } else {
            System.out.println("Libro no encontrado");
            return null;
        }
    }
}
