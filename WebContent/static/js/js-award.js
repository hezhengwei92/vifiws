var num = 0;
var times = 0;
var isBiu = true;

function biu() {
    if (isBiu) {
        timer = setInterval(function () {
            clearColor();
            color();
        }, 40);
        isBiu = false;
    } else {
        biuStop(5)
    }
}


function biuStop(awardNum) {
    console.log(times);
    if (times > 80) {
        clearInterval(timer);
        clearColor();
        changeColor(awardNum)
    }
}

function clearColor() {
    $(".award-turntable-box").css("background-color", "#F9D824");
    $("#box-center").css("background-color", "#F43A2B");
    $(".award-turntable-box-in").css("background-color", "#FDE93F")
}

function color() {
    num++;
    times++;
    num = num > 8 ? num - 8 : num;
    changeColor(num);
}

function changeColor(placeNum) {
    var id = "#box" + placeNum;
    $(id).css("background-color", "#948213");
    $(id).find(".award-turntable-box-in").css("background-color", "#98902D");
}