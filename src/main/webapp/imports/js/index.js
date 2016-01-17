/**
 * Created by osmanalper on 11/01/16.
 */

$(document).ready(function () {
    init();
});

function init(){
    $("#btnDecode").click(function () {
        $.ajax({
            url: '/decodeValue',
            showLoader: true,
            type: 'POST',
            data: {
                decodeValue: $('#decodeValue').val(),
            },
            success: function (data) {
                $('#decodingRes').val(data);
            }
        });
    })

    $("#btnEncode").click(function () {
        $.ajax({
            url: '/encodeValue',
            showLoader: true,
            type: 'POST',
            data: {
                encodeValue: $('#encodeValue').val(),
            },
            success: function (data) {
                $('#encodingRes').val(data);
            }
        });
    })
}
