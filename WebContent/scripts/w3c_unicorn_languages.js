/* $Id:  */
var W3C = {
	
	start: function(){
		
		var tdOk = $$('#translations td.ok');
		tdOk.each(function(element) {
			element.store('tip:text', element.getElement('span').title);
		});
		
		new Tips($$('#translations td.ok'));
		
	}
	
};

window.addEvent('domready', W3C.start);