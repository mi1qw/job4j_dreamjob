function writeFields() {
    writeField("name");
    writeField("description");
}

function writeField(name) {
    var value = sessionStorage.getItem(name);
    if (value) {
        document.getElementById(name).value = value;
        sessionStorage.removeItem(name);
    }
}

function getFields() {
    getField("name");
    getField("description");
}

function getField(name) {
    sessionStorage.setItem(name, document.getElementById(name).value);
}
