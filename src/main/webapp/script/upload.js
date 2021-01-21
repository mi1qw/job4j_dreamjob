$(document).ready(function () {
    let $detach;
    let $uploadCrop;
    let $file;
    let $name;

    // $('button[type=submit]').click(function () {
    $('#send').click(function () {


        // let formData = new FormData();
        // formData.append("file", $file);
        //


        // $('input[type=file]')[0].files[0].value = $file;
        // let form = $('form')[1];
        // let data = new FormData(form);


        let data = new FormData();
        data.append('file', $file, $name);


        // data.forEach(function (n) {
        //     console.log(n);
        // });

        // let xhr = new XMLHttpRequest();
        // xhr.open("POST", 'http://localhost:8080/job4j_dreamjob_war_exploded/addphoto.do');
        // // xhr.setRequestHeader("Content-Type", "text/plain");
        // xhr.send(data);

        $.ajax({
            url: 'http://localhost:8080/job4j_dreamjob_war_exploded/addphoto.do',
            data: data,
            type: 'POST',
            async: false,
            contentType: false,
            cache: false,
            processData: false,
            success: function (response) {
                if (response != 0) {
                    // console.log();
                    console.log("OK");
                } else {
                    alert('file not uploaded');
                }
            },
        });

        // $('div.col-sm:last form').attr('action')

        // event.preventDefault();
        // alert($('form:last').attr('action'));
        window.location.href = $('form:last').attr('action');
        // return false;
    });

    $('input').change(function () {
        // let file = document.querySelector('input[type=file]').files[0];
        let file = $('input[type=file]')[0].files[0];
        if (file.type.match(/image.*/)) {
            $name = file.name;
            $detach = $('#some').detach();
            dsblButtn();

            $uploadCrop = $('#crop').croppie({
                viewport: {
                    width: 300,
                    height: 300,
                    type: 'square'
                },
                enableExif: true,
                boundary: {
                    width: 400,
                    height: 400
                }
            });

            let divOK = addOkbuttn();
            $('#resize').click(function () {
                $uploadCrop.croppie('result', {
                    type: 'blob',
                    quality: 1,
                    size: 'viewport'
                }).then(function (resp) {
                    $file = null;
                    $file = resp;
                    enblButtn();
                    $('#crop').croppie('destroy');
                    $('#crop').prepend($detach);
                    divOK.remove();
                    $detach = null;
                    $('form img').attr('src', URL.createObjectURL(resp));
                });
            });

            let reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function (e) {
                $uploadCrop.croppie('bind', {
                    url: e.target.result
                }).then(function () {
                    console.log('jQuery bind complete');
                });
            }
        }
    });
});

function dsblButtn() {
    $('button[type=submit]').attr("disabled", true);
    $('.form-control').attr("disabled", true);
}

function enblButtn() {
    $('button[type=submit]').attr("disabled", false);
    $('.form-control').attr("disabled", false);
}

function addOkbuttn() {
    let d = document.createElement('div');
    d.setAttribute('class', 'align-self-center');
    d.innerHTML = "<button type=\"button\" id=\"resize\" class=\"btn" +
        " btn-outline-success\">кадрировать</button>";
    $('div.col-sm:first').append(d);
    return d;
}
