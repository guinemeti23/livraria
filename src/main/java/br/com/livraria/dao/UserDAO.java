package br.com.livraria.dao;

import br.com.livraria.model.Usuario;

import java.sql.*;

public class UserDAO {
    public boolean verifyCredentials(Usuario user) {
        String SQL = "SELECT * FROM USUARIO WHERE EMAIL = ?";

        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");

            System.out.println("success in database connection");

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, user.getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("success in select username");

            while (resultSet.next()) {
                String senha = resultSet.getString("senha");

                if (senha.equals(user.getSenha())) {
                    return true;
                }
            }

            connection.close();

            return false;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }


    public String getUserType(Usuario user) {
        String SELECT_SQL = "SELECT GRUPO FROM USUARIO WHERE EMAIL = ? ";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL)) {

            preparedStatement.setString(1, user.getEmail());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("GRUPO");
                }
            }

        } catch (Exception e) {
            System.out.println("Error in getUserType: " + e.getMessage());
        }

        return null;
    }



    public void Cadastro(Usuario user){

        String SQL = "INSERT INTO USUARIO( EMAIL, SENHA, NOME, CPF, GRUPO) VALUES(?,?,?,?,?)";

        try {

            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");

            System.out.println("success in database connection");

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getSenha());
            preparedStatement.setString(3, user.getNome());
            preparedStatement.setString(4, user.getCpf());
            preparedStatement.setString(5, user.getGrupo());
            preparedStatement.setBoolean(6, user.isAtivo());
            preparedStatement.execute();

            System.out.println("success a insert a user");

            connection.close();

        } catch (Exception e) {

            System.out.println("fail in database connection");
            System.out.println("Error: " + e.getMessage());

        }

    }

}