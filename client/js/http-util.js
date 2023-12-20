
function convertDate(r) {
    if (!r || r.length < 8) return null;
    let year = parseInt(r.substring(0, 4));
    let month = parseInt(r.substring(4, 6)) - 1;
    let day = parseInt(r.substring(6, 8));
    return new Date(year, month, day);
}

function convertDateString(r) {
    if (!r) return "";
    let year = r.getFullYear();
    let month = r.getMonth() + 1;
    let day = r.getDate();
    return year + (month + "").lpad(2, "0") + (day + "").lpad(2, "0");
}

function convertDateTime(r) {
    if (!r || r.length < 14) return null;
    let year = parseInt(r.substring(0, 4));
    let month = parseInt(r.substring(4, 6)) - 1;
    let day = parseInt(r.substring(6, 8));
    let hh = parseInt(r.substring(8, 10));
    let mm = parseInt(r.substring(10, 12));
    let ss = parseInt(r.substring(12, 14));
    return new Date(year, month, day, hh, mm, ss, 0);
}

function convertDateTimeString(r) {
    if (!r) return "";
    let year = r.getFullYear();
    let month = r.getMonth() + 1;
    let day = r.getDate();
    let hh = r.getHours();
    let mm = r.getMinutes();
    let ss = r.getSeconds();
    return (
        year +
        (month + "").lpad(2, "0") +
        (day + "").lpad(2, "0") +
        (hh + "").lpad(2, "0") +
        (mm + "").lpad(2, "0") +
        (ss + "").lpad(2, "0")
    );
}


function processResponseData(dataRoot) {
    if (!dataRoot || !(dataRoot instanceof Object)) return;
    for (let rootKey of Object.keys(dataRoot)) {
        console.log('rootKey', rootKey);
        let data = dataRoot[rootKey];
        if (!data || !(data instanceof Object)) continue;

        if (data instanceof Array) {
            for (let subData of data) {
                processResponseData(subData);
            }
        } else {
            if ("date" in data) {
                data.date = convertDate(data.val);
            } else if ("datetime" in data) {
                data.datetime = convertDateTime(data.val);
            } else {
                processResponseData(data);
            }
        }
    }
}

function getRequestData(dataRoot) {
    setRequestData(dataRoot);
    return JSON.stringify(dataRoot);
}

function setRequestData(dataRoot) {
    if (!dataRoot || !(dataRoot instanceof Object)) return;
    for (let rootKey of Object.keys(dataRoot)) {
        console.log('rootKey', rootKey);
        let data = dataRoot[rootKey];
        if (!data || !(data instanceof Object)) continue;

        if (data instanceof Array) {
            for (let subData of data) {
                setRequestData(subData);
            }
        } else {
            if ("date" in data) {
                data.val = convertDateString(data.date);
            } else if ("datetime" in data) {
                data.val = convertDateTimeString(data.datetime);
            } else {
                setRequestData(data);
            }
        }
    }
}
