<#assign form=JspTaglibs["http://www.springframework.org/tags/form"] />

<html>
<head>
<title>
<@spring.messageText "${page.title}" "${page.title}"/>
</title>
</head>
<body>
${rowCount!"ddd"}
${currentPage!"1"}
${pageSize!"0"}
    <div class="container-fluid">
        <div class="row clearfix">
            <div class="col-md-12 column">
				<#assign gridFieldsCandidate>
					<@spring.messageText "${page.entityName}.page.grid.fields" ""/>
				</#assign>
				<#if gridFieldsCandidate?length gt 0>
					<#assign gridFields = gridFieldsCandidate?split(",")>
				<#else>
					<#assign gridFields = {}>
				</#if>
            
            	<table class="table table-bordered table-hover">
            	<thead>
            		<tr>
					<#list page.formFields as field>
                        <#if "${field.type}" != "HIDDEN">
							<#assign name>
								${field.name[field.name?string?last_index_of('.')+1..]}<#t>
							</#assign>
							<#if (gridFields?size gt 0 && gridFields?seq_contains(name)) || (gridFields?size == 0)>
							<td>
								<@spring.messageText "${field.label}" "${field.label}"/>
							</td>
							</#if>
						</#if>
					</#list>
						<td>
							<@spring.messageText "grid.action" "grid.action"/>
						</td>
            		</tr>
            	</thead>
            	<tbody>
					<#list list as item>
            		<tr>
						<#list page.formFields as field>
		                    <#if "${field.type}" != "HIDDEN">
								<#assign name>
									${field.name[field.name?string?last_index_of('.')+1..]}<#t>
								</#assign>
								<#if (gridFields?size gt 0 && gridFields?seq_contains(name)) || (gridFields?size == 0)>
									<td>
				                    <#if "${field.type}" == "SELECT">
				                    	<#assign label>
											<@spring.messageText "${name}.tinyList.label" "${name}.tinyList.label"/>
										</#assign>
	
										<#if label?index_of("+") gt 0>
											<#assign newLabel = ""/>
											<#compress>
											<#list label?split("+") as labelComponent>
												<#if labelComponent?contains("\"") || labelComponent?contains("\'")>
								    				<#assign newLabel>
												 		${newLabel + labelComponent?replace("\"", "")?replace("\'", "")}<#t>
								    				</#assign>
								    			<#else>
								    				<#assign newLabel>
								    				 	${newLabel + item["${name}"]["${labelComponent?trim}"]!}<#t>
													</#assign>
												</#if>
											</#list>
											</#compress>
											${newLabel}
										<#else>
											${item["${name}"][label]}
										</#if>
									<#else>
										${item[name]}
									</#if>
									</td>
								</#if>
							</#if>
						</#list>
						<td>
							<a href="../../edit/${item["id"]}">Editar</a>
							<a onClick="removal(${item['id']}, this)">Excluir</a>
						</td>
            		</tr>
					</#list>
            	</tbody>
            	<tfoot>
            	</tfoot>
            	</table>
            </div>
            <div class="form-group">
                <div class="col-sm-12">
					<a href="../../">
		                <button class="btn btn-primary">
							<@spring.messageText "form.button.new" "form.button.new"/>
						</button>
					</a>
                </div>
            </div>

        </div>
    </div>
</body>
</html>