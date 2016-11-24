/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Este metodo se ejecuta cuando carga toda la pagina



$(function () {
    
     
    
    
    var x = 0;
    $('#tbDetVenta').DataTable({
        languaje: {
            url: "//cdn.datatables.net/plug-ins/1.10.10/i18n/Spanish.json"
        },
        ajax: {
            url: "GetCart",
            dataSrc: function (json) {
               console.log(json);
                if(json['msg']=="[]"){
                    $('#checkout').hide(true);  
                }
           
                return $.parseJSON(json['msg']);
            }
        },
        columns: [
            {data: function (row) {
                    return "<img src='" + row['photo'] + "' height=\"60\" width=\"60\"/>"
                    //<img src="casarural.jpg" alt="Los Tejos" />
                }},
            {data: "code"},
            {data: "productname"},
            {data: "quantity"},
            {data: function (row) {
                    str = " <div align='right'>";
                    str += accounting.formatMoney(row['purchprice']);
                    str += "</div>";
                    return str;
                }},
            {data: function (row) {

                    var importe = row['quantity'] * row['purchprice'];
                    str = " <div align='right'>";
                    str += accounting.formatMoney(importe);
                    str += "</div>";

                    return str;
                }},
            {data: function (row) {
                    //console.log(row);
                    str = " <div align='center'>"
                    str += "<button id='btnBorrar' class='btn btn-danger btn-xs' onClick='deleteProductCart(" + row['code'] + ")'><i class='glyphicon glyphicon-trash'></i>Eliminar</button>";
                    str += "</div>"
                    return str;

                }
            }],
        footerCallback: function (row, data, start, end, display) {
            var api = this.api(), data;

            // Remove the formatting to get integer data for summation
            var intVal = function (i) {
                console.log(i);
                return typeof i === 'string' ?
                        i.replace(/[\<div align='right'>$,</div>]/g, '') * 1 :
                        typeof i === 'number' ?
                        i : 0;
            };

            // Total over all pages
            total = api
                    .column(5)
                    .data()
                    .reduce(function (a, b) {
                        return intVal(a) + intVal(b);
                    }, 0);

            // Total over this page
            pageTotal = api
                    .column(5, {page: 'current'})
                    .data()
                    .reduce(function (a, b) {
                        return intVal(a) + intVal(b);
                    }, 0);

            // Update footer
            $(api.column(5).footer()).html(accounting.formatMoney(pageTotal) + ' ( ' + accounting.formatMoney(total) + ' total)'
                    );
            //console.log(total);
        }
    });

$('#checkout').on('click', function () {

        var hoy = new Date();
        var dd = hoy.getDate();
        var mm = hoy.getMonth() + 1; //hoy es 0!
        var yyyy = hoy.getFullYear();
        if (dd < 10) {
            dd = '0' + dd
        }
        if (mm < 10) {
            mm = '0' + mm
        }
        hoy = mm + '/' + dd + '/' + yyyy;
        
        
        
 
        CheckOut(total,hoy,2,2);
        remove();
        $('#tbDetVenta').dataTable().api().ajax.reload();
        $('#checkout').hide(true);
    });

});



function deleteProductCart(code) {

    swal({
        title: "¿Estas seguro que deseas eliminar este producto?", text: "",
        type: "warning", showCancelButton: true,
        confirmButtonColor: "#DD6B55", confirmButtonText: "Aceptar!",
        cancelButtonText: "Cancelar", closeOnConfirm: false,
        closeOnCancel: false
    }, function (isConfirm) {
        if (isConfirm) {
            var para = {
                "code": code
            };
            ///Comienza a Borrar    
            $.ajax({
                url: "DeleteCart",
                type: "post",
                /*Manda todo el formulario
                 * como mandar parametros por separado en data:
                 */
                data: para
            }).done(
                    function (data) {
                        // alert("Se realizo correctamente"+data.code); 
                        if (data.code === 200) {
                            $.growl.notice({message: data.msg + " " + data.details});
                            swal("Eliminado!", "El registro se elimino correctamente", "success");
                            $('#tbDetVenta').dataTable().api().ajax.reload();
                        } else
                            $.growl.error({message: data.msg + "" + data.details});
                    }
            ).fail(
                    function () {
                        $.growl.error({message: "Algo va mal no se encuentra el servidor"})
                        swal("Cancelado", "Accion No realizada", "error");
                    }
            );
        } else {
            swal("Cancelado", "Accion Cancelada", "error");
        }
    });

}

function CheckOut(amount,date,userid,saleprice) {
    var para={
        "amount": total,
        "date": date, 
        "userid": userid,
        "saleprice": saleprice 
               
    };
    $.ajax({
        url: "CheckOut",
        type: "post",
        /*Manda todo el formulario
         * como mandar parametros por separado en data:
         */
        data: para
    }).done(
            function (data) {
                // alert("Se realizo correctamente"+data.code); 
                if (data.code === 200) {

                    $.growl.notice({message: data.msg + " " + data.details});
                    $('#tbRoles').dataTable().api().ajax.reload();
                    $('#rolename').val("");
                } else
                    $.growl.error({message: data.msg + "" + data.details});
            }
    ).fail(
            function () {
                $.growl.error({message: "Algo va mal no se encuentra el servidor"})
            }
    );
}

function remove(){
     $.ajax({
        url: 'RemoveCart',
        type: 'GET',
        dataType: 'json'
    }).done(function (json) {
      
    }); 
    
}









