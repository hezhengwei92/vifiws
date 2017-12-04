<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <script src="static/js/jquery.min.js"></script>
    <link rel="stylesheet" href="static/css/swiper.min.css">
    <script src="static/js/swiper.min.js"></script>
    <script>

        (function (doc, win) {
            var docEl = doc.documentElement,
                    resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
                    recalc = function () {
                        var clientWidth = docEl.clientWidth;
                        fonsize = 100 * (clientWidth / 750) + 'px';
                        if (!clientWidth) return;
                        if (clientWidth >= 750) {
                            docEl.style.fontSize = '100px';
                        } else {
                            docEl.style.fontSize = fonsize;
                        }
                    };

            if (!doc.addEventListener) return;
            win.addEventListener(resizeEvt, recalc, false);
            doc.addEventListener('DOMContentLoaded', recalc, false);
        })(document, window);
    </script>
    <link rel="stylesheet" href="static/css/reset.css">
    <link rel="stylesheet" href="static/css/common.css">
    <link rel="stylesheet" href="static/css/style-index.css">
    <title>安逸宝免费WiFi，IEbox云逸宝手机伴侣，校园共享大师安全可靠</title>
</head>
<body>

<div class="back">
    <div class="head">
        <div class="head-img"></div>
        <div class="coin-msg">游戏币 ： <span>${coin}</span></div>
        <div class="head-right-msg"><a href="person?uid=tom">会员消费</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
                onclick=location.reload()>刷新</a>
        </div>
    </div>
    <div class="head-GK"></div>
    <div onclick="showProductList()" class="after-head">
        <div class=" after-head-R">共${machinesSize}台摇摆机</div>
        <img class="Fright" src="static/img/direct.png" alt="">
    </div>
    <div class="swiper-container">
        <div class="swiper-wrapper">

            <c:forEach items="${tbMachines}" var="Machines" varStatus="num" >
                <div id="SHOW${Machines.machineNumber}" class="swiper-slide">
                    <div  class="swiper-slide-box">
                        <div class="white-line"></div>
                        <div style=" background: url(${Machines.machineAvatar});" class="product-img"></div>
                        <div class="product-line"><span>${Machines.machineNumber}
                        <c:if test="${Machines.machineStatus ==0}">
                            号空闲中
                        </c:if>
                        <c:if test="${Machines.machineStatus !=0}">
                            号使用中
                        </c:if>
                    </span><br><img src="static/img/coin.png" alt=""><span>${Machines.machineCoin}币/次</span></div>
                    </div>
                </div>
            </c:forEach>

            <%-- <div class="swiper-slide">
                 <div class="swiper-slide-box">
                     <div class="white-line"></div>
                     <div class="product-img"></div>
                     <div class="product-line"><span>2号空闲中</span><br><img src="static/img/coin.png"
                                                                          alt=""><span>1币/次</span></div>
                 </div>

             </div>
             <div class="swiper-slide">
                 <div class="swiper-slide-box">
                     <div class="white-line"></div>
                     <div class="product-img"></div>
                     <div class="product-line"><span>2号空闲中</span><br><img src="static/img/coin.png"
                                                                          alt=""><span>1币/次</span></div>
                 </div>

             </div>
             <div class="swiper-slide">
                 <div class="swiper-slide-box">
                     <div class="white-line"></div>
                     <div class="product-img"></div>
                     <div class="product-line"><span>2号空闲中</span><br><img src="static/img/coin.png"
                                                                          alt=""><span>1币/次</span></div>
                 </div>

             </div>
             <div class="swiper-slide">
                 <div class="swiper-slide-box">
                     <div class="white-line"></div>
                     <div class="product-img"></div>
                     <div class="product-line"><span>2号空闲中</span><br><img src="static/img/coin.png"
                                                                          alt=""><span>1币/次</span></div>
                 </div>

             </div>--%>

        </div>
    </div>

    <div class="mid">
        <div class="after-mid">
            <div onclick="cutMonye()" class="btn-left-cut">
                <div class="cut"></div>
            </div>
            <div class="btn-mid-msg">投<span>0</span>币</div>
            <div onclick="upMonye()" class="btn-right-up"><span class="up">+</span></div>
        </div>
        <div onclick="startProduct()" class="begin-btn">启动 <span>1</span>号机</div>

        <div class="timer"><img id="reLoad" src="static/img/load.gif" alt=""></div>
        <div class="after-begin-btn">充值后点击按钮启动设备</div>
    </div>
    <div class="foot">
        <div class="foot-img-box"><img src="static/img/kefu.png"></div>
        <div onclick="showNewUserHlep()" class="foot-img-box"><img src="static/img/rules.png"></div>
        <div onclick="showPayList()" class="foot-chongzhi">充值</div>
    </div>
</div>

<div  id="alertPayList">
    <div  class="black"></div>
    <div onclick="closePayList()" class="black-msg">
        <div class="black-head">充值后点击按钮启动设备</div>
        <div class="black-begin-1"></div>
    </div>
    <div onclick="closePayList()" class="img-X"></div>
    <div class="Pay-package">
        <div class="Pay-package-head">充值套餐</div>

        <c:forEach items="${tbPackages}" var="pack" varStatus="num">
            <div id="${pack.ukPackageId}" class="Pay-package-num"><p>${pack.packagePrice/100}元</p>
                <p>${pack.packageCurrency}个币</p></div>
        </c:forEach>
        <div class="botton-desc">——游戏币一经售出，概不退款——</div>
    </div>
</div>

<div id="alertProduct">
    <div onclick="closeProductList()" class="black"></div>
    <div onclick="closeProductList()" class="img-X-2"></div>
    <div class="produc-list">
        <div class="produc-list-head">请选择设备编号</div>
        <div class="List">
            <c:forEach items="${tbMachines}" var="Machines" varStatus="num">
                <div id="${Machines.machineNumber}" class="produc-list-num"><p>${Machines.machineNumber}号机</p>
                    <p>${Machines.machineCoin}币</p></div>
            </c:forEach>
        </div>
    </div>
</div>

<div id="newUserHelp">
    <div onclick="closeNewUserHlep()" class="black"></div>
    <div class="midMsg"></div>
    <%--<div class="newUserHelp-img"></div>--%>
</div>

<div id="alertError">游戏币不足，请先充值号</div>

<script>
    var money = 0;
    var ownMoney = ${coin};
    var productId = "88";

    function startProduct() {
        if (money > 0) {
            var reqData = {
                "uid": "tom",
                "costCurrency": $(".btn-mid-msg span").text(),
                machineID: productId
            }
            $.ajax({
                url: "start",
                data: reqData,
                success: function (respData) {
                    console.log(respData)
                    $(".begin-btn").css({"display": "none"});
                    $("#reLoad").css({"display": "block"});
                    $("#reLoad").css({"display": "block"})
                }
            })
        } else {
            $("#alertError").text("充值游戏币需要大于0").css({"display": "block"})
            setTimeout(function () {
                $("#alertError").css({"display": "none"})
            }, 1000);
        }

    }


    //支付包的交互
    $(".Pay-package-num").click(function () {
        $(".Pay-package-num-active").removeClass("Pay-package-num-active");
        $(this).addClass("Pay-package-num-active");
    });

    //点击设备号之后的交互
    $(".produc-list-num").click(function () {
        $(".produc-list-num").removeClass("produc-list-num-active");
        var productId = this.id;
        var showNumberID = "#SHOW" + productId;
        var showNumber =1+parseInt( $(showNumberID).attr("data-swiper-slide-index"));
         $(".begin-btn").find("span").text(productId);
        $(this).addClass("produc-list-num-active");
        mySwiper.slideTo(showNumber, 1000, true);
        closeProductList()
    });
    function upMonye() {
        if (ownMoney > 0) {
            money++;
            ownMoney--;
            showMoney()
        }
    } //加减钱 并且设置修改当前游戏币对应参数
    function cutMonye() {
        if (money > 0) {
            money--;
            ownMoney++;
            showMoney()
        }
    }
    function showMoney() {
        $(".btn-mid-msg span").text(money);
        // $(".coin-msg span").text(ownMoney)
        if (money > 0) {
            $(".begin-btn").css({"background": "-webkit-linear-gradient(#58ceeb, #3290ff)"})
        }
        else {
            $(".begin-btn").css({"background": "#fff"})
        }
    }
    var mySwiper = new Swiper('.swiper-container', {
        slidesOffsetBefore: 100 * document.body.clientWidth / 750 * 2.22,
        centeredSlides: true,//活动模块居中
        slideToClickedSlide: true,//设置点击后居中
        width: 100 * document.body.clientWidth / 750 * 3.08,
        slidePrevClass: 'my-slide-prev',
        slideNextClass: 'my-slide-next',
        slideActiveClass: 'swiper-slide-active',
        loop: true,
        onTransitionEnd: function (swiper) {

            var num = $(".swiper-slide-active").attr("id");
            $(".begin-btn").find("span").text(num.substring(4));
            $(".swiper-slide-active>.swiper-slide-box").css({
                "width": "3.08rem",
                "height": "4.18rem",
                "margin-top": "0.2rem",
                "margin-left": "0rem"
            });
            $(".product-line").css({"margin-top": "0.1rem"});
            $(".product-line img").css({
                "width": "0.29rem",
                "height": "0.2976rem"
            });
            $(".product-img").css({"width": "2.56rem", "height": "2.56rem"});
            $(".swiper-slide-active").find(".product-line").css({"margin-top": "0.24rem"});
            $(".swiper-slide-active").find(".product-img").css({"width": "2.86rem", "height": "2.86rem"});
            $(".swiper-slide-active").find(".product-line img").css({"width": "0.33rem", "height": "0.33rem"});
            /*          $(".my-slide-prev").find(".product-line>span:first").css({"font-size":"0.296rem"}).find(".product-line>span:last").css({"font-size":"0.257rem"})*/
            $(".my-slide-next>.swiper-slide-box").css({
                "width": "2.78rem",
                "height": "3.76rem",
                "margin-top": "0.4rem",
                "margin-left": "0.3rem"
            });
            $(".my-slide-prev>.swiper-slide-box").css({
                "width": "2.78rem",
                "height": "3.76rem",
                "margin-top": "0.4rem",
                "margin-left": "0rem"
            });
        }
    })
    function showProductList() {
        $("#alertProduct").css({"display": "block"})
    }
    function closeProductList() {
        $("#alertProduct").css({"display": "none"})
    }
    function showPayList() {
        $("#alertPayList").css({"display": "block"})
    }

    function closePayList() {
        console.log("closePayList()")
        $("#alertPayList").css({"display": "none"})
    }
    function showNewUserHlep() {
        $("#newUserHelp").css({"display": "block"})
    }
    function closeNewUserHlep() {
        $("#newUserHelp").css({"display": "none"})
    }
$(function () {
    var height = document.documentElement.clientHeight+"px";
    $(".black-msg").css("height",height)
    $(".black").css("height",height)
})
</script>

</body>
</html>
