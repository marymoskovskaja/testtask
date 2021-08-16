/**
 * Функция возвращает текущую дату в строковом формате.
 * @returns {string} дата в формате ГГГГ-ММ-ДД.
 */
function getTodayDate() {
    const now = new Date();
    let month = (now.getMonth() + 1);
    let day = now.getDate();

    if (month < 10) {
        month = "0" + month;
    }

    if (day < 10) {
        day = "0" + day;
    }

    return now.getFullYear() + '-' + month + '-' + day;
}

/**
 * Функция возвращает дату начала текущего года в строковом формате.
 * @returns {string} дата в формате ГГГГ-ММ-ДД.
 */
function getYearFirstDate() {
    const now = new Date();

    return now.getFullYear() + '-01-01';
}