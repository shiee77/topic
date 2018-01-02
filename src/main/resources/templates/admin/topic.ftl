<#import "lib/component.ftl" as c>

<@c.baseHtml contentTitle="目录管理">
<div class="box">
	<div class="box-body">
		<div class="col-md-3">
			<div id="topicTree">
			</div>
		</div>
		<div class="col-md-7">
			<form id="topicForm" class="form-horizontal">
				<input type="hidden" name="id">
				<input type="hidden" name="_method" value="put">
                <@c.BSinput label="名称" name="name"/>
				<div class="form-group">
					<label class="col-md-3 control-label">状态</label>
					<div class="col-md-8">
						<select class="form-control" name="available">
							<option value="1">使用中</option>
							<option value="2">未使用</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-3 control-label">显示内容</label>
					<div class="col-md-8">
						<select class="form-control" name="pageType">
							<option value="1">显示子目录</option>
							<option value="2">显示文章</option>
						</select>
					</div>
				</div>
				<div id="showTopic">
					<div class="form-group">
						<label class="col-md-3 control-label">二级导航</label>
						<div class="col-md-8">
							<select class="form-control" name="secNav">
								<option value="0">不显示</option>
								<option value="1">显示兄弟目录</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">文章排序方式</label>
						<div class="col-md-8">
							<select class="form-control" name="orderType">
								<option value="1">创建时间优先</option>
								<option value="2">更新时间优先</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-3 control-label">文章显示内容</label>
						<div class="col-md-8">
							<div class="form-group">
                                <@c.BScheckbox name="postShowTypes" text="标题" value="1"/>
                                <@c.BScheckbox name="postShowTypes" text="作者" value="2"/>
                                <@c.BScheckbox name="postShowTypes" text="创建时间" value="3"/>
                                <@c.BScheckbox name="postShowTypes" text="更新时间" value="4"/>
                                <@c.BScheckbox name="postShowTypes" text="回复数量" value="5"/>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
		<div class="col-md-2 text-right">
			<button type="button" class="btn btn-app" onclick="addChild()">
				<i class="fa fa-plus-square"></i>
				添加子目录
			</button>
			<button type="button" class="btn btn-app" onclick="saveTopic()">
				<i class="fa fa-save"></i>
				保存
			</button>
		</div>
	</div>
</div>

<script>
	$(function () {
		window.selectedTopicId = -1;
		initTreeView();
	});
	function initTreeView() {
		var url = "${basePath}/admin/rest/t/tree";
		$.get(url, {}, function (result) {
			if (result.status == 0) {
				$("#topicTree").treeview({
					data: result.data.tree,
					expandIcon: 'fa fa-plus',
					collapseIcon: 'fa fa-minus',
					onNodeSelected: function (event, data) {
						getTopicDetail(data.id);
						window.selectedTopicId = data.id;
					}
				});
				$("#topicTree li:first-child").trigger('click');
			}
		});
	}
	function getTopicDetail(topicId) {
		var url = "${basePath}/admin/rest/t/" + topicId;
		$.get(url, {}, function (result) {
			if (result.status == 0) {
			    var topic = result.data.topic;
				$("#topicForm [name='id']").val(topic.id);
				$("#topicForm [name='name']").val(topic.name);
				$("#topicForm [name='available']").val(topic.available);
				$("#topicForm [name='pageType']").val(topic.pageType);
				$("#topicForm [name='orderType']").val(topic.orderType);
				$("#topicForm [name='secNav']").val(topic.secNav);

				assignCheckbox($("#topicForm [name='postShowTypes']"), topic.postShowTypes);

				var $div = $("#topicForm #showTopic");
				if(topic.pageType == 1)
					$div.hide();
				else $div.show()
			} else {
				topicAlert2({result: "danger", message: result.message});
			}
		})
	}
	function saveTopic() {
		var url = "${basePath}/admin/rest/t/" + window.selectedTopicId;
		$.post(url, $("#topicForm").serialize(), function (result) {
			if (result.status == 0) {
				topicAlert2({
					result: "success", message: '更新成功', success: function () {
						location.reload();
					}
				});
			} else {
				topicAlert2({result: "danger", message: result.message});
			}
		});
	}
	function addChild() {
		var modalId = "addChildModal";
		var title = "添加子目录";
		var body = '\
            <form id="addTopicForm">\
                <input type="hidden" name="parentId" value="' + window.selectedTopicId + '">\
                <div class="form-group">\
                    <label for="">名称</label>\
                    <input type="text" class="form-control" name="name">\
                </div>\
            </form>\
        ';
		var footer = '<button type="button" class="btn btn-primary" onclick="doAddChild()">添加</button>';
		var $div = topicModal(modalId, title, body, footer);
		$div.modal();
	}
	function doAddChild() {
		var url = "${basePath}/admin/rest/t";
		$.post(url, $("#addTopicForm").serialize(), function (result) {
			if (result.status == 0) {
				topicAlert({
					result: "success",
					message: '添加成功',
					$parent: $("#addTopicForm"),
					success: function () {
						location.reload();
					}
				});
			} else {
				topicAlert({result: "danger", message: result.message, $parent: $("#addTopicForm")});
			}
		});
	}
</script>
</@c.baseHtml>