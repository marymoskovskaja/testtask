let suppliers = new Array();

/**
 * Загрузка данных отчета за переданный период.
 * @param startDate дата начала периода.
 * @param endDate дата окончания периода.
 */
function downloadTableData(startDate, endDate) {
    /**
     * Формирование данных ячеек.
     * @param sum сумма.
     * @param size объем.
     * @returns {string} информация о суммарном объеме и сумме поставок.
     */
    function getPurchasesInfo(sum, size) {
        return size + ' кг на ' + sum + ' p.';
    }

    $.ajax({
        url: "http://localhost:8080/deliveryReport",
        data: {
            dateFrom: startDate,
            dateTo: endDate
        }
    }).done(function(data) {
        data.forEach(item =>  {
            const product = $("th:contains(" + item[1].name + ")");
            const index = suppliers.findIndex(function(current) {
                return current.id === item[0].id;
            }) + 1;

            if (product.length) {
                $(`tr:contains(` + item[1].name + `)`)[0].children[index].innerText =
                    getPurchasesInfo(item[2], item[3]);
            } else {
                const elem = document.createElement("tr");

                elem.innerHTML = `<th scope="col">` + item[1].name + `</th>
                              <td>` + '-' + `</td>
                              <td>` + '-' + `</td>
                              <td>` + '-' + `</td>`;

                elem.children[index].innerText = getPurchasesInfo(item[2], item[3]);

                $("tbody")[0].append(elem);
            }
        });
    });
}

/**
 * Событие на загрузку документа.
 * Установка начальных дат, загрузка шапки и содержимого отчета.
 */
$(document).ready(function() {
    $('#start_date').val(getYearFirstDate());
    $('#end_date').val(getTodayDate());

    $.ajax({
        url: "http://localhost:8080/suppliers"
    }).done(function(data) {
        suppliers = data;

        data.forEach(item => {
            const elem = document.createElement("th");

            elem.scope = 'col';
            elem.textContent = item.name;

            $("tr")[0].append(elem);

            downloadTableData($("#start_date").val(), $("#end_date").val());
        });
    });
});

/**
 * Событие на клик по кнопке "Применить".
 * Очищает предыдущую выборку и заново загружает данные отчета.
 */
$("#apply_button").click(function () {
    $("tbody").empty();

    downloadTableData($("#start_date").val(), $("#end_date").val());
});