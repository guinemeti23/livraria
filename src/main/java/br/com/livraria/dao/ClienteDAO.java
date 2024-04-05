package br.com.livraria.dao;

import java.sql.*;

import br.com.livraria.model.Cliente;
import br.com.livraria.model.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ClienteDAO {


    public boolean verifyCredentials(Cliente cliente) {
        String SQL = "SELECT SENHA FROM CLIENTE WHERE EMAIL = ?";
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setString(1, cliente.getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String senhaArmazenada = resultSet.getString("SENHA");
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                return encoder.matches(cliente.getSenha(), senhaArmazenada);
            }

            connection.close();
            return false;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public void cadastrarCliente(Cliente cliente, String confirmacaoSenha) {
        if (!cliente.getSenha().equals(confirmacaoSenha)) {
            System.out.println("As senhas não coincidem. Tente novamente.");
            return;
        }

        if (!validarCPF(cliente.getCpf())) {
            System.out.println("CPF inválido. Tente novamente.");
            return;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaEncriptada = encoder.encode(cliente.getSenha());

        String SQL = "INSERT INTO CLIENTE(NOMECOMPLETO, CPF, EMAIL, SENHA, DATANASCIMENTO, GENERO, ENDERECOFATURAMENTO) VALUES(?,?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, cliente.getNomeCompleto());
            preparedStatement.setString(2, cliente.getCpf());
            preparedStatement.setString(3, cliente.getEmail());
            preparedStatement.setString(4, senhaEncriptada);
            preparedStatement.setDate(5, new java.sql.Date(cliente.getDataNascimento().getTime()));
            preparedStatement.setString(6, cliente.getGenero());
            preparedStatement.setString(7, cliente.getEnderecoFaturamento());
            preparedStatement.execute();

            System.out.println("Cadastro realizado com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro no cadastro do cliente: " + e.getMessage());
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

    public void alternarCliente(Cliente cliente, String confirmacaoSenha) {
        if (!cliente.getSenha().equals(confirmacaoSenha)) {
            System.out.println("As senhas não coincidem. Tente novamente.");
            return;
        }

        if (!validarCPF(cliente.getCpf())) {
            System.out.println("CPF inválido. Tente novamente.");
            return;
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senhaEncriptada = encoder.encode(cliente.getSenha());

        String SQL = "UPDATE CLIENTE SET NOMECOMPLETO=?, CPF=?, SENHA=?, DATANASCIMENTO=?, GENERO=?, ENDERECOFATURAMENTO=? WHERE EMAIL=?";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, cliente.getNomeCompleto());
            preparedStatement.setString(2, cliente.getCpf());
            preparedStatement.setString(3, senhaEncriptada);
            preparedStatement.setDate(4, new java.sql.Date(cliente.getDataNascimento().getTime()));
            preparedStatement.setString(5, cliente.getGenero());
            preparedStatement.setString(6, cliente.getEnderecoFaturamento());
            preparedStatement.setString(7, cliente.getEmail());
            preparedStatement.execute();

            System.out.println("Alteração realizada com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro na alteração do cliente: " + e.getMessage());
        }
    }

}
