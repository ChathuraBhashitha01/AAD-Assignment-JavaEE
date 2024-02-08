function loadCustomerIDs(){
    $("#cmbCustomerID").empty();
    customerDB.length=0;
    $.ajax({
        url: "http://localhost:8080/app/customers",
        method: "GET",
        dataType: "json",
        success: function (resp) {
            for (const customer of resp) {
                $("#cmbCustomerID").append("<option >" + customer.id + "</option>");
                const customerDetails = {
                    id: customer.id,
                    name: customer.name,
                    address: customer.address,
                    salary: customer.salary
                }
                customerDB.push(customerDetails);
            }
        }
    });
}

$("#navCustomer").click(function (){
    loadCustomerIDs();
    $("#navCustomer").css( "font-weight","bold")
    $("#navPlaceOrder").css( "font-weight","normal")
    $("#navHome").css( "font-weight","normal")
    $("#navItem").css( "font-weight","normal")
    $("#btnOrderDetails").css('display','none');
});

$("#imgCustomer").click(function () {
    loadCustomerIDs();
});

$("#btnSaveCustomer").click(function (){
    if (checkCustomerAll()) {
        saveCustomer();
        loadCustomerIDs();
        clearCustomerInputField();
    }
    else {
        alert("Faild Saved");
    }
});

$("#btnCustomerGetAll").click(function (){
    getAllCustomers();
});

$("#btnCustomerDelete").click(function (){
    deleteCustomer();
    loadCustomerIDs();
    clearCustomerInputField();
});

$("#btnCustomerUpdate").click(function (){
    updateCustomer();
    loadCustomerIDs();
    clearCustomerInputField();
});

$("#btnCustomerSearch").click(function (){
    let id=$("#cmbCustomerID").val();
    for (let i = 0; i < customerDB.length; i++) {
        if (customerDB[i].id==id){
            $("#cusId").val(customerDB[i].id)
            $("#cusName").val(customerDB[i].name)
            $("#cusAddress").val(customerDB[i].address)
            $("#cusSalary").val(customerDB[i].salary)
        }
    }
    setBtn();
});

function saveCustomer() {
    let customerId = $("#cusId").val();

    if (searchCustomer(customerId.trim()) == undefined){
        let customerName = $("#cusName").val();
        let customerAddress = $("#cusAddress").val();
        let customerSalary = $("#cusSalary").val();

        let newCustomer = {
            id : customerId,
            name : customerName,
            address : customerAddress,
            salary : customerSalary
        };

        const jsonObject=JSON.stringify(newCustomer);
        $.ajax({
            url:"http://localhost:8080/app/customers",
            method:"POST",
            data:jsonObject,
            contentType:("application/json"),

            success: function (resp, textStatus, jqxhr){
                console.log("Success",resp);
                if (jqxhr.status == 201) {
                    // alert(jqxhr.responseText);
                    alert("Added customer successfully");
                }
            },
            error: function (error){
                console.log("Error",error);
            }
        });

    }
    else {
        alert("Customer already exits.!");
        clearCustomerInputField();
    }
}

function searchCustomer(id){
    return customerDB.find(function (customer){
        return customer.id==id;
    });
}
function getAllCustomers(){
    $("#tblCustomer").empty();
    for (let i = 0; i < customerDB.length; i++) {
        let customerId=customerDB[i].id;
        let customerName=customerDB[i].name;
        let customerAddress=customerDB[i].address;
        let customerSalary=customerDB[i].salary;

        let row=`<tr>
                    <td>${customerId}</td>
                    <td>${customerName}</td>
                    <td>${customerAddress}</td>
                    <td>${customerSalary}</td>
                </tr>`;
        $("#tblCustomer").append(row);
        bindCusTrEvents();
    }
}
function bindCusTrEvents() {
    $("#tblCustomer>tr").click(function (){
        let id=$(this).children().eq(0).text();
        let name=$(this).children().eq(1).text();
        let address=$(this).children().eq(2).text();
        let salary=$(this).children().eq(3).text();

        $("#cusId").val(id)
        $("#cusName").val(name)
        $("#cusAddress").val(address)
        $("#cusSalary").val(salary)
    });
}

function deleteCustomer(){
    let id=$("#cusId").val();
    if (searchCustomer(id) == undefined) {
        alert("No such Customer..please check the ID");
    } else {
        let consent = confirm("Do you really want to Delete this customer.?");
        if (consent) {
            $.ajax({
                url: "http://localhost:8080/app/customers?id=" + id,
                method: "DELETE",
                success: function (resp, textStatus, jqxhr) {
                    if (jqxhr.status == 201) {
                        // alert(jqxhr.responseText);
                        alert("Delete customer successfully");
                    }
                },
                error: function (error) {

                }
            });
        }
    }
}

function updateCustomer(){
    let id=$("#cusId").val();

    if (searchCustomer(id) == undefined) {
        clearCustomerInputField();
        alert("No such Customer..please check the ID");
    } else {
        let consent = confirm("Do you really want to update this customer.?");
        if (consent) {
            let name = $("#cusName").val();
            let address = $("#cusAddress").val();
            let salary = $("#cusSalary").val();

            let newCustomer = {
                id: id,
                name: name,
                address: address,
                salary: salary
            };

            const jsonObject = JSON.stringify(newCustomer);
            $.ajax({
                url: "http://localhost:8080/app/customers",
                method: "PUT",
                data: jsonObject,
                contentType: ("application/json"),

                success: function (resp, textStatus, jqxhr) {
                    if (jqxhr.status == 201) {
                        // alert(jqxhr.responseText);
                        alert("Update customer successfully");
                    }
                },
                error: function (error) {
                    console.log("Error", error);
                }
            });
        }
    }
};

