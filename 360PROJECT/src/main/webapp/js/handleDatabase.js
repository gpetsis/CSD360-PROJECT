function initDatabase() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {};
    xhr.open('GET', 'Init');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send();
}

function addNewVehicle(){
    let myForm = document.getElementById('addNewVehicleForm');
    let formData = new FormData(myForm);
    var xhr = new XMLHttpRequest();
    var type;
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
            $('#ajaxContent').html("Successfully added new vehicle.");
            console.log(responseData);
        } else if (xhr.status !== 200) {
            $('#ajaxContent').html('Request failed. Returned status of ' + xhr.status + "<br>");
           const responseData = xhr.responseText;
        }
    };
    const data = {};
    type = formData.get('vehicleType');
    console.log(type);
    if(type == "car"){
        formData.delete('vehicleType');
    }
    else {
        formData.delete('type');
        formData.delete('licensenumber');
        formData.delete('vehicleType');
    }
    formData.forEach((value, key) => (data[key] = value));
    console.log(JSON.stringify(data));
    xhr.open('POST', 'VehicleServlet');
    xhr.setRequestHeader("Vehicle-Type", type);
    xhr.send(JSON.stringify(data));
}

function handleExtraFields(vehicleType){
    if(vehicleType == "car"){
        $('#divLicenseNumber').show();
        $('#divType').show();
    }
    else{
        $('#divLicenseNumber').hide();
        $('#divType').hide();
    }
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