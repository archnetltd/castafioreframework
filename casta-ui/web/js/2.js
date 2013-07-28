jQuery(function(){jQuery.plugins=jQuery.plugins||{};
jQuery.plugins.cache=window.sessionStorage||{};
jQuery.plugins.settings={cache:true,ajax:{cache:true},context:jQuery(document),target:jQuery("head",this.context),init:function(){},preLoad:function(){},postLoad:function(){}};
var defaults=jQuery.plugins.settings,cache=jQuery.plugins.cache;
Plugin=function(name,settings){var that=this;
this.name=name;
for(var i in settings){that[i]=settings[i]
}this.context=this.context||settings.context;
this.target=this.target||settings.target;
this.loaded={};
this.queue=[];
this.init.apply(this);
return this
};
Plugin.prototype.getFile=function(url){if(!url||typeof url!="string"){throw new Error("jQuery.plugin.getFile(url) - url {String} must be specified")
}var that=this,extension=url.split(".")[url.split(".").length-1],fileId=url.replace(/\W/gi,""),cached=cache[url],caching=(defaults.cache===true||defaults.cache=="true");
if(extension!="css"&&extension!="js"){throw new Error("jQuery.plugin.getFile(url) - Invalid extension:"+extension+"\n\t"+url);
return this
}if(caching&&this.loaded[url]){return this
}this.beforeGet(url);
jQuery('[data-file-id="'+fileId+'"]').remove();
if(caching&&cached&&cached!="undefined"){if(extension=="css"){this.target.append('<style type="text/css" rel="stylesheet" data-file-id="'+fileId+'">'+cached+"</style>")
}else{if(extension=="js"){this.target.append('<script type="text/javascript" data-file-id="'+fileId+'">'+cached+"<\/script>")
}}setTimeout(function(){that.afterGet(url)
},0)
}else{if(extension=="css"){(function(){var opts=jQuery.extend({url:url},defaults.ajax),onSuccess=opts.success||function(){};
opts.success=function(response){onSuccess.apply(this,arguments);
that.loaded[url]=true;
cache[url]=response;
that.target.append('<style type="text/css" rel="stylesheet" data-file-id="'+fileId+'">'+response+"</style>");
that.afterGet(url)
};
jQuery.ajax(opts)
})()
}else{if(extension=="js"){(function(){var opts=jQuery.extend({dataType:"script",url:url},defaults.ajax),onSuccess=opts.success||function(){};
opts.success=function(){onSuccess.apply(this,arguments);
var response=(typeof arguments[0]=="string")?arguments[0]:null;
cache[url]=response;
that.loaded[url]=true;
that.afterGet(url)
};
jQuery.ajax(opts)
})()
}}}return this
};
Plugin.prototype.beforeGet=function(url){this.queue.push(url);
defaults.preLoad.call(this,url);
return this
};
Plugin.prototype.afterGet=function(url){var that=this,callback=this.tmp_callback,index=jQuery.inArray(url,this.queue);
if(index==-1){throw new Error("jQuery.plugin.afterGet(url) - Ignoring postLoad for file that should not be in queue:\n "+url);
return this
}this.queue.splice(index,1);
if(this.queue.length==0&&callback){setTimeout(function(){callback.apply(that);
delete that.tmp_callback
},0)
}defaults.postLoad.call(this,url);
return this
};
Plugin.prototype.get=function(){var that=this,files=(typeof this.files=="string")?[this.files]:this.files,callback=arguments[0]||this.callback;
this.tmp_callback=callback;
if(this.isNeeded()!==true){return this
}var getFile=function(file){that.getFile(file)
};
for(var i=0;
i<files.length;
i++){(function(){var file=files[i];
if(jQuery.browser.opera){setTimeout(function(){getFile(file)
},500)
}else{getFile(file)
}})()
}return this
};
Plugin.prototype.isNeeded=function(){var that=this,selectors=(typeof this.selectors=="string")?[this.selectors]:this.selectors,isNeeded;
for(var i=0;
i<selectors.length;
i++){var selector=selectors[i];
if(jQuery(selector,that.context).length>0){isNeeded=true;
break
}}return isNeeded||this
};
jQuery.extend(jQuery,{plugin:function(name,param){var self=jQuery.plugin;
if(arguments.length==0){for(var i in jQuery.plugins){if(i=="settings"||i=="cache"){continue
}jQuery.plugins[i].get()
}return self
}else{if(typeof name!="string"){throw new Error("jQuery.plugin(name,[settings||callback])\n\t\t@param name\t\t{String}\n\t\t@param settings\t{Object}\n\t\t@param callback\t{Function}");
return self
}}if(typeof param=="object"){jQuery.plugins[name]=new Plugin(name,jQuery.extend(defaults,param))
}else{var plugin=jQuery.plugins[name];
if(typeof plugin!="object"){throw new Error("jQuery.plugin: "+name+" is not specified");
return self
}if(typeof param=="function"){plugin.get(param)
}else{if(!param){return plugin
}}}return self
},getPlugin:function(){return jQuery.plugin.apply(this,arguments)
}})
});