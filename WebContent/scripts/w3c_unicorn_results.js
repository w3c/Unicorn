/* $Id: w3c_unicorn_results.js,v 1.13 2009-09-30 16:29:17 tgambet Exp $Id */
var W3C = {
	
	start: function() {
		
		W3C.Observers = $$('.observer');
		
		var slideDuration = 500;
		var invalidObservers = $$('.observer.invalid');
		var scroller = new Fx.Scroll(document);
		
		$$('.title').each(function(title) {
			title.addClass('pointer');
			var iconHolder = new Element('span', {'class': 'arrow'});
			var a;
			if (title.getElement('span.icons') != null)
				a = title.getElement('span.icons');
			else
				a = title.getElement('a.anchor');
			iconHolder.inject(a, 'after');
		});
		
		$$('.section').each(function(section) {
			var title = section.getElement('.title');
			var block = section.getElement('.block');
			section.store('fxSlide', new Fx.Slide(block, {'duration': slideDuration, 'link': 'cancel'}));
			section.store('block', block);
			title.addEvent('click', function(event) {
				W3C.toggle(section);
			});
		});
		
		$$('.observer .section').each(function(section) {
			if (section.hasClass('warnings'))
				W3C.close(section, false);
			if (section.hasClass('errors') || section.hasClass('infos'))
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
				/*(new Chain()).wait(slideDuration).chain(function() {
					W3C.setHash(observer.getProperty('id'));
				}).callChain();*/
			});
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
		});
		
		$('banner').addEvent('click', function() {
			W3C.closeAllObserversBut(W3C.Observers[0], true);
		});
		
		var mySmoothScroll = new Fx.SmoothScroll({
		    links: '.smooth',
		    wheelStops: true
		});
		
		W3C.cleanHash();
		W3C.parseHash();
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
		if (hash == "") {
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
		
		W3C.setHash(hash);
		
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
			window.location.hash = '#' + hash;
		}
	}
};

window.addEvent('domready', W3C.start);

