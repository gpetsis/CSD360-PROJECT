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
        formData.delete('vId');
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
    xhr.setRequestHeader("Request-Type", "Add-Vehicle")
    xhr.send(JSON.stringify(data));
}

function handleExtraFields(vehicleType){
    if(vehicleType == "car"){
        $('#addDivLicenseNumber').show();
        $('#addDivType').show();
        $('#addDivvId').hide();
    }
    else{
        $('#addDivLicenseNumber').hide();
        $('#addDivType').hide();
        $('#addDivvId').show();
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
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
            $('#ajaxContent').html("Successfully added new customer.");
            console.log(responseData);
        } else if (xhr.status !== 200) {
            $('#ajaxContent').html('Request failed. Returned status of ' + xhr.status + "<br>");
           const responseData = xhr.responseText;
        }
    };
    xhr.open('POST', 'Customer');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.setRequestHeader("Request-Type", "Add-Customer");
    xhr.send(JSON.stringify(jsonData));
}

function handleReturnVehicle() {
    var vId = document.getElementById("returnVehicle").value;

    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $('#ajaxContent').html("Succesfully returned vehicle");
        } else if (xhr.status !== 200) {
            $('#ajaxContent').html('Request failed. Returned status of ' + xhr.status + "<br>");
        }
    };
    xhr.open('POST', 'Vehicle');
    xhr.setRequestHeader("Request-Type", "Return-Vehicle");
    xhr.send(vId);

}

function searchVehicles(){
    var output;
    output = $('#search').val();
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
            $('#ajaxContent').html(responseData);
            var vehiclesArray = JSON.parse(responseData);
            for(var i = 0 ; i < vehiclesArray.length ; i++){
                console.log(vehiclesArray[i]);
            }
        } else if (xhr.status !== 200) {
            $('#ajaxContent').html('Request failed. Returned status of ' + xhr.status + "<br>");
            const responseData = xhr.responseText;
        }
    };
    xhr.open('GET', 'Vehicle');
    xhr.setRequestHeader("Request-Type", "Search");
    xhr.setRequestHeader("Vehicle-Type", output.toString());
    xhr.send();
}

function rentVehicle(){
    let myForm = document.getElementById('rentVehicle');
    let formData = new FormData(myForm);
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
            $('#ajaxContent').html("Successfully rented a vehicle.");
            console.log(responseData);
        } else if (xhr.status !== 200) {
            $('#ajaxContent').html('Request failed. Returned status of ' + xhr.status + "<br>");
           const responseData = xhr.responseText;
        }
    };
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    if(data['insurance']) { data['insurance'] = "1"; }
    else { data['insurance'] = "0"; }

    console.log(JSON.stringify(data));
    xhr.open('POST', 'Customer');
    xhr.setRequestHeader("Request-Type", "Rent");
    xhr.send(JSON.stringify(data));
}