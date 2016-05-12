"use strict";
(function () {

var deck = ["Copper", "Copper", "Copper", "Copper", "Copper", "Copper", "Copper", "Estate", "Estate", "Estate"];
var firstGame = ["Cellar", "Market", "Militia", "Mine", "Moat", "Remodel", "Smithy", "Village", "Woodcutter", "Workshop"];


function zoom() {
    $(".ZoomAvailable div").css('cursor', 'pointer')
        .click(
            function () {
                var div = '#CardZoom';
                var image = "url(assets/images/cards/" + $(this).attr("title") + ".jpg)";
                $(div).show();
                $("#CardZoomBackground").show();
                $(div).css("background-image", image);
                console.log($(this).attr("title"));
                console.log(image);
            }
        );

    $("#CardZoom").css('cursor', 'pointer')
        .click(
            function () {
                $('#CardZoom').hide();
                $("#CardZoomBackground").hide();
            }
        );
}

function playablePlaygroundCard() {
    var Card = $("#Playground").children(':last');
    Card.css('cursor', 'pointer').on('click', takeBackCard(Card));
}

function playableHandCard() {
    var Card = $("#Hand").children(':last');
    Card.css('cursor', 'pointer').on('click', playCard(Card));
}

function playCard(Card) {
    $(Card).css('cursor', 'pointer')
        .on('click',
            function () {
                $($(this)).clone().appendTo("#Playground");
                $(this).remove();
                playablePlaygroundCard();
            }
        );
}

function takeBackCard(Card) {
    $(Card).css('cursor', 'pointer')
        .on("click",
            function () {
                $($(this)).clone().appendTo("#Hand");
                $(this).remove();
                playableHandCard();
            }
            );
}

function createHand() {
    var i = 0;
    for (i = 0; i < 5; i = i + 1) {
        $("#Hand").append($('<div id="Card' + i + '" class="' + deck[i] + ' hvr-float"></div>'));
        playableHandCard();
    }
}

function fillAction() {
    var i = 0;
    $("#cardSet").change(function () {
        var cardSet = $("#cardSet option:selected").val();
        console.log(cardSet);
        switch (cardSet) {
            case "firstGame":
                for (i = 0; i < firstGame.length; i = i + 1) {
                    console.log("Fill works");
                }
                break;
            case "2":
                console.log("Select2");
                break;
            case "3":
                console.log("Select3");
                break;
        }
    });
}

function playTreasures() {
    $("#PlayTreasures").css('cursor', 'pointer')
        .on("click",
            function () {
                $('#Hand div').each(function () {
                    if ($(this).attr('class') === ("Copper hvr-float") || $(this).attr('class') === ("Silver hvr-float") || $(this).attr('class') === ("Gold hvr-float")) {
                        $($(this)).clone().appendTo("#Playground");
                        $(this).remove();
                        playablePlaygroundCard();
                    }
                });
            });
}

function shuffle(array) {
    // Knuth Shuffle.
    var currentIndex = array.length, temporaryValue, randomIndex;

    // While there remain elements to shuffle...
    while (0 !== currentIndex) {

        // Pick a remaining element...
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex -= 1;

        // And swap it with the current element.
        temporaryValue = array[currentIndex];
        array[currentIndex] = array[randomIndex];
        array[randomIndex] = temporaryValue;
    }
    console.log('shuffled');
    return array;
}



}());