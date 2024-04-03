package br.com.livraria.dao;

import br.com.livraria.model.Livro;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LivroDAO {

    public void cadastrarLivro(Livro livro) {
        String SQL = "INSERT INTO livros(nome, qtd, descricao, avaliacao, preco) VALUES(?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, livro.getNome());
            preparedStatement.setInt(2, livro.getQuantidade());
            preparedStatement.setString(3, livro.getDescricao());
            preparedStatement.setDouble(4, livro.getAvaliacao());
            preparedStatement.setDouble(5, livro.getPreco());
            preparedStatement.executeUpdate();

            int idLivro;
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idLivro = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID do livro recém-inserido.");
                }
            }

            for (String imagem : livro.getImagens()) {
                cadastrarImagemLivro(connection, idLivro, imagem);
            }

            System.out.println("Cadastro realizado com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro no cadastro do livro: " + e.getMessage());
        }
    }

    private void cadastrarImagemLivro(Connection connection, int idLivro, String imagem) {
        String SQL = "INSERT INTO livro_imagens(livro_id, imagem) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, idLivro);
            preparedStatement.setString(2, imagem);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro no cadastro da imagem do livro: " + e.getMessage());
        }
    }

    public void listarProdutos() {
        String SQL = "SELECT codigo, nome, qtd, preco, status FROM livros";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            StringBuilder htmlOutput = new StringBuilder();

            while (resultSet.next()) {
                String codigo = resultSet.getString("codigo");
                String nome = resultSet.getString("nome");
                int quantidade = resultSet.getInt("qtd");
                double preco = resultSet.getDouble("preco");
                String status = resultSet.getString("status");

                htmlOutput.append("<div class=\"livro\">")
                        .append("<p>Código: ").append(codigo).append("</p>")
                        .append("<p>Nome: ").append(nome).append("</p>")
                        .append("<p>Quantidade: ").append(quantidade).append("</p>")
                        .append("<p>Valor: R$ ").append(String.format("%.2f", preco)).append("</p>")
                        .append("<p>Status: ").append(status).append(" <a href=\"#\"><i class=\"fas fa-edit\"></i> Alterar</a>")
                        .append(" <a href=\"#\"><i class=\"fas fa-ban\"></i> Inativar</a>")
                        .append(" <a href=\"#\"><i class=\"fas fa-sync-alt\"></i> Reativar</a>")
                        .append(" <a href=\"#\"><i class=\"fas fa-eye\"></i> Visualizar</a></p>")
                        .append("</div>");
            }

            System.out.println(htmlOutput.toString());

        } catch (SQLException e) {
            System.out.println("Erro ao listar os produtos: " + e.getMessage());
        }
    }

}