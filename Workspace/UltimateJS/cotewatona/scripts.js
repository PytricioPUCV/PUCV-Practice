// Mensaje de consola al cargar la página
console.log('Página cargada correctamente');

// Efecto de fade-in para la sección del equipo
window.addEventListener('load', () => {
    const teamSection = document.querySelector('.team-section');
    teamSection.style.opacity = 0;
    teamSection.style.transition = 'opacity 2s ease-in-out';
    setTimeout(() => {
        teamSection.style.opacity = 1;
    }, 500);
});

// Animación del header al hacer scroll
window.addEventListener('scroll', () => {
    const header = document.querySelector('header');
    if (window.scrollY > 50) {
        header.style.backgroundColor = '#4e9e18'; // Cambia el color del header al hacer scroll
    } else {
        header.style.backgroundColor = '#61bb1b';
    }
});

// Efecto de hover en los miembros del equipo (ampliar avatar)
const avatars = document.querySelectorAll('.avatar');
avatars.forEach(avatar => {
    avatar.addEventListener('mouseenter', () => {
        avatar.style.transform = 'scale(1.1)';
        avatar.style.transition = 'transform 0.3s ease';
    });

    avatar.addEventListener('mouseleave', () => {
        avatar.style.transform = 'scale(1)';
    });
});

// Parpadeo del borde en el título del equipo
const title = document.querySelector('h2');
setInterval(() => {
    title.style.boxShadow = '0 8px 16px rgba(0, 0, 0, 0.5)';
    setTimeout(() => {
        title.style.boxShadow = '0 8px 16px rgba(0, 0, 0, 0.2)';
    }, 500);
}, 1000);

// Animación suave al hacer clic en los enlaces del menú
const links = document.querySelectorAll('nav a');
links.forEach(link => {
    link.addEventListener('click', (event) => {
        event.preventDefault();
        const target = event.target.getAttribute('href');
        if (target && target !== '#') {
            document.querySelector(target).scrollIntoView({ 
                behavior: 'smooth' 
            });
        }
    });
});
