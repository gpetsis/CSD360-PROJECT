function initDatabase() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {};
    xhr.open('GET', 'Init');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send();
}