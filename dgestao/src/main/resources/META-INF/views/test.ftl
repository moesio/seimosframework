<#--
<#assign form=JspTaglibs["/WEB-INF/tld/spring.tld"]/>
<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]/>
-->
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"] />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Teste de variáveis</title>
</head>
<body>
${1+2}<br/>
<@spring.bind "produto" />
<#--
<#list spring.status.errorMessages as error>
-${error?html}<br/>
</#list>
-->
<@spring.showErrors "produto"/>
<@form.errors path="produto.codigo" cssClass="error" /><br/>
<@form.errors path="produto.nome" cssClass="error" /><br/>
<@form.errors path="produto.validadeAposAberto" cssClass="error" /><br/>
</body>
</html>