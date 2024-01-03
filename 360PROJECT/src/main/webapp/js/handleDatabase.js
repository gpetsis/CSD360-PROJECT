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
    else if(type == "scooter" || type == "bicycle"){
        formData.delete('type');
        formData.delete('licensenumber');
        formData.delete('vehicleType');
    }
    else{
        formData.delete('vehicleType');
        formData.delete('vId');
        formData.delete('type');

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
    else if(vehicleType == "scooter" || vehicleType == "bicycle"){
        $('#addDivLicenseNumber').hide();
        $('#addDivType').hide();
        $('#addDivvId').show();
    }
    else{
        $('#addDivType').hide();
        $('#addDivvId').hide();
        $('#addDivLicenseNumber').show();
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
        }else if(xhr.status == 700){
            $('#ajaxContent').html("Vehicle is already rented.");
        } else if (xhr.status !== 200) {
            $('#ajaxContent').html('Request failed. Returned status of ' + xhr.status + "<br>");
           const responseData = xhr.responseText;
        }
    };
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    if(data['insurance'] == true) { data['insurance'] = "1"; }
    else { data['insurance'] = "0"; }

    console.log(JSON.stringify(data));
    xhr.open('POST', 'Customer');
    xhr.setRequestHeader("Request-Type", "Rent");
    xhr.setRequestHeader("vId", data['vId']);
    xhr.send(JSON.stringify(data));
}

function reportDamage(){
    let myForm = document.getElementById('reportDamage');
    let formData = new FormData(myForm);
    var xhr = new XMLHttpRequest();
    var type;
    var oldvId, newvId;
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
            $('#ajaxContent').html("Successfully added new vehicle.");
            replaceVehicles(oldvId, newvId);

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
    else if(type == "scooter" || type == "bicycle"){
        formData.delete('type');
        formData.delete('licensenumber');
        formData.delete('vehicleType');
    }
    else{
        formData.delete('vehicleType');
        formData.delete('vId');
        formData.delete('type');
    }
    oldvId = formData.get('oldvId');
    newvId = formData.get('vId');
    formData.delete('oldvId');
    formData.forEach((value, key) => (data[key] = value));
    console.log(JSON.stringify(data));
    xhr.open('POST', 'VehicleServlet');
    xhr.setRequestHeader("Vehicle-Type", type);
    xhr.setRequestHeader("Request-Type", "Add-Vehicle")
    xhr.send(JSON.stringify(data));
}

function handleRepairVehicle() {
    let myForm = document.getElementById('repairVehicle');
    let formData = new FormData(myForm);
    
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
            $('#ajaxContent').html("Added vehicle to repair.");
        } else if (xhr.status !== 200) {
            $('#ajaxContent').html('Request failed. Returned status of ' + xhr.status + "<br>");
            const responseData = xhr.responseText;
        }
    };
    
    const data = {};
    formData.forEach((value, key) => (data[key] = value));
    
    xhr.open('POST', 'Vehicle');
    xhr.setRequestHeader("Request-Type", "Repair-Vehicle");
    xhr.send(JSON.stringify(data));   
}

function handleRefreshUnavailable() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
            $('#ajaxContent').html("Succesfully refreshed unavailable vehicles.");
        } else if (xhr.status !== 200) {
            $('#ajaxContent').html('Request failed. Returned status of ' + xhr.status + "<br>");
            const responseData = xhr.responseText;
        }
    };
    
    xhr.open('POST', 'Vehicle');
    xhr.setRequestHeader("Request-Type", "Refresh-Unavailable");
    xhr.send();
}

function replaceVehicles(oldvId, newvId){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
            $('#ajaxContent').html("Successfully replaced the damaged vehicle with a new vehicle.");
            console.log(responseData);
        } else if (xhr.status !== 200) {
            $('#ajaxContent').html('Request failed. Returned status of ' + xhr.status + "<br>");
           const responseData = xhr.responseText;
        }
    };
    console.log(oldvId, newvId);
    xhr.open('PUT', 'Customer');
    xhr.setRequestHeader("oldvId", oldvId);
    xhr.setRequestHeader("newvId", newvId);
    xhr.setRequestHeader("Request-Type", "Replace-Vehicles")
    xhr.send();
}

function handleDamageFields(vehicleType){
    if(vehicleType == "car"){
        $('#reportDamageDivLicenseNumber').show();
        $('#reportDamageDivType').show();
        $('#reportDamageDivvId').hide();
    }
    else if(vehicleType == "scooter" || vehicleType == "bicycle"){
        $('#reportDamageDivLicenseNumber').hide();
        $('#reportDamageDivType').hide();
        $('#reportDamageDivvId').show();
    }
    else{
        $('#reportDamageDivType').hide();
        $('#reportDamageDivvId').hide();
        $('#reportDamageDivLicenseNumber').show();
    }
}