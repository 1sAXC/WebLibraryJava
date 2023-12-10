-H 'Content-Type: application/json' \
$.ajax({

    type: "POST",
    url: "/login",
    contentType: "application/json", // Указывает формат данных в теле запроса
    data: JSON.stringify(formData),
    success: function(response) {
        // Обработка успешного ответа
        console.log(response);
    },
    error: function(error) {
        // Обработка ошибки
        console.log(error);
    }
});
