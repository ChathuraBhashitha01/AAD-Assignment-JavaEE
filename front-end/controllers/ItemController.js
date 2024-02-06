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
    $("#tblItem").empty();

    for (let i = 0; i < itemDB.length; i++) {
        let code=itemDB[i].code;
        let name=itemDB[i].description;
        let price=itemDB[i].unitPrice;
        let qty=itemDB[i].qtyOnHand;

        let row=`<tr>
                    <td>${code}</td>
                    <td>${name}</td>
                    <td>${price}</td>
                    <td>${qty}</td>
                </tr>`;
        $("#tblItem").append(row);
        bindTrEvents();
    }
});

$("#btnItemDelete").click(function (){
    deleteItem();
    loadItemCodes();
    clearItemInputField();
});

$("#btnItemUpdate").click(function (){
    updateItem();
    loadItemCodes();
    clearItemInputField();
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

        loadItemCodes();
        clearItemInputField();
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
    if (itemDB.length==0){
        $("#cmbItemCode").empty();
        $.ajax({
            url: "http://localhost:8080/app/items",
            method: "GET",
            dataType: "json",
            success: function (resp) {
                for (const item of resp) {
                    $("#cmbItemCode").append("<option >" + item.code + "</option>");

                    const itemDetails = {
                        code: item.code,
                        description: item.description,
                        qtyOnHand: item.qtyOnHand,
                        unitPrice: item.unitPrice
                    };
                    itemDB.push(itemDetails);
                }
            }
        });
    }else {
        $("#cmbItemCode").empty();
        for (let i = 0; i < itemDB.length; i++) {
            let id = itemDB[i].code;
            $("#cmdItems").append("<option >" + id + "</option>");
        }
    }
}

function deleteItem(){
    let id=$("#txtItemCode").val();
    if (searchItem(id) == undefined) {
        alert("No such Customer..please check the ID");
    } else {
        let consent = confirm("Do you really want to Delete this item.?");
        if (consent) {
            $.ajax({
                url: "http://localhost:8080/app/items?id=" + id,
                method: "DELETE",

                success: function (resp, jqxhr) {
                    if (jqxhr.status == 201) {
                        alert(jqxhr.responseText);
                    }
                }
            });
        }
    }
}

function updateItem(){
    let id=$("#txtItemCode").val();
    if (searchItem(id) == undefined) {
        alert("No such Customer..please check the ID");
    } else {
        let consent = confirm("Do you really want to update this item.?");
        if (consent) {
            let name = $("#txtItemName").val();
            let price = $("#txtItemPrice").val();
            let qty = $("#txtItemQty").val();

            let newItem = {
                code: id,
                description: name,
                unitPrice: price,
                qtyOnHand: qty
            };

            const jsonObject = JSON.stringify(newItem);
            $.ajax({
                url: "http://localhost:8080/app/items",
                method: "PUT",
                data: jsonObject,

                success: function (resp, jqxhr) {
                    if (jqxhr.status == 201) {
                        alert(jqxhr.responseText);
                    }
                }
            });
        }
    }
}