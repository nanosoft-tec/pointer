jQuery(document).ready(function () {

    $('#company').find('option').remove().end();
    $('#company').append($('<option>', {
            value: '',
            text: '--Selecione--'
        }
    ));
    $.ajax({
        url: '/secure/company/listActive',
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            $.each(data, function (i, item) {
                $('#company').append($('<option>', {
                        value: item.id,
                        text: item.name
                    }
                ));
            });
        }
    });

    var dataTable = $('#datalist').DataTable({
        paging: false,
        language: {
            "sInfo": "",
        },
        ajax: {
            url: '/secure/mark/list',
            data: function(data) {
                data.object = {};
                data.object.period = $('#period').val();
                data.object.companyId = $('#company').val();
                return JSON.stringify(data);
            },
        },
        columns: [
            {
                title: "Data",
                data: "date",
            },
            {
                title: "Entrada",
                data: "inputTime",
            },
            {
                title: "Saida",
                data: "outputTime",
            },
            {
                title: "Total",
                data: "sumTime",
            },
        ]
    });

    $('#find').on('click', function() {
       dataTable.ajax.reload();
    });
});
