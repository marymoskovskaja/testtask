let productData;
const messageForPurchaseAlert = "Pазмер поставки для предыдущей позиции не указан или заполнен некорректно";
const messageForAddDeliveryAlert = "Не заполнены Номер и/или Дата поставки";
const messageForPriceAlert = "Не найдены цены на указанную дату";

/**
 * Событие на загрузку документа.
 * Установка текущей даты.
 * Загрузка перечней значений для полей ввода, добавление обработчиков для этих полей.
 */
$(document).ready(function() {
    $('#date').val(getTodayDate());
    $('#date').change(calcPrice);
    $("select").change(calcPrice);

    $.ajax({
        url: "http://localhost:8080/suppliers"
    }).then(function(data) {
        data.forEach(item =>
            $("#supplier").append("<option value='" + item.id + "'>" + item.name + "</option>")
        );

        $.ajax({
            url: "http://localhost:8080/products"
        }).then(function(data) {
            productData = data;

            productData.forEach(item => {
                $("#product").append("<option value='" + item.id + "'>" + item.name + "</option>");
            });

            $("#product").trigger("change");
        });
    });

});

/**
 * Получение цены на основании заполненных данных.
 */
function calcPrice() {
    if (this.id === "supplier" || this.id === "date") {
        $("select#product").trigger("change");
    } else {
        $.ajax({
            type:"GET",
            url: "http://localhost:8080/price",
            data:{
                date: $("#date").val(),
                supplier: $("#supplier").val(),
                product: this.value
            },
            error: (err) => { alert(messageForPriceAlert) }
        }).done((data) => {
            const priceElem = this.parentElement.parentElement.getElementsByTagName("input").price;

            priceElem.value = data.price ?? 0;
        });
    }
}

/**
 * Событие на сохранение товара.
 * Проверяет корректность заполнения и передает данные на сервер.
 */
$("#save_button").click(function () {
    let purchases = $("purchase");

    if (!lastPurchaseIsFilledAndCorrect(purchases)) {
        alert(messageForPurchaseAlert);

        return;
    }

    if (!$("#number").val() || !$("#date").val()) {
        alert(messageForAddDeliveryAlert);

        return;
    }

    let purchaseProducts = new Array();
    let purchaseSizes = new Array();

    for (let i = 0; i < purchases.length; i++) {
        purchaseProducts.push(purchases[i].getElementsByTagName("select")[0].value);
        purchaseSizes.push(purchases[i].getElementsByTagName("input")[0].value);
    }

    $.ajax({
        type:"POST",
        url: "http://localhost:8080/add-delivery",
        data:{
            number: $("#number").val(),
            date: $("#date").val(),
            supplier: $("#supplier").val(),
            purchaseProducts: purchaseProducts.toString(),
            purchaseSizes: purchaseSizes.toString()
        },
        success: (msg) => { alert(msg) },
        error: (err) => { alert(err.responseText) }
    }).done(() => window.location.href = '/');
});

/**
 * Проверка на заполненность и корректность предыдущей строки с товаром при добавлении новой.
 * @param purchases перечень покупок.
 * @returns {0|boolean} признак прохождения проверки.
 */
function lastPurchaseIsFilledAndCorrect(purchases) {
    let selectedProduct = purchases[purchases.length - 1].getElementsByTagName("select")[0].value;
    let size = purchases[purchases.length - 1].getElementsByTagName("input")[0].value;

    return selectedProduct && parseInt(size) && parseInt(size) >= 0;
}

/**
 * Событие на нажатие на кнопку "Добавить товар".
 * Копирует данные предыдущей строки и добавляет кнопку "-" для удаления новых строк.
 * Добавляет нужные обработчики и вставляет новую строку.
 */
$("#add_product").click(function () {
    /**
     * Обработчик на нажатие кнопки "-".
     * Удаляет соответствующую запись.
     */
    function removeRecord() {
        this.parentNode.remove();
    }

    let purchases = $("purchase");

    if (!productData || !(purchases.length < productData.length)) {
        return;
    }

    if (!lastPurchaseIsFilledAndCorrect(purchases)) {
        alert(messageForPurchaseAlert);

        return;
    }

    let clone = purchases[purchases.length - 1].cloneNode(true);

    if (!clone.getElementsByTagName("button").length) {
        let elem = document.createElement("button");

        elem.className = "btn btn-secondary btn-sm";
        elem.name = "del_product";
        elem.innerText = "-";
        elem.title = "Удалить товар";
        elem.style = "margin-left: 10px;width: 30px";
        elem.onclick = removeRecord;

        clone.append(elem);
    } else {
        clone.getElementsByTagName("button")[0].onclick = removeRecord;
    }

    clone.getElementsByTagName("select")[0].onchange = calcPrice;

    purchases[purchases.length - 1].after(clone);

    $("#supplier").trigger("change");
});