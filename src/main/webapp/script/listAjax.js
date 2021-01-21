$(document).ready(function () {
    $('tbody tr').each(function (i, tr) {
        let tdCity = $(tr).children().eq(4);
        let cityId = tdCity.attr('datatype');

        //работает
        loadCityID(cityId, tdCity);

        //работает
        // loadDoc('/job4j_dreamjob_war_exploded/cities.do?cityid=' + cityId,
        //     function (d) {
        //         console.log('loadDoc  ' + d.responseText);
        //     });

        // остальные криво работают
        // loadCity(parseInt(cityId), this);
        // myAjax(parseInt(cityId), this);
    });

});

function loadCityID(cityid, td) {
    $(td).load('/job4j_dreamjob_war_exploded/cities.do?cityid=' + cityid);
}

function loadDoc(url, cFunction) {
    var xhttp;
    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            cFunction(this);
        }
    };
    xhttp.open("GET", url, true);
    xhttp.send();
}

//остальные ловятся почему-то только через ajaxComplete
// $(document).ajaxComplete(function (event, xhr, settings) {
//     console.log(xhr.responseText);
// });

//done ловится только если запрос без параматров
function myAjax(id) {
    $.ajax({
        type: 'GET',
        url: '/job4j_dreamjob_war_exploded/cities.do',
        // data: {cityid: id},
    }).done(function (data) {
        $(data).each(function (data) {
            console.log(data.responseText);
            console.log(data.responseType);
            console.log('data  ' + data);
        })
    }).fail(function (err) {
        alert(err);
    });
}

//done ловится только если запрос без параматров
function loadCity(id, th) {
    $.get("/job4j_dreamjob_war_exploded/cities.do",
        {cityid: id},
        function (data) {
            console.log(data.responseText);
            console.log(data.responseType);
            console.log('data  ' + data);
        }
    );
    // .done(function (data) {
    //         console.log(data.responseText);
    //         console.log(data.responseType);
    //         console.log('data  ' + data);
    //     }
    // );
}