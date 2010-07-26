// Author: Thomas GAMBET.
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
var W3C = {
	
	start: function(){
		
		W3C.TableRows = $$('fieldset.translation table tbody tr');
	
		if ($('a_miss')) {
			
			$('a_miss').addEvent('click', function (event) {
				event.preventDefault();
				W3C.TableRows.each(function (tr) {
					if (tr.getElement('td.missing')) {
						tr.setStyle('display', '');
					} else {
						tr.setStyle('display', 'none');
					}
				});
			});
			
			$('a_mod').addEvent('click', function (event) {
				event.preventDefault();
				W3C.TableRows.each(function (tr) {
					if (tr.getElement('td.modified')) {
						tr.setStyle('display', '');
					} else {
						tr.setStyle('display', 'none');
					}
				});
			});
			
			$('a_all').addEvent('click', function (event) {
				event.preventDefault();
				W3C.TableRows.each(function (tr) {
					tr.setStyle('display', '');
				});
			});
			
			$$('fieldset.translation td').each(function (td) {
				var input = td.getElement('input');
				input.store('ucn:trad', input.value);
				
				input.addEvent('keyup', function (event) {
					if (input.value != input.retrieve('ucn:trad') && input.value != "") {
						td.addClass('modified');
						td.removeClass('missing');
					} else {
						td.removeClass('modified');
						if (input.value == "")
							td.addClass('missing');
					}
					
					W3C.updateLinks();
				});
			});
			
			W3C.updateLinks();
		
		} else {
		
			var td = $$('#translations td');
			td.each(function(element) {
				var span = element.getElement('span'); 
				element.store('tip:text', span.title);
				span.removeProperty('title');
			});
			var th = $$('#translations th.property_name');
			th.each(function(element) {
				element.store('tip:text', element.title);
				element.removeProperty('title');
			});
			new Tips(td);
			new Tips(th);
		}
		
	},

	updateLinks: function() {
		
		if ($('translations').getElements('td.missing').length == 0)
			$('a_miss').setStyle('display', 'none');
		else
			$('a_miss').setStyle('display', '');
		
		if ($('translations').getElements('td.modified').length == 0)
			$('a_mod').setStyle('display', 'none');
		else
			$('a_mod').setStyle('display', '');
		
		$('a_all').setStyle('display', '');
	}
	
};

window.addEvent('domready', W3C.start);