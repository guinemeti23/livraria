package br.com.livraria.dao;

import br.com.livraria.model.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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



    public boolean isAdmin(Usuario user) {
        String SELECT_SQL = "SELECT GRUPO FROM USUARIO WHERE EMAIL = ? ";

        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SQL);
            preparedStatement.setString(1, user.getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String grupo = resultSet.getString("GRUPO");
                if (grupo.equalsIgnoreCase("administrador")) {
                    return true;
                } else if (grupo.equalsIgnoreCase("estoquista")) {
                    return false;
                }
            }
            connection.close();
            return false;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }


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


    public void alternarUsuario(Usuario user, String confirmacaoSenha) {

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


        String SQL = "update usuario set nome=?, cpf=?, senha=?, grupo=?  where email=?";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, user.getNome());
            preparedStatement.setString(2, user.getCpf());
            preparedStatement.setString(3, senhaEncriptada);
            preparedStatement.setString(4, user.getGrupo());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.execute();

            System.out.println("alteraçao realizada com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro na alteraçao do usuário: " + e.getMessage());
        }
    }


    public boolean atualizarStatusUsuario(int userId, boolean novoStatus) {
        String SQL = "UPDATE usuario SET ativo = ? WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setBoolean(1, novoStatus);
            preparedStatement.setInt(2, userId);

            int rowsUpdated = preparedStatement.executeUpdate();

            connection.close();

            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o status do usuário: " + e.getMessage());
            return false;
        }
    }

    public List<Usuario> listarUsuarios() {
        String SQL = "SELECT * FROM USUARIO";

        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
            System.out.println("success in database connection");
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Usuario> usuarios = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String cpf = resultSet.getString("cpf");
                String email = resultSet.getString("email");
                String grupo = resultSet.getString("grupo");
                boolean status = resultSet.getBoolean("ativo");

                Usuario usuario = new Usuario(id, nome, cpf, email, grupo, status);
                usuarios.add(usuario);
            }


            System.out.println("success in select * usuario ");
            connection.close();
            return usuarios;
        } catch (Exception e) {
            System.out.println("fail in database connection");
            return Collections.emptyList();
        }
    }


    public List<Usuario> listarUsuariosPorNome(String nome) {
        List<Usuario> usuarios = new ArrayList<>();

        String SQL = "SELECT * FROM usuario WHERE nome LIKE ?";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, "%" + nome + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(resultSet.getInt("id"));
                    usuario.setNome(resultSet.getString("nome"));
                    usuario.setEmail(resultSet.getString("email"));
                    usuario.setAtivo(resultSet.getBoolean("ativo"));
                    usuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    public List<Usuario> listarUsuariosPorID(int id) {
        List<Usuario> usuarios = new ArrayList<>();

        String SQL = "SELECT * FROM usuario WHERE id LIKE ?";

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");
             PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {

            preparedStatement.setString(1, "%" + id + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(resultSet.getInt("id"));
                    usuario.setNome(resultSet.getString("nome"));
                    usuario.setCpf(resultSet.getString("cpf"));
                    usuario.setEmail(resultSet.getString("email"));
                    usuario.setGrupo(resultSet.getString("grupo"));

                    usuarios.add(usuario);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuarios;
    }



}