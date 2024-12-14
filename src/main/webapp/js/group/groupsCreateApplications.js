document.addEventListener("DOMContentLoaded", function() {
    const applyForms = document.querySelectorAll(".apply-form");

    applyForms.forEach(form => {
        form.addEventListener("submit", function(event) {
            event.preventDefault(); // Остановить стандартное поведение формы

            const formData = new FormData(this);
            const groupId = formData.get("groupId");
            const button = document.querySelector(`#apply-button-${groupId}`);

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
                    button.textContent = "Заявка подана";
                } else {
                    console.error("Ошибка отправки формы");
                }
            }).catch(error => {
                console.error("Ошибка:", error);
            });
        });
    });
});
