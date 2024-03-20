package br.com.livraria.dao;

import br.com.livraria.model.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



import java.sql.*;

public class UserDAO {
    public boolean verifyCredentials(Usuario user) {
        String SQL = "SELECT SENHA FROM USUARIO WHERE EMAIL = ?";
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, user.getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String senhaArmazenada = resultSet.getString("SENHA");
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                return encoder.matches(user.getSenha(), senhaArmazenada);
            }

            connection.close();
            return false;
        } catch (SQLException e) {
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



    public void cadastrarUsuario(Usuario user, String confirmacaoSenha) {

        if (!user.getSenha().equals(confirmacaoSenha)) {
            System.out.println("As senhas não coincidem. Tente novamente.");
            return;
        }

        if (!validarCPF(user.getCpf())) {
            System.out.println("CPF inválido. Tente novamente.");
            return;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaEncriptada = encoder.encode(user.getSenha());


        String SQL = "INSERT INTO USUARIO(EMAIL, SENHA, NOME, CPF, GRUPO, ATIVO) VALUES(?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, senhaEncriptada);
            preparedStatement.setString(3, user.getNome());
            preparedStatement.setString(4, user.getCpf());
            preparedStatement.setString(5, user.getGrupo());
            preparedStatement.setBoolean(6, true);
            preparedStatement.execute();

            System.out.println("Cadastro realizado com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro no cadastro do usuário: " + e.getMessage());
        }
    }

    private boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[.-]", ""); // Remove pontos e traços
        if ((cpf == null) || (cpf.length() != 11) || cpf.matches(cpf.charAt(0) + "{11}")) {
            return false;
        }

        int soma = 0;
        int resto;

        for (int i = 1; i <= 9; i++) {
            soma += Integer.parseInt(cpf.substring(i - 1, i)) * (11 - i);
        }

        resto = (soma * 10) % 11;

        if ((resto == 10) || (resto == 11)) {
            resto = 0;
        }

        if (resto != Integer.parseInt(cpf.substring(9, 10))) {
            return false;
        }

        soma = 0;

        for (int i = 1; i <= 10; i++) {
            soma += Integer.parseInt(cpf.substring(i - 1, i)) * (12 - i);
        }

        resto = (soma * 10) % 11;

        if ((resto == 10) || (resto == 11)) {
            resto = 0;
        }

        if (resto != Integer.parseInt(cpf.substring(10, 11))) {
            return false;
        }

        return true;
    }
}