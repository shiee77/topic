<#import "web/lib/component.ftl" as c>

<@c.baseHtml title="错误页面">
<style>
	.wrapper {
		margin-top: 1rem;
		padding: 2rem 1rem;
	}
</style>
<div class="container">
	<div class="wrapper">
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="col-md-4 col-md-offset-4">
					<div class="text-center">
                      <h1>${status} ${message}</h1>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
</script>
</@c.baseHtml>
