$(document).ready(function () {
    $('#name').blur(check);
    $('#description').blur(check);
    $('#citydiv').change(check);
    $('#city').click(function () {
        myAjax(this);
        $(this).off();
    });
    loadCity();
});

let check = function check() {
    if ($('#name').val().trim().match(/[\s\d\wа-яА-ЯёЁ]+/)
        && $('#description').val().trim().match(/[\s\d\wа-яА-ЯёЁ]+/)
        && $('#city').val() !== '0') {
        $('#submit').attr("disabled", false);
    } else {
        $('#submit').attr("disabled", true);
    }
}

function myAjax(elem) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/job4j_dreamjob_war_exploded/cities.do',
        contentType: "application/json",
        data: null,
        dataType: 'json'
    }).done(function (data) {
        $(data).each(function (index, element) {
            $(elem).children().last().after("<option value=" + ++index + ">"
                + element + "</option>");
        })
    }).fail(function (err) {
        alert(err);
    });
}

function loadCity() {
    let cityid = $('#city').val();
    if (cityid !== '0') {
        $('#city').load('http://localhost:8080/job4j_dreamjob_war_exploded/cities.do?cityid='
            + cityid);
        check();
    }
}