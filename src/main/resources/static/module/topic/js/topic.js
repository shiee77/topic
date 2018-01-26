function logout() {
    var url = basePath + "/rest/logout";
    $.post(url, {}, function (result) {
        if (result.status == 0) {
            topicAlert({
                result: "success", message: "注销成功", success: function () {
                    location.reload();
                }
            });
        }
    });
}
/**
 * 提示信息框,添加在#main之前
 */
function topicAlert(parameters) {
    var result = parameters.result;
    var message = parameters.message;
    var success = parameters.success;
    var $parent = parameters.$parent;
    if ($parent == undefined) {
        $parent = $("#header");
    }
    var div = '\
        <div class="alert alert-' + result + '" role="alert" style="margin-bottom: 0">\
            <button type="button" class="close" data-dismiss="alert" aria-label="Close">\
                <span aria-hidden="true">&times;</span>\
            </button>\
            <div class="container">\
                ' + message + '\
            </div>\
        </div>';
    var $div = $(div);
    $parent.append($div);
    setTimeout(function () {
        $div.fadeToggle({
            complete: function () {
                if (success != undefined)
                    success();
            }
        });
    }, 1000);
}

/**
 * 返回模态窗口，一个id只构建一次
 * @param modalId
 * @param title
 * @param body
 * @param footer
 * @returns {*|jQuery|HTMLElement}
 */
function topicModal(modalId, title, body, footer) {
    var $modal = $("#" + modalId);
    if ($modal.length > 0) {
        return $modal;
    }
    var div = '\
        <div class="modal fade" id="' + modalId + '" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">\
            <div class="modal-dialog">\
                <div class="modal-content">\
                    <div class="modal-header">\
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>\
                        <h4 class="modal-title">' + title + '</h4>\
                    </div>\
                    <div class="modal-body">\
                    ' + body + '\
                    </div>\
                    <div class="modal-footer">\
                        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>\
                        ' + footer + '\
                    </div>\
                </div>\
            </div>\
        </div>\
        ';
    var $div = $(div);
    $("body").append($div);
    return $div;
}

/**
 * 发表话题
 * @param topicId
 */
function topicPost(topicId) {
    var modalId = "postModal";
    var title = "发表新文章";
    var body = '\
        <form id="postForm">\
            <input type="hidden" name="topicId" value="' + topicId + '">\
            <div class="form-group">\
                <label for="">标题</label>\
                <input type="text" class="form-control" name="title">\
            </div>\
            <div class="form-group">\
                <label for="">内容</label>\
                <textarea name="content" class="form-control form-content" rows="5"></textarea>\
            </div>\
        </form>\
        ';
    var footer = '<button type="button" class="btn btn-primary" onclick="doTopicPost()">发表</button>';
    var $modal = topicModal(modalId, title, body, footer);
    $modal.modal();
}

function doTopicPost() {
    var url = basePath + "/rest/p";
    var str = $("#postForm").find(".form-content").val();
    str = str.replace(/\n/g,"<br/>").replace(/\s/g,"&nbsp;");
    $("#postForm").find(".form-content").val(str);
    $.post(url, $("#postForm").serialize(), function (result) {
        if (result.status == 0) {
            topicAlert({
                result: "success",
                message: "发表成功",
                $parent: $("#postModal .modal-body"),
                success: function () {
                    window.location.href = basePath + "/p/" + result.data.post.id;
                }
            });
        } else {
            topicAlert({
                result: "danger",
                message: result.message,
                $parent: $("#postModal .modal-body")
            });
        }
    });
}

function getSearchInfo(){
    var str = window.location.href;
    window.location.href =  str.substring(0,str.indexOf('?')  )  + '?searchInfo=' + $("#Popover-15392-80405-toggle").val() + '';
}


/**
 * 删除评论
 */
function delPost(postId) {
    var modalId = "delPost";
    var title = "删除帖子";
    if( postId === undefined || postId === null || postId === ''){
        return ;
    }
    var body = '\
        <form id="commentForm">'
        + '<input type="hidden" name="postId" value="' + postId + '">'
        +'  <div class="form-group">\
               <span>确认删除此帖子吗?</span>\
            </div>\
        </form>\
        ';
    var footer = '<button type="button" class="btn btn-primary" onclick="doDelPost(\''+modalId+'\')">删除</button>';
    var $modal = topicModal(modalId, title, body, footer);
    $modal.modal();
}

/**
 * 执行删除帖子
 */
function doDelPost(modalId) {
    var url = basePath + "/rest/p/del";
    $.post(url, $("#commentForm").serialize(), function (result) {
        if (result.status == 0) {
            topicAlert({
                result: "success",
                message: "删除成功",
                $parent: $("#"+modalId+" .modal-body"),
                success: function () {
                    window.history.back();location.reload();
                }
            });
        } else {
            topicAlert({
                result: "danger",
                message: result.message,
                $parent: $("#"+ modalId +" .modal-body")
            });
        }
    });
}


/**
 * 取消点赞
 */
function cancelTopicLiked(postId) {
    var modalId = "cancelTopicLiked";
    var title = "取消点赞";
    if( postId === undefined || postId === null || postId === ''){
        return ;
    }
    var body = '\
        <form id="commentForm">'
        + '<input type="hidden" name="postId" value="' + postId + '">'
        +'  <div class="form-group">\
               <span>确定取消点赞吗?</span>\
            </div>\
        </form>\
        ';
    var footer = '<button type="button" class="btn btn-primary" onclick="doCancelTopicLiked(\''+modalId+'\')">确定</button>';
    var $modal = topicModal(modalId, title, body, footer);
    $modal.modal();
}

/**
 * 执行取消点赞
 */
function doCancelTopicLiked(modalId) {
    var url = basePath + "/rest/p/cancelLiked";
    $.post(url, $("#commentForm").serialize(), function (result) {
        if (result.status == 0) {
            topicAlert({
                result: "success",
                message: "取消点赞成功",
                $parent: $("#"+modalId+" .modal-body"),
                success: function () {
                    location.reload();
                }
            });
        } else {
            topicAlert({
                result: "danger",
                message: result.message,
                $parent: $("#"+ modalId +" .modal-body")
            });
        }
    });
}

/**
 * 点赞
 */
function topicLiked(postId) {
    var modalId = "postLiked";
    var title = "点赞帖子";
    if( postId === undefined || postId === null || postId === ''){
        return ;
    }
    var body = '\
        <form id="commentForm">'
        + '<input type="hidden" name="postId" value="' + postId + '">'
          +'  <div class="form-group">\
               <span>感谢您的赞赏!</span>\
            </div>\
        </form>\
        ';
    var footer = '<button type="button" class="btn btn-primary" onclick="doTopicLiked(\''+modalId+'\')">确定</button>';
    var $modal = topicModal(modalId, title, body, footer);
    $modal.modal();
}

/**
 * 执行点赞
 */
function doTopicLiked(modalId) {
    var url = basePath + "/rest/p/liked";
    $.post(url, $("#commentForm").serialize(), function (result) {
        if (result.status == 0) {
            topicAlert({
                result: "success",
                message: "点赞成功",
                $parent: $("#"+modalId+" .modal-body"),
                success: function () {
                    location.reload();
                }
            });
        } else {
            topicAlert({
                result: "danger",
                message: result.message,
                $parent: $("#"+ modalId +" .modal-body")
            });
        }
    });
}

/**
 * 删除评论
 */
function delComment(commentId) {
    var modalId = "delComment";
    var title = "删除评论";
    if( commentId === undefined || commentId === null || commentId === ''){
        return ;
    }
    var body = '\
        <form id="commentForm">'
        + '<input type="hidden" name="commentId" value="' + commentId + '">'
          +'  <div class="form-group">\
               <span>确认删除此条评论吗?</span>\
            </div>\
        </form>\
        ';
    var footer = '<button type="button" class="btn btn-primary" onclick="doDelComment(\''+modalId+'\')">删除</button>';
    var $modal = topicModal(modalId, title, body, footer);
    $modal.modal();
}

/**
 * 执行删除评论
 */
function doDelComment(modalId) {
    var url = basePath + "/rest/c/del";
    $.post(url, $("#commentForm").serialize(), function (result) {
        if (result.status == 0) {
            topicAlert({
                result: "success",
                message: "删除成功",
                $parent: $("#"+modalId+" .modal-body"),
                success: function () {
                    location.reload();
                }
            });
        } else {
            topicAlert({
                result: "danger",
                message: result.message,
                $parent: $("#"+ modalId +" .modal-body")
            });
        }
    });
}

/**
 * 评论话题
 */
function topicComment(postId,commentId,title,modalId) {
    if( modalId === undefined || modalId === null || modalId === ''){
        modalId = 'commentModal';
    }
    if(title === null || title === undefined || title === ''){
        title = '评论';
    }
    var str = '<input type="hidden" name="postId" value="' + postId + '">';
    if(commentId !== null && commentId !== undefined && commentId !== ''){
        str += '<input type="hidden" name="commentId" value="' + commentId + '">';
    }
    var body = '\
        <form id="commentForm">'+
                str
            +'\
            <div class="form-group">\
                <label for="">内容</label>\
                <textarea name="content" class="form-control" rows="5"></textarea>\
            </div>\
        </form>\
        ';
    var footer = '<button type="button" class="btn btn-primary" onclick="doTopicComment(\''+modalId+'\')">发表</button>';
    var $modal = topicModal(modalId, title, body, footer);
    $modal.modal();
}

function doTopicComment(modalId) {
    var url = basePath + "/rest/c";
    var str = $("#commentForm").find(".form-control").val();
    str = str.replace(/\n/g,"<br/>").replace(/\s/g,"&nbsp;");
    $("#commentForm").find(".form-control").val(str);
    $.post(url, $("#commentForm").serialize(), function (result) {
        if (result.status == 0) {
            topicAlert({
                result: "success",
                message: "发表成功",
                $parent: $("#"+modalId+" .modal-body"),
                success: function () {
                    location.reload();
                }
            });
        } else {
            topicAlert({
                result: "danger",
                message: result.message,
                $parent: $("#"+modalId+" .modal-body")
            });
        }
    });
}



