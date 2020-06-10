jQuery(document).ready(function () {

    $('#company').find('option').remove().end();
    $('#company').append($('<option>', {
            value: '',
            text: '--Selecione--'
        }
    ));
    $('#markType').find('option').remove().end();
    $('#markType').append($('<option>', {
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
    $.ajax({
        url: '/secure/type/list',
        type: "GET",
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            $.each(data, function (i, item) {
                $('#markType').append($('<option>', {
                        value: item,
                        text: item
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
            url: '/secure/mark/listCurrentDay',
        },
        columns: [
            {
                title: "Data",
                data: "date",
            },
            {
                title: "Hora",
                data: "time",
            },
            {
                title: "Tipo",
                data: "type",
            },
            {
                title: "Empresa",
                data: "company.name",
            },
        ]
    });

    $('#save').on('click', function (e) {
        e.preventDefault();
        var datetime = $('#datetime').val();
        var company = $('#company').val();
        var type = $('#markType').val();

        if (datetime === '' || datetime === undefined) {
            alert("Preencha a data Corretamente!");
            return;
        } else if (company === '' || company === undefined) {
            alert("Escolha uma empresa!");
            return;
        } else if (type === '' || type === undefined) {
            alert('Escolha o tipo de Marcação!');
            return;
        }

        var mark = {
            typeId: type,
            date: datetime,
            companyId: company
        };

        $.ajax({
            url: '/secure/mark',
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(mark),
            async: false,
            success: function (data) {
                alert('Marcação Salva!');
                $('#datetime').val('');
                $('#company').val('');
                $('#markType').val('');
                dataTable.ajax.reload();
            }
        });
    });

});
