// Author: Thomas GAMBET.
// (c) COPYRIGHT MIT, ERCIM and Keio, 2009.
var W3C = {
	
	start: function(){
		
		/*W3C.Tabs = $('tabset_tabs');
		W3C.TabLinks = W3C.Tabs.getChildren('li a');
		
		W3C.TaskSelect = $('tasks');
		W3C.TaskOptions = W3C.TaskSelect.getChildren('option');
		W3C.TaskDescrip = $('task_descrip');
		W3C.TaskInputs = $$('input.task');
		
		W3C.LangParameter = $$('html').getProperty('lang')[0];
		
		W3C.Forms = $$('form.ucn_form');
		W3C.Action = W3C.Forms[0].getProperty('action');
		
		// index of selected tab
		W3C.SelectedTab = 0;
		// index of selected task
		W3C.SelectedTask = W3C.TaskOptions.getProperty('value').indexOf(W3C.TaskInputs[0].value);
		// boolean: expand options
		W3C.WithOptions = false;
		
		W3C.Loader = new Element('img', {'src': '/unicorn/images/ajax-loader.gif', 'class': 'loader'});
		W3C.prepareDocument();
		W3C.parseHash();
		//W3C.updateHash();
		
		W3C.showTab(W3C.SelectedTab, false);
		W3C.selectTask(W3C.SelectedTask, false);
		W3C.toggleOptions(false);
		W3C.addOptionEvents();*/
		
		W3C.Loader = new Element('img', {'src': '/unicorn/images/ajax-loader.gif', 'class': 'loader'});
		
		$$('a.init').each(function(link, i) {
			link.addEvent('click', function (event) {
				event.stop();
				W3C.init(link.get('href'));
			});
		});
		
	},
	
	init: function(task) {
		
		var req = new Request.HTML({url: task,
			method: 'get',
			/*onRequest: function() {
				W3C.Loader.injectBefore(W3C.Forms[W3C.SelectedTab].getElement('div.submit'));
			},*/
			onSuccess: function(responseTree, responseElements, responseHTML, responseJavaScript) {
				$('result').set('text', responseHTML);
				return true;
			},
			onFailure: function() {
				// TODO
				return false;
			}
		});
		var queryString = ''; 
		req.send(queryString);
	},
	
	setHash: function(hash){
		if (window.webkit419){
			W3C.FakeForm = W3C.FakeForm || new Element('form', {'method': 'get'}).injectInside(document.body);
			W3C.FakeForm.setProperty('action', '#' + hash).submit();
		} else {
			window.location.replace('#' + hash);
			//window.location.hash = '#' + hash;
		}
	}
	
};

window.addEvent('domready', W3C.start);