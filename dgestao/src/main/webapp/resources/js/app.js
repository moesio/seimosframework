(function(document) {
	"use strict";

	var app = angular.module('programacao', [ 'ui.bootstrap' ]);

	var context = "/dgestao/";

	$(this).ready(
			function() {
				if (this.location.pathname) {
					var page = this.location.pathname;
					createBreadcrumb($("nav a[href='" + page + "']"));
				}

				if ($('form')) {
					$('form').find('input[type!="hidden"],select').first()
							.eq(0).focus();
				}

				$.each($('select[aria-populate]'), function() {
					var entity = $(this).attr('aria-populate');
					var self = $(this);

					$.ajax({
						type : "GET",
						url : context + entity + "/tinyList",
						contentType : "application/json; charset=utf-8",
						dataType : "json",
						success : function(data) {
							populateSelect(self, data);
						}
					}).error(error);
				});

				/*
				$(".table").DataTable({
					"bServerSide": true,
					"bProcessing": true,
					"sPaginationType": "full_numbers",
					"sAjaxSource": "/dgestao/webjars/datatables/1.9.4/examples/ajax/sources/arrays.txt"
					"sAjaxSource": "/dgestao/genero/dataTable",
				});
				*/

			});

	var breadcrumbClick = function() {
		var href = $(this).attr('href');
		createBreadcrumb($('nav a[href="' + href + '"]'));
	};

	var createBreadcrumb = function(tagA) {
		if (tagA.is('a')) {
			var origin = tagA.closest('li');
			var breadcrumb = $('#breadcrumb');
			breadcrumb.html("");

			while (origin.length > 0) {
				breadcrumb.prepend(origin.find('a')[0].outerHTML);
				breadcrumb.prepend(" &raquo; ");
				origin = origin.parent().closest('li');
			}
			breadcrumb.prepend("<a href='./'>Início</a>");
			breadcrumb.find('a').click(breadcrumbClick);
		} else {
			console.log('Breadcrumb is created only from an "a" tag');
		}
	};

	var error = function(data) {
		console.log(JSON.stringify(data));
		console.log(data.statusText);
	}

	var populateSelect = function(select, data) {
		$.each(data, function(index, option) {
			select.append($("<option>", {
				value : option.value,
				text : option.text
			}));
			// console.log(JSON.stringify(option.label));
		});
	}

	window.removal = function(id, element) {
		$.ajax({
			type : "DELETE",
			url : "../../remove/" + id,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(data) {
				// console.log('Removeu ' + id);
				// $(element).closest('tr').remove();
				location.reload();
			}
		}).error(error).fail(error);
	}

	window.submitForm = function(element) {
		var form;
		if (element.is('form')) {
			form = element;
		} else {
			form = element.parents('form');
		}

		// console.log(form.serializeArray());

		var formSerializedResult = {}
		$.each(form.serializeArray(), function(e) {
			var name = formSerializedResult[this.name];
			if (name) {
				if (!name.push) {
					name = [ name ];
				}
				name.push(this.value || '');
			} else {
				formSerializedResult[this.name] = this.value || '';
			}
		});

		// console.log(JSON.stringify(formSerializedResult));

		$.ajax({
			type : "POST",
			url : context + "createAjax",
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			data : JSON.stringify(formSerializedResult),
			// data : $.param($('form').serializeArray()),
			success : function(data) {
				console.log('Com sucesso!');
				// form.find('button[type="reset"]').click();
			}
		}).error(error).fail(error);
	}

})();
