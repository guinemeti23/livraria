document.addEventListener('DOMContentLoaded', function() {
    const imagensInput = document.getElementById('imagens');
    const previewContainer = document.getElementById('imagens-preview');
    const removerImagemSelect = document.getElementById('remover-imagem');
    const removerImagemBtn = document.getElementById('remover-imagem-btn');
    const imagemPrincipalSelect = document.getElementById('imagem-principal');
    const imagensMap = new Map();

    imagensInput.addEventListener('change', function() {
        for (const file of this.files) {
            if (imagensMap.size >= 5) {
                alert('Você já selecionou o máximo de imagens permitido (5).');
                break;
            }

            if (!imagensMap.has(file.name)) {
                imagensMap.set(file.name, file);
                adicionarImagemPreview(file);
            }
        }
    });

    function adicionarImagemPreview(file) {
        const reader = new FileReader();
        reader.onload = function() {
            const image = new Image();
            image.src = reader.result;
            image.style.maxWidth = '100px';
            image.style.maxHeight = '100px';
            image.alt = file.name;
            previewContainer.appendChild(image);

            const option = document.createElement('option');
            option.text = file.name;
            option.value = file.name;
            removerImagemSelect.appendChild(option);

            const principalOption = document.createElement('option');
            principalOption.text = file.name;
            principalOption.value = file.name;
            imagemPrincipalSelect.appendChild(principalOption);
        }
        reader.readAsDataURL(file);
    }

    removerImagemBtn.addEventListener('click', function() {
        const selectedOption = removerImagemSelect.options[removerImagemSelect.selectedIndex];
        if (selectedOption) {
            const fileName = selectedOption.value;
            imagensMap.delete(fileName);

            const images = previewContainer.getElementsByTagName('img');
            for (let i = 0; i < images.length; i++) {
                if (images[i].alt === fileName) {
                    images[i].remove();
                    break;
                }
            }
            selectedOption.remove();


            const principalOptions = imagemPrincipalSelect.options;
            for (let i = 0; i < principalOptions.length; i++) {
                if (principalOptions[i].value === fileName) {
                    principalOptions[i].remove();
                    break;
                }
            }
        }
    });
});