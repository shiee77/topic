<#import "lib/component.ftl" as c>

<@c.baseHtml>
<style>
	#main {
		padding-top: 2rem;
		width: 70%;
		padding-bottom: 2rem;
	}
</style>
<div class="container">
	<div id="main">
		<div>
			<@c.BSnav3 navs=parentTopic/>
		</div>
		<div class="panel panel-default">
			<div class="panel-heading">
				<span style="font-weight: 600;font-size: 18px;">${post.title}</span>
				<span class="navbar-right" style="margin-right:5px;">
                    创建时间：${post.createTime?string("yyyy-MM-dd HH:mm:ss")}
                    来自: <a href="${basePath}/u/${post.userId}">${post.username}</a>
                </span>
			</div>
			<div class="panel-body">
				<span style="color: #8590a6;font-size:14px;">0 人赞同了该帖子</span>
				<div class="pre">${post.content}</div>
			</div>
			<div class="panel-footer" style="padding: 0.1rem 1rem;">
				<div style="text-align: right">
                    <#if Session.sessionUser??>
						<button type="button" class="btn btn-default btn-sm" onclick="topicComment('${postId}')">评论
						</button>
                        <button type="button" class="btn btn-default btn-sm" >点赞
                        </button>
                        <button type="button" class="btn btn-default btn-sm" >举报
                        </button>
                    </#if>
				</div>

			</div>
		</div>
		<ul class="list-group">
            <#list comments as comment>
				<li class="list-group-item">
					#${comment.floor} - <a href="${basePath}/u/${comment.userId}">${comment.username}</a>
					<#if comment.parent?? && comment.parentUserName??> 回复 <a href="${basePath}/u/${comment.parentUserId}">${comment.parentUserName}</a> </#if>

					<div class="pre">${comment.content}</div>
				    <div class="col-md-12"  >
						<div class="col-sm-6"  style="text-align: left;padding-top: 5px;">
							<span style="text-align: left;padding-top: 5px;"><a>查看对话(10)</a> </span>
							<span>回复时间：${comment.createTime?string("yyyy-MM-dd HH:mm:ss")}</span>

						</div>
						<div      style="text-align: right;  ">
							<#if Session.sessionUser??>
								<button type="button" class="btn btn-default btn-sm" onclick="topicComment('${postId}','${comment.id}','回复','topicCommentRe')">回复
								</button>
								<button type="button" class="btn btn-default btn-sm" >点赞
								</button>
								<button type="button" class="btn btn-default btn-sm" >举报
								</button>
							</#if>
						</div>
                      </div>
                    <div class="clearfix"></div>
				</li>
            </#list>
		</ul>
        <@c.BSpage url=url page=page />
	</div>
</div>
</@c.baseHtml>