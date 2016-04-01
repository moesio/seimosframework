<html>
<head>
<title>
<#---
<@spring.message "${webForm.title}"/>
-->
</title>
</head>
<body>
    <div class="container-fluid">
        <div class="row clearfix">
            <div class="col-md-12 column">
            	<table class="grid">
            	<thead>
            		<tr>
					<#list page.formFields as field>
                        <#if "${field.type}" != "HIDDEN">
						<td>
							<@spring.messageText "${field.label}" "${field.label}"/>
						</td>
						</#if>
					</#list>
						<td>
							Ações
						</td>
            		</tr>
            	</thead>
            	<tbody>
					<#list list as item>
            		<tr>
						<#list page.formFields as field>
		                    <#if "${field.type}" != "HIDDEN">
								<td>
								<#assign name>
									${field.name[field.name?string?last_index_of('.')+1..]}<#t>
								</#assign>
								${item[name]}
								</td>
							</#if>
						</#list>
						<td>
							<a href="edit/${item["id"]}">Editar</a> <a onClick="removal(${item['id']}, this)">Excluir</a>
						</td>
            		</tr>
					</#list>
            	</tbody>
            	<tfoot>
            	</tfoot>
            	</table>
            </div>
        </div>
    </div>
</body>
</html>