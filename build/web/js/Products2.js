/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){
    $('#frmProduct').validate({
        rules: {
            code: {
                required: true,
                minlength: 3,
                maxlength: 20
            },
            productname: {
                required: true,
                minlength: 3,
                maxlength: 40
            },
            brand: {
                required: true,
                minlength: 2,
                maxlength: 40
            },
            purchprice: {
                required: true,
                number: true
            },
            stock: {
                required: true,
                number: true,
                digits: true
            },
            salepricemin: {
                required: true,
                number: true
            },
            reorderpoint: {
                required: true,
                number: true,
                digits: true
            },
            currency: {
                required: true,
                maxlength: 3
            },
            salepricemay: {
                required: true,
                number: true
            },
            img: {
                required: true,
                extension: 'jpg|png'
            }
        },
        messages: {
            code: {
                required: 'Campo requerido',
                minlength: 'Introduzca minimo 3 caracteres',
                maxlength: 'Introduzca maximo 20 caracteres'
            },
            productname: {
                required: 'Campo requerido',
                minlength: 'Introduzca minimo 3 caracteres',
                maxlength: 'Introduzca maximo 40 caracteres'
            },
            brand: {
                required: 'Campo requerido',
                minlength: 'Introduzca minimo 2 caracteres',
                maxlength: 'Introduzca maximo 40 caracteres'
            },
            purchprice: {
                required: 'Campo requerido',
                number: 'Introduzca numero valido'
            },
            stock: {
                required: 'Campo requerido',
                number: 'Introduzca numero valido',
                digits: 'Introduzca numero entero'
            },
            salepricemin: {
                required: 'Campo requerido',
                number: 'Introduzca numero valido'
            },
            reorderpoint: {
                required: 'Campo requerido',
                number: 'Introduzca numero valido',
                digits: 'Introduzca numero entero'
            },
            currency: {
                required: 'Campo requerido',
                maxlength: 'Introduzca maximo 3 caracteres'
            },
            salepricemay: {
                required: 'Campo requerido',
                number: 'Introduzca numero valido'
            },
            img: {
                required: 'Campo requerido',
                extension: 'Solo extensiones .jpg, .png'
            }
        },
        highlight: function (element) {
            $(element).closest('.col').addClass('has-error');
        },
        unhighlight: function (element) {
            $(element).closest('.col').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function (error, element) {
            if (element.parent(".input-group").length) {
                error.insertAfter(element.parent());
            }
            else {
                error.insertAfter(element);
            }
        },
        submitHandler: function(form){
            createProduct();
            return false;
        }
    });
    
    $('#tbproducts').DataTable({
        ajax: {
            url: 'GetProducts',
            dataSrc: function(json){
                return $.parseJSON(json.msg);
            }
        },
        language: {
            url: '//cdn.datatables.net/plug-ins/1.10.12/i18n/Spanish.json'
        },
        columns: [
            {data: 'productid'},
            {data: 'code'},
            {data: 'productname'},
            {data: 'brand'},
            {data: function (row){
                    return accounting.formatMoney(row['purchprice']);
            }},
            {data: 'stock'},
            {data: function (row){
                    return accounting.formatMoney(row['salepricemin']);
            }},
            {data: 'reorderpoint'},
            {data: 'currency'},
            {data: function (row){
                    return row['categoryid']['categoryname'];
            }},
            {data: function (row){
                    return accounting.formatMoney(row['salepricemay']);
            }},
            {data: function (row){
                    return "<img src='GetProductImage?img="+ row['img']+"' height='50' width='70'>";
            }},
            { data: function(row){
                    str = "<button id='btnEliminar' class='btn btn-danger btn-xs' onClick='confirm("
                            +row['productid']+
                            ")'>Eliminar"+
                            "</button>";
                    str += " <button id='btnEditar' class='btn btn-primary btn-xs' onClick='showProduct("
                            +row['productid']+
                            ",\""+row['code']+"\""+
                            ",\""+row['productname']+"\""+
                            ",\""+row['brand']+"\""+
                            ","+row['purchprice']+
                            ","+row['stock']+
                            ","+row['salepricemin']+
                            ","+row['reorderpoint']+
                            ",\""+row['currency']+"\""+
                            ","+row['categoryid']['categoryid']+
                            ","+row['salepricemay']+
                            ")'>Actualizar"+
                          "</button>";
                    return str;
            }}
        ]
    });
    
    $('#frmEditProduct').validate({
        rules: {
            code2: {
                required: true,
                minlength: 3,
                maxlength: 20
            },
            productname2: {
                required: true,
                minlength: 3,
                maxlength: 40
            },
            brand2: {
                required: true,
                minlength: 2,
                maxlength: 40
            },
            purchprice2: {
                required: true,
                number: true
            },
            stock2: {
                required: true,
                number: true,
                digits: true
            },
            salepricemin2: {
                required: true,
                number: true
            },
            reorderpoint2: {
                required: true,
                number: true,
                digits: true
            },
            currency2: {
                required: true,
                maxlength: 3
            },
            salepricemay2: {
                required: true,
                number: true
            },
            img2: {
                extension: 'jpg|png'
            }
        },
        messages: {
            code2: {
                required: 'Campo requerido',
                minlength: 'Introduzca minimo 3 caracteres',
                maxlength: 'Introduzca maximo 20 caracteres'
            },
            productname2: {
                required: 'Campo requerido',
                minlength: 'Introduzca minimo 3 caracteres',
                maxlength: 'Introduzca maximo 40 caracteres'
            },
            brand2: {
                required: 'Campo requerido',
                minlength: 'Introduzca minimo 2 caracteres',
                maxlength: 'Introduzca maximo 40 caracteres'
            },
            purchprice2: {
                required: 'Campo requerido',
                number: 'Introduzca numero valido'
            },
            stock2: {
                required: 'Campo requerido',
                number: 'Introduzca numero valido',
                digits: 'Introduzca numero entero'
            },
            salepricemin2: {
                required: 'Campo requerido',
                number: 'Introduzca numero valido'
            },
            reorderpoint2: {
                required: 'Campo requerido',
                number: 'Introduzca numero valido',
                digits: 'Introduzca numero entero'
            },
            currency2: {
                required: 'Campo requerido',
                maxlength: 'Introduzca maximo 3 caracteres'
            },
            salepricemay2: {
                required: 'Campo requerido',
                number: 'Introduzca numero valido'
            },
            img2: {
                extension: 'Solo extensiones .jpg, .png'
            }
        },
        highlight: function (element) {
            $(element).closest('.col').addClass('has-error');
        },
        unhighlight: function (element) {
            $(element).closest('.col').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function (error, element) {
            if (element.parent(".input-group").length) {
                error.insertAfter(element.parent());
            }
            else {
                error.insertAfter(element);
            }
        },
        submitHandler: function(form){
            updateProduct();
            return false;
        }
    });
    
    $('#btnModificar').on('click', function(){
        $('#frmEditProduct').submit();
    });
    
    $.ajax({
        url: 'GetCategorys',
        type: 'GET',
        dataType: 'JSON'
    }).done(function (json){
        if(json.code === 200){
            $.each($.parseJSON(json.msg), function (i, row){
                $('<option></option>', {text: row.categoryname}).attr('value',row.categoryid).appendTo('#categoryid');
                $('<option></option>', {text: row.categoryname}).attr('value',row.categoryid).appendTo('#categoryid2');
            });
        }
    });
});

function createProduct(){
    var data = new FormData($('#frmProduct')[0]);
    
    //Una funcion ajax consta de 3 partes
    //Peticion en si, cuando se hace bien o falla
    $.ajax({
        url: "CreateProduct",
        type: "post",
        data: data,
        contentType: false,
        processData: false
    }).done(function(json){
        if(json.code===200){
            $('#tbproducts').dataTable().api().ajax.reload();
            $('#frmProduct')[0].reset();
            $.growl.notice({
                title: json.detail,
                message: json.msg
            });
        }
        else
            $.growl.error({
                title: json.detail,
                message: json.msg
            });
    }).fail(function(){
        
    });
}

/*BOOTBOX*/
/*Sirve para los dialogos de confirmacion*/
function confirm(row){
    bootbox.confirm({
        title: "Confirmacion para eliminar producto",
        message: "¿Estas seguro que quieres eliminar?, No se podra deshacer.",
        buttons: {
            cancel: {
                label: '<i class="fa fa-times"></i> Cancelar'
            },
            confirm: {
                label: '<i class="fa fa-check"></i> Confirmar'
            }
        },
        callback: function (result) {
            if(result === true){
                deleteProduct(row);
            }
        }
    });
}

function deleteProduct(productid){
    //Una funcion ajax consta de 3 partes
    //Peticion en si, cuando se hace bien o falla
    $.ajax({
        url: "DeleteProduct",
        type: "post",
        data: {productid: productid}
    }).done(function(json){
        if(json.code===200){
            $('#tbproducts').dataTable().api().ajax.reload();
            $.growl.notice({
                title: json.detail,
                message: json.msg
            });
        }
        else{
            $.growl.error({
                title: json.detail,
                message: json.msg
            });
        }
    }).fail(function(){
        
    });
}

function showProduct(productid, code, productname, brand, purchprice, stock,
                salepricemin, reorderpoint, currency, categoryid, salepricemay){
    $('#productid').val(productid);
    $('#code2').val(code);
    $('#productname2').val(productname);
    $('#brand2').val(brand);
    $('#purchprice2').val(purchprice);
    $('#stock2').val(stock);
    $('#salepricemin2').val(salepricemin);
    $('#reorderpoint2').val(reorderpoint);
    $('#currency2').val(currency);
    $('#categoryid2').val(categoryid);
    $('#salepricemay2').val(salepricemay);
    
    $('#modalEditProduct').modal('show');
}

function updateProduct(){
    var data = new FormData($('#frmEditProduct')[0]);
    
    //Una funcion ajax consta de 3 partes
    //Peticion en si, cuando se hace bien o falla
    $.ajax({
        url: "UpdateProduct",
        type: "post",
        data: data,
        contentType: false,
        processData: false
    }).done(function(json){
        if(json.code===200){
            $('#tbproducts').dataTable().api().ajax.reload();
            $('#frmEditProduct')[0].reset();
            $.growl.notice({
                title: json.detail,
                message: json.msg
            });
            $('#modalEditProduct').modal('hide');
        }
        else
            $.growl.error({
                title: json.detail,
                message: json.msg
            });
    }).fail(function(){
        
    });
}