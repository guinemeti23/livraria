document.addEventListener('DOMContentLoaded', function() {
    const imagensInput = document.getElementById('imagens');
    const previewContainer = document.getElementById('preview-container');

    // Lida com a mudança no input de arquivos
    imagensInput.addEventListener('change', function() {
        // Limpa o contêiner de pré-visualizações a cada nova seleção de arquivos
        while (previewContainer.firstChild) {
            previewContainer.removeChild(previewContainer.firstChild);
        }

        // Processa cada arquivo selecionado, limitado a 5 para evitar excesso de carregamento
        Array.from(this.files).slice(0, 5).forEach(file => {
            if (file.type.startsWith('image/')) {  // Verifica se o arquivo é uma imagem
                const reader = new FileReader();
                reader.onload = function(e) {
                    // Cria a tag de imagem para a pré-visualização
                    const imgElement = document.createElement('img');
                    imgElement.src = e.target.result;
                    imgElement.style.width = '100px';  // Define largura da pré-visualização
                    imgElement.style.height = '100px'; // Define altura da pré-visualização
                    imgElement.style.marginRight = '5px'; // Espaçamento entre as imagens
                    previewContainer.appendChild(imgElement);

                    // Cria um botão para remover a pré-visualização da imagem
                    const removeButton = document.createElement('button');
                    removeButton.textContent = 'Remover';
                    removeButton.onclick = function() {
                        // Remove a imagem e o botão ao clicar
                        previewContainer.removeChild(imgElement);
                        previewContainer.removeChild(removeButton);
                    };
                    previewContainer.appendChild(removeButton); // Adiciona o botão ao contêiner
                };
                reader.readAsDataURL(file); // Lê o arquivo como URL de dados para pré-visualização
            }
        });
    });
});
