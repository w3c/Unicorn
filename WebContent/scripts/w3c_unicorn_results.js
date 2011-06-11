// Author: Thomas GAMBET.
// (c) COPYRIGHT MIT, ERCIM and Keio, 2010.
var W3C = {
	
	start: function() {
		
		W3C.cleanHash();
		
		W3C.ContextsToShow = 7;
		
		W3C.Observers = $$('.observer');
		
		$$('input#lang_change').setStyle('display', 'none');
		W3C.LanguagesForm = $('lang_choice');
		W3C.LanguagesForm.addEvent('change', function(event) {
			window.location = "./" + W3C.LanguagesForm.getProperty('action') + "?" + this.toQueryString() + window.location.hash;
		});
		
		var slideDuration = 500;
		var invalidObservers = $$('.observer.invalid');
		var scroller = new Fx.Scroll(document);
		var instantScroller = new Fx.Scroll(document, {'duration': 0});
		
		$$('.section').each(function(section) {
			var title = section.getElement('.title');
			var block = section.getElement('.block');
			
			if (!section.hasClass('observer') || section.getElement('.section') != null) {
				title.addClass('pointer');
				var iconHolder = new Element('span', {'class': 'arrow'});
				var a;
				if (title.getElement('span.icons') != null)
					a = title.getElement('span.icons');
				else
					a = title.getElement('a.anchor');
				iconHolder.inject(a, 'after');
			}
			
			section.store('fxSlide', new Fx.Slide(block, {'duration': slideDuration, 'link': 'cancel'}));
			section.store('block', block);
			title.addEvent('click', function(event) {
				W3C.toggle(section);
			});
		});
		
		$$('.observer .section').each(function(section) {
			if (section.hasClass('warnings'))
				W3C.close(section, false);
			else
				W3C.open(section, false);
		});
		
		$$('.observer .title a').each(function(a) {
			a.addEvent('click', function (event) {
				event.stopPropagation();
			});
		});
		
		$$('.observer').each(function(observer) {
			W3C.open(observer, false);
			if (observer.hasClass('valid') && invalidObservers.length > 0) 
				W3C.close(observer, false);
			observer.getElement('a.anchor').addEvent('click', function(event) {
				W3C.open(observer, true);
			});
			
			if (!observer.hasClass('grouped')) {
				if (observer.getElement('a.infos')) {
					observer.getElement('a.infos').addEvent('click', function(event) {
						event.preventDefault();
						W3C.open(observer, true);
						W3C.closeAllSectionsBut(observer, observer.getElement('div.infos'), true);
						scroller.toElement(observer);
					});
				}
				if (observer.getElement('a.errors')) {
					observer.getElement('a.errors').addEvent('click', function(event) {
						event.preventDefault();
						W3C.open(observer, true);
						W3C.closeAllSectionsBut(observer, observer.getElement('div.errors'), true);
						scroller.toElement(observer);
					});
				}
				if (observer.getElement('a.warnings')) {
					observer.getElement('a.warnings').addEvent('click', function(event) {
						event.preventDefault();
						W3C.open(observer, true);
						W3C.closeAllSectionsBut(observer, observer.getElement('div.warnings'), true);
						scroller.toElement(observer);
					});
				}
			}
		});
		
		$$('div#messages pre').slide('hide');
		
		$$('div#messages > div').each(function(message) {
			if (message.getElement('pre')) {
				message.addClass('pointer');
				message.addEvent('click', function(event) {
					message.getElement('pre').slide('toggle');
				});
			}
		});
		
		var mySmoothScroll = new Fx.SmoothScroll({
			links: '.smooth',
			wheelStops: true
		});
		
		if (window.location.hash == '#collapsed')
			$$('.observer').each(function(observer) {
				W3C.close(observer, false);
			});
		
		W3C.parseHash();
		
		var classes = $('observations').getProperty('class');
		
		var re = new RegExp(/contextShow:'[^']*'/);
		var m = re.exec(classes);
		var s =  "";
		for (i = 0; i < m.length; i++) {
			s = s + m[i];
		}
		var showString = s.replace('contextShow:', '').replace(/'/g, '');
		
		var re = new RegExp(/contextHide:'[^']*'/);
		var m = re.exec(classes);
		var s =  "";
		for (i = 0; i < m.length; i++) {
			s = s + m[i];
		}
		var hideString = s.replace('contextHide:', '').replace(/'/g, '');
		
		$$('td.message').each(function (td) {
			if (td.getProperty('rowspan') > W3C.ContextsToShow) {
				var mainTr = td.getParent('tr');
				var anchorTd = mainTr.getElement('td.anchor');
				var nbContexts = td.getProperty('rowspan');
				var i = 1;
				var tr = mainTr;
				
				var array = new Array();
				var index = 0;
				
				while (i <= nbContexts) {
					if (i > W3C.ContextsToShow) {
						tr.setStyle('display', 'none');
						array[index] = tr;
						index++;
					}
					
					if (i == nbContexts) {
						var showTr = new Element('tr', {'class' : 'showContext'});
						var showTd = new Element('td', {'colspan': '3'});
						showTd.addClass('pointer');
						showTd.setProperty('title', showString.replace(/%1/, array.length));
						showTd.set('text', showString.replace(/%1/, array.length));
						
						showTd.inject(showTr);
						showTr.inject(tr, 'after');
						
						showTr.addEvent('click', function(event) {
							
							var isClosed = array.some(function (tr) {
								if (tr.getStyle('display') == 'none') {
									return true;
								} else
									return false;
							});
							
							if (isClosed) {
								array.each(function (tr) {
									tr.setStyle('display', '');
								});
								td.setProperty('rowspan', nbContexts + 1);
								anchorTd.setProperty('rowspan', nbContexts + 1);
								showTd.setProperty('title', hideString);
								showTd.set('text', hideString);
								instantScroller.toElement(mainTr);
							} else {
								array.each(function (tr) {
									tr.setStyle('display', 'none');
								});
								td.setProperty('rowspan', W3C.ContextsToShow + 1);
								anchorTd.setProperty('rowspan', W3C.ContextsToShow + 1);
								showTd.setProperty('title', showString.replace(/%1/, array.length));
								showTd.set('text', showString.replace(/%1/, array.length));
								instantScroller.toElement(mainTr);
							}
							
						});
					}
					
					tr = tr.getNext('tr');
					i++;
				}
				
				td.setProperty('rowspan', W3C.ContextsToShow + 1);
				anchorTd.setProperty('rowspan', W3C.ContextsToShow + 1);
			}
		});
		
	},
	
	toggle: function(section) {
		var title = section.getElement('.title');
		var slide = section.retrieve('fxSlide');
		if (section.retrieve('open')) {
			W3C.close(section, true);
		} else {
			W3C.open(section, true);
		}
	},
	
	close: function(section, withFx) {
		var opened = section.retrieve('open');
		var title = section.getElement('.title');
		var slide = section.retrieve('fxSlide');
		title.removeClass('toggled');
		section.store('open', false);
		if (withFx && opened) {
			slide.slideOut().chain(function(){
				section.getElement('div').setStyle('display', 'none');
				slide.callChain();
			});
		} else {
			slide.hide();
			section.getElement('div').setStyle('display', 'none');
		}
	},
	
	open: function(section, withFx) {
		var closed = !section.retrieve('open');
		var title = section.getElement('.title');
		var slide = section.retrieve('fxSlide');
		title.addClass('toggled');
		section.store('open', true);
		section.getElement('div').setStyle('display', '');
		if (withFx && closed) {
			slide.slideIn().chain(function(){
				section.getElement('div').setStyle('height', 'auto');
				slide.callChain();
			});
		} else {
			slide.show();
			section.getElement('div').setStyle('height', 'auto');
		}
	},
	
	closeAllObserversBut: function(observer, withFx) {
		W3C.Observers.each(function (ob) {
			if (ob != observer)
				W3C.close(ob, withFx);
		});
		W3C.open(observer, withFx);
	},
	
	closeAllSectionsBut: function(observer, section, withFx) {
		observer.getElements('.section').each(function (sec) {
			if (sec != section) {
				W3C.close(sec, withFx);
			}
		});
		W3C.open(section, withFx);
	},
	
	parseHash: function(){
		var hash = window.location.hash;
		if (hash == "" || hash == '#collapsed') {
			return;
		}
		var tab = hash.replace('#', '').split('_');
		var observerId = tab[0];
		var sectionId = tab[1];
		var uriId = tab[2];
		var messageId = tab[3];
		
		var scroller = new Fx.Scroll(document, {duration: 0});
		
		var observerIndex = W3C.Observers.getProperty('id').indexOf(observerId);
		if (observerIndex == -1) {
			W3C.setHash('');
			return;
		}
		var observer = W3C.Observers[observerIndex];
		//W3C.closeAllObserversBut(observer, false);
		// OR //
		W3C.open(observer, false);
		
		if (!sectionId) {
			scroller.toElement(observer);
			return;
		}
		var sectionIndex =  observer.getElements('.section').getProperty('id').indexOf(observerId + '_' + sectionId);
		if (sectionIndex == -1) {
			W3C.setHash(observerId);
			return;
		}
		var section = observer.getElements('.section')[sectionIndex];
		W3C.closeAllSectionsBut(observer, section, false);
		
		
		if (!uriId) {
			scroller.toElement(observer);
			return;
		}
		var uriIndex =  section.getElements('td.uri').getProperty('id').indexOf(observerId + '_' + sectionId + '_' + uriId);
		if (uriIndex == -1) {
			W3C.setHash(observerId + '_' + sectionId);
			return;
		}
		var uriTd = section.getElements('td.uri')[uriIndex];
		
		if (!messageId) {
			scroller.toElement(uriTd);
			return;
		}
		
		W3C.setHash(hash.replace('#', ''));
		
	},
	
	updateHash: function(){
		var tab = W3C.Forms[W3C.SelectedTab].getProperty('id');
		var task = '+task_' + W3C.TaskOptions[W3C.SelectedTask].getProperty('value');
		var withOptions = W3C.WithOptions ? '+with_options' : '';
				
		W3C.setHash(tab + task + withOptions);
	},
	
	cleanHash: function(){
		var hash = window.location.hash;
		if (hash.match('^#validate-by'))
			W3C.setHash('');
	},
	
	setHash: function(hash){
		if (window.webkit419){
			W3C.FakeForm = W3C.FakeForm || new Element('form', {'method': 'get'}).injectInside(document.body);
			W3C.FakeForm.setProperty('action', '#' + hash).submit();
		} else {
			window.location.replace('#' + hash);
		}
	}
};

window.addEvent('domready', W3C.start);

