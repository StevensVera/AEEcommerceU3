/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {
     /*$.ajax({
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
    */
    $("#frmCompanys").validate({
        rules: {
            companyname: {
                minlength: 3,
                maxlength: 20,
                required: true
            },
             neighborhood: {
                minlength: 3,
                maxlength: 20,
                required: true
            },
             zipcode: {
                minlength: 3,
                maxlength: 20,
                required: true
            },
            city: {
                minlength: 3,
                maxlength: 20,
                required: true
            },
            country: {
                minlength: 3,
                maxlength: 20,
                required: true
            },
            state: {
                minlength: 3,
                maxlength: 20,
                required: true
            },
             region: {
                minlength: 3,
                maxlength: 20,
                required: true
            },
            street: {
                minlength: 3,
                maxlength: 20,
                required: true
            },
            streetnumber: {
                minlength: 3,
                maxlength: 20,
                required: true
            },
            phone: {
                minlength: 3,
                maxlength: 20,
                required: true
            },
            rfc: {
                minlength: 3,
                maxlength: 20,
                required: true
            },
            logo: {
                minlength: 3,
                maxlength: 20,
                required: true
            }
       
        },
        messages: {
            companyname: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo"
            },
            neighborhood: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo"
            },
            zipcode: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo"
            },
            city: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo"
            },
            country: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo"
            },
            state: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo"
            },
            region: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo"
            },
             street: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo"
            },
             streetnumber: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo"
            },
            rfc: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo"
            },
            logo: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo"
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
            newCompanys();
            return false;
        }

    });
    $("#frmEditCompanys").validate({
        rules: {
            rolename: {
                minlength: 3,
                maxlength: 20,
                required: true
            }
        },
        messages: {
            rolename: {
                minlength: "Minimo 3 caracteres",
                maxlength: "máximo 20 caracteres",
                required: "Se requiere este campo"
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
            updateCompany();
            return false;
        }

    });
    
    $('#tbCompanys').DataTable({
        ajax:{
            url:'GetCompanys',
            type: "POST",
                        //dataType: "json",
            contentType: "application/json",
            dataSrc: function (json){
                //console.log(eljson.msg);
                return $.parseJSON(json.msg);
            }
        },
        columns:[
           {
               data:"companyname"
           },
           {
               data: "neighborhood"
           },
           {
               data: "zipcode"
           },
           {
               data: "city"
           },
           {
               data: "country"
           },
           {
               data: "state"
           },
           {
               data: "region"
           },
           {
               data: "street"
           },
           {
               data: "streetnumber"
           },
           {
               data: "phone"
           },
           {
               data: "rfc"
           },
           {
               data: "logo"
           },
           
        {
               data: function(row){
                   console.log(row);
                   str="<div align='center'>";
                   str+= "<button id='btnBorrar' class = 'btn btn-danger btn-xs' onClick = 'CompanyConfirm("+row['companyid']+")'>Borrar</button>";
                   //str+= "&nbsp;<button id='btnEditar' class = 'btn btn-success btn-xs' onClick = 'showRole("+row['roleid']+",\""+row['rolename']+"\")'>Modificar</button>";
                   str+="</div>";
                   return str;
               }
           }
       ]
    });
     $('#btnModificar').on('click', function () {
        $('#frmEditCompany').submit();
    });
});


function updateCompany() {
    $.ajax(
            {
                url: "UpdateCompany",
                type: "post",
                data: {companyid: $('#companyid').val(),companyname: $('#companyname').val()
                      ,neighborhood: $('#neighborhood').val(),zipcode: $('#zipcode').val()
                      ,city: $('#city').val(),country: $('#country').val(),state: $('#state').val()
                      ,region: $('#region').val(),street: $('#street').val(),streetnumbert: $('#streetnumber').val()
                      ,phone: $('#phone').val(),rfc: $('#rfc').val(),logo: $('#logo').val()}
            }
    ).done(
            function (data) {
                if (data.code === 200) {

                    $.growl.notice({message: data.msg});
                    $('#tbCompanys').dataTable().api().ajax.reload();
                    //$('#rolename2').val('');
                    $('#companyname','#neighborhood','#zipcode','#city','#country','#state','#region','#street','#streetnumber','#phone','#rfc','#logo').val('');
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
function deleteCompanys(companyid){
    $.ajax({
        url:'DeleteCompany',
        type:'post',
        data: {
            'companyid':companyid
        }
    }).done(function(eljson){
        if(eljson.code===200){
            $('#tbCompanys').dataTable().api().ajax.reload();
            $('#companyname','#neighborhood','#zipcode','#city','#country','#state','#region','#street','#streetnumber','#phone','#rfc','#logo').val('');
            $.growl.notice({
                title: eljson.detail,
                message: "Eliminado",
                location: 'bc'
            });
        }
        else
            $.growl.error({
                title: eljson.msg,
                message: "Error",
                location: 'bc'
            });
    }).fail();
}

function CompanyConfirm(companyid){
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
        deleteCompanys(companyid);
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
        deleteCompanys(roleid);
    }
       
    }
});   
}

function newCompanys() {
    $.ajax({
        url: "NewCompany",
        type: "post",
        data: $("#frmCompanys").serialize()
    }).done(function (eljson) {
        if (eljson.code === 200){
            $('#tbCompanys').dataTable().api().ajax.reload();
            $('#companyname','#neighborhood','#zipcode','#city','#country','#state','#region','#street','#streetnumber','#phone','#rfc','#logo').val('');
            $.growl.notice({
                title: eljson.detail,
                message: "Todo bien",
                location: 'bc'
            });
        }
        else
            $.growl.error({
                title: eljson.msg,
                message: "Error",
                location: 'bc'
            });
    }).fail();
}