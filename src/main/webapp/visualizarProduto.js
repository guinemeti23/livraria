const carrossel = document.querySelector('.carrossel');
const prevBtn = document.querySelector('.prev');
const nextBtn = document.querySelector('.next');
const imgWidth = carrossel.clientWidth;
let position = 0;

nextBtn.addEventListener('click', () => {
    position -= imgWidth;
    if (position < -(carrossel.scrollWidth - imgWidth)) {
        position = 0;
    }
    carrossel.style.transform = `translateX(${position}px)`;
    prevBtn.style.display = 'block'; // Exibe o botão de navegação anterior ao avançar
});

prevBtn.addEventListener('click', () => {
    position += imgWidth;
    if (position >= 0) {
        position = -(carrossel.scrollWidth - imgWidth);
    }
    carrossel.style.transform = `translateX(${position}px)`;
    if (position === 0) {
        prevBtn.style.display = 'none'; // Oculta o botão de navegação anterior quando voltar ao início
    }
});