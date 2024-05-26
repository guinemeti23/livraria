package br.com.livraria.dao;

import br.com.livraria.model.ItemCarrinho;
import br.com.livraria.model.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    private static final String URL = "jdbc:h2:~/test";
    private static final String USER = "sa";
    private static final String PASSWORD = "sa";

    public void adicionarPedido(Pedido pedido) throws SQLException {
        String sqlPedido = "INSERT INTO Pedidos (clienteId, enderecoId, formaDePagamento, frete, valorTotal, data, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement itemStatement = null;

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, pedido.getClienteId());
            preparedStatement.setInt(2, pedido.getEnderecoId());
            preparedStatement.setString(3, pedido.getFormaDePagamento());
            preparedStatement.setString(4, pedido.getFrete());
            preparedStatement.setDouble(5, pedido.getValorTotal());
            preparedStatement.setDate(6, new java.sql.Date(pedido.getData().getTime()));
            preparedStatement.setString(7, pedido.getStatus());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int pedidoId = generatedKeys.getInt(1);
                String sqlItem = "INSERT INTO ItensPedido (pedidoId, produtoId, nomeProduto, precoUnitario, quantidade) VALUES (?, ?, ?, ?, ?)";
                itemStatement = connection.prepareStatement(sqlItem);
                for (ItemCarrinho item : pedido.getItens()) {
                    itemStatement.setInt(1, pedidoId);
                    itemStatement.setInt(2, item.getLivroId());
                    itemStatement.setString(3, item.getLivroNome());
                    itemStatement.setDouble(4, item.getLivroPreco());
                    itemStatement.setInt(5, item.getQuantidade());
                    itemStatement.executeUpdate();
                }
            }

            connection.commit();
            System.out.println("Cadastro do pedido realizado com sucesso.");
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            System.out.println("Erro ao cadastrar pedido: " + e.getMessage());
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (itemStatement != null) {
                itemStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<Pedido> listarPedidosPorClienteId(int clienteId) throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedidos WHERE clienteId = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, clienteId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Pedido pedido = new Pedido();
                pedido.setPedidoId(resultSet.getInt("pedidoId"));
                pedido.setClienteId(resultSet.getInt("clienteId"));
                pedido.setEnderecoId(resultSet.getInt("enderecoId"));
                pedido.setFormaDePagamento(resultSet.getString("formaDePagamento"));
                pedido.setFrete(resultSet.getString("frete"));
                pedido.setValorTotal(resultSet.getDouble("valorTotal"));
                pedido.setData(resultSet.getDate("data"));
                pedido.setStatus(resultSet.getString("status"));

                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pedidos: " + e.getMessage());
            throw e;
        }

        return pedidos;
    }
    public List<Pedido> listarPedidos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM Pedidos ";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Pedido pedido = new Pedido();
                pedido.setPedidoId(resultSet.getInt("pedidoId"));
                pedido.setClienteId(resultSet.getInt("clienteId"));
                pedido.setEnderecoId(resultSet.getInt("enderecoId"));
                pedido.setFormaDePagamento(resultSet.getString("formaDePagamento"));
                pedido.setFrete(resultSet.getString("frete"));
                pedido.setValorTotal(resultSet.getDouble("valorTotal"));
                pedido.setData(resultSet.getDate("data"));
                pedido.setStatus(resultSet.getString("status"));

                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pedidos: " + e.getMessage());
            throw e;
        }

        return pedidos;
    }

    public void atualizarStatusPedido(int pedidoId, String novoStatus) throws SQLException {
        String sql = "UPDATE Pedidos SET status = ? WHERE pedidoId = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, novoStatus);
            preparedStatement.setInt(2, pedidoId);
            int affectedRows = preparedStatement.executeUpdate();
            System.out.println("Linhas afetadas: " + affectedRows);
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o status do pedido: " + e.getMessage());
            throw e;
        }
    }

    public Pedido buscarPedidoPorId(int pedidoId) throws SQLException {
        Pedido pedido = null;
        String sql = "SELECT * FROM PEDIDOS WHERE PEDIDOID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, pedidoId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                pedido = new Pedido();
                pedido.setPedidoId(resultSet.getInt("PEDIDOID"));
                pedido.setClienteId(resultSet.getInt("CLIENTEID"));
                pedido.setEnderecoId(resultSet.getInt("ENDERECOID"));
                pedido.setFormaDePagamento(resultSet.getString("FORMADEPAGAMENTO"));
                pedido.setFrete(resultSet.getString("FRETE"));
                pedido.setValorTotal(resultSet.getDouble("VALORTOTAL"));
                pedido.setStatus(resultSet.getString("STATUS"));
                pedido.setData(resultSet.getDate("DATA"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pedido por ID: " + e.getMessage());
            throw e;
        }
        return pedido;
    }

    public List<ItemCarrinho> buscarItensPedidoPorPedidoId(int pedidoId) throws SQLException {
        List<ItemCarrinho> itens = new ArrayList<>();
        String sql = "SELECT * FROM ITENSPEDIDO WHERE PEDIDOID = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, pedidoId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ItemCarrinho item = new ItemCarrinho();
                item.setLivroId(resultSet.getInt("PRODUTOID"));
                item.setLivroNome(resultSet.getString("NOMEPRODUTO"));
                item.setLivroPreco(resultSet.getDouble("PRECOUNITARIO"));
                item.setQuantidade(resultSet.getInt("QUANTIDADE"));
                itens.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar itens do pedido: " + e.getMessage());
            throw e;
        }
        return itens;
    }

}


