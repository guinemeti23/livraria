package br.com.livraria.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.livraria.model.Cliente;
import br.com.livraria.model.Endereco;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServlet;

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

    public void cadastrarCliente(Cliente cliente, String confirmacaoSenha, Endereco endereco) {
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

        String SQL_INSERT_ENDERECO = "INSERT INTO Enderecos (CEP, Logradouro, Numero, Complemento, Bairro, Cidade, UF, ClienteID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String SQL_INSERT_CLIENTE = "INSERT INTO Cliente (NomeCompleto, CPF, Email, Senha, DataNascimento, Genero) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement pstmtEndereco = connection.prepareStatement(SQL_INSERT_ENDERECO);
             PreparedStatement pstmtCliente = connection.prepareStatement(SQL_INSERT_CLIENTE, Statement.RETURN_GENERATED_KEYS)) {

            // Inserir o cliente
            pstmtCliente.setString(1, cliente.getNomeCompleto());
            pstmtCliente.setString(2, cliente.getCpf());
            pstmtCliente.setString(3, cliente.getEmail());
            pstmtCliente.setString(4, senhaEncriptada);
            pstmtCliente.setDate(5, new java.sql.Date(cliente.getDataNascimento().getTime()));
            pstmtCliente.setString(6, cliente.getGenero());
            pstmtCliente.executeUpdate();

            // Obter o ID do cliente inserido
            ResultSet rs = pstmtCliente.getGeneratedKeys();
            int clienteID = 0;
            if (rs.next()) {
                clienteID = rs.getInt(1);
            }

            // Inserir o endereço com o ID do cliente
            pstmtEndereco.setString(1, endereco.getCep());
            pstmtEndereco.setString(2, endereco.getLogradouro());
            pstmtEndereco.setString(3, endereco.getNumero());
            pstmtEndereco.setString(4, endereco.getComplemento());
            pstmtEndereco.setString(5, endereco.getBairro());
            pstmtEndereco.setString(6, endereco.getLocalidade());
            pstmtEndereco.setString(7, endereco.getUf());
            pstmtEndereco.setInt(8, clienteID);
            pstmtEndereco.executeUpdate();

            System.out.println("Cadastro realizado com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro no cadastro do cliente: " + e.getMessage());
        }
    }



    private boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[.-]", "");
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

        String SQL = "UPDATE CLIENTE SET NOMECOMPLETO=?, CPF=?, SENHA=?, DATANASCIMENTO=?, GENERO=? WHERE EMAIL=?";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, cliente.getNomeCompleto());
            preparedStatement.setString(2, cliente.getCpf());
            preparedStatement.setString(3, senhaEncriptada);
            preparedStatement.setDate(4, new java.sql.Date(cliente.getDataNascimento().getTime()));
            preparedStatement.setString(5, cliente.getGenero());
            preparedStatement.setString(6, cliente.getEmail());
            preparedStatement.execute();

            System.out.println("Alteração realizada com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro na alteração do cliente: " + e.getMessage());
        }
    }

    public void cadastrarEndereco(Endereco endereco) {
        String SQL = "INSERT INTO Enderecos (CLIENTEID, CEP, Logradouro, Numero, Complemento, Bairro, Cidade, UF) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setInt(1, endereco.getClienteId());
            preparedStatement.setString(2, endereco.getCep());
            preparedStatement.setString(3, endereco.getLogradouro());
            preparedStatement.setString(4, endereco.getNumero());
            preparedStatement.setString(5, endereco.getComplemento());
            preparedStatement.setString(6, endereco.getBairro());
            preparedStatement.setString(7, endereco.getLocalidade());
            preparedStatement.setString(8, endereco.getUf());
            preparedStatement.executeUpdate();

            System.out.println("Cadastro realizado com sucesso.");
        } catch (SQLException e) {
            System.out.println("Erro no cadastro do cliente: " + e.getMessage());
        }
    }


    public List<Cliente> listarClientePorEmail(String email) {
        List<Cliente> clientes = new ArrayList<>();

        String SQL = "SELECT * FROM CLIENTE WHERE Email LIKE ?";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, "%" + email + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(resultSet.getInt("ID"));
                    cliente.setNomeCompleto(resultSet.getString("NOMECOMPLETO"));
                    cliente.setCpf(resultSet.getString("CPF"));
                    cliente.setEmail(resultSet.getString("EMAIL"));
                    cliente.setSenha(resultSet.getString("SENHA"));
                    cliente.setDataNascimento(resultSet.getDate("DATANASCIMENTO"));
                    cliente.setGenero(resultSet.getString("GENERO"));
                    clientes.add(cliente);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    public List<Endereco> listarEndereco(int idCliente) {
        List<Endereco> enderecos = new ArrayList<>();

        String SQL = "SELECT * FROM ENDERECOS WHERE CLIENTEID LIKE ?";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setInt(1, idCliente);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Endereco endereco = new Endereco();
                    endereco.setEnderecoId(resultSet.getInt("ENDERECOID"));
                    endereco.setLogradouro(resultSet.getString("LOGRADOURO"));
                    endereco.setNumero(resultSet.getString("NUMERO"));
                    endereco.setComplemento(resultSet.getString("COMPLEMENTO"));
                    endereco.setBairro(resultSet.getString("BAIRRO"));
                    endereco.setLocalidade(resultSet.getString("CIDADE"));
                    endereco.setUf(resultSet.getString("UF"));
                    endereco.setCep(resultSet.getString("CEP"));
                    enderecos.add(endereco);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return enderecos;
    }

    public static Endereco listarEnderecoEntrega(int enderecoId) {
        String SQL = "SELECT * FROM ENDERECOS WHERE ENDERECOID = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setInt(1, enderecoId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Endereco endereco = new Endereco();
                    endereco.setLogradouro(resultSet.getString("LOGRADOURO"));
                    endereco.setNumero(resultSet.getString("NUMERO"));
                    endereco.setComplemento(resultSet.getString("COMPLEMENTO"));
                    endereco.setBairro(resultSet.getString("BAIRRO"));
                    endereco.setLocalidade(resultSet.getString("CIDADE"));
                    endereco.setUf(resultSet.getString("UF"));
                    endereco.setCep(resultSet.getString("CEP"));
                    return endereco;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Retorna null se não encontrar o endereço ou ocorrer erro
    }


}
