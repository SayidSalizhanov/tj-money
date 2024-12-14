document.addEventListener("DOMContentLoaded", function() {
    const deleteForms = document.querySelectorAll(".delete-form");

    deleteForms.forEach(form => {
        form.addEventListener("submit", function(event) {
            event.preventDefault(); // Остановить стандартное поведение формы

            const formData = new FormData(this);
            const username = formData.get("username");
            const button = document.querySelector(`#delete-button-${username}`);

            // Отправляем запрос вручную
            fetch(this.action, {
                method: this.method,
                body: new URLSearchParams(formData) // Преобразуем FormData в URLSearchParams
            }).then(response => {
                if (response.ok) {
                    console.log("Запрос успешен");
                    // Делаем кнопку неактивной
                    button.classList.add("disabled");
                    button.setAttribute("disabled", "disabled");
                    button.textContent = "Удалено";
                } else {
                    console.error("Ошибка отправки формы");
                }
            }).catch(error => {
                console.error("Ошибка:", error);
            });
        });
    });
});
