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
            Map<String, Object> parameters = uploadImages(req);

            String nome = (String) parameters.get("nome");
            double preco = Double.parseDouble((String) parameters.get("valor"));
            int quantidade = Integer.parseInt((String) parameters.get("quantidade"));
            double avaliacao = Double.parseDouble((String) parameters.get("avaliacao"));
            String descricao = (String) parameters.get("descricao");
            List<String> imagens = (List<String>) parameters.get("imagens");

            Livro livro = new Livro(nome, quantidade, preco, descricao, avaliacao, imagens);
            LivroDAO livroDAO = new LivroDAO();
            livroDAO.cadastrarLivro(livro);

            resp.sendRedirect(req.getContextPath() + "/ListarProdutoServlet");
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Ocorreu um erro durante o processamento da requisição.");
        }
    }

    private Map<String, Object> uploadImages(HttpServletRequest request) throws ServletException, IOException {
        Map<String, Object> parameters = new HashMap<>();
        List<String> imageList = new ArrayList<>();

        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List<FileItem> items = upload.parseRequest(request);

                for (FileItem item : items) {
                    if (item.isFormField()) {
                        parameters.put(item.getFieldName(), item.getString());
                    } else {
                        if (item.getFieldName().equals("imagens")) {
                            String fileName = processUploadedFile(item);
                            imageList.add(fileName);
                        }
                    }
                }
                parameters.put("imagens", imageList);
            } catch (Exception e) {
                e.printStackTrace();
                parameters.put("error", "Erro no upload de arquivo");
            }
        }

        return parameters;
    }

    private String processUploadedFile(FileItem item) throws Exception {
        Long currentTime = new Date().getTime();
        String fileName = currentTime.toString() + "-" + item.getName().replace(" ", "");
        String filePath = getServletContext().getRealPath("img") + File.separator + fileName;
        File file = new File(filePath);
        item.write(file);
        return fileName;
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


}

