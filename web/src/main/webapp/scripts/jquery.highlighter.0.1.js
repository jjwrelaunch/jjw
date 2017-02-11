jQuery.fn.separator=function(valueFunction,options)
{var settings={elementFunction:function()
{return jQuery(this)},elementStyle:'separator'};if(options)
{jQuery.extend(settings,options)}
var lastVal='';jQuery(this).each(function()
{var el=jQuery(this);var curval=valueFunction.apply(el);var elToStyle;if(curval!=lastVal)
{lastVal=curval;elToStyle=settings.elementFunction.apply(el);elToStyle.addClass(settings.elementStyle)}
else
{elToStyle=settings.elementFunction.apply(el);elToStyle.removeClass(settings.elementStyle)}});return(this)};