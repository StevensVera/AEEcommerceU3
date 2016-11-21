/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
   
     $.ajax({
        url:'GetRoles',
        type:'GET',
        dataType:'json'
    }).done(function(json) {
        console.log('si');
        if(json.code === 200){
           console.log('si entro');
           var roles=$.parseJSON(json.msg);
           roles.forEach(function(rol){
               $('<option>'+rol.rolename+'</<option>').attr('value',rol.roleid).appendTo('#cbRoles');
               //$('<option></<option>',{text:'rol.rolename'}).attr('value',rol.roleid).appendTo('#cbRoles');
           });
          }
    });
    
     $.ajax({
        url: 'GetCategorys',
        type: 'get',
        dataType: 'json'
    }).done(function (eljason) {
        if (eljason.code == 200) {
            console.log('si entra al json');
            var categories = $.parseJSON(eljason.msg);
            categories.forEach(function (item) {
                $('<option></option>', {text: item.categoryname}).attr('value', item.categoryid).appendTo('#cat');
                $('<option></option>', {text: item.categoryname}).attr('value', item.categoryid).appendTo('#modalProduct #cat');
            });
        }

    }).fail();
    
     
    
    $("#frmrole").validate({
        rules: {
            code: {
                minlength: 3,
                maxlength: 20,
                required: true
            },
            image:{
                required: true
            },
            productname: {
                minlength: 3,
                maxlength: 20,
                required: true
            },
            brand: {
                minlength: 3,
                maxlength: 20,
                required: true
            },
            purchprice: {
                minlength: 1,
                maxlength: 20,
                required: true
            },
            stock: {
                minlength: 1,
                maxlength: 20,
                required: true
            },
            salepricemin: {
                minlength: 1,
                maxlength: 20,
                required: true
            },
            reorderpoint: {
                minlength: 1,
                maxlength: 20,
                required: true
            },
            currency: {
                minlength: 3,
                maxlength: 20,
                required: true
            },
            /*
            categoryid: {
                minlength: 1,
                maxlength: 20,
                required: true
            }, */
            salepricemay: {
                minlength: 1,
                maxlength: 20,
                required: true
            }
        },
        messages: {
            code: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo",
            },
            productname: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo",
            },
            brand: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo",
            },
            purchprice: {
                minlength: "Minimo 1 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo",
            },
             stock: {
                minlength: "Minimo 1 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo",
            },
             salepricemin: {
                minlength: "Minimo 1 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo",
            },
             reorderpoint: {
                minlength: "Minimo 1 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo",
            },
             currency: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo",
            },/*
             categoryid: {
                minlength: "Minimo 1 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo",
            }, */ 
            salepricemay: {
                minlength: "Minimo 1 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo",
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function (element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function (error, element) {
            if (element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        },
        submitHandler: function (form) {
            newProduct();   /* instaciamos la NewProduct  */
            return false;
        }


    });
    
    $("#btnModificar").on("click", function () {
        $("#frmEditProduct").submit();
    });
    
    
    $("#frmEditProduct").validate({
        lang: 'es',
        rules: {
            productname: {
                minlength: 3,
                maxlength: 40,
                required: true,
            },
            brand: {
                minlength: 3,
                maxlength: 40,
                required: true,
            },
            cat: {
                required: true,
                number: true,
            },
            code: {
                required: true,
                minlength: 3,
                maxlength: 20,
            },
            currency: {
                required: true,
                //minlength: 3,
                maxlength: 3,
            },
            purchprice: {
                //required: true,
                number: true,
            },
            salepricemay: {
                required: true,
                number: true,
            },
            salepricemin: {
                //required: true,
                number: true,
            },
            reorderpoint: {
                //required: true,
                number: true,
            },
            stock: {
                //required: true,
                number: true,
            },
            image2: {
                //required: true,
                required: true,
            },
            
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        unhighlight: function (element) {
            $(element).closest('.form-group').removeClass('has-error');
        },
        errorElement: 'span',
        errorClass: 'help-block',
        errorPlacement: function (error, element) {
            if (element.parent('.input-group').length) {
                error.insertAfter(element.parent());
            } else {
                error.insertAfter(element);
            }
        },
        submitHandler: function (form) {
            UpdateProduct();
            return false;
        }

    });
    
    $('#tbProduct').DataTable({
        ajax:{
            url:'GetProducts',
            type: "POST",
                        //dataType: "json",
            contentType: "application/json",
            dataSrc: function (eljson){
                //console.log(eljson.msg);
                return $.parseJSON(eljson.msg);
            },   
        },
        columns:[
           {
               data: "code"
           },
           {
             data:"photo",
             "render":function (data, type, row){
                  return '<img width="50px" height="50px" src="ProductImage?image='+data+'" />';
              }
           },
           {
               data: "productname"
           },
           {
               data: "brand"
           },
           {
               data: "purchprice"
           },
           {
               data: "stock"
           },
           {
               data: "salepricemin"
           },
           {
               data: "reorderpoint"
           },
           {
               data: "currency"
           },
           /*
            {
               data: "categoryid"
           },
           */
          {
                data: function (row) {
                    return row['categoryid']['categoryname'];
                    
                }
            },
          
           {
               data: "salepricemay"
           },
           
        {
                "data": function (row) {
                    var str = '<div align="center"><button id="btnEliminar" title="Eliminar" class="btn btn-danger btn-xs" onclick="deleteProduct(\'' + row.productid + '\');" > <i class="glyphicon glyphicon-remove"></i></button>';
                    str += '<button id="btnGuardar" title="Actualizar" class="btn btn-success btn-xs" onclick="showProduct' +'(\''
                            + row.productid + '\',\'' + row.productname + '\',\'' + row.brand + '\',\'' + row.categoryid.categoryid + '\',\'' 
                            +row.code + '\',\'' + row.currency + '\',\'' + row.purchprice + '\',\'' + row.salepricemay + '\',\'' 
                            +row.salepricemin + '\',\'' + row.reorderpoint + '\',\'' + row.stock + '\',\'' + row.photo + '\'  );" ><i class="glyphicon glyphicon-edit"></i></button></div>';
                    return str;
                }
            }
          
       ]
    });
});

function updateProduct2(id,code,productname,brand,purchprice,
        stock,salepricemin,reorderpoint,currency,categoryid,salepricemay){
        $.ajax({
        url:'UpdateProduct',   
        type:'post',
        data: {
            productid:id,
            code:code,
            productname:productname,
            brand:brand,
            purchprice:purchprice,
            stock:stock,
            salepricemin:salepricemin,
            reorderpoint:reorderpoint,
            currency:currency,
            categoryid:categoryid,
            salepricemay:salepricemay     
        }
        //data: $('#frmEditRole').serialize(),
    }).done(function(eljson){
        if(eljson.code===200){
            $('#tbProduct').dataTable().api().ajax.reload();
            //$('#rolename2').val('');
            $('#modalProduct').modal('hide');
            $.growl().notice({
                title: eljson.detail,
                message: "Actualizado",
                location: 'bc',
            });
        }
        else
            $.growl.error({
                title: eljson.msg,
                message: "Error",
                location: 'bc',
            });
    }).fail();
}

function showProduct2(code,productname,brand,purchprice,stock,salepricemin,reorderpoint,currency,categoryid,salepricemay,photo){
    $('#code').val(code);
    $('#productname').val(productname);
    $('#brand').val(brand);
    $('#purchprice').val(purchprice);
    $('#stock').val(stock);
    $('#salepricemin').val(salepricemin);
    $('#reorderpoint').val(reorderpoint);
    $('#currency').val(currency);
    $('#categoryid').val(categoryid);
    $('#salepricemay').val(salepricemay);
    //$('#image').val(photo);
    $('#modalRole').modal('show');
    //updateRole(roleid,$('#rolename2').val());
    
}
function showProduct(productid, productname, brand, cat, code, currency, purchprice, salepricemay, salepricemin, reorderpoint, stock, photo) {
    $('#modalProduct #productid').val(productid);
    $('#modalProduct #productname').val(productname);
    $('#modalProduct #brand').val(brand);
    $('#modalProduct #cat').val(cat);
    $('#modalProduct #code').val(code);
    $('#modalProduct #currency').val(currency);
    $('#modalProduct #purchprice').val(purchprice);
    $('#modalProduct #salepricemay').val(salepricemay);
    $('#modalProduct #salepricemin').val(salepricemin);
    $('#modalProduct #reorderpoint').val(reorderpoint);
    $('#modalProduct #stock').val(stock);
    $('#modalProduct #image2').val(photo);

    $('#modalProduct').modal('show');
}


function DeleteProduct(productid){
    $.ajax({
        url:'DeleteRole',
        type:'post',
        data: {
            'productid':productid,
        }
    }).done(function(eljson){
        if(eljson.code===200){
            $('#tbProduct').dataTable().api().ajax.reload();
            $('#code','#productname','#brand','#purchprice','#stock','#salepricemin','#reorderpoint','#currency','#salepricemay').val('');            $.growl.notice({
                title: eljson.detail,
                message: "Eliminado",
                location: 'bc',
            });
        }
        else
            $.growl.error({
                title: eljson.msg,
                message: "Error",
                location: 'bc',
            });
    }).fail();
}

function RoleConfirm(productid){
    bootbox.confirm({
    message: "Seguro que deseas eliminar",
    buttons: {
        confirm: {
            label: 'Yes',
            className: 'btn-success'
        },
        cancel: {
            label: 'No',
            className: 'btn-danger'
        }
    },
    callback: function (result) {
 if(result===true){
        DeleteProduct(productid);
    }
       
    }
});   
}

function RoleBtnRole(roleid){
    bootbox.confirm({
    message: "Seguro que deseas eliminar",
    buttons: {
        confirm: {
            label: 'Yes',
            className: 'btn-success'
        },
        cancel: {
            label: 'No',
            className: 'btn-danger'
        }
    },
    callback: function (result) {
 if(result===true){
        deleteRole(roleid);
    }
       
    }
});   
}


function newProduct() {
    $.ajax({
        url: "NewProduct",
        type: "post",
        data: $("#frmrole").serialize(),
    }).done(function (eljson) {
        if (eljson.code === 200){
            $('#tbProduct').dataTable().api().ajax.reload();
            $('#code','#image','#productname','#brand','#purchprice','#stock','#salepricemin','#reorderpoint','#currency','#salepricemay').val('');
            /*
            $('#productname').val('');
            $('#brand').val('');
            $('#purchprice').val('');
            $('#stock').val('');
            $('#salepricemin').val('');
            $('#reorderpoint').val('');
            $('#currency').val('');
            $('#categoryid').val('');
            $('#salepricemay').val('');
            */
            $.growl.notice({
                title: eljson.detail,
                message: "Todo bien",
                location: 'bc',
            });
        }
        else
            $.growl.error({
                title: eljson.msg,
                message: "Error",
                location: 'bc',
            });
    }).fail();
}

/*
function newProduct2() {
    var data = new FormData($('#frmproduct')[0]);
    $.ajax({
        url: "NewProduct",
        type: "post",
        data: data,
        contentType: false,
        processData: false,
    }).done(function (eljson) {
        console.log(eljson);
        if (eljson.code === 200) {
            $('#tbProducts').dataTable().api().ajax.reload();
            $('#tbProducts').dataTable().api().ajax.reload();
            $('#productname').val('');
            $('#brand').val('');
            $('#cat').val('');
            $('#code').val('');
            $('#currency').val('');
            $('#purchprice').val('');
            $('#salepricemay').val('');
            $('#salepricemin').val('');
            $('#reorderpoint').val('');
            $('#stock').val('');
            $.growl.notice({
                title: eljson.detail,
                message: "Todo bien",
                location: 'bc',
            });
        } else
            $.growl.error({
                title: eljson.msg,
                message: "Error",
                location: 'bc',
            });
    }).fail();
}
*/

function deleteProduct(productid) {
    bootbox.confirm({
        message: "¿Seguro que desea eliminar el registro?",
        buttons: {
            confirm: {
                label: 'Si',
                className: 'btn-success'
            },
            cancel: {
                label: 'NO',
                className: 'btn-danger'
            }
        },
        callback: function (result) {
            if (result == true)
                $.ajax({
                    url: 'DeleteProduct',
                    type: 'post',
                    data: {
                        'productid': productid,
                    }
                }).done(function (eljson) {
                    if (eljson.code === 200) {
                        $('#tbProducts').dataTable().api().ajax.reload();
                        $('#productname').val('');
                        $('#brand').val('');
                        $('#cat').val('');
                        $('#code').val('');
                        $('#currency').val('');
                        $('#purchprice').val('');
                        $('#salepricemay').val('');
                        $('#salepricemin').val('');
                        $('#reorderpoint').val('');
                        $('#stock').val('');
                        $('#image').val('');
                        $.growl.notice({
                            title: eljson.detail,
                            message: "Eliminado",
                            location: 'bc',
                        });
                    } else
                        $.growl.error({
                            title: eljson.msg,
                            message: "Error",
                            location: 'bc',
                        });
                }).fail();
        }
    });

}

function UpdateProduct() {
    $.ajax({
        url: 'UpdateProduct',
        type: 'post',
        data: $('#frmEditProduct').serialize()
    }).done(function (eljson) {
        if (eljson.code === 200) {
            $('#tbProducts').dataTable().api().ajax.reload();
            //$('#productname2').val('');
            $('#modalProduct').modal('hide');
            $.growl.notice({
                title: eljson.detail,
                message: "Actualizado",
                location: 'bc',
            });

        } else
            $.growl.error({
                title: eljson.msg,
                message: "Error",
                location: 'bc',
            });
    }).fail();
}