<#assign form=JspTaglibs["http://www.springframework.org/tags/form"] />
<#assign display=JspTaglibs["http://displaytag.sf.net"] />

<#function deepHash obj path>
	<#assign hashes = path?split(".")>
	<#assign value = obj/>
	<#list hashes as hash>
		<#assign value = value[hash]!/>
	</#list>
	<#if value?is_boolean>
		<#return value?c>
	<#else>
		<#return value>
	</#if>
</#function>

<#macro splitLabel obj label><@compress>
	<#if label?index_of("+") gt 0>
		<#assign newLabel = ""/>
		<#list label?split("+") as labelComponent>
			<#if labelComponent?contains("\"") || labelComponent?contains("\'")>
				<#assign newLabel>${newLabel + labelComponent?trim?replace("\"", "")?replace("\'", "")}</#assign>
			<#else>
				<#assign newLabel>${newLabel + obj[labelComponent?trim]!}</#assign>
			</#if>
		</#list>
		${newLabel}<#t>
	<#else>
		${obj[label]!}
	</#if>
</@compress></#macro>

<#assign gridFieldsCandidate>
	<@spring.messageText "${page.entityName}.page.grid.fields" ""/>
</#assign>
<#if gridFieldsCandidate?length gt 0>
	<#assign gridFields = gridFieldsCandidate?split(",")>
<#else>
	<#assign gridFields = {}>
</#if>
<#-- 
<#list list as item>
	<#list page.formFields?keys as path>
 -->
<#function getFormField item path>
		<#assign field = page.formFields[path]>
		<#if "${field.type}" != "HIDDEN">
			<#assign name>
				${field.name[field.name?string?index_of('.')+1..]}<#t>
			</#assign>

			<#if (gridFields?size gt 0 && gridFields?seq_contains(name)) || (gridFields?size == 0)>
				<#switch "${field.type}">
					<#case "SELECT">
						<#assign view>
							<#assign value = deepHash(item, name) />
							<#assign label>
								<@spring.messageText "${field.label}.label" ""/>
							</#assign>
							<#if !label?trim?has_content>
								<#assign label>
									<@spring.messageText "${field.entity}.tinyList.label" ""/>
								</#assign>
							</#if>
							<#if label?trim?has_content>
								<@splitLabel value label />
							<#else>
								${value}
							</#if>
						</#assign>>
					<#break>
					<#case "BOOLEAN">
						<#assign view>
							<#assign domain>
								<@spring.messageText "${field.label}.jsonDomain" ""/>
							</#assign>
							<#assign value = deepHash(item, name) />
							<#if domain?trim?has_content>
								${domain?eval[value?string?trim]}
							<#else>
								${deepHash(item, name)!}
							</#if>
						</#assign>>
					<#break>
					<#case "DETAIL">
						<#assign view>
							<#list item[name] as detail>
								${detail[label]}
							</#list>
						</#assign>>
					<#break>
					<#case "DATE">
						<#assign view>
							<#if item[name]?has_content>
								${item[name]?date?string['dd/MM/yyyy']}
							<#else>
								${item[name]!}
							</#if>
						</#assign>>
					<#break>
					<#default>
						<#assign view>
							${deepHash(item,name)!}
						</#assign>>
					<#break>
				</#switch>
				${field?api.setView(view)}
			</#if>
		</#if>
	<#return field>
</#function>
<#-- 
	</#list>
</#list>
 -->

<#--
 <@display.table name="list" pagesize=pageSize size=rowCount partialList=true >
	<#list page.formFields as field>
        <#if "${field.type}" != "HIDDEN">
			<#assign name>
				${field.name[field.name?string?last_index_of('.')+1..]}<#t>
			</#assign>
			<#if (gridFields?size gt 0 && gridFields?seq_contains(name)) || (gridFields?size == 0)>
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
			    				<# - -
			    				 	${newLabel + item["${name}"]["${labelComponent?trim}"]!}<#t>
			    				- - >
								</#assign>
							</#if>
						</#list>
						</#compress>
						${newLabel}
					<#else>
					<# - -
						${item["${name}"][label]}
					- - >
					</#if>
				<#else>
					<@display.column property="${name}" />
				</#if>
			</#if>
		</#if>
	</#list>
</@display.table>
-->            
