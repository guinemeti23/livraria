package br.com.livraria.servlet;

import br.com.livraria.dao.LivroDAO;
import br.com.livraria.model.Livro;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.commons.fileupload.servlet.ServletFileUpload.isMultipartContent;

@WebServlet("/CadastroLivro")
public class CadastroLivroServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/cadastroProduto.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Map<String, String> parameters = uploadImage(req);

            String nome = parameters.get("nome");
            System.out.println("Valor do parâmetro 'nome': " + nome);

            double preco = Double.parseDouble(parameters.get("valor"));
            System.out.println("Valor do parâmetro ' preco': " + preco);

            int quantidade = Integer.parseInt(parameters.get("quantidade"));
            System.out.println("Valor do parâmetro 'qtd': " + quantidade);

            double avaliacao = Double.parseDouble(parameters.get("avaliacao"));
            String descricao = parameters.get("descricao");

            String imagemPrincipal = parameters.get("imagemPrincipal");

            String imagem2 = parameters.get("imagem2");

            String imagem3 = parameters.get("imagem3");
            String imagem4 = parameters.get("imagem4");
            String imagem5 = parameters.get("imagem5");


            Livro livro = new Livro(nome, quantidade, preco, descricao, avaliacao, imagemPrincipal, imagem2, imagem3, imagem4, imagem5);

            LivroDAO livroDAO = new LivroDAO();
            livroDAO.cadastrarLivro(livro);

            resp.sendRedirect(req.getContextPath() + "/ListarProdutoServlet");
        } catch (Exception ex) {
            ex.printStackTrace();

            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ocorreu um erro durante o processamento da requisição.");
        }
    }




    private Map< String, String> uploadImage(HttpServletRequest httpServletRequest) {

        HashMap<String, String> parameters = new HashMap<>();

        if (isMultipartContent(httpServletRequest)){
            try {
                DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
                List<FileItem> fileItems = new ServletFileUpload(diskFileItemFactory).parseRequest(httpServletRequest);

                for (FileItem item: fileItems){
                    checkFieldType(item, parameters);
                }

            }catch (Exception e){
                parameters.put("image", "img/default.jpg");
            }

            return  parameters;
        }

        return parameters;

    }

    private void checkFieldType(FileItem fileItem, Map<String, String> requestParameters) throws Exception {
        if (fileItem.isFormField()) {
            requestParameters.put(fileItem.getFieldName(), fileItem.getString());
        } else {
            String fieldName = fileItem.getFieldName();
            if (fieldName.equals("imagemPrincipal") || fieldName.startsWith("imagem")) {

                String fileName = processUploadedFile(fileItem);
                requestParameters.put(fieldName, fileName);
            }
        }
    }


    private String processUploadedFile(FileItem fileItem) throws Exception {
        Long currentTime = new Date().getTime();

        String fileName = currentTime.toString().concat("-").concat(fileItem.getName().replace(" ",""));
        String filePath = this.getServletContext().getRealPath("img").concat(File.separator).concat(fileName);

        fileItem.write(new File(filePath));

        return fileName;
    }
}

