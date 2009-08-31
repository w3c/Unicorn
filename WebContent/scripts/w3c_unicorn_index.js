var W3C = {
	
	start: function(){
		
		W3C.Tabs = $('tabset_tabs');
		W3C.TabLinks = W3C.Tabs.getChildren('li a');
		
		W3C.TaskSelect = $('tasks');
		W3C.TaskOptions = W3C.TaskSelect.getChildren('option');
		W3C.TaskDescrip = $('task_descrip');
		W3C.TaskInputs = $$('input.task');
		
		W3C.LangParameter = $$('input.lang')[0].value;
		W3C.Forms = $$('form[action=observe]');
		
		// index of selected tab
		W3C.SelectedTab = 0;
		// index of selected task
		W3C.SelectedTask = W3C.TaskOptions.getProperty('value').indexOf(W3C.TaskInputs[0].value);
		// boolean: expand options
		W3C.WithOptions = false;
		
		W3C.Loader = new Element('img', {'src': 'images/ajax-loader.gif', 'class': 'loader'});
		W3C.prepareDocument();
		W3C.parseHash();
		W3C.updateHash();
		
		W3C.showTab(W3C.SelectedTab, false);
		W3C.selectTask(W3C.SelectedTask, false);
		W3C.toggleOptions(false);
		W3C.addOptionEvents();
		
		console.log(W3C.isUrl("http://www.w3.org"));
		console.log(W3C.isUrl("http://jigsaw.w3.org/css-validator/validator?uri=www.w3.org&profile=css3&usermedium=all&warning=1&lang=fr&output=ucn"));
		console.log(W3C.isUrl("http://localhost:8080/unicorn_dev/#validate-by-uri+task_full-css+with_options"));
		console.log(W3C.isUrl("http://www.%w3.org/TR/CSS21/colors.html#propdef-color"));
		console.log(W3C.isUrl("/w3.org"));
		console.log(W3C.isUrl("www.w3.org"));
	},
	
	prepareDocument: function(){
		
		$$('input#task_change').setStyle('display', 'none');
		
		W3C.TaskSelect.addEvent('change', function (event) {
			event.stop();
			W3C.selectTask(this.selectedIndex, true);
			W3C.updateHash();
		});
		
		W3C.TabLinks.each(function(link, i) {
			link.addEvent('click', function (event) {
				event.stop();
				W3C.showTab(i, true);
				W3C.updateHash();
			});
		});
		
		W3C.TaskOptions.addEvent('mouseover', function() {
			W3C.TaskDescrip.set('text', this.title);
		});
		
		W3C.TaskOptions.addEvent('mouseout', function() {
			W3C.TaskDescrip.set('text', W3C.TaskOptions[W3C.SelectedTask].title);
		});
		
	},
	
	addOptionEvents: function () {
		$$('fieldset.advanced legend').removeEvent('click');
		
		$$('fieldset.advanced legend').addEvent('click', function (event) {
			W3C.WithOptions = !W3C.WithOptions;
			W3C.toggleOptions(true);
			W3C.updateHash();
		});
	},
	
	showTab: function(tabIndex, withFX) {
		if (W3C.Tabs.getElements('li')[tabIndex] == W3C.Tabs.getElement('li.selected'))
			return;
		
		W3C.SelectedTab = tabIndex;
		W3C.Forms.each(function(form, i) {
			if (i != tabIndex) {
				form.setStyle('display', 'none');
			} else {
				if (withFX)
					form.setStyle('opacity', 0);
				form.setStyle('display', 'block');
			}
		});
		W3C.TabLinks.each(function(link, i) {
			if (i != tabIndex) {
				link.setProperty('class', '');
			} else {
				link.setProperty('class', 'selected');
			}
		});
		
		W3C.toggleOptions(false);
		
		if (withFX) {
			W3C.Forms[tabIndex].set('tween', {'duration': 350});
			W3C.Forms[tabIndex].tween('opacity', 0, 1);
		}
	},
	
	selectTask: function(taskIndex, withFX) {
		W3C.SelectedTask = taskIndex;
		var options = $$('fieldset.options');
		var taskId;
		
		W3C.TaskOptions.each(function(option, i) {
			if (i != taskIndex) {
				option.removeProperty('selected');
			} else {
				taskId = option.value;
				option.setProperty('selected', 'selected');
			}
		});
		W3C.TaskDescrip.set('text', W3C.TaskOptions[taskIndex].title);
		
		W3C.TaskInputs.each(function (input) {
			input.value = taskId;
		});
		
		var options = $$('fieldset.options');
		options.setStyle('display', 'none');
		
		options.getElements('.option_input').each(function (input) {
			input.setProperty('disabled', 'disabled');
		});
		
		var currentOptions = options.filter('fieldset.' + taskId);
		
		if (!currentOptions.length > 0)
			W3C.requestOptions(taskIndex, withFX);
		else {
			currentOptions.getElements('.option_input').each(function (input) {
				input.removeProperty('disabled');
			});
			currentOptions.setStyle('opacity', 0);
			currentOptions.setStyle('display', 'block');
			if (withFX) {
				currentOptions.set('tween', {'duration': 350});
				currentOptions.tween('opacity', 0, 1);
			}
			currentOptions.setStyle('opacity', 1);
		}
		
		W3C.toggleOptions(false);
	},
	
	requestOptions: function(taskIndex, withFX) {
		
		var req = new Request.HTML({url: window.location.pathname,
			method: 'get',
			onRequest: function() {
				W3C.Loader.injectBefore(W3C.Forms[W3C.SelectedTab].getElement('div.submit'));
			},
			onSuccess: function(responseTree, responseElements, responseHTML, responseJavaScript) {
				var option;
				responseElements.filter('fieldset.options').each(function(fieldset) {
					W3C.Forms.each(function (form, i) {
						var clone = fieldset.clone();
						clone.setStyle('opacity', 0);
						clone.injectBefore(form.getElement('div.submit'));
						if (withFX) {
							clone.set('tween', {'duration': 350});
							clone.tween('opacity', 0, 1);
						}
						clone.setStyle('opacity', 1);
					});
				});
				W3C.Loader.dispose();
				W3C.toggleOptions(false);
				W3C.addOptionEvents();
				return true;
			},
			onFailure: function() {
				// TODO
				return false;
			}
		});
		var queryString = 'ucn_task=' + W3C.TaskOptions[taskIndex].value + '&ucn_lang=' + W3C.LangParameter; 
		req.send(queryString);
	},
	
	toggleOptions: function(withFX) {
		var fieldsets = $$('fieldset.advanced');
		if (fieldsets.length == 0)
			return;
		
		if (W3C.WithOptions) {
			fieldsets.addClass('toggled');
			fieldsets.removeClass('toggles');
		}
		else {
			fieldsets.addClass('toggles');
			fieldsets.removeClass('toggled');
		}
		
		W3C.Forms.each(function (form, i) {
			
			var advancedOptions = form.getElements('fieldset.advanced div.options');
			
			if (withFX && i == W3C.SelectedTab) {
				if (W3C.WithOptions) {
					advancedOptions.slide('in');
				} else {
					advancedOptions.slide('out');
				}
			} else {
				if (W3C.WithOptions) {
					advancedOptions.slide('show');
				} else {
					advancedOptions.slide('hide');
				}
			}
		});
	},
	
	parseHash: function(){
		
		var hash = window.location.hash;
		
		if (hash == "") {
			return;
		}
		
		var tab = hash.replace('#', '').split('+');
		var selectedTab = tab[0];
		var selectedTask = tab[1];
		var withOptions = tab[2];
		
		// get selected tab
		var index = W3C.Forms.getProperty('id').indexOf(selectedTab);
		if (index == -1) {
			W3C.setHash('');
			return;
		}
		W3C.SelectedTab = index;
		
		// get selected task
		if (!selectedTask || !selectedTask.contains("task_")) {
			W3C.setHash(selectedTab);
			return;
		}
		var taskIndex =  W3C.TaskOptions.getProperty('value').indexOf(selectedTask.replace('task_', ''));
		if (taskIndex == -1) {
			W3C.setHash(selectedTab);
			return;
		}
		W3C.SelectedTask = taskIndex;
		
		// with_options ?
		if (!withOptions || !withOptions == "with_options") {
			W3C.setHash(selectedTab + "+" + selectedTask);
			return;
		}
		W3C.WithOptions = true;
	},
	
	updateHash: function(){
		var tab = W3C.Forms[W3C.SelectedTab].getProperty('id');
		var task = '+task_' + W3C.TaskOptions[W3C.SelectedTask].getProperty('value');
		var withOptions = W3C.WithOptions ? '+with_options' : '';
				
		W3C.setHash(tab + task + withOptions);
	},
	
	setHash: function(hash){
		if (window.webkit419){
			W3C.FakeForm = W3C.FakeForm || new Element('form', {'method': 'get'}).injectInside(document.body);
			W3C.FakeForm.setProperty('action', '#' + hash).submit();
		} else {
			window.location.hash = hash;
		}
	},
	
	isUrl: function(url){
		return	url.test(".*");
	}
	
};

window.addEvent('domready', W3C.start);