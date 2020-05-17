function checkPass() {
    var pass1 = document.getElementById('password');
    var pass2 = document.getElementById('password2');
    var message = document.getElementById('error-nwl');
    var goodColor = "#66cc66";
    var badColor = "#ff6666";
    if (pass1.value === pass2.value) {
        message.style.color = goodColor;
        message.innerHTML = "ok!"
    } else {
        message.style.color = badColor;
        message.innerHTML = " These passwords don't match"
    }
}