<#macro selectForField field>
	<#assign value>
		<@spring.messageText "${field.label}.select.value" ""/><#t>
	</#assign>
	<#assign label>
		<@spring.messageText "${field.label}.select.label" ""/><#t>
	</#assign>
	<select class="form-control">
	<#if field.type.list?exists>
		<#list field.type.list as item>
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
	    				 	${newLabel + item["${labelComponent?trim}"]!}<#t>
						</#assign>
					</#if>
				</#list>
				</#compress>
				<option value="${item["${value}"]!}">${newLabel!}</option>
			<#else>
	    		<option value="${item["${value}"]!}">${item["${label}"]!"${field.label}.select.label"}</option>
			</#if>
		</#list>
	</#if>
	</select>
</#macro>

<#macro selectForFixedDomain field>
	<#assign domain>
		<@spring.messageText "${field.label}.jsonDomain" ""/>
	</#assign>
	<#if domain?length gt 0>
		<@spring.formSingleSelect "${field.name}" "${domain}"?eval "class='form-control'"/><#t>
	<#else>
    	<@spring.formInput "${field.name}", "class='form-control' maxlength='${field.length!0}'"/><#t>
	</#if>
</#macro>

<html>
<head>
<title>
<@spring.message "${page.title}"/>
</title>
</head>
<body>
    <div class="container-fluid">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="tabbable" id="tab-list">
                    <ul class="nav nav-tabs">
                        <li class="active">
                            <a href="#cadastro" data-toggle="tab"><@spring.message "form.tab.appform"/></a>
                        </li>
                        <li>
                            <a href="#listagem" data-toggle="tab"><@spring.message "form.tab.list"/></a>
                        </li>
                    </ul>
                    <div class="tab-content">
                        <div class="tab-pane active" id="cadastro">
                            <form method='post' action='${page.properties.creation?exists?string('./create', './update')}'>
                            <#-- 
                            enctype="application/json; charset=utf-8"
                            -->
                                <div class="col-md-12 column">
								<#list page.formFields as field>
                                    <#switch "${field.type}">
										<#case "HIDDEN">
                                        	<@spring.formHiddenInput "${field.name}", "class='form-control' maxlength='${field.length!0}'"/><#t>
									    <#break>
										<#case "INTEGER">
                                        <div class="form-group">
                                            <label class="col-sm-2 text-right control-label"><@spring.messageText "${field.label}" "${field.label}"/></label>
                                            <div class="col-sm-10">
                                            	<@spring.formInput "${field.name}", "class='form-control' maxlength='${field.length!0}'", "number"/><#t>
	                                        </div>
                                        </div>
										<#break>
										<#case "TEXT">
                                        <div class="form-group">
                                            <label class="col-sm-2 text-right control-label"><@spring.messageText "${field.label}" "${field.label}"/></label>
                                            <div class="col-sm-10">
                                            	<@selectForFixedDomain field=field/>
	                                        </div>
                                        </div>
										<#break>
										<#case "SELECT">
                                        <div class="form-group">
                                            <label class="col-sm-2 text-right control-label"><@spring.messageText "${field.label}" "${field.label}"/></label>
                                            <div class="col-sm-10">
                                            <#--
                                        		<@selectForField field=field/>
                                            -->
											<@spring.formSingleSelect "${field.name}.${field.type.idFieldName}" {} "class='form-control' aria-populate='${field.type.reference}'"/><#t>
	                                        </div>
                                        </div>
								    	<#break>
                                	</#switch>
								</#list>
                                <div class="form-group">
                                    <div class="col-sm-offset-2 col-sm-10">
                                    <#--
                                    em caso de submissão ajax
                                    	<button type="button" class="btn btn-primary" onclick="submitForm($(this))"><@spring.message "form.button.save"/></button>
                                    -->
                                    	<input type="submit" class="btn btn-primary" value="<@spring.message "form.button.save"/>"/>
                                    <#--
                                    -->
                                    <button type="reset" class="btn btn-default"><@spring.message "form.button.clean"/></button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="tab-pane" id="listagem" >
                        <div class="container-fluid">
                            <div class="row clearfix">
                                <div class="col-md-12 column">
                                    <div class="form-group">
                                    botões
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                        
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>