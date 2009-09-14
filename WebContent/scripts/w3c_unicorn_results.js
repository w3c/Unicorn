var W3C = {
	
	start: function() {
		
		W3C.ObserverEvents = true;
		W3C.Observers = $$('.observer');
		
		var slideDuration = 500;
		var invalidObservers = $$('.observer.invalid');
		var scroller = new Fx.Scroll(document);
		
		$$('.title').each(function(title) {
			title.addClass('pointer');
			title.addClass('toggles');
		});
		
		$$('.section').each(function(section) {
			var title = section.getElement('.title');
			var block = section.getElement('.block');
			section.store('fxSlide', new Fx.Slide(block, {'duration': slideDuration, 'link': 'cancel'}));
			section.store('block', block);
			title.addEvent('click', function(event) {
				if (!section.hasClass('observer') || W3C.ObserverEvents)
					W3C.toggle(section);
			});
		});
		
		$$('.observer .section').each(function(section) {
			if (section.hasClass('warnings'))
				W3C.close(section, false).callChain();
			if (section.hasClass('errors') || section.hasClass('infos'))
				W3C.open(section, false).callChain();
		});
		
		$$('.observer .title a').each(function(a) {
			a.addEvent('click', function (event) {
				event.stopPropagation();
			});
		});
		
		$$('.observer').each(function(observer) {
			W3C.open(observer, false).callChain();
			if (observer.hasClass('valid') && invalidObservers.length > 0) 
				W3C.close(observer, false).callChain();
			observer.getElement('a.anchor').addEvent('click', function(event) {
				event.preventDefault();
				W3C.open(observer, true);
				scroller.toElement(observer);
				
				/*if (!observer.retrieve('open')) {
					W3C.closeAllObserversBut(observer, true).chain(function() {
						scroller.toElement(observer);
					});
				} else {
					scroller.toElement(observer);
				}*/
			});
		});
		
		var mySmoothScroll = new Fx.SmoothScroll({
		    links: '.smooth',
		    wheelStops: true
		});
	},
	
	toggle: function(section) {
		if (!section.hasClass('observer'))
			W3C.observersEvents(false);
		var title = section.getElement('.title');
		var slide = section.retrieve('fxSlide');
	    if (section.retrieve('open')) {
	    	/*title.removeClass('toggled');
	    	section.store('open', false);
	    	return slide.slideOut().chain(function(){
			    section.getElement('div').setStyle('height', '0');
			    slide.callChain();
			}).chain(function() {
				W3C.observersEvents(true);
				slide.callChain();
			});*/
	    	return W3C.close(section, true);
	    } else {
	    	/*title.addClass('toggled');
	    	section.store('open', true);
	    	return slide.slideIn().chain(function(){
			    section.getElement('div').setStyle('height', 'auto');
			    slide.callChain();
			}).chain(function() {
				W3C.observersEvents(true);
				slide.callChain();
			});*/
	    	return W3C.open(section, true);
	    }
		/*return slide.toggle().chain(function(){
		    if (section.retrieve('open'))
		    	section.getElement('div').setStyle('height', 'auto');
		    else
		    	section.getElement('div').setStyle('height', '0');
		    slide.callChain();
		}).chain(function() {
			W3C.observersEvents(true);
			slide.callChain();
		});*/
	},
	
	close: function(section, withFx) {
		var opened = section.retrieve('open');
		var title = section.getElement('.title');
		var slide = section.retrieve('fxSlide');
	    title.removeClass('toggled');
	    section.store('open', false);
		if (withFx && opened) {
			return slide.slideOut().chain(function(){
		    	section.getElement('div').setStyle('height', '0');
		    	slide.callChain();
			});
		} else {
			return slide.hide().chain(function(){
				section.getElement('div').setStyle('height', '0');
				slide.callChain();
			});
		}
	},
	
	open: function(section, withFx) {
		var closed = !section.retrieve('open');
		var title = section.getElement('.title');
		var slide = section.retrieve('fxSlide');
	    title.addClass('toggled');
	    section.store('open', true);
	    if (withFx && closed) {
	    	return slide.slideIn().chain(function(){
		    	section.getElement('div').setStyle('height', 'auto');
		    	slide.callChain();
			});
		} else {
	    	return slide.show().chain(function(){
	    		section.getElement('div').setStyle('height', 'auto');
	    		slide.callChain();
	    	});
	    }
	},
	
	closeAllObserversBut: function(observer, withFx) {
		W3C.Observers.each(function (ob) {
			if (ob != observer)
				W3C.close(ob, withFx).callChain();
		});
		return W3C.open(observer, withFx);
	},
	
	
	closeAllSectionsBut: function(observer, section, withFx) {
		return;
	},
	
	observersEvents: function(on) {
		if (on) {
			W3C.ObserverEvents = true;
			W3C.Observers.getElement('.title').addClass('pointer');
		} else {
			W3C.ObserverEvents = true;
			W3C.Observers.getElement('.title').removeClass('pointer');
		}
	},
	
	setHash: function(hash){
		if (window.webkit419){
			W3C.FakeForm = W3C.FakeForm || new Element('form', {'method': 'get'}).injectInside(document.body);
			W3C.FakeForm.setProperty('action', '#' + hash).submit();
		} else {
			window.location.hash = hash;
		}
	}
};

window.addEvent('domready', W3C.start);

