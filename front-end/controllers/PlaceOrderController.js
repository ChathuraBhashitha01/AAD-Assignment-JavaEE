loadCustomerIDs();
loadItemsCodes();
loadOrderIDs();
genarateOrderIDs();

setCurrentDate();

$("#navPlaceOrder").click(function (){
    loadCustomerIDs();
    loadItemsCodes();
    $("#navPlaceOrder").css( "font-weight","bold")
    $("#navCustomer").css( "font-weight","normal")
    $("#navItem").css( "font-weight","normal")
    $("#navHome").css( "font-weight","normal")
    $("#btnOrderDetails").css('display','block');
    $("#frmOrderDetails").css('display','none');
});

$("#btnOrderDetails").click(function (){
    $("#frmOrderDetails").css('display','block');
    loadOrderIDs();
});

$("#btnOrderDetailBack").click(function (){
    $("#frmOrderDetails").css('display','none');
});

$("#btnPurchase").click(function (){
    placeOrder();
    clearPlaceOrderInputField();
    genarateOrderIDs();
    setCurrentDate();
})

function loadCustomerIDs(){
    $("#cmbCustomer").empty();
    for (let i = 0; i <customerDB.length ; i++) {
        let id=customerDB[i].id;
        $("#cmbCustomer").append("<option >"+id +"</option>");
    }
}

$("#cmbCustomer").click(function () {
    var customer = searchCustomer($(this).val());
    $("#txtCustomerName").val(customer.name);
    $("#txtCustomerAddress").val(customer.address);
    $("#txtCustomerSalary").val(customer.salary);


});

function loadItemsCodes(){
    $("#cmdItems").empty();
    for (let i = 0; i <itemDB.length ; i++) {
        let id=itemDB[i].code;
        $("#cmdItems").append("<option >"+id +"</option>");
    }
}

$("#cmdItems").click(function () {
    var item = searchItem($(this).val());
    $("#txtGetItemName").val(item.description);
    $("#txtGetItemPrice").val(item.unitPrice);
    $("#txtGetQtyOnHand").val(item.qtyOnHand);

});

$("#btnAddItem").click(function () {
    let id = $("#cmdItems").val();
    let name = $("#txtGetItemName").val();
    let price = $("#txtGetItemPrice").val();
    let qty = $("#txtOrderQty").val();
    let total = parseFloat(price) * parseFloat(qty);
    let allTotal = 0;
    let exitItem=true;


    $('#tblPlaceOrder>tr').each(function () {
        let alreadyAddedId=$(this).children().eq(0).text();
        if (id==alreadyAddedId) {
            $(this).children().eq(0).text(id);
            $(this).children().eq(1).text(name);
            $(this).children().eq(2).text(price);
            $(this).children().eq(3).text(qty);
            $(this).children().eq(4).text(qty * price);
            exitItem=false;
        }else {

        }
    });


    if (exitItem) {
        let row = `<tr>
                 <td>${id}</td>
                 <td>${name}</td>
                 <td>${price}</td>
                 <td>${qty}</td>
                 <td>${total}</td>
                </tr>`;

        $("#tblPlaceOrder").append(row);
    }

    $('#tblPlaceOrder>tr').each(function () {
        let total = $(this).children().eq(4).text();
        allTotal += parseFloat(total);
    });

    $("#txtTotal").text(allTotal);
    $("#txtSubtotal").text(allTotal);
    $("#txtOrderQty").val(0);
    removeEvent();
});

function placeOrder(){
    let orderId=$("#txtOrderId").val();
    if(searchOrder(orderId.trim()) == undefined) {
        let cusId = $("#cmbCustomer").val();
        let date = $("#txtDate").val();
        let total = $("#txtSubtotal").val();

        let orderDetailArray = [];
        let code = "";
        let qty = 0;
        let price = 0;
        $('#tblPlaceOrder>tr').each(function () {
            code = $(this).children().eq(0).text();
            qty = $(this).children().eq(3).text();
            price = $(this).children().eq(2).text();

            let orderDetail = {
                oid: orderId,
                code: code,
                qty: qty,
                unitPrice: price
            };
            orderDetailArray.push(orderDetail);
        });

        const placeOrder = {
            orderID: orderId,
            date: date,
            customerID: cusId,
            total: total,
            orderDetails: orderDetailArray
        };
        const jsonObject = JSON.stringify(placeOrder);
        $.ajax({
            url: "http://localhost:8080/app/placeorders",
            method: "POST",
            data: jsonObject,
            contentType: ("application/json"),

            success: function (resp, jqxhr) {
                console.log("Success", resp);
                if (jqxhr.status == 201) {
                    alert(jqxhr.responseText);
                }
            },
            error: function (error) {
                console.log("Error", error);
            }
        });
        clearCustomerInputField();
    }else {
        alert("Order id already exits.!");
        clearCustomerInputField();
    }
};
function searchOrder(id){
    return orderDB.find(function (order){
        return order.oid==id;
    });
}

$("#txtCash").on("keydown keyup input", function () {
    setBalance();
});

$("#txtDiscount").on("keydown keyup input", function (e){
    let total = $("#txtTotal").text();
    let cash=$("#txtCash").text();
    if(total>0){
        let discount = $(this).val();
        let discountMoney = (total/100*discount);
        total -= discountMoney;
        let balance=cash-total;
        $("#txtSubtotal").text(total);
        setBalance();
    }

});

function setBalance() {
    let subtotal= $("#txtSubtotal").text();
    let cashText = $("#txtCash").val();
    if (!isNaN(cashText)) {
        let balance = cashText - subtotal;
        $("#txtBalance").val(balance);
    } else {
        $("#txtBalance").val("0");
    }
}

let idCounts=1;
function genarateOrderIDs(){
    if (orderDB.length==0){
        $("#txtOrderId").val("OR00-001");
    }else {
        if (orderDB.length > 0) {
            idCounts++;
            $("#txtOrderId").val("OR00-00" + idCounts);
        }
        if (orderDB.length >= 10) {
            idCounts++;
            $("#txtOrderId").val("OR00-0" + idCounts);
        }
        if (orderDB.length >= 100) {
            idCounts++;
            $("#txtOrderId").val("OR00-" + idCounts);
        }
    }
}

function removeEvent() {
    $('#tblPlaceOrder>tr').on('dblclick',function () {
        $(this).remove();
    });
}

function setCurrentDate(){
    // let currentdate = new Date();
    // let date =currentdate.getDay() + "/" + currentdate.getMonth()
    //     + "/" + currentdate.getFullYear();

    const dateString = new Date(Date.now()).toLocaleString();
    const todaysDate = dateString.slice(0,3).match(/[0-9]/i) ? dateString.split(' ')[0].split(',')[0] : dateString.split(' ')[1] + " " + dateString.split(' ')[2] + " " + dateString.split(' ')[3];
    $("#txtDate").val(todaysDate);
}

function loadOrderIDs(){
    $("#cmbOrderID").empty();
    $.ajax({
        url:"http://localhost:8080/app/orders",
        method:"GET",
        dataType:"json",
        success:function (resp){
            for (const order of resp) {
                $("#cmbOrderID").append("<option >"+order.oid+"</option>");
                const orderDetails={
                    oid:order.oid,
                    date:order.date,
                    customerID:order.id,
                    total: order.total,
                    orderDetails:order.orderDetails
                };
                orderDB.push(orderDetails);
            }
        }
    });
}
$("#cmbOrderID").click(function () {
    let orderID=$("#cmbOrderID").val();
    $("#tblOrderDetails>tr").remove();
    for (let i = 0; i < orderDB.length; i++) {
        if (orderDB[i].oid==orderID) {
            let date = orderDB[i].date;
            let id = orderDB[i].customerID;

            $("#txtOrderDate").val(date);
            $("#txtCustomer").val(id);

            for (let j = 0; j < orderDB[i].orderDetails.length; j++) {
                let code = orderDB[i].orderDetails[j].code;
                let qty = orderDB[i].orderDetails[j].qty;
                let price = orderDB[i].orderDetails[j].unitPrice;

                let row = `<tr>
                    <td>${orderID}</td>
                    <td>${code}</td>
                    <td>${qty}</td>
                    <td>${price}</td>
                    </tr>`
                $("#tblOrderDetails").append(row);
            }
        }
    }
});