document.getElementById('contact-form').addEventListener('submit', function(event) {
    var name = document.getElementById('name').value;
    var email = document.getElementById('email').value;
    var message = document.getElementById('message').value;
    var emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;

    if (name.trim() === '') {
        alert('Пожалуйста, введите ваше имя.');
        event.preventDefault();
    } else if (!emailPattern.test(email)) {
        alert('Пожалуйста, введите корректный email.');
        event.preventDefault();
    } else if (message.trim() === '') {
        alert('Пожалуйста, введите сообщение.');
        event.preventDefault();
    } else {
        alert('Форма успешно отправлена!');
    }
});
