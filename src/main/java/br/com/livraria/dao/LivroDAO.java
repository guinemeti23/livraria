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
        String SQL = "INSERT INTO livros(nome, qtd, descricao, avaliacao, preco) VALUES(?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, livro.getNome());
            preparedStatement.setInt(2, livro.getQuantidade());
            preparedStatement.setString(3, livro.getDescricao());
            preparedStatement.setDouble(4, livro.getAvaliacao());
            preparedStatement.setDouble(5, livro.getPreco());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idLivro = generatedKeys.getInt(1);
                    insertImages(idLivro, livro.getImagens(), connection);
                } else {
                    throw new SQLException("Falha ao obter o ID do livro recém-inserido.");
                }
            }
            System.out.println("Cadastro realizado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro no cadastro do livro: " + e.getMessage());
        }
    }

    private void insertImages(int livroId, List<String> imagens, Connection connection) throws SQLException {
        String SQL = "INSERT INTO livro_imagens(livro_id, imagem) VALUES(?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            for (String url : imagens) {
                preparedStatement.setInt(1, livroId);
                preparedStatement.setString(2, url);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }




    public List<Livro> listarProdutos() {
        String SQL = "SELECT * FROM livros";
        String SQL_IMAGES = "SELECT imagem FROM livro_imagens WHERE livro_id = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            List<Livro> livros = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                int qtd = resultSet.getInt("qtd");
                String descricao = resultSet.getString("descricao");
                double avaliacao = resultSet.getDouble("avaliacao");
                double preco = resultSet.getDouble("preco");

                List<String> imagens = new ArrayList<>();
                try (PreparedStatement psImages = connection.prepareStatement(SQL_IMAGES)) {
                    psImages.setInt(1, id);
                    try (ResultSet rsImages = psImages.executeQuery()) {
                        while (rsImages.next()) {
                            imagens.add(rsImages.getString("imagem"));
                        }
                    }
                }

                Livro livro = new Livro(id, nome, qtd, preco, descricao, avaliacao, imagens);
                livros.add(livro);
            }

            System.out.println("Sucesso na consulta de livros");
            return livros;
        } catch (SQLException e) {
            System.out.println("Falha na conexão com o banco de dados: " + e.getMessage());
            return Collections.emptyList();
        }
    }


}