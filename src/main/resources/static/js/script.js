$(document).ready(function() {

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).bind('ajaxSend', function(e, xhr) {
        xhr.setRequestHeader(header, token);
    });
    $.extend($.fn.dataTable.defaults, {
        ajax : {
            contentType : 'application/json; charset=utf-8',
            type : 'POST',
            data : function(data) {
                return JSON.stringify(data);
            },
        },
        responsive : true,
        stateSave : true,
        processing : true,
        searching : false,
        ordering : false,
        serverSide : true,
        lengthChange : false,
        paging: false,
        retrieve: true,
        language : {
            "sEmptyTable" : "Nenhum registro encontrado",
            "sInfo" : "Mostrando de _START_ até _END_ de _TOTAL_ registros",
            "sInfoEmpty" : "Mostrando 0 até 0 de 0 registros",
            "sInfoFiltered" : "(Filtrados de _MAX_ registros)",
            "sInfoPostFix" : "",
            "sInfoThousands" : ".",
            "sLengthMenu" : "_MENU_ resultados por página",
            "sLoadingRecords" : "Carregando... <i class='fa fa-spinner fa-spin'></i>",
            "sProcessing" : "Processando... <i class='fa fa-spinner fa-spin'></i>",
            "sZeroRecords" : "Nenhum registro encontrado",
            "sSearch" : "Pesquisar",
            "oPaginate" : {
                "sNext" : "Próximo",
                "sPrevious" : "Anterior",
                "sFirst" : "Primeiro",
                "sLast" : "Último"
            },
            "oAria" : {
                "sSortAscending" : ": Ordenar colunas de forma ascendente",
                "sSortDescending" : ": Ordenar colunas de forma descendente"
            }
        }
    });
});
