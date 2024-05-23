package br.com.livraria.dao;

import br.com.livraria.model.Livro;
import br.com.livraria.model.Usuario;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LivroDAO {

    public void cadastrarLivro(Livro livro) {
        String SQL = "INSERT INTO livros(nome, qtd, descricao, avaliacao, preco, IMAGEM_PRINCIPAL, IMAGEM2, IMAGEM3, IMAGEM4, IMAGEM5) VALUES(?,?,?,?,?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, livro.getNome());
            preparedStatement.setInt(2, livro.getQuantidade());
            preparedStatement.setString(3, livro.getDescricao());
            preparedStatement.setDouble(4, livro.getAvaliacao());
            preparedStatement.setDouble(5, livro.getPreco());
            preparedStatement.setString(6, livro.getImagemPrincipal());
            preparedStatement.setString(7, livro.getImagem2());
            preparedStatement.setString(8, livro.getImagem3());
            preparedStatement.setString(9, livro.getImagem4());
            preparedStatement.setString(10, livro.getImagem5());
            preparedStatement.executeUpdate();

            int idLivro;
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idLivro = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID do livro recém-inserido.");
                }
            }


            System.out.println("Cadastro realizado com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro no cadastro do livro: " + e.getMessage());
        }
    }



    public List<Livro> listarProdutos() {
        String SQL = "SELECT * FROM livros";

        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
            System.out.println("Sucesso na conexão com o banco de dados");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Livro> livros = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                int qtd = resultSet.getInt("qtd");
                String descricao = resultSet.getString("descricao");
                double avaliacao = resultSet.getDouble("avaliacao");
                double preco = resultSet.getDouble("preco");
                String imagemPrincipal = resultSet.getString("imagem_principal");
                String imagem2 = resultSet.getString("imagem2");
                String imagem3 = resultSet.getString("imagem3");
                String imagem4 = resultSet.getString("imagem4");
                String imagem5 = resultSet.getString("imagem5");

                Livro livro = new Livro(id, nome, qtd, preco, descricao, avaliacao,
                        imagemPrincipal, imagem2, imagem3, imagem4, imagem5);
                livros.add(livro);
            }

            System.out.println("Sucesso na consulta de livros");
            connection.close();
            return livros;
        } catch (SQLException e) {
            System.out.println("Falha na conexão com o banco de dados: " + e.getMessage());
            return Collections.emptyList();
        }
    }


}