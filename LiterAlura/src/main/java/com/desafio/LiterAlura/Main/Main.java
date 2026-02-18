package com.desafio.LiterAlura.Main;

import com.desafio.LiterAlura.Model.*;
import com.desafio.LiterAlura.Service.ConverteDados;
import com.desafio.LiterAlura.Service.ObterDadosApi;
import com.desafio.LiterAlura.repository.AutorRepository;
import com.desafio.LiterAlura.repository.DadosLivroRepository;
import java.util.*;

public class Main {

    private ConverteDados conversor = new ConverteDados();
    private Scanner scanner = new Scanner(System.in);
    private ObterDadosApi dadosApi = new ObterDadosApi();
    private AutorRepository autorRepository;
    private DadosLivroRepository dadosLivroRepository;

    public Main(AutorRepository repository, DadosLivroRepository dadosLivroRepository) {
        this.autorRepository = repository;
        this.dadosLivroRepository = dadosLivroRepository;
    }

    public void ExibirMenu() {

        var auxiliar = -1;

        while (auxiliar != 0) {
            var menu = """
                    1 - Buscar livro pelo título.
                    2 - Listar livros registrados.
                    3 - Listar autores registrados.
                    4 - Listar autores vivos em um determinado ano.
                    5 - Listar livros em um determinado idioma;
                    
                    0 - sair.
                    
                    """;

            System.out.println(menu);
            auxiliar = scanner.nextInt();
            scanner.nextLine();

            switch (auxiliar) {
                case 0:
                    auxiliar = 0;
                    break;
                case 1:
                    buscarLivroApi();
                    break;
                case 2:
                    ListagemLivros();
                    break;
                case 3:
                    ListagemAutores();
                    break;
                case 4:
                    BuscaAutorPeloAno();
                    break;
                case 5:
                    BuscarLivroIdioma();
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }



    private void buscarLivroApi() {
        var dados = getDadosLivro();
        RecordLivro dadosLivro = dados.resultados().getFirst();
        RecordAutor dadosAutorr = dadosLivro.autor().getFirst();


        Optional<Autor> autorExistente = autorRepository.findByNomeAutor(dadosAutorr.nome());
        Autor autor;
        if (autorExistente.isPresent()) {
            autor = autorExistente.get();
            System.out.println("Autor já existe no banco!");
        } else {
            autor = new Autor(dadosAutorr);
            autorRepository.save(autor);
            System.out.println("Autor salvo no banco!");
        }

        Optional<DadosLivro> LivroExistente = dadosLivroRepository.findByTitulo(dadosLivro.titulo());
        DadosLivro livro;
        if (LivroExistente.isPresent()) {
            System.out.println("Já adicionado '" + dadosLivro.titulo() + "' no banco de dados");
        } else {
            livro = new DadosLivro(dadosLivro);
            livro.setAutor(autor);
            dadosLivroRepository.save(livro);
            System.out.println("Livro salvo com sucesso");
        }
    }

    private RecordResults getDadosLivro() {
        System.out.println("Digite o nome do livro que deseja buscar");
        var livroSelecionado = scanner.nextLine();
        if (livroSelecionado.isEmpty()) {
            System.out.println("Por favor digite um nome de um livro");
            return getDadosLivro();
        } else {
            var json = dadosApi.ObterDadosDaApi("https://gutendex.com/books/?search=" + livroSelecionado.replace(" ", "+"));
            RecordResults dados = conversor.obterDados(json, RecordResults.class);
            if (dados.resultados().isEmpty()) {
                System.out.println("Nenhum livro encotrado com esse nome");
                return getDadosLivro();
            }
            return dados;
        }
    }

    private void ListagemLivros() {
        List<DadosLivro> dadosLivros;
        dadosLivros = dadosLivroRepository.findAll();
        System.out.println("\n" + "-----LISTA COMPLETA LIVROS-----" + "\n");
        dadosLivros.forEach(System.out::println);
    }

    private void ListagemAutores () {
        List <Autor> autores = autorRepository.findAll();
        System.out.println("\n" + "-----LISTA COMPLETA DE AUTORES-----" + "\n");
        autores.forEach(autor -> {
            System.out.println("Nome autor: " + autor.getNomeAutor());
            System.out.println("Ano de nascimento: " + autor.getDataNascimento());
            System.out.println("Ano de falecimento: " + autor.getDataFalecimento());
            System.out.println("Livros: " + dadosLivroRepository.findTitulosByAutorId(autor.getId()) + "\n");
            System.out.println("----------------------------------------" + "\n");
        });
    }

    private void BuscaAutorPeloAno() {
        System.out.println("Digite o ano em que deseja pesquisar os autores vivos");
        var anoEscolhido = scanner.nextInt();
        scanner.nextLine();
        List<Autor> autores = autorRepository.buscarAutorPeloAno(anoEscolhido);

        System.out.println("\n" + "-----AUTORES VIVOS EM " + anoEscolhido + " -----" + "\n");
        autores.forEach(autor -> {
            System.out.println("Nome autor: " + autor.getNomeAutor());
            System.out.println("Ano de nascimento: " + autor.getDataNascimento());
            System.out.println("Ano de falecimento: " + autor.getDataFalecimento());
            System.out.println("Livros: " + dadosLivroRepository.findTitulosByAutorId(autor.getId()) + "\n");
            System.out.println("----------------------------------------" + "\n");
        });
    }

    private void BuscarLivroIdioma() {
        System.out.println("""
                
                Insira o idioma em que deseja fazer a busca
                
                PT - Português
                FR - Francês
                EN - Inglês
                ES - Espanhol
                """);

        var idiomaEscolhido = scanner.nextLine();

        if (idiomaEscolhido.isEmpty()) {
            System.out.println("Por favor, digite uma opção válida");
            BuscarLivroIdioma();
        } else {
            List<DadosLivro> dadosLivros;
            dadosLivros = dadosLivroRepository.buscarLivroIdioma(idiomaEscolhido);
            System.out.println("\n" + "-----LIVROS-----" + "\n");
            dadosLivros.forEach(System.out::println);
        }
    }
}
