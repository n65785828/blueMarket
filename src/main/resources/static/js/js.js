/**
 * Created by yaling.he on 2015/11/17.
 */

//供应商管理页面上点击删除按钮弹出删除框(providerList.html)
$(function () {
    blindRemoveProvider();
});

function blindRemoveProvider(){
    $('.removeProvider').click(function () {
        $('.zhezhao').css('display', 'block');
        $('#removeProv').fadeIn();
        $('#yes').attr('href','/deleteProvider?id='+this.name+"&pageNum="+this.childNodes[0].alt)
    });
}

$(function () {
    $('#no').click(function () {
        $('.zhezhao').css('display', 'none');
        $('#removeProv').fadeOut();
    });
});


//订单管理页面上点击删除按钮弹出删除框(billList.html)
$(function () {
    removeBill();
});

function removeBill(){
    $('.removeBill').click(function () {
        $('.zhezhao').css('display', 'block');
        $('#removeBi').fadeIn();
        $('#yes').attr("href","/deleteBill?id="+this.name+"&pageNum="+this.childNodes[0].alt)
    });
}

$(function () {
    $('#no').click(function () {
        $('.zhezhao').css('display', 'none');
        $('#removeBi').fadeOut();
    });
});




//用户管理页面上点击删除按钮弹出删除框(userList.html)
$(function () {
    bindRemoveUser();
});

function bindRemoveUser(){
    $('.removeUser').click(function () {
        $('.zhezhao').css('display', 'block');
        $('#removeUse').fadeIn();
        $('#yes').attr("href","/delete?id="+this.name+"&userLv="+this.childNodes[0].name+"&pageNum="+this.childNodes[0].alt)
    });
}



$(function () {
    $('#no').click(function () {
        $('.zhezhao').css('display', 'none');
        $('#removeUse').fadeOut();
    });
});

