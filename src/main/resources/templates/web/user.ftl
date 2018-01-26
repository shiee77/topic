<#import "lib/component.ftl" as c>

<@c.baseHtml title="用户信息">
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
<style>
	.wrapper {
		margin-top: 1rem;
		padding: 2rem 1rem;
	}
</style>
<div class="container">
	<div class="wrapper col-md-9">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="col-md-4  ">
					<div class="text-left">
						<h2>${user.username}</h2>
					</div>
				</div>
			</div>
		</div>
        <div class="nav2-bg"><@c.BSnavUser userId=user.id title=title /></div>
        <div class="list-group">
            <#if posts?? && posts?size gt 0 >
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
                <#else >
                    <li class="list-group-item">
                        <div class="list-title">
                           <span>未找到对应的数据!</span>
                        </div>

                    </li>

            </#if>

        </div>
	</div>
</div>
<script>
</script>
</@c.baseHtml>
