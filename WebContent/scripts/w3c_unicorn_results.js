var W3C = {
	
	start: function() {
		
		W3C.Links = $$('h2').concat($$('h3'));
		W3C.Links.each(function(link) {
			link.addClass('pointer');
			link.addClass('toggles');
		});
		
		$$('div.warnings').getElement('div.result').slide('hide');
		if ($$('div.observer.invalid').length > 0)	
			$$('div.observer.valid').getElement('div.results').slide('hide');
		else
			$$('div.observer.valid').getElement('h2').addClass('toggled');
		
		$$('div.observer.invalid').getElement('h2').addClass('toggled');
		$$('div.errors').getElement('h3').addClass('toggled');
		$$('div.infos').getElement('h3').addClass('toggled');
		
		$$('div.observer').each(function(observer) {
			observer.getElement('h2').addEvent('click', function(event) {
				observer.getElement('div.results').slide('toggle');
				this.toggleClass('toggled');
			});
		});
		
		W3C.ResultBlock = $$('div.errors').concat($$('div.infos'), $$('div.warnings')); 
		W3C.ResultBlock.each(function(results) {
			results.getElement('h3').addEvent('click', function(event){
				results.getElement('div.result').slide('toggle');
				results.getParent('div').getParent('div').setStyle('height', 'auto');
				this.toggleClass('toggled');
			});
		});
		
	}
};

window.addEvent('domready', W3C.start);

