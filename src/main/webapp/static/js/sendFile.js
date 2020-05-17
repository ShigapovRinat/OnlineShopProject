var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
function sendFile() {
    let formData = new FormData();
    let files = ($('#file'))[0]['files'];
    let title = document.getElementById('title').value;
    let price = document.getElementById('price').value;
    let manufacturer = document.getElementById('manufacturer').value;
    let description = document.getElementById('description').value;
    let type = document.getElementById('type').value;
    [].forEach.call(files, function (file, i, files) {
        formData.append("multipartFile", file);
    });
    formData.append('title', title);
    formData.append('price', price);
    formData.append('manufacturer', manufacturer);
    formData.append('description', description);
    formData.append('type', type);
    $.ajax({
        type: "POST",
        url: "http://localhost:8080/addGood",
        data: formData,
        processData: false,
        contentType: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        }
    })
        .done(function () {
            alert('Success')
        })
        .fail(function () {
            alert('fail')
        });
}