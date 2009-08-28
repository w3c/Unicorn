var W3C = {
	
	start: function() {
		
		W3C.Links = $$('h2').concat($$('h3'));
		W3C.Links.each(function(link) {
			link.addClass('pointer');
		});
		
		//$merge($$('h2'), $$('h3')).addClass('pointer');
		
		W3C.Observers = $$('div.observer');
		
		W3C.Observers.each(function(observer) {
			observer.getElement('h2').addEvent('click', function(event) {
				observer.getElement('div.results').slide('toggle');
			});
		});
		
		$$('div.warnings').getElement('div.result').slide('hide');
		if ($$('div.observer.invalid').length > 0)	
			$$('div.observer.valid').getElement('div.results').slide('hide');
		
		W3C.ResultBlock = $$('div.errors').concat($$('div.infos'), $$('div.warnings')); 
		
		W3C.ResultBlock.each(function(results) {
			results.getElement('h3').addEvent('click', function(event){
				results.getElement('div.result').slide('toggle');
				results.getParent('div').getParent('div').setStyle('height', 'auto');
			});
		});
		
		
	}
	
};

window.addEvent('domready', W3C.start);

