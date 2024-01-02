function initDatabase() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {};
    xhr.open('GET', 'Init');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send();
}

function handleAddNewCustomer() {
    let myForm = document.getElementById('addNewCustomerForm');
    let formData = new FormData(myForm);
    var jsonData = {};
    formData.forEach(function(value, key){
        jsonData[key] = value;
        console.log(key, jsonData[key]);
    });
    
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {};
    xhr.open('POST', 'Customer');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send(JSON.stringify(jsonData));
}