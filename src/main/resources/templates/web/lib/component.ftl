<#macro baseHtml title="国泰论坛" searchInfo=''>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${title}</title>

	<link href="${basePath}/module/bootstrap/css/bootstrap.min.css" rel="stylesheet">

	<script>
		var basePath = "${basePath}";
	</script>
	<script src="${basePath}/module/jquery/jquery-1.12.3.min.js"></script>
	<script src="${basePath}/module/jquery.cookie/jquery.cookie.js"></script>
	<script src="${basePath}/module/bootstrap/js/bootstrap.min.js"></script>
	<script src="${basePath}/module/topic/js/topic.js"></script>
	<style>
		#content {
			background: #ccc;
		}
		.SearchBar-searchIcon {
            display: inline-block;
            padding: 0 16px;
            font-size: 14px;
            line-height: 1px;
            color: #8590a6;
            text-align: center;
            cursor: pointer;
            right: -21px;
            top: -16px;
            bottom: -16px;
            background: none;
            border-radius: 3px;
            border: none;
            margin: 0;
		}

		.Input-after {
            position: absolute;
            top: 42%;
            right: 0px;
            display: flex;
            -webkit-box-orient: vertical;
            -webkit-box-direction: normal;
            margin: auto;
            flex-direction: column;
            transform: translate3d(0,-50%,0);
            -webkit-box-pack: center;
		}
		.Input-wrapper--grey{
            background: #f7f8fa;
			width: 326px;
			height: 34px;
			padding: 4px 10px;
			font-size: 14px;
			border: 1px solid #e7eaf1;
			border-radius: 3px;
			box-sizing: border-box;
		}
	</style>
</head>
<body>
<div>
	<div id="header">

        <@BSnav navs=topNav searchInfo=searchInfo/>
	</div>
	<div id="content">
        <#nested>
	</div>
	<div id="footer">
		<div class="container">

		</div>
	</div>
</div>
</body>
</html>
</#macro>

<#-- 带标签的input,label:显示内容 name:name -->
<#macro BSinput label name type="text" id="">
<div class="form-group">
	<label for="${id}">${label}</label>
	<input type="${type}" class="form-control" id="${id}" name="${name}">
</div>
</#macro>

<#-- 一级导航,navs:Topic列表,topicId:topicId -->
<#macro BSnav navs searchInfo=''>
<nav class="navbar-default">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="${basePath}/">首页</a>
		</div>
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
                <#list navs as nav>
					<li class=""><a href="${basePath}/t/${nav.id}">${nav.name}</a></li>
                </#list>
			</ul>
			<div style="    display: flex; justify-content: space-between;">
				<div class="SearchBar-toolWrapper"  style="display: flex; margin-top: 10px;margin-left: 16%;">
					<form class="SearchBar-tool"  style="position: relative; float: left; overflow: hidden;">
						<div  >
							<div    >
								<div class="SearchBar-input Input-wrapper Input-wrapper--grey" >
									<input type="text" maxlength="100" value="${searchInfo}"  id="Popover-15392-80405-toggle"
                                           onkeypress="if(event.keyCode==13) {searchInfoDiv.click();return false;}"
										   placeholder="搜索你感兴趣的内容…"
										style="padding: 0; font-family: inherit; font-size: inherit;  font-weight: inherit;width:300px; background: transparent; border: none; outline: none; resize: none;">
									<div class="Input-after" id="searchInfoDiv" name="searchInfoDiv" onclick="getSearchInfo()"  >
										<span class="  SearchBar-searchIcon " aria-label="搜索"    >
											<svg viewBox="0 0 16 16" class="Icon Icon--search" style="height:16px;width:16px;" width="16" height="16" aria-hidden="true"  >

												<g   style="fill: #afbdcf;">
													<path d="M12.054 10.864c.887-1.14 1.42-2.57 1.42-4.127C13.474 3.017 10.457 0 6.737 0S0 3.016 0 6.737c0 3.72 3.016 6.737 6.737 6.737 1.556 0 2.985-.533 4.127-1.42l3.103 3.104c.765.46 1.705-.37 1.19-1.19l-3.103-3.104zm-5.317.925c-2.786 0-5.053-2.267-5.053-5.053S3.95 1.684 6.737 1.684 11.79 3.95 11.79 6.737 9.522 11.79 6.736 11.79z"></path>
												</g>
											</svg>
										</span>
									</div>
								</div><!-- react-empty: 29 -->
							</div>
						</div>
					</form>
				</div>
				<div>
					<ul class="nav navbar-nav navbar-right">
						<#if Session.sessionUser??>
							<li class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
								   aria-expanded="false">
								${Session.sessionUser.username}
									<span class="caret"></span>
								</a>
								<ul class="dropdown-menu">
									<li><a href="${basePath}/u/${Session.sessionUser.id}">个人中心</a></li>
									<li role="separator" class="divider"></li>
									<li><a href="javascript:;" onclick="logout()">注销</a></li>
								</ul>
							</li>
						<#else>
							<li><a href="${basePath}/login">登录</a></li>
							<li><a href="${basePath}/register">注册</a></li>
						</#if>
					</ul>
				</div>
            </div>
		</div>
	</div>
</nav>
</#macro>

<#-- 二级导航,navs:Topic列表,topicId:topicId -->
<#macro BSnav2 navs topicId=0>
<ul class="nav nav-pills">
    <#list navs as nav>
		<#--索引值大于或等于6，则跳出循环-->
        <#if nav?index gte 6><#break></#if>
		<li role="presentation" <#if nav.id == topicId>class="active"</#if>>
			<a href="${basePath}/t/${nav.id}">${nav.name}</a>
		</li>
    </#list>

</ul>
</#macro>

<#macro BSnavUser userId  title=0>
<ul class="nav nav-pills">
		<li role="presentation"  <#if title == 0> class="active" </#if> >
			<a href="${basePath}/u/${userId}">浏览</a>
		</li>
        <#--<li role="presentation" class="active">-->
            <#--<a href="${basePath}/u/activities">动态</a>-->
        <#--</li>-->
		<li role="presentation" <#if title == 1> class="active" </#if>  >
            <a href="${basePath}/u/following/${userId}">关注</a>
        </li>
		<li role="presentation"  <#if title == 2> class="active" </#if> >
            <a href="${basePath}/u/collections/${userId}">收藏</a>
        </li>
		<li role="presentation" <#if title == 3> class="active" </#if> >
            <a href="${basePath}/u/answers/${userId}">回复</a>
        </li>

</ul>
</#macro>

<#-- 路径导航 -->
<#macro BSnav3 navs>
<ol class="breadcrumb" style="margin: 0; padding: 0.5rem 1rem">
    <#list navs as nav>
		<li><a href="${basePath}/t/${nav.id}">${nav.name}</a></li>
    </#list>
</ol>
</#macro>

<#-- 页码,url:无参路径,page:分页模型 -->
<#macro BSpage url page>
<ul class="pagination">
	<li>
		<a href="${url}?page=${page.prePage}" aria-label="Previous">
			<span aria-hidden="true">&laquo;</span>
		</a>
	</li>
    <#list page.startPage..<page.page as p >
		<li>
			<a class="active" href="${url}?page=${p}">${p}</a>
		</li>
    </#list>
    <#list page.page..page.endPage as p >
		<li <#if page.page == p>class="active"</#if>>
			<a href="${url}?page=${p}">${p}</a>
		</li>
    </#list>
	<li>
		<a href="${url}?page=${page.nextPage}" aria-label="Next">
			<span aria-hidden="true">&raquo;</span>
		</a>
	</li>
</ul>
</#macro>

