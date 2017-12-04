<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <script src="static/js/jquery.min.js"></script>
    <script>
        (function (doc, win) {
            var docEl = doc.documentElement,
                    resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
                    recalc = function () {
                        var clientWidth = docEl.clientWidth;
                        var fonsize = 100 * (clientWidth / 750) + 'px';
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
    <link rel="stylesheet" href="static/css/style-person.css">
    <title>安逸宝免费WiFi，IEbox云逸宝手机伴侣，校园共享大师安全可靠</title>
</head>
<body>
<div class="head">
    <div class="head-person-msg">
        <div class="head-person-msg-L">
            <div class="head-img-out">
                <div class="person-head-img"></div>
                <div class="person-head-img-level"></div>
            </div>
            <div class="msg-L">
                <span>${tbUser.userNickname}</span><br>
                <span>ID:${tbUser.ukKeyId}</span><span class="msg-right">${tbUser.userType}</span>
            </div>
        </div>
    </div>
</div>
<div class="level-msg">
    <div class="level-msg-line1"><span>在充值100成为白银会员</span>
        <div onclick="showAlert1()" class="answer-radius">?</div>
        <img src="static/img/level-pic.png">
    </div>
</div>


<c:if test='${tbConsumeRecords.size()>0 || tbUserRecords.size()>0}'>
    <div class="XF-record">消费记录</div>
    <div class="record-list">
        <c:forEach items="${tbConsumeRecords}" var="comsume" varStatus="vs">
            <div class="record">
                <div class="record-line1"><span>启动时间</span> : ${comsume.createTime}</div>
                <div class="record-line1"><span>启动设备</span> : ${comsume.machineNumber}__${comsume.machinePosition}</div>
                <div class="record-line1"><span>小号币数</span> : ${comsume.costCurrency}</div>
            </div>
            <div class="line-FG"></div>
        </c:forEach>

        <c:forEach items="${tbUserRecords}" var="record" varStatus="rd">
            <div class="record">
                <div class="record-line1"><span>支付时间</span> : ${record.createTime}</div>
                <div class="record-line1"><span>支付方式</span> : ${record.buyType}</div>
                <div class="record-line1"><span>支付金额</span> : ${record.buyPackageMoney}</div>
                <div class="record-line1"><span>订单编号</span> : ${record.ukOrderId}</div>
            </div>
            <div class="line-FG"></div>
        </c:forEach>
    </div>
    <div onclick="getMore()" class="getMore">加载更多</div>
</c:if>
<c:if test='${tbConsumeRecords.size()<=0 && tbUserRecords.size()<=0}'>
    <div class="no-record"></div>
</c:if>

<div id="alert1">
    <div class="black"></div>
    <div class="alert-msg">
        <div class="alert-msg-ling1">会员成长说明</div>
    <textarea class="textarea-box" rows="8" cols="35" readonly>
会员累积充值金额越多等级越高，可享受
的会员权益越多：
黄铜：累积充值金额0~100元
白银：累积充值金额100~300元
黄金：累积充值金额300~500元
铂金：累积充值金额500~2000元
砖石：累积充值金额2000~5000元
王者：累积充值金额5000以上
    </textarea>
        <div class="hr"></div>
        <div onclick="hiddenAlert1()" class="alert-msg-ling2">我知道了</div>

    </div>
</div>

<!--<div class="XF-record">消费记录</div>-->
<!--<div class="record-list">

    <div class="record">
        <div class="record-line1"><span>启动时间</span> : 2017-11-23 18:23:12</div>
        <div class="record-line1"><span>启动设备</span> : 你好你好</div>
        <div class="record-line1"><span>小号币数</span> : 1</div>
    </div>

    <div class="line-FG"></div>
    <div class="record">
        <div class="record-line1"><span>支付时间</span> : 2017-11-23 18:23:12</div>
        <div class="record-line1"><span>支付方式</span> : 你好你好</div>
        <div class="record-line1"><span>支付金额</span> : 1</div>
        <div class="record-line1"><span>订单编号</span> : 1</div>
    </div>
    <div class="line-FG"></div>
</div>-->


<!--    <div class="alert-msg-ling2">会员积累充值金额越多等级越高，可享受</div>
    <div class="alert-msg-ling2">的会员权益越多</div>
    <div class="alert-msg-ling3">黄铜：累计充值金额0-100元</div>
    <div class="alert-msg-ling3">白银：累计充值金额100-300元</div>
    <div class="alert-msg-ling3">铂金：累积充值金额500~2000元</div>
    <div class="alert-msg-ling3">砖石：累积充值金额2000~5000元</div>
    <div class="alert-msg-ling3">王者：累积充值金额5000以上</div>-->


<script>
    var page = 5;
    var consumerSize = ${tbConsumeRecords.size()};
    var reocrdSize = ${tbUserRecords.size()};
    var uid = 'tom';
    function getMore() {
        /**
         * 如果大于6则做一次请求，并且更新当前的page
         */
        if (reocrdSize >=6) {
            $.ajax({
                url: "getMoreReocrd?uid="+uid+"&page="+page,
                 success: function (data) {
                        $.each($.parseJSON(data), function (k, v) {
                            $(".record-list").append(
                                    "  <div class='record'>" +
                                    "<div class='record-line1'><span>启动时间</span> : "+v.createTime+"</div>" +
                                    "<div class='record-line1'><span>启动设备</span> : "+v.idxMachineId+"__"+v.idxAgentId+"</div>" +
                                    "<div class='record-line1'><span>小号币数</span> : "+v.costCurrency+"</div>" +
                                    "</div>" +
                                    "<div class='line-FG'></div>"
                            )
                        });
                }
            });
        } else if (consumerSize == 6) {

            $.ajax({
                url: "getMoreConsume?uid="+uid+"&page="+page,
                success: function (data) {
                    $.each($.parseJSON(data), function (k, v) {
                        $(".record-list").append(
                                $(".record-list").append(
                                        "<div class='record'>" +
                                        "<div class='record-line1'><span>支付时间</span> : "+v.createTime+"</div>" +
                                        "<div class='record-line1'><span>支付方式</span> : "+ v.userType+"</div>" +
                                        "<div class='record-line1'><span>支付金额</span> : 1</div>" +
                                        "<div class='record-line1'><span>订单编号</span> : 1</div>" +
                                        "</div>" +
                                        "<div class='line-FG'></div>")
                        )
                    });
                }
            });

        }
    }
    function showAlert1() {
        $("#alert1").css({"display": "block"})
    }
    function hiddenAlert1() {
        $("#alert1").css({"display": "none"})
    }

</script>

</body>
</html>
