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
    
    $("#frmrole").validate({
        rules: {
            rolename: {
                minlength: 3,
                maxlength: 20,
                required: true,
            },
        },
        messages: {
            rolename: {
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
    $("#frmEditRole").validate({
        rules: {
            rolename: {
                minlength: 3,
                maxlength: 20,
                required: true,
            }
        },
        messages: {
            rolename: {
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
            updateRole();
            return false;
        }
        

    });
    
    
    
    
    $('#tbRoles').DataTable({
        ajax:{
            url:'GetRoles',
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
                   return numeral(row.roleid).format('$0,0.00');
                   
               }
           },
           {
               data: "rolename"
           },
           {
               data: function(row){
                   console.log(row);
                   str="<div align='center'>";
                   str+= "<button id='btnBorrar' class = 'btn btn-danger btn-xs' onClick = 'RoleConfirm("+row['roleid']+")'>Borrar</button>";
                   str+= "&nbsp;<button id='btnEditar' class = 'btn btn-success btn-xs' onClick = 'showRole("+row['roleid']+",\""+row['rolename']+"\")'>Modificar</button>";
                   str+="</div>"
                   return str;
               }
           }
       ]
    });
    
     $('#btnModificar').on('click', function () {
        $('#frmEditRole').submit();
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
function updateRole() {
    $.ajax(
            {
                url: "UpdateRole",
                type: "post",
                data: {roleid: $('#roleid').val(), rolename: $('#rolename2').val()}
            }
    ).done(
            function (data) {
                if (data.code === 200) {

                    $.growl.notice({message: data.msg});
                    $('#tbRoles').dataTable().api().ajax.reload();
                    $('#rolename2').val('');
                    $('#modalRole').modal('toggle');
                } else
                    $.growl.error({message: data.msg});
            }
    ).fail(
            function () {
                $.growl.error({message: "Algo va mal!!!"});
            }
    );
}

function showRole(roleid,rolename){
    $('#roleid').val(roleid);
    $('#rolename2').val(rolename);
    $('#modalRole').modal('show');
    //updateRole(roleid,$('#rolename2').val());
    
}
function deleteRole(roleid){
    $.ajax({
        url:'DeleteRole',
        type:'post',
        data: {
            'roleid':roleid,
        }
    }).done(function(eljson){
        if(eljson.code===200){
            $('#tbRoles').dataTable().api().ajax.reload();
            $('#rolename').val('');
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

function RoleConfirm(roleid){
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

function newRole() {
    $.ajax({
        url: "CreateRole",
        type: "post",
        data: $("#frmrole").serialize(),
    }).done(function (eljson) {
        if (eljson.code === 200){
            $('#tbRoles').dataTable().api().ajax.reload();
            $('#rolename').val('');
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
 User:{
  id: 1
  *
  *
  companyid:{
        companyid:1
        companyname:nombre
  }
        getUsers(){
            "user.listall"
               for(listaUser.size()){
                uv.setid(list.gct(i).getId())
                 listeuser.add(uv)
               }
        }
 
 }
userview {
company:company
}
  
 
 */