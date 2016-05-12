var lastscreen = "#Initial";
var currentscreen = "#Initial";
var soundID = "Thunder";



function showScreens() {
    "use strict";

    $("#New").on('click',function(){
        $("#Initial").slideUp(600);
        $("#NewGame").slideDown(600);
        lastscreen = "#Initial";
    });

    $("#Continue").on('click',function(){
        $("#Initial").slideUp(600);
        $("#ContinueGame").slideDown(600);
        lastscreen = "#Initial";
    });

    $("#Highscores").on('click',function(){
        $("#Initial").slideUp(600);
        $("#Scores").slideDown(600);
        lastscreen = "#Initial";
        $("#Player3Box").hide();
        $("#Player4Box").hide();
    });

    $("#2P").on('click',function(){
        $("#NewGame").slideUp(600);
        $("#GameInfo").slideDown(600);
        lastscreen = "#NewGame";
        $("#Player3Box").hide();
        $("#Player4Box").hide();
    });

    $("#3P").on('click',function(){
        $("#NewGame").slideUp(600);
        $("#GameInfo").slideDown(600);
        lastscreen = "#NewGame";
        $("#Player3Box").show();
        $("#Player4Box").hide();
    });

    $("#4P").on('click',function(){
        $("#NewGame").slideUp(600);
        $("#GameInfo").slideDown(600);
        lastscreen = "#NewGame";
        $("#Player3Box").show();
        $("#Player4Box").show();
    });

    $("#StartNewGame").on('click', function () {
        $("#GameInfo").slideUp(600);
        $("#IdScreen").slideDown(600);
        lastscreen = "#GameInfo";
    });

    $("#GameSettings").on('click', function () {
        $("#GameInfo").slideUp(600);
        $("#Settings").slideDown(600);
        lastscreen = "#GameInfo";
    });

    $("#IdOk").on('click', function () {
        $("#IdScreen").slideUp(600);
        $("#TheGame").slideDown(600);
        $(".topcorner").hide();
        $(".bottomcorner").hide();
        lastscreen = "#GameInfo";
    });
}
    //LOL
function goBack() {
    "use strict";

    $('.Screen').each(function (i, obj) {

        if ($(obj).css('display') != 'none'){
            currentscreen = "#" + $(obj).attr("id");
            console.log(currentscreen)
        }
    });


    if (currentscreen === lastscreen) {
        $(currentscreen).slideUp(600);
        $("#Initial").slideDown(600);
    }


    else {


    $(currentscreen).slideUp(600); 
    $(lastscreen).slideDown(600);
    }

    currentscreen = lastscreen;
}

function changeStyle() {
    var css = '#css';
    if ($(css).attr('href') == "assets/css/style.css"){
        $(css).attr('href','assets/css/style2.css');
    }
    else {
        $(css).attr('href','assets/css/style.css');
    }

}


$(".ZoomAvailable div").css('cursor', 'pointer')
    .click(
        function(){
            var div = '#CardZoom';
            var image = "url(Cards/" + $(this).attr("title") + ".jpg)";
            $(div).show();
            $("#CardZoomBackground").show();
            $(div).css("background-image", image);
            console.log($(this).attr("title"));
            console.log(image);
        });

$("#CardZoom").css('cursor', 'pointer')
    .click(
        function(){
            $('#CardZoom').hide();
            $("#CardZoomBackground").hide();
        });

$(document).ready(function() {
    console.log('JS LOADED BITCH');
    showScreens();
    $('#Back').on('click', goBack);
    $('#Style').on('click', changeStyle);
    $("#ActionCard1").on('click', showcards);
});

