
loadCustomerIDs();

$("#navCustomer").click(function (){
    $("#navCustomer").css( "font-weight","bold")
    $("#navPlaceOrder").css( "font-weight","normal")
    $("#navHome").css( "font-weight","normal")
    $("#navItem").css( "font-weight","normal")
    $("#btnOrderDetails").css('display','none');
});

$("#btnSaveCustomer").click(function (){
    if (checkCustomerAll()) {
        saveCustomer();
    }
    else {
        alert("Faild Saved");
    }
});

$("#btnCustomerGetAll").click(function (){
    getAllCustomer();
});

$("#btnCustomerDelete").click(function (){
    deleteCustomer();
    // loadCustomerIDs();
    // getAllCustomer();
    // clearCustomerInputField();
});

$("#btnCustomerUpdate").click(function (){
    updateCustomer();
    // getAllCustomer();
    // clearCustomerInputField();
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

            success: function (resp){
                console.log("Success",resp);
                if (jqxhr.status==201){
                    alert(jqxhr.responseText);
                }
            },
            error: function (error){
                console.log("Error",error);
            }
        });
        // loadCustomerIDs();
        // getAllCustomer();
       // clearCustomerInputField();
    }
    else {
        alert("Customer already exits.!");
        //clearCustomerInputField();
    }
}

function searchCustomer(id){
    return customerDB.find(function (customer){
        return customer.id==id;
    });
}

function getAllCustomer(){
    $("#tblCustomer").empty();

    $.ajax({
        url:"http://localhost:8080/app/customers",
        method:"GET",

        success:function (resp){
            console.log("Success : ",resp)

            for (const customer of resp) {

                const row = `<tr>
                                <td>${customer.id}</td>
                                <td>${customer.name}</td>
                                <td>${customer.address}</td>
                                <td>${customer.salary}</td>
                            </tr>`;
                $('#tblCustomer').append(row);
            }
        },
        error:function (error){
            console.log("error : "+error)
        }
    });

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
function loadCustomerIDs(){
    $("#cmbCustomerID").empty();
    for (let i = 0; i <customerDB.length ; i++) {
        let id=customerDB[i].id;
        $("#cmbCustomerID").append("<option >"+id +"</option>");
    }
}
function deleteCustomer(){
    let id=$("#cusId").val();
    // if (searchCustomer(id) == undefined) {
    //     alert("No such Customer..please check the ID");
    // } else {
    //     let consent = confirm("Do you really want to Delete this customer.?");
    //     if (consent) {
    //         for (let i = 0; i < customerDB.length; i++) {
    //             if (customerDB[i].id == id) {
    //                 customerDB.splice(i, 1);
    //                 return true;
    //             }
    //         }
    //
    //     }
    // }
    // return false;

    $.ajax({
        url:"http://localhost:8080/app/customers?id="+id,
        method:"DELETE",
       success:function (resp,jqxhr){
           if (jqxhr.status==201){
               alert(jqxhr.responseText);
           }
       },
        error: function (error) {

        }
    });
}

function updateCustomer(){
    let id=$("#cusId").val();
    let name = $("#cusName").val();
    let address = $("#cusAddress").val();
    let salary = $("#cusSalary").val();
    // if (searchCustomer(id) == undefined) {
    //     alert("No such Customer..please check the ID");
    // } else {
    //     let consent = confirm("Do you really want to update this customer.?");
    //     if (consent) {
    //         let customer = searchCustomer(id);
    //
    //         let customerName = $("#cusName").val();
    //         let customerAddress = $("#cusAddress").val();
    //         let customerSalary = $("#cusSalary").val();
    //
    //         customer.name = customerName;
    //         customer.address = customerAddress;
    //         customer.salary = customerSalary;
    //
    //     }
    // }

    let newCustomer = {
        id : id,
        name : name,
        address : address,
        salary : salary
    };

    const jsonObject=JSON.stringify(newCustomer);
    $.ajax({
        url:"http://localhost:8080/app/customers",
        method:"PUT",
        data:jsonObject,
        contentType:("application/json"),

        success: function (resp,jqxhr){
            if (jqxhr.status==201){
                alert(jqxhr.responseText);
            }
        },
        error: function (error){
            console.log("Error",error);
        }
    });
}