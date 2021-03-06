<#assign form=JspTaglibs["http://www.springframework.org/tags/form"] />
<#--
<#assign form=JspTaglibs["/WEB-INF/tld/spring.tld"]/>
<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]/>
-->

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
	    	<@spring.formInput "${field.name}", "maxlength='${field.length!0}'"/><#t>
	</#if>
</#macro>

<@spring.bind page.entityName />

<#list page.formFields?keys as path>
	<#assign field = page.formFields[path] />
	<#switch "${field.type}">
		<#case "HIDDEN">
			<#assign view>
				<@spring.formHiddenInput "${field.name}", "maxlength='${field.length!0}'"/><#t>
			</#assign>
		<#break>
		<#case "INTEGER">
			<#assign view>
				<@spring.formInput "${field.name}", "maxlength='${field.length!0}'", "number"/><#t>
			</#assign>
		<#break>
		<#case "TEXT">
			<#assign view>
				<@selectForFixedDomain field/>
			</#assign>
		<#break>
		<#case "SELECT">
			<#assign view>
				<#--
					<@selectForField field/>
				${field.populator?is_hash?c}
				-->
				<@spring.formSingleSelect "${field.name}.${field.idFieldName}" field.populator ""/><#t>
			</#assign>
		<#break>
		<#case "ENUM">
			<#assign view>
				enum
			</#assign>
		<#break>
		<#case "DETAIL">
			<#assign view>
				<#--
				<@spring.formMultiSelect "${field.name}" field.populator "class='form-control'"/><#t>
				-->
				<@spring.bind "${field.name}"/>
				<#assign subList = spring.status.value?default(" ")>
				<select multiple="multiple" id="${spring.status.expression}" name="${spring.status.expression}" >
					<#list field.populator?keys as value>
						<#if subList?contains(value) >
							<#assign isSelected = true>
						<#else>
							<#assign isSelected = false>
						</#if>
					<option value="${value?html}"<#if isSelected> selected="selected"</#if>>${field.populator[value]?html}
					</#list>
				</select>
			</#assign>
		<#break>
		<#case "DOUBLE">
			<#assign view>
				double
			</#assign>
		<#break>
		<#case "DATE">
			<#assign view>
				<@spring.formInput "${field.name}", "maxlength='${field.length!0}'", "date"/><#t>
			</#assign>
		<#break>
		<#case "BOOLEAN">
			<#assign view>
				<@spring.formCheckbox "${field.name}" />
			</#assign>
		<#break>
	</#switch>
	${field?api.setView(view)}
</#list>
