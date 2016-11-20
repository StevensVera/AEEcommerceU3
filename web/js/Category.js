/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
     $.ajax({
        url:'GetCategorys',
        type:'GET',
        dataType:'json'
    }).done(function(json) {
        console.log('si');
        if(json.code === 200){
           console.log('si entro');
           var roles=$.parseJSON(json.msg);
           roles.forEach(function(rol){
               $('<option>'+rol.categoryname+'</<option>').attr('value',rol.categoryid).appendTo('#cbCategory');
               //$('<option></<option>',{text:'rol.rolename'}).attr('value',rol.roleid).appendTo('#cbRoles');
           });
           
          
          }
    });
    
    $("#frmcategory").validate({
        rules: {
            categoryname: {
                minlength: 3,
                maxlength: 20,
                required: true,
            },
        },
        messages: {
            categoryname: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo",
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
            newRole();
            return false;
        }

    });
    $("#frmEditCategory").validate({
        rules: {
            categoryname: {
                minlength: 3,
                maxlength: 20,
                required: true,
            }
        },
        messages: {
            categoryname: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo",
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
            updateCategory();
            return false;
        }
        

    });
    
    
    
    
    $('#tbCategory').DataTable({
        ajax:{
            url:'GetCategorys',
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
               data: function(row){
                   return numeral(row.categoryid).format('$0,0.00');
                   
               }
           },
           {
               data: "categoryname"
           },
           {
               data: function(row){
                   console.log(row);
                   str="<div align='center'>";
                   str+= "<button id='btnBorrar' class = 'btn btn-danger btn-xs' onClick = 'CategoryConfirm("+row['categoryid']+")'>Borrar</button>";
                   str+= "&nbsp;<button id='btnEditar' class = 'btn btn-success btn-xs' onClick = 'showCategory("+row['categoryid']+",\""+row['categoryname']+"\")'>Modificar</button>";
                   str+="</div>"
                   return str;
               }
           }
       ]
    });
    
     $('#btnModificar').on('click', function () {
        $('#frmEditCategory').submit();
    });
});
/*
function updateRole(id,name){
        $.ajax({
        url:'UpdateRole',
        type:'post',
        data: {
            roleid: id,
            rolename:name
        }
        //data: $('#frmEditRole').serialize(),
    }).done(function(eljson){
        if(eljson.code===200){
            $('#tbRoles').dataTable().api().ajax.reload();
            //$('#rolename2').val('');
            $('#modalRole').modal('hide');
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
*/
function updateCategory() {
    $.ajax(
            {
                url: "UpdateCategory",
                type: "post",
                data: {categoryid: $('#categoryid').val(), categoryname: $('#categoryname2').val()}
            }
    ).done(
            function (data) {
                if (data.code === 200) {

                    $.growl.notice({message: data.msg});
                    $('#tbCategory').dataTable().api().ajax.reload();
                    $('#categoryname2').val('');
                    $('#modalCategory').modal('toggle');
                } else
                    $.growl.error({message: data.msg});
            }
    ).fail(
            function () {
                $.growl.error({message: "Algo va mal!!!"});
            }
    );
}

function showCategory(categoryid,categoryname){
    $('#categoryid').val(categoryid);
    $('#categoryname2').val(categoryname);
    $('#modalCategory').modal('show');
    //updateRole(categoryid,$('#rolename2').val());
    
}
function deleteCategory(categoryid){
    $.ajax({
        url:'DeleteCategory',
        type:'post',
        data: {
            'categoryid':categoryid,
        }
    }).done(function(eljson){
        if(eljson.code===200){
            $('#tbCategory').dataTable().api().ajax.reload();
            $('#categoryname').val('');
            $.growl.notice({
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

function CategoryConfirm(categoryid){
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
          deleteCategory(categoryid);
    }
       
    }
});   
}

function RoleBtnRole(categoryid){
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
                deleteCategory(categoryid);
    }
       
    }
});   
}

function newRole() {
    $.ajax({
        url: "CreateCategory",
        type: "post",
        data: $("#frmcategory").serialize(),
    }).done(function (eljson) {
        if (eljson.code === 200){
            $('#tbRoles').dataTable().api().ajax.reload();
            $('#categoryname').val('');
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
