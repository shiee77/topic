<#import "lib/component.ftl" as c>

<@c.baseHtml searchInfo=searchInfo>
<style>
	.list-title a {
		color: #555555;
		font-size: 18px;
	}

	.nav2-bg {
		background: #ddd;
		border-radius: 0.4rem;
	}

	.wrapper {
		padding: 2rem 1rem;
		float: left;
	}

	.column {
		padding: 3rem 0;
		background: #fff;
	}
</style>
<div class="container">
	<div id="main" class="wrapper col-md-9">
        <#if nav2?? >
			<div class="nav2-bg"><@c.BSnav2 navs=nav2 topicId=topicId/></div>
        </#if>
        <#if childTopics??>
			<div class="row">
				<#--在这个位置需要将子模块增加进去-->
                <#list childTopics as topic>
					<div class="col-sm-6 col-md-4">
						<div class="thumbnail">
							<div class="caption">
								<a href="${basePath}/t/${topic.id}">
									<h3>${topic.name}</h3>
									<p>
									</p>
								</a>
							</div>
						</div>
					</div>
                </#list>
			</div>
        </#if>
		<#if posts?? && posts?size gt 0>
            <div class="list-group">
				<#list posts as post>
                    <li class="list-group-item">
                        <div class="list-title">
                            <a href="${basePath}/p/${post.id}">${post.title}</a>
                        </div>
                        <div class="pull-right">
                            <span class="badge">${post.commentNumber}</span>
                        </div>
                        <div class="post-bottom">
                            <span>作者：<a href="/u/${post.userId}">${post.username}</a></span>
                            <span>创建时间：${post.createTime?string("yyyy-MM-dd HH:mm:ss")}</span>
                            <span>最后回复时间：${post.updateTime?string("yyyy-MM-dd HH:mm:ss")}</span>
                        </div>
                    </li>
				</#list>
            </div>
            <div><@c.BSpage page=page url=url/></div>
			<#else >
                <div class="list-group">

					<li class="list-group-item">
						<div class="list-title">
							<span>未查询到对应的数据!</span>
						</div>
					</li>

                </div>
                <div><@c.BSpage page=page url=url/></div>
		</#if>
	</div>
	<div class="wrapper col-md-3">
        <#if Session.sessionUser?? && topicId?? && posts??>
			<div class="column">
				<div class="text-center">
					<button type="button" class="btn btn-default" onclick="topicPost('${topicId}')">发表新话题</button>
				</div>
			</div>
        </#if>
	</div>
</div>
</@c.baseHtml>