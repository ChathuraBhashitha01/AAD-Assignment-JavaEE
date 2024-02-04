//getAllItems();
loadItemCodes();

$("#navItem").click(function (){
    $("#navCustomer").css( "font-weight","normal")
    $("#navPlaceOrder").css( "font-weight","normal")
    $("#navHome").css( "font-weight","normal")
    $("#navItem").css( "font-weight","bold")
    $("#btnOrderDetails").css('display','none');
});

$("#btnItemSave").click(function (){
    if (checkAll()) {
        saveItem();
    }
    else {
        alert("Faild Saved");
    }
});

$("#btnItemGetAll").click(function (){
    getAllItems();
});

$("#btnItemDelete").click(function (){
    deleteItem();
    // loadItemCodes();
    // getAllItems();
    // clearItemInputField();
});

$("#btnItemUpdate").click(function (){
    updateItem();
    // getAllItems();
    // loadItemCodes();
    // clearItemInputField();
});

$("#btnItemSearch").click(function (){
    let code=$("#cmbItemCode").val();
    for (let i = 0; i < itemDB.length; i++) {
        if (itemDB[i].code==code){
            $("#txtItemCode").val(itemDB[i].code)
            $("#txtItemName").val(itemDB[i].description)
            $("#txtItemPrice").val(itemDB[i].unitPrice)
            $("#txtItemQty").val(itemDB[i].qtyOnHand)
        }
    }
});

function saveItem() {
    let code = $("#txtItemCode").val();

    if (searchItem(code.trim()) == undefined){
        let name = $("#txtItemName").val();
        let price = $("#txtItemPrice").val();
        let qty = $("#txtItemQty").val();

        let newItem = {
            code : code,
            description : name,
            unitPrice : price,
            qtyOnHand : qty
        };

        const jsonObject=JSON.stringify(newItem);
        $.ajax({
            url:"http://localhost:8080/app/items",
            method:"POST",
            data:jsonObject,

            success:function (resp,jqxhr){
                if (jqxhr.status==201){
                    alert(jqxhr.responseText);
                }
            }
        });

        // loadItemCodes();
        // getAllItems();
        // clearItemInputField();
    }
    else {
        alert("Item already exits.!");
        clearItemInputField();
    }
}

function searchItem(code){
    return itemDB.find(function(item){
        return item.code==code;
    });
}

function getAllItems(){
    $("#tblItem").empty();
    $.ajax({
        url:"http://localhost:8080/app/items",
        method:"GET",
        dataType:"json",
        success:function (resp){
            console.log("Success : ",resp)

            for (const item of resp) {

                const row = `<tr>
                                <td>${item.code}</td>
                                <td>${item.description}</td>
                                <td>${item.qtyOnHand}</td>
                                <td>${item.unitPrice}</td>
                            </tr>`;
                $('#tblItem').append(row);
                bindTrEvents()
            }
        },
        error:function (error){
            console.log("error : "+error)
        }
    });
}
function bindTrEvents() {
    $("#tblItem>tr").click(function (){
        let code=$(this).children().eq(0).text();
        let name=$(this).children().eq(1).text();
        let price=$(this).children().eq(2).text();
        let qty=$(this).children().eq(3).text();

        $("#txtItemCode").val(code)
        $("#txtItemName").val(name)
        $("#txtItemPrice").val(price)
        $("#txtItemQty").val(qty)
    });
}
function loadItemCodes(){
    $("#cmbItemCode").empty();
    for (let i = 0; i <itemDB.length ; i++) {
        let code=itemDB[i].code;
        $("#cmbItemCode").append("<option >"+code +"</option>");
    }
}

function deleteItem(){
    let id=$("#txtItemCode").val();
    $.ajax({
        url:"http://localhost:8080/app/items?id="+id,
        method:"DELETE",

        success:function (resp,jqxhr){
            if (jqxhr.status==201){
                alert(jqxhr.responseText);
            }
        }
    });
}

function updateItem(){
    let id=$("#txtItemCode").val();
    let name = $("#txtItemName").val();
    let price = $("#txtItemPrice").val();
    let qty = $("#txtItemQty").val();

    let newItem = {
        code : id,
        description : name,
        unitPrice : price,
        qtyOnHand : qty
    };

    const jsonObject=JSON.stringify(newItem);
    $.ajax({
        url:"http://localhost:8080/app/items",
        method:"PUT",
        data:jsonObject,

        success:function (resp,jqxhr){
            if (jqxhr.status==201){
                alert(jqxhr.responseText);
            }
        }
    });
}