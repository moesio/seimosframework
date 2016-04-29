<#--
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"] />
-->
<#assign form=JspTaglibs["/WEB-INF/tld/spring.tld"]/>
<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]/>

<#macro selectForField field>
	<#assign value>
		<@spring.messageText "${field.label}.select.value" ""/><#t>
	</#assign>
	<#assign label>
		<@spring.messageText "${field.label}.select.label" ""/><#t>
	</#assign>
	&lt;select class="form-control"&gt;<br/>
	<#if field.populator?exists>
		<#list field.populator as item>
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
				option value="${item["${value}"]!}" ${newLabel!}1</option
			<#else>
				${field.label}
	    		&lt;option value="${item.id}"&gt;${item["nome"]}&lt;/option&gt;<br/>
			</#if>
		</#list>
	</#if>
	&lt;/select&gt;
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

<#macro label field>
	<label class="col-sm-2 text-right control-label ${field.mandatory?then('required', '')}"><@spring.messageText "${field.label}" "${field.label}"/></label>
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
	            <form method='post' action='${page.properties.creation?exists?then('./create', '../update')}'>
				<@spring.bind page.entityName />
		        <div class="col-md-12 column">
	            <#-- 
	            enctype="application/json; charset=utf-8"
	            -->
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
					<@spring.showErrors "<br/>" "text-danger"/>
						</div>
					</div>
					<#list page.formFields as field>
	                    <#switch "${field.type}">
							<#case "HIDDEN">
	                        	<@spring.formHiddenInput "${field.name}", "class='form-control' maxlength='${field.length!0}'"/><#t>
						    <#break>
							<#case "INTEGER">
	                        <div class="form-group">
	                        	<@label field=field/>
	                            <div class="col-sm-10">
	                            	<@spring.formInput "${field.name}", "class='form-control' maxlength='${field.length!0}'", "number"/><#t>
	                            </div>
	                        </div>
							<#break>
							<#case "TEXT">
	                        <div class="form-group">
	                        	<@label field=field/>
	                            <div class="col-sm-10">
	                            	<@selectForFixedDomain field=field/>
	                            </div>
	                        </div>
							<#break>
							<#case "SELECT">
	                        <div class="form-group">
	                        	<@label field=field/>
	                            <div class="col-sm-10">
	                            <#--
	                        		<@selectForField field=field/>
	                            -->
								<@spring.formSingleSelect "${field.name}.${field.idFieldName}" field.populator "class='form-control'"/><#t>
	                            </div>
	                        </div>
					    	<#break>
							<#case "DOUBLE">
					    	<#break>
							<#case "DATE">
					    	<#break>
							<#case "BOOLEAN">
					    	<#break>
	                	</#switch>
		                <div class="form-group">
		                    <div class="col-sm-offset-2 col-sm-10 bg-danger">
							<@form.errors path="${field.name}" cssClass="error" />
		                    </div>
		                </div>
					</#list>
	                <div class="form-group">
	                    <div class="col-sm-offset-2 col-sm-10">
	                    <#--
	                    em caso de submissão ajax
	                    	<button type="button" class="btn btn-primary" onclick="submitForm($(this))"><@spring.message "form.button.save"/></button>
	                    -->
	                    	<input type="submit" class="btn btn-primary" value="<@spring.message "form.button.save"/>"/>
	                    <button type="reset" class="btn btn-default"><@spring.message "form.button.clean"/></button>
	                    </div>
	                </div>
	            </div>
                </form>
	        </div>
	    </div>
    </div>
</body>
</html>