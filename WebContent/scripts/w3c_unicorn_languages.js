/* $Id:  */
var W3C = {
	
	start: function(){
		
		var tdOk = $$('#translations td.ok');
		tdOk.each(function(element) {
			var span = element.getElement('span'); 
			element.store('tip:text', span.title);
			span.removeProperty('title');
		});
		
		new Tips(tdOk);
		
	}
	
};

window.addEvent('domready', W3C.start);