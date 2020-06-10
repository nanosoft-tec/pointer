jQuery(document).ready(function () {

    var dataTable = $('#datalist').DataTable({
        paging: false,
        language: {
            "sInfo": "",
        },
        ajax: {
            url: '/secure/company/list',
        },
        columns: [
            {
                title: "Nome",
                data: "name",
            },
            {
                title: "Estado",
                data: "active",
                render: function (data, type, full, meta) {
                    return '<a class="btn-small edit" href="javascript://" title="Mudar Estado">' +
                        (data ? '<i class="fa fa-ban"></i>' : '<i class="fa fa-check"></i>')
                        + '</a>';
                },
            },
        ]
    }).on('click', 'a.edit', function () {
        var data = dataTable.row($(this).parents('tr')).data();
        $.ajax({
            url: '/secure/company/' + data.id,
            type: "PUT",
            async: false,
            success: function (data) {
                alert('Empresa Atualizado com Sucesso!');
                dataTable.ajax.reload();
            }
        });
    });

    $('#save').on('click', function (e) {
        e.preventDefault();
        var name = $('#company').val();

        if (name === '' || name === undefined) {
            alert("Escolha uma empresa!");
            return;
        }

        var company = {
            name: name
        };

        $.ajax({
            url: '/secure/company',
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(company),
            async: false,
            success: function (data) {
                alert('Empresa Salva!');
                $('#company').val('');
                dataTable.ajax.reload();
            }
        });
    });

});
