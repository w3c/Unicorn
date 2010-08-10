// Author: Thomas GAMBET.
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
var W3C = {
	
	start: function(){
		
		W3C.Loader = new Element('img', {'src': '/unicorn/images/ajax-loader.gif', 'class': 'loader'});
		
		$$('a.init').each(function(link, i) {
			var req = new Request.HTML({url: link.get('href'),
				link: 'ignore',
				noCache: 'true',
				method: 'get',
				update: $('result'),
				onRequest: function() {
					W3C.Loader.injectBefore($('result'));
				},
				onComplete: function() {
					W3C.Loader.dispose();
				}
			});
			
			link.addEvent('click', function (event) {
				event.stop();
				req.send();
			});
		});
		
	}
	
};

window.addEvent('domready', W3C.start);