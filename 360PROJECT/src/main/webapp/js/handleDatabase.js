function initDatabase() {
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {};
    xhr.open('POST', 'Init');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.send();
}

function askAnything() {
    var query = document.getElementById("askAnythingInput").value;
    
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
            $('#ajaxContent').html(responseData);
//            var vehiclesArray = responseData;
//            for(var i = 0 ; i < vehiclesArray.length ; i++){
//                console.log(vehiclesArray[i]);
//            }
        } else if (xhr.status !== 200) {
            $('#ajaxContent').html('Request failed. Returned status of ' + xhr.status + "<br>");
            const responseData = xhr.responseText;
        }
    };
    
    console.log(query);
    
    xhr.open('GET', 'Init');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.setRequestHeader("Query", query);
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
            $('#ajaxContent').html("Vehicle is already rented or unavailable.");
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
    xhr.setRequestHeader("vId", data['vId']);
    xhr.send(JSON.stringify(data));
}

function reportDamage(){
    let myForm = document.getElementById('reportDamage');
    let formData = new FormData(myForm);
    var xhr = new XMLHttpRequest();
    var type;
    var entrydate = formData.get('entrydate');
    var repaircost = formData.get('repaircost');
    var oldvId, newvId;
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
            $('#ajaxContent').html("Successfully added new vehicle.");
            replaceVehicles(oldvId, newvId, entrydate, repaircost);
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
    formData.delete('repaircost');
    formData.delete('entrydate');
    formData.forEach((value, key) => (data[key] = value));
    console.log(JSON.stringify(data));
    xhr.open('POST', 'VehicleServlet');
    xhr.setRequestHeader("Vehicle-Type", type);
    xhr.setRequestHeader("Request-Type", "Add-Vehicle");
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

function replaceVehicles(oldvId, newvId, entrydate, repaircost){
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
    xhr.setRequestHeader("entrydate", entrydate);
    xhr.setRequestHeader("repaircost", repaircost);
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

function reportAccident(){
    let myForm = document.getElementById('reportAccident');
    let formData = new FormData(myForm);
    var xhr = new XMLHttpRequest();
    var type;
    var oldvId, newvId;
    var entrydate = formData.get('entrydate');
    var repaircost = formData.get('repaircost');
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
            $('#ajaxContent').html("Successfully added new vehicle.");
            replaceVehiclesAfterAccident(oldvId, newvId, entrydate, repaircost);
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
    formData.delete('entrydate');
    formData.delete('repaircost');
    formData.forEach((value, key) => (data[key] = value));
    console.log(JSON.stringify(data));
    xhr.open('POST', 'VehicleServlet');
    xhr.setRequestHeader("Vehicle-Type", type);
    xhr.setRequestHeader("Request-Type", "Add-Vehicle")
    xhr.send(JSON.stringify(data));
}

function handleAccidentFields(vehicleType){
    if(vehicleType == "car"){
        $('#reportAccidentDivLicenseNumber').show();
        $('#reportAccidentDivType').show();
        $('#reportAccidentDivvId').hide();
    }
    else if(vehicleType == "scooter" || vehicleType == "bicycle"){
        $('#reportAccidentDivLicenseNumber').hide();
        $('#reportAccidentDivType').hide();
        $('#reportAccidentDivvId').show();
    }
    else{
        $('#reportAccidentDivType').hide();
        $('#reportAccidentDivvId').hide();
        $('#reportAccidentDivLicenseNumber').show();
    }
}

function replaceVehiclesAfterAccident(oldvId, newvId, entrydate, repaircost){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
            $('#ajaxContent').html("Successfully replaced the damaged vehicle with a new vehicle.(After Accident)");
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
    xhr.setRequestHeader("entrydate", entrydate);
    xhr.setRequestHeader("repaircost", repaircost);
    xhr.setRequestHeader("Request-Type", "Replace-Vehicles-Accident")
    xhr.send();
}

function firstQuestion(){
    var output;
    var query;
    output = $('#firstQuestion').val();
    if(output == 'availableVehicles'){
        query = "SELECT vehicles.vId FROM vehicles LEFT OUTER JOIN rents ON vehicles.vId = rents.vId LEFT OUTER JOIN unavailable ON vehicles.vId = unavailable.vId WHERE rents.vId IS NULL AND unavailable.vId IS NULL";
    }
    else if(output == 'rentedVehicles'){
        query = "SELECT vehicles.vId FROM vehicles INNER JOIN rents ON vehicles.vId = rents.vId";
    }else if(output == 'availableCars'){
        query = "SELECT cars.licensenumber FROM cars LEFT OUTER JOIN rents ON cars.licensenumber = rents.vId LEFT OUTER JOIN unavailable ON cars.licensenumber = unavailable.vId WHERE rents.vId IS NULL AND unavailable.vId IS NULL";
    }else if(output == 'rentedCars'){
        query = "SELECT cars.licensenumber FROM cars INNER JOIN rents ON cars.licensenumber = rents.vId";
    }else if(output == 'availableScooters'){
        query = "SELECT scooters.vId FROM scooters LEFT OUTER JOIN rents ON scooters.vId = rents.vId LEFT OUTER JOIN unavailable ON scooters.vId = unavailable.vId WHERE rents.vId IS NULL AND unavailable.vId IS NULL";
    }else if(output == 'rentedScooters'){
        query = "SELECT scooters.vId FROM scooters INNER JOIN rents ON scooters.vId = rents.vId";
    }else if(output == 'availableBicycles'){
        query = "SELECT bicycles.vId FROM bicycles LEFT OUTER JOIN rents ON bicycles.vId = rents.vId LEFT OUTER JOIN unavailable ON bicycles.vId = unavailable.vId WHERE rents.vId IS NULL AND unavailable.vId IS NULL";
    }else if(output == 'rentedBicycles'){
        query = "SELECT bicycles.vId FROM bicycles INNER JOIN rents ON bicycles.vId = rents.vId";
    }else if(output == 'availableMotorcycles'){
        query = "SELECT motorcycles.licensenumber FROM motorcycles LEFT OUTER JOIN rents ON motorcycles.licensenumber = rents.vId LEFT OUTER JOIN unavailable ON motorcycles.licensenumber = unavailable.vId WHERE rents.vId IS NULL AND unavailable.vId IS NULL";
    }else if(output == 'rentedMotorcycles'){
        query = "SELECT motorcycles.licensenumber FROM motorcycles INNER JOIN rents ON motorcycles.licensenumber = rents.vId";
    }
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            const responseData = xhr.responseText;
            $('#ajaxContent').html(responseData);
        } else if (xhr.status !== 200) {
            $('#ajaxContent').html('Request failed. Returned status of ' + xhr.status + "<br>");
            const responseData = xhr.responseText;
        }
    };
    console.log(query);
    xhr.open('GET', 'Init');
    xhr.setRequestHeader("Content-type", "application/json");
    xhr.setRequestHeader("Query", query);
    xhr.send();
}